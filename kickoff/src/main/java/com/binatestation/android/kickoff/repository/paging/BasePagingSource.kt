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

import androidx.paging.PagingSource
import com.binatestation.android.kickoff.repository.models.*
import retrofit2.HttpException
import java.io.IOException

open class BasePagingSource<DataModelType : Any>(
    private val getAllSuspend: suspend (pageIndex: Int, pageSize: Int) -> ApiResponse<List<DataModelType>>
) : PagingSource<String, DataModelType>() {


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

    override suspend fun load(params: LoadParams<String>): LoadResult<String, DataModelType> {
        return try {
            val data = getAllSuspend(
                params.key?.toIntOrNull() ?: 1,
                params.loadSize
            )
            when (data) {
                is ApiSuccessResponse -> {
                    LoadResult.Page(
                        data = data.body,
                        prevKey = getBeforePageKey(data.currentPage),
                        nextKey = getAfterPageKey(data.currentPage, data.totalPages)
                    )
                }
                is ApiErrorResponse -> {
                    LoadResult.Error(ApiErrorThrowable(data.errorMessage))
                }
                is ApiEmptyResponse -> {
                    LoadResult.Error(ApiNoDataThrowable())
                }
                is ApiNoNetworkResponse -> {
                    LoadResult.Error(ApiErrorThrowable(data.errorMessage))
                }
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}