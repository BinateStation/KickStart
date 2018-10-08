package com.binatestation.kickstart.models;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_ADDRESS;
import static com.binatestation.kickstart.utils.Constants.KEY_COMPANY;
import static com.binatestation.kickstart.utils.Constants.KEY_EMAIL;
import static com.binatestation.kickstart.utils.Constants.KEY_PHONE;
import static com.binatestation.kickstart.utils.Constants.KEY_USERNAME;
import static com.binatestation.kickstart.utils.Constants.KEY_WEBSITE;


/**
 * Created by RKR on 7/28/2017.
 * UserModel
 */

public class UserModel extends BaseModel implements Parcelable {

    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private CompanyModel company;

    public UserModel() {
    }

    public UserModel(long id, String name, String username, String email, Address address, String phone, String website, CompanyModel company) {
        super(id, name);
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public UserModel(@NonNull JSONObject jsonObject) {
        super(jsonObject);
        setUsername(jsonObject.isNull(KEY_USERNAME) ? "" : jsonObject.optString(KEY_USERNAME));
        setEmail(jsonObject.isNull(KEY_EMAIL) ? "" : jsonObject.optString(KEY_EMAIL));
        JSONObject addressJsonObject = jsonObject.optJSONObject(KEY_ADDRESS);
        if (addressJsonObject != null) {
            setAddress(new Address(addressJsonObject));
        }
        setPhone(jsonObject.isNull(KEY_PHONE) ? "" : jsonObject.optString(KEY_PHONE));
        setWebsite(jsonObject.isNull(KEY_WEBSITE) ? "" : jsonObject.optString(KEY_WEBSITE));
        JSONObject companyJsonObject = jsonObject.optJSONObject(KEY_COMPANY);
        if (companyJsonObject != null) {
            setCompany(new CompanyModel(companyJsonObject));
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public CompanyModel getCompany() {
        return this.company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

}
