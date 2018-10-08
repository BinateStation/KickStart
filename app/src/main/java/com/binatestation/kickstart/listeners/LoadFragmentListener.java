package com.binatestation.kickstart.listeners;

import android.support.v4.app.Fragment;

/**
 * Created by RKR on 08-08-2018.
 * LoadFragmentListener.
 */
public interface LoadFragmentListener {
    /**
     * load the fragments to the container
     *
     * @param fragment       Fragment value
     * @param tag            String value
     * @param addToBackStack whether this fragment add to back stack or not
     */
    void loadFragment(Fragment fragment, String tag, boolean addToBackStack);
}
