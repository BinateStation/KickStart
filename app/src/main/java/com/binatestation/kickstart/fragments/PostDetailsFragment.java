package com.binatestation.kickstart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.models.PostModel;

public class PostDetailsFragment extends BaseFragment {
    public static final String TAG = "PostDetailsFragment";
    private static final String KEY_POST_MODEL = "POST_MODEL";

    private PostModel mPostModel;

    public static PostDetailsFragment newInstance(PostModel postModel) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_POST_MODEL, postModel);
        PostDetailsFragment fragment = new PostDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPostModel = bundle.getParcelable(KEY_POST_MODEL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nameTextView = view.findViewById(R.id.field_name);
        TextView descriptionTextView = view.findViewById(R.id.field_description);

        nameTextView.setText(mPostModel.getName());
        descriptionTextView.setText(mPostModel.getBody());
    }
}
