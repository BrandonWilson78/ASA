package za.asa_media.awesome_sa.modules_.place_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.place_.adapter.FavouritePlaceAdapter;
import za.asa_media.awesome_sa.modules_.place_.api.FireApiGetFavourites;
import za.asa_media.awesome_sa.modules_.place_.callback.IFavouriteCallback;
import za.asa_media.awesome_sa.modules_.place_.model.FavouritePlaceInfoModel;
import za.asa_media.awesome_sa.modules_.placedetail_.PlaceDetailActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class FavouritePlaces extends AppCompatActivity implements IFavouriteCallback {

    UiHandleMethods uiHandle;
    private Activity mContext = this;
    private ImageView mImageBack;
    // get fav
    private RecyclerView mRecycleFavouritePlaces;
    private List<FavouritePlaceInfoModel> mListFavourite;
    private FavouritePlaceAdapter mAdapterFav;
    private SessionCityOculus mSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_places);

        mListFavourite = new ArrayList<>();
        uiHandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);


        mImageBack = (ImageView) findViewById(R.id.img_back_header);

        // get fav
        mRecycleFavouritePlaces = (RecyclerView) findViewById(R.id.recycler_view_favourite_place);
        // recycle view parameters set
        mRecycleFavouritePlaces.setLayoutManager(new LinearLayoutManager(mContext));
        //  mRecycleFavouritePlaces.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        // mRecycleFavouritePlaces.setItemAnimator(new DefaultItemAnimator());


        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouritePlaces.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

            }
        });
        // get fav items
        getFavourites();

    }

    private void getFavourites() {
        new FireApiGetFavourites(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (response != null) {
                        Log.d("fav", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

                                            String placeid = jResponse.optString("placeid").toString().trim();
                                            JSONArray batchIdJson = jResponse.optJSONArray("batchid");
                                            String[] batchId = new String[batchIdJson.length()];

                                            if (batchIdJson != null) {
                                                for (int j = 0; j < batchIdJson.length(); j++) {
                                                    batchId[j] = batchIdJson.optString(j).toString().trim();
                                                }
                                            }
                                            // String batchid = jResponse.optString("batchid").toString().trim();
                                            String rating = jResponse.optString("rating").trim();
                                            String distance = jResponse.optString("distance").trim();
                                            String address = jResponse.optString("address").trim();
                                            String place_name = jResponse.optString("place_name").trim();
                                            String latitude = jResponse.optString("latitude").trim();
                                            String longitude = jResponse.optString("longitude").trim();


                                            mListFavourite.add(new FavouritePlaceInfoModel(placeid, batchId, rating, distance, address, place_name,latitude,longitude));
                                        }

                                        if (mListFavourite.size() > 0) {
                                            mAdapterFav = new FavouritePlaceAdapter(mContext, mListFavourite);
                                            mRecycleFavouritePlaces.setAdapter(mAdapterFav);
                                        }
                                    }
                                }
                            } else {

                                uiHandle.showToast(response.optString(("message")));
                            }

                        } else {
                            uiHandle.showToast("Something went wrong");
                        }
                    }
                } catch (Exception e) {
                    uiHandle.showToast(e.toString());

                }
            }
        }.execute(URLListApis.URL_GET_FAVOURITES);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void getPlaceDetail(HashMap<String, String[]> mHashmap, String place_id, String distance, float rating) {

        //uihandle.showToast(id);
        if (TextUtils.isEmpty(place_id)) {
            uiHandle.showToast("Sorry, Unknown Place Id");
        } else {

            InitialValueSetUp.mPlaceId = place_id;
            InitialValueSetUp.mDistance = distance;
            InitialValueSetUp.mRating = rating;
            //   uihandle.showToast("1. " + "ChIJ2S7cDBzsDzkRCCVJrI3FdPg" + "\n2." + id);
            InitialValueSetUp.mBatchs = mHashmap.get(InitialValueSetUp.mPlaceId);

            Intent intent = new Intent(mContext, PlaceDetailActivity.class);
            //  intent.putExtra("batchArray", mHashmap.get(InitialValueSetUp.mPlaceId));
            //  intent.putExtra("pl_id",place_id);

            startActivity(intent);
            mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


        }


    }
}
