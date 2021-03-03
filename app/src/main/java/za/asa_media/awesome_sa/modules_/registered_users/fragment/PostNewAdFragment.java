package za.asa_media.awesome_sa.modules_.registered_users.fragment;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.customizedfragment.CustomizedPlaceFragment;
import za.asa_media.awesome_sa.modules_.FireApi.FireApiAddBusinessPost;
import za.asa_media.awesome_sa.modules_.registered_users.ChoosePlan;
import za.asa_media.awesome_sa.modules_.registered_users.PostNewAdPresenter;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

import static android.content.ContentValues.TAG;


public class PostNewAdFragment extends Fragment {

    private Activity mContext;
    private TextView mTextViewContinue;
    private UiHandleMethods uihandle;
    private View mView = null;
    // search business on google database below fragment getting detail

    /*** Custom PlaceAutoComplete Fragment  ***/
    private CustomizedPlaceFragment autocompleteFragment;

    private String businessName, businessCatagory, country, streetAddress, city, state, pinCode, number, webAddress, placeId;
    private EditText mEditTextAddBusinessName, mEditTextAddBusinessCatagry, mEditTextCountry, mEditTextStreetAddress, mEditTextCity, mEditTextState, mEditTextPinCode, mEditTextPhoneNumber, mEditTextWebsiteAddress;

    // session object required to get user id and token initial saved
    private SessionCityOculus mSession;



   /**//*
   /**/
    /**** Social Platforms  ***//**//**//**/
    private EditText mEditTextFacebook, mEditTextLinkedin, mEditTextTwitter, mEditTextGooglePlus;
    private EditText mEditTextInstagram, mEditTextYoutube, mEditTextVimeo, mEditTextSnapChat;

    private String mFacebook, mTwitter, mLinkedin, mGooglePlus, mInstagram, mYoutube, mVimeo, mSnapchat;

    private PostNewAdPresenter mPresenter;

    // new design


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        mPresenter = new PostNewAdPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //    Inflate the layout for this fragment
        if (mView == null) {
            //  mView = inflater.inflate(R.layout.my_test_layout, container, false);
            mView = inflater.inflate(R.layout.fragment_post_new_ad, container, false);
        }
        initialisationSetup(mView);
        return mView;
    }


    private void initialisationSetup(View mView) {

        // Business info
        autocompleteFragment = (CustomizedPlaceFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mTextViewContinue = mView.findViewById(R.id.txt_continue);
        mEditTextAddBusinessName = mView.findViewById(R.id.add_business_name);
        mEditTextAddBusinessCatagry = mView.findViewById(R.id.add_business_category);
        mEditTextCountry = mView.findViewById(R.id.add_country);
        mEditTextStreetAddress = mView.findViewById(R.id.add_street_address);
        mEditTextCity = mView.findViewById(R.id.add_city);
        mEditTextState = mView.findViewById(R.id.add_state);
        mEditTextPinCode = mView.findViewById(R.id.add_pin_code);
        mEditTextPhoneNumber = mView.findViewById(R.id.add_business_contact_number);
        mEditTextWebsiteAddress = mView.findViewById(R.id.add_website_address);

        // implement values from previous detail
        if (mSession.getFlagFromDetail()) {
            uiHnadlingComp(true);

            autocompleteFragment.setText(mSession.getBusinessNameTabbed());

            mEditTextAddBusinessName.setText(mSession.getBusinessNameTabbed());
            mEditTextAddBusinessCatagry.setText(mSession.getCategoryTabbed());
            mEditTextCountry.setText(mSession.getCountryTabbed());
            mEditTextStreetAddress.setText(mSession.getAddressTabbed());
            mEditTextCity.setText(mSession.getCityTabbed());
            mEditTextState.setText(mSession.getStateTabbed());
            mEditTextPinCode.setText(mSession.getPinCodeTabbed());
            mEditTextPhoneNumber.setText(mSession.getPhoneTabbed());
            mEditTextWebsiteAddress.setText(mSession.getWebsiteLinkTabbed());
        } else {
            uiHnadlingComp(false);
        }


        /**** Social Platforms  ***/
        mEditTextFacebook = mView.findViewById(R.id.add_facebook_link);
        mEditTextLinkedin = mView.findViewById(R.id.add_linkdin_link);
        mEditTextTwitter = mView.findViewById(R.id.add_twitter_link);
        mEditTextGooglePlus = mView.findViewById(R.id.add_google_link);
        mEditTextInstagram = mView.findViewById(R.id.add_instagram);
        mEditTextVimeo = mView.findViewById(R.id.add_vimeo_link);
        mEditTextSnapChat = mView.findViewById(R.id.add_snapchat_link);
        mEditTextYoutube = mView.findViewById(R.id.add_youtube_link);

        mEditTextAddBusinessCatagry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCatagories();
            }
        });
        /*   typeFilter = new AutocompleteFilter.Builder().setCountry("IN").setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE) .build();  */

        //    autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setAllowReturnTransitionOverlap(true);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                uiHnadlingComp(true);
                //uihandle.showToast(place.getId() + "\n" + place.getName() + "\n" + place.getAddress());
                // slected place lat long
                placeId = place.getId();
                businessName = place.getName().toString();
                number = String.valueOf(place.getPhoneNumber().toString());
                // address = place.getAddress().toString();

                // String spl[] = address.split(",");
                // save place id for further use

                mSession.setPlaceId(placeId);
                mSession.setCAdPlaceId(placeId);


                mSession.setlatitudeTabbed(String.valueOf(place.getLatLng().latitude));
                mSession.setLongitudeTabbed(String.valueOf(place.getLatLng().longitude));

                // get full detail of that particular place
                getPlaceDetailName(place.getLatLng().latitude, place.getLatLng().longitude);
            /*
              if (spl.length > 3) {
                    country = spl[spl.length - 1];
                    String pin_state[] = spl[3].split(" ");

                    state = pin_state[0];
                    pinCode = pin_state[1];

                    city = spl[spl.length - 3];
                    streetAddress = spl[0] + spl[1];
                }
              */

                mEditTextAddBusinessName.setText("" + businessName);
                mEditTextPhoneNumber.setText("" + number);

                if (place.getWebsiteUri() != null) {
                    mEditTextWebsiteAddress.setText("" + place.getWebsiteUri());
                } else {
                    mEditTextWebsiteAddress.setText("");
                }

                //      goForUpdateInfoOnMap(sLat, sLon);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                uihandle.showToast("please provide accurate detail");
            }
        });

        //     autocompleteFragment.setText("Pizza hut");
        //     autocompleteFragment.zzJk();

        //     Continue Click
        mTextViewContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForAddBusiness("");

            }
        });

    }

    public void goForAddBusiness(String mAgree) {
        if (!checkFieldForEmpty()) {
            if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                uihandle.showToast("Check your Internet Connectivity");
            } else {
                //Fire Add post api here
                setSocialVal();
                new FireApiAddBusinessPost(mContext) {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        uihandle.startProgress("While post is being saved...");
                    }

                    @Override
                    protected void onPostExecute(JSONObject response) {
                        super.onPostExecute(response);
                        try {
                            uihandle.stopProgressDialog();
                            if (response != null) {
                                Log.d("info", response.toString());
                                if (response.has("status")) {
                                    if (response.optBoolean("status")) {
                                        uihandle.showToast(response.optString(("message")));

                                        mSession.setBPlaceId(mSession.getPlaceId());
                                        mSession.setBLat(mSession.getLatitudeTabbed());
                                        mSession.setBLon(mSession.getLongitudeTabbed());


                                        uihandle.explicitIntent(ChoosePlan.class);
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
                }.execute(URLListApis.URL_ADD_BUSINESS, mSession.getToken(), mSession.getLoggedId(), mSession.getPlaceId(), mEditTextAddBusinessName.getText().toString().trim(), mEditTextAddBusinessCatagry.getText().toString().trim(), mEditTextCountry.getText().toString().trim(), mEditTextStreetAddress.getText().toString().trim(), mEditTextCity.getText().toString().trim(), mEditTextState.getText().toString().trim(), mEditTextPinCode.getText().toString().trim(), mEditTextPhoneNumber.getText().toString().trim(), mEditTextWebsiteAddress.getText().toString().trim(),
                        mFacebook, mTwitter, mGooglePlus, mInstagram, mLinkedin, mYoutube, mVimeo, mSnapchat,
                        mSession.getLatitudeTabbed(), mSession.getLongitudeTabbed(), mAgree);
            }
        }
    }

    public void createWarningDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!")
                .setContentText(msg)
                .setConfirmText("Proceed")
                .setCancelText("Cancel")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();

                        goForAddBusiness("go");

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })

                .show();
    }

    private void setSocialVal() {
        mFacebook = mEditTextFacebook.getText().toString();
        mTwitter = mEditTextTwitter.getText().toString();
        mLinkedin = mEditTextLinkedin.getText().toString();
        mInstagram = mEditTextInstagram.getText().toString();
        mGooglePlus = mEditTextGooglePlus.getText().toString();
        mSnapchat = mEditTextSnapChat.getText().toString();
        mVimeo = mEditTextVimeo.getText().toString();
        mYoutube = mEditTextYoutube.getText().toString();

    }

    private void uiHnadlingComp(boolean flag) {
        //  mEditTextAddBusinessCatagry.setEnabled(flag);
        mEditTextAddBusinessName.setEnabled(flag);
        //   mEditTextAddBusinessCatagry.setEnabled(flag);
        mEditTextCountry.setEnabled(flag);
        mEditTextStreetAddress.setEnabled(flag);
        mEditTextCity.setEnabled(flag);
        mEditTextState.setEnabled(flag);
        mEditTextPinCode.setEnabled(flag);
        mEditTextPhoneNumber.setEnabled(flag);
        mEditTextWebsiteAddress.setEnabled(flag);
    }

    // check for empty fields
    private boolean checkFieldForEmpty() {
        boolean mFlag = false;

        businessName = mEditTextAddBusinessName.getText().toString().trim();
        businessCatagory = mEditTextAddBusinessCatagry.getText().toString().trim();
        country = mEditTextCountry.getText().toString().trim();
        streetAddress = mEditTextStreetAddress.getText().toString().trim();
        city = mEditTextCity.getText().toString().trim();
        state = mEditTextState.getText().toString().trim();
        pinCode = mEditTextPinCode.getText().toString().trim();
        number = mEditTextPhoneNumber.getText().toString().trim();
        webAddress = mEditTextWebsiteAddress.getText().toString().trim();


        if (TextUtils.isEmpty(businessName)) {
            uihandle.showToast("Business can't be empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(businessCatagory)) {
            uihandle.showToast("Category can't be empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(country) || country == null) {
            uihandle.showToast("Please provide country");
            mFlag = true;
        } else if (TextUtils.isEmpty(streetAddress) || streetAddress == null) {
            uihandle.showToast("Please provide address");
        } else if (TextUtils.isEmpty(city) || city == null) {
            uihandle.showToast("Please provide city");
            mFlag = true;
        } else if (TextUtils.isEmpty(state) || state == null) {
            uihandle.showToast("Please provide state");
            mFlag = true;
        } else if (TextUtils.isEmpty(pinCode) || pinCode == null) {
            uihandle.showToast("Please provide pinCode");
            mFlag = true;
        } else if (TextUtils.isEmpty(number) || number == null) {
            uihandle.showToast("Please provide p.number");
            mFlag = true;
        }

        if (TextUtils.isEmpty(webAddress) || webAddress == null) {
            webAddress = "http://";
        }

        return mFlag;
    }

    /* */
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
                    mEditTextCountry.setText("" + addresses.get(0).getCountryName());
                }
                if (addresses.get(0).getAddressLine(0) != null) {
                    mEditTextStreetAddress.setText("" + addresses.get(0).getAddressLine(0));
                }
                if (addresses.get(0).getAdminArea() != null) {
                    mEditTextState.setText("" + addresses.get(0).getAdminArea());
                }
                if (addresses.get(0).getPostalCode() != null) {
                    mEditTextPinCode.setText("" + addresses.get(0).getPostalCode());
                }

                if (addresses.get(0).getSubAdminArea() != null) {
                    mEditTextCity.setText("" + addresses.get(0).getSubAdminArea());
                } else if (addresses.get(0).getLocality() != null)
                    mEditTextCity.setText("" + addresses.get(0).getLocality());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.mContext = context;
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }


    private void getCatagories() {
        new MaterialDialog.Builder(getActivity())
                .title("Categories")
                .items(mPresenter.getNearByGooglePlaces())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        //  String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        // goForPlaces(keyValue);
                        mEditTextAddBusinessCatagry.setText(text.toString().trim());
                        //     uihandle.showToast(text.toString());
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

}

 /*  @Override
    public void onPause() {
        super.onPause();
      *//*  try {
            autocompleteFragment = null;
        }catch (Exception e)
        {
            uihandle.showToast(e.toString());
        }*//*
             getChildFragmentManager().beginTransaction()
                .remove(autocompleteFragment)
                .commit();
    }*/