package za.asa_media.awesome_sa.modules_.FireApi;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/31/2017.
 */

public class FireApiGetAdsInfoWithBatchId extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private SessionCityOculus mSession;
    private String batchId, placeid;

    public FireApiGetAdsInfoWithBatchId(Activity context) {
        this.context = context;
        this.jsonParser = new JSONParser();
        this.mSession = new SessionCityOculus(context);
        batchId = InitialValueSetUp.adBatchId;
        placeid = InitialValueSetUp.adPlaceId;

    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {
            HashMap<String, String> params = new HashMap<>();

            params.put("batchid", batchId);
            params.put("placeid", placeid);

            jsonObject = jsonParser.makeHttpRequest(url[0], "POST", params);

        } catch (Exception e) {
            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;

    }


}
