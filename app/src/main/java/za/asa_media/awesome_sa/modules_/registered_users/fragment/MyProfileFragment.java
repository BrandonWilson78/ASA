package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.BasicInfoEdit;
import za.asa_media.awesome_sa.modules_.registered_users.IImageChangerCallback;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.modules_.registered_users.StaticValuesEditProfile;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetUserInfo;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-05 on 6/17/2017.
 */

public class MyProfileFragment extends Fragment {

    private Activity mContext;
    private UiHandleMethods uihandle;
    private View mView = null;
    private CircleImageView mProfileImage;
    private ImageView mImageProfileBackground, mImageEditProfile;
    private TextView
            /*mTextViewNameHeader,*/ mTextViewEmailHeader, mTextViewDobHeader,
            mTextViewEmail, mTextViewFirstname, mTextViewLastname, mTextViewDateOfBirth,
            mTextViewCity, mTextViewCountry, mTextViewPhoneNumber; /* mTextViewLat, mTextViewLong;*/

    private TextView mTextBBusinessName, mTextBBusinessCategory, mTextBAddress, mTextBCountry,
            mTextBState, mTextBPinCode, mTextBBusinessContact, mTextBWebAddress,
            mTextBFacebookLink, mTextBGoogleLink, mTextBYoutube, mTextBInstagramLink;


    private SessionCityOculus mSession;
    private ProgressDialog pd;
    private TextView mTextViewFullName;

    //   View pager setup
    private IImageChangerCallback mCallback;

    public MyProfileFragment() {
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
            mView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        }

        initViews(mView);
        return mView;
    }

    private void initViews(View view) {
        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        /** Business Profile Info including Social info  **/
        mTextBBusinessName = view.findViewById(R.id.textview_business_value);
        mTextBBusinessCategory = view.findViewById(R.id.textview_business_category_value);
        mTextBAddress = view.findViewById(R.id.textview_street_address_value);
        mTextBCountry = view.findViewById(R.id.textview_business_country_value);
        mTextBState = view.findViewById(R.id.textview_state_value);
        mTextBPinCode = view.findViewById(R.id.textview_pin_code_value);
        mTextBBusinessContact = view.findViewById(R.id.textview_business_contact_value);
        mTextBWebAddress = view.findViewById(R.id.textview_web_address_value);

        mTextBFacebookLink = view.findViewById(R.id.textview_facebook_value);
        mTextBGoogleLink = view.findViewById(R.id.textview_google_page_link_value);
        mTextBYoutube = view.findViewById(R.id.textview_youtube_link_value);
        mTextBInstagramLink = view.findViewById(R.id.textview_instagram_value);

        //   Profile Information
        mTextViewFullName = view.findViewById(R.id.textview_full_name);
        mImageEditProfile = view.findViewById(R.id.img_edit_profile);
        mProfileImage = view.findViewById(R.id.img_user);
        mImageProfileBackground = view.findViewById(R.id.img_user_background);

        //    mTextViewNameHeader = (TextView) view.findViewById(R.id.textview_username_header);
        mTextViewEmailHeader = view.findViewById(R.id.textview_email_header);
        mTextViewDobHeader = view.findViewById(R.id.textview_date_of_birth);

        mTextViewEmail = view.findViewById(R.id.textview_email_value);
        mTextViewFirstname = view.findViewById(R.id.textview_firstname_value);
        mTextViewLastname = view.findViewById(R.id.textview_last_name_value);
        mTextViewDateOfBirth = view.findViewById(R.id.textview_dob_value);
        mTextViewCity = view.findViewById(R.id.textview_city_value);
        mTextViewCountry = view.findViewById(R.id.textview_country_value);
        //   mTextViewLat = (TextView) view.findViewById(R.id.textview_latitude_value);
        //   mTextViewLong = (TextView) view.findViewById(R.id.textview_longitude_value);

        mImageEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.explicitIntent(BasicInfoEdit.class);
               /* mContext.finish();*/
            }
        });

        // calling for userinfo on opening
        getUserInfo();


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

                                        //  Store Session
                                        mSession.setLoggedId(jObj.optString("id"));
                                        mSession.setLoggedFirstname(jObj.optString("firstname"));
                                        mSession.setLoggedLastname(jObj.optString("lastname"));
                                        mSession.setLoggedEmail(jObj.optString("email"));
                                        mSession.setUserImageUrl(jObj.optString("image"));

                                        mSession.setLoggedLatitude(jObj.optString("lat"));
                                        mSession.setLoggedLong(jObj.optString("long"));
                                        mSession.setLoggedPhone(jObj.optString("phone"));
                                        mSession.setLoggedStatus(jObj.optString("status"));
                                        mSession.setLoggedCreatedOn(jObj.optString("created_on"));




                                        mTextViewFullName.setText("" + name);


                                        //    mTextViewNameHeader.setText("" + name);
                                        mTextViewEmailHeader.setText("" + jObj.optString("email"));
                                        mTextViewDobHeader.setText("" + jObj.optString("dob"));

                                        String mEmail = jObj.optString("email");
                                        mTextViewEmail.setText(mEmail);
                                        mTextViewFirstname.setText("" + UiHandleMethods.capitalizeString(jObj.optString("firstname")));
                                        mTextViewLastname.setText("" + jObj.optString("lastname"));

                                        String mDob = jObj.optString("dob");
                                        if (!mDob.equals("null")) {
                                            mTextViewDateOfBirth.setText(mDob);
                                            mSession.setLoggedDob(jObj.optString("dob"));
                                        }

                                        String mCity = jObj.optString("city");
                                        if (!mCity.equals("null")) {
                                            mTextViewCity.setText("" + UiHandleMethods.capitalizeString(mCity));
                                            mSession.setLoggedCity(jObj.optString("city"));
                                        }
                                        String mCountry = jObj.optString("country");
                                        if (!mCountry.equals("null")) {
                                            mTextViewCountry.setText("" + UiHandleMethods.capitalizeString(mCountry));
                                            mSession.setLoggedCountry(jObj.optString("country"));
                                        }

                                        //        mTextViewLat.setText("" + jObj.optString("lat"));
                                        //        mTextViewLong.setText("" + jObj.optString("long"));

                                        // Laoding Images
                                        if (!TextUtils.isEmpty(mSession.getUserImageUrl())) {
                                            //  uihandle.loadImageWithGlide(mSession.getUserImageUrl(), mProfileImage);

                                            uihandle.setImageWithLazyLoading(mSession.getUserImageUrl(), mProfileImage);
                                            //  uihandle.setImageWithLazyLoading(mImageProfileBackground, mSession.getUserImageUrl());
                                        }

                                        // Calling callback for the nav header change
                                        mCallback.getChangeImageAndData(UiHandleMethods.capitalizeString(jObj.optString("firstname")),jObj.optString("firstname"),mSession.getUserImageUrl());

                                        //  jObj.optString("status");
                                        //  jObj.optString("id");
                                        //  jObj.optString("created_on");

                                        //  uihandle.explicitIntent(LoggedInUserDashboard.class);
                                        //  context.finish();
                                        //  Business Profile Info
                                        JSONObject jObj2 = jArray.optJSONObject("1");

                                        StaticValuesEditProfile.instagram = jObj2.optString("instagram");
                                        StaticValuesEditProfile.linkdin = jObj2.optString("linkdin");
                                        StaticValuesEditProfile.google = jObj2.optString("google");
                                        StaticValuesEditProfile.vimeo = jObj2.optString("vimeo");
                                        StaticValuesEditProfile.website = jObj2.optString("website");
                                        StaticValuesEditProfile.userid = jObj2.optString("userid");
                                        StaticValuesEditProfile.state = jObj2.optString("state");
                                        StaticValuesEditProfile.number = jObj2.optString("number");
                                        StaticValuesEditProfile.country = jObj2.optString("country");
                                        StaticValuesEditProfile.city = jObj2.optString("city");
                                        StaticValuesEditProfile.id = jObj2.optString("id");
                                        StaticValuesEditProfile.pincode = jObj2.optString("pincode");
                                        StaticValuesEditProfile.twitter = jObj2.optString("twitter");
                                        StaticValuesEditProfile.businessid = jObj2.optString("businessid");
                                        StaticValuesEditProfile.category = jObj2.optString("category");
                                        StaticValuesEditProfile.snapchat = jObj2.optString("snapchat");
                                        StaticValuesEditProfile.address = jObj2.optString("address");
                                        StaticValuesEditProfile.facebook = jObj2.optString("facebook");
                                        StaticValuesEditProfile.business_name = jObj2.optString("name");
                                        StaticValuesEditProfile.youtube = jObj2.optString("youtube");
                                        StaticValuesEditProfile.longitude = jObj2.optString("longitude");
                                        StaticValuesEditProfile.latitude = jObj2.optString("latitude");

                                        // Apply values to views for display
                                        mTextBBusinessName.setText(StaticValuesEditProfile.business_name);
                                        mTextBBusinessCategory.setText(StaticValuesEditProfile.category);
                                        mTextBAddress.setText(StaticValuesEditProfile.address);
                                        mTextBCountry.setText(StaticValuesEditProfile.country);
                                        mTextBState.setText(StaticValuesEditProfile.state);
                                        mTextBPinCode.setText(StaticValuesEditProfile.pincode);
                                        mTextBBusinessContact.setText(StaticValuesEditProfile.number);
                                        mTextBWebAddress.setText(StaticValuesEditProfile.website);

                                        mTextBFacebookLink.setText(StaticValuesEditProfile.facebook);
                                        mTextBGoogleLink.setText(StaticValuesEditProfile.google);
                                        mTextBInstagramLink.setText(StaticValuesEditProfile.instagram);
                                        mTextBYoutube.setText(StaticValuesEditProfile.youtube);

                                        mSession.setBName(jObj2.optString("name"));
                                        mSession.setBCategory(jObj2.optString("category"));
                                        mSession.setBLat(jObj2.optString("latitude"));
                                        mSession.setBLon(jObj2.optString("longitude"));
                                        mSession.setBAddress(jObj2.optString("address"));

                                        mSession.setBPlaceId(jObj2.optString("businessid"));
                                        mSession.setPlaceIdTabbed(jObj2.optString("businessid"));

                                        mSession.setBPhone(jObj2.optString("number"));
                                        mSession.setBWebsiteLink(jObj2.optString("website"));

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
        if (context instanceof LoggedInUserDashboard) {
            mCallback = (IImageChangerCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
