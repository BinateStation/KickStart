package com.binatestation.kickstart.models.enums;

/**
 * Created by RKR on 29-09-2017.
 * Status
 */

public enum Type {
    NONE(0),
    TEST(1),
    SELF(2),
    DEPENDANT(3),
    HOME(4);

    private int mValue;

    Type(int value) {

        mValue = value;
    }

    public static Type fromString(String stringValue) {
        for (Type status : Type.values()) {
            if (status.toString().equalsIgnoreCase(stringValue)) {
                return status;
            }
        }
        return NONE;
    }

    public static Type fromValue(int value) {
        for (Type status : Type.values()) {
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
