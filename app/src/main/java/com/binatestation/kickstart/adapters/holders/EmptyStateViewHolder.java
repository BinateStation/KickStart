package com.binatestation.kickstart.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.listeners.AdapterListener;
import com.binatestation.kickstart.models.EmptyStateModel;


/**
 * Created by RKR on 21-09-2017.
 * EmptyStateViewHolder
 */

public class EmptyStateViewHolder extends BaseViewHolder {
    public static final int LAYOUT = R.layout.adapter_empty_state;

    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView messageTextView;

    public EmptyStateViewHolder(View itemView, AdapterListener adapter) {
        super(itemView, adapter);
        iconImageView = itemView.findViewById(R.id.icon);
        titleTextView = itemView.findViewById(R.id.title);
        messageTextView = itemView.findViewById(R.id.message);
    }

    @Override
    public void bindView(Object object) {
        if (object instanceof EmptyStateModel) {
            EmptyStateModel emptyStateModel = (EmptyStateModel) object;
            iconImageView.setImageResource(emptyStateModel.getIcon());
            titleTextView.setText(emptyStateModel.getTitle());
            messageTextView.setText(emptyStateModel.getMessage());
        }
    }

}
