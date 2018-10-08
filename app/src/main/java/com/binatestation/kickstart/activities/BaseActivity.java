package com.binatestation.kickstart.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.binatestation.kickstart.fragments.dialogs.AlertDialogFragment;
import com.binatestation.kickstart.fragments.dialogs.ProgressDialogFragment;
import com.binatestation.kickstart.network.models.ErrorModel;

import org.json.JSONObject;

import static com.binatestation.kickstart.utils.Constants.KEY_MESSAGE;
import static com.binatestation.kickstart.utils.Constants.KEY_MESSAGE_TITLE;


public abstract class BaseActivity extends AppCompatActivity {

    private ActionBar actionBar = null;
    private ProgressDialogFragment mProgressDialogFragment;
    private boolean showDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        showDialog = false;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    /**
     * sets the title
     *
     * @param title String value
     */
    public void setTitle(String title) {
        if (getActiveActionBar() != null) {
            getActiveActionBar().setDisplayShowTitleEnabled(true);
            getActiveActionBar().setLogo(null);
            getActiveActionBar().setTitle(title);
        }
    }

    public void setHomeAsUp() {
        ActionBar actionBar = getActiveActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hideHomeAsUp() {
        ActionBar actionBar = getActiveActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    protected ActionBar getActiveActionBar() {
        if (actionBar == null) {
            actionBar = getSupportActionBar();
        }
        return actionBar;
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
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes));
        if (isShowDialog()) {
            alertDialogFragment.show(getSupportFragmentManager(), alertDialogFragment.getTag());
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
    @SuppressWarnings("unused")
    public void showProgressWheel() {
        if (isShowDialog()) {
            if (getProgress().isAdded()) {
                return;
            }
            if (getProgress().isShowing()) {
                return;
            }
            getProgress().show(getSupportFragmentManager(), ProgressDialogFragment.TAG);
        }
    }

    /**
     * hide progress wheel
     */
    @SuppressWarnings("unused")
    public void hideProgressWheel() {
        getProgress().dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
