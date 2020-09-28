package com.binatestation.android.kickoff.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadStateAdapter
import com.binatestation.android.kickoff.R
import com.binatestation.android.kickoff.repository.models.EmptyStateModel
import com.binatestation.android.kickoff.utils.adapters.holders.EmptyStateViewHolder
import com.binatestation.android.kickoff.utils.listeners.ViewBinder

class NetworkLoadStateAdapter : LoadStateAdapter<EmptyStateViewHolder>() {
    override fun onBindViewHolder(holder: EmptyStateViewHolder, loadState: LoadState) {
        val viewBinder = holder as ViewBinder
        viewBinder.bindView(getEmptyStateModelFromNetworkState(loadState))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): EmptyStateViewHolder {
        return EmptyStateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_empty_state, parent, false
            )
        )
    }

    private fun getEmptyStateModelFromNetworkState(loadState: LoadState): EmptyStateModel {
        return when (loadState) {
            is LoadState.Loading -> EmptyStateModel.loadingDataModel
            is Error -> EmptyStateModel(message = loadState.error.localizedMessage)
            else -> EmptyStateModel.unKnownEmptyModel
        }
    }

}