package com.binatestation.kickstart.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.network.models.ErrorModel;

import java.util.ArrayList;


/**
 * Created by RKR on 21-09-2017.
 * EmptyStateModel
 */

public class EmptyStateModel extends BaseModel implements Parcelable {

    public static final Creator<EmptyStateModel> CREATOR = new Creator<EmptyStateModel>() {
        @Override
        public EmptyStateModel createFromParcel(Parcel in) {
            return new EmptyStateModel(in);
        }

        @Override
        public EmptyStateModel[] newArray(int size) {
            return new EmptyStateModel[size];
        }
    };
    /**
     * Icon
     */
    @DrawableRes
    private int icon;
    /**
     * Message
     */
    private String message;

    public EmptyStateModel(long id, String title, String message, @DrawableRes int icon) {
        super(id, title);
        this.icon = icon;
        this.message = message;
    }

    protected EmptyStateModel(Parcel in) {
        super(in);
        icon = in.readInt();
        message = in.readString();
    }

    /**
     * gets the EmptyStateModel
     *
     * @param errorModel ErrorModel
     * @return EmptyStateModel
     */
    public static EmptyStateModel getEmptyModel(ErrorModel errorModel) {
        return new EmptyStateModel(-1, errorModel.getErrorTitle(), errorModel.getErrorMessage(), R.drawable.ic_hourglass_empty_black_24dp);
    }

    /**
     * gets the EmptyStateModel
     *
     * @return EmptyStateModel
     */
    public static EmptyStateModel getEmptyDataModel() {
        return new EmptyStateModel(-1, "No Data", "No data available for you yet, Please tune after some times..!", R.drawable.ic_hourglass_empty_black_24dp);
    }

    /**
     * gets the list of EmptyStateModels
     *
     * @return list of EmptyStateModels
     */
    public static ArrayList<Object> getEmptyDataModels() {
        return getEmptyDataModels(EmptyStateModel.getEmptyDataModel());
    }

    /**
     * gets the list of EmptyStateModels
     *
     * @param emptyStateModel EmptyStateModel
     * @return list of EmptyStateModels
     */
    public static ArrayList<Object> getEmptyDataModels(EmptyStateModel emptyStateModel) {
        ArrayList<Object> emptyStateModels = new ArrayList<>();
        emptyStateModels.add(emptyStateModel);
        return emptyStateModels;
    }

    /**
     * gets EmptyStateModel
     *
     * @return EmptyStateModel
     */
    public static EmptyStateModel getNoInternetEmptyModel() {
        return new EmptyStateModel(-1, "No Internet", "Sorry you are not connected to internet, Please check your connection and try again!", R.drawable.ic_signal_wifi_off_black_24dp);
    }

    /**
     * gets EmptyStateModel
     *
     * @return EmptyStateModel
     */
    public static EmptyStateModel getUnKnownEmptyModel() {
        return new EmptyStateModel(-1, "Alert", "Something went wrong, Please try again later !", R.drawable.ic_hourglass_empty_black_24dp);
    }

    /**
     * gets EmptyStateModel
     *
     * @return EmptyStateModel
     */
    public static EmptyStateModel getTestCartEmptyStateModel() {
        return new EmptyStateModel(-1, "Alert", "Something went wrong, Please try again later !", R.drawable.ic_hourglass_empty_black_24dp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmptyStateModel) {
            EmptyStateModel emptyStateModel = (EmptyStateModel) obj;
            return getId() == emptyStateModel.getId();
        }
        return super.equals(obj);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(icon);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * gets the icon
     *
     * @return icon
     */
    public int getIcon() {
        return icon;
    }

    /**
     * sets the icon
     *
     * @param icon int value
     */
    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    /**
     * gets the title
     *
     * @return title
     */
    public String getTitle() {
        return super.getName();
    }

    /**
     * sets the title
     *
     * @param title String value
     */
    public void setTitle(String title) {
        super.setName(title);
    }

    /**
     * gets the message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message
     *
     * @param message String value
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
