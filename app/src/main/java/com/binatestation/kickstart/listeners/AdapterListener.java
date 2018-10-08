package com.binatestation.kickstart.listeners;

/**
 * Created by RKR on 29-08-2018.
 * AdapterListener.
 */
public interface AdapterListener {
    /**
     * Method to get click listener
     *
     * @return the click listener
     */
    OnListItemClickListener getClickListener();

    /**
     * Method to get item at position
     *
     * @param position the position of item to get
     * @return the object at position
     */
    Object getItem(int position);
}
