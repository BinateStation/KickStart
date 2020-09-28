package com.binatestation.kickstart.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.binatestation.kickstart.repository.models.UOMModel

/**
 * UomDao
 *
 * @author Raghu Krishnan R
 */
@Dao
interface UomDao {

    @get:Query("SELECT * FROM uom")
    val all: LiveData<List<UOMModel>>

    @Query("SELECT * FROM uom")
    fun getAllSync(): List<UOMModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(models: List<UOMModel>)

    @Query("select * from uom where id = :id")
    operator fun get(id: Int): LiveData<UOMModel>

    @Query("select * from uom where id = :id")
    fun getSync(id: Int): UOMModel

    @Query("SELECT * FROM uom")
    fun getAllPageSource(): PagingSource<Int, UOMModel>
}