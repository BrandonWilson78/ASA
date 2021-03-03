package za.asa_media.awesome_sa.modules_.registered_users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.FireApi.FireApiAddUserInfo;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class BasicInfo extends AppCompatActivity {

    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private UiHandleMethods uihandle;
    private CircleImageView mProfileImage;
    private EditText
            mEditTextName, mEditTextDateOfBirth,
            mEditTextCity, mEditTextCountry,
             mEditTextPhoneNumber;
    private Button mBtnCancel, mBtnDone;
    private FloatingActionButton mFloatingCameraImage;
    private Activity mContext = this;
    //  private PlaceAutocompleteFragment autocompleteFragment;
    private String lat, lon;
    private String dob, phone, city, country;
    // session object
    private SessionCityOculus mSession;
    private String image = "";
    private String path = "";

    private TextView mTextViewFirstName, mTextViewLastName, mTextViewFullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        // calling initviews
        initViews();
    }

    private void initViews() {
        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        //autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mProfileImage = (CircleImageView) findViewById(R.id.img_profile_buisness);
        //  mEditTextName = (EditText) findViewById(R.id.edittext_basic_info_name);
        mEditTextDateOfBirth = (EditText) findViewById(R.id.edittext_basic_info_birth_date);
        mEditTextCity = (EditText) findViewById(R.id.edittext_basic_info_city);
        mEditTextCountry = (EditText) findViewById(R.id.edittext_basic_info_country);

        mEditTextPhoneNumber = (EditText) findViewById(R.id.edittext_basic_info_phone_number);

        mTextViewFirstName = (TextView) findViewById(R.id.textview_first_name);
        mTextViewLastName = (TextView) findViewById(R.id.textview_last_name);
        mTextViewFullName = (TextView) findViewById(R.id.textview_full_name);


        mBtnCancel = (Button) findViewById(R.id.btn_basic_info_cancel);
        mBtnDone = (Button) findViewById(R.id.btn_basic_info_done);
        mFloatingCameraImage = (FloatingActionButton) findViewById(R.id.floating_button_image_select);

        // setting values to views from screen
        mTextViewFirstName.setText("" + mSession.getLoggedFirstname());
        mTextViewLastName.setText("" + mSession.getLoggedLastname());
        mTextViewFullName.setText("" + UiHandleMethods.capitalizeString(mSession.getLoggedFirstname()) + " " + mSession.getLoggedLastname());

        mEditTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.hideSoftKeyboard();
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

              /*  //  PickImageDialog.build(new PickSetup()).show((FragmentActivity) mContext);
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        if (r.getError() == null) {
                            //If you want the Uri.
                            //Mandatory to refresh image from Uri.
                            getImageView().setImageURI(null);
                            //Setting the real returned image.
                            //getImageView().setImageURI(r.getUri());

                            //Set bitmap image to the place holder
                            getImageView().setImageBitmap(r.getBitmap());
                            image = r.getUri().getEncodedPath();

                            //Image path
                            uihandle.showToast("" + image);

                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();
                            uihandle.showToast(r.getError().getMessage());
                        }
                    }
                }).show((FragmentActivity) mContext);*/
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicInfo.this.finish();
                mSession.setIsLogged(false);
            }
        });

        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //       uihandle.explicitIntent(StartAdvertiseBusinessActivity.class);
                //        mContext.finish();
                if (!checkFieldForEmpty()) {
                    if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                        uihandle.showToast("Check your Internet Connectivity");
                    } else {
                        // Todo: Fire add user info api here
                        new FireApiAddUserInfo(mContext,
                                mSession.getLoggedFirstname(), mSession.getLoggedLastname(),
                                image, mSession.getLoggedId(),
                                mEditTextDateOfBirth.getText().toString().trim(),
                                mEditTextPhoneNumber.getText().toString().trim(),
                                mEditTextCity.getText().toString().trim(),
                                mEditTextCountry.getText().toString().trim(),
                                // lat , lon
                                "", "", mSession.getToken()).execute(URLListApis.URL_ADD_USERINFO, "basic");

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
        //  autocompleteFragment.setAllowReturnTransitionOverlap(true);
        //  autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
           /* @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                // Log.i(TAG, "Place: " + place.getName());

                // uihandle.showToast(place.getId() + "\n" + place.getName() + "\n" + place.getAddress());
                // slected place lat long
         *//*       businessName = place.getName().toString();
                number = String.valueOf(place.getPhoneNumber().toString());
                address = place.getAddress().toString();
                String spl[] = address.split(",");
                country = spl[spl.length - 1];
                String pin_state[] = spl[3].split(" ");

                state = pin_state[1];
                pinCode = pin_state[0];

                city = spl[spl.length - 3];
                streetAddress = spl[0] + spl[1];*//*
                lat = String.valueOf(place.getLatLng().latitude);
                lon = String.valueOf(place.getLatLng().longitude);
                autocompleteFragment.setText(lat + " , " + lon);

                // country = place.getId();


                //   uihandle.showToast(state + "-" + pinCode);

                // goForUpdateInfoOnMap(sLat, sLon);


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                uihandle.showToast("please provide accurate detail");
            }
        });

*/
    }

    private boolean checkFieldForEmpty() {
        boolean mFlag = false;

        dob = mEditTextDateOfBirth.getText().toString().trim();
        phone = mEditTextPhoneNumber.getText().toString().trim();
        city = mEditTextCity.getText().toString().trim();
        country = mEditTextCountry.getText().toString().trim();

        if (TextUtils.isEmpty(dob)) {
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
        } /*else if (TextUtils.isEmpty(lat)) {
            uihandle.showToast("Location not selected");
            mFlag = true;
        } else if (TextUtils.isEmpty(lon)) {
            uihandle.showToast("Location not selected");
            mFlag = true;
        }*/ else if (TextUtils.isEmpty(image)) {
            uihandle.showToast("Image is not selected");
            mFlag = true;
        }

        return mFlag;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSession.setIsLogged(false);
        // uihandle.explicitIntentFromLeft(SignInActivity.class);
        this.finish();
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


}
