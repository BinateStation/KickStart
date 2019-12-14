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

package com.binatestation.kickstart.repository.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by RKR on 7/28/2017.
 * UserModel
 */

@Entity(tableName = "User")
data class UserModel(
    @PrimaryKey
    var id: Long? = null,
    var email: String? = null,
    var role: String? = null,
    var password: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readLong()) {
        email = parcel.readString()
        role = parcel.readString()
        password = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id ?: 0)
        parcel.writeString(email)
        parcel.writeString(role)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }


}
