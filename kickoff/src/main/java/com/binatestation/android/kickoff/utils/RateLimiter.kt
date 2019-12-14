/*
 * Created By RKR
 * Last Updated at 14/12/19 5:32 PM.
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

package com.binatestation.android.kickoff.utils

import android.os.SystemClock
import androidx.collection.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 */
class RateLimiter {
    private val timestamps = ArrayMap<String, Long>()

    @Synchronized
    fun shouldFetch(key: String, timeoutVal: Int, timeUnit: TimeUnit): Boolean {
        val timeout = timeUnit.toMillis(timeoutVal.toLong())
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null) {
            timestamps[key] = now
            return true
        }
        if (now - lastFetched > timeout) {
            timestamps[key] = now
            return true
        }
        return false
    }

    private fun now() = SystemClock.uptimeMillis()

}
