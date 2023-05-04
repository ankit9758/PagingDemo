package com.cheezycode.pagingdemo.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheezycode.pagingdemo.R
import com.cheezycode.pagingdemo.databinding.ItemLoaderBinding

class LoaderAdapter(private val retry: () -> Unit):LoadStateAdapter<LoaderAdapter.LoadViewHolder>() {


    class LoadViewHolder(private val binding: ItemLoaderBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }
        fun bind(loadState:LoadState){
            binding.apply {
                progressBarLoding.isVisible=loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvError.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val binding= DataBindingUtil.inflate<ItemLoaderBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_loader,parent,false)
        return LoadViewHolder(binding,retry)
    }
}