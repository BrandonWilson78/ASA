package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/24/2017.
 */

public class FireApiChoosePlan extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String userid, token;

    public FireApiChoosePlan(Activity context) {
        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);
        userid = mSession.getLoggedId();
        token = mSession.getToken();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While plans are being fetched...");

    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();

            params.put("userid", userid);
            params.put("token", token);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    uihandle.stopProgressDialog();
    }
}
