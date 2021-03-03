package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.NotificationAdapter;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetNotifications;
import za.asa_media.awesome_sa.modules_.registered_users.model.NotificationModel;
import za.asa_media.awesome_sa.support.ScaleInItemAnimator;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class NotificationsFragment extends Fragment {
    private Activity mContext;
    private TextView mTextViewHeading;

    private UiHandleMethods uihandle;
    private Toolbar mToolbar;
    private ImageView mImageBackArrow;

    // recyclerview decalarations
    private RecyclerView mRecycleNotifications;
    private NotificationAdapter mAdapter;
    private List<NotificationModel> mNotificatonListData;

    private View mView = null;
    private TextView mTextViewNotificationText;

    public NotificationsFragment() {
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
            mView = inflater.inflate(R.layout.fragment_notifications, container, false);
        }

        // calling initviews
        initViews(mView);
        initialiseRecyclerView();
        return mView;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.mContext = context;

    }

    private void initViews(View view) {
        // initialisations
        uihandle = new UiHandleMethods(mContext);

        mNotificatonListData = new ArrayList<>();
        mRecycleNotifications = view.findViewById(R.id.lst_notofication);
        mTextViewNotificationText = view.findViewById(R.id.txt_your_notification);


    }

    private void initialiseRecyclerView() {
        mRecycleNotifications.setLayoutManager(new LinearLayoutManager(mContext));
        // mRecycleNotifications.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecycleNotifications.setItemAnimator(new ScaleInItemAnimator());

        //  mRecycleNotifications.setItemAnimator(new OvershootInLeftAnimator());

        getNotifications();
    }

    public void getNotifications() {
        new FireApiGetNotifications(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    //  uihandle.stopProgressDialog();
                    if (response != null) {
                        Log.d("notifications", response.toString());
                        if (response.optBoolean("status")) {
                            //uihandle.showToast(response.optString(("message")));
                            if (response.has("data")) {
                                JSONArray jObj = response.optJSONArray("data");
                                if (jObj != null) {
                                    for (int i = 0; i < jObj.length(); i++) {
                                        JSONObject jResponse = jObj.optJSONObject(i);

                                        String planName = jResponse.optString("plan_name").toString().trim();
                                        String detail = jResponse.optString("detail").toString().trim();
                                        String purchaseDate = jResponse.optString("purchase_date").toString().trim();
                                        String activationDate = jResponse.optString("activation_date").toString().trim();
                                        String ExpiryDate = jResponse.optString("expiry_date").toString().trim();

                                        mNotificatonListData.add(new NotificationModel(planName, purchaseDate, activationDate, ExpiryDate, detail));
                                    }

                                    // initialise adapter and put values to recycel view
                                    if (mNotificatonListData.size() > 0) {
                                        mTextViewNotificationText.setVisibility(View.GONE);
                                        mAdapter = new NotificationAdapter(mContext, mNotificatonListData);
                                        mRecycleNotifications.setAdapter(mAdapter);
                                    }
                                }
                            }
                        } else {
                            mTextViewNotificationText.setVisibility(View.VISIBLE);
                           // uihandle.showToast(response.optString("message"));
                        }
                    } else {
                        mTextViewNotificationText.setText("something went wrong!");
                        mTextViewNotificationText.setVisibility(View.VISIBLE);

                    }
                } catch (Exception e) {
                    uihandle.showToast(e.toString());
                }
            }
        }.execute(URLListApis.URL_GET_NOTIFICATIONS);
    }
}
