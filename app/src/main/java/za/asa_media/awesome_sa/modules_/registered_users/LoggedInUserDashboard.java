package za.asa_media.awesome_sa.modules_.registered_users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.catalogue.CatalogActivity;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.common_util_.api_request_volley.HttpRequester;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.event.EventsActivity;
import za.asa_media.awesome_sa.modules_.login_.LoginPresenter;
import za.asa_media.awesome_sa.modules_.news.NewsActivity;
import za.asa_media.awesome_sa.modules_.place_.MainPresenter;
import za.asa_media.awesome_sa.modules_.place_.SelectedPlaceListActivity;
import za.asa_media.awesome_sa.modules_.place_.callback.MainCallback;
import za.asa_media.awesome_sa.modules_.promotion.PromotionActivity;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdCatalogue;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdEvent;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdNews;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdPromotions;
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdSpecials;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdCatalogue;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdEvent;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdNews;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdPromotion;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdSpecial;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGetNewPurchasedId;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiGoForLogout;
import za.asa_media.awesome_sa.modules_.registered_users.api.FireApiUpdatePaymentStatus;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.HomeFragment;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.IBusinessViewCallBack;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.IMyAdsCallback;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.IRunningAdsCallback;
import za.asa_media.awesome_sa.modules_.registered_users.model.CreateAdModel;
import za.asa_media.awesome_sa.modules_.registered_users.model.RunningAdsModel;
import za.asa_media.awesome_sa.modules_.registered_users.presenter.DashboardPresenter;
import za.asa_media.awesome_sa.modules_.special.SpecialActivity;
import za.asa_media.awesome_sa.modules_.terms_conditions.TermsAndConditions;
import za.asa_media.awesome_sa.paypal_.PayPalPresenter;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.fusedlocationapi.LocationFinder;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

import static za.asa_media.awesome_sa.R.id.frame_screen;

public class LoggedInUserDashboard extends CommonActivity implements View.OnClickListener,
        IBusinessViewCallBack,
        IMyAdsCallback,
        IRunningAdsCallback,
        MainCallback, IImageChangerCallback {

    //    urls to load navigation header background image
    //    and profile image
    //    index to identify current nav menu item
    public static int navItemIndex = 0;
    //
    public static boolean mFlagRun = false;
    public CircleImageView imgProfile;
    Bundle savedInstanceState;
    boolean doubleBackToExitPressedOnce = false;
    String latest_price = "";
    private ActionBarDrawerToggle toggle;
    private Activity mContext = this;
    // navigation drawaer setup
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtEmailId;
    private Toolbar toolbar;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private DashboardPresenter mPresenterDashboard;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private MainPresenter mPresenterMain;
    private boolean flag = true;
    // location finder
    private LocationFinder mLocationApiClient;
    //  Paypal initialisation
    private LoginPresenter mPresenter;
    private PayPalPresenter mPresenterPaypal;
    private String paymentDetails;
    private String mState, paymentId, paymentCreatedTime;
    private TextView mTextViewChanger;
    private List<String> mListPlans;
    private List<String> mListViewChange;
    private Fragment mBusinessViewFragment = null;
    private RelativeLayout mRelRenewPlanLayout;
    private LinearLayout mLinearPurchaseAddPlan;
    private TextView mTextFreeAddTrailStatus, mTextExpireDate;
    private Button mButtonRenewPlan;
    private TextView mTextSpecial, mTextEvents, mTextPromotions, mTextNews, mTextCataloge;
    private ImageView mImageSpecial, mImageEvents, mImagePromotions, mImageNews, mImageCataloge;
    private HomeFragment mHome = null;
    private BubbleLayout bubbleLayout;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.new_app_blue_color));
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_logged_in_user_dashboard);


        initViews();
        implementListeners();

    }

    private void implementListeners() {
        mTextViewChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  getViewChangerDialog();
                showPopupWindowForViewChange(view);
            }
        });
    }

    private void showPopupWindowForViewChange(View v) {
        int[] location = new int[2];
        v.getLocationInWindow(location);
        TextView mTextUser = bubbleLayout.findViewById(R.id.btext_user);
        TextView mTextBusiness = bubbleLayout.findViewById(R.id.btext_business);
        bubbleLayout.setArrowDirection(ArrowDirection.TOP_CENTER);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], v.getHeight() + location[1]);

        mTextUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextViewChanger.setText("User View");
                try {
                    popupWindow.dismiss();

                    mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(frame_screen, new HomeFragment());
                    fragmentTransaction.commitAllowingStateLoss();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mTextBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextViewChanger.setText("Business View");

                popupWindow.dismiss();

                mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.new_app_blue_color));
                toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.new_app_blue_color));

                navItemIndex = 0;
                DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_HOME;
                loadHomeFragment();
                loadNavHeader();

            }
        });

        //   popupWindow.showAtLocation(v, Gravity.TOP, 0, 0);
    }

    private void initViews() {

        //   location
        mListViewChange = new ArrayList<>();
        mListViewChange.add("Business View");
        mListViewChange.add("User View");

        mPresenterPaypal = new PayPalPresenter(mContext);
        mPresenter = new LoginPresenter(mContext);
        mLocationApiClient = new LocationFinder(mContext);

        mSession = new SessionCityOculus(mContext);
        mPresenterMain = new MainPresenter(mContext);

        mPresenterDashboard = new DashboardPresenter(mContext);
        uihandle = new UiHandleMethods(mContext);
        mHandler = new Handler();
//######### for view changer ############\\
        bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.popup_view_changer, null);
        popupWindow = BubblePopupHelper.create(this, bubbleLayout);


        // ####### remove class name near hamburger icon #############\\
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mTextViewChanger = (TextView) findViewById(R.id.text_view_changer);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.txt_username);
        txtEmailId = navHeader.findViewById(R.id.txt_email_id);
        imgProfile = navHeader.findViewById(R.id.img_user_header);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        /****
         * Initializing
         * Navigation
         * Menu
         ****/


        setUpNavigationView();

        if (mSession.getAdsDirectTag()) {
            navItemIndex = 3;
            DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_MY_ADS;
            loadHomeFragment();
            loadNavHeader();
            mSession.setAdsDirectTag(false);
        } else if (getSessionInstance().getNavIndex()) {
            navItemIndex = 2;
            DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_POST_NEW_AD;
            loadHomeFragment();
            loadNavHeader();
            getSessionInstance().setNavIndex(false);
        } else {
            if (savedInstanceState == null) {
                navItemIndex = 0;
                DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_HOME;
                loadHomeFragment();
                loadNavHeader();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uihandle.hideSoftKeyboard();



      /*  if (mSession.getAdsDirectTag()) {
            navItemIndex = 3;
            DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_MY_ADS;
            loadHomeFragment();
            loadNavHeader();
            mSession.setAdsDirectTag(false);
        } else {
            if (savedInstanceState == null) {
                navItemIndex = 0;
                DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_HOME;
                loadHomeFragment();
                loadNavHeader();
            }
        }
*/

        // load nav menu header data
        loadNavHeader();

        // location set up check

     /*   if (mPresenter.onStartCheck()) {
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
                if (!(new PermissionCheck(this).check())) {
                    mLocationApiClient.startApiClient();
                }
                //  mFlagPermission = true;
            } else {
                mLocationApiClient.startApiClient();
            }
        }*/


    }

    @Override
    public void onBackPressed() {
       /* if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            this.finish();
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

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText(UiHandleMethods.capitalizeString(mSession.getLoggedFirstname()) + " " + mSession.getLoggedLastname());
        txtEmailId.setText(mSession.getLoggedEmail());

        //     uihandle.showToast(URLListApis.URL_COMMON_IMAGES + "" + mSession.getUserImageUrl());
        //     loading header background image
        //     uihandle.loadImageWithGlide(URLListApis.URL_COMMON_IMAGES+mSession.getUserImageUrl(), imgProfile);
        //     displayImage(1,imgProfile);
        Glide.with(this).load(URLListApis.URL_COMMON_IMAGES + mSession.getUserImageUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        //   Loading profile image
        //   uihandle.loadWebImage(imgProfile, mSession.getUserImageUrl());

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    public void displayImage(int position, CircleImageView mImageView) {

        Picasso.with(mContext)
                .load(URLListApis.URL_COMMON_IMAGES + mSession.getUserImageUrl())
                // .noPlaceholder().noFade()
                .fit()
                .centerInside()
                // .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(mImageView);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {

        if (flag) {
            if (getIntent().hasExtra("loadFragment")) {
                Log.e("Intent", "inside");
                navItemIndex = 1;
                DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_PROFILE;
                flag = false;
            }
        }
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(DashboardPresenter.CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        //    Sometimes, when fragment has huge data, screen seems hanging
        //    when switching between navigation menus
        //    So using runnable, the fragment is loaded with cross fade effect
        //    This effect can be seen in Gmail app


        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = mPresenterDashboard.getHomeFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(frame_screen, fragment, DashboardPresenter.CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        try {
            if (navItemIndex != 0) {
                mTextViewChanger.setVisibility(View.GONE);
            } else {
                mTextViewChanger.setVisibility(View.VISIBLE);
                mTextViewChanger.setText("Business View");
            }

            getSupportActionBar().setTitle(activityTitles[navItemIndex]);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                mContext.getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.new_app_blue_color));
                toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.new_app_blue_color));

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:


                        navItemIndex = 0;
                        DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_HOME;
                        break;

                    case R.id.nav_post_new_ad:
                      /*  new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                            }  });*/


                        //   uihandle.explicitIntent(ChoosePlan.class);
                        // Todo: commentred below 2 lines and added above line according to new design
                        navItemIndex = 2;
                        DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_POST_NEW_AD;

                        // Todo: done today 4pm 5 april
                        mSession.setFlagFromDetail(false);

                        break;
                    case R.id.nav_my_ads:
                        navItemIndex = 3;
                        DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_MY_ADS;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 4;
                        DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_transaction_history:
                        navItemIndex = 5;
                        DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_TRANSACTION_HISTORY;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        DashboardPresenter.CURRENT_TAG = DashboardPresenter.TAG_PROFILE;
                        break;

                    case R.id.nav_terms_and_condition:
                        //  launch new intent instead of loading fragment
                        uihandle.explicitIntent(TermsAndConditions.class);

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                            }
                        });

                        return true;

                    case R.id.nav_logout:
                        // launch new intent instead of loading fragment
                        // startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                            }
                        });

                        logoutDialog("Do you really want to Logout?");
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                        super.onDrawerClosed(drawerView);
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                        super.onDrawerOpened(drawerView);
                        invalidateOptionsMenu();
                    }
                };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void getAdDetail(String batchId, CreateAdModel adData, String[] mBatchForAds) {
        //   uihandle.showToast(createAdModel.getPlaceName() + "\n" + createAdModel.getPlanName() + "\nBatch_id: " + batchId);
        // Todo: Apply her logic to transfer control and pass value to next activity for add advertisement
        mSession.setCAdPurchaseId(adData.getPurchaseId());
        mSession.setCAdPlaceId(adData.getPlaceId());
        mSession.setCAdPlanId(adData.getPlanId());
        mSession.setCAdBatchId(batchId);
        mSession.setCAdPlaceName(adData.getPlaceName());
        mSession.setCAdExpiryDate(adData.getExpiryDate());

        InitialValueSetUp.mBatchs = mBatchForAds;
             /*
             Batch_id: 1- catalog
             Batch_id: 2- event
             Batch_id: 3-promotion
             Batch_id: 4-special
             Batch_id: 5 -news
           */

        switch (batchId) {
            case "1":
                uihandle.explicitIntent(CreateAdCatalogue.class);

                break;
            case "2":
                uihandle.explicitIntent(CreateAdEvent.class);
                break;
            case "3":
                uihandle.explicitIntent(CreateAdPromotions.class);
                break;
            case "4":
                uihandle.explicitIntent(CreateAdSpecials.class);
                break;
            case "5":
                uihandle.explicitIntent(CreateAdNews.class);
                break;
        }
    }

    public void showRenewDialog(final String mPurchaseID) {

        mListPlans = new ArrayList<>();

        mListPlans.add("Renew existing plan");
        mListPlans.add("Upgrade to $99 per year plan");

        new MaterialDialog.Builder(this)
                .title("Choose plan")
                .items(mListPlans)
                .contentColorRes(R.color.colorPrimaryDark)
                .widgetColorRes(R.color.colorPrimaryDark)
                .widgetColor(getResources().getColor(R.color.new_app_blue_color))

                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        if (text != null) {
                            //     mChoosedPlanForRenew = text.toString();
                            if (which == 0) {
                                getNewPurchasedId(mPurchaseID, "current");
                            } else if (which == 1) {
                                getNewPurchasedId(mPurchaseID, "upgrade");
                            }
                            //    uihandle.showToast(which + mChoosedPlanForRenew);
//
                        }
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @Override
    public void getPlanDetail(String purchased_id, String planId) {
        //   uihandle.showToast("batch: id: " + purchased_id);
        //   Todo: implement api here for purchased id new
        if (planId.equals("24")) {
            //                    createWarningDialog("Your 30 days trail pack is expired. You have to Purchased One Year Subscription Plan To Proceed! at just $" + latest_price + " price", purchaseid);
            showRenewDialog(purchased_id);
        } else {
            getNewPurchasedId(purchased_id, "current");
        }


    }

    private void getNewPurchasedId(final String purchased_id, final String mStatus) {
        new FireApiGetNewPurchasedId(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                String purchaseId = response.optString("purchaseid");
                                String mNewPrice = response.optString("planprice");
                                mSession.setPurchaseId(purchaseId);
                                // go for particular payment option selected by the user
                                mPresenterPaypal.getPayment(mNewPrice);
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
        }.execute(URLListApis.URL_GET_NEW_PURCHSED_ID, purchased_id, mStatus);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If the result is from paypal
        //If the result is from paypal
        if (requestCode == PayPalPresenter.PAYPAL_REQUEST_CODE) {
            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);
                        refineResponse(paymentDetails);
                        //Starting a new activity for the payment details and also putting the payment details with intent
                        /* mContext.startActivity(new Intent(mContext, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));   */

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

    }

    public void refineResponse(String jsonResponse) {
        try {

            JSONObject jsonDetails = new JSONObject(jsonResponse);

            //Displaying payment details
            JSONObject oResponse = jsonDetails.optJSONObject("response");

            paymentCreatedTime = oResponse.optString("create_time"); // date payment initiated
            paymentId = oResponse.optString("id").toString(); // txn id
            mState = oResponse.optString("state").toString(); // status
            mSession.setPaymentStatus(mState);
            mSession.setPaymentTxnId(paymentId);

            if (mState.equals("approved")) {

                goForUpdatePaymentStatus();
            }
            // createSuccessDialog("Payment has been " + mState + ". Thankyou");
        } catch (JSONException e) {
            uihandle.showToast(e.getMessage());
        }
    }

    private void goForUpdatePaymentStatus() {
        new FireApiUpdatePaymentStatus(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
              /* {
                    "status": true,
                   "message": "Payment Received Successfull"
                                                         } */
                try {
                    if (response != null) {
                        Log.d("info", response.toString());
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                //  uihandle.showToast(response.optString(("message")));
                                createSuccessDialog("Your payment has been received. Now you can create Ads for plan purchased.\nGoto-->MyAds-->Create Ads.");
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
        }.execute(URLListApis.URL_PURCHASE_UPDATESTATUS);

    }

    public void createSuccessDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // for testing purpose after testing uncomment
                        //  startActivity(new Intent(mContext, PaymentSuccessful.class));
                        // uihandle.explicitIntent(PaymentSuccessful.class);

                        mSession.setAdsDirectTag(true);
                        Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                        mContext.finish();

                    }
                })
                .show();
    }

    @Override
    public void getRunningAdDetail(String batch_id, RunningAdsModel adData) {


        mSession.setCAdPurchaseId(adData.getPurchaseId());
        mSession.setCAdPlaceId(adData.getPlaceId());
        mSession.setCAdPlanId(adData.getPlanId());
        mSession.setCAdBatchId(batch_id);
        mSession.setCAdPlaceName(adData.getPlaceName());
        mSession.setCAdExpiryDate(adData.getExpiryDate());

        switch (batch_id) {
            case "1":
                uihandle.explicitIntent(EditAdCatalogue.class);

                break;
            case "2":
                uihandle.explicitIntent(EditAdEvent.class);
                break;
            case "3":
                uihandle.explicitIntent(EditAdPromotion.class);
                break;
            case "4":
                uihandle.explicitIntent(EditAdSpecial.class);
                break;
            case "5":
                uihandle.explicitIntent(EditAdNews.class);
                //  uihandle.explicitIntent(CreateAdNews.class);
                break;
        }

    }

    public void logoutDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("LOGOUT")
                .setContentText(msg).setConfirmText("Yes").setCancelText("No").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
                new FireApiGoForLogout(mContext).execute(URLListApis.URL_LOGOUT);
            }
        }).show();
    }

    @Override
    public void getGoogleSearchPlace(String place_name) {
        // hide keypad while click on selected place
        uihandle.hideSoftKeyboard();
        if (TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurret_lat()) &&
                TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurrent_lng())) {
            uihandle.createDialogBox("c", "Location not found");
        } else {
            if (InitialValueSetUp.mTypeHeading.equals("Home makers & improvers")) {
                homeImproverDialog();
            } else if (InitialValueSetUp.mTypeHeading.equals("Hotels & Accommodation")) {
                hotelAccomodationDialog("Hotels & Accommodation");
            } else if (InitialValueSetUp.mTypeHeading.equals("Things to do")) {
                thingsToDoDialog("Things to do");
            } else if (InitialValueSetUp.mTypeHeading.equals("Nightlife")) {
                getNightLifes();
            } else if (InitialValueSetUp.mTypeHeading.equals("Gym")) {
                getGymTypes();
            } else if (InitialValueSetUp.mTypeHeading.equals("Restaurants")) {
                getRestaurantsDialog();
            } else if (InitialValueSetUp.mTypeHeading.equals("Retail therapy")) {
                retailTherapyDialog();
            } else if (InitialValueSetUp.mTypeHeading.equals("Spas")) {
                getSpasDialog();
            } else if (InitialValueSetUp.mTypeHeading.equals("Hair & Beauty")) {
                getHairBeautyDialog();
            } else if (InitialValueSetUp.mTypeHeading.equals("Travel & Tours")) {
                getTravelAndTourDialog();
            } else {
                getSessionInstance().setCategoryFlagHavingSubcategory(false);
                goForPlaces(place_name);
            }
        }
        // uiHandle.showToast(place_name);
    }

    private void getHairBeautyDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Hair & Beauty")
                .items(mPresenterMain.getHairAndBeauty())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getTravelAndTourDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Travel & Tours")
                .items(mPresenterMain.getTourAndTravels())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getSpasDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Spas")
                .items(mPresenterMain.getSpaList())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void retailTherapyDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Retail therapy")
                .items(mPresenterMain.getRetailTherapy())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }
    // paypal destroy service

    private void getRestaurantsDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Restaurants")
                .items(mPresenterMain.getRestaurantsList())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void getGymTypes() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Gym")
                .items(mPresenterMain.getGymLis())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        // goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void getNightLifes() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Nightlife")
                .items(mPresenterMain.getNightLifes())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        // goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void thingsToDoDialog(String title) {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title(title)
                .items(mPresenterMain.getThingsToDo())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void hotelAccomodationDialog(String title) {
        new MaterialDialog.Builder(mContext)
                .titleGravity(GravityEnum.CENTER)
                .title(title)
                .items(mPresenterMain.getHotelAccommodation())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void homeImproverDialog() {
        new MaterialDialog.Builder(mContext)
                .titleGravity(GravityEnum.CENTER)
                .title("Home makers & improvers")
                .items(mPresenterMain.getHomeImprovers())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenterMain.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void goForPlaces(String nm) {
        InitialValueSetUp.mType = nm;
        getSessionInstance().setCategoryTabbed(InitialValueSetUp.mTypeHeading);  //1

        Intent intent = new Intent(mContext, SelectedPlaceListActivity.class);
        startActivity(intent);
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    protected void onDestroy() {
        mPresenterPaypal.destroy();
        super.onDestroy();

    }

    @Override
    public void getBusinessViewDetails(View v) {

        getSessionInstance().setPlaceIdTabbed(mSession.getBPlaceId());   // 3
        //Todo:
        getSessionInstance().setPlaceId(mSession.getBPlaceId());
        getSessionInstance().setCAdPlaceId(mSession.getBPlaceId());

        mSession.setCategoryTabbed(mSession.getBCategory());
        mSession.setBusinessNameTabbed(mSession.getBName());

        // plan views starts
        mRelRenewPlanLayout = (RelativeLayout) v.findViewById(R.id.rel_renew_plan);
        mLinearPurchaseAddPlan = (LinearLayout) v.findViewById(R.id.linear_purchase_add_plan);
        mTextFreeAddTrailStatus = (TextView) v.findViewById(R.id.textview_free_add_trial);
        mTextExpireDate = (TextView) v.findViewById(R.id.textview_free_add_expires);
        mButtonRenewPlan = (Button) v.findViewById(R.id.btn_renew_plan);

        mTextSpecial = (TextView) v.findViewById(R.id.batch_text_place_detail_special);
        mTextEvents = (TextView) v.findViewById(R.id.batch_text_place_detail_events);
        mTextPromotions = (TextView) v.findViewById(R.id.batch_text_place_detail_promotion);
        mTextNews = (TextView) v.findViewById(R.id.batch_text_place_detail_news);
        mTextCataloge = (TextView) v.findViewById(R.id.batch_text_place_detail_catalog);

        mImageSpecial = (ImageView) v.findViewById(R.id.batch_img_place_detail_special);
        mImageEvents = (ImageView) v.findViewById(R.id.batch_img_place_detail_events);
        mImagePromotions = (ImageView) v.findViewById(R.id.batch_img_place_detail_promotion);
        mImageCataloge = (ImageView) v.findViewById(R.id.batch_img_place_detail_catalog);
        mImageNews = (ImageView) v.findViewById(R.id.batch_img_place_detail_news);


        // plan views end
        // get plan detail
        goForGetPlanDetail();


    }

    private void goForGetPlanDetail() {

        if (!isNetworkConnected()) {
            showToast(NETWORK_ERROR);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("place_id", getSessionInstance().getPlaceIdTabbed());

        new HttpRequester(Request.Method.POST, this, map, 100, URLListApis.URL_GET_PLAN_DEATAIL_WITH_PLACEID, this);
    }

    private void goForRenewPlan(String mPriceIdd, String mStatus) {

        if (!isNetworkConnected()) {
            showToast(NETWORK_ERROR);
            return;
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("userid", getSessionInstance().getLoggedId());
        map.put("token", getSessionInstance().getToken());
        map.put("purchaseid", mPriceIdd);
        map.put("status", mStatus);

        new HttpRequester(Request.Method.POST, this, map, 101,
                URLListApis.URL_GET_NEW_PURCHSED_ID, this);

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        try {

            Log.e("Response", response);
            JSONObject jsonObject = new JSONObject(response);

            switch (serviceCode) {
                case 100:
                    refinePlanDetail(jsonObject);
                    break;
                case 101:
                    refineRenewResponse(jsonObject, latest_price);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refineRenewResponse(JSONObject response, String mLatest_price) {
        try {
            if (response != null) {
                Log.d("info", response.toString());
                if (response.has("status")) {
                    if (response.optBoolean("status")) {
                        String purchaseId = response.optString("purchaseid");
                        String mNewPrice = response.optString("planprice");
                        mSession.setPurchaseId(purchaseId);
                        //    go for particular payment option selected by the user
                        //    mPresenterPaypal.getPayment(mLatest_price);

                        mPresenterPaypal.getPayment(mNewPrice);

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

    private void refinePlanDetail(JSONObject response) {
        try {
            //  uihandle.showToast(response.optString(("message")));
            if (response.optBoolean("status")) {
                JSONArray jObj = response.optJSONArray("data");
                if (jObj != null) {
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jResponse = jObj.optJSONObject(i);

                        String userid = jResponse.optString("userid").toString().trim();
                        final String purchaseid = jResponse.optString("purchase_id").toString().trim();
                        String placeId = jResponse.optString("place_id").toString().trim();
                        String placeName = jResponse.optString("place_name").toString().trim();
                        String planName = jResponse.optString("plan_name").toString().trim();
                        final String planId = jResponse.optString("plan_id").toString().trim();
                        final String price = jResponse.optString("price").toString().trim();

                        JSONArray batchIdJson = jResponse.optJSONArray("batchid");
                        String[] batchId = new String[batchIdJson.length()];

                        if (batchIdJson != null) {
                            for (int j = 0; j < batchIdJson.length(); j++) {
                                batchId[j] = batchIdJson.optString(j).toString().trim();
                            }
                        }

                        String purchaseDate = jResponse.optString("purchase_date").toString().trim();
                        String ExpiryDate = jResponse.optString("expiry_date").toString().trim();
                        String transactionId = jResponse.optString("transaction_id").toString().trim();
                        String paymentStatus = jResponse.optString("payment_status").toString().trim();
                        String planStatus = jResponse.optString("plan_status").toString().trim();
                        latest_price = jResponse.optString("latest_price").toString().trim(); // waiting for the api


                        // set for the next view
                        InitialValueSetUp.mPlaceName = placeName;


                        if (getSessionInstance().getLoggedId().equals(userid)) {
                            mRelRenewPlanLayout.setVisibility(View.VISIBLE);

                            mTextFreeAddTrailStatus.setText(Html.fromHtml(planName + ": <font color='#1D375E'>" + planStatus + "</font>"));
                            mTextExpireDate.setText("EXPIRE: " + ExpiryDate);

                            if (planStatus.equals("active")) {
                                mButtonRenewPlan.setVisibility(View.GONE);
                            }


                            try {
                                String batchs[] = batchId;
                                if (batchs != null) {
                                    for (String batch : batchs) {
                                        Log.d("batch", "" + batch);
                                        if (batch.equals("1")) {
                                            mImageCataloge.setImageResource(R.drawable.catalog);
                                            mTextCataloge.setBackgroundColor(getResources().getColor(R.color.batch_catalogue_color));
                                            mImageCataloge.setOnClickListener(this);
                                        }
                                        if (batch.equals("2")) {
                                            mImageEvents.setImageResource(R.drawable.events);
                                            mTextEvents.setBackgroundColor(getResources().getColor(R.color.batch_event_color));
                                            mImageEvents.setOnClickListener(this);
                                        }
                                        if (batch.equals("3")) {
                                            mImagePromotions.setImageResource(R.drawable.promotions);
                                            mTextPromotions.setBackgroundColor(getResources().getColor(R.color.batch_promotion_color));
                                            mImagePromotions.setOnClickListener(this);

                                        }
                                        if (batch.equals("4")) {
                                            mImageSpecial.setImageResource(R.drawable.specials);
                                            mTextSpecial.setBackgroundColor(getResources().getColor(R.color.batch_special_color));
                                            mImageSpecial.setOnClickListener(this);
                                        }
                                        if (batch.equals("5")) {
                                            mImageNews.setImageResource(R.drawable.news);
                                            mTextNews.setBackgroundColor(getResources().getColor(R.color.batch_news_color));
                                            mImageNews.setOnClickListener(this);
                                        }
                                    }
                                    //   }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }


                            mButtonRenewPlan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (planId.equals("24")) {

                                        //                    createWarningDialog("Your 30 days trail pack is expired. You have to Purchased One Year Subscription Plan To Proceed! at just $" + latest_price + " price", purchaseid);
                                        showRenewDialog(purchaseid);
                                    } else {
                                        goForRenewPlan(purchaseid, "current");
                                    }


                                }
                            });


                        }
                    }
                }
            } else {
                if (getSessionInstance().isLogged()) {
                    mLinearPurchaseAddPlan.setVisibility(View.VISIBLE);
                }
            }

        } catch (Exception e) {
            uihandle.showToast(e.toString());

        }
    }

    @Override
    public void onErrorFound(String errorResponse, int serviceCode) {
        super.onErrorFound(errorResponse, serviceCode);
        try {
            dismissIOSProgress();
            Log.e("Response", errorResponse);
            showToast(errorResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.batch_img_place_detail_catalog:

                InitialValueSetUp.adBatchId = "1";
                InitialValueSetUp.adPlaceId = mSession.getBPlaceId();

                intent = new Intent(mContext, CatalogActivity.class);
                //  intent.putExtra("address", mTextAddress.getText().toString());
                startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_events:
                //    mUiHandleMethods.explicitIntent(EventsActivity.class);
                InitialValueSetUp.adPlaceId = mSession.getBPlaceId();
                InitialValueSetUp.adBatchId = "2";
                intent = new Intent(mContext, EventsActivity.class);
                //    intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_promotion:
                //   mUiHandleMethods.explicitIntent(PromotionActivity.class);
                InitialValueSetUp.adPlaceId = mSession.getBPlaceId();
                InitialValueSetUp.adBatchId = "3";
                intent = new Intent(mContext, PromotionActivity.class);
                //     intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_news:
                //  mUiHandleMethods.explicitIntent(NewsActivity.class);
                InitialValueSetUp.adPlaceId = mSession.getBPlaceId();
                InitialValueSetUp.adBatchId = "5";
                intent = new Intent(mContext, NewsActivity.class);
                //      intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.batch_img_place_detail_special:
                InitialValueSetUp.adPlaceId = mSession.getBPlaceId();
                InitialValueSetUp.adBatchId = "4";
                intent = new Intent(mContext, SpecialActivity.class);
                //       intent.putExtra("address", mTextAddress.getText().toString());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

        }

    }

    @Override
    public void getChangeImageAndData(String firstname, String mLastName, String mImage) {
        loadNavHeader();
    }

 /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.logged_in_user_dashboard, menu);
          return true;
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          // Handle action bar item clicks here. The action bar will
          // automatically handle clicks on the HomeFragment/Up button, so long
          // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();

          //noinspection SimplifiableIfStatement
          if (id == R.id.action_settings) {
              return true;
          }

          return super.onOptionsItemSelected(item);
      }*/

 /*   private void getViewChangerDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Choose View")
                .items(mListViewChange)
                // .itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());

                        uihandle.showToast(text.toString().trim());
                        mTextViewChanger.setText(text.toString().trim());


                        //  text.toString().trim();

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }*/
}
