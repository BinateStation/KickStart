package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_BODY;
import static com.binatestation.kickstart.utils.Constants.KEY_TITLE;
import static com.binatestation.kickstart.utils.Constants.KEY_USER_ID;

public class PostModel extends BaseModel implements Parcelable {
    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };
    private long userId;
    private String body;

    public PostModel() {
    }

    public PostModel(long id, String name, long userId, String body) {
        super(id, name);
        this.userId = userId;
        this.body = body;
    }

    public PostModel(@NonNull JSONObject jsonObject) {
        super(jsonObject);
        setName(jsonObject.isNull(KEY_TITLE) ? "" : jsonObject.optString(KEY_TITLE));
        setUserId(jsonObject.optLong(KEY_USER_ID));
        setBody(jsonObject.isNull(KEY_BODY) ? "" : jsonObject.optString(KEY_BODY));
    }

    protected PostModel(Parcel in) {
        super(in);
        userId = in.readLong();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(userId);
        dest.writeString(body);
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
