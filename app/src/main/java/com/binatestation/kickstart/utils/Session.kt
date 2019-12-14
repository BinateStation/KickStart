/*
 * Created By RKR
 * Last Updated at 14/12/19 4:09 PM.
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

package com.binatestation.kickstart.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.binatestation.kickstart.repository.models.AuthTokenModel
import com.binatestation.kickstart.ui.splash.SplashActivity
import com.binatestation.kickstart.utils.Constants.KEY_ACCESS_TOKEN
import com.binatestation.kickstart.utils.Constants.KEY_CREATED_AT
import com.binatestation.kickstart.utils.Constants.KEY_EXPIRES_IN
import com.binatestation.kickstart.utils.Constants.KEY_PRIMARY_SHOP_ID
import com.binatestation.kickstart.utils.Constants.KEY_REFRESH_TOKEN
import com.binatestation.kickstart.utils.Constants.KEY_SCOPE
import com.binatestation.kickstart.utils.Constants.KEY_TOKEN_EXPIRES_AT
import com.binatestation.kickstart.utils.Constants.KEY_TOKEN_TYPE

@Suppress("unused")
/**
 * Created by RKR on 14-08-2018.
 * Session.
 */
object Session {
    private const val KEY_SESSION = "SESSION"
    val repoListRateLimit = RateLimiter()

    fun setPrimaryShopId(context: Context, primaryShopId: Long) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit()
            .putLong(KEY_PRIMARY_SHOP_ID, primaryShopId).apply()
    }

    fun getPrimaryShopId(context: Context): Long {
        return context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE)
            .getLong(KEY_PRIMARY_SHOP_ID, 0)
    }

    fun setTokenExpiresAt(context: Context, time: Long) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit()
            .putLong(KEY_TOKEN_EXPIRES_AT, time).apply()
    }

    fun getTokenExpiresAt(context: Context): Long {
        return context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE)
            .getLong(KEY_TOKEN_EXPIRES_AT, 0)
    }

    fun setToken(context: Context, authTokenModel: AuthTokenModel) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit()
            .putString(KEY_ACCESS_TOKEN, authTokenModel.accessToken)
            .putString(KEY_TOKEN_TYPE, authTokenModel.tokenType)
            .putInt(KEY_EXPIRES_IN, authTokenModel.expiresIn)
            .putString(KEY_REFRESH_TOKEN, authTokenModel.refreshToken)
            .putString(KEY_SCOPE, authTokenModel.scope)
            .putLong(KEY_CREATED_AT, authTokenModel.createdAt)
            .apply()
    }

    fun getToken(context: Context): AuthTokenModel {
        return AuthTokenModel(
            context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(
                KEY_ACCESS_TOKEN,
                ""
            ) ?: "",
            context.getSharedPreferences(
                KEY_SESSION,
                Context.MODE_PRIVATE
            ).getString(KEY_TOKEN_TYPE, "") ?: "",
            context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getInt(
                KEY_EXPIRES_IN,
                0
            ),
            context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(
                KEY_REFRESH_TOKEN,
                ""
            ) ?: "",
            context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(KEY_SCOPE, "")
                ?: "",
            context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getLong(
                KEY_CREATED_AT,
                0
            )
        )
    }

    fun clearAccessToken(context: Context) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit()
            .remove(KEY_ACCESS_TOKEN).apply()
    }

    fun logout(activity: Activity?) {
        activity?.let {
            it.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit().clear().apply()
            PreferenceManager.getDefaultSharedPreferences(it).edit().clear().apply()
            val intent = Intent(it, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            it.startActivity(intent)
            it.finishAndRemoveTask()
        }
    }


}
