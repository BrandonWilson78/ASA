package za.asa_media.awesome_sa.modules_.placedetail_.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 6/1/2017.
 */

public class FireApiGetShareLink extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;


    public FireApiGetShareLink(Activity context) {

        this.context = context;
        this.jsonParser = new JSONParser();
        this.uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("Loading...");
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("test", "test");
            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            uihandle.stopProgressDialog();
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

                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        if (response.has("data")) {
                            JSONArray jArray = response.optJSONArray("data");
                            if (jArray != null) {
                                JSONObject jObj = jArray.optJSONObject(0);
                                String details = jObj.optString("detail");
                                mSession.setShareLinks(details);

                            }

                        } else {
                            uihandle.showToast("Something went wrong");
                        }

                    } else {
                        uihandle.showToast(response.optString("message"));
                    }
                }
            }

        } catch (Exception e) {
            uihandle.showToast(e.toString());

        }


    }
}
