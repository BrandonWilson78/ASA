package za.asa_media.awesome_sa.modules_.new_module_changes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.common_util_.api_request_volley.HttpRequester;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-05 on 20-Apr-18.
 */

public class ActivityHowToRegister extends CommonActivity {

    private Activity mContext = this;
    private UiHandleMethods uihandle;

    private TextView mTextRegisterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_register);
        init();
        howToRegisterApi();
    }

    private void init() {
        uihandle = new UiHandleMethods(mContext);
        mTextRegisterText = (TextView) findViewById(R.id.text_register);
    }


    public void goBackk(View v) {
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }


    private void howToRegisterApi() {

        if (!isNetworkConnected()) {
            showToast(NETWORK_ERROR);
            return;
        }
        showIOSProgress("Loading");
        HashMap<String, String> map = new HashMap<>();
        new HttpRequester(Request.Method.POST, this, map, 100,
                URLListApis.URL_HOW_TO_REGISTER, this);

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        try {
            Log.e("Response", response);
            JSONObject jsonObject = new JSONObject(response);

            switch (serviceCode) {
                case 100:
                    dismissIOSProgress();
                    refineDetail(jsonObject);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void refineDetail(JSONObject response) {
        try {
            //  uihandle.showToast(response.optString(("message")));
            if (response.optBoolean("status")) {
                JSONArray jObj = response.optJSONArray("data");
                if (jObj != null) {
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jResponse = jObj.optJSONObject(i);

                        String id = jResponse.optString("id").toString().trim();
                        String register_detail = jResponse.optString("register_detail").toString().trim();

                        //###############  set Register Details  #############\\
                        mTextRegisterText.setText(register_detail);

                    }
                }

            } else {
                uihandle.showToast(response.optString("message"));
            }

        } catch (Exception e) {
            uihandle.showToast(e.toString());

        }
    }

    @Override
    public void onErrorFound(String errorResponse, int serviceCode) {
        super.onErrorFound(errorResponse, serviceCode);
        try {
            dismissIOSProgress();
            Log.e("Response", errorResponse);
            showToast(errorResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
