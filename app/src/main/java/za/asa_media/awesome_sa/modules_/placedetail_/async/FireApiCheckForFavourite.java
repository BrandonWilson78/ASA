package za.asa_media.awesome_sa.modules_.placedetail_.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/31/2017.
 */

public class FireApiCheckForFavourite extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;


    public FireApiCheckForFavourite(Activity context) {
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
            params.put("deviceid", url[2]);
            Log.d("ids",url[1]+"\n"+url[2]);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {
            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;
    }


}