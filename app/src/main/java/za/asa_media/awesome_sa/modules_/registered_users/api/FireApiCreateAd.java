package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import za.asa_media.awesome_sa.support.MultipartFormRequest;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

/**
 * Created by Snow-Dell-05 on 5/26/2017.
 */

public class FireApiCreateAd extends AsyncTask<String, Void, JSONObject> {

    String ad_name, description, start_date, end_date, coupan, email, website, landline, mobile, EventLocation, EventTime;
    private Activity context;

    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String image, image2, image3, userid, token, placeId, purchaseId, planId, batchId, placeName, expiryDate;
    private StringBuilder result;
    private JSONObject jObj;

    public FireApiCreateAd(Activity context, String image, String image2, String image3) {

        this.context = context;
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);

        this.image = image;
        this.image2 = image2;
        this.image3 = image3;

        userid = mSession.getLoggedId();
        token = mSession.getToken();

        purchaseId = mSession.getCAdPurchaseId();
        placeId = mSession.getCAdPlaceId();
        planId = mSession.getCAdPlanId();
        batchId = mSession.getCAdBatchId();
        placeName = mSession.getCAdPlaceName();
        expiryDate = mSession.getCAdExpiryDate();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While ad is being uploaded...");
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            ad_name = url[1];
            description = url[2];
            start_date = url[3];
            end_date = url[4];
            coupan = url[5];
            email = url[6];
            website = url[7];
            landline = url[8];
            mobile = url[9];
            EventLocation = url[10];
            EventTime = url[11];

            jsonObject = goForCreateAd(url[0], image, image2, image3);

        } catch (Exception e) {

            // uihandle.stopProgressDialog();
            Log.d("question", e.toString());
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        super.onPostExecute(response);
        uihandle.stopProgressDialog();
    }


    public JSONObject goForCreateAd(String requestURL, String fileName, String file2, String file3) {
        String charset = "UTF-8";
        try {
            MultipartFormRequest multipart = new MultipartFormRequest(requestURL, charset);

            multipart.addFormField("token", token);
            multipart.addFormField("userid", userid);
            multipart.addFormField("place_id", placeId);
            multipart.addFormField("plan_id", planId);
            multipart.addFormField("purchase_id", purchaseId);
            multipart.addFormField("batch_id", batchId);
            multipart.addFormField("place_name", placeName);
            multipart.addFormField("expiry_date", expiryDate);

            multipart.addFormField("ad_name", ad_name);
            multipart.addFormField("description", description);
            multipart.addFormField("start_date", start_date);
            multipart.addFormField("end_date", end_date);
            multipart.addFormField("coupon", coupan);

            multipart.addFormField("email", email);
            multipart.addFormField("website", website);
            multipart.addFormField("landline", landline);
            multipart.addFormField("mobile", mobile);
            multipart.addFormField("EventLocation", EventLocation);
            multipart.addFormField("EventTime", EventTime);

            if (!fileName.equals("")) {
                multipart.addFilePart("image", new File(fileName));
            } else {
                multipart.addFormField("image", "");
            }
            if (!file2.equals("")) {
                multipart.addFilePart("image1", new File(file2));
            } else {
                multipart.addFormField("image1", "");
            }
            if (!file3.equals("")) {
                multipart.addFilePart("image2", new File(file3));
            } else {
                multipart.addFormField("image2", "");
            }

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