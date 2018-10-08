package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_TITLE;
import static com.binatestation.kickstart.utils.Constants.KEY_USER_ID;

public class AlbumModel extends BaseModel implements Parcelable {
    public static final Creator<AlbumModel> CREATOR = new Creator<AlbumModel>() {
        @Override
        public AlbumModel createFromParcel(Parcel in) {
            return new AlbumModel(in);
        }

        @Override
        public AlbumModel[] newArray(int size) {
            return new AlbumModel[size];
        }
    };
    private long userId;

    public AlbumModel() {
    }

    public AlbumModel(long id, String name, long userId) {
        super(id, name);
        this.userId = userId;
    }

    public AlbumModel(@NonNull JSONObject jsonObject) {
        super(jsonObject);
        setName(jsonObject.isNull(KEY_TITLE) ? "" : jsonObject.optString(KEY_TITLE));
        setUserId(jsonObject.optLong(KEY_USER_ID));
    }

    protected AlbumModel(Parcel in) {
        super(in);
        userId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
