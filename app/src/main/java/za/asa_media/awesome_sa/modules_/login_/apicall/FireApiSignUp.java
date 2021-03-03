package za.asa_media.awesome_sa.modules_.login_.apicall;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.modules_.login_.SignUpActivityPart2;
import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListKeys;

/**
 * Created by Snow-Dell-05 on 5/18/2017.
 */

public class FireApiSignUp extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;

    public FireApiSignUp(Activity context) {

        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While user is being created...");
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put(URLListKeys.SIGNUP_FIRSTNAME, url[1]);
            params.put(URLListKeys.SIGNUP_LASTNAME, url[2]);
            params.put(URLListKeys.SIGNUP_EMAIL, url[3]);
            params.put(URLListKeys.SIGNUP_PASSWORD, url[4]);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {

            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }

    /*/*
        {
      "status": true,
      "message": "User Created Successfull",
      "token": "9f9239b7e8e92dc20e13e473c2ae9949",
      "data": {
        "id": "32",
        "firstname": "johny",
        "lastname": "doe",
        "email": "johndoe112@gmail.com",
        "phone": null,
        "dob": null,
        "city": null,
        "country": null,
        "lat": null,
        "long": null,
        "status": "active",
        "created_on": "2017-05-22 09:46:29"
      }
    }
        */
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
                        mSession.setToken(response.optString("token"));
                        if (response.has("data")) {
                            JSONObject jObj = response.optJSONObject("data");
                            mSession.setIsLogged(false);
                            mSession.setLoggedStatus(jObj.optString("status"));
                            mSession.setLoggedId(jObj.optString("id"));
                            mSession.setLoggedEmail(jObj.optString("email"));
                            mSession.setLoggedFirstname(jObj.optString("firstname"));
                            mSession.setLoggedLastname(jObj.optString("lastname"));
                            mSession.setLoggedCreatedOn(jObj.optString("created_on"));
                        }

                        // go for singn up part 2   // BasicInfo
                        uihandle.explicitIntent(SignUpActivityPart2.class);
                        context.finish();
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