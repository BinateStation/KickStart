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
 * Last Updated at 9/4/20 11:50 AM.
 */

package com.binatestation.kickstart.repository

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