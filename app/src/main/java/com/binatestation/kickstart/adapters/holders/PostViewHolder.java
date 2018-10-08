package com.binatestation.kickstart.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.listeners.AdapterListener;
import com.binatestation.kickstart.models.AlbumModel;
import com.binatestation.kickstart.models.CommentModel;
import com.binatestation.kickstart.models.PhotoModel;
import com.binatestation.kickstart.models.PostModel;
import com.binatestation.kickstart.models.TodoModel;
import com.binatestation.kickstart.models.UserModel;

public class PostViewHolder extends BaseViewHolder {
    public static final int LAYOUT = R.layout.adapter_post;

    private TextView nameTextView;
    private TextView descriptionTextView;

    public PostViewHolder(View itemView, AdapterListener adapterListener) {
        super(itemView, adapterListener);
        nameTextView = itemView.findViewById(R.id.field_name);
        descriptionTextView = itemView.findViewById(R.id.field_description);
    }

    @Override
    public void bindView(Object object) {
        if (object instanceof PostModel) {
            PostModel postModel = (PostModel) object;
            nameTextView.setText(postModel.getName());
            descriptionTextView.setText(postModel.getBody());
        }
        if (object instanceof CommentModel) {
            CommentModel commentModel = (CommentModel) object;
            nameTextView.setText(commentModel.getName());
            descriptionTextView.setText(commentModel.getBody());
        }
        if (object instanceof AlbumModel) {
            AlbumModel commentModel = (AlbumModel) object;
            nameTextView.setText(commentModel.getName());
//            descriptionTextView.setText(commentModel.getBody());
        }
        if (object instanceof PhotoModel) {
            PhotoModel commentModel = (PhotoModel) object;
            nameTextView.setText(commentModel.getName());
            descriptionTextView.setText(commentModel.getUrl());
        }
        if (object instanceof TodoModel) {
            TodoModel commentModel = (TodoModel) object;
            nameTextView.setText(commentModel.getName());
            descriptionTextView.setText(commentModel.isCompleted() + "");
        }
        if (object instanceof UserModel) {
            UserModel commentModel = (UserModel) object;
            nameTextView.setText(commentModel.getName());
            descriptionTextView.setText(commentModel.getEmail());
        }
    }
}
