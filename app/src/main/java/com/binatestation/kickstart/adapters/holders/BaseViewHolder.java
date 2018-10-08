package com.binatestation.kickstart.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.binatestation.kickstart.listeners.AdapterListener;
import com.binatestation.kickstart.listeners.ViewBinder;


/**
 * Created by RKR on 08-12-2017.
 * BaseViewHolder
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ViewBinder {
    private final AdapterListener mAdapterListener;

    BaseViewHolder(View itemView, AdapterListener adapterListener) {
        super(itemView);
        this.mAdapterListener = adapterListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION && mAdapterListener != null && mAdapterListener.getClickListener() != null) {
            mAdapterListener.getClickListener().onClickItem(mAdapterListener.getItem(position), position, view);
        }
    }

    @Override
    public abstract void bindView(Object object);

    public AdapterListener getAdapterListener() {
        return mAdapterListener;
    }
}
