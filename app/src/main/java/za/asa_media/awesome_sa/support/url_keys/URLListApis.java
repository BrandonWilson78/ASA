package za.asa_media.awesome_sa.support.url_keys;

public class URLListApis {


    // Api key for places data not for map
    public static final String api_key = "AIzaSyBej3CtGXxEC7cZiUhnz1m_gxXJvZx9lfE";// "AIzaSyDOM5wTOgj9wreSSTiNzhD8PjPJ4VLBu8A";
    public static final String URL_SEARCH_NEAR_BY_PLACE; //     /user/login **
    public static final String URL_PLACES_DETAILS;

    // restful api used inapplication
    public static final String URL_NEXT_PAGE_WITH_TOKEN;
    public static final String URL_GENERATE_TOKEN;// auth/generatekey
    public static final String URL_LOGIN;
    public static final String URL_SIGNUP; // http://112.196.5.114/asa/api/auth/forgotpass
    public static final String URL_FORGOT_PASSWORD;
    public static final String URL_ADD_USERINFO;
    public static final String URL_ADD_BUSINESS;
    public static final String URL_GET_PLANS;
    public static final String URL_PURCHASE_ADDPAYMENT;     //purchase/addpayment
    public static final String URL_PURCHASE_UPDATESTATUS;   //purchase/updatestatus
    public static final String URL_PURCHASE_HISTORY;        //purchase/history
    public static final String URL_ADS_GET_DETAIL;
    public static final String URL_ADS_CREATED;             //http://112.196.5.114/asa/api/ads/createad
    public static final String URL_GET_ADS_DETAIL_WITH_STATUS;  //http://112.196.5.114/asa/api/ads/getaddsdetail
    public static final String URL_GET_USERINFO;
    public static final String URL_GET_ALL_RUNNING_ADS;
    public static final String URL_GET_INFO_WITH_BATCH_ID;
    public static final String URL_ADD_TO_FAVOURITE;
    public static final String URL_CHECK_FOR_FAVOURITE;
    public static final String URL_GET_SHARE_LINK;
    public static final String URL_GET_NOTIFICATIONS;  // http://112.196.5.114/asa/api/user/adduserinfo
    public static final String URL_GET_FAVOURITES;     // http://112.196.5.114/asa; deviceid

    public static final String URL_SAVE_NOTIFICATION;
    public static final String URL_CHECK_NOTIFICATION; // http://112.196.5.114/asa/api/ads/updated
    public static final String URL_EDIT_ADS;
    public static final String URL_BUSINESS_DETAIL;     //http://112.196.5.114/asa/api/business/businessdetails
    public static final String URL_GET_SOCIAL_INFO;    // http://112.196.5.114/asa/api/auth/businesssocialdetails
    public static final String URL_GET_EXPIRED_ADS;      //http://112.196.5.114/asa/api/ads/getexpirydetail

    public static final String URL_GET_NEW_PURCHSED_ID;      //http://112.196.5.114/asa/api/purchase/getnewpurchaseid

    public static final String URL_PAYMENT_API;   // http://112.196.5.114/asa/api/payment


    public static final String URL_GET_AD_INFO_FOR_LOGGED_USER;
    //http://112.196.5.114/asa
    public static final String URL_TERMS_AND_CONDITIONS;
    public static final String URL_LOGOUT;  //

    public static final String URL_GET_PLAN_DEATAIL_WITH_PLACEID;   //  http://112.196.5.114/asa/api/auth/getaddsdetailsbyplaceid

    //     From 30 Mar 2018
    public static final String URL_SAVE_BUSINESS_DETAILS;           //   http://112.196.5.114/asa/api/auth/savebusiness

    // how to register
    public static final String URL_HOW_TO_REGISTER; // http://112.196.5.114/asa/api/auth/registerdetail


    //    URL_HOW_TO_REGISTER = (new StringBuilder(commonApiURL)).append("/api/auth/registerdetail").toString();

    public static final String URL_COMMON_IMAGES;
    public static String URL_GOOGLE_IMAGES_LINK_ = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photoreference=TASVEER&key=" + api_key;

    static String commonGoogleURL;
    static String commonImageURL;
    static String commonApiURL;

    static {

        //   New Url
        commonGoogleURL = "https://maps.googleapis.com/maps/api/place";
        //   http://112.196.5.114/asa/dist/img/ads/3328Wirsing-SendingAndReceivingDataViaBluetoothWithAnAndroidDevice.pdf
        commonImageURL = "http://112.196.5.114/asa/dist/img/userimage/"; // "http://awesomeafricaapp.com/dist/img/userimage/";
        //   commonApiURL = "http://awesomeafricaapp.com/dist/img/userimage/"

        commonApiURL = "http://www.awesomeafricaapp.com";// "http://112.196.5.114/asa"; // http://www.awesomeafricaapp.com/
        //  http://awesomeafricaapp.com/dist/img/userimage/
        URL_COMMON_IMAGES = commonImageURL;
        //&rankby=distance
        URL_SEARCH_NEAR_BY_PLACE = (new StringBuilder(commonGoogleURL)).append("/nearbysearch/json?location=lt_lg&radius=20000&xyz&key=" + api_key).toString();
        URL_PLACES_DETAILS = (new StringBuilder(commonGoogleURL)).append("/details/json?placeid=JAGH_&key=" + api_key).toString();//
        URL_NEXT_PAGE_WITH_TOKEN = (new StringBuilder(commonGoogleURL)).append("/nearbysearch/json?pagetoken=PAGE_TOKEN&key=" + api_key).toString();

        // Rest full api urls
        URL_GENERATE_TOKEN = (new StringBuilder(commonApiURL)).append("/api/auth/generatekey").toString();
        URL_LOGIN = (new StringBuilder(commonApiURL)).append("/api/auth/login").toString();
        URL_SIGNUP = (new StringBuilder(commonApiURL)).append("/api/auth/signup").toString();
        URL_FORGOT_PASSWORD = (new StringBuilder(commonApiURL)).append("/api/auth/forgotpass").toString();
        URL_ADD_USERINFO = (new StringBuilder(commonApiURL)).append("/api/user/adduserinfo").toString();
        URL_ADD_BUSINESS = (new StringBuilder(commonApiURL)).append("/api/business/addbusiness").toString();
        URL_GET_PLANS = (new StringBuilder(commonApiURL)).append("/api/plans/getplans").toString();
        URL_PURCHASE_ADDPAYMENT = (new StringBuilder(commonApiURL)).append("/api/purchase/addpayment").toString();
        URL_PURCHASE_UPDATESTATUS = (new StringBuilder(commonApiURL)).append("/api/purchase/updatestatus").toString();
        URL_PURCHASE_HISTORY = (new StringBuilder(commonApiURL)).append("/api/purchase/history").toString();
        URL_ADS_GET_DETAIL = (new StringBuilder(commonApiURL)).append("/api/ads/getdetail").toString();
        URL_ADS_CREATED = (new StringBuilder(commonApiURL)).append("/api/ads/createad").toString();
        URL_GET_ADS_DETAIL_WITH_STATUS = (new StringBuilder(commonApiURL)).append("/api/ads/getaddsdetail").toString();
        URL_GET_USERINFO = (new StringBuilder(commonApiURL)).append("/api/user/userinfo").toString();
        URL_GET_ALL_RUNNING_ADS = (new StringBuilder(commonApiURL)).append("/api/auth/getallrunningadds").toString();
        URL_GET_INFO_WITH_BATCH_ID = (new StringBuilder(commonApiURL)).append("/api/adsuser/getaddsinfo").toString();
        URL_ADD_TO_FAVOURITE = (new StringBuilder(commonApiURL)).append("/api/adsuser/setfavourite").toString();
        URL_CHECK_FOR_FAVOURITE = (new StringBuilder(commonApiURL)).append("/api/adsuser/checkfav").toString();
        URL_GET_SHARE_LINK = (new StringBuilder(commonApiURL)).append("/api/auth/sharedetail").toString();
        URL_GET_NOTIFICATIONS = (new StringBuilder(commonApiURL)).append("/api/purchase/plannotification").toString();

        URL_GET_FAVOURITES = (new StringBuilder(commonApiURL)).append("/api/adsuser/getfavourites").toString().trim();

        URL_CHECK_NOTIFICATION = (new StringBuilder(commonApiURL)).append("/api/pushnotification/checkstatus").toString();
        URL_SAVE_NOTIFICATION = (new StringBuilder(commonApiURL)).append("/api/pushnotification/savestatus").toString();
        URL_EDIT_ADS = (new StringBuilder(commonApiURL)).append("/api/ads/updated").toString();
        URL_BUSINESS_DETAIL = (new StringBuilder(commonApiURL)).append("/api/business/businessdetails").toString();
        URL_GET_AD_INFO_FOR_LOGGED_USER = (new StringBuilder(commonApiURL)).append("/api/ads/getaddinformation").toString();
        URL_GET_SOCIAL_INFO = (new StringBuilder(commonApiURL)).append("/api/auth/businesssocialdetails").toString();
        URL_PAYMENT_API = (new StringBuilder(commonApiURL)).append("/api/payment").toString();   // http://112.196.5.114/asa/api/payment
        URL_GET_EXPIRED_ADS = (new StringBuilder(commonApiURL)).append("/api/ads/getexpirydetail").toString();
        URL_GET_NEW_PURCHSED_ID = (new StringBuilder(commonApiURL)).append("/api/purchase/getnewpurchaseid").toString();

        URL_TERMS_AND_CONDITIONS = (new StringBuilder(commonApiURL)).append("/api/auth/termscondition").toString();
        URL_LOGOUT = (new StringBuilder(commonApiURL)).append("/api/auth/logout").toString();

        //    FROM 30 Mar 2018
        URL_SAVE_BUSINESS_DETAILS = (new StringBuilder(commonApiURL)).append("/api/auth/savebusiness").toString();
        URL_GET_PLAN_DEATAIL_WITH_PLACEID = (new StringBuilder(commonApiURL)).append("/api/auth/getaddsdetailsbyplaceid").toString();
        URL_HOW_TO_REGISTER = (new StringBuilder(commonApiURL)).append("/api/auth/registerdetail").toString();
    }

    // http://112.196.5.114/asa/api/business/businessdetails

}










