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
 * Last Updated at 8/4/20 6:19 PM.
 */

package com.binatestation.android.kickoff.repository.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.binatestation.android.kickoff.repository.models.PagedResponseModel
import retrofit2.Call

abstract class BasePagedRepository<DataModelType> {

    fun getAll(
        pageIndex: Int,
        pageSize: Int,
        getAllCallBack: (pageIndex: Int, pageSize: Int) -> Call<List<DataModelType>>
    ): LiveData<PagedResponseModel<DataModelType>> {
        val data = MutableLiveData<PagedResponseModel<DataModelType>>()
        val sourceFactory =
            BaseDataSourceFactory(pageIndex, pageSize, getAllCallBack)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            pageSize = pageSize
        )

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
