package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/30/2017.
 */

public class FireApiGetUserInfo extends AsyncTask<String, Void, JSONObject> {
    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String userid, token;

    public FireApiGetUserInfo(Activity context) {
        this.context = context;
        this.jsonParser = new JSONParser();
        this.uihandle = new UiHandleMethods(context);
        this.mSession = new SessionCityOculus(context);
        userid = mSession.getLoggedId();
        token = mSession.getToken();


    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();

            params.put(URLListKeys.USER_ID, userid);
            params.put(URLListKeys.USER_TOKEN, token);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

           // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }


}
