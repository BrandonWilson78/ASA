package za.asa_media.awesome_sa.modules_.placedetail_;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.modules_.async.HttpsConnHandler;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-07 on 4/20/2017.
 */

public class PlaceDetailJsonHandler {
    private List<BeanPlaceDetail> lstBeanPlaceDetail = null;
    private ArrayList<String> lstOpeningScheduleList = null;
    private ArrayList<String> lstReviews = null;
    private ArrayList<String> lstPhotoReferences = null;
    private BeanPlaceDetail objBeanPlaceDetail = null;
    private ArrayList<BeanPlaceDetailReviews> lstPlaceDetailReviews = null;



    public PlaceDetailJsonHandler() {

        init();
    }

    private void init() {
        lstBeanPlaceDetail = new ArrayList<>();
        lstPlaceDetailReviews = new ArrayList<>();
        lstOpeningScheduleList = new ArrayList<>();
        objBeanPlaceDetail = new BeanPlaceDetail();
        lstReviews = new ArrayList<>();
        lstPhotoReferences = new ArrayList<>();
    }

    public BeanPlaceDetail getPlaceDetailList() {
        JSONObject mainDetailSearchObject = HttpsConnHandler.requestWebService(URLListApis.URL_PLACES_DETAILS.replaceAll(" ", "%20").replace("JAGH_", InitialValueSetUp.mPlaceId));
        try {

            if (mainDetailSearchObject.has("result")) {
                JSONObject resultObject = mainDetailSearchObject.optJSONObject("result");
                objBeanPlaceDetail.setPd_formatted_address(resultObject.optString("formatted_address"));

                if (resultObject.has("formatted_phone_number")) {
                    objBeanPlaceDetail.setPd_formatted_phone_no(resultObject.optString("formatted_phone_number"));
                }
                if (resultObject.has("international_phone_number")) {
                    objBeanPlaceDetail.setPd_international_phone_no(resultObject.optString("international_phone_number"));
                }

                if (resultObject.has("geometry")) {
                    objBeanPlaceDetail.setLatitude(resultObject.optJSONObject("geometry").optJSONObject("location").optString("lat"));
                    objBeanPlaceDetail.setLongitude(resultObject.optJSONObject("geometry").optJSONObject("location").optString("lng"));
                }


                if (resultObject.has("rating")) {
                    objBeanPlaceDetail.setRatingCount(resultObject.optString("rating"));
                }


                if (resultObject.has("photos")) {
                    //      objBeanPlaceDetail.setPd_photo_refrence(resultObject.optJSONArray("photos").
                    // optJSONObject(0).optString("photo_reference"));

                    JSONArray photoArray = resultObject.optJSONArray("photos");
                    for (int i = 0; i < photoArray.length(); i++) {
                        JSONObject mJ = photoArray.optJSONObject(i);

                        lstPhotoReferences.add(mJ.optString("photo_reference"));
                    }
                    objBeanPlaceDetail.setPd_photo_refrence(lstPhotoReferences);
                }


                if (resultObject.has("url")) {
                    objBeanPlaceDetail.setPd_location_url(resultObject.optString("url"));
                }

                if (resultObject.has("website")) {
                    objBeanPlaceDetail.setPd_website_url(resultObject.optString("website"));
                }

                //opening schedule list
                if (resultObject.has("opening_hours")) {
                    objBeanPlaceDetail.setFlagOpenNow(resultObject.optJSONObject("opening_hours").optBoolean("open_now"));
                    JSONArray scheduleArray = resultObject.optJSONObject("opening_hours").optJSONArray("weekday_text");
                    for (int i = 0; i < scheduleArray.length(); i++) {
                        lstOpeningScheduleList.add(i, scheduleArray.optString(i));
                    }
                    objBeanPlaceDetail.setPd_openning_schedule(lstOpeningScheduleList);
                }


                if (resultObject.has("reviews")) {
                    JSONArray reviewArray = resultObject.optJSONArray("reviews");
                    for (int i = 0; i < reviewArray.length(); i++) {
                        BeanPlaceDetailReviews objBeanPlaceDetailReviews = new BeanPlaceDetailReviews();

                        objBeanPlaceDetailReviews.setPdreview_author_name(reviewArray.optJSONObject(i).optString("author_name"));
                        objBeanPlaceDetailReviews.setPdreview_author_url(reviewArray.optJSONObject(i).optString("author_url"));
                        objBeanPlaceDetailReviews.setPdreview_profile_photo_url(reviewArray.optJSONObject(i).optString("profile_photo_url"));
                        objBeanPlaceDetailReviews.setPdreview_text(reviewArray.optJSONObject(i).optString("text"));
                        objBeanPlaceDetailReviews.setPdreview_raing(reviewArray.optJSONObject(i).optDouble("rating"));

                        lstPlaceDetailReviews.add(objBeanPlaceDetailReviews);
                    }

                    objBeanPlaceDetail.setPd_reviews(lstPlaceDetailReviews);
                }

            }
        } catch (Exception e) {
            Log.e("ASA", e.getMessage());
        }
        return objBeanPlaceDetail;
    }


}
