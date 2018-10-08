package com.binatestation.kickstart.fragments.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.adapters.RecyclerViewAdapter;
import com.binatestation.kickstart.listeners.OnListItemClickListener;
import com.binatestation.kickstart.models.MultiSelectDataModel;

import java.util.ArrayList;

/**
 * Created by RKR on 11-09-2018.
 * MultiSelectDialogFragment.
 */
public class MultiSelectDialogFragment extends BaseDialogFragment implements OnListItemClickListener, View.OnClickListener {
    public static final String TAG = "MultiSelectDialogFragme";

    private static final String KEY_REQUEST_CODE = "REQUEST_CODE";
    private int mRequestCode;
    private boolean mMultiSelectable = true;
    private Object mSelectedItem;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private MultiSelectListener mListener;

    public static MultiSelectDialogFragment newInstance(int requestCode) {

        Bundle args = new Bundle();
        args.putInt(KEY_REQUEST_CODE, requestCode);
        MultiSelectDialogFragment fragment = new MultiSelectDialogFragment();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestCode = bundle.getInt(KEY_REQUEST_CODE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(getAdapter());
        View actionPositive = view.findViewById(R.id.action_positive);
        View actionNegative = view.findViewById(R.id.action_negative);

        actionPositive.setOnClickListener(this);
        actionNegative.setOnClickListener(this);
    }

    public void setListener(MultiSelectListener mListener) {
        this.mListener = mListener;
    }

    /**
     * gets the instance of LinearLayoutManager
     *
     * @return LinearLayoutManager
     */
    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }

    /**
     * gets the recyclerView
     *
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setMultiSelectable(boolean multiSelectable) {
        this.mMultiSelectable = multiSelectable;
    }

    /**
     * gets the adapter
     *
     * @return RecyclerViewAdapter
     */
    public RecyclerViewAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter();
            mAdapter.setClickListener(this);
        }
        return mAdapter;
    }

    public void setData(ArrayList<?> data) {
        if (data != null) {
            getAdapter().setTypedData(MultiSelectDataModel.parseData(data));
        }
    }

    @Override
    public void onClickItem(Object object, int position, View actionView) {
        if (!mMultiSelectable) {
            mSelectedItem = object;
            actionPositive();
        }
    }

    public ArrayList<Object> getSelectedItems() {
        ArrayList<Object> selectedObjects = new ArrayList<>();
        if (mMultiSelectable) {
            ArrayList<?> objects = getAdapter().getData();
            for (Object object : objects) {
                if (object instanceof MultiSelectDataModel) {
                    MultiSelectDataModel multiSelectDataModel = (MultiSelectDataModel) object;
                    if (multiSelectDataModel.isSelected())
                        selectedObjects.add(multiSelectDataModel.getItem());
                }
            }
        } else {
            if (mSelectedItem instanceof MultiSelectDataModel) {
                MultiSelectDataModel selectedItem = (MultiSelectDataModel) mSelectedItem;
                selectedObjects.add(selectedItem.getItem());
            }
        }
        return selectedObjects;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_positive) {
            actionPositive();
        } else if (v.getId() == R.id.action_negative) {
            dismiss();
        }
    }

    private void actionPositive() {
        if (mListener != null) {
            mListener.onSubmit(getSelectedItems(), mRequestCode);
        }
        dismiss();
    }

    public interface MultiSelectListener {
        void onSubmit(ArrayList<Object> objects, int requestCode);
    }
}
