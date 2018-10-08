package com.binatestation.kickstart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.binatestation.kickstart.R;
import com.binatestation.kickstart.activities.DetailsActivity;
import com.binatestation.kickstart.models.PostModel;
import com.binatestation.kickstart.models.TodoModel;
import com.binatestation.kickstart.network.JSONArrayResponseListener;
import com.binatestation.kickstart.network.JsonArrayRequestFactory;
import com.binatestation.kickstart.network.models.ErrorModel;
import com.binatestation.kickstart.utils.Utils;

import org.json.JSONArray;

import static com.binatestation.kickstart.utils.Constants.END_URL_TODOS;
import static com.binatestation.kickstart.utils.Constants.KEY_DATA_MODEL;

public class TodoListFragment extends SwipeListFragment implements JSONArrayResponseListener {
    public static final String TAG = "PhotoListFragment";

    public static TodoListFragment newInstance() {

        Bundle args = new Bundle();

        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
        Log.d(TAG, "getData() called");
        JsonArrayRequestFactory requestFactory = JsonArrayRequestFactory.newInstance(END_URL_TODOS);
        requestFactory.setRequestMethod(Request.Method.GET);

        requestFactory.call(this);
    }

    @Override
    public void onClickItem(Object object, int position, View actionView) {
        if (object instanceof PostModel) {
            PostModel postModel = (PostModel) object;
            navigateToPostDetails(postModel);
        }
    }

    private void navigateToPostDetails(PostModel postModel) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.setAction(getString(R.string.post_details));
        intent.putExtra(KEY_DATA_MODEL, postModel);
        startActivity(intent);
    }

    @Override
    public void onResponse(JSONArray response, long requestId) {
        Log.d(TAG, "onResponse() called with: response = [" + response + "], requestId = [" + requestId + "]");
        hideProgress(getSwipeRefreshLayout());
        getAdapter().setTypedData(Utils.parseJsonArray(response, TodoModel.class));
    }

    @Override
    public void onErrorResponse(ErrorModel error, long requestId) {
        Log.d(TAG, "onErrorResponse() called with: error = [" + error + "], requestId = [" + requestId + "]");
        hideProgress(getSwipeRefreshLayout());
        showAlert(error);
    }
}
