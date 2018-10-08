package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_ALBUM_ID;
import static com.binatestation.kickstart.utils.Constants.KEY_THUMBNAIL_URL;
import static com.binatestation.kickstart.utils.Constants.KEY_TITLE;
import static com.binatestation.kickstart.utils.Constants.KEY_URL;

public class PhotoModel extends BaseModel implements Parcelable {
    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel in) {
            return new PhotoModel(in);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };
    private long albumId;
    private String url;
    private String thumbnailUrl;

    public PhotoModel() {
    }

    public PhotoModel(long id, String name, long albumId, String url, String thumbnailUrl) {
        super(id, name);
        this.albumId = albumId;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }


    public PhotoModel(@NonNull JSONObject jsonObject) {
        super(jsonObject);
        setName(jsonObject.isNull(KEY_TITLE) ? "" : jsonObject.optString(KEY_TITLE));
        setAlbumId(jsonObject.optLong(KEY_ALBUM_ID));
        setUrl(jsonObject.isNull(KEY_URL) ? "" : jsonObject.optString(KEY_URL));
        setThumbnailUrl(jsonObject.isNull(KEY_THUMBNAIL_URL) ? "" : jsonObject.optString(KEY_THUMBNAIL_URL));
    }

    private PhotoModel(Parcel in) {
        super(in);
        albumId = in.readLong();
        url = in.readString();
        thumbnailUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(albumId);
        dest.writeString(url);
        dest.writeString(thumbnailUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
