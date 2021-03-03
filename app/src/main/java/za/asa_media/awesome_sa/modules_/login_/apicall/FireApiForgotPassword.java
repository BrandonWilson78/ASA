package za.asa_media.awesome_sa.modules_.login_.apicall;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.modules_.login_.ForgotPasswordConfirm;
import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/18/2017.
 */

public class FireApiForgotPassword extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;


    public FireApiForgotPassword(Activity context) {

        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);


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
            params.put(URLListKeys.FORGOT_PASSWORD_EMAIL, url[1]);
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
                Log.d("login", response.toString());
                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        uihandle.showToast(response.optString(("message")));

                        //sent the control to next proceeding screen
                        uihandle.explicitIntent(ForgotPasswordConfirm.class);
                        context.finish();
                        /// uihandle.explicitIntentFromLeft(SignInActivity.class);
                        //context.finish();
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