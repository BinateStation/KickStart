package com.binatestation.kickstart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.activities.BaseActivity;
import com.binatestation.kickstart.fragments.dialogs.AlertDialogFragment;
import com.binatestation.kickstart.fragments.dialogs.ProgressDialogFragment;
import com.binatestation.kickstart.network.models.ErrorModel;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_MESSAGE;
import static com.binatestation.kickstart.utils.Constants.KEY_MESSAGE_TITLE;


/**
 * Created by RKR on 23/10/16.
 * BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    public static final String TAG_PROGRESS_DIALOG = "progress_dialog";
    private ProgressDialogFragment mProgressDialogFragment;
    private boolean showDialog;

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
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
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
     * gets the instance of ProgressDialogFragment
     *
     * @return instance of ProgressDialogFragment
     */
    public ProgressDialogFragment getProgress() {
        if (mProgressDialogFragment == null) {
            mProgressDialogFragment = ProgressDialogFragment.newInstance();
        }
        return mProgressDialogFragment;
    }

    /**
     * show progress wheel
     */
    public void showProgressWheel() {
        if (isShowDialog()) {
            if (getProgress().isAdded()) {
                return;
            }
            if (getProgress().isShowing()) {
                return;
            }
            getProgress().show(getChildFragmentManager(), TAG_PROGRESS_DIALOG);
        }
    }

    /**
     * hide progress wheel
     */
    public void hideProgressWheel() {
        if (mProgressDialogFragment != null) {
            if (!mProgressDialogFragment.isAdded()) {
                return;
            }
            mProgressDialogFragment.dismiss();
        }
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        setShowDialog(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setShowDialog(false);
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
        if (isShowDialog()) {
            alertDialogFragment.show(getChildFragmentManager(), alertDialogFragment.getTag());
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
