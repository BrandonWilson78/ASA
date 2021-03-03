package za.asa_media.awesome_sa.modules_.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Snow-Dell-05 on 4/25/2017.
 */

public class SelectedPlaceListdata implements Parcelable {
    public static final Creator<SelectedPlaceListdata> CREATOR = new Creator<SelectedPlaceListdata>() {
        @Override
        public SelectedPlaceListdata createFromParcel(Parcel in) {
            return new SelectedPlaceListdata(in);
        }

        @Override
        public SelectedPlaceListdata[] newArray(int size) {
            return new SelectedPlaceListdata[size];
        }
    };
    boolean ps_opening_status;
    boolean mFlagCheckTiming;
    double ps_rating;
    private String ps_formatted_address, ps_lat, ps_lng,
            ps_name,
            ps_place_id, ps_photo_refrence, ps_photo_height;
    private boolean mCheck;

    public SelectedPlaceListdata(Parcel in) {
        ps_opening_status = in.readByte() != 0;
        mFlagCheckTiming = in.readByte() != 0;
        ps_rating = in.readDouble();
        ps_formatted_address = in.readString();
        ps_lat = in.readString();
        ps_lng = in.readString();
        ps_name = in.readString();
        ps_place_id = in.readString();
        ps_photo_refrence = in.readString();
        ps_photo_height = in.readString();
        mCheck = in.readByte() != 0;
    }

    public boolean ismCheck() {
        return mCheck;

    }

    public void setmCheck(boolean mCheck) {
        this.mCheck = mCheck;

    }

    public String getPs_formatted_address() {
        return ps_formatted_address;
    }

    public void setPs_formatted_address(String ps_formatted_address) {
        this.ps_formatted_address = ps_formatted_address;
    }

    public boolean ismFlagCheckTiming() {
        return mFlagCheckTiming;
    }

    public void setmFlagCheckTiming(boolean mFlagCheckTiming) {
        this.mFlagCheckTiming = mFlagCheckTiming;
    }

    public String getPs_lat() {
        return ps_lat;
    }

    public void setPs_lat(String ps_lat) {
        this.ps_lat = ps_lat;
    }

    public String getPs_lng() {
        return ps_lng;
    }

    public void setPs_lng(String ps_lng) {
        this.ps_lng = ps_lng;
    }

    public String getPs_name() {
        return ps_name;
    }

    public void setPs_name(String ps_name) {
        this.ps_name = ps_name;
    }

    public boolean isPs_opening_status() {
        return ps_opening_status;
    }

    public void setPs_opening_status(boolean ps_opening_status) {
        this.ps_opening_status = ps_opening_status;
    }

    public String getPs_place_id() {
        return ps_place_id;
    }

    public void setPs_place_id(String ps_place_id) {
        this.ps_place_id = ps_place_id;
    }

    public String getPs_photo_refrence() {
        return ps_photo_refrence;
    }

    public void setPs_photo_refrence(String ps_photo_refrence) {
        this.ps_photo_refrence = ps_photo_refrence;
    }

    public String getPs_photo_height() {
        return ps_photo_height;
    }

    public void setPs_photo_height(String ps_photo_height) {
        this.ps_photo_height = ps_photo_height;
    }

    public double getPs_rating() {
        return ps_rating;
    }

    public void setPs_rating(double ps_rating) {
        this.ps_rating = ps_rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (ps_opening_status ? 1 : 0));
        dest.writeByte((byte) (mFlagCheckTiming ? 1 : 0));
        dest.writeDouble(ps_rating);
        dest.writeString(ps_formatted_address);
        dest.writeString(ps_lat);
        dest.writeString(ps_lng);
        dest.writeString(ps_name);
        dest.writeString(ps_place_id);
        dest.writeString(ps_photo_refrence);
        dest.writeString(ps_photo_height);
        dest.writeByte((byte) (mCheck ? 1 : 0));
    }
}
