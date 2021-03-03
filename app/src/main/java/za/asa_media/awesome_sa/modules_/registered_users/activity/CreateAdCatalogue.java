package za.asa_media.awesome_sa.modules_.registered_users.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiCreateAd;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiFetchBusinessDetail;
import za.asa_media.awesome_sa.modules_.registered_users.utility.FilePath;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class CreateAdCatalogue extends AppCompatActivity implements View.OnClickListener {

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String image = "";
    private Activity mContext = this;
    private ImageView mImageCross, mBatchImage;
    private EditText
            //mEditTextAdName,
            mEditTextDescription,
    // mEditTextStartDate,
    //  mEditTextEndDate,
    //  mEditTextCoupan,
    mEditTextEmail, mEditTextWebsite, mEditTextLandline, mEditTextMobile;
    private TextView mTextViewSubmit;
    // other helping object need to perform operation
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private String description,/* startDate, adName,endDate, coupan */
            email, website, landline, mobile;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    //Uri to store the image uri
    private Uri filePath;


    private ImageView mBatchSpecial, mBatchEvent, mBatchPromotion, mBatchNews, mBatchCatalogue;
    private String batchs[];
    private TextView mTextSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad_catalog);
        // callingn init views for initial setup
        initViews();
    }

    private void initViews() {

        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        mImageCross = (ImageView) findViewById(R.id.img_header_cancel);
        mBatchImage = (ImageView) findViewById(R.id.image_batch_layout);

        //   mEditTextAdName = (EditText) findViewById(R.id.editText_special_name);
        mEditTextDescription = (EditText) findViewById(R.id.editText_first_name);
        // mEditTextStartDate = (EditText) findViewById(R.id.editText_start_date);
        //  mEditTextEndDate = (EditText) findViewById(R.id.editText_end_date);
        //  mEditTextCoupan = (EditText) findViewById(R.id.editText_voucher_code);
        mEditTextEmail = (EditText) findViewById(R.id.editText_email_id);
        mEditTextWebsite = (EditText) findViewById(R.id.editText_website);
        mEditTextLandline = (EditText) findViewById(R.id.editText_landline);
        mEditTextMobile = (EditText) findViewById(R.id.editText_mobile);

        // Header batchs
        mBatchSpecial = (ImageView) findViewById(R.id.batch_special);
        mBatchEvent = (ImageView) findViewById(R.id.batch_event);
        mBatchPromotion = (ImageView) findViewById(R.id.batch_promotion);
        mBatchNews = (ImageView) findViewById(R.id.batch_news);
        mBatchCatalogue = (ImageView) findViewById(R.id.batch_catalogue);

        mTextViewSubmit = (TextView) findViewById(R.id.txt_submit_special);
        mTextSkip = (TextView) findViewById(R.id.txt_skip);

        mTextViewSubmit.setOnClickListener(this);

        // power up batchs
        setBatchHighlight();

        mImageCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  CreateAdCatalogue.this.finish();
                //  overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                mSession.setAdsDirectTag(true);
                Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                mContext.finish();
            }
        });
        mBatchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        // calling contact info for the catalogue
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
        InitialValueSetUp.mBatchs = ArrayUtils.removeElement(batchs, "1");
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
            CreateAdCatalogue.this.finish();
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        }
    }

    public void setBatchHighlight() {
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

            /***** highlights batch according to plan ****/

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
                CreateAdCatalogue.this.finish();
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

            }
        } catch (Exception ex) {

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

        // adName = mEditTextAdName.getText().toString().trim();
        description = mEditTextDescription.getText().toString().trim();
        //  startDate = mEditTextStartDate.getText().toString().trim();
        //  endDate = mEditTextEndDate.getText().toString().trim();
        //  coupan = mEditTextCoupan.getText().toString().trim();

        email = mEditTextEmail.getText().toString().trim();
        website = mEditTextWebsite.getText().toString().trim();
        landline = mEditTextLandline.getText().toString().trim();
        mobile = mEditTextMobile.getText().toString().trim();

        if (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(email)
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
                                        mEditTextLandline.setText("" + mSession.getLoggedPhone());
                                        mEditTextEmail.setText("" + mSession.getLoggedEmail());

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
        mSession.setCAdBatchId("1");
        new FireApiCreateAd(mContext, image, "", "") {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                uihandle.showToast(response.optString(("message")));

                                // setting after fill ads successfully
                                //mBatchCatalogue.setEnabled(false);
                                //  mBatchCatalogue.setImageResource(R.drawable.grey_catalog);

                                //setBatchHighlight();
                                skipCallback();
                                //   CreateAdCatalogue.this.finish();
                                //  overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

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
        }.execute(URLListApis.URL_ADS_CREATED, "N/A", description, "N/A", "N/A", "N/A", email, website, landline, mobile, "N/A", "N/A");

    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("pdf/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            String path = FilePath.getPath(this, filePath);
            //    image = uihandle.getImagePath(data);             //    uihandle.getVideoPath(mData);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                image = data.getData().getPath();
            } else {
             //     mImageVideo = uihandle.getVideoPath(mData);    //    mData.getData().getPath();
                image = path;
            }

            //  image = path;
            //   uihandle.showToast(image+"\npath:--> " + path);
            generateImageFromPdf(filePath);
        }
    }

    void generateImageFromPdf(Uri pdfUri) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);

            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);

            mBatchImage.setImageBitmap(bmp);

            saveImage(bmp);

            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch (Exception e) {
            //todo with exception
        }
    }

    private void saveImage(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if (!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "PDF.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
/*
     // choose image dialog from Gallary and Camera
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
            image = uihandle.getImagePath(data);
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

        mBatchImage.setImageBitmap(bm);

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

        mBatchImage.setImageBitmap(thumbnail);
    }*/
}
