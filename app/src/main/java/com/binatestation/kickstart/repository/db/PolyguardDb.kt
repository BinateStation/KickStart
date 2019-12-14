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

package com.binatestation.kickstart.repository.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.binatestation.kickstart.repository.db.dao.AccountDao
import com.binatestation.kickstart.repository.models.AccountModel

/**
 * Created by RKR on 13-08-2018.
 * PolyguardDb.
 */

@Database(
    entities = [
        AccountModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PolyguardDb : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @VisibleForTesting
        private val DATABASE_NAME = "Polyguard.db"

        @Volatile
        private var sInstance: PolyguardDb? = null

        fun getInstance(context: Context): PolyguardDb {
            return sInstance ?: synchronized(this) {
                sInstance ?: buildDatabase(context.applicationContext).also { sInstance = it }
            }
        }

        /**
         * Build the database. only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context): PolyguardDb {
            return Room.databaseBuilder(appContext, PolyguardDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
