package za.asa_media.awesome_sa.modules_.login_;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.customizedfragment.CustomizedPlaceFragment;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.common_util_.api_request_volley.HttpRequester;
import za.asa_media.awesome_sa.modules_.registered_users.BasicInfo;
import za.asa_media.awesome_sa.modules_.registered_users.PostNewAdPresenter;
import za.asa_media.awesome_sa.modules_.registered_users.StaticValuesEditProfile;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-05 on 28-Mar-18.
 */

public class SignUpActivityPart2 extends CommonActivity {


    protected EditText mEditCategoryName;

    protected TextView mTextSelectBusinessRegister;
    protected EditText mEditBusinessName;
    protected EditText mEditAddress;

    private CustomizedPlaceFragment mSearchLocationFragment;

    private PostNewAdPresenter mPresenter;
    private Activity mContext = this;
    private UiHandleMethods uihandle;
    private Button mBtnSignUp;
    private SessionCityOculus mSession;

    private String mCategoryName = "", mBusinessName = "", mAddress = "", userid = "", businessid = "",
            country = "", latitude = "", longitude = "", city = "", state = "", pincode = "", number = "", website = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part_2);


        init();
        getPreviousValues();
    }

    private void getPreviousValues() {
        userid = mSession.getLoggedId();
    }

    private void init() {
        String mB = "Select your <font color='#1D375E'>Google Registered Business?</font>";

        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        mPresenter = new PostNewAdPresenter();

        mBtnSignUp = (Button) findViewById(R.id.btn_signup);
        mEditCategoryName = (EditText) findViewById(R.id.edt_business_category_name);
        mTextSelectBusinessRegister = (TextView) findViewById(R.id.m_text_register_business);
        mEditBusinessName = (EditText) findViewById(R.id.edt_business_name);
        mEditAddress = (EditText) findViewById(R.id.edt_address);

        mSearchLocationFragment = (CustomizedPlaceFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mSearchLocationFragment.setAllowReturnTransitionOverlap(true);
        mSearchLocationFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                //   TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place);

                //   uihandle.showToast(place.getId() + "\n" + place.getName() + "\n" + place.getAddress());
                //    slected place lat long

                businessid = place.getId();
                mBusinessName = place.getName().toString();
                number = String.valueOf(place.getPhoneNumber().toString());
                longitude = String.valueOf(place.getLatLng().longitude);
                latitude = String.valueOf(place.getLatLng().latitude);

                StaticValuesEditProfile.businessid = businessid;
                StaticValuesEditProfile.business_name = mBusinessName;

                if (place.getWebsiteUri() != null) {
                    website = String.valueOf(place.getWebsiteUri());
                } else {
                    website = "";
                }
                getPlaceDetailName(place.getLatLng().latitude, place.getLatLng().longitude);

                // get full detail of that particular place
                mEditBusinessName.setText(mBusinessName);

                //    uiHandle.onLocationChanged(mTextMyLocationNameHeader);
                //     mTextMyLocationNameHeader.setText(Html.fromHtml("<font color='#7F7F7F'>Your Location<br></font>" + place.getName()));
                //    mFrameLocation.setVisibility(View.GONE);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                uihandle.showToast("please provide accurate detail");
            }
        });


        //  Implement Values
        mTextSelectBusinessRegister.setText(Html.fromHtml(mB));
        mEditBusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchLocationFragment.zzJk();
            }
        });

        mEditCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCatagories();
            }
        });
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpClick();
            }
        });
    }


    public void onSignUpClick() {
        if (!checkFieldForEmpty()) {
            if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                uihandle.showToast("Internet Unavailable");
            } else {
                //  Todo: Fire Api
                goForSignUp(100);

            }
        } else {
            uihandle.showToast("");
        }
    }


    private boolean checkFieldForEmpty() {
        boolean mFlag = false;

        mCategoryName = mEditCategoryName.getText().toString().trim();
        mBusinessName = mEditBusinessName.getText().toString().trim();
        mAddress = mEditAddress.getText().toString().trim();

        if (TextUtils.isEmpty(mCategoryName)) {
            mEditCategoryName.setError("Field can't be empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(mBusinessName)) {
            mEditBusinessName.setError("Field can't be empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(mAddress)) {
            mEditAddress.setError("Address field can't be empty");
            mFlag = true;
        }

        return mFlag;
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
                    country = addresses.get(0).getCountryName();
                }
                if (addresses.get(0).getAddressLine(0) != null) {
                    mAddress = addresses.get(0).getAddressLine(0);
                }
                if (addresses.get(0).getAdminArea() != null) {
                    state = addresses.get(0).getAdminArea();
                }
                if (addresses.get(0).getPostalCode() != null) {
                    pincode = addresses.get(0).getPostalCode();
                }

                if (addresses.get(0).getSubAdminArea() != null) {
                    city = addresses.get(0).getSubAdminArea();
                } else if (addresses.get(0).getLocality() != null)
                    city = addresses.get(0).getLocality();

                mEditAddress.setText(mAddress);
                uihandle.setCursorOnLast(mEditAddress, mEditAddress.length());

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCatagories() {
        new MaterialDialog.Builder(mContext)
                .title("Categories")
                .items(mPresenter.getNearByGooglePlaces())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        //  String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        // goForPlaces(keyValue);

                        mEditCategoryName.setText(text.toString().trim());

                        StaticValuesEditProfile.category = text.toString().trim();
                        //     uihandle.showToast(text.toString());
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void goForSignUp(int mServiceCode) {
        //      mPhoneNumber = (new StringBuilder(mEdtCountryCode.getText().toString().trim())).append(refineNumberFromExtraSymbols(mEditPhoneNumber.getText().toString().trim())).toString();
        //      Todo: Remove after testing and uncomment above
        if (!isNetworkConnected()) {
            showToast(NETWORK_ERROR);
            return;
        }
   /* Parameters :
:
: ::
: ::::
:::
:*/
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", userid); // 197
        map.put("name", mBusinessName);
        map.put("businessid", businessid);
        map.put("category", mCategoryName);
        map.put("country", country);
        map.put("address", mAddress);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("city", city);
        map.put("state", state);
        map.put("pincode", pincode);
        map.put("number", number);
        map.put("website", website);

        showIOSProgress("Loading...");
        new HttpRequester(Request.Method.POST, this, map,
                mServiceCode, URLListApis.URL_SAVE_BUSINESS_DETAILS, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        try {
            JSONObject jsonObject = null;
            Log.e("Response", response);

            if (!TextUtils.isEmpty(response) || !response.equals("")) {
                jsonObject = new JSONObject(response);
            }

            switch (serviceCode) {
                case 100:
                    dismissIOSProgress();
                    if (jsonObject != null) {
                        // {"status":false,"message":"No new post"}
                        if (jsonObject.optBoolean("status")) {
                            uihandle.showToast(jsonObject.optString("message"));

                            String mUniqueBusinessId = jsonObject.optJSONObject("data").optString("business_unique_id");

                            StaticValuesEditProfile.id = mUniqueBusinessId;

                            // go for singn up part 2   //
                            uihandle.explicitIntent(BasicInfo.class);
                            mContext.finish();

                        } else {
                            uihandle.showToast(jsonObject.optString("message"));
                        }
                    } else {
                        //showFancyToast(TastyToast.CONFUSING, ERROR_EMPTY_JSON);
                        showToast(ERROR_EMPTY_JSON);
                    }
                    break;


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void onErrorFound(String errorResponse, int serviceCode) {
        super.onErrorFound(errorResponse, serviceCode);
        try {
            dismissIOSProgress();
            Log.e("Response", errorResponse);
            showToast("Something went wrong, try later!");
            //    JSONObject jsonObject = new JSONObject(errorResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}


