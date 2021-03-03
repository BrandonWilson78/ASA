package za.asa_media.awesome_sa.modules_.special;

import android.app.Activity;
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
import za.asa_media.awesome_sa.modules_.event.EventsActivity;
import za.asa_media.awesome_sa.modules_.news.NewsActivity;
import za.asa_media.awesome_sa.modules_.promotion.PromotionActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class SpecialActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity mContext = this;
    private TextView
            mTextViewSpecialPlaceName, mTextViewSpecialAddress,
            mTextViewSpecialName, mTextViewSpecialDecription,
            mTextViewStartDate, mTextViewSpecialEndDate,
            mTextViewSpecialVoucher, mTextViewSpecialEmailUrl,
            mTextViewSpecialWebsiteUrl, mTextViewSpecialLandline,
            mTextViewSpecialMobile, mTextViewSpecialDistance,
            mTextViewSpecialStatus;


    private View mViewSpecialStatusView;
    private RatingBar mSpecialRatingBar;

    private ImageView mBatchSpecial, mBatchEvent, mBatchPromotion, mBatchNews, mBatchCatalogue;
    private ImageView mImageViewSpecialCancel, mImageViewFooterBack, mImageViewFooterForword;

    // Other helping objects
    private UiHandleMethods uihandle;
    private SelectedPlaceListdata mPlaceDateObject;
    private String imge_path;
    // pager decalaration
    private ViewPager mViewPager;
    private AdapterSocialsUser mAdapterSocials;
    private CircleIndicator mCircleIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        // calling init views for initialsetup
        initViews();
    }

    private void initViews() {

        // views initialisations

        uihandle = new UiHandleMethods(mContext);
        mTextViewSpecialPlaceName = (TextView) findViewById(R.id.txt_nearby_place_name);
        mTextViewSpecialAddress = (TextView) findViewById(R.id.txt_nearby_address);
        mTextViewSpecialDistance = (TextView) findViewById(R.id.txt_nearby_distance);
        mTextViewSpecialStatus = (TextView) findViewById(R.id.txt_nearby_status);
        mViewSpecialStatusView = findViewById(R.id.status_view);

        mTextViewSpecialName = (TextView) findViewById(R.id.txt_special_name);
        mTextViewSpecialDecription = (TextView) findViewById(R.id.txt_description);
        mTextViewStartDate = (TextView) findViewById(R.id.text_start_date);
        mTextViewSpecialEndDate = (TextView) findViewById(R.id.text_end_date);
        mTextViewSpecialVoucher = (TextView) findViewById(R.id.text_voucher);
        mTextViewSpecialEmailUrl = (TextView) findViewById(R.id.text_contact_email);
        mTextViewSpecialWebsiteUrl = (TextView) findViewById(R.id.text_website_link);
        mTextViewSpecialLandline = (TextView) findViewById(R.id.text_landline_number);
        mTextViewSpecialMobile = (TextView) findViewById(R.id.text_mobile_number);

        // header batchs
        mBatchSpecial = (ImageView) findViewById(R.id.batch_special);
        mBatchEvent = (ImageView) findViewById(R.id.batch_event);
        mBatchPromotion = (ImageView) findViewById(R.id.batch_promotion);
        mBatchNews = (ImageView) findViewById(R.id.batch_news);
        mBatchCatalogue = (ImageView) findViewById(R.id.batch_catalogue);


        mSpecialRatingBar = (RatingBar) findViewById(R.id.ratingBar_nearby);
        mImageViewSpecialCancel = (ImageView) findViewById(R.id.img_header_cancel);

        mImageViewFooterBack = (ImageView) findViewById(R.id.img_footer_back);
        mImageViewFooterForword = (ImageView) findViewById(R.id.img_footer_forword);
        // pager setting

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setOffscreenPageLimit(2);


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

        } else /*if (getIntent().hasExtra("address"))*/ {

            try {
                // uncheck all batches before highlights batchs
                InitialValueSetUp.adBatchId = "4";
                clickableBatchs();
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
        // get special info
        getSpecialAdInfo();
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

        mImageViewFooterBack.setVisibility(View.INVISIBLE);
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

    private void getSpecialAdInfo() {
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
                                        mTextViewSpecialName.setText("Special Name: " + name);


                                        mTextViewSpecialDecription.setText(Html.fromHtml("<b><font color=\"#000000\">" + "Description: " + "</font></b>" +"<font color=\"#6b6969\">"+ jObj.optString("description")+"</font>"));
                                        // mTextViewSpecialDecription.setText(jObj.optString("description"));

                                        mTextViewStartDate.setText("" + jObj.optString("start_date"));

                                        mTextViewSpecialEndDate.setText("" + jObj.optString("end_date"));
                                        mTextViewSpecialVoucher.setText("" + jObj.optString("coupon"));
                                        mTextViewSpecialEmailUrl.setText("" + jObj.optString("email"));
                                        mTextViewSpecialWebsiteUrl.setText("" + jObj.optString("website"));

                                        mTextViewSpecialLandline.setText("" + jObj.optString("landline"));
                                        mTextViewSpecialMobile.setText("" + jObj.optString("mobile"));

                                        imge_path = jObj.optString("ad_image");
                                        String image2 = jObj.optString("ad_image1");
                                        String image3 = jObj.optString("ad_image2");

                                        String imagesS[] = {imge_path, image2, image3};

                                        mAdapterSocials = new AdapterSocialsUser(mContext, imagesS);
                                        mViewPager.setAdapter(mAdapterSocials);
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
        }
                .execute(URLListApis.URL_GET_INFO_WITH_BATCH_ID);


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

            case R.id.image_batch_layout:
                uihandle.showToast("Under scruitny");
                break;

            case R.id.img_footer_back:
                // TODO :Some stuff if want different.... not in sequence
                break;
            case R.id.img_footer_forword:
                uihandle.explicitIntent(EventsActivity.class);
                this.finish();
                break;


            default:
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}
