package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_CITY;
import static com.binatestation.kickstart.utils.Constants.KEY_GEO;
import static com.binatestation.kickstart.utils.Constants.KEY_STREET;
import static com.binatestation.kickstart.utils.Constants.KEY_SUITE;
import static com.binatestation.kickstart.utils.Constants.KEY_ZIPCODE;

public class Address implements Parcelable {
    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

    public Address() {
    }

    public Address(String street, String suite, String city, String zipcode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }


    public Address(@NonNull JSONObject jsonObject) {
        setStreet(jsonObject.isNull(KEY_STREET) ? "" : jsonObject.optString(KEY_STREET));
        setSuite(jsonObject.isNull(KEY_SUITE) ? "" : jsonObject.optString(KEY_SUITE));
        setCity(jsonObject.isNull(KEY_CITY) ? "" : jsonObject.optString(KEY_CITY));
        setZipcode(jsonObject.isNull(KEY_ZIPCODE) ? "" : jsonObject.optString(KEY_ZIPCODE));
        JSONObject geoJsonObject = jsonObject.optJSONObject(KEY_GEO);
        if (geoJsonObject != null) {
            setGeo(new Geo(geoJsonObject));
        }
    }

    protected Address(Parcel in) {
        street = in.readString();
        suite = in.readString();
        city = in.readString();
        zipcode = in.readString();
        geo = in.readParcelable(Geo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(suite);
        dest.writeString(city);
        dest.writeString(zipcode);
        dest.writeParcelable(geo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return this.suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return this.geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
