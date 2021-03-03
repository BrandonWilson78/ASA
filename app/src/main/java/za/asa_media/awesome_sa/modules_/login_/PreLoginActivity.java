package za.asa_media.awesome_sa.modules_.login_;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.new_module_changes.AdapterViewPagerStarting;
import za.asa_media.awesome_sa.modules_.place_.MainScreen;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.paypal_.PayPalPresenter;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.check_permissions.PermissionCheck;
import za.asa_media.awesome_sa.support.fusedlocationapi.LocationFinder;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

public class PreLoginActivity extends AppCompatActivity {

    public static ProgressDialog mLocationDialog;
    final long DELAY_MS = 3000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    boolean doubleBackToExitPressedOnce = false;
    PayPalPresenter mP;
    int currentPage = 0;
    Timer timer;
    private Activity mContext = this;
    private UiHandleMethods uihandle;
    // views decalarations
    private Button mBtnUser, mBtnBusiness;
    private LocationFinder mLocationApiClient;
    private LoginPresenter mPresenter;
    private boolean mFlagPermission = false;
    private String mDeviceId;
    private SessionCityOculus mSession;
    private TextView mTextViewBelowBusiness;
    // New Implementation  with new designe
    private ViewPager pager;
    private int mCurrentPage;
    private ImageView mImgPreOne, mImgPreTwo, mImgPreOne_, mImgPreTwo_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeStatusBar();
        setContentView(R.layout.activity_pre_login);

        // calling initviews for intialisations
        initViews();
        applyContent();
    }

    private void removeStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void applyContent() {

        AdapterViewPagerStarting mAdapter = new AdapterViewPagerStarting(getSupportFragmentManager(), 4);
        pager.setAdapter(mAdapter);


        /*** After setting the adapter use the timer ***/
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();   //    This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //   currentPage = position;

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentPage = position;

                //###### clear all rounds for gray first then fill color ########\\
                clearRounds();
                if (mCurrentPage == 0) {
                    mImgPreOne_.setImageResource(R.mipmap.round_brown_18);

                } else if (mCurrentPage == 1) {
                    mImgPreOne.setImageResource(R.mipmap.round_brown_18);

                } else if (mCurrentPage == 2) {
                    mImgPreTwo.setImageResource(R.mipmap.round_brown_18);

                } else if (mCurrentPage == 3) {
                    mImgPreTwo_.setImageResource(R.mipmap.round_brown_18);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void clearRounds() {
        mImgPreOne.setImageResource(R.mipmap.round_gray_18);
        mImgPreTwo.setImageResource(R.mipmap.round_gray_18);
        mImgPreOne_.setImageResource(R.mipmap.round_gray_18);
        mImgPreTwo_.setImageResource(R.mipmap.round_gray_18);

    }

    private void initViews() {

        //  Initialisations
        mSession = new SessionCityOculus(this);
        uihandle = new UiHandleMethods(mContext);
        mPresenter = new LoginPresenter(mContext);
        mLocationApiClient = new LocationFinder(this);

        pager = (ViewPager) findViewById(R.id.pager);


        mDeviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        mSession.setDeviceToken(mDeviceId);
        //  uihandle.showToast(mDeviceId);

        mImgPreOne = (ImageView) findViewById(R.id.img_pre_one);
        mImgPreTwo = (ImageView) findViewById(R.id.img_pre_two);
        mImgPreOne_ = (ImageView) findViewById(R.id.img_pre_one_);
        mImgPreTwo_ = (ImageView) findViewById(R.id.img_pre_two_);

        mBtnUser = (Button) findViewById(R.id.btn_user);
        mBtnBusiness = (Button) findViewById(R.id.btn_business);
        mTextViewBelowBusiness = (TextView) findViewById(R.id.txt_broadcast_africa);

        //  Animation translateText = AnimationUtils.loadAnimation(this, R.anim.animatefile);
        String mB = "How to <font color='#1D375E'>register your Business on Google?</font>";
        mTextViewBelowBusiness.setText(Html.fromHtml(mB));

        //  mTextViewBelowBusiness.startAnimation(translateText);
        mTextViewBelowBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uihandle.explicitIntent(ActivityHowToRegister.class);
                uihandle.openWebLink("https://www.google.com/intl/en/business/");
            }
        });

        mBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  uihandle.explicitIntent(UserDashboard.class);
                if (TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurret_lat()) &&
                        TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurrent_lng())) {
                    //  uihandle.createDialogBox("c", "");

                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setCustomImage(R.mipmap.app_icon_new)
                            .setTitleText("Location Unavailable")
                            .setContentText("Please wait location is being fetched")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    mLocationApiClient = new LocationFinder(mContext);
                                    mLocationDialog = ProgressDialog.show(mContext, "Please wait", "While location is being fetched", false, false);
                                    mLocationDialog.setCancelable(true);
                                    mLocationApiClient.startApiClient();

                                }
                            })
                            .show();

                } else {
                    uihandle.explicitIntent(MainScreen.class);
                }

            }
        });
        mBtnBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mPresenterPayPal.getPayment(123);
                goToIntialScreen();

            }
        });
    }

    private void goToIntialScreen() {
        boolean isLogin = false;
        try {
            isLogin = mSession.isLogged();
            // userType = csfm.getUserType();
            if (isLogin) {
                uihandle.explicitIntent(LoggedInUserDashboard.class);
                this.finish();
            } else {
                //ActivityUnRegisteredDefault
                Intent intent = new Intent(PreLoginActivity.this, SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


            }

            // }

          /*  Log.d("b",""+isLogin+""+csfm.getUserType()+""+csfm.getUserId());
           // startActivity(new Intent(this, activityClass));
            Toast.makeText(this,""+isLogin+""+csfm.getUserType()+""+csfm.getUserId(),Toast.LENGTH_LONG).show();
     */
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter.onStartCheck()) {
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
                if (!(new PermissionCheck(this).check())) {
                    mLocationApiClient = new LocationFinder(mContext);
                    mLocationDialog = ProgressDialog.show(mContext, "Please wait", "While location is being fetched", false, false);
                    mLocationDialog.setCancelable(true);
                    mLocationApiClient.startApiClient();
                }
                //         mFlagPermission = true;
            } else {
                mLocationApiClient = new LocationFinder(mContext);
                mLocationDialog = ProgressDialog.show(mContext, "Please wait", "While location is being fetched", false, false);
                mLocationDialog.setCancelable(true);
                mLocationApiClient.startApiClient();
            }
        }

       /*  if (InitialValueSetUp.mConectivityCheck) {
            if (mLocationDialog.isShowing()) {
                mLocationDialog.dismiss(); } }
       */

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionCheck.PERMISSION_CODE) {
            for (int i = 0; i < grantResults.length; i++) {

                if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    if (i == grantResults.length - 1) {
                        if (mPresenter.onStartCheck()) {
                            mLocationApiClient.startApiClient();
                        }
                    }
                } else {

                    uihandle.showToast("Permission ungranted");
                    // mContext.finish();
                    // mContext.moveTaskToBack(true);
                    return;
                }
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationApiClient.stopApiClient();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            this.finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
