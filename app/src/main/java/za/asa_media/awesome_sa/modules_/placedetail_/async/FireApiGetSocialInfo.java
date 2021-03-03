package za.asa_media.awesome_sa.modules_.placedetail_.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 6/24/2017.
 */

public class FireApiGetSocialInfo extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;


    public FireApiGetSocialInfo(Activity context) {
        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
    }


    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("placeid", url[1]);
            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {
            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;
    }


}