package za.asa_media.awesome_sa.modules_.place_.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/30/2017.
 */

public class FireApiGetAllRunningAds extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;


    public FireApiGetAllRunningAds(Activity context) {

        this.context = context;
        this.jsonParser = new JSONParser();
        this.uihandle = new UiHandleMethods(context);
        this.mSession = new SessionCityOculus(context);

    }


    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("test", "test");
            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {
            Log.d("question", e.toString());
        }
        return jsonObject;

    }

}
