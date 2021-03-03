package za.asa_media.awesome_sa.support;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;
import za.asa_media.awesome_sa.support.lazyloading.ImageLoader;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-05 on 4/21/2017.
 */

public class UiHandleMethods {
    // calendar selection
    private Calendar myCalendar;
    private EditText mEditTextDate;
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            try {
                updateLabel();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    };
    private ProgressDialog mProgressDialog;
    private Activity mContext;

    /* public boolean isGooglePlayServicesAvailable() {
         int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
         if (ConnectionResult.SUCCESS == status) {
             return true;
         } else {
             GooglePlayServicesUtil.getErrorDialog(status, mContext, 0).show();
             return false;
         }
     }*/
    private SessionCityOculus mSession;

    public UiHandleMethods(Activity mContext) {
        this.mContext = mContext;
        mSession = new SessionCityOculus(mContext);
    }

    public static String capitalizeString(String mData) {
        if (mData == null || mData.trim().isEmpty()) {
            return mData;
        }
        char c[] = mData.trim().toLowerCase().toCharArray();
        c[0] = Character.toUpperCase(c[0]);

        return new String(c);

    }

    // get screen width
    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void onImageClickDialog(ImageView mImageView) {
        final Dialog d = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        d.setContentView(R.layout.dialog_full_screen_image_view);
        ImageView mImage = d.findViewById(R.id.image);
        ImageView mCross = d.findViewById(R.id.image_cross);
        mCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });
        mImage.setImageBitmap(getBitmapFromView(mImageView));

        d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        d.show();
    }

    public Bitmap getBitmapFromView(ImageView mView) {
        try {
            if (mView != null && mView.getDrawable() != null) {
                return ((BitmapDrawable) mView.getDrawable()).getBitmap();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return null;

    }

    public void onLocationChanged(final TextView mTextView) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mContext, Locale.getDefault());

        double latitude = Double.parseDouble(ReservedLocation.getSingletonInstance().getCurret_lat());
        double longitude = Double.parseDouble(ReservedLocation.getSingletonInstance().getCurrent_lng());


        Log.e("latitude", "latitude--" + latitude);
        try {
            Log.e("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {

                final String addFull = addresses.get(0).getSubAdminArea();
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                //  ro_gps_location.setText(state + " , " + city + " , " + country);
                //  ro_address.setText(address + " , " + knownName + " , " + postalCode);
                mSession.setUserCurrentLocation(addFull);
                InitialValueSetUp.locationName = addFull;

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // mTextView.setText(Html.fromHtml("<font color='#7F7F7F'>Your Location<br></font>" + addFull));
                        mTextView.setText(Html.fromHtml(addFull));
                    }
                });


                // showToast(state + " , " + city + " , " + country + "\n\n" + address + " , " + knownName + " , " + postalCode);
                Log.e("address: ", state + " , " + city + " , " + country + "\n\n" + address + " , " + knownName + " , " + postalCode + "\n" + addFull);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startProgress(String msg) {
        mProgressDialog = ProgressDialog.show(mContext, "Please wait", msg, false, false);
    }

    public void stopProgressDialog() {
        if (isShowingDialog()) {
            mProgressDialog.hide();
        }
    }

    public boolean isShowingDialog() {
        return mProgressDialog.isShowing();
    }

    // difes the soft input keypad
    public void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }

    }


    public void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setStatusBarColor(int color) {

        Window window = mContext.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }


    }

    public void loadWebImage(CircleImageView imgNetwork, String nameofImage) {

        final String URL = URLListApis.URL_COMMON_IMAGES + nameofImage;
        ImageLoader imageLoader = new ImageLoader(mContext);
        imageLoader.DisplayImage(URL, imgNetwork);

    }

    public void setImageWithLazyLoading(String path, CircleImageView imagePlace) {

        final String URL = URLListApis.URL_COMMON_IMAGES + path;
        ImageLoader imageLoader = new ImageLoader(mContext);
        imageLoader.DisplayImage(URL, imagePlace);

    }

    public void setImageWithPicasso(ImageView imageHolder, String path) {
        if (TextUtils.isEmpty(path)) {
            Picasso.with(mContext).load(R.drawable.catalog_banner)
                    .error(R.drawable.background_plain)
                    .placeholder(R.drawable.background_plain)
                    .into(imageHolder);
        } else {
            Picasso.with(mContext).load(path)
                    .error(R.drawable.background_plain)
                    .placeholder(R.drawable.background_plain)
                    .into(imageHolder);
        }
    }

    public void loadImageWithGlide(String imagePath, CircleImageView imgView) {
        Glide.with(mContext).load(URLListApis.URL_COMMON_IMAGES + imagePath)
                .crossFade()
                .placeholder(R.drawable.drawer_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView);

    }


    public void setUnderLine(TextView mTextView) {

        Paint paint = new Paint();
        paint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextView.setPaintFlags(paint.getFlags());


    }

    /******
     * load animations for slide up and slide down
     ******/


    public void openWebLink(String webLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webLink));
        // Always use string resources for UI text. This says something like "Share this photo with"
        String title = "Choose one";
        // Create and start the chooser
        Intent chooser = Intent.createChooser(intent, title);
        mContext.startActivity(chooser);
    }


    // slide down animation
    public Animation slideDown() {
        return AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
    }

    // slide up animation
    public Animation slideUp() {
        return AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
    }

    /******
     * check camera present in system or not
     ******/
    public boolean hasCamera() {
        return mContext.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT);
    }

    /******
     * get path of selected image
     ******/
    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        @SuppressWarnings("deprecation")
        Cursor cursor = mContext.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    // calendar setup method
    public void getCalendarDialogDate(EditText mEditTextDate) {
        this.mEditTextDate = mEditTextDate;

        // calendar intialisations
        myCalendar = Calendar.getInstance();

        new DatePickerDialog(mContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        // for desable previous date setting
        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        // datePickerDialog.show();
    }

    // update calendar with format
    private void updateLabel() throws ParseException {

        String myFormat = "yyyy-MM-dd"; // dd/MM/yyyyIn which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditTextDate.setText(sdf.format(myCalendar.getTime()));
    }

    // filter numbers only from symbol mixed String
    public String filterNumberFromFormattedString(String num) {
        String finalNumber = "".trim();
        for (int index = 0; index < num.length(); index++) {
            if (Character.isDigit(num.charAt(index))) {
                finalNumber = finalNumber + num.charAt(index);

            }

        }

        return finalNumber;
    }

    // make a call on number click
    public void callDialog(final String number, String showNumber) {
        try {

            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setTitle("Would u like to do call?");
            dialog.setMessage(showNumber);
            dialog.setCancelable(false);
/*
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (!hasPermissions(c, PERMISSIONS)) {
                ActivityCompat.requestPermissions(c, PERMISSIONS, PERMISSION_CODE);
                return true;
            }*/

            dialog.setPositiveButton("Call", new android.content.DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

//                        mContext.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},101);
                        return;
                    } else {
                        mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
                    }
                }
            });
            dialog.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // send email on desired Address
    public void sendEmail(String emailToAddress) {
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        mContext.startActivity(Intent.createChooser(intent, ""));*/

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + emailToAddress + "?subject=" + "" + "&body=" + "");
        intent.putExtra(Intent.EXTRA_CC, "");
        intent.setData(data);
        mContext.startActivity(Intent.createChooser(intent, ""));
    }

    // change date format
    public String changeDateFormat(String incomingDate) {
        // parse the String "29/07/2013" to a java.util.Date object
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse(incomingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // format the java.util.Date object to the desired format
        return new SimpleDateFormat("MM/dd/yy").format(date);

    }

    // set cursor after last character in the edittext on page loading
    public void setCursorOnLast(EditText editText, int length) {

        Selection.setSelection(editText.getText(), length);


    }

    //  set tabs with on activity Magic tabs
    // dialog messages with the types

    // first letter of word is slightly bigger than other words
    public void makeFirstLetterSpannable(String title, TextView titleTextView) {

        final SpannableString spannableString = new SpannableString(title);
        String splitString[] = title.split("\\s+");
        char splitStringFirstChartacter[] = new char[splitString.length];
        int positions[] = new int[splitString.length];

        for (int i = 0; i < splitString.length; i++) {

            splitStringFirstChartacter[i] = splitString[i].charAt(0);
            if ((splitStringFirstChartacter[i] >= 'a' && splitStringFirstChartacter[i] <= 'z') || (splitStringFirstChartacter[i] >= 'A' && splitStringFirstChartacter[i] <= 'Z') || (splitStringFirstChartacter[i] >= '0' && splitStringFirstChartacter[i] <= '9')) {
                positions[i] = title.indexOf(splitStringFirstChartacter[i]);
            }

            spannableString.setSpan(new RelativeSizeSpan(1.5f), positions[i], positions[i] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        titleTextView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    // first letter of word is slightly bigger than other words
    public void makeFirstLetterSpannableOnButton(String title, TextView titleTextView) {

        final SpannableString spannableString = new SpannableString(title);
        String splitString[] = title.split("\\s+");
        char splitStringFirstChartacter[] = new char[splitString.length];
        int positions[] = new int[splitString.length];

        for (int i = 0; i < splitString.length; i++) {

            splitStringFirstChartacter[i] = splitString[i].charAt(0);
            if ((splitStringFirstChartacter[i] >= 'a' && splitStringFirstChartacter[i] <= 'z') || (splitStringFirstChartacter[i] >= 'A' && splitStringFirstChartacter[i] <= 'Z') || (splitStringFirstChartacter[i] >= '0' && splitStringFirstChartacter[i] <= '9')) {
                positions[i] = title.lastIndexOf(splitStringFirstChartacter[i]);
            }

            spannableString.setSpan(new RelativeSizeSpan(1.5f), positions[i], positions[i] + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        titleTextView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }


    public void createDialogBox(String type, String msg) {

        if (msg.equals("")) msg = "Something went wrong! On Server Side";
        switch (type) {
            case "w":
            case "W":
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Check Fields! ")
                        .setContentText(msg)
                        .setConfirmText("Accept")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;


            case "E":
            case "e":

                new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Sorry!")
                        .setContentText(msg)
                        .show();
                break;
            case "c":
            case "C":
                new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setCustomImage(R.mipmap.app_icon_new)
                        .setTitleText("Location Unavailable")
                        .setContentText("Please wait location is being fetched")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;

            case "S":
            case "s":
                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText(msg)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;

            case "sim":
            case "SIM":
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Check Field! ")
                        .setContentText(msg)
                        .setConfirmText("Accept")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;


        }
      /*  new SweetAlertDialog(mContext)
                .setTitleText("Here's a message!")
                .show();
*/



      /*  new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .show();*/
      /*
      //Show the cancel button and bind listener to it：
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();*/
       /*
       // dialog confirm and then show dialog again when completed
       new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();*/

       /*

        // custom messsge dialog
        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Sweet!")

                .setContentText("Here's a custom image.")
                .setCustomImage(R.mipmap.ic_network)
                .show();*/
/*
// warning dialog
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes")
                .show();
*/
// error dialog


    }

    public void confirmDialog(String msg, final Activity mActivity) {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismissWithAnimation();
                        mActivity.finish();
                        mActivity.overridePendingTransition(R.anim.modal_in, R.anim.modal_out);
                    }
                })
                .show();

    }

    public void cameraIntent(int code) {
        Intent cameraintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //  startActivityForResult(cameraintent, 101);
        mContext.startActivityForResult(Intent.createChooser(cameraintent, "Click Picture"), code);
    }

    /*
Solution with actual 90% calculation:
@Override public void onStart() {
   Dialog dialog = getDialog();
   if (dialog != null) {
     dialog.getWindow()
        .setLayout((int) (getScreenWidth(getActivity()) * .9), ViewGroup.LayoutParams.MATCH_PARENT);
   }
}
    */

    /*
      public void showAlertWindow() {
        //show the dialog first
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Test Dialog")
                .setMessage("This should expand to the full width")
                .show();
        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }
*/
/*

    public void clearBackStack(Activity sourceAct, Activity source) {
      //  Intent intent = new Intent(sourceAct.this, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
*/
    public void shareMessage(String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "CitiOculus App");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));


    }

    public void shareImage(File path) {

        Intent share = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(path);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(share, "Share Image to"));
    }

    public String getImagePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;

    }

    public void appendTextView(String Value, LinearLayout mLinear) {

        TextView msg = new TextView(mContext);
        msg.setText(Value);
        msg.setPadding(10, 10, 10, 10);
        msg.setTextColor(mContext.getResources().getColor(R.color.color_white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 15, 0, 0);
        params.gravity = Gravity.LEFT;
        msg.setLayoutParams(params);
        msg.setGravity(Gravity.CENTER);
        mLinear.addView(msg);
    }

    //  show Toast
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }


    public void goForNextBatch(Class cl) {
        mContext.startActivity(new Intent(mContext, cl));
        mContext.finish();
        mContext.overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

    }

    public void explicitIntent(Class c) {
        mContext.startActivity(new Intent(mContext, c));
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }

    public void explicitIntentFromLeft(Class c) {
        mContext.startActivity(new Intent(mContext, c));
        mContext.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }
}
