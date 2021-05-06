/*
 * (c) Binate Station Private Limited. All rights reserved.
 */

package com.binatestation.android.kickoff.utils


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import com.binatestation.android.kickoff.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToLong

@Suppress("unused")
/**
 * Created by RKR on 27-01-2016.
 * Utils.
 */
object Utils {

    private const val TAG = "Utils"

    /**
     * gets RequestOptions for setting profile image
     *
     * @return RequestOptions
     */
    private val profileImageRequestOption: RequestOptions
        get() = RequestOptions.errorOf(R.drawable.ic_person_grey_24dp)
            .placeholder(R.drawable.ic_person_grey_24dp)

    /**
     * gets RequestOptions for setting profile image
     *
     * @return RequestOptions
     */
    private val requestOption: RequestOptions
        get() = RequestOptions.errorOf(R.drawable.ic_image_gery_24dp)
            .placeholder(R.drawable.ic_image_gery_24dp)

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param imageUrl  String
     */
    fun setImage(imageView: ImageView?, imageUrl: String) {
        imageView?.let {
            Glide.with(it)
                .applyDefaultRequestOptions(requestOption)
                .load(imageUrl)
                .into(it)
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param bitmap    bitmap image to load
     */
    fun setImage(imageView: ImageView?, bitmap: Bitmap) {
        imageView?.let {
            Glide.with(it)
                .applyDefaultRequestOptions(requestOption)
                .load(bitmap)
                .into(it)
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param imageUrl  String
     */
    fun setCircleProfileImage(imageView: ImageView?, imageUrl: String) {
        imageView?.let {
            Glide.with(it)
                .setDefaultRequestOptions(profileImageRequestOption)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(it)
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param imageUri  String
     */
    fun setCircleProfileImage(imageView: ImageView?, imageUri: Uri) {
        imageView?.let {
            Glide.with(it)
                .setDefaultRequestOptions(profileImageRequestOption)
                .load(imageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(it)
        }
    }

    /**
     * sets the Profile Image
     *
     * @param imageView ImageView
     * @param imageUrl  String value
     */
    fun setProfileImage(imageView: ImageView?, imageUrl: String?) {
        imageView?.let {
            Glide.with(it)
                .setDefaultRequestOptions(profileImageRequestOption)
                .load(imageUrl)
                .into(it)
        }
    }

    /**
     * sets Circle Profile Image
     *
     * @param imageView ImageView
     * @param bitmap    Bitmap
     */
    fun setCircleProfileImage(imageView: ImageView?, bitmap: Bitmap) {
        imageView?.let {
            Glide.with(it)
                .setDefaultRequestOptions(profileImageRequestOption)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(it)
        }
    }

    fun isInvalidEmail(target: CharSequence?): Boolean {
        val emailPattern =
            Pattern.compile("^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(.\\w{2,3})+$")
        return target == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(target)
            .matches() || !emailPattern.matcher(
            target
        ).matches()
    }

    fun isInvalidPhone(target: CharSequence?): Boolean {
        return target == null || !android.util.Patterns.PHONE.matcher(target).matches()
    }

    fun getUnixTimeStamp(timeInMillis: Long): Long {
        return timeInMillis / 1000

    }

    fun getTimeInMillis(unixTimeStamp: Long): Long {
        return unixTimeStamp * 1000
    }

    /**
     * method to parse JSONArray in to POJOs
     *
     * @param jsonArray the non null jason array
     * @param cls       the POJO class type
     * @param <T>       type
     * @return the ArrayList of POJO class
    </T> */
    fun <T> parseJsonArray(jsonArray: JSONArray, cls: Class<T>): ArrayList<T> {
        val clsArrayList = ArrayList<T>()
        try {
            val constructor = cls.getConstructor(JSONObject::class.java)
            try {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.optJSONObject(i)
                    if (jsonObject != null) {
                        clsArrayList.add(constructor.newInstance(jsonObject))
                    }
                }
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        return clsArrayList
    }

    fun parseDate(date: String): Long {
        if (TextUtils.isEmpty(date)) {
            return 0
        }
        return try {
            val splits =
                date.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)".toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()
            if (splits.size > 2) {
                java.lang.Long.parseLong(splits[1])
            } else 0
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }

    }


    @JvmStatic
    fun displayDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date(date))
    }

    fun serverDate(date: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date(date))
    }

    fun serverDateToMillis(date: String?): Long {
        date?.let {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return try {
                dateFormat.parse(it)?.time ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        } ?: return 0
    }

    fun expenseDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date(date))
    }

    private fun base64(bitmap: Bitmap): String {
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: OutOfMemoryError) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: Exception) {
            ""
        }

    }

    fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun getFileExtension(url: String): String {
        return MimeTypeMap.getFileExtensionFromUrl(url)
    }

    fun getPhotoForServer(bitmap: Bitmap): String {
        return "data:image/png;base64," + base64(bitmap)
    }

    fun getFileName(path: String): String {
        return try {
            path.substring(path.lastIndexOf("\\") + 1)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    }

    fun localeToEmoji(locale: Locale): String {
        val countryCode = locale.country
        return try {
            val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
            String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    }

    private fun getLocalePosition(locales: ArrayList<Locale>, locale: Locale): Int {
        Log.d(TAG, "getPosition() called with: locales = [$locales], locale = [$locale]")
        for (i in locales.indices) {
            val loc = locales[i]
            if (loc.country.equals(locale.country, ignoreCase = true)) {
                return i
            }
        }
        return -1
    }

    fun loadJSONFromAsset(context: Context): String? {
        val json: String
        try {
            val `is` = context.assets.open("phone.json")
            val size = `is`.available()
            val buffer = ByteArray(size)

            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    fun ensureDouble(value: String): Double {
        if (TextUtils.isEmpty(value)) {
            return 0.0
        }
        return try {
            val qty = java.lang.Double.parseDouble(value)
            if (qty < java.lang.Double.MAX_VALUE) {
                qty
            } else {
                java.lang.Double.MAX_VALUE - 1
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }

    }

    fun ensureInteger(value: String): Int {
        if (TextUtils.isEmpty(value)) {
            return 0
        }
        return try {
            val qty = java.lang.Long.parseLong(value)
            if (qty < Integer.MAX_VALUE) {
                qty.toInt()
            } else {
                Integer.MAX_VALUE - 1
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }

    }


    fun getPercentage(value: Double, percentage: Double): Double {
        return roundOff(value * percentage / 100)
    }

    fun getExcludedAmount(value: Double, percentage: Double): Double {
        return roundOff(value * 100 / (100 + percentage))
    }

    private fun roundOff(value: Double): Double {
        return (value * 100).roundToLong() / 100.0
    }

}
