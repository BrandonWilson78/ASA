package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.AdapterTransactionHistory;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetTransactionHistory;
import za.asa_media.awesome_sa.modules_.registered_users.model.TransactionHistoryModel;
import za.asa_media.awesome_sa.support.ScaleInItemAnimator;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;


public class TransactionHistoryFragment extends Fragment {


    private Activity mContext;
    private UiHandleMethods uihandle;

    // recyclerview decalarations
    private RecyclerView mRecycleTransactions;
    private AdapterTransactionHistory mAdapter;
    private List<TransactionHistoryModel> mListTransactions;
    private View mView = null;

    private TextView mTextViewTransactionText;


    public TransactionHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        }
        // calling initviews
        initViews(mView);
        initialiseRecyclerView();
        return mView;
    }

    private void initViews(View view) {
        // initialisations
        uihandle = new UiHandleMethods(mContext);

        mListTransactions = new ArrayList<>();
        mRecycleTransactions = view.findViewById(R.id.lst_paymer);
        mTextViewTransactionText = view.findViewById(R.id.txt_your_history);
        // mTextViewTransactionText.setText("You have not made any Transactin yet!");
        //  mTextViewTransactionText.setVisibility(View.VISIBLE);

    }

    private void initialiseRecyclerView() {
        mRecycleTransactions.setLayoutManager(new LinearLayoutManager(mContext));
        // mRecycleNotifications.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecycleTransactions.setItemAnimator(new ScaleInItemAnimator());

        //  mRecycleNotifications.setItemAnimator(new OvershootInLeftAnimator());

        // calling for history retrieval
        getTransactions();
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


    private void getTransactions() {

        new FireApiGetTransactionHistory(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (response != null) {
                        Log.d("history", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {

                                if (response.optString("message").equals("No History Found".trim())) {
                                    mTextViewTransactionText.setVisibility(View.VISIBLE);
                                    //uihandle.showToast(response.optString("message"));
                                }

                                //uihandle.showToast(response.optString(("message")));
                                if (response.has("data")) {
                                    String mDtString = response.optString("data");
                                    if (!TextUtils.isEmpty(mDtString)) {
                                        JSONArray jObj = response.optJSONArray("data");
                                        if (jObj != null) {
                                            for (int i = 0; i < jObj.length(); i++) {
                                                JSONObject jResponse = jObj.optJSONObject(i);

                                                String placeName = jResponse.optString("place_name").toString().trim();
                                                String planName = jResponse.optString("plan_name").toString().trim();
                                                String price = jResponse.optString("price").toString().trim();
                                                String purchaseDate = jResponse.optString("purchase_date").toString().trim();
                                                String ExpiryDate = jResponse.optString("expiry_date").toString().trim();

                                                if (TextUtils.isEmpty(ExpiryDate)) {
                                                    ExpiryDate = "N/A";
                                                }

                                                String transactionId = jResponse.optString("transaction_id").toString().trim();
                                                if (transactionId == null) {
                                                    transactionId = "N/A";
                                                }
                                                String paymentStatus = jResponse.optString("payment_status").toString().trim();
                                                String adStatus = jResponse.optString("ad_status").toString().trim();

                                                mListTransactions.add(new TransactionHistoryModel(placeName,
                                                        planName, price, purchaseDate, ExpiryDate,
                                                        transactionId, paymentStatus, adStatus));
                                            }
                                            // initialise adapter and put values to recycel view
                                            if (mListTransactions.size() > 0) {
                                                mTextViewTransactionText.setVisibility(View.GONE);
                                                mAdapter = new AdapterTransactionHistory(mContext, mListTransactions);
                                                mRecycleTransactions.setAdapter(mAdapter);
                                            }

                                        }
                                    } else {
                                        mTextViewTransactionText.setVisibility(View.VISIBLE);
                                        // uihandle.showToast(response.optString("message"));
                                    }


                                }

                            } else {
                                mTextViewTransactionText.setVisibility(View.VISIBLE);
                                // uihandle.showToast(response.optString("message"));
                            }


                        } else {
                            uihandle.showToast("something went wrong!");
                        }
                    } else {
                        mTextViewTransactionText.setText("something went wrong!");
                        mTextViewTransactionText.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }
            }
        }

                .execute(URLListApis.URL_PURCHASE_HISTORY);

    }


}
