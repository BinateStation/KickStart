/*
 * Created By RKR
 * Last Updated at 14/12/19 5:48 PM.
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

enum class Status(val value: Int) {
    NONE(0),
    PENDING(1),
    ACTIVE(2),
    PAID(3),
    SEND(4),
    RECEIVED(5),
    READ(6),
    CANCELLED(7),
    APPROVED(8),
    CONFIRMED(9),
    RESCHEDULED(10),
    CONSULTED(11),
    COMPLETED(12),
    FAILED(13),
    ONGOING(14),
    DELIVERD(15),
    SUCCESS(16),
    ERROR(17),
    LOADING(18),
    DELETED(19);


    companion object {

        fun fromString(stringValue: String): Status {
            for (status in Status.values()) {
                if (status.toString().equals(stringValue, ignoreCase = true)) {
                    return status
                }
            }
            return NONE
        }

        fun fromValue(value: Int): Status {
            for (status in Status.values()) {
                if (status.value == value) {
                    return status
                }
            }
            return NONE
        }
    }
}
