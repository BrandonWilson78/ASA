package za.asa_media.awesome_sa.modules_.login_.apicall;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

/**
 * Created by Snow-Dell-05 on 5/18/2017.
 */

public class FireApiLogin extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;

    public FireApiLogin(Activity context) {
        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);

    }



    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();

            params.put(URLListKeys.LOGIN_EMAIL, url[1]);
            params.put(URLListKeys.LOGIN_PASSWORD, url[2]);
            params.put(URLListKeys.USER_TOKEN, url[3]);

            Log.d("dd", url[3]);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }


}



/*
            Map<String, String> hMap = new HashMap<>();
            hMap.put(, web_token.trim());
            hMap.put(, mEditTextEmail.getText().toString().trim().replace(" ",""));
            hMap.put(, mEditTextPassword.getText().toString().trim().replace(" ",""));
            // fire api
            new FireApiGenerateToken(mContext).requestPostJson(URLListApis.URL_LOGIN, hMap, "Trying to login...");
 */