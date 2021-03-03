package za.asa_media.awesome_sa.modules_.placedetail_;

import java.io.Serializable;
import java.util.ArrayList;


public class BeanPlaceDetail implements Serializable {

    private String pd_formatted_address;
    private String pd_formatted_phone_no;
    private String pd_international_phone_no;
    private ArrayList<String> pd_photo_refrence;
    private String pd_location_url;
    private String pd_website_url;
    private boolean flagOpenNow = false;
    private ArrayList<String> pd_openning_schedule;
    private ArrayList<BeanPlaceDetailReviews> pd_reviews;
    private String ratingCount;

    private String Latitude = "";
    private String Longitude = "";


    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public BeanPlaceDetail() {
        pd_photo_refrence = new ArrayList<>();
        pd_openning_schedule = new ArrayList<>();
        pd_reviews = new ArrayList<>();
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String mLatitude) {
        this.Latitude = mLatitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String mLongitude) {
        this.Longitude = mLongitude;
    }

    public boolean isFlagOpenNow() {
        return flagOpenNow;
    }

    public void setFlagOpenNow(boolean mFlagOpenNow) {
        this.flagOpenNow = mFlagOpenNow;
    }

    public String getPd_formatted_address() {
        return pd_formatted_address;
    }

    public void setPd_formatted_address(String pd_formatted_address) {
        this.pd_formatted_address = pd_formatted_address;
    }

    public String getPd_formatted_phone_no() {
        return pd_formatted_phone_no;
    }

    public void setPd_formatted_phone_no(String pd_formatted_phone_no) {
        this.pd_formatted_phone_no = pd_formatted_phone_no;
    }

    public String getPd_international_phone_no() {
        return pd_international_phone_no;
    }

    public void setPd_international_phone_no(String pd_international_phone_no) {
        this.pd_international_phone_no = pd_international_phone_no;
    }

    public ArrayList<String> getPd_photo_refrence() {
        return pd_photo_refrence;
    }

    public void setPd_photo_refrence(ArrayList<String> pd_photo_refrence) {
        this.pd_photo_refrence = pd_photo_refrence;
    }


    public ArrayList<BeanPlaceDetailReviews> getPd_reviews() {
        return pd_reviews;
    }

    public void setPd_reviews(ArrayList<BeanPlaceDetailReviews> pd_reviews) {

        this.pd_reviews = pd_reviews;

    }

    public String getPd_location_url() {
        return pd_location_url;
    }

    public void setPd_location_url(String pd_location_url) {
        this.pd_location_url = pd_location_url;
    }

    public String getPd_website_url() {
        return pd_website_url;
    }

    public void setPd_website_url(String pd_website_url) {
        this.pd_website_url = pd_website_url;
    }

    public ArrayList<String> getPd_openning_schedule() {
        return pd_openning_schedule;
    }

    public void setPd_openning_schedule(ArrayList<String> pd_openning_schedule) {
        this.pd_openning_schedule = pd_openning_schedule;
    }
}
