package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 6/13/2017.
 */

public class FireApiFetchBusinessDetail extends AsyncTask<String, Void, JSONObject> {


    private JSONParser jsonParser;
    private SessionCityOculus mSession;
    private String userid, token, placeid;

    public FireApiFetchBusinessDetail(Activity context) {
         this.jsonParser = new JSONParser();
        this.mSession = new SessionCityOculus(context);
        this.userid = mSession.getLoggedId();
        this.token = mSession.getToken();
        this.placeid = mSession.getCAdPlaceId();

    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();

            params.put("userid", userid);
            params.put("token", token);
            params.put("placeid", placeid);
            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {
            Log.d("error", e.toString());
        }
        return jsonObject;

    }

}
