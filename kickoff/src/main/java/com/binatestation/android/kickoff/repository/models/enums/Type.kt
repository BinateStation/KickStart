/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.repository.models.enums

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
            for (status in values()) {
                if (status.toString().equals(stringValue, ignoreCase = true)) {
                    return status
                }
            }
            return NONE
        }

        fun fromValue(value: Int): Type {
            for (status in values()) {
                if (status.value == value) {
                    return status
                }
            }
            return NONE
        }
    }
}
