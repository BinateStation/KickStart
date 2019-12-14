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
import androidx.databinding.ObservableField
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Account")
data class AccountModel(
    @SerializedName("id")
    @PrimaryKey
    var id: Long = 0,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("code")
    var code: String? = null,
    @SerializedName("parent_id")
    var parentId: Long? = null,
    @SerializedName("children")
    @Ignore
    var children: ArrayList<AccountModel>? = null,
    @Ignore
    override var isSelected: Boolean = false
) : Parcelable, MultiSelectDataModel {

    @Ignore
    var expanded = ObservableField(false)
    @Ignore
    @Expose(serialize = false, deserialize = false)
    var nameError = ObservableField("")
    @Ignore
    @Expose(serialize = false, deserialize = false)
    var codeError = ObservableField("")

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.createTypedArrayList(AccountModel),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(code)
        parcel.writeValue(parentId)
        parcel.writeTypedList(children)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$name  ${code ?: ""}"
    }

    companion object CREATOR : Parcelable.Creator<AccountModel> {
        override fun createFromParcel(parcel: Parcel): AccountModel {
            return AccountModel(parcel)
        }

        override fun newArray(size: Int): Array<AccountModel?> {
            return arrayOfNulls(size)
        }

        fun transformAccount(
            accountModel: AccountModel,
            baseArray: List<AccountModel>
        ): AccountModel {
            val children = baseArray.filter { it.parentId == accountModel.id }
            return if (children.isNullOrEmpty()) {
                accountModel
            } else {
                children.map { data -> transformAccount(data, baseArray) }
                accountModel.children = children as ArrayList<AccountModel>
                accountModel
            }
        }
    }

}