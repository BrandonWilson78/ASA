package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.placedetail_.BeanPlaceDetail;
import za.asa_media.awesome_sa.modules_.placedetail_.BeanPlaceDetailReviews;
import za.asa_media.awesome_sa.modules_.placedetail_.adapter.AdapterGoogleImages;
import za.asa_media.awesome_sa.modules_.placedetail_.adapter.AdapterReviews;
import za.asa_media.awesome_sa.modules_.placedetail_.async.PlaceDetailCallApi;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.paypal_.PayPalPresenter;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

import static za.asa_media.awesome_sa.R.id.rating_review_bottom;

/**
 * Created by Snow-Dell-05 on 18-Apr-18.
 */

public class FragmentBusinessView extends Fragment implements OnStreetViewPanoramaReadyCallback,
        View.OnClickListener,
        OnMapReadyCallback {

    private String latest_price = "";

    private View mView = null;
    private Activity mContext;
    private UiHandleMethods uihandle;
    private Button mBtnPurchasePlan;
    //   detail parameters
    private TextView mTextAddress;
    private TextView mTextMondayVal, mTextTuesdayVal, mTextWednesdayVal, mTextThursdayVal, mTextFridayVal, mTextSaturdayVal, mTextSundayVal;
    private RatingBar mRatingBar;
    //  Contacts View Declarations Textview_site_link
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
    // Review
    // Recycle views Reviews
    private RecyclerView mRecycleReview;
    private ArrayList<BeanPlaceDetailReviews> mReviewList;
    private AdapterReviews mAdapterReview;

    private BeanPlaceDetail bObj;

    //  Google map setup decalarations
    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private SupportStreetViewPanoramaFragment mSupportStreetFragment;
    private ViewFlipper mFlipper;

    private ImageView mImageViewPhone, mImageViewPhoneOnMap;

    //  Views declarations Address
    private SessionCityOculus mSession;

    private PayPalPresenter mPresenterPaypal;
    private List<String> mListPlans;
    private String mChoosedPlanForRenew = "";
    private String paymentDetails;
    private String mState, paymentId, paymentCreatedTime;

    //####  callback  ####\\
    private IBusinessViewCallBack mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uihandle = new UiHandleMethods(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.my_test_layout_2, container, false);
        }

        init(mView);
        getPlaceDetail();

        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        if (mContext instanceof LoggedInUserDashboard) {
            mCallback = (IBusinessViewCallBack) activity;
        }

    }

    private void init(View v) {
        uihandle = new UiHandleMethods(mContext);
        mPresenterPaypal = new PayPalPresenter(mContext);
        uihandle.setStatusBarColor(ContextCompat.getColor(mContext, R.color.new_app_blue_color));
        mSession = new SessionCityOculus(mContext);

        mReviewList = new ArrayList<>();
        mListGoogleImageReferences = new ArrayList<>();
        mFlipper = (ViewFlipper) v.findViewById(R.id.frame_street_map);

        mBtnPurchasePlan = (Button) v.findViewById(R.id.btn_purchase_plan);
        mImageViewPhone = v.findViewById(R.id.imageView_phone);
        mImageViewPhoneOnMap = v.findViewById(R.id.imageView_phone_map);


        mTextAddress = (TextView) v.findViewById(R.id.text_place_detail_address);
        mRatingBar = (RatingBar) v.findViewById(R.id.ratingbar_place_detail_);

        //    contacts
        mTextPhoneNumber = (TextView) v.findViewById(R.id.textview_phone_number);
        mTextSiteLink1 = (TextView) v.findViewById(R.id.textview_site_link);
        mTextSiteLink2 = (TextView) v.findViewById(R.id.map_link);

        // Schedule Views
        mTextMondayVal = (TextView) v.findViewById(R.id.textview_monday_value);
        mTextTuesdayVal = (TextView) v.findViewById(R.id.textview_tuesday_value);
        mTextWednesdayVal = (TextView) v.findViewById(R.id.textview_wednesday_value);
        mTextThursdayVal = (TextView) v.findViewById(R.id.textview_thursday_value);
        mTextFridayVal = (TextView) v.findViewById(R.id.textview_friday_value);
        mTextSaturdayVal = (TextView) v.findViewById(R.id.textview_saturday);
        mTextSundayVal = (TextView) v.findViewById(R.id.textview_sunday_value);


        mLinearOpeningHours = (LinearLayout) v.findViewById(R.id.linear_opening_hours);

        mTextOpenNow = (TextView) v.findViewById(R.id.text_open_now);
        mTextNoMoreImages = (TextView) v.findViewById(R.id.txt_place_imag_g);
        mRelativeReviewsLay = (RelativeLayout) v.findViewById(rating_review_bottom);
        mRecycleGoogleImages = (RecyclerView) v.findViewById(R.id.recycle_photo_place_list);
        mRecycleGoogleImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        mLinearDirection = (LinearLayout) v.findViewById(R.id.linear_direction);
        mTextRatingNum = (TextView) v.findViewById(R.id.text_place_detail_rating);
        mTextGoogleReviewsCount = (TextView) v.findViewById(R.id.text_place_detail_google_reviews_count);

        // Recycler view
        mRecycleReview = (RecyclerView) v.findViewById(R.id.recycle_reviews_place_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecycleReview.setLayoutManager(mLayoutManager);

        //  Todo street view fragment
        mSupportStreetFragment = (SupportStreetViewPanoramaFragment) getChildFragmentManager().findFragmentById(R.id.street_view_fragmentt);
        mSupportStreetFragment.getStreetViewPanoramaAsync(this);


        mBtnPurchasePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   uihandle.explicitIntent(ChoosePlan.class);
                mSession.setNavIndex(true);
                mSession.setFlagFromDetail(true);

                Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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
        mImageViewPhone.setOnClickListener(this);
        mImageViewPhoneOnMap.setOnClickListener(this);

        // #########  calling api for business detail #######//
        mCallback.getBusinessViewDetails(v);
    }

    @Override
    public void onResume() {
        super.onResume();
        //  googlemap
        if (!TextUtils.isEmpty(String.valueOf(mSession.getBLat())) && !TextUtils.isEmpty(String.valueOf(mSession.getBLon()))) {
            mSupportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
            if (mSupportMapFragment != null) {
                mSupportMapFragment.getMapAsync(this);
            }

        } else {
            uihandle.showToast("Google doesn't get your location");
        }


    }

    public void goMakeCall() {
        if (!mTextPhoneNumber.getText().toString().equals("N/A")) {
            uihandle.callDialog(mTextPhoneNumber.getText().toString(), mTextPhoneNumber.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.imageView_phone:
                goMakeCall();
                break;


            case R.id.imageView_phone_map:
                goMakeCall();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // For showing a move to my location button
        // mGoogleMap.
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);


        goForUpdateInfoOnMap(Double.parseDouble(mSession.getBLat()), Double.parseDouble(mSession.getBLon()));

    }

    private void goForUpdateInfoOnMap(double lt, double ln) {
        // For dropping a marker at a point on the Map
        LatLng ltlg = new LatLng(lt, ln);
        mGoogleMap.addMarker(new MarkerOptions().position(ltlg).title(mSession.getBName()));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ltlg).zoom(12).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void getPlaceDetail() {
        //  mFrameMap.setVisibility(View.VISIBLE);
        //  mFrameStreet.setVisibility(View.VISIBLE);
        InitialValueSetUp.mPlaceId = mSession.getBPlaceId();

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
                InitialValueSetUp.statusOpening = "OPEN";
            } else {
                mTextOpenNow.setText(Html.fromHtml("Hours: <font color='#1D375E'>Closed now</font>"));
                InitialValueSetUp.statusOpening = "CLOSED";
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
            //    mTextPlaceDetailName.setText(InitialValueSetUp.mPlaceName);
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

            // for next view set values
            //Distance
            Location startPoint = new Location("locationA");
            startPoint.setLatitude(Double.parseDouble(ReservedLocation.getSingletonInstance().getCurret_lat()));
            startPoint.setLongitude(Double.parseDouble(ReservedLocation.getSingletonInstance().getCurrent_lng()));

            Location endPoint = new Location("locationB");
            endPoint.setLatitude(Double.parseDouble(bObj.getLatitude()));
            endPoint.setLongitude(Double.parseDouble(bObj.getLongitude()));

            //distance in km
            final double distance = (startPoint.distanceTo(endPoint)) / 1000;

            InitialValueSetUp.mPlaceAddress = mTextAddress.getText().toString();
            InitialValueSetUp.mDistancePlace = new DecimalFormat("###.##").format(distance) + "k.m";

            //     mTextViewSpecialDistance.setText("" + InitialValueSetUp.mDistancePlace);
            //     mTextViewSpecialAddress.setText("" + );

            if (!TextUtils.isEmpty(mPhoneNumber)) mTextPhoneNumber.setText(mPhoneNumber);
            if (!TextUtils.isEmpty(mWebsiteUrl)) mTextSiteLink1.setText(mWebsiteUrl);
            if (!TextUtils.isEmpty(mGoogleMapUrl)) mTextSiteLink2.setText(mGoogleMapUrl);


            if (bObj.getRatingCount() != null) {
                mRatingBar.setRating(Float.parseFloat(bObj.getRatingCount()));
                mTextRatingNum.setText("" + bObj.getRatingCount());
                // set for the next view android
                InitialValueSetUp.mRating = Float.parseFloat(bObj.getRatingCount());
            }


            //  Schedule
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
            mSession.setlatitudeTabbed(bObj.getLatitude());
            mSession.setLongitudeTabbed(bObj.getLongitude());

            mSession.setAddressTabbed(mAddress);
            mSession.setPhoneTabbed(mPhoneNumber);
            mSession.setWebsiteLinkTabbed(mWebsiteUrl);

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
                    mSession.setCountryTabbed(addresses.get(0).getCountryName());
                }

                if (addresses.get(0).getAdminArea() != null) {
                    mSession.setStateTabbed(addresses.get(0).getAdminArea());
                }
                if (addresses.get(0).getPostalCode() != null) {
                    mSession.setPinCodeTabbed(addresses.get(0).getPostalCode());
                }

                if (addresses.get(0).getSubAdminArea() != null) {
                    mSession.setCityTabbed(addresses.get(0).getSubAdminArea());
                } else if (addresses.get(0).getLocality() != null)
                    mSession.setCityTabbed(addresses.get(0).getLocality());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mPresenterPaypal.destroy();
        super.onDestroy();
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        Log.e("street", "inside");
        if (!TextUtils.isEmpty(String.valueOf(mSession.getBLat())) && !TextUtils.isEmpty(String.valueOf(mSession.getBLon()))) {

            LatLng latLng = new LatLng(Double.parseDouble(mSession.getBLat()), Double.parseDouble(mSession.getBLon()));
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
        } else {
            mFlipper.removeViewAt(0);
        }


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
