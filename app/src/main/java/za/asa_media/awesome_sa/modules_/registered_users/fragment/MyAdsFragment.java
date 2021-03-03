package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.AdapterAdUnderReview;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.AdapterCreateAd;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.AdapterExpiredAds;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.AdapterRejectedAds;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.MyAdsRunningRecycleAdapter;
import za.asa_media.awesome_sa.modules_.registered_users.api.FetchAdsDetailWithStatus;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetBuyAds;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetExpiredAds;
import za.asa_media.awesome_sa.modules_.registered_users.model.CreateAdModel;
import za.asa_media.awesome_sa.modules_.registered_users.model.ExpiredAdModel;
import za.asa_media.awesome_sa.modules_.registered_users.model.RejectedAdModel;
import za.asa_media.awesome_sa.modules_.registered_users.model.RunningAdsModel;
import za.asa_media.awesome_sa.modules_.registered_users.model.UnderReviewAdModel;
import za.asa_media.awesome_sa.support.ScaleInItemAnimator;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class MyAdsFragment extends Fragment {

    private Activity mContext;
    private UiHandleMethods uihandle;

    //  Plus and Minus imageView decalaratoions
    private ImageView mImageExpandPlusCreate, mImageExpandMinusCreate;
    private ImageView mImageExpandPlusRunning, mImageExpandMinusRunning;
    private ImageView mImageExpandPlusReview, mImageExpandMinusReview;
    private ImageView mImageExpandPlusRejected, mImageExpandMinusRejected;
    private ImageView mImageExpandPlusExpired, mImageExpandMinusExpired;

    private View mView = null;

    // setup recycle view
    private RecyclerView mRecycleCreateAds;
    private RecyclerView mRecycleRunningAds;
    private RecyclerView mRecycleRejectAds;
    private RecyclerView mRecycleUnderReviewAds;
    private RecyclerView mRecycleExpiredAds;

    // adapter corresponding to recyclerview
    private AdapterCreateAd mAdapterCreates;
    //  private MyAdsRunningRecycleAdapter mAdapterRunnings;
    private AdapterAdUnderReview mAdapterUnderReviewAds;
    private AdapterRejectedAds mAdapterRejectedAds;
    private AdapterExpiredAds mAdapterExpiredAds;

    // List data for recycler view
    private List<CreateAdModel> mListCreateAds;
    private List<RunningAdsModel> mListRunningAds;
    private List<UnderReviewAdModel> mListUnderReviewAds;
    private List<RejectedAdModel> mListRejectedAds;
    private List<ExpiredAdModel> mListExpiredAds;

    private LinearLayout mLinearCreateNewAds, mLinearRunningAds,
            mLinearAdsUnderReview, mLinearRejectedAds, mLinearExpiredAds;

    // call back decalaration
    public MyAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_my_ads, container, false);
        }
        // calling initviews for initial initialisations and setup recycler view
        initviews(mView);
        recycleViewSetup(mView);
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.mContext = context;
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    private void initviews(View view) {

        // ArrayList for ads ads
        mListCreateAds = new ArrayList<>();
        mListRunningAds = new ArrayList<>();
        mListUnderReviewAds = new ArrayList<>();
        mListRejectedAds = new ArrayList<>();
        mListExpiredAds = new ArrayList<>();

        uihandle = new UiHandleMethods(mContext);

        /****** Create ads view  ******/
        mImageExpandPlusCreate = view.findViewById(R.id.img_plus_create_ads);
        mImageExpandMinusCreate = view.findViewById(R.id.img_minus_create_ads);

        /****** Running ads view  ******/
        mImageExpandPlusRunning = view.findViewById(R.id.img_plus_running_ads);
        mImageExpandMinusRunning = view.findViewById(R.id.img_minus_running_ads);

        /****** Under Review view ******/
        mImageExpandPlusReview = view.findViewById(R.id.img_plus_ads_under_review);
        mImageExpandMinusReview = view.findViewById(R.id.img_minus_ads_under_review);

        /****** Rejected ads view  ******/
        mImageExpandPlusRejected = view.findViewById(R.id.img_plus_rejected_ads);
        mImageExpandMinusRejected = view.findViewById(R.id.img_minus_rejected_ads);

        /****** Expired ads view  ******/
        mImageExpandPlusExpired = view.findViewById(R.id.img_plus_expired_ads);
        mImageExpandMinusExpired = view.findViewById(R.id.img_minus_expired_ads);

        // ads recycle initialisation
        mRecycleCreateAds = view.findViewById(R.id.recycle_create_ads);
        mRecycleRunningAds = view.findViewById(R.id.txt_running_ads_description);
        mRecycleUnderReviewAds = view.findViewById(R.id.recycle_under_review_ads);
        mRecycleRejectAds = view.findViewById(R.id.recycle_rejected_ads);
        mRecycleExpiredAds = view.findViewById(R.id.recycle_expired_ads);

        /***  Listener method for to support click to parent layout***/
        mLinearCreateNewAds = view.findViewById(R.id.linear_create_ads);
        mLinearRunningAds = view.findViewById(R.id.linear_running_ads);
        mLinearAdsUnderReview = view.findViewById(R.id.linear_ads_under_review);
        mLinearRejectedAds = view.findViewById(R.id.linear_rejected_ads);
        mLinearExpiredAds = view.findViewById(R.id.linear_expired_ads);


        //        if (mListCreateAds.size() == 0) {
        //        choosePlanDialog("You have to buy subscription plan before proceed!\n Go to Post new Ad");
        //        }

        // apply listener to imageviews
        mImageExpandMinusCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleCreateAds.setVisibility(View.GONE);
                mImageExpandPlusCreate.setVisibility(View.VISIBLE);
                mImageExpandMinusCreate.setVisibility(View.GONE);
                if (mListCreateAds.size() == 0) {
                    choosePlanDialog("You have to buy subscription plan before proceed!\n Go to Post new Ad");
                }
            }
        });

        mImageExpandPlusCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleCreateAds.setVisibility(View.VISIBLE);
                mImageExpandPlusCreate.setVisibility(View.GONE);
                mImageExpandMinusCreate.setVisibility(View.VISIBLE);
                if (mListCreateAds.size() == 0) {
                    choosePlanDialog("You have to buy subscription plan before proceed!\n Go to Post new Ad");
                }
            }
        });

        /////////
        mImageExpandMinusRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleRunningAds.setVisibility(View.GONE);
                mImageExpandPlusRunning.setVisibility(View.VISIBLE);
                mImageExpandMinusRunning.setVisibility(View.GONE);
            }
        });

        mImageExpandPlusRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleRunningAds.setVisibility(View.VISIBLE);
                mImageExpandPlusRunning.setVisibility(View.GONE);
                mImageExpandMinusRunning.setVisibility(View.VISIBLE);
            }
        });
        ////////////
        mImageExpandMinusReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleUnderReviewAds.setVisibility(View.GONE);
                mImageExpandPlusReview.setVisibility(View.VISIBLE);
                mImageExpandMinusReview.setVisibility(View.GONE);
            }
        });

        mImageExpandPlusReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleUnderReviewAds.setVisibility(View.VISIBLE);
                mImageExpandPlusReview.setVisibility(View.GONE);
                mImageExpandMinusReview.setVisibility(View.VISIBLE);
            }
        });

        mImageExpandMinusRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleRejectAds.setVisibility(View.GONE);
                mImageExpandPlusRejected.setVisibility(View.VISIBLE);
                mImageExpandMinusRejected.setVisibility(View.GONE);
            }
        });

        mImageExpandPlusRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleRejectAds.setVisibility(View.VISIBLE);
                mImageExpandPlusRejected.setVisibility(View.GONE);
                mImageExpandMinusRejected.setVisibility(View.VISIBLE);
            }
        });

        ////////////////
        mImageExpandMinusExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleExpiredAds.setVisibility(View.GONE);
                mImageExpandPlusExpired.setVisibility(View.VISIBLE);
                mImageExpandMinusExpired.setVisibility(View.GONE);
            }
        });

        mImageExpandPlusExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleExpiredAds.setVisibility(View.VISIBLE);
                mImageExpandPlusExpired.setVisibility(View.GONE);
                mImageExpandMinusExpired.setVisibility(View.VISIBLE);
            }
        });


        /**** Listener of Parent Layout   ****/

        mLinearCreateNewAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageExpandPlusCreate.getVisibility() == View.VISIBLE) {
                    buttonSwitchingHandler(View.VISIBLE, mRecycleCreateAds, mImageExpandPlusCreate, mImageExpandMinusCreate);
                } else if (mImageExpandPlusCreate.getVisibility() == View.GONE) {
                    buttonSwitchingHandler(View.GONE, mRecycleCreateAds, mImageExpandPlusCreate, mImageExpandMinusCreate);
                }

                if (mListCreateAds.size() == 0) {
                    choosePlanDialog("You have to buy subscription plan before proceed!\n Go to Post new Ad");
                }
            }
        });


        mLinearRunningAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageExpandPlusRunning.getVisibility() == View.VISIBLE) {
                    buttonSwitchingHandler(View.VISIBLE, mRecycleRunningAds, mImageExpandPlusRunning, mImageExpandMinusRunning);
                } else if (mImageExpandPlusRunning.getVisibility() == View.GONE) {
                    buttonSwitchingHandler(View.GONE, mRecycleRunningAds, mImageExpandPlusRunning, mImageExpandMinusRunning);
                }
            }
        });


        mLinearAdsUnderReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageExpandPlusReview.getVisibility() == View.VISIBLE) {
                    buttonSwitchingHandler(View.VISIBLE, mRecycleUnderReviewAds, mImageExpandPlusReview, mImageExpandMinusReview);
                } else if (mImageExpandPlusReview.getVisibility() == View.GONE) {
                    buttonSwitchingHandler(View.GONE, mRecycleUnderReviewAds, mImageExpandPlusReview, mImageExpandMinusReview);
                }
            }
        });


        mLinearRejectedAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageExpandPlusRejected.getVisibility() == View.VISIBLE) {
                    buttonSwitchingHandler(View.VISIBLE, mRecycleRejectAds, mImageExpandPlusRejected, mImageExpandMinusRejected);
                } else if (mImageExpandPlusRejected.getVisibility() == View.GONE) {
                    buttonSwitchingHandler(View.GONE, mRecycleRejectAds, mImageExpandPlusRejected, mImageExpandMinusRejected);
                }
            }
        });

        /////------>>>>>>>>>>>>>>>>>>>
        mLinearExpiredAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageExpandPlusExpired.getVisibility() == View.VISIBLE) {
                    buttonSwitchingHandler(View.VISIBLE, mRecycleExpiredAds, mImageExpandPlusExpired, mImageExpandMinusExpired);
                } else if (mImageExpandPlusExpired.getVisibility() == View.GONE) {
                    buttonSwitchingHandler(View.GONE, mRecycleExpiredAds, mImageExpandPlusExpired, mImageExpandMinusExpired);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAdsToCreate();

        fetchAdsWithRunningStatus("active", mRecycleRunningAds);
        fetchAdsWithUnderReviewStatus("pending", mRecycleUnderReviewAds);
        fetchAdsWithRejectedStatus("rejected", mRecycleRejectAds);
        fetchAdsWithExpiredStatus("", mRecycleExpiredAds);

    }

    private void recycleViewSetup(View view) {

        /****** Create ads Recycles view   ******/
        mRecycleCreateAds.setLayoutManager(new LinearLayoutManager(mContext));

        mRecycleCreateAds.setItemAnimator(new DefaultItemAnimator());

        /****** Running ads Recycles view   ******/
        mRecycleRunningAds.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleRunningAds.setItemAnimator(new ScaleInItemAnimator());
        /****** Under review ads ads Recycles view   ******/
        mRecycleUnderReviewAds.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleUnderReviewAds.setItemAnimator(new ScaleInItemAnimator());
        /****** Rejected ads Recycles view   ******/
        mRecycleRejectAds.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleRejectAds.setItemAnimator(new ScaleInItemAnimator());
        /****** Expired ads Recycle view   ******/
        mRecycleExpiredAds.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleExpiredAds.setItemAnimator(new ScaleInItemAnimator());

    }

    private void fetchAdsToCreate() {
        new FireApiGetBuyAds(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (mListCreateAds.size() > 0) {
                        mListCreateAds.clear();
                    }
                    if (response != null) {
                        Log.d("create_ads", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

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


                                            mListCreateAds.add(new CreateAdModel(purchaseid, placeId, placeName, planId,
                                                    planName, price, batchId, purchaseDate, ExpiryDate,
                                                    transactionId, paymentStatus, planStatus));


                                        }
                                        // initialise adapter and put values to recycel view
                                        mAdapterCreates = new AdapterCreateAd(mContext, mListCreateAds);
                                        mRecycleCreateAds.setAdapter(mAdapterCreates);


                                    }
                                }

                            } else {

                                uihandle.showToast(response.optString(("message")));
                            }

                        } else {
                            uihandle.showToast("Something went wrong");
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }
            }
        }.execute(URLListApis.URL_ADS_GET_DETAIL);
    }

    private void fetchAdsWithRunningStatus(String status, final RecyclerView mRecycle) {
        new FetchAdsDetailWithStatus(mContext) {

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (mListRunningAds.size() > 0) {
                        mListRunningAds.clear();
                    }
                    if (response != null) {
                        Log.d("history", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

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

                                            mListRunningAds.add(new RunningAdsModel(purchaseid, placeId, placeName, planId,
                                                    planName, price, batchId, purchaseDate, ExpiryDate,
                                                    transactionId, paymentStatus, planStatus));
                                        }
                                        // initialise adapter and put values to recycel view
                                        MyAdsRunningRecycleAdapter mAdapterRunnings = new MyAdsRunningRecycleAdapter(mContext, mListRunningAds);
                                        mRecycle.setAdapter(mAdapterRunnings);
                                    }
                                }
                            } else {
                                //uihandle.showToast(response.optString(("message")));
                            }
                        } else {
                            uihandle.showToast("Something went wrong");
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_GET_ADS_DETAIL_WITH_STATUS, status);


    }

    private void fetchAdsWithUnderReviewStatus(String status, final RecyclerView mRecycle) {
        new FetchAdsDetailWithStatus(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (mListUnderReviewAds.size() > 0) {
                        mListUnderReviewAds.clear();
                    }
                    if (response != null) {
                        Log.d("history", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

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

                                            mListUnderReviewAds.add(new UnderReviewAdModel(purchaseid, placeId, placeName, planId,
                                                    planName, price, batchId, purchaseDate, ExpiryDate,
                                                    transactionId, paymentStatus, planStatus));
                                        }
                                        // initialise adapter and put values to recycel view
                                        AdapterAdUnderReview mAdapterRunnings = new AdapterAdUnderReview(mContext, mListUnderReviewAds);
                                        mRecycle.setAdapter(mAdapterRunnings);
                                    }
                                }
                            } else {
                                // uihandle.showToast(response.optString(("message")));
                            }
                        } else {
                            uihandle.showToast("Something went wrong");
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_GET_ADS_DETAIL_WITH_STATUS, status);


    }

    private void fetchAdsWithRejectedStatus(String status, final RecyclerView mRecycle) {
        new FetchAdsDetailWithStatus(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (mListRejectedAds.size() > 0) {
                        mListRejectedAds.clear();
                    }
                    if (response != null) {
                        Log.d("history", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

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

                                            mListRejectedAds.add(new RejectedAdModel(purchaseid, placeId, placeName, planId,
                                                    planName, price, batchId, purchaseDate, ExpiryDate,
                                                    transactionId, paymentStatus, planStatus));
                                        }
                                        // initialise adapter and put values to recycel view
                                        AdapterRejectedAds mAdapterRunnings = new AdapterRejectedAds(mContext, mListRejectedAds);
                                        mRecycle.setAdapter(mAdapterRunnings);
                                    }
                                }
                            } else {
                                // uihandle.showToast(response.optString(("message")));
                            }
                        } else {
                            uihandle.showToast("Something went wrong");
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_GET_ADS_DETAIL_WITH_STATUS, status);


    }

    private void fetchAdsWithExpiredStatus(String status, final RecyclerView mRecycle) {
        new FireApiGetExpiredAds(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (mListExpiredAds.size() > 0) {
                        mListExpiredAds.clear();
                    }
                    if (response != null) {
                        Log.d("expired_ads", response.toString());
                        if (response.has("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {

                                    JSONArray jObj = response.optJSONArray("data");
                                    if (jObj != null) {
                                        for (int i = 0; i < jObj.length(); i++) {
                                            JSONObject jResponse = jObj.optJSONObject(i);

                                            String purchaseid = jResponse.optString("purchase_id").toString().trim();
                                            String placeId = jResponse.optString("place_id").toString().trim();
                                            String placeName = jResponse.optString("place_name").toString().trim();
                                            String planName = jResponse.optString("plan_name").toString().trim();
                                            String planId = jResponse.optString("plan_id").toString().trim();
                                            String price = jResponse.optString("price").toString().trim();
                                            String latest_price = jResponse.optString("latest_price").toString().trim();

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

                                            mListExpiredAds.add(new ExpiredAdModel(latest_price,purchaseid, placeId, placeName, planId,
                                                    planName, price, batchId, purchaseDate, ExpiryDate,
                                                    transactionId, paymentStatus));
                                        }
                                        // initialise adapter and put values to recycel view
                                        AdapterExpiredAds mAdp = new AdapterExpiredAds(mContext, mListExpiredAds);
                                        mRecycle.setAdapter(mAdp);
                                    }
                                }
                            } else {

                                uihandle.showToast(response.optString(("message")));
                            }

                        } else {
                            uihandle.showToast("Something went wrong");
                        }
                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }
            }
        }.execute(URLListApis.URL_GET_EXPIRED_ADS);
    }

    public void choosePlanDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Create Ads")
                .setContentText(msg)
                .setConfirmText("OK")


                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();


                    }
                }).show();

    }

    private void buttonSwitchingHandler(int state, RecyclerView mRecyclerView, ImageView plusImage, ImageView minusImage) {
        if (state == View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
            plusImage.setVisibility(View.GONE);
            minusImage.setVisibility(View.VISIBLE);

        } else if (state == View.GONE) {

            mRecyclerView.setVisibility(View.GONE);
            plusImage.setVisibility(View.VISIBLE);
            minusImage.setVisibility(View.GONE);

        }

    }


}
