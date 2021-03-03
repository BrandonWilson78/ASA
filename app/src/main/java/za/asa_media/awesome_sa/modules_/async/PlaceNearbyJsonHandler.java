package za.asa_media.awesome_sa.modules_.async;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.SelectedPlaceListdata;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-07 on 4/20/2017.
 */

public class PlaceNearbyJsonHandler {
    private List<SelectedPlaceListdata> lst_Place_searches = null;

    public PlaceNearbyJsonHandler() {
        init();
    }

    //  URLListApis.URL_SEARCH_NEAR_BY_PLACE.replace("xyz", InitialValueSetUp.mType)
    public List<SelectedPlaceListdata> get_nearby_places(String url)  {

        JSONObject main_object = HttpsConnHandler.requestWebService(url);

        Log.e("ASA", URLListApis.URL_SEARCH_NEAR_BY_PLACE);
        try {
            if (main_object.has("error_message")) {
                //  main_object = new JSONObject(GetJson.getJson);
            }
            if (main_object.has("next_page_token")) {
                String token = main_object.optString("next_page_token").trim();
                InitialValueSetUp.nextPageToken = token;
                Log.d("intial_token", InitialValueSetUp.nextPageToken);
            } else {

                InitialValueSetUp.nextPageToken = "";
            }


            Log.e("data: ", "" + main_object);
            JSONArray jsonArray = main_object.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject index_object = jsonArray.optJSONObject(i);
                SelectedPlaceListdata objBean = new SelectedPlaceListdata(Parcel.obtain());
                objBean.setPs_formatted_address(index_object.optString("vicinity"));
                objBean.setPs_lat(Double.toString(index_object.optJSONObject("geometry").optJSONObject("location").optDouble("lat")));
                objBean.setPs_lng(Double.toString(index_object.optJSONObject("geometry").optJSONObject("location").optDouble("lng")));
                objBean.setPs_name(index_object.optString("name"));
                if (index_object.has("place_id")) {
                    objBean.setPs_place_id(index_object.optString("place_id"));
                }
                if (index_object.has("opening_hours")) {
                    objBean.setmFlagCheckTiming(true);
                    objBean.setPs_opening_status(index_object.optJSONObject("opening_hours").optBoolean("open_now"));

                }
                //    Check if need
                if (index_object.has("photos")) {
                    objBean.setPs_photo_refrence(index_object.optJSONArray("photos").optJSONObject(0).optString("photo_reference"));
                }
                if (index_object.has("rating")) {
                    objBean.setPs_rating(index_object.optDouble("rating"));
                }

                lst_Place_searches.add(objBean);
            }
        } catch (Exception e) {
            Log.e("Exception nearby_places", e.toString());
        }
        return lst_Place_searches;
    }

    private void init() {
        lst_Place_searches = new ArrayList<>();
    }


}
