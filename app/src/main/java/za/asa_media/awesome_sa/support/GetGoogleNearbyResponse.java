package za.asa_media.awesome_sa.support;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Snow-Dell-05 on 19-Feb-18.
 */

public class GetGoogleNearbyResponse extends AsyncTask<String, Void, JSONObject> {

    JSONParserJson jsonParser;
    StringBuilder result;
    JSONObject jObj;

    public GetGoogleNearbyResponse() {
        this.jsonParser = new JSONParserJson();
    }

    @Override
    protected JSONObject doInBackground(String... url) {

        try {
            return getJsonResponseFromGoogle(url[0]);
        } catch (Exception e) {
            Log.d("TAG", e.toString());
            return null;
        }

    }

    private JSONObject getJsonResponseFromGoogle(String requestURL) {
        String charset = "UTF-8";
        try {
            MultipartFormRequest multipart = new MultipartFormRequest(requestURL, charset);

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

