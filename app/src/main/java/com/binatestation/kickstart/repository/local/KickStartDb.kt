/*
 * Copyright (c) 2020. Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.kickstart.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binatestation.kickstart.repository.local.dao.UomDao
import com.binatestation.kickstart.repository.models.UOMModel

/**
 * Created by RKR on 13-08-2018.
 * MyTyreDb.
 */

@Database(
    entities = [
        UOMModel::class
    ], version = 1, exportSchema = false
)
abstract class KickStartDb : RoomDatabase() {

    abstract fun uomDao(): UomDao

    companion object {
        private const val DATABASE_NAME = "KickStart.db"

        private var sInstance: KickStartDb? = null

        fun getInstance(context: Context): KickStartDb {
            return sInstance ?: synchronized(this) {
                sInstance ?: buildDatabase(context.applicationContext).also { sInstance = it }
            }
        }

        /**
         * Build the database. only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context): KickStartDb {
            return Room.databaseBuilder(appContext, KickStartDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
