package com.binatestation.kickstart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.adapters.ViewPagerAdapter;

/**
 * Created by RKR on 30-08-2018.
 * ViewPagerFragment.
 */
public abstract class TabLayoutFragment extends BaseFragment {
    private static final String TAG = "ViewPagerFragment";

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intView(view);
    }

    private void intView(@NonNull View view) {
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager()));
    }

    public void addFragment(Fragment fragment, String title) {
        Log.d(TAG, "addFragment() called with: fragment = [" + fragment + "], title = [" + title + "]");
        if (mViewPagerAdapter != null) {
            mViewPagerAdapter.addFrag(fragment, title);
            mViewPagerAdapter.notifyDataSetChanged();
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }
}
