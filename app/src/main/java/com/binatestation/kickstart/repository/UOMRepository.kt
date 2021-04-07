/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.kickstart.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.binatestation.android.kickoff.repository.models.ApiResponse
import com.binatestation.android.kickoff.repository.paging.BasePageKeyedRemoteMediator
import com.binatestation.android.kickoff.repository.paging.BasePagingRepository
import com.binatestation.kickstart.repository.local.dao.UomDao
import com.binatestation.kickstart.repository.models.UOMModel
import com.binatestation.kickstart.repository.network.api.UOMApi

class UOMRepository(private val uomApi: UOMApi, private val uomDao: UomDao) :
    BasePagingRepository<UOMModel> {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAll(pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = BasePageKeyedRemoteMediator(
            getAllSuspend = { pageIndex, size ->
                ApiResponse.create(
                    response = uomApi.getAllSuspend(
                        pageIndex = pageIndex,
                        pageSize = size
                    )
                )
            },
            insertData = { data -> uomDao.insertAll(data) }
        )
    ) {
        uomDao.getAllPageSource()
    }.flow

}
