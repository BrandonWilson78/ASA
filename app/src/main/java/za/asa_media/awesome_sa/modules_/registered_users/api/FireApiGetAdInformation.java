package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParserJson;
import za.asa_media.awesome_sa.support.MultipartFormRequest;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Snow-Dell-05 on 6/13/2017.
 */

public class FireApiGetAdInformation extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParserJson jsonParser;
    private SessionCityOculus mSession;

    private String batchId, placeid, userid, token;
    private StringBuilder result;
    private JSONObject jObj = null;

    public FireApiGetAdInformation(Activity context) {

        this.context = context;
        this.jsonParser = new JSONParserJson();
        this.mSession = new SessionCityOculus(context);

        batchId = mSession.getCAdBatchId();
        placeid = mSession.getCAdPlaceId();
        userid = mSession.getLoggedId();
        token = mSession.getToken();

    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            jsonObject = getAdInformation(url[0]);
        } catch (Exception e) {
            // uihandle.stopProgressDialog();
            Log.d("error", e.toString());
        }
        return jsonObject;

    }

    public JSONObject getAdInformation(String requestURL) {
        String charset = "UTF-8";
        try {
            MultipartFormRequest multipart = new MultipartFormRequest(requestURL, charset);

            multipart.addFormField("userid", userid);
            multipart.addFormField("token", token);
            multipart.addFormField("batchid", batchId);
            multipart.addFormField("placeid", placeid);

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
