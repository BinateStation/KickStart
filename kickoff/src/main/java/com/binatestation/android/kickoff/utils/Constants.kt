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
 * Last Updated at 7/4/20 5:37 PM.
 */

package com.binatestation.android.kickoff.utils

/**
 * Created by RKR on 17/10/2016.
 * Constants.
 */

object Constants {
    /**
     * General Constants Keys
     */
    object GeneralConstants {
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_MESSAGE = "message"
        const val KEY_MESSAGE_TITLE = "message_title"
        const val KEY_PASSWORD = "password"
        const val KEY_GRANT_TYPE = "grant_type"
        const val KEY_CLIENT_ID = "client_id"
        const val KEY_CLIENT_SECRET = "client_secret"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_TOKEN_TYPE = "token_type"
        const val KEY_EXPIRES_IN = "expires_in"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_SCOPE = "scope"
        const val KEY_CREATED_AT = "created_at"
        const val KEY_TOKEN = "token"
        const val KEY_TOKEN_EXPIRES_AT = "token_expires_at"
        const val KEY_PAGE = "page"
        const val KEY_ITEMS = "items"
        const val KEY_CURRENT_PAGE = "Current-Page"
        const val KEY_TOTAL_PAGES = "Total-Pages"
    }

    /**
     * API End URL
     */
    object EndUrls {
        const val END_URL_OAUTH_TOKEN = "oauth/token"
        const val END_URL_OAUTH_REVOKE = "oauth/revoke"
        const val END_URL_ME = "/me"
        const val END_URL_USERS = "/users"
    }
}
