package com.cheezycode.pagingdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.cheezycode.pagingdemo.R
import com.cheezycode.pagingdemo.databinding.ActivityMainBinding
import com.cheezycode.pagingdemo.paging.LoaderAdapter
import com.cheezycode.pagingdemo.paging.QuotePagingAdapter
import com.cheezycode.pagingdemo.paging.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var quoteViewModel: QuoteViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var quotePagingAdapter: QuotePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.quoteList)
        quotePagingAdapter = QuotePagingAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter =
            quotePagingAdapter.withLoadStateHeaderAndFooter(LoaderAdapter { quotePagingAdapter.refresh() },
                LoaderAdapter { quotePagingAdapter.retry() })

        lifecycleScope.launchWhenStarted {
            quoteViewModel.list.collectLatest {
                quotePagingAdapter.submitData(lifecycle, it)
            }
        }
        setData()
    }

    private fun setData() {
        quotePagingAdapter.let { adapter ->
            lifecycleScope.launch {
                adapter.loadStateFlow.collect {
                    if (it.refresh is LoadState.Loading && !it.refresh.endOfPaginationReached) {
                        showProgressBar()
                    }
                    if(it.refresh is LoadState.Error){
                        dismissProgressBar()
                        val error=(it.refresh as LoadState.Error).error.localizedMessage?:"No Internet"
                        showToast(error)
                    }

                    if(it.prepend is LoadState.NotLoading&&it.prepend.endOfPaginationReached){
                        dismissProgressBar()
                    }
                }
            }
        }
    }

      private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, "$message", Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility=View.VISIBLE
    }
    private fun dismissProgressBar() {
        binding.progressBar.visibility=View.GONE
    }
}