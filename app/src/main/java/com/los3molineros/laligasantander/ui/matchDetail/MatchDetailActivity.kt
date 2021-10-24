package com.los3molineros.laligasantander.ui.matchDetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.laligasantander.R
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.model.MatchResult
import com.los3molineros.laligasantander.data.remote.ApiDataSource
import com.los3molineros.laligasantander.data.remote.RetrofitClient
import com.los3molineros.laligasantander.databinding.ActivityMatchDetailBinding
import com.los3molineros.laligasantander.domain.matchDetail.MatchDetailRepoImpl
import com.los3molineros.laligasantander.presentation.MatchDetailViewModel
import com.los3molineros.laligasantander.presentation.MatchDetailViewModelFactory
import com.squareup.picasso.Picasso

class MatchDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchDetailBinding
    private lateinit var adapter: EventsAdapter
    private var matchId: Int? = null

    private val viewModel by viewModels<MatchDetailViewModel> {
        MatchDetailViewModelFactory(
            MatchDetailRepoImpl(
                ApiDataSource(RetrofitClient.webservice),
                FirestoreParams()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun initUI() {
        initBanner()
        getIntentData()
        initSubscribers()
    }

    private fun initBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

    private fun getIntentData() {
        val bundle: Bundle = intent.extras!!
        matchId = bundle.getInt("MATCH_ID")

        matchId?.let { match ->
            viewModel.getMatchData(match)
        }
    }

    private fun initSubscribers() {
        viewModel.matchDetail.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show()
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    loadUI(it.data)
                }
            }
        })
    }

    private fun loadUI(data: MatchResult) {
        // header
        //debugLog(description = "matchid: ${data.match_id}")
        Picasso.get().load(data.home_team.logo).into(binding.ivHomeLogo)
        Picasso.get().load(data.away_team.logo).into(binding.ivAwayLogo)
        binding.txtHomeTeam.text = data.home_team.short_code
        binding.txtAwayTeam.text = data.away_team.short_code
        binding.txtResult.text = data.stats.ft_score

        if (data.status_code == 3) {
            binding.txtStatus.text = getString(R.string.ended)
        } else {
            binding.txtStatus.text = "${data.minute.toString()}' "
        }

        adapter = EventsAdapter(data.match_events, data.home_team.team_id, data.away_team.team_id)
        binding.rvEvents.adapter = adapter
    }

}