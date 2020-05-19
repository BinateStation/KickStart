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
 * Last Updated at 19/5/20 6:17 PM.
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package com.binatestation.android.kickoff.repository.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.binatestation.android.kickoff.R
import java.util.*


/**
 * Created by RKR on 21-09-2017.
 * EmptyStateModel
 */

data class EmptyStateModel(
    var id: Long = 0,
    var title: String? = null,
    var message: String? = null,
    @DrawableRes
    var icon: Int = 0
) : Parcelable {


    var tag: Any? = null

    constructor(`in`: Parcel) : this(
        `in`.readLong(),
        `in`.readString(),
        `in`.readString(),
        `in`.readInt()
    )

    override fun equals(other: Any?): Boolean {
        if (other is EmptyStateModel) {
            val emptyStateModel = other as EmptyStateModel?
            return id == emptyStateModel?.id
        }
        return super.equals(other)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(title)
        dest.writeInt(icon)
        dest.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + icon
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }

    companion object CREATOR : Parcelable.Creator<EmptyStateModel> {
        override fun createFromParcel(parcel: Parcel): EmptyStateModel {
            return EmptyStateModel(
                parcel
            )
        }

        override fun newArray(size: Int): Array<EmptyStateModel?> {
            return arrayOfNulls(size)
        }


        @Suppress("unused")
                /**
                 * gets the EmptyStateModel
                 *
                 * @param errorModel ErrorModel
                 * @return EmptyStateModel
                 */
        fun getEmptyModel(errorModel: ErrorModel): EmptyStateModel {
            return EmptyStateModel(
                -1,
                errorModel.errorTitle ?: "",
                errorModel.errorMessage,
                R.drawable.ic_hourglass_empty_black_24dp
            )
        }

        /**
         * gets the EmptyStateModel
         *
         * @return EmptyStateModel
         */
        private val emptyDataModel: EmptyStateModel
            get() = EmptyStateModel(
                -1,
                "No Data",
                "No data available for you yet, Please tune after some times..!",
                R.drawable.ic_no_data
            )

        /**
         * gets the EmptyStateModel
         *
         * @return EmptyStateModel
         */
        private val loadingDataModel: EmptyStateModel
            get() = EmptyStateModel(
                -1,
                "",
                "Loading......",
                R.drawable.avd_progress
            )

        /**
         * gets the list of EmptyStateModels
         *
         * @return list of EmptyStateModels
         */
        val emptyDataModels: ArrayList<Any>
            get() = getEmptyDataModels(
                emptyDataModel
            )

        /**

         * gets the list of EmptyStateModels
         *
         * @return list of EmptyStateModels
         */
        val loadingDataModels: ArrayList<Any>
            get() = getEmptyDataModels(
                loadingDataModel
            )

        /**
         * gets the list of EmptyStateModels
         *
         * @param emptyStateModel EmptyStateModel
         * @return list of EmptyStateModels
         */
        fun getEmptyDataModels(emptyStateModel: EmptyStateModel): ArrayList<Any> {
            return arrayListOf(emptyStateModel)
        }

        @Suppress("unused")
                /**
                 * gets EmptyStateModel
                 *
                 * @return EmptyStateModel
                 */
        val noInternetEmptyModel: EmptyStateModel
            get() = EmptyStateModel(
                -1,
                "No Internet",
                "Sorry you are not connected to internet, Please check your connection and try again!",
                R.drawable.ic_signal_wifi_off_black_24dp
            )


        /**
         * gets EmptyStateModel
         *
         * @return EmptyStateModel
         */
        val unKnownEmptyModel: EmptyStateModel
            get() = EmptyStateModel(
                -1,
                "Alert",
                "Something went wrong, Please try again later !",
                R.drawable.ic_hourglass_empty_black_24dp
            )
    }
}
