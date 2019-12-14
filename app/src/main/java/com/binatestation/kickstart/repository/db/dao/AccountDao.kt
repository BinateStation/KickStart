/*
 * Created By RKR
 * Last Updated at 14/12/19 3:54 PM.
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

package com.binatestation.kickstart.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.binatestation.kickstart.repository.models.AccountModel

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(models: List<AccountModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: AccountModel)

    @Delete
    fun delete(model: AccountModel)

    @Query("Select * from Account where id =:id")
    fun get(id: Long): AccountModel

    @Query("Select * from Account where id =:id")
    fun getSync(id: Long): AccountModel?

    @Query("Select * from Account")
    fun getAll(): LiveData<List<AccountModel>>

    @Query("Select * from Account")
    fun getAllSync(): List<AccountModel>
}