package za.asa_media.awesome_sa.modules_.registered_users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.customizedfragment.CustomizedPlaceFragment;
import za.asa_media.awesome_sa.modules_.FireApi.FireApiAddUserInfo;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class BasicInfoEdit extends AppCompatActivity {

    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private UiHandleMethods uihandle;
    private CircleImageView mProfileImage;
    private EditText
         mEditTextDateOfBirth,
            mEditTextCity, mEditTextCountry,
           mEditTextPhoneNumber;

    /***   Full Name Text VIew ***/
    private TextView mTextViewFullName;

    private Button mBtnCancel, mBtnDone;
    private FloatingActionButton mFloatingCameraImage;
    private Activity mContext = this;
    /*  private PlaceAutocompleteFragment autocompleteFragment;*/
    private String lat, lon;
    private String dob, phone, city, country, f_name = "", l_name = "";
    // session object
    private SessionCityOculus mSession;
    private String image = "";
    private String path = "";
    private CustomizedPlaceFragment mSearchLocationFragment;

    private EditText mEditViewFirstName, mEditViewLastName, mEditBBusinessName, mEditBBusinessCategory, mEditBAddress, mEditBCountry,
            mEditBState, mEditBPinCode, mEditBBusinessContact, mEditBWebAddress,
            mEditBFacebookLink, mEditBGoogleLink, mEditBYoutube, mEditBInstagramLink;

    private String mCategoryName = "", mBusinessName = "", mAddress = "", userid = "", businessid = "",
            business_country = "", business_latitude = "", business_longitude = "",
            business_city = "", business_state = "", business_pincode = "",
            business_number = "", business_website = "";

    private PostNewAdPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.new_app_blue_color));
        setContentView(R.layout.activity_basic_info_edit);

        // Calling initviews
        initViews();
        applyValuesToViews();
        listeners();
    }

    private void listeners() {

        mEditBBusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchLocationFragment.zzJk();
            }
        });

        mEditBBusinessCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCatagories();
            }
        });


        mEditTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.getCalendarDialogDate(mEditTextDateOfBirth);
            }
        });

        mFloatingCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uihandle.hasCamera()) {
                    Image_Picker_Dialog();
                } else {
                    uihandle.showToast("sorry,camera not available");
                }


            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(mContext, LoggedInUserDashboard.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                mContext.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uihandle.explicitIntent(StartAdvertiseBusinessActivity.class);
                // mContext.finish();
                if (!checkFieldForEmpty()) {
                    if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                        uihandle.showToast("Check your Internet Connectivity");
                    } else {
                        //    Todo: Fire SignUp api here
                        new FireApiAddUserInfo(mContext, f_name, l_name, image, mSession.getLoggedId(),
                                mEditTextDateOfBirth.getText().toString().trim(),
                                mEditTextPhoneNumber.getText().toString().trim(),
                                mEditTextCity.getText().toString().trim(),
                                mEditTextCountry.getText().toString().trim(),
                                /*  lat,lng  */
                                "", "", mSession.getToken()).execute(URLListApis.URL_ADD_USERINFO, "edit");

                                             /*
            params.put(URLListKeys.ADD_USER_INFO_USER_ID, url[1]);
            params.put(URLListKeys.ADD_USER_INFO_DOB, url[2]);
            params.put(URLListKeys.ADD_USER_INFO_PHONE, url[3]);
            params.put(URLListKeys.ADD_USER_INFO_CITY, url[4]);
            params.put(URLListKeys.ADD_USER_INFO_COUNTRY, url[5]);
            params.put(URLListKeys.ADD_USER_INFO_LAT, url[6]);
            params.put(URLListKeys.ADD_USER_INFO_LONG, url[7]);
              */
                    }
                }
            }
        });
        // find location fragment
          /*    typeFilter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();*/

        //  autocompleteFragment.setFilter(typeFilter);

        mSearchLocationFragment = (CustomizedPlaceFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mSearchLocationFragment.setAllowReturnTransitionOverlap(true);
        mSearchLocationFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                //   TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place);

                //   uihandle.showToast(place.getId() + "\n" + place.getName() + "\n" + place.getAddress());
                //    slected place lat long

                mBusinessName = place.getName().toString();
                business_number = String.valueOf(place.getPhoneNumber().toString());
                business_longitude = String.valueOf(place.getLatLng().longitude);
                business_latitude = String.valueOf(place.getLatLng().latitude);

                if (place.getWebsiteUri() != null) {
                    business_website = String.valueOf(place.getWebsiteUri());
                } else {
                    business_website = "";
                }

                StaticValuesEditProfile.businessid = place.getId();
                StaticValuesEditProfile.business_name = mBusinessName;
                StaticValuesEditProfile.number = business_number;
                StaticValuesEditProfile.longitude = business_longitude;
                StaticValuesEditProfile.latitude = business_latitude;
                StaticValuesEditProfile.website = business_website;


                getPlaceDetailName(place.getLatLng().latitude, place.getLatLng().longitude);

                // get full detail of that particular place
                mEditBBusinessName.setText(mBusinessName);
                mEditBBusinessContact.setText(business_number);
                mEditBWebAddress.setText(business_website);

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
                    business_country = addresses.get(0).getCountryName();
                }
                if (addresses.get(0).getAddressLine(0) != null) {
                    mAddress = addresses.get(0).getAddressLine(0);
                }
                if (addresses.get(0).getAdminArea() != null) {
                    business_state = addresses.get(0).getAdminArea();
                }
                if (addresses.get(0).getPostalCode() != null) {
                    business_pincode = addresses.get(0).getPostalCode();
                }

                if (addresses.get(0).getSubAdminArea() != null) {
                    business_city = addresses.get(0).getSubAdminArea();
                } else if (addresses.get(0).getLocality() != null)
                    business_city = addresses.get(0).getLocality();


                StaticValuesEditProfile.country = business_country;
                StaticValuesEditProfile.address = mAddress;
                StaticValuesEditProfile.state = business_state;
                StaticValuesEditProfile.pincode = business_pincode;
                StaticValuesEditProfile.city = business_city;


                mEditBState.setText(business_state);
                mEditBPinCode.setText(business_pincode);
                mEditBCountry.setText(business_country);
                mEditBAddress.setText(mAddress);
                uihandle.setCursorOnLast(mEditBAddress, mEditBAddress.length());

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyValuesToViews() {
        // Set Values to fields
        mEditTextDateOfBirth.setText(mSession.getLoggedDob());
        mEditTextCity.setText(mSession.getLoggedCity());
        uihandle.setCursorOnLast(mEditTextCity, mEditTextCity.getText().length());
        mEditTextCountry.setText(mSession.getLoggedCountry());
        mEditTextPhoneNumber.setText(mSession.getLoggedPhone());

        uihandle.setImageWithLazyLoading(mSession.getUserImageUrl(), mProfileImage);
        //   uihandle.loadImageWithGlide(mSession.getUserImageUrl(), mProfileImage);

        // set values to business fields

        // Apply values to views for display
        mEditBBusinessName.setText(StaticValuesEditProfile.business_name);
        mEditBBusinessCategory.setText(StaticValuesEditProfile.category);
        mEditBAddress.setText(StaticValuesEditProfile.address);
        mEditBCountry.setText(StaticValuesEditProfile.country);
        mEditBState.setText(StaticValuesEditProfile.state);
        mEditBPinCode.setText(StaticValuesEditProfile.pincode);
        mEditBBusinessContact.setText(StaticValuesEditProfile.number);
        mEditBWebAddress.setText(StaticValuesEditProfile.website);

        mEditBFacebookLink.setText(StaticValuesEditProfile.facebook);
        mEditBGoogleLink.setText(StaticValuesEditProfile.google);
        mEditBInstagramLink.setText(StaticValuesEditProfile.instagram);
        mEditBYoutube.setText(StaticValuesEditProfile.youtube);


    }

    private void initViews() {

        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        mPresenter = new PostNewAdPresenter();
       /* autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);*/
        mProfileImage = (CircleImageView) findViewById(R.id.img_profile_buisness);

        mEditTextDateOfBirth = (EditText) findViewById(R.id.edittext_basic_info_birth_date);
        mEditTextCity = (EditText) findViewById(R.id.edittext_basic_info_city);
        mEditTextCountry = (EditText) findViewById(R.id.edittext_basic_info_country);

        mEditTextPhoneNumber = (EditText) findViewById(R.id.edittext_basic_info_phone_number);

        mEditViewFirstName = (EditText) findViewById(R.id.textview_first_name);
        mEditViewLastName = (EditText) findViewById(R.id.textview_last_name);
        mTextViewFullName = (TextView) findViewById(R.id.textview_full_name);

        /*** Business profile* **/
        mEditBBusinessName = (EditText) findViewById(R.id.textview_business_value);
        mEditBBusinessCategory = (EditText) findViewById(R.id.textview_business_category_value);
        mEditBAddress = (EditText) findViewById(R.id.textview_street_address_value);
        mEditBCountry = (EditText) findViewById(R.id.textview_business_country_value);
        mEditBState = (EditText) findViewById(R.id.textview_state_value);
        mEditBPinCode = (EditText) findViewById(R.id.textview_pin_code_value);
        mEditBBusinessContact = (EditText) findViewById(R.id.textview_business_contact_value);
        mEditBWebAddress = (EditText) findViewById(R.id.textview_web_address_value);

        mEditBFacebookLink = (EditText) findViewById(R.id.textview_facebook_value);
        mEditBGoogleLink = (EditText) findViewById(R.id.textview_google_page_link_value);
        mEditBYoutube = (EditText) findViewById(R.id.textview_youtube_link_value);
        mEditBInstagramLink = (EditText) findViewById(R.id.textview_instagram_value);
               /* .....//.....   */

        mEditViewFirstName.setText(mSession.getLoggedFirstname());
        mEditViewLastName.setText(mSession.getLoggedLastname());
        mTextViewFullName.setText(mSession.getLoggedFirstname() + " " + mSession.getLoggedLastname());


        mBtnCancel = (Button) findViewById(R.id.btn_basic_info_cancel);
        mBtnDone = (Button) findViewById(R.id.btn_basic_info_done);
        mFloatingCameraImage = (FloatingActionButton) findViewById(R.id.floating_button_image_select);


    }

    public void goBackk(View v) {
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    private CircleImageView getImageView() {
        return mProfileImage;
    }

    private boolean checkFieldForEmpty() {
        boolean mFlag = false;

        dob = mEditTextDateOfBirth.getText().toString().trim();
        phone = mEditTextPhoneNumber.getText().toString().trim();
        city = mEditTextCity.getText().toString().trim();
        country = mEditTextCountry.getText().toString().trim();
        f_name = mEditViewFirstName.getText().toString().trim();
        l_name = mEditViewLastName.getText().toString().trim();


        StaticValuesEditProfile.business_name = mEditBBusinessName.getText().toString().trim();
        StaticValuesEditProfile.category = mEditBBusinessCategory.getText().toString().trim();

        StaticValuesEditProfile.number = mEditBBusinessContact.getText().toString().trim();
        StaticValuesEditProfile.website = mEditBWebAddress.getText().toString().trim();

        StaticValuesEditProfile.country = mEditBCountry.getText().toString().trim();
        StaticValuesEditProfile.address = mEditBAddress.getText().toString().trim();
        StaticValuesEditProfile.state = mEditBState.getText().toString().trim();
        StaticValuesEditProfile.pincode = mEditBPinCode.getText().toString().trim();

        StaticValuesEditProfile.facebook = mEditBFacebookLink.getText().toString().trim();
        StaticValuesEditProfile.google = mEditBGoogleLink.getText().toString().trim();
        StaticValuesEditProfile.instagram = mEditBInstagramLink.getText().toString().trim();
        StaticValuesEditProfile.youtube = mEditBYoutube.getText().toString().trim();

        if (TextUtils.isEmpty(f_name)) {
            uihandle.showToast("Field can't be empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(l_name)) {
            uihandle.showToast("Field can't be empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(dob)) {
            uihandle.showToast("Please fill Dob");
            mFlag = true;
        } else if (TextUtils.isEmpty(phone)) {
            uihandle.showToast("Please provide P.Number");
            mFlag = true;
        } else if (TextUtils.isEmpty(city)) {
            uihandle.showToast("Please Provide City");
            mFlag = true;
        } else if (TextUtils.isEmpty(country)) {
            uihandle.showToast("Please provide Country");

            /*** Removed the Auto Complete fragment from ui  ***/

        } /*else if (TextUtils.isEmpty(lat)) {
            uihandle.showToast("Location not selected");
            mFlag = true;
        } else if (TextUtils.isEmpty(lon)) {
            uihandle.showToast("Location not selected");
            mFlag = true;
        }*/

        return mFlag;
    }


    //choose image dialog from Gallary and Camera
    public void Image_Picker_Dialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Select Profile Picture");
        myAlertDialog.setMessage("Choose");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //call gallary intent
                galleryIntent();
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //calling camera intent
                cameraIntent();

            }
        });
        myAlertDialog.show();

    }

    // Choose image from gallery
    private void galleryIntent() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FILE);//one can be replaced with any action code

    }

    // click image from camera
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);//zero can be replaced with any action code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {

            onCaptureImageResult(data);
            //path = uihandle.getRealPathFromURI(data.getData());

            //  uihandle.showToast(path);

        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data);
            path = uihandle.getImagePath(data);
            image = path;
            // uihandle.showToast(path);
        }

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mProfileImage.setImageBitmap(bm);

    }

    // processing after select image from camera as well as gallery
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");

        String path = destination.getPath();
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        image = destination.getAbsolutePath();
        //  uihandle.showToast(image);

        mProfileImage.setImageBitmap(thumbnail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // uihandle.explicitIntentFromLeft(SignInActivity.class);

      /*  Intent in = new Intent(this, LoggedInUserDashboard.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);*/
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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
                        mEditBBusinessCategory.setText(text.toString().trim());
                        StaticValuesEditProfile.category = text.toString().trim();
                        //     uihandle.showToast(text.toString());
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

}
