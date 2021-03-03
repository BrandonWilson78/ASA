package za.asa_media.awesome_sa.modules_.data;

import za.asa_media.awesome_sa.modules_.place_.MainScreen;

/**
 * Created by Snow-Dell-05 on 4/25/2017.
 */

public class InitialValueSetUp {

    public static String mType = "";
    public static String mTypeHeading = "";
    public static String mTypeHeadingForListing = "";
    public static String mPlaceId = "";
    public static String mDistance = "";
    public static float mRating = 0f;
    public static String mPlaceName = "";
    public static double lat = 0.0000;
    public static double lng = 0.0000;
    public static MainScreen mContext;
    public static String nextPageToken;

    // Batch Id and Place Id
    public static String adBatchId = "";
    public static String adPlaceId = "";
    public static String statusOpening = "";

    public static String favFlag = "";
    public static String mPlaceAddress = "";

    // batch array
    public static String[] mBatchs;
    public static SelectedPlaceListdata mPlaceObj;
    public static String mDistancePlace;

    public static boolean mConectivityCheck = false;


    public static String locationName;
    public static String searchedLocationName = "";
}
