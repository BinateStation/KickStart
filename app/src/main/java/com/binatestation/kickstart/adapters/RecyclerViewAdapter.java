package com.binatestation.kickstart.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binatestation.kickstart.adapters.holders.EmptyStateViewHolder;
import com.binatestation.kickstart.adapters.holders.MultiSelectViewHolder;
import com.binatestation.kickstart.adapters.holders.PostViewHolder;
import com.binatestation.kickstart.listeners.AdapterListener;
import com.binatestation.kickstart.listeners.OnListItemClickListener;
import com.binatestation.kickstart.listeners.ViewBinder;
import com.binatestation.kickstart.models.AlbumModel;
import com.binatestation.kickstart.models.CommentModel;
import com.binatestation.kickstart.models.EmptyStateModel;
import com.binatestation.kickstart.models.MultiSelectDataModel;
import com.binatestation.kickstart.models.PhotoModel;
import com.binatestation.kickstart.models.PostModel;
import com.binatestation.kickstart.models.TodoModel;
import com.binatestation.kickstart.models.UserModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by RKR on 7/13/2017.
 * RecyclerViewAdapter
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterListener {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Object> data;
    /**
     * the listener object to have list item click
     */
    private OnListItemClickListener mClickListener;

    public RecyclerViewAdapter() {
        data = EmptyStateModel.getEmptyDataModels();
    }

    @Override
    public OnListItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnListItemClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public ArrayList<?> getData() {
        return data;
    }

    /**
     * sets the data to the adapter class
     *
     * @param data ArrayList of any Model class
     */
    public void setData(ArrayList<Object> data) {
        if (data.size() > 0) {
            this.data = data;
        } else {
            this.data = EmptyStateModel.getEmptyDataModels();
        }
        notifyDataSetChanged();
    }

    /**
     * sets the data to the adapter class
     *
     * @param data ArrayList of any Model class
     */
    public void setTypedData(List<?> data) {
        ArrayList<Object> objects = new ArrayList<>(data);
        if (data.size() > 0) {
            this.data = objects;
        } else {
            this.data = EmptyStateModel.getEmptyDataModels();
        }
        notifyDataSetChanged();
    }

    public void add(Object object) {
        if (this.data.remove(EmptyStateModel.getUnKnownEmptyModel())) {
            notifyDataSetChanged();
        }
        this.data.add(object);
        notifyItemInserted(data.size() - 1);
    }

    public void add(Object object, int position) {
        if (this.data.remove(EmptyStateModel.getUnKnownEmptyModel())) {
            notifyDataSetChanged();
        }
        if (position < data.size()) {
            this.data.add(position, object);
            notifyItemInserted(position);
        }
    }

    /**
     * removes item from specified position
     *
     * @param position position of the item to remove
     */
    @SuppressWarnings("unused")
    public void removeItem(int position) {
        if (position < data.size() && position >= 0) {
            this.data.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = getItem(position);
        if (object instanceof PostModel || object instanceof CommentModel ||
                object instanceof AlbumModel || object instanceof PhotoModel || object instanceof TodoModel
                || object instanceof UserModel) {
            return PostViewHolder.LAYOUT;
        }
        if (object instanceof EmptyStateModel) {
            return EmptyStateViewHolder.LAYOUT;
        }
        if (object instanceof MultiSelectDataModel) {
            return MultiSelectViewHolder.LAYOUT;
        }
        return EmptyStateViewHolder.LAYOUT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case PostViewHolder.LAYOUT:
                return new PostViewHolder(itemView, this);
            case MultiSelectViewHolder.LAYOUT:
                return new MultiSelectViewHolder(itemView, this);
        }
        return new EmptyStateViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        if (holder instanceof ViewBinder) {
            ViewBinder viewBinder = (ViewBinder) holder;
            viewBinder.bindView(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}

