package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.modules_.login_.PreLoginActivity;
import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

/**
 * Created by Snow-Dell-05 on 6/3/2017.
 */

public class FireApiGoForLogout extends AsyncTask<String, Void, JSONObject> {
    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String userid, token;

    public FireApiGoForLogout(Activity context) {
        this.context = context;
        this.jsonParser = new JSONParser();
        this.uihandle = new UiHandleMethods(context);
        this.mSession = new SessionCityOculus(context);

        userid = mSession.getLoggedId();
        token = mSession.getToken();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("Logging out...");
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
                Log.d("logut", response.toString());
                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        uihandle.showToast(response.optString(("message")));
                        mSession.logoutUser();
                        uihandle.explicitIntentFromLeft(PreLoginActivity.class);
                        context.finish();

                    } else {
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
}
