package za.asa_media.awesome_sa.modules_.FireApi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.login_.SignInActivity;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.modules_.registered_users.StaticValuesEditProfile;
import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.MultipartFormRequest;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

/**
 * Created by Snow-Dell-05 on 5/22/2017.
 */

public class FireApiAddUserInfo extends AsyncTask<String, Void, JSONObject> {

    private StringBuilder result;
    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private JSONObject jObj;
    private String id, birth, number, city, country, lat, lon, token, image, mEmail, mFirstName, mLastName;
    private SessionCityOculus mSession;
    private String mFlag = "";


    public FireApiAddUserInfo(Activity context, String mFirstName, String mLastName, String image, String loggedId, String birth, String number, String city, String country, String lat, String lon, String token) {

        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);

        this.mFirstName = mFirstName;
        this.mLastName = mLastName;


        this.image = image;
        this.id = loggedId;
        this.birth = birth;
        this.number = number;
        this.city = city;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.token = token;
        this.mSession = new SessionCityOculus(context);
        this.mEmail = mSession.getLoggedEmail();


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While info is being added...");
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            mFlag = url[1];
            jsonObject = updateUserProfile(url[0], image);
        } catch (Exception e) {
            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }

    @Override
    protected void onPostExecute(JSONObject response) {
        super.onPostExecute(response);
        try {
            uihandle.stopProgressDialog();
            if (response != null) {
                Log.d("info", response.toString());

                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        uihandle.showToast(response.optString(("message")));
                        if (mFlag.equals("edit")) {

                            Intent in = new Intent(context, LoggedInUserDashboard.class);
                            in.putExtra("loadFragment", "load");
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(in);

                            context.finish();
                            context.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                        } else {
                            mSession.setIsProfileFilled(true);
                            uihandle.explicitIntentFromLeft(SignInActivity.class);
                            context.finish();
                        }
                    } else {
                        mSession.setIsLogged(false);
                        uihandle.showToast(response.optString(("message")));
                    }
                } else {
                    uihandle.showToast("Something went wrong!");
                }
            }
        } catch (Exception e) {
            uihandle.showToast(e.toString());
        }
    }

    public JSONObject updateUserProfile(String requestURL, String fileName) {
        String charset = "UTF-8";
        try {
            MultipartFormRequest multipart = new MultipartFormRequest(requestURL, charset);

            multipart.addFormField("firstname", mFirstName);
            multipart.addFormField("lastname", mLastName);

            multipart.addFormField(URLListKeys.ADD_USER_INFO_USER_ID, id);
            multipart.addFormField(URLListKeys.ADD_USER_INFO_DOB, birth);
            multipart.addFormField(URLListKeys.ADD_USER_INFO_PHONE, number);
            multipart.addFormField(URLListKeys.ADD_USER_INFO_CITY, city);
            multipart.addFormField(URLListKeys.ADD_USER_INFO_COUNTRY, country);
            multipart.addFormField(URLListKeys.ADD_USER_INFO_LAT, lat);
            multipart.addFormField(URLListKeys.ADD_USER_INFO_LONG, lon);
            multipart.addFormField(URLListKeys.USER_TOKEN, token);

            multipart.addFormField("placeid", StaticValuesEditProfile.businessid);    // 197
            multipart.addFormField("email", mEmail);
            multipart.addFormField("businessid", StaticValuesEditProfile.id);
            multipart.addFormField("business_name", StaticValuesEditProfile.business_name);
            multipart.addFormField("category", StaticValuesEditProfile.category);


            multipart.addFormField("business_country", StaticValuesEditProfile.country);
            multipart.addFormField("business_address", StaticValuesEditProfile.address);
            multipart.addFormField("business_latitude", StaticValuesEditProfile.latitude);
            multipart.addFormField("business_longitude", StaticValuesEditProfile.longitude);
            multipart.addFormField("business_city", StaticValuesEditProfile.city);
            multipart.addFormField("business_state", StaticValuesEditProfile.state);
            multipart.addFormField("business_pincode", StaticValuesEditProfile.pincode);
            multipart.addFormField("business_phone", StaticValuesEditProfile.number);
            multipart.addFormField("website", StaticValuesEditProfile.website);

            multipart.addFormField("facebook", StaticValuesEditProfile.facebook);
            multipart.addFormField("google", StaticValuesEditProfile.google);
            multipart.addFormField("instagram", StaticValuesEditProfile.instagram);
            multipart.addFormField("youtube", StaticValuesEditProfile.youtube);


            if (!fileName.equals("")) {
                multipart.addFilePart(URLListKeys.ADD_USER_INFO_IMAGE, new File(fileName));
            } else {
                multipart.addFormField(URLListKeys.ADD_USER_INFO_IMAGE, "");
            }

            //  multipart.addFilePart("myFile2", new File(selectedPath4));

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");
            result = new StringBuilder();
            for (String line : response) {

                result.append(line);
                System.out.println(line);
                Log.d("r", line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;

    }


}