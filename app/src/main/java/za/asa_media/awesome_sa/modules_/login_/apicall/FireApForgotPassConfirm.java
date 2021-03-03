package za.asa_media.awesome_sa.modules_.login_.apicall;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.modules_.login_.SignInActivity;
import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/19/2017.
 */

public class FireApForgotPassConfirm extends AsyncTask<String, Void, JSONObject> {
    private UiHandleMethods mUiHandleMethods;
    private Activity mContext;
    private JSONParser mJsonParser;

    public FireApForgotPassConfirm(Activity mContext) {
        this.mContext = mContext;
        mUiHandleMethods = new UiHandleMethods(mContext);
        mJsonParser = new JSONParser();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mUiHandleMethods.startProgress("Updating Password...");

    }

    @Override
    protected JSONObject doInBackground(String... values) {
        JSONObject mJsonObject = null;
        HashMap<String, String> postParams = new HashMap<>();
        postParams.put(URLListKeys.NEW_PASSWORD, values[1]);
        postParams.put(URLListKeys.CONFIRM_PASSWORD, values[2]);
        postParams.put(URLListKeys.FORGOT_PASSWORD_CODE, values[3]);
        try {
            mJsonObject = mJsonParser.makeHttpRequest(values[0], "POST", postParams);
        } catch (Exception e) {
            Log.e("ForgotPAssConfirm", e.toString());
        }

        return mJsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        mUiHandleMethods.stopProgressDialog();

        try {
            if (jsonObject != null) {
                if (jsonObject.has("status")) {
                    if (jsonObject.optBoolean("status")) {
                        mUiHandleMethods.showToast(jsonObject.optString("message"));
                        mUiHandleMethods.explicitIntentFromLeft(SignInActivity.class);
                        mContext.finish();
                    } else {
                        mUiHandleMethods.showToast(jsonObject.optString("message"));
                    }
                } else {
                    mUiHandleMethods.showToast("Something Went Wrong!");
                }
            }
        } catch (Exception e) {
            mUiHandleMethods.showToast(e.toString());
        }
    }
}
