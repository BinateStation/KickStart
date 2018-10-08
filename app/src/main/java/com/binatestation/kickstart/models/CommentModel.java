package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_BODY;
import static com.binatestation.kickstart.utils.Constants.KEY_EMAIL;
import static com.binatestation.kickstart.utils.Constants.KEY_POST_ID;

public class CommentModel extends BaseModel implements Parcelable {
    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };
    private long postId;
    private String email;
    private String body;

    public CommentModel() {
    }

    public CommentModel(long id, String name, long postId, String email, String body) {
        super(id, name);
        this.postId = postId;
        this.email = email;
        this.body = body;
    }

    public CommentModel(@NonNull JSONObject jsonObject) {
        super(jsonObject);
        setPostId(jsonObject.optLong(KEY_POST_ID));
        setEmail(jsonObject.isNull(KEY_EMAIL) ? "" : jsonObject.optString(KEY_EMAIL));
        setBody(jsonObject.isNull(KEY_BODY) ? "" : jsonObject.optString(KEY_BODY));
    }

    protected CommentModel(Parcel in) {
        super(in);
        postId = in.readLong();
        email = in.readString();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(postId);
        dest.writeString(email);
        dest.writeString(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
