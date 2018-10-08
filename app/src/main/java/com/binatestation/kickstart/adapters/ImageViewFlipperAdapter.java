package com.binatestation.kickstart.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.models.BaseModel;
import com.binatestation.kickstart.utils.Utils;

import java.util.ArrayList;


/**
 * Created by RKR on 30-08-2017.
 * ImageViewFlipperAdapter
 */

public class ImageViewFlipperAdapter extends BaseAdapter {

    private ArrayList<Object> data = new ArrayList<>();

    public ImageViewFlipperAdapter() {
    }


    public void setData(ArrayList<?> data) {
        this.data = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_image_switcher, parent, false);
        if (position < data.size()) {
            Object object = getItem(position);
            if (object instanceof BaseModel) {
                BaseModel baseModel = (BaseModel) object;

                ImageView image = convertView.findViewById(R.id.image_view);
                Utils.setProfileImage(image, baseModel.getName());
            }
        }

        return convertView;
    }
}
