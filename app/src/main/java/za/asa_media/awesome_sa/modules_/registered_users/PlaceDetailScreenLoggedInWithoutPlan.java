package za.asa_media.awesome_sa.modules_.registered_users;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.catalogue.CatalogActivity;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.common_util_.api_request_volley.HttpRequester;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.event.EventsActivity;
import za.asa_media.awesome_sa.modules_.news.NewsActivity;
import za.asa_media.awesome_sa.modules_.placedetail_.BeanPlaceDetail;
import za.asa_media.awesome_sa.modules_.placedetail_.BeanPlaceDetailReviews;
import za.asa_media.awesome_sa.modules_.placedetail_.adapter.AdapterGoogleImages;
import za.asa_media.awesome_sa.modules_.placedetail_.adapter.AdapterReviews;
import za.asa_media.awesome_sa.modules_.placedetail_.async.PlaceDetailCallApi;
import za.asa_media.awesome_sa.modules_.promotion.PromotionActivity;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiUpdatePaymentStatus;
import za.asa_media.awesome_sa.modules_.special.SpecialActivity;
import za.asa_media.awesome_sa.paypal_.PayPalPresenter;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

import static za.asa_media.awesome_sa.R.id.rating_review_bottom;

/**
 * Created by Snow-Dell-05 on 04-Apr-18.
 */

public class PlaceDetailScreenLoggedInWithoutPlan extends CommonActivity implements
        View.OnClickListener,
        OnStreetViewPanoramaReadyCallback,
        OnMapReadyCallback {

    String latest_price = "";
    private Activity mContext = this;
    private UiHandleMethods uihandle;
    private Button mBtnPurchasePlan;
    // detail parameters
    private TextView mTextAddress;
    private TextView mTextMondayVal, mTextTuesdayVal, mTextWednesdayVal, mTextThursdayVal, mTextFridayVal, mTextSaturdayVal, mTextSundayVal;
    private RatingBar mRatingBar;
    // Contacts View Declarations Textview_site_link
    private TextView mTextPhoneNumber, mTextSiteLink1, mTextSiteLink2;
    private String mAddress, mPhoneNumber, mWebsiteUrl, mGoogleMapUrl;
    //...//...//....//..../..../..../..../...../...../...
    private RecyclerView mRecycleGoogleImages;
    private List<String> mListGoogleImageReferences;
    private AdapterGoogleImages mAdapterGoogelImages;
    private TextView mTextOpenNow;
    private TextView mTextNoMoreImages;
    //..
    private LinearLayout mLinearDirection;
    private TextView mTextRatingNum, mTextGoogleReviewsCount;
    private boolean mFlagOpenNow = false;
    private LinearLayout mLinearOpeningHours;
    private RelativeLayout mRelativeReviewsLay;
    // review
    // Recycleviews Reviews
    private RecyclerView mRecycleReview;
    private ArrayList<BeanPlaceDetailReviews> mReviewList;
    private AdapterReviews mAdapterReview;
    private ViewFlipper mFlipper;
    private BeanPlaceDetail bObj;
    private TextView mTextSpecial, mTextEvents, mTextPromotions, mTextNews, mTextCataloge;
    private ImageView mImageSpecial, mImageEvents, mImagePromotions, mImageNews, mImageCataloge;
    // Google map setup decalarations
    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private StreetViewPanoramaFragment mStreetViewFragment;
    // Views declarations Address
    private TextView mTextPlaceDetailName;
    private SessionCityOculus mSession;
    private RelativeLayout mRelRenewPlanLayout;
    private LinearLayout mLinearPurchaseAddPlan;
    private TextView mTextFreeAddTrailStatus, mTextExpireDate;
    private Button mButtonRenewPlan;
    private PayPalPresenter mPresenterPaypal;
    private List<String> mListPlans;
    private String mChoosedPlanForRenew = "";
    private String paymentDetails;
    private String mState, paymentId, paymentCreatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test_layout);

        init();
        // get plan detail
        goForGetPlanDetail();
        getPlaceDetail();

    }

    private void init() {
        uihandle = new UiHandleMethods(mContext);
        mPresenterPaypal = new PayPalPresenter(mContext);
        uihandle.setStatusBarColor(ContextCompat.getColor(mContext, R.color.new_app_blue_color));
        mSession = new SessionCityOculus(mContext);

        mReviewList = new ArrayList<>();
        mListGoogleImageReferences = new ArrayList<>();

        // plan views starts
        mRelRenewPlanLayout = (RelativeLayout) findViewById(R.id.rel_renew_plan);
        mLinearPurchaseAddPlan = (LinearLayout) findViewById(R.id.linear_purchase_add_plan);
        mTextFreeAddTrailStatus = (TextView) findViewById(R.id.textview_free_add_trial);
        mTextExpireDate = (TextView) findViewById(R.id.textview_free_add_expires);
        mButtonRenewPlan = (Button) findViewById(R.id.btn_renew_plan);
        // plan views end

        mBtnPurchasePlan = (Button) findViewById(R.id.btn_purchase_plan);
        mFlipper = (ViewFlipper) findViewById(R.id.frame_street_map);

        mTextSpecial = (TextView) findViewById(R.id.batch_text_place_detail_special);
        mTextEvents = (TextView) findViewById(R.id.batch_text_place_detail_events);
        mTextPromotions = (TextView) findViewById(R.id.batch_text_place_detail_promotion);
        mTextNews = (TextView) findViewById(R.id.batch_text_place_detail_news);
        mTextCataloge = (TextView) findViewById(R.id.batch_text_place_detail_catalog);

        mImageSpecial = (ImageView) findViewById(R.id.batch_img_place_detail_special);
        mImageEvents = (ImageView) findViewById(R.id.batch_img_place_detail_events);
        mImagePromotions = (ImageView) findViewById(R.id.batch_img_place_detail_promotion);
        mImageCataloge = (ImageView) findViewById(R.id.batch_img_place_detail_catalog);
        mImageNews = (ImageView) findViewById(R.id.batch_img_place_detail_news);

        //    place details name on header
        mTextPlaceDetailName = (TextView) findViewById(R.id.c);

        mTextAddress = (TextView) findViewById(R.id.text_place_detail_address);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar_place_detail_);

        //    contacts
        mTextPhoneNumber = (TextView) findViewById(R.id.textview_phone_number);
        mTextSiteLink1 = (TextView) findViewById(R.id.textview_site_link);
        mTextSiteLink2 = (TextView) findViewById(R.id.map_link);

        // Schedule Views
        mTextMondayVal = (TextView) findViewById(R.id.textview_monday_value);
        mTextTuesdayVal = (TextView) findViewById(R.id.textview_tuesday_value);
        mTextWednesdayVal = (TextView) findViewById(R.id.textview_wednesday_value);
        mTextThursdayVal = (TextView) findViewById(R.id.textview_thursday_value);
        mTextFridayVal = (TextView) findViewById(R.id.textview_friday_value);
        mTextSaturdayVal = (TextView) findViewById(R.id.textview_saturday);
        mTextSundayVal = (TextView) findViewById(R.id.textview_sunday_value);


        mLinearOpeningHours = (LinearLayout) findViewById(R.id.linear_opening_hours);

        mTextOpenNow = (TextView) findViewById(R.id.text_open_now);
        mTextNoMoreImages = (TextView) findViewById(R.id.txt_place_imag_g);
        mRelativeReviewsLay = (RelativeLayout) findViewById(rating_review_bottom);
        mRecycleGoogleImages = (RecyclerView) findViewById(R.id.recycle_photo_place_list);
        mRecycleGoogleImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        mLinearDirection = (LinearLayout) findViewById(R.id.linear_direction);
        mTextRatingNum = (TextView) findViewById(R.id.text_place_detail_rating);
        mTextGoogleReviewsCount = (TextView) findViewById(R.id.text_place_detail_google_reviews_count);

        // Recycler view
        mRecycleReview = (RecyclerView) findViewById(R.id.recycle_reviews_place_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecycleReview.setLayoutManager(mLayoutManager);

        //  googlemap
        if (!TextUtils.isEmpty(String.valueOf(InitialValueSetUp.lat)) && !TextUtils.isEmpty(String.valueOf(InitialValueSetUp.lng))) {
            mSupportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            mSupportMapFragment.getMapAsync(this);
        } else {
            uihandle.showToast("Google doesn't get your location");
        }

        //  Todo street view fragment
        mStreetViewFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.street_view_fragment);
        mStreetViewFragment.getStreetViewPanoramaAsync(this);


        try {
            String batchs[] = InitialValueSetUp.mBatchs;
            if (batchs != null) {
                for (String batch : batchs) {
                    Log.d("batch", "" + batch);
                    if (batch.equals("1")) {
                        mImageCataloge.setImageResource(R.drawable.catalog);
                        mTextCataloge.setBackgroundColor(getResources().getColor(R.color.batch_catalogue_color));
                        mImageCataloge.setOnClickListener(this);
                    }
                    if (batch.equals("2")) {
                        mImageEvents.setImageResource(R.drawable.events);
                        mTextEvents.setBackgroundColor(getResources().getColor(R.color.batch_event_color));
                        mImageEvents.setOnClickListener(this);
                    }
                    if (batch.equals("3")) {
                        mImagePromotions.setImageResource(R.drawable.promotions);
                        mTextPromotions.setBackgroundColor(getResources().getColor(R.color.batch_promotion_color));
                        mImagePromotions.setOnClickListener(this);

                    }
                    if (batch.equals("4")) {
                        mImageSpecial.setImageResource(R.drawable.specials);
                        mTextSpecial.setBackgroundColor(getResources().getColor(R.color.batch_special_color));
                        mImageSpecial.setOnClickListener(this);
                    }
                    if (batch.equals("5")) {
                        mImageNews.setImageResource(R.drawable.news);
                        mTextNews.setBackgroundColor(getResources().getColor(R.color.batch_news_color));
                        mImageNews.setOnClickListener(this);
                    }
                }
                // }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        mBtnPurchasePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   uihandle.explicitIntent(ChoosePlan.class);
                getSessionInstance().setNavIndex(true);
                getSessionInstance().setFlagFromDetail(true);

                Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                mContext.finish();
            }
        });

        // setting click listener
        mTextSiteLink1.setOnClickListener(this);
        mTextSiteLink2.setOnClickListener(this);
        mTextPhoneNumber.setOnClickListener(this);
        mTextOpenNow.setOnClickListener(this);
        mRelativeReviewsLay.setOnClickListener(this);
        mLinearDirection.setOnClickListener(this);
        mButtonRenewPlan.setOnClickListener(this);

    }

    public void goBackC(View v) {
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void goMakeCall(View v) {
        if (!mTextPhoneNumber.getText().toString().equals("N/A")) {
            uihandle.callDialog(mTextPhoneNumber.getText().toString(), mTextPhoneNumber.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.batch_img_place_detail_catalog:

                InitialValueSetUp.adBatchId = "1";
                InitialValueSetUp.adPlaceId = getSessionInstance().getPlaceIdTabbed();

                intent = new Intent(mContext, CatalogActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_events:
                // mUiHandleMethods.explicitIntent(EventsActivity.class);
                InitialValueSetUp.adPlaceId = getSessionInstance().getPlaceIdTabbed();
                InitialValueSetUp.adBatchId = "2";
                intent = new Intent(mContext, EventsActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_promotion:
                //  mUiHandleMethods.explicitIntent(PromotionActivity.class);
                InitialValueSetUp.adPlaceId = getSessionInstance().getPlaceIdTabbed();
                InitialValueSetUp.adBatchId = "3";
                intent = new Intent(mContext, PromotionActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_news:
                //  mUiHandleMethods.explicitIntent(NewsActivity.class);
                InitialValueSetUp.adPlaceId = getSessionInstance().getPlaceIdTabbed();
                InitialValueSetUp.adBatchId = "5";
                intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_special:
                InitialValueSetUp.adPlaceId = getSessionInstance().getPlaceIdTabbed();
                InitialValueSetUp.adBatchId = "4";
                intent = new Intent(mContext, SpecialActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.textview_phone_number:
                if (!mTextPhoneNumber.getText().toString().equals("N/A")) {
                    uihandle.callDialog(mTextPhoneNumber.getText().toString(), mTextPhoneNumber.getText().toString());
                }
                break;
            case R.id.textview_site_link:
                if (!mTextSiteLink1.getText().toString().trim().equals("N/A")) {
                    uihandle.openWebLink(mTextSiteLink1.getText().toString().trim());
                }

                break;

            case R.id.map_link:
                if (!mTextSiteLink2.getText().toString().trim().equals("N/A")) {
                    uihandle.openWebLink(mTextSiteLink2.getText().toString().trim());
                }
                break;

            case R.id.img_compass:
                if (!TextUtils.isEmpty(mGoogleMapUrl)) {
                    uihandle.openWebLink(mGoogleMapUrl);
                } else {
                    uihandle.showToast("Sorry, Direction is not specified");
                }
                break;
            case R.id.linear_direction:
                if (!TextUtils.isEmpty(mGoogleMapUrl)) {
                    uihandle.openWebLink(mGoogleMapUrl);
                } else {
                    uihandle.showToast("Sorry, Direction is not specified");
                }
                break;


            case R.id.text_open_now:
                if (mLinearOpeningHours.isShown()) {
                    mLinearOpeningHours.setVisibility(View.GONE);
                } else {
                    mLinearOpeningHours.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rating_review_bottom:
                if (mRecycleReview.isShown()) {
                    mRecycleReview.setVisibility(View.GONE);
                } else {
                    mRecycleReview.setVisibility(View.VISIBLE);
                }
                break;


        }
    }

    public void createWarningDialog(String msg, final String mPurchaseId) {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!")
                .setContentText(msg)
                .setConfirmText("Proceed")
                .setCancelText("Cancel")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();

                        //          goForRenewPlan(mPurchaseId);


                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })

                .show();
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Log.e("street", "inside");
        LatLng latLng = new LatLng(InitialValueSetUp.lat, InitialValueSetUp.lng);
        // LatLng latLng1 = new LatLng(-33.87365, 151.20689);
        streetViewPanorama.setPosition(latLng);

        streetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
            @Override
            public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                if (streetViewPanoramaLocation != null && streetViewPanoramaLocation.links != null) {
                    //   mFrameStreet.setVisibility(View.GONE);
                } else {
                    mFlipper.removeViewAt(0);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // For showing a move to my location button
        // mGoogleMap.
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);


        goForUpdateInfoOnMap(InitialValueSetUp.lat, InitialValueSetUp.lng);


    }

    private void goForUpdateInfoOnMap(double lt, double ln) {
        // For dropping a marker at a point on the Map
        LatLng ltlg = new LatLng(lt, ln);
        mGoogleMap.addMarker(new MarkerOptions().position(ltlg).title(InitialValueSetUp.mPlaceName));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ltlg).zoom(12).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void getPlaceDetail() {
        //  mFrameMap.setVisibility(View.VISIBLE);
        //  mFrameStreet.setVisibility(View.VISIBLE);
        new PlaceDetailCallApi(mContext) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uihandle.startProgress("Place detail is being fetched");
            }

            @Override
            protected void onPostExecute(BeanPlaceDetail beanPlaceDetail) {
                super.onPostExecute(beanPlaceDetail);
                try {
                    uihandle.stopProgressDialog();
                    if (beanPlaceDetail != null) {

                        // calling update ui method to update views
                        updateUi(beanPlaceDetail);
                    } else {
                        uihandle.showToast("Sorry! place detail not found");
                    }
                } catch (Exception e) {
                    Log.e("ASA", e.toString());
                }
            }
        }.execute();
    }

    private void updateUi(BeanPlaceDetail bObj) {
        try {
            //  put open now
            if (bObj.isFlagOpenNow()) {
                mTextOpenNow.setText(Html.fromHtml("Hours: <font color='#1D375E'>Open now</font>"));
            } else {
                mTextOpenNow.setText(Html.fromHtml("Hours: <font color='#1D375E'>Closed now</font>"));
            }

            mListGoogleImageReferences.addAll(bObj.getPd_photo_refrence());

            if (mListGoogleImageReferences != null && mListGoogleImageReferences.size() > 0) {
                mAdapterGoogelImages = new AdapterGoogleImages(mContext, mListGoogleImageReferences);
                mRecycleGoogleImages.setAdapter(mAdapterGoogelImages);
            } else {
                mRecycleGoogleImages.setVisibility(View.GONE);
                mTextNoMoreImages.setVisibility(View.VISIBLE);
            }

            this.bObj = bObj;

            //     beanPlaceDetail.getPd_reviews();
            mTextPlaceDetailName.setText(InitialValueSetUp.mPlaceName);
            mAddress = bObj.getPd_formatted_address();
            mPhoneNumber = bObj.getPd_formatted_phone_no();
            mWebsiteUrl = bObj.getPd_website_url();
            mGoogleMapUrl = bObj.getPd_location_url();


            String phn = "", website = "";
            if (TextUtils.isEmpty(mPhoneNumber)) {

                phn = "";
            } else {
                phn = " Contact: " + mPhoneNumber;
            }

            //    check for weblink
            if (TextUtils.isEmpty(mWebsiteUrl)) {
                website = "";
            } else {
                website = " Website: " + mWebsiteUrl;
            }

            //       Apply values to views
            mTextAddress.setText(Html.fromHtml("<font color='#7F7F7F'>ADDRESS<br></font>" + mAddress));

            if (!TextUtils.isEmpty(mPhoneNumber)) mTextPhoneNumber.setText(mPhoneNumber);
            if (!TextUtils.isEmpty(mWebsiteUrl)) mTextSiteLink1.setText(mWebsiteUrl);
            if (!TextUtils.isEmpty(mGoogleMapUrl)) mTextSiteLink2.setText(mGoogleMapUrl);


            mRatingBar.setRating(InitialValueSetUp.mRating);
            mTextRatingNum.setText("" + InitialValueSetUp.mRating);

            // Schedule
            if (bObj.getPd_openning_schedule() != null && bObj.getPd_openning_schedule().size() > 0) {
                mTextMondayVal.setText(bObj.getPd_openning_schedule().get(0).split(":", 2)[1]);
                mTextTuesdayVal.setText(bObj.getPd_openning_schedule().get(1).split(":", 2)[1]);
                mTextWednesdayVal.setText(bObj.getPd_openning_schedule().get(2).split(":", 2)[1]);
                mTextThursdayVal.setText(bObj.getPd_openning_schedule().get(3).split(":", 2)[1]);
                mTextFridayVal.setText(bObj.getPd_openning_schedule().get(4).split(":", 2)[1]);
                mTextSaturdayVal.setText(bObj.getPd_openning_schedule().get(5).split(":", 2)[1]);
                mTextSundayVal.setText(bObj.getPd_openning_schedule().get(6).split(":", 2)[1]);
            }

            //   Reviews fetching for particular place
            mReviewList = bObj.getPd_reviews();
            if (mReviewList.size() != 0) {
                mTextGoogleReviewsCount.setText(mReviewList.size() + " Google Reviews");
            }

            if (mReviewList != null) {
                mAdapterReview = new AdapterReviews(mContext, mReviewList);
                mRecycleReview.setAdapter(mAdapterReview);
            } else {
                uihandle.showToast("Reviews unavailable");
            }

            // setting values for purchase plan
            getSessionInstance().setlatitudeTabbed(bObj.getLatitude());
            getSessionInstance().setLongitudeTabbed(bObj.getLongitude());

            getSessionInstance().setAddressTabbed(mAddress);
            getSessionInstance().setPhoneTabbed(mPhoneNumber);
            getSessionInstance().setWebsiteLinkTabbed(mWebsiteUrl);

            // find pin, city stae ,country
            if (this.bObj != null) {
                new SyncGetSplitAddress().execute();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            uihandle.showToast(ex.toString());
        }
    }

    private void getPlaceDetailName(double lt1, double ln1) {
        Geocoder geocoder = new Geocoder(mContext);
        try {
            List<Address> addresses = geocoder.getFromLocation(lt1, ln1, 1);
            if (addresses != null && addresses.size() > 0) {

                for (Address address : addresses) {
                    // uihandle.showToast("" + address);
                    Log.i("info", address.toString());
                }

                if (addresses.get(0).getCountryName() != null) {
                    getSessionInstance().setCountryTabbed(addresses.get(0).getCountryName());
                }

                if (addresses.get(0).getAdminArea() != null) {
                    getSessionInstance().setStateTabbed(addresses.get(0).getAdminArea());
                }
                if (addresses.get(0).getPostalCode() != null) {
                    getSessionInstance().setPinCodeTabbed(addresses.get(0).getPostalCode());
                }

                if (addresses.get(0).getSubAdminArea() != null) {
                    getSessionInstance().setCityTabbed(addresses.get(0).getSubAdminArea());
                } else if (addresses.get(0).getLocality() != null)
                    getSessionInstance().setCityTabbed(addresses.get(0).getLocality());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goForGetPlanDetail() {

        if (!isNetworkConnected()) {
            showToast(NETWORK_ERROR);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("place_id", getSessionInstance().getPlaceIdTabbed());

        new HttpRequester(Request.Method.POST, this, map, 100,
                URLListApis.URL_GET_PLAN_DEATAIL_WITH_PLACEID, this);
    }

    private void goForRenewPlan(String mPriceIdd, String mStatus) {

        if (!isNetworkConnected()) {
            showToast(NETWORK_ERROR);
            return;
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("userid", getSessionInstance().getLoggedId());
        map.put("token", getSessionInstance().getToken());
        map.put("purchaseid", mPriceIdd);
        map.put("status", mStatus);

        new HttpRequester(Request.Method.POST, this, map, 101,
                URLListApis.URL_GET_NEW_PURCHSED_ID, this);

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        try {

            Log.e("Response", response);
            JSONObject jsonObject = new JSONObject(response);

            switch (serviceCode) {
                case 100:
                    refinePlanDetail(jsonObject);
                    break;
                case 101:
                    refineRenewResponse(jsonObject, latest_price);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refineRenewResponse(JSONObject response, String mLatest_price) {
        try {
            if (response != null) {
                Log.d("info", response.toString());
                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        String purchaseId = response.optString("purchaseid");
                        String mNewPrice = response.optString("planprice");
                        mSession.setPurchaseId(purchaseId);
                        //    go for particular payment option selected by the user
                        //    mPresenterPaypal.getPayment(mLatest_price);

                        mPresenterPaypal.getPayment(mNewPrice);

                    } else {
                        uihandle.showToast(response.optString(("message")));
                    }
                } else {
                    uihandle.showToast("Something went wrong!");
                }
            }
        } catch (Exception e) {
            uihandle.showToast(e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If the result is from paypal
        //If the result is from paypal
        if (requestCode == PayPalPresenter.PAYPAL_REQUEST_CODE) {
            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);
                        refineResponse(paymentDetails);
                        //Starting a new activity for the payment details and also putting the payment details with intent
                        /* mContext.startActivity(new Intent(mContext, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));   */

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

    }

    public void refineResponse(String jsonResponse) {
        try {

            JSONObject jsonDetails = new JSONObject(jsonResponse);

            //Displaying payment details
            JSONObject oResponse = jsonDetails.optJSONObject("response");

            paymentCreatedTime = oResponse.optString("create_time"); // date payment initiated
            paymentId = oResponse.optString("id").toString(); // txn id
            mState = oResponse.optString("state").toString(); // status

            mSession.setPaymentStatus(mState);
            mSession.setPaymentTxnId(paymentId);

            if (mState.equals("approved")) {

                goForUpdatePaymentStatus();
            }
            // createSuccessDialog("Payment has been " + mState + ". Thankyou");
        } catch (JSONException e) {
            uihandle.showToast(e.getMessage());
        }
    }

    private void goForUpdatePaymentStatus() {
        new FireApiUpdatePaymentStatus(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
    /* { "status": true, "message": "Payment Received Successfull"  } */
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                //  uihandle.showToast(response.optString(("message")));
                                createSuccessDialog("Your payment has been received. Now you can create Ads for plan purchased.\nGoto-->MyAds-->Create Ads.");
                            } else {
                                uihandle.showToast(response.optString(("message")));
                            }  } else {
                            uihandle.showToast("Something went wrong!");
                        } }

                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                } }
        }.execute(URLListApis.URL_PURCHASE_UPDATESTATUS);

    }

    public void createSuccessDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // for testing purpose after testing uncomment
                        //  startActivity(new Intent(mContext, PaymentSuccessful.class));
                        // uihandle.explicitIntent(PaymentSuccessful.class);


                        Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                        mContext.finish();

                    }
                })
                .show();
    }

    private void refinePlanDetail(JSONObject response) {
        try {
            //  uihandle.showToast(response.optString(("message")));
            if (response.optBoolean("status")) {
                JSONArray jObj = response.optJSONArray("data");
                if (jObj != null) {
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jResponse = jObj.optJSONObject(i);

                        String userid = jResponse.optString("userid").toString().trim();
                        final String purchaseid = jResponse.optString("purchase_id").toString().trim();
                        String placeId = jResponse.optString("place_id").toString().trim();
                        String placeName = jResponse.optString("place_name").toString().trim();
                        String planName = jResponse.optString("plan_name").toString().trim();
                        final String planId = jResponse.optString("plan_id").toString().trim();
                        final String price = jResponse.optString("price").toString().trim();

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
                        latest_price = jResponse.optString("latest_price").toString().trim(); // waiting for the api

                        if (getSessionInstance().getLoggedId().equals(userid)) {
                            mRelRenewPlanLayout.setVisibility(View.VISIBLE);

                            mTextFreeAddTrailStatus.setText(Html.fromHtml(planName + ": <font color='#1D375E'>" + planStatus + "</font>"));
                            mTextExpireDate.setText("EXPIRE: " + ExpiryDate);

                            if (planStatus.equals("active")) {
                                mButtonRenewPlan.setVisibility(View.GONE);
                            }

                            mButtonRenewPlan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (planId.equals("24")) {

                                        //                    createWarningDialog("Your 30 days trail pack is expired. You have to Purchased One Year Subscription Plan To Proceed! at just $" + latest_price + " price", purchaseid);
                                        showRenewDialog(purchaseid);
                                    } else {
                                        goForRenewPlan(purchaseid, "current");
                                    }
                                }
                            });
                        }  }
                }
            } else {
                if (getSessionInstance().isLogged()) {
                    mLinearPurchaseAddPlan.setVisibility(View.VISIBLE);
                }
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

    public void showRenewDialog(final String mPurchaseID) {

        mListPlans = new ArrayList<>();
        mListPlans.add("Renew existing plan");
        mListPlans.add("Upgrade to $99 per year plan");

        new MaterialDialog.Builder(this)
                .title("Choose plan")
                .items(mListPlans)
                .contentColorRes(R.color.colorPrimaryDark)
                .widgetColorRes(R.color.colorPrimaryDark)
                .widgetColor(getResources().getColor(R.color.new_app_blue_color))

                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        if (text != null) {
                            mChoosedPlanForRenew = text.toString();
                            if (which == 0) {
                                goForRenewPlan(mPurchaseID, "current");
                            } else if (which == 1) {
                                goForRenewPlan(mPurchaseID, "upgrade");
                            }
                            //    uihandle.showToast(which + mChoosedPlanForRenew);
//
                        }
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }


    @Override
    protected void onDestroy() {
        mPresenterPaypal.destroy();
        super.onDestroy();
    }

    private class SyncGetSplitAddress extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                getPlaceDetailName(Double.parseDouble(bObj.getLatitude()), Double.parseDouble(bObj.getLongitude()));
                return "Pass";
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String aData) {
            super.onPostExecute(aData);
        }
    }


}
