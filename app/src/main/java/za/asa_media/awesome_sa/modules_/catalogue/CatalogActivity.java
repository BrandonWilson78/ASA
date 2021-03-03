package za.asa_media.awesome_sa.modules_.catalogue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.FireApi.FireApiGetAdsInfoWithBatchId;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.SelectedPlaceListdata;
import za.asa_media.awesome_sa.modules_.event.EventsActivity;
import za.asa_media.awesome_sa.modules_.news.NewsActivity;
import za.asa_media.awesome_sa.modules_.promotion.PromotionActivity;
import za.asa_media.awesome_sa.modules_.special.SpecialActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class CatalogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CatalogActivity.class.getName();

    private Activity mContext = this;
    private TextView
            mTextViewSpecialPlaceName, mTextViewSpecialDistance,
            mTextViewSpecialAddress, mTextViewSpecialStatus, mTextViewSpecialDecription,
    //  mTextViewSpecialName,
    //  mTextViewStartDate, mTextViewSpecialEndDate,
    //  mTextViewSpecialEventLocation, mTextViewEventTime,


    mTextViewSpecialEmailUrl,
            mTextViewSpecialWebsiteUrl,
            mTextViewSpecialLandline,
            mTextViewSpecialMobile;


    private ImageView mImageViewSpecialImage;
    private View mViewSpecialStatusView;
    private RatingBar mSpecialRatingBar;
    private ImageView mBatchSpecial, mBatchEvent, mBatchPromotion, mBatchNews, mBatchCatalogue;
    private ImageView mImageViewSpecialCancel, mImageViewFooterBack, mImageViewFooterForword;

    // Other helping objects
    private UiHandleMethods uihandle;
    private SelectedPlaceListdata mPlaceDateObject;
    private String pdf_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cataloge);
        // calling init views for initialsetup
        initViews();

    }

    private void initViews() {

        //     views initialisations
        uihandle = new UiHandleMethods(mContext);
        mImageViewSpecialCancel = (ImageView) findViewById(R.id.img_header_cancel);
        mImageViewFooterBack = (ImageView) findViewById(R.id.img_footer_back);
        mImageViewFooterForword = (ImageView) findViewById(R.id.img_footer_forword);

        // views
        mTextViewSpecialPlaceName = (TextView) findViewById(R.id.txt_nearby_place_name);
        mTextViewSpecialAddress = (TextView) findViewById(R.id.txt_nearby_address);
        mTextViewSpecialDistance = (TextView) findViewById(R.id.txt_nearby_distance);
        mTextViewSpecialStatus = (TextView) findViewById(R.id.txt_nearby_status);
        mViewSpecialStatusView = findViewById(R.id.status_view);
        mImageViewSpecialImage = (ImageView) findViewById(R.id.image_batch_layout);
        mSpecialRatingBar = (RatingBar) findViewById(R.id.ratingBar_nearby);

        // header batchs
        mBatchSpecial = (ImageView) findViewById(R.id.batch_special);
        mBatchEvent = (ImageView) findViewById(R.id.batch_event);
        mBatchPromotion = (ImageView) findViewById(R.id.batch_promotion);
        mBatchNews = (ImageView) findViewById(R.id.batch_news);
        mBatchCatalogue = (ImageView) findViewById(R.id.batch_catalogue);

        mTextViewSpecialDecription = (TextView) findViewById(R.id.txt_description);
        mTextViewSpecialEmailUrl = (TextView) findViewById(R.id.txt_email_);
        mTextViewSpecialWebsiteUrl = (TextView) findViewById(R.id.txt_website_link);
        mTextViewSpecialLandline = (TextView) findViewById(R.id.txt_landline_);
        mTextViewSpecialMobile = (TextView) findViewById(R.id.txt_mobile_);


        // check intent for values
        if (getIntent() != null && getIntent().hasExtra("objPlace")) {
            mPlaceDateObject = getIntent().getParcelableExtra("objPlace");
            // setting values to views
            mTextViewSpecialPlaceName.setText("" + mPlaceDateObject.getPs_name());

            mSpecialRatingBar.setRating((float) mPlaceDateObject.getPs_rating());

            if (mPlaceDateObject.isPs_opening_status()) {
                mTextViewSpecialStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                mTextViewSpecialStatus.setText("OPEN");
                mViewSpecialStatusView.setBackground(mContext.getResources().getDrawable(R.drawable.green_circle));
            } else {
                mTextViewSpecialStatus.setText("CLOSED");
            }

            mTextViewSpecialDistance.setText("" + getIntent().getStringExtra("distance"));
            mTextViewSpecialAddress.setText("" + mPlaceDateObject.getPs_formatted_address());

        } else/* if (getIntent() != null && getIntent().hasExtra("address"))*/ {
         /*   bObj = (BeanPlaceDetail) getIntent().getSerializableExtra("objPlaceAct");*/
            // setting values to views

            try {
                // uncheck all batches before highlights batchs
                InitialValueSetUp.adBatchId = "1";
                clickableBatchs();
                String batchs[] = InitialValueSetUp.mBatchs;
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
                }
            } catch (Exception ex) {

            }


            mTextViewSpecialPlaceName.setText("" + InitialValueSetUp.mPlaceName);
            mSpecialRatingBar.setRating(InitialValueSetUp.mRating);

            if (InitialValueSetUp.statusOpening.equals("OPEN")) {
                mTextViewSpecialStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                mTextViewSpecialStatus.setText("OPEN");
                mViewSpecialStatusView.setBackground(mContext.getResources().getDrawable(R.drawable.green_circle));
            } else {
                mTextViewSpecialStatus.setText("CLOSED");
            }

            mTextViewSpecialDistance.setText("" + InitialValueSetUp.mDistancePlace);
            mTextViewSpecialAddress.setText("" + InitialValueSetUp.mPlaceAddress);
            Log.e("Inside", "has extra");

        }


        // called apply listeners method
        applyListenersToViews();


        // download pdf and show to imageviw
        getCatalogeInfo();

        // apply listener to batch
        applyListenerToBatchs();

    }

    private void applyListenerToBatchs() {

        mBatchCatalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(CatalogActivity.class);
            }
        });

        mBatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(EventsActivity.class);
            }
        });

        mBatchPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(PromotionActivity.class);
            }
        });

        mBatchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(NewsActivity.class);
            }
        });
        mBatchSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.goForNextBatch(SpecialActivity.class);
            }
        });


    }


    private void applyListenersToViews() {

        mImageViewFooterForword.setVisibility(View.INVISIBLE);
        mImageViewSpecialCancel.setOnClickListener(this);
        mImageViewFooterBack.setOnClickListener(this);
        mImageViewFooterForword.setOnClickListener(this);


        mImageViewSpecialImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPdfInUrl();
            }
        });

        mTextViewSpecialEmailUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.sendEmail(mTextViewSpecialEmailUrl.getText().toString().trim());
            }
        });

        mTextViewSpecialLandline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.callDialog(mTextViewSpecialLandline.getText().toString().trim(), mTextViewSpecialLandline.getText().toString().trim());

            }
        });
        mTextViewSpecialMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.callDialog(mTextViewSpecialMobile.getText().toString().trim(), mTextViewSpecialMobile.getText().toString().trim());

            }
        });
        mTextViewSpecialWebsiteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uihandle.openWebLink(mTextViewSpecialWebsiteUrl.getText().toString().trim());
            }
        });


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

        switch (v.getId()) {
            case R.id.img_header_cancel:
                this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;

            case R.id.img_footer_back:
                // TODO :Some stuff if want different.... not in sequence
                startActivity(new Intent(mContext, NewsActivity.class));
                this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.img_footer_forword:
                // mUiHandleMethods.explicitIntent(NewsActivity.class);
                break;

            default:
                break;

        }

    }

    private void getCatalogeInfo() {
        new FireApiGetAdsInfoWithBatchId(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                if (response.has("data")) {
                                    JSONArray jArray = response.optJSONArray("data");
                                    if (jArray != null) {
                                        JSONObject jObj = jArray.optJSONObject(0);

                                        //  String name = uihandle.capitalizeString(jObj.optString("ad_name"));
                                        //    mTextViewSpecialName.setText("Event Name: " + name);
                                        ///    mTextViewSpecialDecription.setText(jObj.optString("description"));
                                        ///    mTextViewStartDate.setText("" + jObj.optString("start_date"));
                                        //     mTextViewSpecialEndDate.setText("" + jObj.optString("end_date"));

                                        pdf_url = jObj.optString("ad_image");

                                        //   mTextViewSpecialDecription.setText("" + jObj.optString("description"));
                                        mTextViewSpecialDecription.setText(Html.fromHtml("<b><font color=\"#000000\">" + "Description: " + "</font></b>" + "<font color=\"#6b6969\">" + jObj.optString("description") + "</font>"));

                                        mTextViewSpecialEmailUrl.setText("" + jObj.optString("email"));
                                        mTextViewSpecialWebsiteUrl.setText("" + jObj.optString("website"));
                                        mTextViewSpecialLandline.setText("" + jObj.optString("landline"));
                                        mTextViewSpecialMobile.setText("" + jObj.optString("mobile"));

                                        //     mTextViewSpecialEventLocation.setText("" + jObj.optString("EventLocation"));
                                        //    mTextViewEventTime.setText("" + jObj.optString("EventTime"));

                                    }
                                    //     Calling download pdf for imageview and to checkdetail
                                    downloadPdfAndConvertToBmp(pdf_url);

                                } else {
                                    uihandle.showToast("Something went wrong!");
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
        }.execute(URLListApis.URL_GET_INFO_WITH_BATCH_ID);
    }


   /* */

    /***** Downloading pdf from server *****//*
    public void downloadAndOpenPDF() {

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "pdf");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File(folder, "Read.pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        DownloadFile("http://112.196.5.114/asa/dist/img/ads/3328Wirsing-SendingAndReceivingDataViaBluetoothWithAnAndroidDevice.pdf", file);
        // showPdf();

    }
     */
    public void downloadPdfAndConvertToBmp(String mPdf_link) {

        new DownloadFile().execute(mPdf_link, "Read.pdf");

        //   new DownloadVideoFileFromServer().execute(pdf_url, "Read.pdf");
        //     downloadPdfContent(pdf_url);
    }

    public void downloadPdfContent(String urlToDownload) {
        try {

            String fileName = "xyz";
            String fileExtension = ".pdf";

//           download pdf file.
            URL url = new URL(urlToDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/mydownload/";
            File file = new File(PATH);
            if (!file.exists()) {
                file.mkdirs();
            }

            File outputFile = new File(file, fileName + fileExtension);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            System.out.println("--pdf downloaded--ok--" + urlToDownload);
        } catch (Exception e) {
            e.printStackTrace();

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

            mImageViewSpecialImage.setImageBitmap(bmp);

            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void viewPdfInUrl() {
      /*  File pdfFile = new File(Environment.getExternalStorageDirectory() + "/awesomeAfrica/" + "Read.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {

            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            uihandle.showToast("No Application available to view PDF");
        }*/
        WebView webView = new WebView(mContext);
        String url = "http://docs.google.com/gview?embedded=true&url=" + pdf_url;
        Log.i(TAG, "Opening PDF: " + url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(url);
    //    StaticPdf.mPef_remote_url = pdf_url;
    //    uihandle.explicitIntent(PdfViewScreen.class);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    private class DownloadFile extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "awesomeAfrica");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File pdfFile = new File(folder, fileName);
            try {
                //
                if (!pdfFile.exists()) {
                    //  pdfFile.mkdirs();
                    pdfFile.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return FileDownloader.downloadFile(fileUrl, pdfFile);
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            //   showPdf();
            if (aVoid) {
                File file = new File(Environment.getExternalStorageDirectory() + "/awesomeAfrica/Read.pdf");
                Uri pdfUri = Uri.fromFile(file);
                generateImageFromPdf(pdfUri);
            } else {
                Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }


        }
    }

    /* public void showPdf() {
         File file = new File(Environment.getExternalStorageDirectory() + "/awesomeAfrica/Read.pdf");
         PackageManager packageManager = getPackageManager();
         Intent testIntent = new Intent(Intent.ACTION_VIEW);
         testIntent.setType("application/pdf");
         List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
         Intent intent = new Intent();
         intent.setAction(Intent.ACTION_VIEW);
         Uri uri = Uri.fromFile(file);
         intent.setDataAndType(uri, "application/pdf");
         startActivity(intent);
     }
 */
    // download video from server
    private class DownloadVideoFileFromServer extends AsyncTask<String, Integer, String> {
        private ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (pdialog == null) {
                pdialog = ProgressDialog.show(mContext, "Pdf is being downloaded", "Please Wait", false, false);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
                if (s != null) {
                    uihandle.showToast("Video successfully downloaded! Check your Download Folder");
                } else {
                    uihandle.showToast("Video downloaing failed!");
                }
            } catch (Exception e) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return mp4load(params[0], params[1]);
            } catch (Exception e) {

            }

            return null;
        }

        // method perform downloading video operation
        private String mp4load(String urling, String videoName) {
            try {
                URL url = new URL(urling);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                //c.setDoOutput(true);
                con.connect();

                String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                String fileName = videoName;
                File outputFile = new File(downloadsPath, fileName);

                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = con.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();

                //  in = downloadsPath + "/" + fileName;
                //   out = "/sdcard/" + fileName;
                //send broadcast to gallery to show new incomimng data
                //    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

                return "done";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }


}