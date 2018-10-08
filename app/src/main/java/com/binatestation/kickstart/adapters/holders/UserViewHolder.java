package com.binatestation.kickstart.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.adapters.RecyclerViewAdapter;
import com.binatestation.kickstart.models.UserModel;


/**
 * Created by RKR on 8/2/2017.
 * UserViewHolder
 */

public class UserViewHolder extends BaseViewHolder {
    public static final int LAYOUT = R.layout.fragment_user;

    TextView mDescriptionTextView;
    TextView mNameTextView;
    ImageView mProfileImageView;

    public UserViewHolder(View itemView, RecyclerViewAdapter recyclerViewAdapter) {
        super(itemView, recyclerViewAdapter);

        mProfileImageView = itemView.findViewById(R.id.profile_image);
        mNameTextView = itemView.findViewById(R.id.field_name);
        mDescriptionTextView = itemView.findViewById(R.id.field_description);
        itemView.setOnClickListener(this);
    }


    @Override
    public void bindView(Object object) {
        if (object instanceof UserModel) {
            UserModel userModel = (UserModel) object;
            mNameTextView.setText(userModel.getName());
            mDescriptionTextView.setText(userModel.getEmail());
//            Utils.setCircleProfileImage(mProfileImageView, userModel.g());
        }
    }

    /**
     * sets the caption
     *
     * @param caption String value
     */
    void setCaption(String caption) {
        this.mDescriptionTextView.setText(caption);
    }

    void setName(String name) {
        this.mNameTextView.setText(name);
    }
}
