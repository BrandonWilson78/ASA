package za.asa_media.awesome_sa.modules_.registered_users.activity;

import android.app.Activity;
import android.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.relex.circleindicator.CircleIndicator;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.adapter.PagerAdapterAdPromotionImages;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiCreateAd;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetAdInformation;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class EditAdPromotion extends AppCompatActivity implements View.OnClickListener, IAdsImageCallback {

    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private final int REQUEST_CAMERA2 = 2, SELECT_FILE2 = 3;
    private final int REQUEST_CAMERA3 = 4, SELECT_FILE3 = 5;
    private Activity mContext = this;
    private ImageView mImageCross, mBatchImage;
    private EditText mEditTextAdName,
            mEditTextDescription, mEditTextStartDate, mEditTextEndDate, mEditTextCoupan,
            mEditTextEmail, mEditTextWebsite, mEditTextLandline, mEditTextMobile;
    private TextView mTextViewSubmit;
    // other helping object need to perform operation
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String adName, description, startDate, endDate, coupan, email, website, landline, mobile;
    private String image_path;
    // view pager setup
    private String image = "", image2 = "", image3 = "";
    private ViewPager mViewPager;
    private int images[] = {R.drawable.img_promotion, R.drawable.img_promotion, R.drawable.img_promotion};
    private PagerAdapterAdPromotionImages myCustomPagerAdapter;
    private ImageView mImageView;
    private CircleIndicator mCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ad_promotion);
        // callingn init views for initial setup
        initViews();

    }

    private void initViews() {

        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        mImageCross = (ImageView) findViewById(R.id.img_header_cancel);
        //   mBatchImage = (ImageView) findViewById(R.id.image_batch_layout);
        mEditTextAdName = (EditText) findViewById(R.id.editText_special_name);
        mEditTextDescription = (EditText) findViewById(R.id.editText_first_name);
        mEditTextStartDate = (EditText) findViewById(R.id.editText_start_date);
        mEditTextEndDate = (EditText) findViewById(R.id.editText_end_date);
        mEditTextCoupan = (EditText) findViewById(R.id.editText_voucher_code);
        mEditTextEmail = (EditText) findViewById(R.id.editText_email_id);
        mEditTextWebsite = (EditText) findViewById(R.id.editText_website);
        mEditTextLandline = (EditText) findViewById(R.id.editText_landline);
        mEditTextMobile = (EditText) findViewById(R.id.editText_mobile);

        mTextViewSubmit = (TextView) findViewById(R.id.txt_submit_special);
        mTextViewSubmit.setOnClickListener(this);


        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setOffscreenPageLimit(2);


        mImageCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAdPromotion.this.finish();
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
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

      /*  mBatchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image_Picker_Dialog();
            }
        });*/

        // calling contact info api for the place ad info detail
        getPromotionAdInfo();
    }

    @Override
    public void onClick(View v) {


        adName = mEditTextAdName.getText().toString().trim();
        description = mEditTextDescription.getText().toString().trim();
        startDate = mEditTextStartDate.getText().toString().trim();
        endDate = mEditTextEndDate.getText().toString().trim();
        coupan = mEditTextCoupan.getText().toString().trim();

        email = mEditTextEmail.getText().toString().trim();
        website = mEditTextWebsite.getText().toString().trim();
        landline = mEditTextLandline.getText().toString().trim();
        mobile = mEditTextMobile.getText().toString().trim();

        if (!TextUtils.isEmpty(adName) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(startDate) &&
                !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(coupan) && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(landline) && !TextUtils.isEmpty(mobile)) {
            // go hit the api
            goForUpdate();


        } else {
            uihandle.showToast("Please Fill all Fields");
        }
    }

    private void getPromotionAdInfo() {
        new FireApiGetAdInformation(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("login", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {
                                    JSONArray jArray = response.optJSONArray("data");
                                    if (jArray != null) {
                                        JSONObject jObj = jArray.optJSONObject(0);

                                        String name = UiHandleMethods.capitalizeString(jObj.optString("ad_name"));
                                        mEditTextAdName.setText("" + name);

                                        mEditTextDescription.setText(jObj.optString("description"));

                                        mEditTextStartDate.setText("" + jObj.optString("start_date"));

                                        mEditTextEndDate.setText("" + jObj.optString("end_date"));
                                        mEditTextCoupan.setText("" + jObj.optString("coupon"));
                                        mEditTextEmail.setText("" + jObj.optString("email"));
                                        mEditTextWebsite.setText("" + jObj.optString("website"));

                                        mEditTextLandline.setText("" + jObj.optString("landline"));
                                        mEditTextMobile.setText("" + jObj.optString("mobile"));

                                        image_path = jObj.optString("ad_image");
                                        String image_path2 = jObj.optString("ad_image1");
                                        String image_path3 = jObj.optString("ad_image2");
                                        String im[] = {image_path, image_path2, image_path3};
                                        myCustomPagerAdapter = new PagerAdapterAdPromotionImages(mContext, im ,images, 2);
                                        mViewPager.setAdapter(myCustomPagerAdapter);
                                        mCircleIndicator.setViewPager(mViewPager);

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
        }
                .execute(URLListApis.URL_GET_AD_INFO_FOR_LOGGED_USER);
    }

    private void goForUpdate() {
        new FireApiCreateAd(mContext, image.trim(), image2.trim(), image3.trim()) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);

                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                uihandle.showToast(response.optString(("message")));
                                EditAdPromotion.this.finish();
                                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

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
        }.execute(URLListApis.URL_EDIT_ADS, adName, description, startDate, endDate, coupan, email, website, landline, mobile, "N/A", "N/A");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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

    @Override
    public void getImagePosition(int pos, ImageView mImage) {
        this.mImageView = mImage;
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

    private void onSelectFromGalleryResult(Intent data, ImageView mImageView) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mImageView.setImageBitmap(bm);

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


}
