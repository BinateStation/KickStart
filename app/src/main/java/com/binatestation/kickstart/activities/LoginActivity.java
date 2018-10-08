package com.binatestation.kickstart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.utils.Session;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private TextInputLayout mUsernameTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mUsernameTextInputLayout = findViewById(R.id.field_username_layout);
        mPasswordTextInputLayout = findViewById(R.id.field_password_layout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_login) {
            actionLogin();
        }
    }

    private void actionLogin() {
        if (isValidInput()) {
            login();
        }
    }

    private boolean isValidInput() {
        if (mUsernameTextInputLayout.getEditText() != null && TextUtils.isEmpty(mUsernameTextInputLayout.getEditText().getText())) {
            mUsernameTextInputLayout.setError(getString(R.string.error_invalid_username));
            mUsernameTextInputLayout.getEditText().requestFocus();
            return false;
        }
        if (mPasswordTextInputLayout.getEditText() != null && TextUtils.isEmpty(mPasswordTextInputLayout.getEditText().getText())) {
            mPasswordTextInputLayout.setError(getString(R.string.error_invalid_password));
            mPasswordTextInputLayout.getEditText().requestFocus();
            return false;
        }
        return true;
    }

    private void login() {
        Session.setUserId(this, 1);
        finishLogin();
    }

    private void finishLogin() {
        startActivity(new Intent(this, SplashActivity.class));
        finishAffinity();
    }
}
