package za.asa_media.awesome_sa.support.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import za.asa_media.awesome_sa.support.url_keys.PreferencesKeys;

/**
 * Created by Snow-Dell-05 on 5/18/2017.
 */

public class SessionCityOculus {

    private static final String PREF_NAME = "awesome_africa";
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private int PRIVATE_MODE;
    private Context _context;
    private int securityQuestionLength = 0;


    public SessionCityOculus(Context context) {

        PRIVATE_MODE = 0;
        _context = context;

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public static String getSupportId() {
        return "";
    }

    public void setIsLogged(boolean flag) {
        editor.putBoolean(PreferencesKeys.IS_LOGIN, flag);
        editor.commit();

    }

    public boolean isLogged() {
        return pref.getBoolean(PreferencesKeys.IS_LOGIN, false);
    }

    public void setIsProfileFilled(boolean flag) {
        editor.putBoolean("is_profile_filled", flag);
        editor.commit();

    }

    public boolean isProfileFilled() {
        return pref.getBoolean("is_profile_filled", false);
    }


    ////////// .......///////......////////........////////.........///////.......////////

    /******
     * Getter methods
     *****/
    /******
     * Setter methods
     *****/
    public Boolean getNavIndex() {
        return pref.getBoolean("nav_index_", false);
    }

    public void setNavIndex(boolean nav_index) {
        editor.putBoolean("nav_index_", nav_index);
        editor.commit();
    }

    //.......
    public Boolean getCategoryFlagHavingSubcategory() {
        return pref.getBoolean("flag_category_having_sub_category", false);
    }

    public void setCategoryFlagHavingSubcategory(boolean flag_from_detail_) {
        editor.putBoolean("flag_category_having_sub_category", flag_from_detail_);
        editor.commit();
    }


    //........


//...........//........//.................//...........//..........//............


    public Boolean getFlagFromDetail() {
        return pref.getBoolean("flag_from_detail_", false);
    }

    public void setFlagFromDetail(boolean flag_from_detail_) {
        editor.putBoolean("flag_from_detail_", flag_from_detail_);
        editor.commit();
    }


    public String getLatitudeTabbed() {
        return pref.getString("latitude_tabbed", "");
    }

    public void setlatitudeTabbed(String latitude_tabbed) {
        editor.putString("latitude_tabbed", latitude_tabbed);
        editor.commit();
    }

    public String getLongitudeTabbed() {
        return pref.getString("longitude_tabbed", "");
    }

    public void setLongitudeTabbed(String userToken) {
        editor.putString("longitude_tabbed", userToken);
        editor.commit();
    }


    public String getCategoryTabbed() {
        return pref.getString("category_tabbed", "");
    }

    public void setCategoryTabbed(String userToken) {
        editor.putString("category_tabbed", userToken);
        editor.commit();
    }

    public String getBusinessNameTabbed() {
        return pref.getString("business_name_searched", "");
    }

    public void setBusinessNameTabbed(String userToken) {
        editor.putString("business_name_searched", userToken);
        editor.commit();
    }


    public String getCountryTabbed() {
        return pref.getString("Country_tabbed", "");
    }

    public void setCountryTabbed(String userToken) {
        editor.putString("Country_tabbed", userToken);
        editor.commit();
    }

    public String getCityTabbed() {
        return pref.getString("city_tabbed", "");
    }

    public void setCityTabbed(String city_tabbed) {
        editor.putString("city_tabbed", city_tabbed);
        editor.commit();
    }

    public String getStateTabbed() {
        return pref.getString("state_tabbed", "");
    }

    public void setStateTabbed(String userToken) {
        editor.putString("state_tabbed", userToken);
        editor.commit();
    }


    public String getPlaceIdTabbed() {
        return pref.getString("tabbed_place_id", "");
    }

    public void setPlaceIdTabbed(String b_lat) {
        editor.putString("tabbed_place_id", b_lat);
        editor.commit();
    }

    public String getAddressTabbed() {
        return pref.getString("b_addresstabbed", "");
    }

    public void setAddressTabbed(String b_address) {
        editor.putString("b_addresstabbed", b_address);
        editor.commit();
    }

    public String getPhoneTabbed() {
        return pref.getString("tabbed_phone_number", "");
    }

    public void setPhoneTabbed(String b_phone_number) {
        editor.putString("tabbed_phone_number", b_phone_number);
        editor.commit();
    }

    public String getWebsiteLinkTabbed() {
        return pref.getString("tabbed_website_link", "");
    }

    public void setWebsiteLinkTabbed(String b_lat) {
        editor.putString("tabbed_website_link", b_lat);
        editor.commit();
    }

    public String getPinCodeTabbed() {
        return pref.getString("tabbed_pin_code", "");
    }

    public void setPinCodeTabbed(String b_lat) {
        editor.putString("tabbed_pin_code", b_lat);
        editor.commit();
    }


    //......../.....///.....................//...................//..............
    public String getToken() {
        return pref.getString(PreferencesKeys.USER_TOKEN, null);
    }

    public void setToken(String userToken) {
        editor.putString(PreferencesKeys.USER_TOKEN, userToken);
        editor.commit();
    }

    public String getLoggedStatus() {
        return pref.getString(PreferencesKeys.LOGGED_IN_STATUS, null);
    }

    public void setLoggedStatus(String userStatus) {
        editor.putString(PreferencesKeys.LOGGED_IN_STATUS, userStatus);
        editor.commit();
    }

    public String getLoggedId() {
        return pref.getString(PreferencesKeys.LOGGED_IN_USER_ID, null);
    }

    public void setLoggedId(String userId) {
        editor.putString(PreferencesKeys.LOGGED_IN_USER_ID, userId);
        editor.commit();
    }

    public String getLoggedFirstname() {
        return pref.getString(PreferencesKeys.LOGGED_IN_FIRSTNAME, null);
    }

    public void setLoggedFirstname(String firstname) {
        editor.putString(PreferencesKeys.LOGGED_IN_FIRSTNAME, firstname);
        editor.commit();
    }

    public String getLoggedLastname() {
        return pref.getString(PreferencesKeys.LOGGED_IN_LASTNAME, null);
    }

    public void setLoggedLastname(String lastname) {
        editor.putString(PreferencesKeys.LOGGED_IN_LASTNAME, lastname);
        editor.commit();
    }

    public String getLoggedEmail() {
        return pref.getString(PreferencesKeys.LOGGED_IN_EMAIL, null);
    }

    public void setLoggedEmail(String emailId) {
        editor.putString(PreferencesKeys.LOGGED_IN_EMAIL, emailId);
        editor.commit();
    }

    public String getLoggedCreatedOn() {
        return pref.getString(PreferencesKeys.LOGGED_IN_CREATED_ON, null);
    }

    public void setLoggedCreatedOn(String createdTime) {
        editor.putString(PreferencesKeys.LOGGED_IN_CREATED_ON, createdTime);
        editor.commit();
    }


    public String getLoggedCity() {
        return pref.getString("loged_city", null);
    }

    public void setLoggedCity(String loged_city) {
        editor.putString("loged_city", loged_city);
        editor.commit();
    }

    public String getLoggedCountry() {
        return pref.getString("loged_country", null);
    }

    public void setLoggedCountry(String loged_country) {
        editor.putString("loged_country", loged_country);
        editor.commit();
    }


    public String getLoggedLatitude() {
        return pref.getString("loged_lat", null);
    }

    public void setLoggedLatitude(String loged_lat) {
        editor.putString("loged_lat", loged_lat);
        editor.commit();
    }

    public String getLoggedLong() {
        return pref.getString("loged_long", null);
    }

    public void setLoggedLong(String loged_long) {
        editor.putString("loged_long", loged_long);
        editor.commit();
    }

    public String getLoggedDob() {
        return pref.getString("loged_dob", null);
    }

    public void setLoggedDob(String loged_dob) {
        editor.putString("loged_dob", loged_dob);
        editor.commit();
    }

    public String getLoggedPhone() {
        return pref.getString("loged_phone", null);
    }

    public void setLoggedPhone(String loggedPhone) {
        editor.putString("loged_phone", loggedPhone);
        editor.commit();
    }



    public String getPlaceId() {
        return pref.getString(PreferencesKeys.PLACE_ID, null);
    }

    public void setPlaceId(String place_id) {
        editor.putString(PreferencesKeys.PLACE_ID, place_id);
        editor.commit();
    }


    public String getPurchaseId() {
        return pref.getString("purchase_id", null);
    }

    public void setPurchaseId(String purchaseId) {
        editor.putString("purchase_id", purchaseId);
        editor.commit();
    }

    public String getPaymentStatus() {
        return pref.getString("payment_status", null);
    }

    public void setPaymentStatus(String paymentStatus) {
        editor.putString("payment_status", paymentStatus);
        editor.commit();
    }

    public String getPaymentTxnId() {
        return pref.getString("txn_id", null);
    }

    public void setPaymentTxnId(String txnId) {
        editor.putString("txn_id", txnId);
        editor.commit();
    }

    public boolean getAdsDirectTag() {
        return pref.getBoolean("direct_ads_tag", false);
    }

    public void setAdsDirectTag(boolean value) {
        editor.putBoolean("direct_ads_tag", value);
        editor.commit();
    }


    public boolean getPaymentStatusCancelOrNot() {
        return pref.getBoolean("payment_status_cancel_or_not", false);
    }

    public void setPaymentStatusCancelOrNot(boolean value) {
        editor.putBoolean("payment_status_cancel_or_not", value);
        editor.commit();
    }


    public String getCAdPurchaseId() {
        return pref.getString("c_purchase_id", null);
    }

    // set credential for create add required to save
    public void setCAdPurchaseId(String purchaseId) {
        editor.putString("c_purchase_id", purchaseId);
        editor.commit();
    }

    public String getCAdPlaceId() {
        return pref.getString("c_place_id", null);
    }

    public void setCAdPlaceId(String cplaceId) {
        editor.putString("c_place_id", cplaceId);
        editor.commit();
    }

    public String getCAdPlanId() {
        return pref.getString("c_plan_id", null);
    }

    public void setCAdPlanId(String planId) {
        editor.putString("c_plan_id", planId);
        editor.commit();
    }

    public String getCAdBatchId() {
        return pref.getString("c_batch_id", null);
    }

    public void setCAdBatchId(String cbatchId) {
        editor.putString("c_batch_id", cbatchId);
        editor.commit();
    }

    public String getCAdPlaceName() {
        return pref.getString("c_placename", null);
    }

    public void setCAdPlaceName(String cPlacename) {
        editor.putString("c_placename", cPlacename);
        editor.commit();
    }

    public String getCAdExpiryDate() {
        return pref.getString("c_expiry_date", null);
    }

    public void setCAdExpiryDate(String expiryDate) {
        editor.putString("c_expiry_date", expiryDate);
        editor.commit();
    }

    public String getShareLinks() {
        return pref.getString("shareLinks", "");
    }

    public void setShareLinks(String shareLinks) {

        editor.putString("shareLinks", shareLinks);
        editor.commit();
    }

    public String getDeviceToken() {
        return pref.getString("m_device_token", "");
    }

    public void setDeviceToken(String deviceToken) {
        editor.putString("m_device_token", deviceToken);
        editor.commit();
    }

    public String getUserImageUrl() {
        return pref.getString("image_url_user", "");
    }

    public void setUserImageUrl(String userImageUrl) {
        editor.putString("image_url_user", userImageUrl);
        editor.commit();
    }

    public Boolean getNotificationEnable() {
        return pref.getBoolean("notification_check", false);
    }

    public void setNotificationEnable(boolean notificationEnable) {
        editor.putBoolean("notification_check", notificationEnable);
        editor.commit();
    }


    public String getUserCurrentLocation() {
        return pref.getString("user_current_location_", "");
    }

    public void setUserCurrentLocation(String userLocation) {
        editor.putString("user_current_location_", userLocation);
        editor.commit();
    }
    // .........//..........//Save detail for the business...//..........//.........//.........//..........//...........//............


    public String getBLat() {
        return pref.getString("b_lat", "");
    }

    public void setBLat(String b_lat) {
        editor.putString("b_lat", b_lat);
        editor.commit();
    }

    public String getBLon() {
        return pref.getString("b_lon", "");
    }

    public void setBLon(String b_lat) {
        editor.putString("b_lon", b_lat);
        editor.commit();
    }

    public String getBCategory() {
        return pref.getString("b_category", "");
    }

    public void setBCategory(String b_lat) {
        editor.putString("b_category", b_lat);
        editor.commit();
    }

    public String getBName() {
        return pref.getString("b_name", "");
    }

    public void setBName(String b_name) {
        editor.putString("b_name", b_name);
        editor.commit();
    }


    public String getBPlaceId() {
        return pref.getString("b_place_id", "");
    }

    public void setBPlaceId(String b_lat) {
        editor.putString("b_place_id", b_lat);
        editor.commit();
    }

    public String getBAddress() {
        return pref.getString("b_address", "");
    }

    public void setBAddress(String b_address) {
        editor.putString("b_address", b_address);
        editor.commit();
    }

    public String getBPhone() {
        return pref.getString("b_phone_number", "");
    }

    public void setBPhone(String b_phone_number) {
        editor.putString("b_phone_number", b_phone_number);
        editor.commit();
    }

    public String getBWebsiteLink() {
        return pref.getString("b_website_link", "");
    }

    public void setBWebsiteLink(String b_lat) {
        editor.putString("b_website_link", b_lat);
        editor.commit();
    }


    public void logoutUser() {

        editor.clear();
        editor.commit();

        //   new UpdateDatabaseAndLogout(this.getEmailId() ,this.getUserPass() ,this.getUserType()).execute(URLListKeywords.URL_LOGOUT);
    }

    public void showToast(String string) {
        Toast.makeText(_context, string, Toast.LENGTH_SHORT).show();

    }


}
   /* *//*    Gcm methods  starts here    *//*
     public void setGcmFlag(boolean flag) {
        editor.putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, flag);
        editor.commit();
    }
    public void setGcmToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }
    public Boolean getGcmFlag() {
        return pref.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
    }
    public String getGcmToken() {
        return pref.getString("token", null);
    }



    *//*   Gcm methods Ends Here   *//*
*/