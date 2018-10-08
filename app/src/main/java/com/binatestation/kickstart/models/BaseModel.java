package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_ID;
import static com.binatestation.kickstart.utils.Constants.KEY_NAME;


/**
 * Created by RKR on 7/28/2017.
 * BaseModel
 */

public class BaseModel implements Parcelable {
    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
    /**
     * Id
     */
    private long id;
    /**
     * Name
     */
    private String name;

    public BaseModel() {
    }

    public BaseModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseModel(@NonNull JSONObject jsonObject) {
        this.id = jsonObject.optLong(KEY_ID);
        if (jsonObject.has(KEY_NAME)) {
            this.name = jsonObject.isNull(KEY_NAME) ? "" : jsonObject.optString(KEY_NAME);
        }
    }

    protected BaseModel(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * sets the id
     *
     * @param id long value
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name variable
     *
     * @param name String value
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseModel) {
            BaseModel baseModel = (BaseModel) obj;
            return baseModel.getId() == getId();
        }
        return super.equals(obj);
    }
}
