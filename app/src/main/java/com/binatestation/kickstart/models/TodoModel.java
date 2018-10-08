package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_COMPLETED;
import static com.binatestation.kickstart.utils.Constants.KEY_TITLE;
import static com.binatestation.kickstart.utils.Constants.KEY_USER_ID;

public class TodoModel extends BaseModel implements Parcelable {
    public static final Creator<TodoModel> CREATOR = new Creator<TodoModel>() {
        @Override
        public TodoModel createFromParcel(Parcel in) {
            return new TodoModel(in);
        }

        @Override
        public TodoModel[] newArray(int size) {
            return new TodoModel[size];
        }
    };
    private long userId;
    private boolean completed;

    public TodoModel() {
    }

    public TodoModel(long id, String name, long userId, boolean completed) {
        super(id, name);
        this.userId = userId;
        this.completed = completed;
    }

    public TodoModel(@NonNull JSONObject jsonObject) {
        super(jsonObject);
        setName(jsonObject.isNull(KEY_TITLE) ? "" : jsonObject.optString(KEY_TITLE));
        setUserId(jsonObject.optLong(KEY_USER_ID));
        setCompleted(jsonObject.optBoolean(KEY_COMPLETED));
    }

    protected TodoModel(Parcel in) {
        super(in);
        userId = in.readLong();
        completed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(userId);
        dest.writeByte((byte) (completed ? 1 : 0));
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
