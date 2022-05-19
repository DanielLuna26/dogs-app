package com.softmoon.dogsapp.presentation.dogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.softmoon.dogsapp.R
import com.softmoon.dogsapp.databinding.DogActivityBinding
import com.softmoon.dogsapp.presentation.recycler_view.adapters.DogAdapterRecyclerView
import com.softmoon.dogsapp.utils.handleError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DogActivity : AppCompatActivity() {

    private val viewModel: DogViewModel by viewModels()
    private lateinit var binding: DogActivityBinding
    private lateinit var dogAdapter: DogAdapterRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DogActivityBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(root)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getDogs()
        setUpSwipeRefresh()
        setUpRecyclerView()
        collectStates()
    }

    private fun setUpSwipeRefresh() {
        binding.dogSrDogs.setOnRefreshListener {
            viewModel.getDogs()
        }
    }

    private fun setUpRecyclerView() {
        dogAdapter = DogAdapterRecyclerView()
        binding.dogRvDogs.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DogActivity)
            adapter = dogAdapter
        }
    }

    private fun collectStates() = lifecycleScope.launchWhenResumed {
        viewModel.viewState.collect {
            renderUI(it)
        }
    }

    private fun renderUI(viewSate: DogViewModel.ViewState) {
        when (viewSate) {
            is DogViewModel.ViewState.Failure -> {
                binding.dogSrDogs.isRefreshing = false
                inflateEmptyState()
                handleError(viewSate.res) {
                    viewModel.getDogs()
                }
            }
            DogViewModel.ViewState.Loading -> {
                binding.dogSrDogs.isRefreshing = true
            }
            is DogViewModel.ViewState.Success -> {
                binding.dogSrDogs.isRefreshing = false
                dogAdapter.items = viewSate.dogs
                if (viewSate.dogs.isEmpty())
                    inflateEmptyState()
            }
        }
    }

    private fun inflateEmptyState() {
        binding.dogVsEmpty.viewStub?.inflate()
    }
}