package za.asa_media.awesome_sa.modules_.registered_users.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import za.asa_media.awesome_sa.support.JSONParser;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 5/25/2017.
 */

public class FireApiUpdatePaymentStatus extends AsyncTask<String, Void, JSONObject> {

    private Activity context;
    private JSONParser jsonParser;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String userid, token, purchaseid, state, txnId;


    public FireApiUpdatePaymentStatus(Activity context) {

        this.context = context;
        jsonParser = new JSONParser();
        uihandle = new UiHandleMethods(context);
        mSession = new SessionCityOculus(context);

        userid = mSession.getLoggedId();
        token = mSession.getToken();
        purchaseid = mSession.getPurchaseId();
        state = mSession.getPaymentStatus();
        txnId = mSession.getPaymentTxnId();


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uihandle.startProgress("While payment is being confirmed...");
    }

    /*
    token:3e431daa68ed3fd4c8a9f6aabc934a5b
    userid:3
    purchaseid:2
    state:approved
    transaction_id:PAY-51X6730865876902PLESCCZA
        */
    @Override
    protected JSONObject doInBackground(String... url) {
        JSONObject jsonObject = null;
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("token", token);
            params.put("userid", userid);
            params.put("purchaseid", purchaseid);
            params.put("state", state);
            params.put("transaction_id", txnId);

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
        uihandle.stopProgressDialog();
    }
}