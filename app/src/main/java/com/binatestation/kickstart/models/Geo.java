package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_LAT;
import static com.binatestation.kickstart.utils.Constants.KEY_LNG;

public class Geo implements Parcelable {
    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };
    private double lat;
    private double lng;

    public Geo() {
    }

    public Geo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Geo(@NonNull JSONObject jsonObject) {
        setLat(jsonObject.optDouble(KEY_LAT));
        setLng(jsonObject.optDouble(KEY_LNG));
    }

    protected Geo(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
