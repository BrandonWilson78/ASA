package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.PagerAdapterCategory;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetUserInfo;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class HomeFragment extends Fragment {

    private Activity mContext;
    private UiHandleMethods uihandle;
    private View mView = null;
    private CircleImageView mProfileImage;
    private ImageView mImageProfileBackground, mImageEditProfile;
    private TextView
            mTextViewNameHeader, mTextViewEmailHeader, mTextViewDobHeader,
            mTextViewEmail, mTextViewFirstname, mTextViewLastname, mTextViewDateOfBirth,
            mTextViewCity, mTextViewCountry, mTextViewPhoneNumber, mTextViewLat, mTextViewLong;
    private SessionCityOculus mSession;
    private ProgressDialog pd;

    //  view pager setup
    //  view pager decalarations
    private ViewPager pager;
    private CircleIndicator indicator;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
        }

        initViews(mView);
        return mView;
    }

    private void initViews(View view) {
        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        indicator = view.findViewById(R.id.indicator);
        pager = view.findViewById(R.id.pager);

        /*
        mImageEditProfile = (ImageView) view.findViewById(R.id.img_edit_profile);
        mProfileImage = (CircleImageView) view.findViewById(R.id.img_user);
        mImageProfileBackground = (ImageView) view.findViewById(R.id.img_user_background);

        mTextViewNameHeader = (TextView) view.findViewById(R.id.textview_username_header);
        mTextViewEmailHeader = (TextView) view.findViewById(R.id.textview_email_header);
        mTextViewDobHeader = (TextView) view.findViewById(R.id.textview_date_of_birth);

        mTextViewEmail = (TextView) view.findViewById(R.id.textview_email_value);
        mTextViewFirstname = (TextView) view.findViewById(R.id.textview_firstname_value);
        mTextViewLastname = (TextView) view.findViewById(R.id.textview_last_name_value);
        mTextViewDateOfBirth = (TextView) view.findViewById(R.id.textview_dob_value);
        mTextViewCity = (TextView) view.findViewById(R.id.textview_city_value);
        mTextViewCountry = (TextView) view.findViewById(R.id.textview_country_value);
        mTextViewLat = (TextView) view.findViewById(R.id.textview_latitude_value);
        mTextViewLong = (TextView) view.findViewById(R.id.textview_longitude_value);

        mImageEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.explicitIntent(BasicInfoEdit.class);
                mContext.finish();
            }
        });*/

        //   Calling for userinfo on opening
        //   getUserInfo();


        // apply listeners method
        applyListenersToViews();


    }

    private void applyListenersToViews() {
        PagerAdapterCategory mAdapter = new PagerAdapterCategory(getChildFragmentManager(), 3);
        pager.setAdapter(mAdapter);
        indicator.setViewPager(pager);


        //  Implement Images


    }

    private void getUserInfo() {
        new FireApiGetUserInfo(mContext) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ProgressDialog.show(mContext, "Please wait", "User Info is being refreshed..", false, false);
            }

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    pd.hide();
                    if (response != null) {
                        Log.d("home", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {
                                    JSONObject jArray = response.optJSONObject("data");
                                    if (jArray != null) {

                                        JSONObject jObj = jArray.optJSONObject("0");

                                        String name = UiHandleMethods.capitalizeString(jObj.optString("firstname") + " " + jObj.optString("lastname"));

                                        // Store Session
                                        mSession.setLoggedId(jObj.optString("id"));
                                        mSession.setLoggedFirstname(jObj.optString("firstname"));
                                        mSession.setLoggedLastname(jObj.optString("lastname"));
                                        mSession.setLoggedEmail(jObj.optString("email"));
                                        mSession.setUserImageUrl(jObj.optString("image"));
                                        mSession.setLoggedDob(jObj.optString("dob"));
                                        mSession.setLoggedCity(jObj.optString("city"));
                                        mSession.setLoggedCountry(jObj.optString("country"));
                                        mSession.setLoggedLatitude(jObj.optString("lat"));
                                        mSession.setLoggedLong(jObj.optString("long"));
                                        mSession.setLoggedPhone(jObj.optString("phone"));
                                        mSession.setLoggedStatus(jObj.optString("status"));
                                        mSession.setLoggedCreatedOn(jObj.optString("created_on"));


                                        mTextViewNameHeader.setText("" + name);
                                        mTextViewEmailHeader.setText("" + jObj.optString("email"));
                                        mTextViewDobHeader.setText("" + jObj.optString("dob"));

                                        mTextViewEmail.setText("" + jObj.optString("email"));
                                        mTextViewFirstname.setText("" + UiHandleMethods.capitalizeString(jObj.optString("firstname")));
                                        mTextViewLastname.setText("" + jObj.optString("lastname"));
                                        mTextViewDateOfBirth.setText("" + jObj.optString("dob"));

                                        mTextViewCity.setText("" + jObj.optString("city"));
                                        mTextViewCountry.setText("" + jObj.optString("country"));
                                        mTextViewLat.setText("" + jObj.optString("lat"));
                                        mTextViewLong.setText("" + jObj.optString("long"));

                                        // laoding images
                                        if (!TextUtils.isEmpty(mSession.getUserImageUrl())) {
                                            uihandle.loadImageWithGlide(mSession.getUserImageUrl(), mProfileImage);
                                            //  uihandle.setImageWithLazyLoading(mImageProfileBackground, mSession.getUserImageUrl());
                                        }


                                        // jObj.optString("status");
                                        // jObj.optString("id");
                                        // jObj.optString("created_on");

                                        //  uihandle.explicitIntent(LoggedInUserDashboard.class);
                                        // context.finish();
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
        }.execute(URLListApis.URL_GET_USERINFO);
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.mContext = context;
     /*   if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (uihandle != null) {
            uihandle = null;
        }
        if (mView != null) {
            mView = null;
        }


    }


}
