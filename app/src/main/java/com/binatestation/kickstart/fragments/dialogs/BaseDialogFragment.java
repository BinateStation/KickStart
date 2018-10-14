package com.binatestation.kickstart.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.activities.BaseActivity;
import com.binatestation.kickstart.network.models.ErrorModel;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_MESSAGE;
import static com.binatestation.kickstart.utils.Constants.KEY_MESSAGE_TITLE;


/**
 * Created by RKR on 23/10/16.
 * BaseFragment
 */

public abstract class BaseDialogFragment extends DialogFragment {

    /**
     * sets the title
     *
     * @param title String value
     */
    protected void setTitle(String title) {
        try {
            if (getActivity() instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) getActivity();
                baseActivity.setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout != null) {
            setColorSchemeResources(swipeRefreshLayout);
        }
    }

    /**
     * show progress
     *
     * @param swipeRefreshLayout SwipeRefreshLayout
     */
    public void showProgress(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    /**
     * hide progress
     *
     * @param swipeRefreshLayout SwipeRefreshLayout
     */
    public void hideProgress(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    protected void setColorSchemeResources(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.progress_red,
                R.color.progress_blue,
                R.color.progress_yellow,
                R.color.progress_green
        );
    }

    /**
     * show progress wheel
     */
    public void showProgressWheel() {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) fragmentActivity;
            baseActivity.showProgressWheel();
        }
    }

    /**
     * hide progress wheel
     */
    public void hideProgressWheel() {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) fragmentActivity;
            baseActivity.hideProgressWheel();
        }
    }

    /**
     * show alert message
     *
     * @param message String value
     * @return instance of AlertDialogFragment
     */
    public AlertDialogFragment showAlert(String message) {
        return showAlert("", message);
    }

    /**
     * show alert message
     *
     * @param title   String value
     * @param message String value
     * @return instance of AlertDialogFragment
     */
    public AlertDialogFragment showAlert(String title, String message) {
        if (getContext() == null) {
            return null;
        }
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes));
        try {
            alertDialogFragment.show(getChildFragmentManager(), alertDialogFragment.getTag());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertDialogFragment;
    }

    /**
     * show alert
     *
     * @param errorModel ErrorModel
     */
    public AlertDialogFragment showAlert(@NonNull final ErrorModel errorModel) {
        return showAlert(errorModel.getErrorTitle(), errorModel.getErrorMessage());
    }


    /**
     * show alert
     *
     * @param jsonObject ErrorModel
     */
    public AlertDialogFragment showAlert(@NonNull final JSONObject jsonObject) {
        return showAlert(jsonObject.optString(KEY_MESSAGE_TITLE), jsonObject.optString(KEY_MESSAGE));
    }
}
