package za.asa_media.awesome_sa.modules_.placedetail_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.catalogue.CatalogActivity;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.event.EventsActivity;
import za.asa_media.awesome_sa.modules_.news.NewsActivity;
import za.asa_media.awesome_sa.modules_.placedetail_.adapter.AdapterGoogleImages;
import za.asa_media.awesome_sa.modules_.placedetail_.adapter.AdapterReviews;
import za.asa_media.awesome_sa.modules_.placedetail_.async.FireApiAddToFavourite;
import za.asa_media.awesome_sa.modules_.placedetail_.async.FireApiCheckForFavourite;
import za.asa_media.awesome_sa.modules_.placedetail_.async.FireApiGetSocialInfo;
import za.asa_media.awesome_sa.modules_.placedetail_.async.PlaceDetailCallApi;
import za.asa_media.awesome_sa.modules_.promotion.PromotionActivity;
import za.asa_media.awesome_sa.modules_.special.SpecialActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

import static za.asa_media.awesome_sa.R.id.rating_review_bottom;

public class PlaceDetailActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        OnStreetViewPanoramaReadyCallback,
        View.OnClickListener {

    // Device id
    private String deviceId, plId;
    // Views declarations Address
    private TextView mTextPlaceDetailName;
    private Activity mContext = this;
    private ImageView mImageViewbackHeader, mImageCompass, mImageStar, mImageDownload;
    private ImageView mImageSpecial, mImageEvents, mImagePromotions, mImageNews, mImageCataloge;
    private TextView mTextSpecial, mTextEvents, mTextPromotions, mTextNews, mTextCataloge;

    private TextView mTextAddress, mTextDistance;
    private TextView mTextMondayVal, mTextTuesdayVal, mTextWednesdayVal, mTextThursdayVal, mTextFridayVal, mTextSaturdayVal, mTextSundayVal;
    private RatingBar mRatingBar;
    private UiHandleMethods mUiHandleMethods = null;
    // Contacts view declarations textview_site_link
    private TextView mTextPhoneNumber, mTextSiteLink1, mTextSiteLink2;
    private String mAddress, mPhoneNumber, mWebsiteUrl, mGoogleMapUrl;
    private LatLng latLng;
    // Google map setup decalarations
    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private StreetViewPanoramaFragment mStreetViewFragment;
    //
    private RelativeLayout mFrameMap, mFrameStreet;
    // Recycleviews Reviews
    private RecyclerView mRecycleReview;
    private ArrayList<BeanPlaceDetailReviews> mReviewList;
    private AdapterReviews mAdapterReview;
    private boolean mFlagFav = false;
    private ViewFlipper mFlipper;

    private BeanPlaceDetail bObj;

    // Session declarations
    private SessionCityOculus mSession;

    private String shareDetail = "I found this on CitiOculus App. Let's go check it out!";

    // social icons info
    private TextView mtextViewSocialHeading;
    private LinearLayout mLinearSocialIcon;
    private ImageView mImageFacebook, mImageInsta, mImageGoogle, mImageYoutube;
    private String mGoogleLink, mFacebookLink, mInstaLink, mYoutubeLink;
    private LinearLayout mLinearDirection;
    private TextView mTextRatingNum, mTextGoogleReviewsCount;
    private boolean mFlagOpenNow = false;
    private LinearLayout mLinearOpeningHours;
    private RelativeLayout mRelativeReviewsLay;

    //...//...//....//..../..../..../..../...../...../...
    private RecyclerView mRecycleGoogleImages;
    private List<String> mListGoogleImageReferences;
    private AdapterGoogleImages mAdapterGoogelImages;
    private TextView mTextOpenNow;
    private TextView mTextNoMoreImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_place_details);
        //     Calling init views for initialisation
        initViews();
        getPlaceDetail();
        //    Calling share api for fetching share detail
        //    New FireApiGetShareLink(mContext).execute(URLListApis.URL_GET_SHARE_LINK);

    }

    private void initViews() {

        // Initialisations
        mUiHandleMethods = new UiHandleMethods(this);
        mSession = new SessionCityOculus(mContext);
        deviceId = mSession.getDeviceToken();
        mReviewList = new ArrayList<>();
        mListGoogleImageReferences = new ArrayList<>();
        mLinearOpeningHours = (LinearLayout) findViewById(R.id.linear_opening_hours);

        mTextOpenNow = (TextView) findViewById(R.id.text_open_now);
        mTextNoMoreImages = (TextView) findViewById(R.id.txt_place_imag_g);
        mRelativeReviewsLay = (RelativeLayout) findViewById(rating_review_bottom);
        mRecycleGoogleImages = (RecyclerView) findViewById(R.id.recycle_photo_place_list);
        mRecycleGoogleImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mLinearDirection = (LinearLayout) findViewById(R.id.linear_direction);
        mTextRatingNum = (TextView) findViewById(R.id.text_place_detail_rating);
        mTextGoogleReviewsCount = (TextView) findViewById(R.id.text_place_detail_google_reviews_count);


        mFlipper = (ViewFlipper) findViewById(R.id.frame_street_map);
        mFrameMap = (RelativeLayout) findViewById(R.id.frame_map);
        mFrameStreet = (RelativeLayout) findViewById(R.id.frame_street_view);

        mImageViewbackHeader = (ImageView) findViewById(R.id.img_back_header);

        // below street map view
        mImageCompass = (ImageView) findViewById(R.id.img_compass);
        mImageStar = (ImageView) findViewById(R.id.img_star);
        mImageDownload = (ImageView) findViewById(R.id.img_download);

        mImageSpecial = (ImageView) findViewById(R.id.batch_img_place_detail_special);
        mImageEvents = (ImageView) findViewById(R.id.batch_img_place_detail_events);
        mImagePromotions = (ImageView) findViewById(R.id.batch_img_place_detail_promotion);
        mImageCataloge = (ImageView) findViewById(R.id.batch_img_place_detail_catalog);
        mImageNews = (ImageView) findViewById(R.id.batch_img_place_detail_news);

        mTextSpecial = (TextView) findViewById(R.id.batch_text_place_detail_special);
        mTextEvents = (TextView) findViewById(R.id.batch_text_place_detail_events);
        mTextPromotions = (TextView) findViewById(R.id.batch_text_place_detail_promotion);
        mTextNews = (TextView) findViewById(R.id.batch_text_place_detail_news);
        mTextCataloge = (TextView) findViewById(R.id.batch_text_place_detail_catalog);


        mTextAddress = (TextView) findViewById(R.id.text_place_detail_address);
        mTextDistance = (TextView) findViewById(R.id.text_place_detail_distance);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar_place_detail_);

        // contacts
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

        // place details name on header
        mTextPlaceDetailName = (TextView) findViewById(R.id.txt_place_detail_name);

        // social icon info
        mtextViewSocialHeading = (TextView) findViewById(R.id.txt_social_heading);
        mLinearSocialIcon = (LinearLayout) findViewById(R.id.social_icon_layout);
        mImageFacebook = (ImageView) findViewById(R.id.social_fb);
        mImageInsta = (ImageView) findViewById(R.id.social_insta);
        mImageGoogle = (ImageView) findViewById(R.id.social_google);
        mImageYoutube = (ImageView) findViewById(R.id.social_share);

        // Recycler view
        mRecycleReview = (RecyclerView) findViewById(R.id.recycle_reviews_place_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleReview.setLayoutManager(mLayoutManager);

        //  googlemap
        if (!TextUtils.isEmpty(String.valueOf(InitialValueSetUp.lat)) && !TextUtils.isEmpty(String.valueOf(InitialValueSetUp.lng))) {
            mSupportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            mSupportMapFragment.getMapAsync(PlaceDetailActivity.this);
        } else {
            mUiHandleMethods.showToast("Google doesn't get your location");
        }
        //  Todo street view fragment
        mStreetViewFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.street_view_fragment);
        mStreetViewFragment.getStreetViewPanoramaAsync(this);

        mImageViewbackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceDetailActivity.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                // mSupportMapFragment = null;
            }
        });


        //    if (getIntent() != null) {

        //      String batchs[] = (String[]) getIntent().getCharSequenceArrayExtra("batchArray");

        //   plId = getIntent().getStringExtra("pl_id");

        // mUiHandleMethods.showToast(plId);
        // get batchs id
        // mUiHandleMethods.showToast(InitialValueSetUp.mPlaceId);
        //

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

        // set Listeners
        mImageCompass.setOnClickListener(this);
        mTextSiteLink1.setOnClickListener(this);
        mTextSiteLink2.setOnClickListener(this);
        mTextPhoneNumber.setOnClickListener(this);
        mTextOpenNow.setOnClickListener(this);
        mImageStar.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mRelativeReviewsLay.setOnClickListener(this);
        mLinearDirection.setOnClickListener(this);

        // check for fav if have put star
        checkForFav();
        // GET SOCIAL INFO ICONS
        getSocialIconInfo();
        // listener to socials icons
        socialIconClicks();

    }



    private void socialIconClicks() {
        mImageGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mGoogleLink)) {
                    mUiHandleMethods.openWebLink("http://" + mGoogleLink.trim());
                }
            }
        });

        mImageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mFacebookLink)) {
                    mUiHandleMethods.openWebLink("http://" + mFacebookLink.trim());
                }
            }
        });

        mImageInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mInstaLink)) {
                    mUiHandleMethods.openWebLink("http://" + mInstaLink.trim());
                }
            }
        });


        mImageYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mYoutubeLink)) {
                    mUiHandleMethods.openWebLink("http://" + mYoutubeLink.trim());
                }
            }
        });

    }

    public void getPlaceDetail() {
        //  mFrameMap.setVisibility(View.VISIBLE);
        //  mFrameStreet.setVisibility(View.VISIBLE);
        new PlaceDetailCallApi(this) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mUiHandleMethods.startProgress("Place detail is being fetched");
            }

            @Override
            protected void onPostExecute(BeanPlaceDetail beanPlaceDetail) {
                super.onPostExecute(beanPlaceDetail);
                try {
                    mUiHandleMethods.stopProgressDialog();
                    if (beanPlaceDetail != null) {
                        // calling update ui method to update views
                        updateUi(beanPlaceDetail);
                    } else {
                        mUiHandleMethods.showToast("Sorry! Place detail not found");
                    }
                } catch (Exception e) {
                    Log.d("ASA", e.toString());
                }
            }
        }.execute();
    }

    private void updateUi(BeanPlaceDetail bObj) {

        //  put open now
        if (bObj.isFlagOpenNow()) {
            mTextOpenNow.setText(Html.fromHtml("Hours: <font color='#864149'>Open now</font>"));
        } else {
            mTextOpenNow.setText(Html.fromHtml("Hours: <font color='#BC262C'>Closed now</font>"));
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

        // check for weblink
        if (TextUtils.isEmpty(mWebsiteUrl)) {
            website = "";
        } else {
            website = " Website: " + mWebsiteUrl;
        }
        shareDetail += " " + InitialValueSetUp.mPlaceName + " " + mAddress + phn + "\n" + website + "\n\nDownload CitiOculus app my friend! iTunes and Google Play Store\n\n" + "Itunes app store link: http://apple.co/2wFxWu8\n" +
                "Play store link: https://play.google.com/store/apps/details?id=za.asa_media.awesome_sa&hl=en";

        // apply values to views
        mTextAddress.setText(Html.fromHtml("<font color='#7F7F7F'>ADDRESS<br></font>" + mAddress));
        if (!TextUtils.isEmpty(mPhoneNumber)) mTextPhoneNumber.setText(mPhoneNumber);
        if (!TextUtils.isEmpty(mWebsiteUrl)) mTextSiteLink1.setText(mWebsiteUrl);
        if (!TextUtils.isEmpty(mGoogleMapUrl)) mTextSiteLink2.setText(mGoogleMapUrl);

        mTextDistance.setText(InitialValueSetUp.mDistance);
        mRatingBar.setRating(InitialValueSetUp.mRating);
        mTextRatingNum.setText("" + InitialValueSetUp.mRating);

        //Schedule
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
            mUiHandleMethods.showToast("Reviews unavailable");
        }


    }

    public void goMakeCall(View v) {
        if (!mTextPhoneNumber.getText().toString().equals("N/A")) {
            mUiHandleMethods.callDialog(mTextPhoneNumber.getText().toString(), mTextPhoneNumber.getText().toString());
        }
    }

    //         Street view callback when street view ready
    @Override
    public void onStreetViewPanoramaReady(final StreetViewPanorama streetViewPanorama) {

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
    protected void onResume() {
        super.onResume();
        mUiHandleMethods.hideSoftKeyboard();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.batch_img_place_detail_catalog:

                InitialValueSetUp.adBatchId = "1";
                InitialValueSetUp.adPlaceId = InitialValueSetUp.mPlaceId;

                intent = new Intent(mContext, CatalogActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_events:
                // mUiHandleMethods.explicitIntent(EventsActivity.class);
                InitialValueSetUp.adPlaceId = InitialValueSetUp.mPlaceId;
                InitialValueSetUp.adBatchId = "2";
                intent = new Intent(mContext, EventsActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_promotion:
                //  mUiHandleMethods.explicitIntent(PromotionActivity.class);
                InitialValueSetUp.adPlaceId = InitialValueSetUp.mPlaceId;
                InitialValueSetUp.adBatchId = "3";
                intent = new Intent(mContext, PromotionActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_news:
                //  mUiHandleMethods.explicitIntent(NewsActivity.class);
                InitialValueSetUp.adPlaceId = InitialValueSetUp.mPlaceId;
                InitialValueSetUp.adBatchId = "5";
                intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_special:
                InitialValueSetUp.adPlaceId = InitialValueSetUp.mPlaceId;
                InitialValueSetUp.adBatchId = "4";
                intent = new Intent(mContext, SpecialActivity.class);
                intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.textview_phone_number:
                if (!mTextPhoneNumber.getText().toString().equals("N/A")) {
                    mUiHandleMethods.callDialog(mTextPhoneNumber.getText().toString(), mTextPhoneNumber.getText().toString());
                }
                break;
            case R.id.textview_site_link:
                if (!mTextSiteLink1.getText().toString().trim().equals("N/A")) {
                    mUiHandleMethods.openWebLink(mTextSiteLink1.getText().toString().trim());
                }

                break;
            case R.id.map_link:
                if (!mTextSiteLink2.getText().toString().trim().equals("N/A")) {
                    mUiHandleMethods.openWebLink(mTextSiteLink2.getText().toString().trim());
                }
                break;
            case R.id.img_download:
                mUiHandleMethods.shareMessage(shareDetail);
                break;

            case R.id.img_compass:
                if (!TextUtils.isEmpty(mGoogleMapUrl)) {
                    mUiHandleMethods.openWebLink(mGoogleMapUrl);
                } else {
                    mUiHandleMethods.showToast("Sorry, Direction is not specified");
                }
                break;
            case R.id.linear_direction:
                if (!TextUtils.isEmpty(mGoogleMapUrl)) {
                    mUiHandleMethods.openWebLink(mGoogleMapUrl);
                } else {
                    mUiHandleMethods.showToast("Sorry, Direction is not specified");
                }
                break;

            case R.id.img_star:

                // Toast.makeText(this, deviceId, Toast.LENGTH_SHORT).show();
                if (!mFlagFav) {
                    mImageStar.setImageResource(R.drawable.star_fav_fill);
                    mFlagFav = true;
                    new FireApiAddToFavourite(mContext).
                            execute(URLListApis.URL_ADD_TO_FAVOURITE, InitialValueSetUp.mPlaceId, deviceId, InitialValueSetUp.mDistance, "" + InitialValueSetUp.mRating, mAddress, InitialValueSetUp.mPlaceName, String.valueOf(InitialValueSetUp.lat), String.valueOf(InitialValueSetUp.lng));
                } else {
                    mImageStar.setImageResource(R.drawable.star);
                    mFlagFav = false;
                    new FireApiAddToFavourite(mContext).
                            execute(URLListApis.URL_ADD_TO_FAVOURITE, InitialValueSetUp.mPlaceId, deviceId, InitialValueSetUp.mDistance, "" + InitialValueSetUp.mRating, mAddress, InitialValueSetUp.mPlaceName, String.valueOf(InitialValueSetUp.lat), String.valueOf(InitialValueSetUp.lng));
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

    private void goForUpdateInfoOnMap(double lt, double ln) {
        // For dropping a marker at a point on the Map
        LatLng ltlg = new LatLng(lt, ln);
        mGoogleMap.addMarker(new MarkerOptions().position(ltlg).title(InitialValueSetUp.mPlaceName));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ltlg).zoom(12).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO not working stree view
        //    mFrameStreet.setVisibility(View.GONE);
        //

        mGoogleMap = googleMap;
        // For showing a move to my location button
        // mGoogleMap.
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);

        goForUpdateInfoOnMap(InitialValueSetUp.lat, InitialValueSetUp.lng);

    }

    private void checkForFav() {
        // mUiHandleMethods.showToast(plId + "\n" + deviceId);
        new FireApiCheckForFavourite(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    Log.d("info", response.toString());
                    if (response != null) {
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                //uihandle.showToast(response.optString(("message")));
                                InitialValueSetUp.favFlag = response.optString("message");
                                //   mUiHandleMethods.showToast("" + InitialValueSetUp.favFlag);

                                if (InitialValueSetUp.favFlag.equals("1")) {
                                    mFlagFav = true;
                                    mImageStar.setImageResource(R.drawable.star_fav_fill);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    mUiHandleMethods.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_CHECK_FOR_FAVOURITE, InitialValueSetUp.mPlaceId, deviceId);

    }

    private void getSocialIconInfo() {
        // mUiHandleMethods.showToast(plId + "\n" + deviceId);
        new FireApiGetSocialInfo(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    Log.d("info", response.toString());
                    if (response != null) {
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                //uihandle.showToast(response.optString(("message")));

                                if (response.has("data")) {
                                    JSONArray jArray = response.optJSONArray("data");
                                    if (jArray != null) {

                                        JSONObject jObj = jArray.optJSONObject(0);

                                        /*
                   "f1": "www.facebook.com/uksushi",
            "l1": "www.linkdin.com/limko",
            "t1": "www.twitter.com/twitto_page",
                   "i1": "www.insta.com/intopage",
            "y1": "www.youtube.com/pannel",
            "v1": "www.vimeo.co/vimko",
            "s1": "www.snapchat.com/shaap",
                   "g1": "www.google.com/pkshs"
                          */
                                        mFacebookLink = jObj.optString("f1").trim();
                                        String l1 = jObj.optString("l1").trim();
                                        String t1 = jObj.optString("t1").trim();
                                        mInstaLink = jObj.optString("i1").trim();
                                        mYoutubeLink = jObj.optString("y1").trim();
                                        String v1 = jObj.optString("v1").trim();
                                        String s1 = jObj.optString("s1").trim();
                                        mGoogleLink = jObj.optString("g1").trim();

                                        if (!TextUtils.isEmpty(mFacebookLink)) {
                                            mtextViewSocialHeading.setVisibility(View.VISIBLE);
                                            mLinearSocialIcon.setVisibility(View.VISIBLE);
                                            mImageFacebook.setVisibility(View.VISIBLE);

                                        }
                                        if (!TextUtils.isEmpty(mInstaLink)) {
                                            mtextViewSocialHeading.setVisibility(View.VISIBLE);
                                            mLinearSocialIcon.setVisibility(View.VISIBLE);
                                            mImageInsta.setVisibility(View.VISIBLE);
                                        }

                                        if (!TextUtils.isEmpty(mGoogleLink)) {
                                            mtextViewSocialHeading.setVisibility(View.VISIBLE);
                                            mLinearSocialIcon.setVisibility(View.VISIBLE);
                                            mImageGoogle.setVisibility(View.VISIBLE);
                                        }

                                        if (!TextUtils.isEmpty(mYoutubeLink)) {
                                            mtextViewSocialHeading.setVisibility(View.VISIBLE);
                                            mLinearSocialIcon.setVisibility(View.VISIBLE);
                                            mImageYoutube.setVisibility(View.VISIBLE);

                                        }


                                    }
                                } else {
                                    mUiHandleMethods.showToast("Something went wrong on Server");
                                }
                            } else {

                                // mUiHandleMethods.showToast(response.optString("message"));
                            }


                        }
                    }
                } catch (Exception e) {
                    mUiHandleMethods.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_GET_SOCIAL_INFO, InitialValueSetUp.mPlaceId);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mUiHandleMethods = null;
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}


  /*
     @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          if (requestCode == PermissionCheck.PERMISSION_CODE) {
              for (int i = 0; i < grantResults.length; i++) {
                  if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                      if (i == grantResults.length - 1) {

                          if (!mTextPhoneNumber.getText().toString().equals("N/A")) {
                              mUiHandleMethods.callDialog(mTextPhoneNumber.getText().toString(), mTextPhoneNumber.getText().toString());
                              mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTextPhoneNumber.getText().toString().trim())));

                          }

                      }


                  } else {

                      mUiHandleMethods.showToast("Permission ungranted");
                      mContext.finish();
                      mContext.moveTaskToBack(true);
                      return;
                  }
              }
          }

      }*/