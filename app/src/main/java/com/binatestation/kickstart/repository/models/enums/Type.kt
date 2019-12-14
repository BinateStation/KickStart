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

package com.binatestation.kickstart.repository.models.enums

/**
 * Created by RKR on 29-09-2017.
 * Status
 */

enum class Type(val value: Int) {
    NONE(0),
    CREATION(1),
    INSERTION(2),
    DEPENDANT(3),
    HOME(4),
    SELECT(5),
    SALE_SELECT(6);


    companion object {

        fun fromString(stringValue: String): Type {
            for (status in Type.values()) {
                if (status.toString().equals(stringValue, ignoreCase = true)) {
                    return status
                }
            }
            return NONE
        }

        fun fromValue(value: Int): Type {
            for (status in Type.values()) {
                if (status.value == value) {
                    return status
                }
            }
            return NONE
        }
    }
}
