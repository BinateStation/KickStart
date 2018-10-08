package com.binatestation.kickstart.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.binatestation.kickstart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;


/**
 * Created by RKR on 27-01-2016.
 * Utils.
 */
public class Utils {

    private static final String TAG = "Utils";

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param imageUrl  String
     */
    public static void setImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView)
                    .applyDefaultRequestOptions(getRequestOption())
                    .load(imageUrl)
                    .into(imageView);
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param bitmap    bitmap image to load
     */
    public static void setImage(ImageView imageView, @NonNull Bitmap bitmap) {
        if (imageView != null) {
            Glide.with(imageView)
                    .applyDefaultRequestOptions(getRequestOption())
                    .load(bitmap)
                    .into(imageView);
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param imageUrl  String
     */
    public static void setCircleProfileImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView)
                    .setDefaultRequestOptions(getProfileImageRequestOption())
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param imageUri  String
     */
    public static void setCircleProfileImage(ImageView imageView, Uri imageUri) {
        if (imageView != null) {
            Glide.with(imageView)
                    .setDefaultRequestOptions(getProfileImageRequestOption())
                    .load(imageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        }
    }

    /**
     * sets the Profile Image
     *
     * @param imageView ImageView
     * @param imageUrl  String value
     */
    public static void setProfileImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView)
                    .setDefaultRequestOptions(getProfileImageRequestOption())
                    .load(imageUrl)
                    .into(imageView);
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param bitmap    Bitmap
     */
    public static void setCircleProfileImage(ImageView imageView, @NonNull Bitmap bitmap) {
        if (imageView != null) {
            Glide.with(imageView)
                    .setDefaultRequestOptions(getProfileImageRequestOption())
                    .load(bitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        }
    }

    /**
     * gets RequestOptions for setting profile image
     *
     * @return RequestOptions
     */
    private static RequestOptions getProfileImageRequestOption() {
        return RequestOptions.errorOf(R.drawable.ic_person_grey_24dp).placeholder(R.drawable.ic_person_grey_24dp);
    }

    /**
     * gets RequestOptions for setting profile image
     *
     * @return RequestOptions
     */
    private static RequestOptions getRequestOption() {
        return RequestOptions.errorOf(R.drawable.ic_image_gery_24dp).placeholder(R.drawable.ic_image_gery_24dp);
    }

    public static boolean isInvalidEmail(CharSequence target) {
        return target == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @SuppressWarnings("unused")
    public static long getUnixTimeStamp(long timeInMillis) {
        return timeInMillis / 1000;

    }

    @SuppressWarnings("unused")
    public static long getTimeInMillis(long unixTimeStamp) {
        return unixTimeStamp * 1000;
    }

    /**
     * method to parse JSONArray in to POJOs
     *
     * @param jsonArray the non null jason array
     * @param cls       the POJO class type
     * @param <T>       type
     * @return the ArrayList of POJO class
     */
    public static <T> ArrayList<T> parseJsonArray(@NonNull JSONArray jsonArray, Class<T> cls) {
        ArrayList<T> clsArrayList = new ArrayList<>();
        try {
            Constructor<T> constructor = cls.getConstructor(JSONObject.class);
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    if (jsonObject != null) {
                        clsArrayList.add(constructor.newInstance(jsonObject));
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return clsArrayList;
    }

    public static String getIpConfig() {
        StringBuilder ipConfig = new StringBuilder();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        ipConfig.append(inetAddress.getHostAddress()).append("\n");
                    }

                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, "getIpConfig: ", ex);
        }
        return ipConfig.toString();
    }

    public static long parseDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        try {
            String[] splits = date.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            if (splits.length > 2) {
                return Long.parseLong(splits[1]);
            }
            return 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String displayDate(Context context, long date) {
        return DateUtils.formatDateTime(
                context,
                date,
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR
        );
    }

    public static String serverDate(long date) {
        return "/Date(" + date + ")/";
    }

    private static String base64(@NonNull Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (OutOfMemoryError e) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressWarnings("unused")
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @SuppressWarnings("unused")
    public static String getFileExtension(String url) {
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    public static String getPhotoForServer(@NonNull Bitmap bitmap) {
        return "data:image/png;base64," + base64(bitmap);
    }

    public static String getFileName(String path) {
        try {
            return path.substring(path.lastIndexOf("\\") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressWarnings("unused")
    public static String localeToEmoji(@NonNull Locale locale) {
        String countryCode = locale.getCountry();
        try {
            int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
            int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
            return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressWarnings("unused")
    public static ArrayList<Locale> getLocaleList() {
        ArrayList<Locale> locales = new ArrayList<>();
        for (String country : Locale.getISOCountries()) {
            if (!TextUtils.isEmpty(country) && !"IL".equalsIgnoreCase(country)) {
                locales.add(new Locale("", country));
            }
        }
        Collections.sort(locales, (o1, o2) -> o1.getDisplayCountry().compareToIgnoreCase(o2.getDisplayCountry()));
        int position = getLocalePosition(locales, new Locale("ar", "AE"));
        if (position >= 0) {
            Locale locale = locales.remove(position);
            locales.add(0, locale);
        }
        return locales;
    }

    private static int getLocalePosition(ArrayList<Locale> locales, Locale locale) {
        Log.d(TAG, "getPosition() called with: locales = [" + locales + "], locale = [" + locale + "]");
        for (int i = 0; i < locales.size(); i++) {
            Locale loc = locales.get(i);
            if (loc.getCountry().equalsIgnoreCase(locale.getCountry())) {
                return i;
            }
        }
        return -1;
    }

    public static String loadJSONFromAsset(@NonNull Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("phone.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
