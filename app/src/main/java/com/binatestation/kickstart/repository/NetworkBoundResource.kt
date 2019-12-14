/*
 * Created By RKR
 * Last Updated at 14/12/19 5:08 PM.
 *
 * Copyright (c) 2019. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.binatestation.kickstart.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.binatestation.android.kickoff.repository.models.Resource
import com.binatestation.kickstart.repository.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()
    private var dbSource = MutableLiveData<ResultType>()

    init {
        GlobalScope.launch {
            val data = loadFromDb()
            withContext(Dispatchers.Main) {
                dbSource.value = data
                if (shouldFetch(dbSource.value)) {
                    fetchFromNetwork()
                } else {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.value = newValue
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }

        apiResponse.enqueue(object : Callback<ResultType> {
            override fun onFailure(call: Call<ResultType>, throwable: Throwable) {
                postValue(ApiResponse.create(throwable))
            }

            override fun onResponse(call: Call<ResultType>, response: Response<ResultType>) {
                postValue(ApiResponse.create(call, response))
            }
        })
    }

    private fun postValue(apiResponse: ApiResponse<ResultType>) {
        when (apiResponse) {
            is ApiSuccessResponse -> {
                val data = processResponse(apiResponse)
                saveCallResult(data)
                setValue(Resource.success(data))
            }
            is ApiEmptyResponse -> {
                onEmptyResponse()
                setValue(Resource.success(null))
            }
            is ApiErrorResponse -> {
                onFetchFailed()
                setValue(Resource.error(apiResponse.errorMessage, null))
            }
            is ApiNoNetworkResponse -> {
                onNotConnected()
                setValue(Resource.error(apiResponse.errorMessage, null))
            }
        }
    }

    protected open fun onFetchFailed() {}
    protected open fun onNotConnected() {}

    protected open fun onEmptyResponse() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<ResultType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType? = null): Boolean

    @WorkerThread
    protected open fun loadFromDb(): ResultType? = dbSource.value

    @MainThread
    protected abstract fun createCall(): Call<ResultType>
}
