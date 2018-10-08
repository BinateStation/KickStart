package com.binatestation.kickstart.models.enums;

/**
 * Created by RKR on 29-09-2017.
 * Status
 */

public enum Status {
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
    FAILED(13);

    private int mValue;

    Status(int value) {

        mValue = value;
    }

    public static Status fromString(String stringValue) {
        for (Status status : Status.values()) {
            if (status.toString().equalsIgnoreCase(stringValue)) {
                return status;
            }
        }
        return NONE;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return NONE;
    }

    public int getValue() {
        return mValue;
    }
}
