package com.los3molineros.laligasantander.ui.standings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.laligasantander.R
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.remote.ApiDataSource
import com.los3molineros.laligasantander.data.remote.RetrofitClient
import com.los3molineros.laligasantander.databinding.FragmentStandingBinding
import com.los3molineros.laligasantander.domain.standings.StandingsRepoImpl
import com.los3molineros.laligasantander.presentation.StandingViewModel
import com.los3molineros.laligasantander.presentation.StandingViewModelFactory


class StandingFragment : Fragment(R.layout.fragment_standing) {
    private lateinit var binding: FragmentStandingBinding
    private lateinit var adapter: StandingAdapter
    private val viewModel by viewModels<StandingViewModel> {
        StandingViewModelFactory(
            StandingsRepoImpl(
                ApiDataSource(RetrofitClient.webservice),
                FirestoreParams()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStandingBinding.bind(view)
        binding.ivHelp.setOnClickListener {
            Snackbar.make(
                binding.root,
                "${getString(R.string.help_standing)}",
                BaseTransientBottomBar.LENGTH_LONG
            ).show()
        }
        initUI()
    }

    private fun initUI() {
        viewModel.standingUIList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter = StandingAdapter(it.data)
                    binding.rvStandings.adapter = adapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show()
                }
            }
        })
    }


}