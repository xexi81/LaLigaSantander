package com.los3molineros.laligasantander.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.laligasantander.common.CommonFunctions.debugLog
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.data.remote.RetrofitClient
import com.los3molineros.laligasantander.data.remote.ApiDataSource
import com.los3molineros.laligasantander.databinding.ActivityMainBinding
import com.los3molineros.laligasantander.domain.MainRepoImpl
import com.los3molineros.laligasantander.presentation.MainViewModel
import com.los3molineros.laligasantander.presentation.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(MainRepoImpl(ApiDataSource(RetrofitClient.webservice), FirestoreParams()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.searchLeagueId().observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtLiga.visibility = View.GONE
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLiga.visibility = View.GONE
                    Snackbar.make(binding.root, "${it.exception.message}", BaseTransientBottomBar.LENGTH_LONG).show()
                    debugLog(description = it.exception.message.toString())
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLiga.visibility = View.VISIBLE
                    binding.txtLiga.text = it.data.toString()
                }
            }
        })


    }
}