package com.binatestation.kickstart.network;


import com.binatestation.kickstart.network.models.ErrorModel;

import org.json.JSONArray;


/**
 * Created by RKR on 21-04-2017.
 * JSONArrayResponseListener
 */

public interface JSONArrayResponseListener {
    /**
     * Response callback after successful response from server.
     *
     * @param response  the result array from the response
     * @param requestId the request id
     */
    void onResponse(JSONArray response, long requestId);

    /**
     * Response callback while error occur during network call
     *
     * @param error     the Error model
     * @param requestId the request id
     */
    void onErrorResponse(ErrorModel error, long requestId);
}
