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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * sets the title
     *
     * @param title String value
     */
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setLogo(null);
            getSupportActionBar().setTitle(title);
        }
    }

    public void setHomeAsUp() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hideHomeAsUp() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
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
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(title, message, getString(android.R.string.yes));
        try {
            alertDialogFragment.show(getSupportFragmentManager(), alertDialogFragment.getTag());
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



    /**
     * show progress wheel
     */
    public void showProgressWheel() {
        ProgressDialogFragment.getInstance().show(getSupportFragmentManager(), ProgressDialogFragment.TAG);
    }

    /**
     * hide progress wheel
     */
    public void hideProgressWheel() {
        ProgressDialogFragment.getInstance().dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
