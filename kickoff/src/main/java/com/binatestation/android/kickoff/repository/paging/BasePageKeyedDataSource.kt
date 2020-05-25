/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last Updated at 25/5/20 6:38 PM.
 */

package com.binatestation.android.kickoff.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.binatestation.android.kickoff.repository.models.*

class BasePageKeyedDataSource<DataModelType>(
    private val pageIndex: Int,
    private val pageSize: Int,
    private val getAllCallBack: (pageIndex: Int, pageSize: Int, apiCallBack: (ApiResponse<List<DataModelType>>) -> Unit) -> Unit
) : PageKeyedDataSource<String, DataModelType>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()


    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, DataModelType>
    ) {
        networkState.postValue(NetworkState.LOADING)
        getAll(
            pageIndex = params.key.toIntOrNull() ?: 1,
            pageSize = params.requestedLoadSize,
            callback = { data, currentPage, totalPages ->
                callback.onResult(
                    data,
                    getAdjacentPageKey(false, currentPage, totalPages)
                )
            }
        ) {
            retry = if (it) null else {
                { loadBefore(params, callback) }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, DataModelType>
    ) {
        networkState.postValue(NetworkState.LOADING)
        getAll(
            pageIndex = params.key.toIntOrNull() ?: 1,
            pageSize = params.requestedLoadSize,
            callback = { data, currentPage, totalPages ->
                callback.onResult(
                    data,
                    getAdjacentPageKey(true, currentPage, totalPages)
                )
            }
        ) {
            retry = if (it) null else {
                { loadAfter(params, callback) }
            }
        }
    }

    private fun getAdjacentPageKey(
        isIncrement: Boolean,
        currentPage: String?,
        totalPages: String?
    ): String? {
        val intOfCurrentPage = currentPage?.toIntOrNull()
        val intOfTotalPages = totalPages?.toIntOrNull() ?: 0
        return intOfCurrentPage?.let {
            if (isIncrement) {
                if (it < intOfTotalPages) (it + 1).toString() else null
            } else {
                if (it <= 1) null else (it - 1).toString()
            }
        }
    }

    private fun getBeforePageKey(
        currentPage: String?
    ): String? {
        val intOfCurrentPage = currentPage?.toIntOrNull()
        return intOfCurrentPage?.let {
            if (it <= 1) null else (it - 1).toString()
        }
    }

    private fun getAfterPageKey(
        currentPage: String?,
        totalPages: String?
    ): String? {
        val intOfCurrentPage = currentPage?.toIntOrNull()
        val intOfTotalPages = totalPages?.toIntOrNull() ?: 0
        return intOfCurrentPage?.let {
            if (it < intOfTotalPages) (it + 1).toString() else null
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, DataModelType>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        getAll(
            pageIndex = if (pageIndex <= 0) 1 else pageIndex,
            pageSize = if (pageSize <= 0) 10 else pageSize,
            callback = { data, currentPage, totalPages ->
                initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(
                    data,
                    getBeforePageKey(currentPage),
                    getAfterPageKey(currentPage, totalPages)
                )
            }
        ) {
            retry = if (it) null else {
                {
                    loadInitial(params, callback)
                    initialLoad.postValue(NetworkState.error("unknown error"))
                }
            }
        }
    }

    fun getAll(
        pageIndex: Int,
        pageSize: Int,
        callback: (data: List<DataModelType>, currentPage: String?, totalPages: String?) -> Unit,
        retry: (Boolean) -> Unit
    ) {
        getAllCallBack(pageIndex, pageSize) {
            when (it) {
                is ApiSuccessResponse -> {
                    val items = it.body
                    retry(true)
                    callback(items, it.currentPage, it.totalPages)
                    networkState.postValue(NetworkState.LOADED)
                }
                is ApiErrorResponse -> {
                    retry(false)
                    networkState.postValue(NetworkState.error(it.errorMessage))
                }
                is ApiEmptyResponse -> {
                    retry(false)
                    networkState.postValue(NetworkState.NO_DATA)
                }
                is ApiNoNetworkResponse -> {
                    retry(false)
                    networkState.postValue(NetworkState.NO_INTERNET)
                }
            }
        }
    }
}