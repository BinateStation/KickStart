package com.binatestation.kickstart.network;


import com.binatestation.kickstart.network.models.ErrorModel;

import org.json.JSONObject;


/**
 * Created by RKR on 03-04-2017.
 * JSONObjectResponseListener
 */

public interface JSONObjectResponseListener {
    /**
     * Response callback after successful response from server.
     *
     * @param response  the result object from the response
     * @param requestId the request id
     */
    void onResponse(JSONObject response, long requestId);

    /**
     * Response callback while error occur during network call
     *
     * @param error     the Error model
     * @param requestId the request id
     */
    void onErrorResponse(ErrorModel error, long requestId);
}
