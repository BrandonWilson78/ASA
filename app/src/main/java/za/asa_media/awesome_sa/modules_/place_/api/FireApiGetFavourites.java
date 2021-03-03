package za.asa_media.awesome_sa.modules_.place_.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParserJson;
import za.asa_media.awesome_sa.support.MultipartFormRequest;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Snow-Dell-05 on 6/2/2017.
 */

public class FireApiGetFavourites extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParserJson jsonParser;
    private ProgressDialog mDialog;
    private String mDeviceId;
    private StringBuilder result;
    private JSONObject jObj;
    private SessionCityOculus mSession;

    public FireApiGetFavourites(Activity context) {

        this.context = context;
        this.jsonParser = new JSONParserJson();
        mSession = new SessionCityOculus(context);
        mDeviceId = mSession.getDeviceToken();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = ProgressDialog.show(context, "Please wait", "While fetching favourite places", false, true);

    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            // mDeviceId = url[1].trim();
            HashMap<String, String> params = new HashMap<>();
            params.put("deviceid", mDeviceId);
            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {
            Log.d("TAG", e.toString());
            if (mDialog.isShowing()) {
                mDialog.hide();
            }
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (mDialog.isShowing()) {
            mDialog.hide();
        }
    }


    public JSONObject getFavouritePlaces(String requestURL) {
        String charset = "UTF-8";
        try {
            MultipartFormRequest multipart = new MultipartFormRequest(requestURL, charset);
            multipart.addFormField("deviceid", mDeviceId);

            //  multipart.addFilePart("myFile2", new File(selectedPath4));
            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");
            result = new StringBuilder();
            for (String line : response) {

                result.append(line);
                System.out.println(line);
                Log.d("r", line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;

    }


}
