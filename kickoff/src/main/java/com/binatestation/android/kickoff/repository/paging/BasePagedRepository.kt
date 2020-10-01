/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.repository.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.binatestation.android.kickoff.repository.models.ApiResponse
import com.binatestation.android.kickoff.repository.models.PagedResponseModel

@Deprecated(
    message = "BasePagedRepository is deprecated and has been replaced by BasePagingRepository",
    replaceWith = ReplaceWith(
        "BasePagingRepository<DataModelType>",
        "com.binatestation.android.kickoff.repository.paging.BasePagingRepository"
    )
)
abstract class BasePagedRepository<DataModelType : Any> {

    fun getAll(
        pageIndex: Int,
        pageSize: Int,
        getAllCallBack: (pageIndex: Int, pageSize: Int, apiCallBack: (ApiResponse<List<DataModelType>>) -> Unit) -> Unit
    ): LiveData<PagedResponseModel<DataModelType>> {
        val data = MutableLiveData<PagedResponseModel<DataModelType>>()
        val sourceFactory =
            BaseDataSourceFactory(pageIndex, pageSize, getAllCallBack)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = Pager(
            PagingConfig(
                pageSize = pageSize
            ),
            null,
            sourceFactory.asPagingSourceFactory(
                ArchTaskExecutor.getIOThreadExecutor().asCoroutineDispatcher()
            )
        ).liveData

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        data.value = PagedResponseModel(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
        return data
    }

}
