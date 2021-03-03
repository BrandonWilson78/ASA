package za.asa_media.awesome_sa.modules_.login_.apicall;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

/**
 * Created by Snow-Dell-05 on 8/10/2017.
 */

public class FireApiGenerateTokenAsync extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private ResponseCallback mCallback;

    public FireApiGenerateTokenAsync(Activity mContext) {
        this.context = mContext;

        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);
        // call back initialisation
        mCallback = (ResponseCallback) mContext;
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            HashMap<String, String> params = new HashMap<>();
            params.put("test", "");
            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            Log.d("question", e.toString());
        }
        return jsonObject;

    }

    @Override
    protected void onPostExecute(JSONObject response) {
        super.onPostExecute(response);
        try {
            mCallback.haveResponse(response);

        } catch (Exception e) {
            uihandle.showToast(e.toString());

        }

    }
}