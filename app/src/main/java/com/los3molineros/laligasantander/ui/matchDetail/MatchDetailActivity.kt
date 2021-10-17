package com.los3molineros.laligasantander.ui.matchDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.los3molineros.laligasantander.databinding.ActivityMatchDetailBinding

class MatchDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchDetailBinding
    private var matchId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        getIntentData()
    }

    private fun getIntentData() {
        val bundle: Bundle = intent.extras!!
        matchId = bundle.getInt("MATCH_ID")
        binding.txtPrueba.text = matchId.toString()
    }

    override fun onBackPressed() {
        finish()
    }
}