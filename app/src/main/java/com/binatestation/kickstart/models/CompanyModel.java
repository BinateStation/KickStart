package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_BS;
import static com.binatestation.kickstart.utils.Constants.KEY_CATCH_PHRASE;
import static com.binatestation.kickstart.utils.Constants.KEY_NAME;

public class CompanyModel implements Parcelable {
    public static final Creator<CompanyModel> CREATOR = new Creator<CompanyModel>() {
        @Override
        public CompanyModel createFromParcel(Parcel in) {
            return new CompanyModel(in);
        }

        @Override
        public CompanyModel[] newArray(int size) {
            return new CompanyModel[size];
        }
    };
    private String name;
    private String catchPhrase;
    private String bs;

    public CompanyModel() {
    }

    public CompanyModel(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    public CompanyModel(@NonNull JSONObject jsonObject) {
        setName(jsonObject.isNull(KEY_NAME) ? "" : jsonObject.optString(KEY_NAME));
        setCatchPhrase(jsonObject.isNull(KEY_CATCH_PHRASE) ? "" : jsonObject.optString(KEY_CATCH_PHRASE));
        setBs(jsonObject.isNull(KEY_BS) ? "" : jsonObject.optString(KEY_BS));
    }

    protected CompanyModel(Parcel in) {
        name = in.readString();
        catchPhrase = in.readString();
        bs = in.readString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return this.catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return this.bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(catchPhrase);
        dest.writeString(bs);
    }
}
