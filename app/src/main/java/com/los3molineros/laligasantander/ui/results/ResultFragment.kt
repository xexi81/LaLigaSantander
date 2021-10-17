package com.los3molineros.laligasantander.ui.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.laligasantander.R
import com.los3molineros.laligasantander.common.CommonFunctions
import com.los3molineros.laligasantander.common.CommonFunctions.debugLog
import com.los3molineros.laligasantander.common.Resource
import com.los3molineros.laligasantander.data.firestore.FirestoreParams
import com.los3molineros.laligasantander.databinding.FragmentResultBinding
import com.los3molineros.laligasantander.domain.results.ResultsRepoImpl
import com.los3molineros.laligasantander.presentation.ResultsViewModel
import com.los3molineros.laligasantander.presentation.ResultsViewModelFactory


class ResultFragment : Fragment(R.layout.fragment_result) {
    private lateinit var binding: FragmentResultBinding
    private lateinit var adapter: ResultAdapter

    private val viewModel by viewModels<ResultsViewModel> {
        ResultsViewModelFactory(ResultsRepoImpl(FirestoreParams(), requireContext()))
    }

    private var round: Int? = null
    private var maxRound: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentResultBinding.bind(view)
        adapter = ResultAdapter(null)
        binding.rvResults.adapter = adapter


        initUI()
    }


    private fun initUI() {
        initSubscribers()
        initRecycler()
    }

    private fun initSubscribers() {
        viewModel.maxRound.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    debugLog(description = "maxRound is ${it.data}")
                    maxRound = it.data
                }
                is Resource.Failure -> {
                    Snackbar.make(
                        binding.root,
                        "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show()
                    CommonFunctions.debugLog(description = it.exception.message.toString())
                }
            }
        })

        viewModel.round.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {

                    round = it.data
                    debugLog(description = "round is $round")

                    initHeader()
                    initListeners()
                }
                is Resource.Failure -> {
                    Snackbar.make(
                        binding.root,
                        "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show()
                    CommonFunctions.debugLog(description = it.exception.message.toString())
                }
            }
        })
    }

    private fun initHeader() {
        if (round == 1) {
            binding.txtBackRound.visibility = View.INVISIBLE
            binding.ivBackRound.visibility = View.INVISIBLE
        } else {
            binding.txtBackRound.visibility = View.VISIBLE
            binding.ivBackRound.visibility = View.VISIBLE
            val pastRound = round?.minus(1)
            binding.txtBackRound.text = "${getString(R.string.j)}$pastRound"
        }

        if (round == maxRound) {
            binding.txtNextRound.visibility = View.INVISIBLE
            binding.ivNextRound.visibility = View.INVISIBLE
        } else {
            binding.txtNextRound.visibility = View.VISIBLE
            binding.ivNextRound.visibility = View.VISIBLE
            val nextRound = round?.plus(1)
            binding.txtNextRound.text = "${getString(R.string.j)}$nextRound"
        }

        binding.txtRound.text = "${getString(R.string.round)} $round"
    }

    private fun initRecycler() {
        viewModel.matchList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    debugLog(description = it.data.toString())
                    binding.progressBar.visibility = View.GONE

                    adapter.setResults(it.data)

                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show()
                    CommonFunctions.debugLog(description = it.exception.message.toString())
                }
            }
        })
    }

    private fun initListeners() {

        round?.let { r ->
            binding.txtBackRound.setOnClickListener {
                viewModel.otherRound(r - 1)
                initUI()
            }

            binding.ivBackRound.setOnClickListener {
                viewModel.otherRound(r - 1)
                initUI()
            }

            binding.txtNextRound.setOnClickListener {
                viewModel.otherRound( r + 1)
                initUI()
            }

            binding.ivNextRound.setOnClickListener {
                viewModel.otherRound( r + 1)
                initUI()
            }

        }
    }

}





