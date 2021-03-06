/*
 * (c) Binate Station Private Limited. All rights reserved.
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
    @Suppress("unused")
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
        const val KEY_REQUEST_ID = "request_id"
        const val KEY_LINK = "link"
        const val KEY_PAGE_ITEMS = "Page-Items"
        const val KEY_TOTAL_COUNT = "Total-Count"
    }

    /**
     * API End URL
     */
    @Suppress("unused")
    object EndUrls {
        const val END_URL_OAUTH_TOKEN = "oauth/token"
        const val END_URL_OAUTH_REVOKE = "oauth/revoke"
        const val END_URL_ME = "/me"
        const val END_URL_USERS = "/users"
    }
}
