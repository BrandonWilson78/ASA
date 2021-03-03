package za.asa_media.awesome_sa.modules_.login_.apicall;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snow-Dell-05 on 5/17/2017.
 */

public class FireApiGenerateToken {

    private static JSONObject mJsonOutput = null;
    String tag_json_obj = "json_obj_req";
    private String url;
    private String method;
    private HashMap<String, String> params;

    private Activity mContext;
    private ResponseCallback mCallback;


    public FireApiGenerateToken(Activity mContext) {
       /* this.url = url;
        this.method = method;
        this.params = params;*/
        this.mContext = mContext;
        mJsonOutput = new JSONObject();

        // call back initialisation
        mCallback = (ResponseCallback) mContext;

    }

    public static JSONObject getJsonResponse() {

        return mJsonOutput;

    }

    public void requestPostJson(String url, final Map<String, String> params, String progressMsg) {


        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        mJsonOutput = response;
                        mCallback.haveResponse(response);

                        Log.d("Response", response.toString());
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error", error.toString());

            }
        }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        queue.add(postRequest);


    }


}
