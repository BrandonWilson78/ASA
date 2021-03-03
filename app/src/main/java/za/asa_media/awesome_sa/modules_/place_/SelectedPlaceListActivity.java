package za.asa_media.awesome_sa.modules_.place_;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.adapter.SelectedPlaceListAdapter;
import za.asa_media.awesome_sa.modules_.async.AsyncHandler;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.SelectedPlaceListdata;
import za.asa_media.awesome_sa.modules_.place_.api.FireApiGetAllRunningAds;
import za.asa_media.awesome_sa.modules_.place_.callback.SelectedPlaceListCallback;
import za.asa_media.awesome_sa.modules_.place_.model.ActiveAdsModel;
import za.asa_media.awesome_sa.modules_.placedetail_.PlaceDetailActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class SelectedPlaceListActivity extends CommonActivity implements SelectedPlaceListCallback {

    private Activity mContext = this;
    private ImageView mImageHeaderBack, mImgHeaderNearMePlace;
    private TextView mTextViewHeader;

    private UiHandleMethods uihandle;
    // recyclerview setup
    private RecyclerView mRecyclePlacesList;
    private List<SelectedPlaceListdata> mListPlaceData;
    private SelectedPlaceListAdapter mPlaceAdapter;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private boolean mNoDataFlag = false;

    // ADS LIST WITH PLACE IDS
    private List<ActiveAdsModel> mListActiveAds;
    private ProgressDialog mProgressDialog;

    private FrameLayout mFrameCardNoData;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_place_list);

        // calling init for initialisations
        initViews();
        implementListeners();
    }

    private void implementListeners() {
        mTextViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InitialValueSetUp.mTypeHeadingForListing.equals("Home makers & improvers")) {
                    homeImproverDialog();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Hotels & Accommodation")) {
                    hotelAccomodationDialog("Hotels & Accommodation");
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Things to do")) {
                    // set flag for the show sub category
                    thingsToDoDialog("Things to do");
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Nightlife")) {
                    getNightLifes();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Gym")) {
                    getGymTypes();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Restaurants")) {
                    getRestaurantsDialog();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Retail therapy")) {
                    retailTherapyDialog();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Spas")) {
                    getSpasDialog();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Hair & Beauty")) {
                    getHairBeautyDialog();
                } else if (InitialValueSetUp.mTypeHeadingForListing.equals("Travel & Tours")) {
                    getTravelAndTourDialog();
                }
            }
        });

    }

    private void getTravelAndTourDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Travel & Tours")
                .items(mPresenter.getTourAndTravels())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getHairBeautyDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Hair & Beauty")
                .items(mPresenter.getHairAndBeauty())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getSpasDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Spas")
                .items(mPresenter.getSpaList())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void retailTherapyDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Retail therapy")
                .items(mPresenter.getRetailTherapy())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getGymTypes() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Gym")
                .items(mPresenter.getGymLis())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        // goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void getNightLifes() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Nightlife")
                .items(mPresenter.getNightLifes())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        // goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void thingsToDoDialog(String title) {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title(title)
                .items(mPresenter.getThingsToDo())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        // set flag for the show sub category
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void hotelAccomodationDialog(String title) {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title(title)
                .items(mPresenter.getHotelAccommodation())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        // set flag for the show sub category
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }


    private void homeImproverDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Home makers & improvers")
                .items(mPresenter.getHomeImprovers())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        // set flag for the show sub category
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);

                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void getRestaurantsDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Restaurants")
                .items(mPresenter.getRestaurantsList())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void goForPlaces(String nm) {
        InitialValueSetUp.mType = nm;
        mTextViewHeader.setText(Html.fromHtml(UiHandleMethods.capitalizeString(InitialValueSetUp.mTypeHeading)));
       // fetchAllAds();
        finish();
        startActivity(getIntent());

    }

    private void initViews() {
//      initialisations
        uihandle = new UiHandleMethods(this);
        mPresenter = new MainPresenter(mContext);
        mListActiveAds = new ArrayList<>();
        mListPlaceData = new ArrayList<>();

        mFrameCardNoData = (FrameLayout) findViewById(R.id.search_card_);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress);
        mToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("");

        mTextViewHeader = mToolbar.findViewById(R.id.textView_header_center);
        mImageHeaderBack = mToolbar.findViewById(R.id.img_back_header);
//      mImgHeaderNearMePlace = mToolbar.findViewById(R.id.img_near_me_header);


        mRecyclePlacesList = (RecyclerView) findViewById(R.id.recycle_selected_place_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclePlacesList.setLayoutManager(mLayoutManager);

//      mRecyclePlacesList.setItemAnimator(new DefaultItemAnimator());
//      setting value to views and applied listener
        if (!TextUtils.isEmpty(InitialValueSetUp.mTypeHeading)) {
            //     "<font color='#7F7F7F'>Category Name<br></font>" +
            if (getSessionInstance().getCategoryFlagHavingSubcategory()) {

                mTextViewHeader.setClickable(true);
                mTextViewHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_arrow_d, 0);
                mTextViewHeader.setCompoundDrawablePadding(8);
                mTextViewHeader.setText(Html.fromHtml(UiHandleMethods.capitalizeString(InitialValueSetUp.mTypeHeading)));
            } else {
                mTextViewHeader.setText(Html.fromHtml(UiHandleMethods.capitalizeString(InitialValueSetUp.mTypeHeading)));
            }
        } else {

            mTextViewHeader.setText("N/A");
        }

        mImageHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedPlaceListActivity.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        //     calling ads api for tagging to google api palces
        //     calling near by places api
        //     setPlacesAdapterOnRecycle();

        fetchAllAds();
    }

    private void fetchAllAds() {
        new FireApiGetAllRunningAds(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {

                        mListActiveAds.clear();

                        Log.d("history", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

                                            String userid = jResponse.optString("userid").toString().trim();
                                            String purchaseid = jResponse.optString("purchase_id").toString().trim();
                                            String placeId = jResponse.optString("place_id").toString().trim();
                                            String placeName = jResponse.optString("place_name").toString().trim();
                                            String planName = jResponse.optString("plan_name").toString().trim();
                                            String planId = jResponse.optString("plan_id").toString().trim();
                                            String price = jResponse.optString("price").toString().trim();

                                            JSONArray batchIdJson = jResponse.optJSONArray("batchid");
                                            String[] batchId = new String[batchIdJson.length()];

                                            if (batchIdJson != null) {
                                                for (int j = 0; j < batchIdJson.length(); j++) {
                                                    batchId[j] = batchIdJson.optString(j).toString().trim();
                                                }
                                            }

                                            String purchaseDate = jResponse.optString("purchase_date").toString().trim();
                                            String ExpiryDate = jResponse.optString("expiry_date").toString().trim();
                                            String transactionId = jResponse.optString("transaction_id").toString().trim();
                                            String paymentStatus = jResponse.optString("payment_status").toString().trim();
                                            String planStatus = jResponse.optString("plan_status").toString().trim();

                                            mListActiveAds.add(new ActiveAdsModel(userid, purchaseid, placeId, placeName, planId,
                                                    planName, price, batchId, purchaseDate, ExpiryDate,
                                                    transactionId, paymentStatus, planStatus));
                                        }

                                        //  initialise adapter and put values to recycel view
                                        //  mAdapterCreates = new AdapterCreateAd(mContext, mListCreateAds);
                                        //  mRecycleCreateAds.setAdapter(mAdapterCreates);


                                        // calling near by places api
                                        setPlacesAdapterOnRecycle();
                                    } else {

                                        //  calling near by places api
                                        setPlacesAdapterOnRecycle();
                                    }
                                }
                            } else {
                                setPlacesAdapterOnRecycle();
                                uihandle.showToast(response.optString(("message")));
                            }
                        } else {
                            setPlacesAdapterOnRecycle();
                            uihandle.showToast("Something went wrong");
                        }
                    } else {
                        // calling near by places api
                        setPlacesAdapterOnRecycle();
                    }

                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }
            }
        }.execute(URLListApis.URL_GET_ALL_RUNNING_ADS);
    }

    private void setPlacesAdapterOnRecycle() {
        new AsyncHandler(this, URLListApis.URL_SEARCH_NEAR_BY_PLACE.replace("lt_lg", ReservedLocation.getSingletonInstance().getCurret_lat() + "," + ReservedLocation.getSingletonInstance().getCurrent_lng()).replace("xyz", InitialValueSetUp.mType)) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    if (mProgressDialog == null) {
                        mProgressDialog = ProgressDialog.show(SelectedPlaceListActivity.this, "", "While places are being fetched", false, false);
                    }
                } catch (Exception e) {
                    Log.e("setPlacesAdapterRecycle", e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(List<SelectedPlaceListdata> selectedPlaceListdatas) {
                super.onPostExecute(selectedPlaceListdatas);
                try {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    if (selectedPlaceListdatas != null && selectedPlaceListdatas.size() > 0) {
                        mListPlaceData.clear();

                        mListPlaceData.addAll(selectedPlaceListdatas);

                        if (mPlaceAdapter == null) {
                            mPlaceAdapter = new SelectedPlaceListAdapter(SelectedPlaceListActivity.this, mListPlaceData, mListActiveAds);
                            mRecyclePlacesList.setAdapter(mPlaceAdapter);
                        } else {
                            mPlaceAdapter.notifyDataSetChanged();
                        }


                    } else {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }

                        mRecyclePlacesList.setVisibility(View.GONE);
                        mFrameCardNoData.setVisibility(View.VISIBLE);


                        //       uihandle.showToast("Sorry, Near by data not found");
                    }
                } catch (Exception e) {

                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }

                    Log.d("ASA", e.toString());
                }
            }

        }.execute();
    }

    @Override
    public void getPlaceId(HashMap<String, String[]> hash, String id, String distance, float rating) {

        InitialValueSetUp.mPlaceId = id;
        InitialValueSetUp.mDistance = distance;

        InitialValueSetUp.mRating = rating;
        //   uihandle.showToast("1." + "ChIJ2S7cDBzsDzkRCCVJrI3FdPg" + "\n2." + id);

        InitialValueSetUp.mBatchs = hash.get(InitialValueSetUp.mPlaceId);

        getSessionInstance().setPlaceIdTabbed(id);   // 3
        //Todo:
        getSessionInstance().setPlaceId(id);
        getSessionInstance().setCAdPlaceId(id);

        //  uihandle.showToast(id);
        if (TextUtils.isEmpty(id)) {
            uihandle.showToast("Sorry, Unknown Place Id");
            return;
        } else if (getSessionInstance().isLogged()) {

            Intent intent = new Intent(mContext, PlaceDetailActivity.class);
            //    intent.putExtra("batchArray", hash.get(InitialValueSetUp.mPlaceId));
            //    intent.putExtra("pl_id", id);
            startActivity(intent);
            mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

          /*  if (InitialValueSetUp.mBatchs.length > 0) {
                Intent intent = new Intent(mContext, PlaceDetailScreenLoggedInWithPlan.class);
                //    intent.putExtra("batchArray", hash.get(InitialValueSetUp.mPlaceId));
                //    intent.putExtra("pl_id", id);
                startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            } else {


            }*/

        } else {
            Intent intent = new Intent(mContext, PlaceDetailActivity.class);
            //       intent.putExtra("batchArray", hash.get(InitialValueSetUp.mPlaceId));
            //       intent.putExtra("pl_id", id);

            startActivity(intent);
            mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


        }

    }

    @Override
    public void getNextPageData(int position) {
        if (!mNoDataFlag) {
            setNextpageDataToRecyclerView(position);
        }

    }

    private void setNextpageDataToRecyclerView(final int position) {
        new AsyncHandler(this, URLListApis.URL_NEXT_PAGE_WITH_TOKEN.replace("PAGE_TOKEN", InitialValueSetUp.nextPageToken)) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    mProgressBar.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e("setPlacesAdapterRecycle", e.getMessage());
                }

            }

            @Override
            protected void onPostExecute(List<SelectedPlaceListdata> selectedPlaceListdatas) {
                super.onPostExecute(selectedPlaceListdatas);
                try {
                    if (mProgressBar.isShown()) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    if (selectedPlaceListdatas != null && selectedPlaceListdatas.size() > 0) {

                        mListPlaceData.addAll(selectedPlaceListdatas);
                        mPlaceAdapter.notifyDataSetChanged();
                        mRecyclePlacesList.scrollToPosition(position);

                        //     mRecyclePlacesList.setAdapter(mPlaceAdapter);
                    } else {
                        if (mProgressBar.isShown()) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        uihandle.showToast("No more Data Available");
                        mNoDataFlag = true;
                    }
                } catch (Exception e) {
                    if (mProgressBar.isShown()) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                    Log.d("AS", e.toString());
                }
            }

        }.execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);


    }

}
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_business:
                uihandle.explicitIntent(LoggedInUserScreen.class);
                return true;
            case R.id.item_basic_info:
                uihandle.explicitIntent(BasicInfo.class);
                //this.finish();

                return true;
            case R.id.item_login:
                Toast.makeText(getApplicationContext(), "Under Scruitny", Toast.LENGTH_LONG).show();
                return true;

        }
        return true;
    }
*/