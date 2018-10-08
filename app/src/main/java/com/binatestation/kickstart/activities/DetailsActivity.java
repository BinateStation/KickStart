package com.binatestation.kickstart.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.fragments.PostDetailsFragment;
import com.binatestation.kickstart.models.PostModel;

import static com.binatestation.kickstart.utils.Constants.KEY_DATA_MODEL;

public class DetailsActivity extends BaseActivity {

    private static final String TAG = "DetailsActivity";
    /**
     * instance of Model classes
     */
    private Object mDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setHomeAsUp();

        mDataModel = getIntent().getParcelableExtra(KEY_DATA_MODEL);
        if (savedInstanceState == null) {
            switchFragments();
        }
    }


    /**
     * loads the fragments
     */
    private void switchFragments() {
        String action = getIntent().getAction();
        if (getString(R.string.post_details).equalsIgnoreCase(action)) {
            if (mDataModel instanceof PostModel) {
                PostModel postModel = (PostModel) mDataModel;
                loadFragment(PostDetailsFragment.newInstance(postModel), PostDetailsFragment.TAG);
            }
        }

    }

    /**
     * load the fragments to the container
     *
     * @param fragment Fragment value
     * @param tag      String value
     */
    private void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_frame_layout, fragment, tag)
                .commit();
    }

}
