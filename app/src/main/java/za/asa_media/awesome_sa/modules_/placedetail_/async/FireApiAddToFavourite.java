package za.asa_media.awesome_sa.modules_.placedetail_.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/31/2017.
 */

public class FireApiAddToFavourite extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;

    /*
        placeid:ChIJ2S7cDBzsDzkRCCVJrI3FdPg
        deviceid:lskdvnho2398ry689wufgc8237tg
   */

    public FireApiAddToFavourite(Activity context) {
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

            params.put("distance", url[3]);
            params.put("rating", url[4]);
            params.put("address", url[5]);
            params.put("place_name", url[6]);
            params.put("latitude",url[7] );
            params.put("longitude", url[8]);

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
        try {
            if (response != null) {
                Log.d("info", response.toString());
                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        uihandle.showToast(response.optString(("message")));
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