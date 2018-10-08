package com.binatestation.kickstart.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {
    private ImageView mProfileImageView;
    private TextView mNameTextView;
    private TextView mDescriptionTextView;
    private View mRootView;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * gets the instance of UserFragment
     *
     * @return UserFragment
     */
    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_user, container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileImageView = view.findViewById(R.id.profile_image);
        mNameTextView = view.findViewById(R.id.field_name);
        mDescriptionTextView = view.findViewById(R.id.field_description);

    }


    /**
     * sets the color of name
     *
     * @param color int value
     */
    public void setNameColor(@ColorRes int color) {
        if (mNameTextView != null && getContext() != null) {
            mNameTextView.setTextColor(ContextCompat.getColor(getContext(), color));
        }

    }

    /**
     * sets the color of description
     *
     * @param color int value
     */
    public void setDescriptionColor(@ColorRes int color) {
        if (mDescriptionTextView != null && getContext() != null) {
            mDescriptionTextView.setTextColor(ContextCompat.getColor(getContext(), color));
        }
    }

    /**
     * sets the name
     *
     * @param label String value
     */
    public void setName(String label) {
        if (mNameTextView != null) {
            if (!TextUtils.isEmpty(label)) {
                mNameTextView.setText(label);
            }
        }

    }

    /**
     * sets the profile image
     *
     * @param imageUri String value
     */
    public void setProfileImage(Uri imageUri) {
        if (mProfileImageView != null) {
            if (imageUri != null) {
                Utils.setCircleProfileImage(mProfileImageView, imageUri);
            }
        }

    }

    /**
     * sets the profile image
     *
     * @param imageUrl String value
     */
    public void setProfileImage(String imageUrl) {
        if (mProfileImageView != null) {
            if (imageUrl != null) {
                Utils.setCircleProfileImage(mProfileImageView, imageUrl);
            }
        }

    }

    /**
     * sets the description
     *
     * @param description String value
     */
    public void setDescription(String description) {
        if (mDescriptionTextView != null) {
            if (!TextUtils.isEmpty(description)) {
                mDescriptionTextView.setText(description);
            }
        }

    }

}
