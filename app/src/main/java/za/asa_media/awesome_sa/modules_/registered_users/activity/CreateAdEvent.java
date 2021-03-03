package za.asa_media.awesome_sa.modules_.registered_users.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import me.relex.circleindicator.CircleIndicator;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.PagerAdapterAdEventImages;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiCreateAd;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiFetchBusinessDetail;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class CreateAdEvent extends AppCompatActivity implements View.OnClickListener, IAdsImageCallback {


    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private final int REQUEST_CAMERA2 = 2, SELECT_FILE2 = 3;
    private final int REQUEST_CAMERA3 = 4, SELECT_FILE3 = 5;

    private Activity mContext = this;
    private ImageView mImageCross, mBatchImage;
    private EditText mEditTextAdName,
            mEditTextDescription, mEditTextStartDate, mEditTextEndDate, mEditTextLocationName, mEditTextEventStartTime,
            mEditTextEmail, mEditTextWebsite, mEditTextLandline, mEditTextMobile;
    private TextView mTextViewSubmit;
    // other helping object need to perform operation
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String adName, description, startDate, endDate, coupan, email, website, landline, mobile, locationName, eventStartTime;

    private ImageView mBatchSpecial, mBatchEvent, mBatchPromotion, mBatchNews, mBatchCatalogue;
    private String batchs[];

    // view pager setup
    private String image = "", image2 = "", image3 = "";
    private ViewPager mViewPager;
    private int images[] = {R.drawable.img_event, R.drawable.img_event, R.drawable.img_event};
    private PagerAdapterAdEventImages myCustomPagerAdapter;
    private ImageView mImageView;
    private CircleIndicator mCircleIndicator;

    private TextView mTextSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad_event);

        // callingn init views for initial setup
        initViews();
    }

    private void initViews() {

        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        mTextSkip = (TextView) findViewById(R.id.txt_skip);

        mImageCross = (ImageView) findViewById(R.id.img_header_cancel);

        mEditTextAdName = (EditText) findViewById(R.id.editText_special_name);
        mEditTextDescription = (EditText) findViewById(R.id.editText_first_name);
        mEditTextStartDate = (EditText) findViewById(R.id.editText_start_date);
        mEditTextEndDate = (EditText) findViewById(R.id.editText_end_date);

        mEditTextLocationName = (EditText) findViewById(R.id.editText_lcoation_name);
        mEditTextEventStartTime = (EditText) findViewById(R.id.editText_event_time);


        // header batchs
        mBatchSpecial = (ImageView) findViewById(R.id.batch_special);
        mBatchEvent = (ImageView) findViewById(R.id.batch_event);
        mBatchPromotion = (ImageView) findViewById(R.id.batch_promotion);
        mBatchNews = (ImageView) findViewById(R.id.batch_news);
        mBatchCatalogue = (ImageView) findViewById(R.id.batch_catalogue);


        mEditTextEmail = (EditText) findViewById(R.id.editText_email_id);
        mEditTextWebsite = (EditText) findViewById(R.id.editText_website);
        mEditTextLandline = (EditText) findViewById(R.id.editText_landline);
        mEditTextMobile = (EditText) findViewById(R.id.editText_mobile);

        mTextViewSubmit = (TextView) findViewById(R.id.txt_submit_special);
        mTextViewSubmit.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setOffscreenPageLimit(2);
        myCustomPagerAdapter = new PagerAdapterAdEventImages(mContext, new String[]{"", "", ""}, images, 1);
        mViewPager.setAdapter(myCustomPagerAdapter);
        mCircleIndicator.setViewPager(mViewPager);
        // highlights to batchs

        try {
            // uncheck all batches before highlights batchs
            clickableBatchs();
            batchs = InitialValueSetUp.mBatchs;
            int lengthh = batchs.length;
            if (lengthh == 1) {
                mTextSkip.setVisibility(View.INVISIBLE);
            } else {
                mTextViewSubmit.setText("Next");
            }
            if (batchs != null) {
                for (String batch : batchs) {
                    Log.d("batch", "" + batch);
                    if (batch.equals("1")) {
                        mBatchCatalogue.setEnabled(true);
                        mBatchCatalogue.setImageResource(R.drawable.catalog);

                    }
                    if (batch.equals("2")) {
                        mBatchEvent.setEnabled(true);
                        mBatchEvent.setImageResource(R.drawable.events);

                    }

                    if (batch.equals("3")) {
                        mBatchPromotion.setEnabled(true);
                        mBatchPromotion.setImageResource(R.drawable.promotions);

                        // mImagePromotions.setVisibility(View.VISIBLE);
                    }

                    if (batch.equals("4")) {
                        //mImageSpecial.setVisibility(View.VISIBLE);
                        mBatchSpecial.setEnabled(true);
                        mBatchSpecial.setImageResource(R.drawable.specials);


                    }
                    if (batch.equals("5")) {
                        // mImageNews.setVisibility(View.VISIBLE);
                        mBatchNews.setEnabled(true);
                        mBatchNews.setImageResource(R.drawable.news);

                    }
                }
                // }
            } else {
                CreateAdEvent.this.finish();
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }

        } catch (Exception ex) {

        }


        mImageCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* CreateAdEvent.this.finish();
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);*/

                mSession.setAdsDirectTag(true);
                Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                mContext.finish();
            }
        });

        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.hideSoftKeyboard();
                uihandle.getCalendarDialogDate(mEditTextStartDate);

            }
        });
        mEditTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.hideSoftKeyboard();
                uihandle.getCalendarDialogDate(mEditTextEndDate);

            }
        });


        mEditTextEventStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                uihandle.hideSoftKeyboard();
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateAdEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mEditTextEventStartTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        // calling contact info
        mEditTextLandline.setText("" + mSession.getLoggedPhone());
        mEditTextEmail.setText("" + mSession.getLoggedEmail());
        getContactInfo();
        // apply listener to batch
        applyListenerToBatchs();

    }

    private void applyListenerToBatchs() {

        mBatchCatalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CreateAdCatalogue.class);
            }
        });

        mBatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CreateAdEvent.class);
            }
        });

        mBatchPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CreateAdPromotions.class);
            }
        });

        mBatchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CreateAdNews.class);
            }
        });
        mBatchSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CreateAdSpecials.class);
            }
        });

        mTextSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipCallback();
            }
        });

    }

    private void skipCallback() {
        InitialValueSetUp.mBatchs = ArrayUtils.removeElement(batchs, "2");
        if (!Arrays.asList(InitialValueSetUp.mBatchs).isEmpty()) {
            for (String batch : InitialValueSetUp.mBatchs) {
                Log.d("batch", "" + batch);
                if (batch.equals("1")) {
                    uihandle.goForNextBatch(CreateAdCatalogue.class);
                    return;

                } else if (batch.equals("2")) {
                    // mBatchEvent.setEnabled(true);
                    //mBatchEvent.setImageResource(R.drawable.events);
                    uihandle.goForNextBatch(CreateAdEvent.class);
                    return;

                } else if (batch.equals("3")) {
                    // mBatchPromotion.setEnabled(true);
                    // mBatchPromotion.setImageResource(R.drawable.promotions);
                    uihandle.goForNextBatch(CreateAdPromotions.class);
                    return;
                    // mImagePromotions.setVisibility(View.VISIBLE);
                } else if (batch.equals("4")) {
                    //mImageSpecial.setVisibility(View.VISIBLE);
                    //mBatchSpecial.setEnabled(true);
                    //mBatchSpecial.setImageResource(R.drawable.specials);
                    uihandle.goForNextBatch(CreateAdSpecials.class);
                    return;

                } else if (batch.equals("5")) {
                    // mImageNews.setVisibility(View.VISIBLE);
                    //    mBatchNews.setEnabled(true);
                    //    mBatchNews.setImageResource(R.drawable.news);
                    uihandle.goForNextBatch(CreateAdNews.class);
                    return;
                }
            }
        } else {
            CreateAdEvent.this.finish();
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        }

    }

    public void clickableBatchs() {
        mBatchCatalogue.setEnabled(false);
        mBatchEvent.setEnabled(false);
        mBatchNews.setEnabled(false);
        mBatchPromotion.setEnabled(false);
        mBatchSpecial.setEnabled(false);

    }


    @Override
    public void onClick(View v) {

        adName = mEditTextAdName.getText().toString().trim();
        description = mEditTextDescription.getText().toString().trim();
        startDate = mEditTextStartDate.getText().toString().trim();
        endDate = mEditTextEndDate.getText().toString().trim();

        locationName = mEditTextLocationName.getText().toString().trim();
        eventStartTime = mEditTextEventStartTime.getText().toString().trim();

        email = mEditTextEmail.getText().toString().trim();
        website = mEditTextWebsite.getText().toString().trim();
        landline = mEditTextLandline.getText().toString().trim();
        mobile = mEditTextMobile.getText().toString().trim();

        if (!TextUtils.isEmpty(adName) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(startDate) &&
                !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(locationName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(eventStartTime)
                && !TextUtils.isEmpty(landline) && !TextUtils.isEmpty(mobile)) {
            // go hit the api
            goForCreateAd();


        } else {
            uihandle.showToast("Please Fill all Fields");
        }
    }


    public void getContactInfo() {
        new FireApiFetchBusinessDetail(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("home", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {
                                    JSONArray jArray = response.optJSONArray("data");
                                    if (jArray != null) {
                                        JSONObject jObj = jArray.optJSONObject(0);

                                        mEditTextMobile.setText("" + jObj.optString("number"));
                                        mEditTextWebsite.setText("" + jObj.optString("website"));


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
        }.execute(URLListApis.URL_BUSINESS_DETAIL);


    }


    private void goForCreateAd() {
        mSession.setCAdBatchId("2");
        new FireApiCreateAd(mContext, image, image2, image3) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);

                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                uihandle.showToast(response.optString(("message")));

                                skipCallback();


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
        }.execute(URLListApis.URL_ADS_CREATED, adName, description, startDate, endDate, "N/A", email, website, landline, mobile, locationName, eventStartTime);

    }


    //choose image dialog from Gallary and Camera
    public void Image_Picker_Dialog(final int cam, final int file) {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Select Profile Picture");
        myAlertDialog.setMessage("Choose");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //call gallary intent
                galleryIntent(file);
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //calling camera intent
                cameraIntent(cam);

            }
        });
        myAlertDialog.show();

    }

    // Choose image from gallery
    private void galleryIntent(int file) {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, file);//one can be replaced with any action code

    }

    // click image from camera
    private void cameraIntent(int cam) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, cam);//zero can be replaced with any action code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            onCaptureImageResult(data, "1", mImageView);
            //path = uihandle.getRealPathFromURI(data.getData());

            //  uihandle.showToast(path);
        } else if (requestCode == REQUEST_CAMERA2 && resultCode == RESULT_OK) {
            onCaptureImageResult(data, "2", mImageView);
            //path = uihandle.getRealPathFromURI(data.getData());

            //  uihandle.showToast(path);
        } else if (requestCode == REQUEST_CAMERA3 && resultCode == RESULT_OK) {
            onCaptureImageResult(data, "3", mImageView);
            //path = uihandle.getRealPathFromURI(data.getData());
            //  uihandle.showToast(path);

        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data, mImageView);
            image = uihandle.getImagePath(data);
            // uihandle.showToast(path);
        } else if (requestCode == SELECT_FILE2 && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data, mImageView);
            image2 = uihandle.getImagePath(data);
            // uihandle.showToast(path);
        } else if (requestCode == SELECT_FILE3 && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data, mImageView);
            image3 = uihandle.getImagePath(data);
            // uihandle.showToast(path);
        }
    }

    private void onSelectFromGalleryResult(Intent data, ImageView mImageView1) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mImageView1.setImageBitmap(bm);

    }

    // processing after select image from camera as well as gallery
    private void onCaptureImageResult(Intent data, String mImg, ImageView mImageView) {

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

        if (mImg.equals("1")) {
            image = destination.getAbsolutePath();
        } else if (mImg.equals("2")) {
            image2 = destination.getAbsolutePath();
        }
        if (mImg.equals("3")) {
            image3 = destination.getAbsolutePath();
        }
        //  image = destination.getAbsolutePath();
        //  uihandle.showToast(image);

        mImageView.setImageBitmap(thumbnail);
    }

    @Override
    public void getImagePosition(int pos, ImageView mImageView) {
        this.mImageView = mImageView;
        if (pos == 1) {
            if (uihandle.hasCamera()) {
                Image_Picker_Dialog(REQUEST_CAMERA, SELECT_FILE);
            } else {
                uihandle.showToast("Sorry you have no Camera available!");
            }
        } else if (pos == 2) {
            if (uihandle.hasCamera()) {
                Image_Picker_Dialog(REQUEST_CAMERA2, SELECT_FILE2);
            } else {
                uihandle.showToast("Sorry you have no Camera available!");
            }

        } else if (pos == 3) {

            if (uihandle.hasCamera()) {
                Image_Picker_Dialog(REQUEST_CAMERA3, SELECT_FILE3);
            } else {
                uihandle.showToast("Sorry you have no Camera available!");
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
