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
 * Created by Snow-Dell-05 on 6/2/2017.
 */

public class FireApiSetting extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String deviceid;
    private String notification_code;

    public FireApiSetting(Activity context) {

        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);
        deviceid = mSession.getDeviceToken();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While request is being sent...");
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("deviceid", deviceid);
            params.put("notification_code", url[1]);
            notification_code = url[1];
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
            uihandle.stopProgressDialog();
            if (response != null) {
                Log.d("info", response.toString());
                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                   //     uihandle.showToast(response.optString(("message")));

                 // setting notification code so that no flikering comes during check
                        if (notification_code.equals("1")) {
                            mSession.setNotificationEnable(true);
                        }
                        if (notification_code.equals("0")) {
                            mSession.setNotificationEnable(false);
                        }

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