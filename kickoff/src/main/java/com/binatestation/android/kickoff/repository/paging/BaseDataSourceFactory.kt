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
 * Last Updated at 9/4/20 11:44 AM.
 */

package com.binatestation.android.kickoff.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.binatestation.android.kickoff.repository.models.ApiResponse

@Deprecated(
    message = "BaseDataSourceFactory is no more needed"
)
class BaseDataSourceFactory<DataModelType : Any>(
    private val pageIndex: Int,
    private val pageSize: Int,
    private val getAllCallBack: (pageIndex: Int, pageSize: Int, apiCallBack: (ApiResponse<List<DataModelType>>) -> Unit) -> Unit
) : DataSource.Factory<String, DataModelType>() {
    val sourceLiveData = MutableLiveData<BasePageKeyedDataSource<DataModelType>>()

    override fun create(): DataSource<String, DataModelType> {
        val source = BasePageKeyedDataSource(pageIndex, pageSize, getAllCallBack)
        sourceLiveData.postValue(source)
        return source
    }
}
