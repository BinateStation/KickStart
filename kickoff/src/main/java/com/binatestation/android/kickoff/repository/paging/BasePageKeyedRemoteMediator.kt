/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.binatestation.android.kickoff.repository.models.*
import retrofit2.HttpException
import java.io.IOException

/**
 * BasePageKeyedRemoteMediator
 *
 * @author Raghu Krishnan R
 */
@OptIn(ExperimentalPagingApi::class)
class BasePageKeyedRemoteMediator<DataModelType : Any>(
    private val getAllSuspend: suspend (pageIndex: Int, pageSize: Int) -> ApiResponse<List<DataModelType>>,
    private val insertData: suspend (data: List<DataModelType>) -> Unit
) : RemoteMediator<Int, DataModelType>() {
    private var pageIndex: Int? = 1
    private var totalPages: Int = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataModelType>
    ): MediatorResult {
        try {

            pageIndex = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> if ((pageIndex ?: 0) > 1) ((pageIndex ?: 0) - 1) else null
                LoadType.APPEND -> if ((pageIndex ?: totalPages) < totalPages) ((pageIndex
                    ?: 0) + 1) else null
            }

            if (pageIndex == null) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            val data = getAllSuspend(
                pageIndex ?: 1,
                if (pageIndex == 1) state.config.pageSize else state.config.pageSize
            )
            var endOfPaginationReached = false
            when (data) {
                is ApiSuccessResponse -> {
                    insertData(data.body)
                    totalPages = data.totalPages?.toIntOrNull() ?: 0
                    endOfPaginationReached = data.body.isEmpty()
                }
                is ApiErrorResponse -> {
                    MediatorResult.Error(ApiErrorThrowable(data.errorMessage))
                    endOfPaginationReached = true
                }
                is ApiEmptyResponse -> {
                    MediatorResult.Error(ApiNoDataThrowable())
                    endOfPaginationReached = true
                }
                is ApiNoNetworkResponse -> {
                    MediatorResult.Error(ApiErrorThrowable(data.errorMessage))
                    endOfPaginationReached = true
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}