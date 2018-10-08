package com.binatestation.kickstart.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.binatestation.kickstart.BuildConfig;
import com.binatestation.kickstart.network.models.ErrorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RKR on 22-07-2018.
 * JsonObjectRequestFactory.
 */
@SuppressWarnings("unused")
public class JsonArrayRequestFactory implements Response.Listener<JSONArray>, Response.ErrorListener {

    private static final String TAG = "JsonArrayRequestFactory";

    private int mRequestMethod = -2;
    private String mMethodName;
    private long mRequestId = 1;
    private JSONObject mParams = new JSONObject();
    private String mGetParams = "";
    private Context mContext;
    private JSONObjectResponseListener mJsonObjectResponseListener;
    private JSONArrayResponseListener mJsonArrayResponseListener;
    private StringResponseListener mStringResponseListener;

    /**
     * Gets new instance for JSONObject request factory.
     *
     * @param methodName the method name of API
     * @return a new object for JsonObjectRequestFactory;
     */
    public static JsonArrayRequestFactory newInstance(String methodName) {
        JsonArrayRequestFactory requestFactory = new JsonArrayRequestFactory();
        requestFactory.setMethodName(methodName);

        return requestFactory;
    }

    /**
     * gets the connection url
     *
     * @return the url where to post the request
     */
    private static String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @SuppressWarnings("unused")
    public void setRequestMethod(int requestMethod) {
        this.mRequestMethod = requestMethod;
    }

    /**
     * Creates StringRequest using the details added in this StringRequestFactory object.
     *
     * @return new object to StringRequest;
     */
    private JsonArrayRequest create() {
        if (mRequestMethod == -2) {
            if (mParams.length() > 0) {
                mRequestMethod = Request.Method.POST;
            } else {
                mRequestMethod = Request.Method.GET;
            }
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                mRequestMethod,
                getBaseUrl() + getMethodName() + mGetParams,
                null,
                this,
                this
        ) {
            @Override
            public byte[] getBody() {
                if (mRequestMethod == Method.POST && getInputParam() != null) {
                    return getInputParam().toString().getBytes();
                }
                return super.getBody();
            }
        };
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "---------------------------------Request---------------------------------------");
            Log.d(TAG, "create: Url :- " + jsonArrayRequest.getUrl());
            Log.d(TAG, "create: Params :- " + mParams);
            Log.d(TAG, "---------------------------------Request End---------------------------------------");
        }
        return jsonArrayRequest;
    }

    private JSONObject getInputParam() {
        if (mRequestMethod == Request.Method.GET) {
            return null;
        } else {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(mParams);
            return mParams;
        }
    }

    /**
     * Method used to do Api call
     *
     * @param context this listener object for listen the result
     */
    public void call(Context context) {
        mContext = context;
        if (context instanceof JSONObjectResponseListener) {
            mJsonObjectResponseListener = (JSONObjectResponseListener) context;
        }
        if (context instanceof JSONArrayResponseListener) {
            mJsonArrayResponseListener = (JSONArrayResponseListener) context;
        }
        if (context instanceof StringResponseListener) {
            mStringResponseListener = (StringResponseListener) context;
        }
        VolleySingleton.getInstance(context).addToRequestQueue(context, create());
    }

    /**
     * Method used to do Api call
     *
     * @param fragment this listener object for listen the result
     */
    public void call(Fragment fragment) {
        mContext = fragment.getContext();
        if (fragment instanceof JSONObjectResponseListener) {
            mJsonObjectResponseListener = (JSONObjectResponseListener) fragment;
        }
        if (fragment instanceof JSONArrayResponseListener) {
            mJsonArrayResponseListener = (JSONArrayResponseListener) fragment;
        }
        if (fragment instanceof StringResponseListener) {
            mStringResponseListener = (StringResponseListener) fragment;
        }
        if (mContext != null) {
            VolleySingleton.getInstance(mContext).addToRequestQueue(mContext, create());
        }
    }


    /**
     * Gets the method name use to post
     *
     * @return the method name
     */
    private String getMethodName() {
        return mMethodName;
    }

    /**
     * Sets the method name for the current request
     *
     * @param methodName the method name
     */
    private void setMethodName(String methodName) {
        this.mMethodName = methodName;
    }

    /**
     * gets the request id to post
     *
     * @return the the request id
     */
    @SuppressWarnings("unused")
    private long getRequestId() {
        return mRequestId;
    }

    /**
     * Sets the unique request id to the request
     *
     * @param requestId the request id
     */
    public void setRequestId(long requestId) {
        this.mRequestId = requestId;
    }

    /**
     * use to add get params in to the params
     *
     * @param key   the key of parameter
     * @param value the String value of parameter
     */
    public void addGetParam(String key, String value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the String value of parameter
     */
    public void addParam(String key, String value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the float value of parameter
     */
    public void addGetParam(String key, float value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the float value of parameter
     */
    public void addParam(String key, float value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the long value of parameter
     */
    public void addGetParam(String key, long value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the long value of parameter
     */
    public void addParam(String key, long value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the int value of parameter
     */
    public void addGetParam(String key, int value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the int value of parameter
     */
    public void addParam(String key, int value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the double value of parameter
     */
    public void addGetParam(String key, double value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the double value of parameter
     */
    public void addParam(String key, double value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the boolean value of parameter
     */
    public void addGetParam(String key, boolean value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the boolean value of parameter
     */
    public void addParam(String key, boolean value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the JSONArray value of parameter
     */
    public void addGetParam(String key, @NonNull JSONArray value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the JSONArray value of parameter
     */
    public void addParam(String key, @NonNull JSONArray value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * use to add get params
     *
     * @param key   the key of parameter
     * @param value the JSONObject value of parameter
     */
    public void addGetParam(String key, @NonNull JSONObject value) {
        mGetParams += (TextUtils.isEmpty(mGetParams) ? "?" : "&") + key + "=" + value;
    }

    /**
     * use to add params in to the params json object in the root json object
     *
     * @param key   the key of parameter
     * @param value the JSONObject value of parameter
     */
    public void addParam(String key, @NonNull JSONObject value) {
        try {
            mParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "onErrorResponse: ", error);
        ErrorModel errorModel;
        if (mContext != null) {
            if (error instanceof NoConnectionError) {
                errorModel = ErrorModel.getNoConnectionError(mContext);
            } else if (error instanceof TimeoutError) {
                errorModel = ErrorModel.getTimeoutError(mContext);
            } else if (error instanceof NetworkError) {
                errorModel = ErrorModel.getNetworkError(mContext);
            } else {
                errorModel = ErrorModel.getUnKnownError(mContext);
            }
            if (mJsonArrayResponseListener != null) {
                mJsonArrayResponseListener.onErrorResponse(errorModel, mRequestId);
                return;
            }
            if (mJsonObjectResponseListener != null) {
                mJsonObjectResponseListener.onErrorResponse(errorModel, mRequestId);
                return;
            }
            if (mStringResponseListener != null) {
                mStringResponseListener.onErrorResponse(errorModel, mRequestId);
            }
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResponse: --------------------- Response--------------------------");
            Log.d(TAG, "onResponse() called with: response = [" + response + "]");
            Log.d(TAG, "onResponse: --------------------- Response End ---------------------");
        }
        if (mJsonArrayResponseListener != null) {
            if (response != null) {
                mJsonArrayResponseListener.onResponse(response, mRequestId);
                return;
            }
        }
        if (mJsonArrayResponseListener != null && mContext != null) {
            mJsonArrayResponseListener.onErrorResponse(ErrorModel.getUnKnownError(mContext), mRequestId);
            return;
        }
        if (mJsonObjectResponseListener != null && mContext != null) {
            mJsonObjectResponseListener.onErrorResponse(ErrorModel.getUnKnownError(mContext), mRequestId);
            return;
        }
        if (mStringResponseListener != null && mContext != null) {
            mStringResponseListener.onErrorResponse(ErrorModel.getUnKnownError(mContext), mRequestId);
        }
    }
}
