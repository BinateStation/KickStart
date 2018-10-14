package com.binatestation.kickstart.fragments.dialogs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.binatestation.kickstart.R;


/**
 * Dialog fragment to show progress dialog.
 */
public class ProgressDialogFragment extends BaseDialogFragment {

    public static final String TAG = "ProgressDialogFragment";

    private static ProgressDialogFragment sProgressDialogFragment;

    private boolean showing;

    public ProgressDialogFragment() {
        // Required empty public constructor
    }

    public static ProgressDialogFragment getInstance() {
        if (sProgressDialogFragment == null) {
            sProgressDialogFragment = newInstance();
        }
        return sProgressDialogFragment;
    }
    /**
     * gets the instance of ProgressDialogFragment
     *
     * @return ProgressDialogFragment
     */
    public static ProgressDialogFragment newInstance() {

        Bundle args = new Bundle();

        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme_Translucent);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProgressBar progressBar = new ProgressBar(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar.setLayoutParams(layoutParams);
        return progressBar;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!showing) {
            super.show(manager, tag);
            showing = true;
        }
    }

    @Override
    public void dismiss() {
        if (showing) {
            super.dismiss();
            showing = false;
        }
    }
}
