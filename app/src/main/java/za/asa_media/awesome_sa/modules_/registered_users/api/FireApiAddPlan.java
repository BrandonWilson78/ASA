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

public class FireApiAddPlan extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String userid, token,placeId;

    public FireApiAddPlan(Activity context) {

        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);

        userid = mSession.getLoggedId();
        token = mSession.getToken();
        placeId=mSession.getPlaceId();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While plan is being submitted...");
    }

   /*
    token:3e431daa68ed3fd4c8a9f6aabc934a5b
    userid:3
    placeid:asvasvasv2352352345
    planid:2
    price:25
    batchid:3
    {
      "status": true,
      "message": "Payment Initialize.",
      "purchaseid": 2

    }

   */

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("token", token);
            params.put("userid", userid);
            params.put("placeid", placeId);
            params.put("planid", url[1]);
            params.put("price", url[2]);
            params.put("batchid", url[3]);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }


    @Override
    protected void onPostExecute(JSONObject response) {
        super.onPostExecute(response);
       uihandle.stopProgressDialog();
    }
}