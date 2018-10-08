package com.binatestation.kickstart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.binatestation.kickstart.activities.SplashActivity;
import com.binatestation.kickstart.models.UserModel;

import static com.binatestation.kickstart.utils.Constants.KEY_EMAIL;
import static com.binatestation.kickstart.utils.Constants.KEY_NAME;
import static com.binatestation.kickstart.utils.Constants.KEY_PHONE;
import static com.binatestation.kickstart.utils.Constants.KEY_ZIPCODE;

/**
 * Created by RKR on 14-08-2018.
 * Session.
 */
public class Session {
    private static final String KEY_SESSION = "SESSION";


    public static void setUserId(@NonNull Context context, long userId) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit()
                .putLong(KEY_ZIPCODE, userId).apply();
    }

    public static long getUserId(@NonNull Context context) {
        return context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE)
                .getLong(KEY_ZIPCODE, 0);
    }

    public static String getUserEmail(@NonNull Context context) {
        return context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE)
                .getString(KEY_EMAIL, "");
    }

    public static void setUser(@NonNull Context context, UserModel userModel) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit()
                .putLong(KEY_ZIPCODE, userModel.getId())
                .putString(KEY_NAME, userModel.getName())
                .putString(KEY_EMAIL, userModel.getEmail())
                .putString(KEY_PHONE, userModel.getPhone())
                .apply();
    }

    public static UserModel getUser(@NonNull Context context) {
        UserModel userModel = new UserModel();
        userModel.setId(context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getLong(KEY_ZIPCODE, 0));
        userModel.setName(context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(KEY_NAME, ""));
        userModel.setEmail(context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(KEY_EMAIL, ""));
        userModel.setPhone(context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(KEY_PHONE, ""));
        return userModel;
    }

    public static void logout(@NonNull Activity activity) {
        activity.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit().clear().apply();
        activity.finishAndRemoveTask();
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }


}
