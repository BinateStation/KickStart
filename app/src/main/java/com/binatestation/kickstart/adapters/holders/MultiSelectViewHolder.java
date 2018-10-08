package com.binatestation.kickstart.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.listeners.AdapterListener;
import com.binatestation.kickstart.models.MultiSelectDataModel;

/**
 * Created by RKR on 11-09-2018.
 * MultiSelectViewHolder.
 */
public class MultiSelectViewHolder extends BaseViewHolder implements CompoundButton.OnCheckedChangeListener {
    public static final int LAYOUT = R.layout.adapter_multi_select;


    public MultiSelectViewHolder(View itemView, AdapterListener adapterListener) {
        super(itemView, adapterListener);
        if (itemView instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) itemView;
            checkBox.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void bindView(Object object) {
        if (itemView instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) itemView;
            if (object instanceof MultiSelectDataModel) {
                MultiSelectDataModel multiSelectDataModel = (MultiSelectDataModel) object;
                checkBox.setChecked(multiSelectDataModel.isSelected());
                checkBox.setText(multiSelectDataModel.getItem().toString());
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (getAdapterListener() != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
            Object object = getAdapterListener().getItem(getAdapterPosition());
            if (object instanceof MultiSelectDataModel) {
                MultiSelectDataModel multiSelectDataModel = (MultiSelectDataModel) object;
                multiSelectDataModel.setSelected(isChecked);
            }
        }
    }
}
