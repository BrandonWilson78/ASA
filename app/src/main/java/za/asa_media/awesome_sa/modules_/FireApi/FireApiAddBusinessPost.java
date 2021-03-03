package za.asa_media.awesome_sa.modules_.FireApi;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

/**
 * Created by Snow-Dell-05 on 5/22/2017.
 */

public class FireApiAddBusinessPost extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;

    public FireApiAddBusinessPost(Activity context) {
        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
    }


    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put(URLListKeys.USER_TOKEN, url[1]);
            params.put(URLListKeys.ADD_USER_INFO_USER_ID, url[2]);
            params.put(URLListKeys.ADD_BUSINESS_ID, url[3]);
            params.put(URLListKeys.ADD_BUSINESS_NAME, url[4]);
            params.put(URLListKeys.ADD_BUSINESS_CATEGORY, url[5]);
            params.put(URLListKeys.ADD_BUSINESS_COUNTRY, url[6]);
            params.put(URLListKeys.ADD_BUSINESS_ADDRESS, url[7]);
            params.put(URLListKeys.ADD_BUSINESS_CITY, url[8]);
            params.put(URLListKeys.ADD_BUSINESS_STATE, url[9]);
            params.put(URLListKeys.ADD_BUSINESS_PINCODE, url[10]);
            params.put(URLListKeys.ADD_BUSINESS_PHONE, url[11]);
            params.put(URLListKeys.ADD_BUSINESS_WEBSITE_URL, url[12]);
        /*  mFacebook, mTwitter, mGooglePlus, mInstagram, mLinkedin,
                                mYoutube, mVimeo, mSnapchat */
            params.put(URLListKeys.ADD_FACEBOOK, url[13]);
            params.put(URLListKeys.ADD_TWITTER, url[14]);
            params.put(URLListKeys.ADD_GOOGLE, url[15]);
            params.put(URLListKeys.ADD_INSTAGRAM, url[16]);
            params.put(URLListKeys.ADD_LINKEDIN, url[17]);
            params.put(URLListKeys.ADD_YOUTUBE, url[18]);
            params.put(URLListKeys.ADD_VIMEO, url[19]);
            params.put(URLListKeys.ADD_SNAPCHAT, url[20]);

            params.put("latitude", url[21]);
            params.put("longitude", url[22]);
            params.put("agree", url[23]);


            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;
    }


}