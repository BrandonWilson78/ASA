package za.asa_media.awesome_sa.modules_.place_.model;

/**
 * Created by Snow-Dell-05 on 6/2/2017.
 */
/*
             "placeid": "ChIJ1SUbnIjuDzkR4TImjmW_dKk",
            "batchid": "[1]",
            "rating": "0.0",
            "distance": "6.87k.m",
            "address": "Ground Floor, Plot No. S.C.F.-4",
            "place_name": "Domino's Pizza"
*/

public class FavouritePlaceInfoModel {
    private String placeid;
    private String batchid[];
    private String rating;
    private String distance;
    private String address;
    private String place_name;

    private String latitude;
    private String longitude;

    public FavouritePlaceInfoModel(String placeid, String batchid[], String rating, String distance, String address, String place_name, String latitude, String longitude) {
        this.placeid = placeid;
        this.batchid = batchid;
        this.rating = rating;
        this.distance = distance;
        this.address = address;
        this.place_name = place_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPlaceid() {
        return placeid;
    }

    public String[] getBatchId() {
        return batchid;
    }

    public String getRating() {
        return rating;
    }

    public String getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public String getPlace_name() {
        return place_name;
    }
}