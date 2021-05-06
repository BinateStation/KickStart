/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

@file:Suppress("unused")

package com.binatestation.android.kickoff.repository.models.enums

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
    DELIVERED(15),
    SUCCESS(16),
    ERROR(17),
    LOADING(18),
    DELETED(19),
    RUNNING(20),
    NO_DATA(21),
    NO_INTERNET(22);


    companion object {

        fun fromString(stringValue: String): Status {
            for (status in values()) {
                if (status.toString().equals(stringValue, ignoreCase = true)) {
                    return status
                }
            }
            return NONE
        }

        fun fromValue(value: Int): Status {
            for (status in values()) {
                if (status.value == value) {
                    return status
                }
            }
            return NONE
        }
    }
}
