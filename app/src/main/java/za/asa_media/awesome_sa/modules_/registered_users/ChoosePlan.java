package za.asa_media.awesome_sa.modules_.registered_users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdCatalogue;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdEvent;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdNews;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdPromotions;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdSpecials;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.ChoosePlanAdapter;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiAddPlan;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiChoosePlan;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetBuyAds;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiUpdatePaymentStatus;
import za.asa_media.awesome_sa.modules_.registered_users.model.ChoosePlanModel;
import za.asa_media.awesome_sa.paypal_.PayPalPresenter;
import za.asa_media.awesome_sa.support.CircleCheckBox;
import za.asa_media.awesome_sa.support.ScaleInItemAnimator;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class ChoosePlan extends AppCompatActivity implements IChoosePlane {

    //  String mTextHeading = "Get" + "<b>" + " UNLIMITED" + "</b> " + "ads to place daily\n by taking out one of our subscriptions.";
    private String mTextHeading = "Businesses now have the chance to use this game changing app for $5 once off adverts with the option of using this ad portal all options for a mere $99 a year. ( No commission or booking fees paid to CitiOculus)";

    private TextView mTextViewDescription, mTextViewPublish;
    private Button mBtnCreditDebit, mBtnPaypal;
    private Activity mContext = this;
    private UiHandleMethods uihandle;

    private TextView mTextViewHeading;
    private Toolbar mToolbar;
    private ImageView mImageBackArrow;

    // once of ad decalarations

    /*
    //findImageViewClicked
    private ImageView mImageViewcatalog;
    private ImageView mImageViewspecial;
    private ImageView mImageViewevents;
    private ImageView mImageViewpromotion;
    private ImageView mImageViewnews;*/

    // for payment method
    private int paymentAmount = 0;

    private PayPalPresenter mPresenterPaypal;
    private String paymentDetails;
    private String mState, paymentId, paymentCreatedTime;

    // plans list
    private RecyclerView mRecyclePlans;
    private ChoosePlanAdapter mAdapterPlan;
    private List<ChoosePlanModel> mListPlans;
    private boolean mFlagOneSelection;
    private SessionCityOculus mSession;
    private ImageView mImageeCatalog;
    private ImageView mImageSpecials;
    private ImageView mImageEvent;
    private ImageView mImagePromotions;
    private ImageView mImageNews;
    private String batchId = "0";
    private String planId;
    private String planprice = "";

    // payment option alg
    private boolean mFlagCards, mFlagPaypal;
    /* planid:2
       price:25
       batchid:3  */

    private WebView myWebView;
    private int purchase_status = -1;
    // agreement check box needs to be selected for successfully registration
    private CircleCheckBox checkBoxTerms;
    // flag of agreement checked or not
    private boolean mCheckAgreement = false;


    /*

    {
     "client": {
      "environment": "sandbox",
     "paypal_sdk_version": "2.15.3",
     "platform": "Android",
        "product_name": "PayPal-Android-SDK"
      },
      "response": {
      "create_time": "2017-05-23T11:47:57Z",
      "id": "PAY-51X6730865876902PLESCCZA",
      "intent": "sale",
       "state": "approved"
       },
      "response_type": "payment"
      }

    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.new_app_blue_color));
        setContentView(R.layout.activity_choose_plan);
        // calling initviews for initialisations
        initViews();
    }

    private void initViews() {
        // paypal process initiate

        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        mPresenterPaypal = new PayPalPresenter(mContext);
        mListPlans = new ArrayList<>();
        mRecyclePlans = (RecyclerView) findViewById(R.id.lst_subscription_plans);
        mRecyclePlans.setLayoutManager(new LinearLayoutManager(this));
        // mRecycleNotifications.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclePlans.setItemAnimator(new ScaleInItemAnimator());


        checkBoxTerms = (CircleCheckBox) findViewById(R.id.checkbox_agreement);

        mToolbar = (Toolbar) findViewById(R.id.myToolbar);
        mTextViewHeading = mToolbar.findViewById(R.id.textView_header_center);
        mImageBackArrow = mToolbar.findViewById(R.id.img_back_header);
        mTextViewHeading.setText("Purchase Plan");


        mTextViewDescription = (TextView) findViewById(R.id.txt_basic_info);

        mTextViewPublish = (TextView) findViewById(R.id.txt_publish);

        mBtnCreditDebit = (Button) findViewById(R.id.btn_choose_credit_debit);
        mBtnPaypal = (Button) findViewById(R.id.btn_choose_paypal);

        mBtnCreditDebit.setVisibility(View.INVISIBLE);
        mBtnPaypal.setVisibility(View.INVISIBLE);

        checkBoxTerms.setOnCheckedChangeListener(new CircleCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CircleCheckBox view, boolean isChecked) {
                //  System.out.println("CHECK " + isChecked);

                mCheckAgreement = isChecked;

            }
        });


        goForPlans();


        /*

        // once of ad batches initialisation
        mImageViewcatalog = (ImageView) findViewById(R.id.batch_img_catalog_once);
        mImageViewspecial = (ImageView) findViewById(R.id.batch_img_special_once);
        mImageViewevents = (ImageView) findViewById(R.id.batch_img_events_once);
        mImageViewpromotion = (ImageView) findViewById(R.id.batch_img_promotion_once);
        mImageViewnews = (ImageView) findViewById(R.id.batch_img_news_once);

      */
        /*

        // apply listener on them
        mImageViewcatalog.setOnClickListener(this);
        mImageViewspecial.setOnClickListener(this);
        mImageViewevents.setOnClickListener(this);
        mImageViewpromotion.setOnClickListener(this);
        mImageViewnews.setOnClickListener(this);
*/

        //checked change listner


        // apply values and implement listeners
        mTextViewDescription.setText(Html.fromHtml(mTextHeading));
        mImageBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePlan.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        mBtnCreditDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uihandle.explicitIntent(CreditCard.class);
                // uihandle.showToast("Under scruitny!");

           /*     myWebView = new WebView(mContext);
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                myWebView.setWebViewClient(new MyWebViewClient());
                myWebView.loadUrl("http://112.196.5.114/asa/api/payment");*/

                //Todo : comment after doing changes

                //   mBtnCreditDebit.setBackgroundColor(R.drawable.layout_button_round_dark);
                //   mBtnPaypal.setBackgroundColor(R.drawable.layout_button_round);

                //    mBtnCreditDebit.setBackground(getResources().getDrawable(R.drawable.layout_button_round_dark));
                //    mBtnPaypal.setBackground(getResources().getDrawable(R.drawable.layout_button_round));

                uihandle.showToast("coming soon!");
                // Todo: comment after doing changes
                //   mFlagCards = true;
                //  mFlagPaypal = false;
            }
        });

        mBtnPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (planprice != "") {
                    if (mFlagOneSelection) {
                        uihandle.showToast("select atleast one batch");
                    } else {
                       /* if (mFlagCards) {

                     uihandle.explicitIntent(PaymentByPaygate.class);
                         //   goForAddPlan();
                          //  clearBatchs();

                        } else*/
                        if (mFlagPaypal) {

                            goForAddPlan();
                            clearBatchs();

                        } else {
                            uihandle.showToast("Please select payment method before proceed!");
                        }
                    }
                } else {
                    uihandle.showToast("Please select plan before proceed!");
                }

              /*
                mFlagPaypal = true;
                mFlagCards = false;

                mBtnPaypal.setBackground(getResources().getDrawable(R.drawable.layout_button_round_dark));
                mBtnCreditDebit.setBackground(getResources().getDrawable(R.drawable.layout_button_round));*/


            }
        });

        mTextViewPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //          Todo: Remove this flag when you put two options of payment or paypal button intialize
                mFlagPaypal = true;

                if (planprice != "") {
                    if (mFlagOneSelection) {
                        uihandle.showToast("select atleast one batch");
                    } else {
                       /* if (mFlagCards) {

                 uihandle.explicitIntent(PaymentByPaygate.class);
                           //   goForAddPlan();
                           //    clearBatchs();

                        } else*/
                        // if (mFlagPaypal) {

                        // check for the agreement to be checked or not
                        if (mCheckAgreement) {
                            goForAddPlan();
                            clearBatchs();
                        } else {
                            uihandle.showToast("Please accept the terms & conditions!");
                        }



                       /* } else {
                            uihandle.showToast("Please select payment method before proceed!");
                        }*/

                    }
                } else {
                    uihandle.showToast("Please select plan before proceed!");
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If the result is from paypal
        //If the result is from paypal
        if (requestCode == PayPalPresenter.PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
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
                        /*mContext.startActivity(new Intent(mContext, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));
*/


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

            paymentCreatedTime = oResponse.optString("create_time");  // date payment initiated
            paymentId = oResponse.optString("id").toString();         // txn id
            mState = oResponse.optString("state").toString();         // status
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
              /* {
                    "status": true,
                   "message": "Payment Received Successfull"
                                                         } */
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                //  uihandle.showToast(response.optString(("message")));

                                fetchAdsToCreate();
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
        }.execute(URLListApis.URL_PURCHASE_UPDATESTATUS);

    }

    private void fetchAdsToCreate() {
        new FireApiGetBuyAds(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (response != null) {
                        Log.d("create_ads", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        JSONObject jResponse = jObj.optJSONObject(jObj.length() - 1);

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

                                        mSession.setCAdPurchaseId(purchaseid);
                                        mSession.setCAdPlaceId(placeId);
                                        mSession.setCAdPlanId(planId);
                                        mSession.setCAdPlaceName(placeName);
                                        mSession.setCAdExpiryDate(ExpiryDate);

                                        InitialValueSetUp.mBatchs = batchId;

                                        if (purchase_status == 0) {
                                            createSuccessDialog("Your 30 trail has been Approved. Now you can create Ads.\n\nGoto-->MyAds-->Create Ads.");
                                        } else {
                                            createSuccessDialog("Your payment has been received. Now you can create Ads for plan purchased.\n\nGoto-->MyAds-->Create Ads.");
                                        }
                                    }
                                }
                            }
                        } else {
                            uihandle.showToast(response.optString(("message")));
                        }
                    } else {
                        uihandle.showToast("Something went wrong");
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_ADS_GET_DETAIL);
    }

    private void goForPlans() {
        new FireApiChoosePlan(mContext) {

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (response != null) {
                        Log.d("info_plan", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.has("data")) {
                                JSONArray jObj = response.optJSONArray("data");
                                if (jObj != null) {
                                    for (int i = 0; i < jObj.length(); i++) {
                                        JSONObject jResponse = jObj.optJSONObject(i);
                                        String planId = jResponse.optString("id").toString().trim();
                                        String planName = jResponse.optString("plan_name").toString().trim();
                                        String plan_subtitle = jResponse.optString("plan_subtitle").toString().trim();
                                        String plan_price = jResponse.optString("plan_price").toString().trim();

                                        String plan_batch = jResponse.optString("plan_batch").toString().trim();

                                        mListPlans.add(new ChoosePlanModel(planId, planName, plan_subtitle, plan_price, plan_batch));


                                    }
                                    // initialise adapter and put values to recycel view
                                    mAdapterPlan = new ChoosePlanAdapter(mContext, mListPlans);
                                    mRecyclePlans.setAdapter(mAdapterPlan);

                                }
                            }
                        } else {
                            uihandle.showToast(response.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }
            }
        }.execute(URLListApis.URL_GET_PLANS);
    }

    private void goForAddPlan() {
        new FireApiAddPlan(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                uihandle.showToast(response.optString(("message")));
                                int purchaseId = response.optInt("purchaseid");
                                purchase_status = purchaseId;
                                mSession.setPurchaseId(String.valueOf(purchaseId));

                                // go for particular payment option selected by the user
                                if (purchaseId == 0) {
                                    fetchAdsToCreate();

                                } else {
                                    if (mFlagCards) {
                                        uihandle.explicitIntent(PaymentByPaygate.class);

                                    } else if (mFlagPaypal) {
                                        //planprice //"0.02"
                                        mPresenterPaypal.getPayment(planprice);
                                    } else {
                                        uihandle.showToast("Something went wrong!");
                                    }
                                }
                            } else {
                                createWarningDialog(response.optString(("message")));
                            }

                        } else {
                            uihandle.showToast("Something went wrong!");
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_PURCHASE_ADDPAYMENT, planId, planprice, batchId);

    }

    public void createWarningDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!")
                .setContentText(msg)
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })

                .show();
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
            /*
             Batch_id: 1- catalog
             Batch_id: 2- event
             Batch_id: 3-promotion
             Batch_id: 4-special
             Batch_id: 5 -news
            */

                        sDialog.dismiss();
                        mSession.setAdsDirectTag(true);
                        String[] batc = InitialValueSetUp.mBatchs;

                        if (batc.length > 0) {
                            for (String batch : batc) {
                                switch (batch) {
                                    case "1":
                                        uihandle.explicitIntent(CreateAdCatalogue.class);
                                        break;
                                    case "2":
                                        uihandle.explicitIntent(CreateAdEvent.class);
                                        break;
                                    case "3":
                                        uihandle.explicitIntent(CreateAdPromotions.class);
                                        break;
                                    case "4":
                                        uihandle.explicitIntent(CreateAdSpecials.class);
                                        break;
                                    case "5":
                                        uihandle.explicitIntent(CreateAdNews.class);
                                        break;
                                    default:
                                        uihandle.explicitIntent(CreateAdCatalogue.class);
                                        break;
                                }
                            }
                        }


                    }
                })
                .show();
    }

    //     paypal destroy service
    @Override
    protected void onDestroy() {
        mPresenterPaypal.destroy();
        super.onDestroy();

    }

    @Override
    public void getDetailOfPlan(ChoosePlanModel data) {

        View v = mRecyclePlans.findViewHolderForAdapterPosition(0).itemView;

        mImageeCatalog = v.findViewById(R.id.batch_img_catalog);
        mImageSpecials = v.findViewById(R.id.batch_img_special_1);
        mImageEvent = v.findViewById(R.id.batch_img_events_1);
        mImagePromotions = v.findViewById(R.id.batch_img_promotion_1);
        mImageNews = v.findViewById(R.id.batch_img_news_1);

        if (data.getPlanBatch().equals("one")) {
            mFlagOneSelection = true;

            mImageeCatalog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlagOneSelection = false;
                    batchId = "[1]";
                    InitialValueSetUp.mBatchs = new String[]{"1"};
                    clearBatchs();
                    mImageeCatalog.setImageResource(R.drawable.catalog);
                }
            });
            mImageSpecials.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mFlagOneSelection = false;
                    batchId = "[4]";
                    InitialValueSetUp.mBatchs = new String[]{"4"};
                    clearBatchs();
                    mImageSpecials.setImageResource(R.drawable.specials);
                }
            });
            mImageEvent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mFlagOneSelection = false;
                    batchId = "[2]";
                    InitialValueSetUp.mBatchs = new String[]{"2"};
                    clearBatchs();
                    mImageEvent.setImageResource(R.drawable.events);
                }
            });
            mImagePromotions.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mFlagOneSelection = false;
                    batchId = "[3]";
                    InitialValueSetUp.mBatchs = new String[]{"3"};
                    clearBatchs();
                    mImagePromotions.setImageResource(R.drawable.promotions);
                }
            });
            mImageNews.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mFlagOneSelection = false;
                    batchId = "[5]";
                    InitialValueSetUp.mBatchs = new String[]{"5"};
                    clearBatchs();
                    mImageNews.setImageResource(R.drawable.news);
                }
            });

        } else {
            mFlagOneSelection = false;
            batchId = "[1,2,3,4,5]";
            InitialValueSetUp.mBatchs = new String[]{"1", "2", "3", "4", "5"};
        }

        planprice = data.getPlanPrice();
        planId = data.getPlanId();

        //   paymentAmount = Integer.parseInt(data.getPlanPrice());

        //  uihandle.showToast("planId:" + planId + "\n" + "batchId: " + batchId + "\n" + "amount: " + planprice);
    }

    void clearBatchs() {
        mImageeCatalog.setImageResource(R.drawable.grey_catalog);
        mImageSpecials.setImageResource(R.drawable.grey_specials);
        mImageEvent.setImageResource(R.drawable.grey_events);
        mImagePromotions.setImageResource(R.drawable.grey_promotions);
        mImageNews.setImageResource(R.drawable.grey_news);
    }


}
