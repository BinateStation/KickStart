package com.binatestation.kickstart.fragments.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

import com.binatestation.kickstart.network.models.ErrorModel;


/**
 * Alert dialog fragment
 */
public class AlertDialogFragment extends BaseDialogFragment {
    public static final String TAG = "AlertDialogFragment";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_POSITIVE_BUTTON_TEXT = "positive_button_text";
    private static final String KEY_NEGATIVE_BUTTON_TEXT = "negative_button_text";
    private static final String KEY_NEUTRAL_BUTTON_TEXT = "neutral_button_text";

    private String mTitle;
    private String mMessage;
    private String mPositiveButtonText;
    private String mNegativeButtonText;
    private String mNeutralButtonText;

    private DialogInterface.OnClickListener mOnClickListener;

    public AlertDialogFragment() {
        // Required empty public constructor
    }

    public static AlertDialogFragment newInstance(ErrorModel errorModel) {
        Log.d(TAG, "newInstance() called with: errorModel = [" + errorModel + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, errorModel.getErrorTitle());
        args.putString(KEY_MESSAGE, errorModel.getErrorMessage());
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * gets the instance of AlertDialogFragment
     *
     * @param title   String
     * @param message String
     * @return AlertDialogFragment
     */
    public static AlertDialogFragment newInstance(String title, String message) {
        Log.d(TAG, "newInstance() called with: title = [" + title + "], message = [" + message + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * gets the instance of AlertDialogFragment
     *
     * @param title              String value
     * @param message            String value
     * @param positiveButtonText String value
     * @return AlertDialogFragment
     */
    public static AlertDialogFragment newInstance(String title, String message, String positiveButtonText) {
        Log.d(TAG, "newInstance() called with: title = [" + title + "], message = [" + message + "], positiveButtonText = [" + positiveButtonText + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * gets the instance of AlertDialogFragment
     *
     * @param title              String value
     * @param message            String value
     * @param positiveButtonText String value
     * @param negativeButtonText String value
     * @return AlertDialogFragment
     */
    public static AlertDialogFragment newInstance(String title, String message, String positiveButtonText, String negativeButtonText) {
        Log.d(TAG, "newInstance() called with: title = [" + title + "], message = [" + message + "], positiveButtonText = [" + positiveButtonText + "], negativeButtonText = [" + negativeButtonText + "]");
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        args.putString(KEY_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mOnClickListener == null) {
            if (context instanceof DialogInterface.OnClickListener) {
                mOnClickListener = (DialogInterface.OnClickListener) context;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(KEY_TITLE);
            mMessage = bundle.getString(KEY_MESSAGE);
            if (TextUtils.isEmpty(mPositiveButtonText) && bundle.containsKey(KEY_POSITIVE_BUTTON_TEXT)) {
                mPositiveButtonText = bundle.getString(KEY_POSITIVE_BUTTON_TEXT);
            }
            if (TextUtils.isEmpty(mNegativeButtonText) && bundle.containsKey(KEY_NEGATIVE_BUTTON_TEXT)) {
                mNegativeButtonText = bundle.getString(KEY_NEGATIVE_BUTTON_TEXT);
            }
            if (TextUtils.isEmpty(mNeutralButtonText) && bundle.containsKey(KEY_NEUTRAL_BUTTON_TEXT)) {
                mNeutralButtonText = bundle.getString(KEY_NEUTRAL_BUTTON_TEXT);
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            // request a window without the title
            Window window = dialog.getWindow();
            if (window != null) {
                window.requestFeature(Window.FEATURE_NO_TITLE);
            }
            return dialog;
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle(mTitle);
            alertDialogBuilder.setMessage(mMessage);
            alertDialogBuilder.setPositiveButton(mPositiveButtonText, mOnClickListener);
            alertDialogBuilder.setNegativeButton(mNegativeButtonText, mOnClickListener);
            alertDialogBuilder.setNeutralButton(mNeutralButtonText, mOnClickListener);
            return alertDialogBuilder.create();
        }
    }


    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

}
