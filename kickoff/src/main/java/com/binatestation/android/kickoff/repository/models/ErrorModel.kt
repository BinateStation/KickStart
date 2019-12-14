/*
 * Created By RKR
 * Last Updated at 14/12/19 5:35 PM.
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

package com.binatestation.android.kickoff.repository.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.binatestation.android.kickoff.R

import org.json.JSONObject


/**
 * Created by RKR on 04-04-2017.
 * ErrorModel
 */

class ErrorModel : Parcelable {

    var show: Boolean = true
    var errorTitle: String? = null
    var errorMessage: String? = null

    constructor(errorTitle: String, errorMessage: String) {
        this.errorTitle = errorTitle
        this.errorMessage = errorMessage
    }

    constructor(jsonObject: JSONObject) {
        this.errorTitle = jsonObject.optString(KEY_ERROR_TITLE)
        this.errorMessage = jsonObject.optString(KEY_ERROR_MESSAGE)
    }

    private constructor(`in`: Parcel) {
        this.errorTitle = `in`.readString()
        this.errorMessage = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(errorTitle)
        out.writeString(errorMessage)
    }

    companion object CREATOR : Parcelable.Creator<ErrorModel> {
        override fun createFromParcel(parcel: Parcel): ErrorModel {
            return ErrorModel(
                parcel
            )
        }

        override fun newArray(size: Int): Array<ErrorModel?> {
            return arrayOfNulls(size)
        }

        private const val KEY_ERROR_TITLE = "error"
        private const val KEY_ERROR_MESSAGE = "error_description"

        fun getNoConnectionError(context: Context): ErrorModel {
            return ErrorModel(
                context.getString(R.string.default_no_networks_error_title),
                context.getString(R.string.default_no_networks_error_message)
            )
        }

        fun getTimeoutError(context: Context): ErrorModel {
            return ErrorModel(
                context.getString(R.string.default_unknown_error_title),
                context.getString(R.string.default_unknown_error_message)
            )
        }

        fun getNetworkError(context: Context): ErrorModel {
            return ErrorModel(
                context.getString(R.string.default_unknown_error_title),
                context.getString(R.string.default_unknown_error_message)
            )
        }

        fun getUnKnownError(context: Context): ErrorModel {
            return ErrorModel(
                context.getString(R.string.default_unknown_error_title),
                context.getString(R.string.default_unknown_error_message)
            )
        }


        fun newInstance(context: Context, message: String): ErrorModel {
            return ErrorModel(
                context.getString(android.R.string.dialog_alert_title),
                message
            )
        }
    }
}
