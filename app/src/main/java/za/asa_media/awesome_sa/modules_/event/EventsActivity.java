package za.asa_media.awesome_sa.modules_.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import me.relex.circleindicator.CircleIndicator;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.FireApi.FireApiGetAdsInfoWithBatchId;
import za.asa_media.awesome_sa.modules_.catalogue.CatalogActivity;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.SelectedPlaceListdata;
import za.asa_media.awesome_sa.modules_.news.NewsActivity;
import za.asa_media.awesome_sa.modules_.promotion.PromotionActivity;
import za.asa_media.awesome_sa.modules_.special.SpecialActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {


    private Activity mContext = this;
    private TextView
            mTextViewSpecialPlaceName, mTextViewSpecialDistance,
            mTextViewSpecialAddress, mTextViewSpecialStatus,
            mTextViewSpecialName, mTextViewSpecialDecription,
            mTextViewStartDate, mTextViewSpecialEndDate,
            mTextViewSpecialEventLocation, mTextViewEventTime,


    mTextViewSpecialEmailUrl,
            mTextViewSpecialWebsiteUrl,
            mTextViewSpecialLandline,
            mTextViewSpecialMobile;


    private ImageView mImageViewSpecialImage;
    private View mViewSpecialStatusView;
    private RatingBar mSpecialRatingBar;

    private ImageView mBatchSpecial, mBatchEvent, mBatchPromotion, mBatchNews, mBatchCatalogue;
    private ImageView mImageViewSpecialCancel, mImageViewFooterBack, mImageViewFooterForword;

    // Other helping objects
    private UiHandleMethods uihandle;
    private SelectedPlaceListdata mPlaceDateObject;
    private String imge_path;

    // view pager decalration s
    private ViewPager mViewPager;
    private AdapterEventUser mAdapterEvent;
    private CircleIndicator mCircleIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // calling init views for initialsetup
        initViews();

    }

    private void initViews() {

        // views initialisations
        uihandle = new UiHandleMethods(mContext);
        mImageViewSpecialCancel = (ImageView) findViewById(R.id.img_header_cancel);
        mImageViewFooterBack = (ImageView) findViewById(R.id.img_footer_back);
        mImageViewFooterForword = (ImageView) findViewById(R.id.img_footer_forword);


        // views
        mTextViewSpecialPlaceName = (TextView) findViewById(R.id.txt_nearby_place_name);
        mTextViewSpecialAddress = (TextView) findViewById(R.id.txt_nearby_address);
        mTextViewSpecialDistance = (TextView) findViewById(R.id.txt_nearby_distance);
        mTextViewSpecialStatus = (TextView) findViewById(R.id.txt_nearby_status);
        mViewSpecialStatusView = findViewById(R.id.status_view);
        //  mImageViewSpecialImage = (ImageView) findViewById(R.id.image_batch_layout);


        // header batchs
        mBatchSpecial = (ImageView) findViewById(R.id.batch_special);
        mBatchEvent = (ImageView) findViewById(R.id.batch_event);
        mBatchPromotion = (ImageView) findViewById(R.id.batch_promotion);
        mBatchNews = (ImageView) findViewById(R.id.batch_news);
        mBatchCatalogue = (ImageView) findViewById(R.id.batch_catalogue);


        mTextViewSpecialName = (TextView) findViewById(R.id.txt_special_name);
        mTextViewSpecialDecription = (TextView) findViewById(R.id.txt_description);
        mTextViewStartDate = (TextView) findViewById(R.id.txt_start_date);
        mTextViewSpecialEndDate = (TextView) findViewById(R.id.txt_end_date);
        mTextViewSpecialEventLocation = (TextView) findViewById(R.id.txt_event_location);

        mTextViewEventTime = (TextView) findViewById(R.id.txt_event_time);

        mTextViewSpecialEmailUrl = (TextView) findViewById(R.id.txt_email_);
        mTextViewSpecialWebsiteUrl = (TextView) findViewById(R.id.txt_website_link);
        mTextViewSpecialLandline = (TextView) findViewById(R.id.txt_landline_);
        mTextViewSpecialMobile = (TextView) findViewById(R.id.txt_mobile_);
        mSpecialRatingBar = (RatingBar) findViewById(R.id.ratingBar_nearby);

        // view pager setup
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.indicator);


        // check intent for values
        if (getIntent() != null && getIntent().hasExtra("objPlace")) {
            mPlaceDateObject = getIntent().getParcelableExtra("objPlace");
            // setting values to views
            mTextViewSpecialPlaceName.setText("" + mPlaceDateObject.getPs_name());
            mSpecialRatingBar.setRating((float) mPlaceDateObject.getPs_rating());

            if (mPlaceDateObject.isPs_opening_status()) {
                mTextViewSpecialStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                mTextViewSpecialStatus.setText("OPEN");
                mViewSpecialStatusView.setBackground(mContext.getResources().getDrawable(R.drawable.green_circle));
            } else {
                mTextViewSpecialStatus.setText("CLOSED");
            }

            mTextViewSpecialDistance.setText("" + getIntent().getStringExtra("distance"));
            mTextViewSpecialAddress.setText("" + mPlaceDateObject.getPs_formatted_address());

        } else/* if (getIntent().hasExtra("address"))*/ {


            try {
                // uncheck all batches before highlights batchs
                clickableBatchs();
                InitialValueSetUp.adBatchId = "2";
                String batchs[] = InitialValueSetUp.mBatchs;
                if (batchs != null) {
                    for (String batch : batchs) {
                        Log.d("batch", "" + batch);
                        if (batch.equals("1")) {
                            mBatchCatalogue.setEnabled(true);
                            mBatchCatalogue.setImageResource(R.drawable.catalog);

                        }
                        if (batch.equals("2")) {
                            mBatchEvent.setEnabled(true);
                            mBatchEvent.setImageResource(R.drawable.events);

                        }
                        if (batch.equals("3")) {
                            mBatchPromotion.setEnabled(true);
                            mBatchPromotion.setImageResource(R.drawable.promotions);

                            // mImagePromotions.setVisibility(View.VISIBLE);
                        }

                        if (batch.equals("4")) {
                            //mImageSpecial.setVisibility(View.VISIBLE);
                            mBatchSpecial.setEnabled(true);
                            mBatchSpecial.setImageResource(R.drawable.specials);


                        }
                        if (batch.equals("5")) {
                            // mImageNews.setVisibility(View.VISIBLE);
                            mBatchNews.setEnabled(true);
                            mBatchNews.setImageResource(R.drawable.news);

                        }
                    }
                    // }
                }
            } catch (Exception ex) {

            }


            mTextViewSpecialPlaceName.setText("" + InitialValueSetUp.mPlaceName);
            mSpecialRatingBar.setRating(InitialValueSetUp.mRating);

            if (InitialValueSetUp.statusOpening.equals("OPEN")) {
                mTextViewSpecialStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                mTextViewSpecialStatus.setText("OPEN");
                mViewSpecialStatusView.setBackground(mContext.getResources().getDrawable(R.drawable.green_circle));
            } else {
                mTextViewSpecialStatus.setText("CLOSED");
            }

            mTextViewSpecialDistance.setText("" + InitialValueSetUp.mDistancePlace);
            mTextViewSpecialAddress.setText("" + InitialValueSetUp.mPlaceAddress);
        }

        // called apply listeners method
        applyListenersToViews();

        // get Event Info
        getEventAdInfo();
        // apply listener to batch
        applyListenerToBatchs();

    }

    private void applyListenerToBatchs() {

        mBatchCatalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CatalogActivity.class);
            }
        });

        mBatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(EventsActivity.class);
            }
        });

        mBatchPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(PromotionActivity.class);
            }
        });

        mBatchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(NewsActivity.class);
            }
        });
        mBatchSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(SpecialActivity.class);
            }
        });


    }

    private void applyListenersToViews() {

        mImageViewSpecialCancel.setOnClickListener(this);
        mImageViewFooterBack.setOnClickListener(this);
        mImageViewFooterForword.setOnClickListener(this);
        mTextViewSpecialEmailUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.sendEmail(mTextViewSpecialEmailUrl.getText().toString().trim());
            }
        });

        mTextViewSpecialLandline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.callDialog(mTextViewSpecialLandline.getText().toString().trim(), mTextViewSpecialLandline.getText().toString().trim());

            }
        });
        mTextViewSpecialMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.callDialog(mTextViewSpecialMobile.getText().toString().trim(), mTextViewSpecialMobile.getText().toString().trim());

            }
        });
        mTextViewSpecialWebsiteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.openWebLink(mTextViewSpecialWebsiteUrl.getText().toString().trim());
            }
        });




    }

    public void clickableBatchs() {
        mBatchCatalogue.setEnabled(false);
        mBatchEvent.setEnabled(false);
        mBatchNews.setEnabled(false);
        mBatchPromotion.setEnabled(false);
        mBatchSpecial.setEnabled(false);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_header_cancel:
                this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;

            case R.id.img_footer_back:
                // TODO :Some stuff if want different.... not in sequence
                startActivity(new Intent(mContext, SpecialActivity.class));
                this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.img_footer_forword:
                uihandle.explicitIntent(PromotionActivity.class);
                this.finish();
                break;
            default:
                break;
        }
    }

    private void getEventAdInfo() {
        new FireApiGetAdsInfoWithBatchId(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("login", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {
                                    JSONArray jArray = response.optJSONArray("data");
                                    if (jArray != null) {
                                        JSONObject jObj = jArray.optJSONObject(0);

                                        String name = UiHandleMethods.capitalizeString(jObj.optString("ad_name"));
                                        mTextViewSpecialName.setText("Event Name: " + name);


                                        mTextViewSpecialDecription.setText(Html.fromHtml("<b><font color=\"#000000\">" + "Description: " + "</font></b>" +"<font color=\"#6b6969\">"+ jObj.optString("description")+"</font>"));

                                     // mTextViewSpecialDecription.setText(jObj.optString("description"));

                                        mTextViewStartDate.setText("" + jObj.optString("start_date"));

                                        mTextViewSpecialEndDate.setText("" + jObj.optString("end_date"));
                                        mTextViewSpecialEmailUrl.setText("" + jObj.optString("email"));
                                        mTextViewSpecialWebsiteUrl.setText("" + jObj.optString("website"));

                                        mTextViewSpecialLandline.setText("" + jObj.optString("landline"));
                                        mTextViewSpecialMobile.setText("" + jObj.optString("mobile"));
                                        mTextViewSpecialEventLocation.setText("" + jObj.optString("EventLocation"));
                                        mTextViewEventTime.setText("" + jObj.optString("EventTime"));

                                        imge_path = jObj.optString("ad_image");

                                        String image2 = jObj.optString("ad_image1");
                                        String image3 = jObj.optString("ad_image2");

                                        String imagesS[] = {imge_path, image2, image3};

                                        mAdapterEvent = new AdapterEventUser(mContext, imagesS);
                                        mViewPager.setAdapter(mAdapterEvent);
                                        mCircleIndicator.setViewPager(mViewPager);


                                    }


                                } else {
                                    uihandle.showToast("Something went wrong");
                                }


                            } else {
                                uihandle.showToast(response.optString("message"));
                            }


                        }
                    }

                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }


            }
        }.execute(URLListApis.URL_GET_INFO_WITH_BATCH_ID);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
