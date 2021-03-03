package za.asa_media.awesome_sa.modules_.place_;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.relex.circleindicator.CircleIndicator;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.customizedfragment.CustomizedPlaceFragment;
import za.asa_media.awesome_sa.modules_.adapter.NearByPlacesRecycleAdapter;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.place_.adapter.MyAdapter;
import za.asa_media.awesome_sa.modules_.place_.callback.MainCallback;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;

public class MainScreen extends CommonActivity implements MainCallback {

    boolean doubleBackToExitPressedOnce = false;
    boolean mFlagCheck = false;
    private Activity mContext = this;
    private Toolbar mToolbar;
    private TextView mTextMyLocationNameHeader;
    // near by places setup
    // private List<NearByPlacesData> mListData;
    //  private RecyclerView mRecycleNearByPlaces;
    // private NearByPlacesRecycleAdapter mAdapterGrid;

    private EditText mEditTextSearch;
    private ImageView mImageViewHeaderSetting, mImageViewHeaderStar;

    // Helper object
    private UiHandleMethods uiHandle;
    private MainPresenter mPresenter;
    private NearByPlacesRecycleAdapter mAdap;

    // view pager decalarations
    private ViewPager pager;
    private CircleIndicator indicator;


    /* Location search */
    private FrameLayout mFrameLocation;
    private CustomizedPlaceFragment mSearchLocationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // calling init views for initialisation
        initViews();
    }

    private void initViews() {

        // initialisations
        InitialValueSetUp.mContext = this;
        mPresenter = new MainPresenter(InitialValueSetUp.mContext);
        uiHandle = new UiHandleMethods(MainScreen.this);

        //   mListData = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(mToolbar);
        ActionBar mAct = getSupportActionBar();
        mAct.setTitle("");

        mEditTextSearch = (EditText) findViewById(R.id.edittext_search);
        mImageViewHeaderSetting = (ImageView) findViewById(R.id.img_header_setting_left);
        mImageViewHeaderStar = (ImageView) findViewById(R.id.img_header_star_right);
        mTextMyLocationNameHeader = (TextView) findViewById(R.id.text_header_location);

        uiHandle.onLocationChanged(mTextMyLocationNameHeader);

        /*  location search */
        mFrameLocation = (FrameLayout) findViewById(R.id.search_loc);
        mSearchLocationFragment = (CustomizedPlaceFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mSearchLocationFragment.setAllowReturnTransitionOverlap(true);
        mSearchLocationFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                //   TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place);

                //   uihandle.showToast(place.getId() + "\n" + place.getName() + "\n" + place.getAddress());
                //    slected place lat long

                String businessName = place.getName().toString();
                // get full detail of that particular place

                ReservedLocation.getSingletonInstance().setCurrent_lng(String.valueOf(place.getLatLng().longitude));
                ReservedLocation.getSingletonInstance().setCurret_lat(String.valueOf(place.getLatLng().latitude));

                //    uiHandle.onLocationChanged(mTextMyLocationNameHeader);
                //  mTextMyLocationNameHeader.setText(Html.fromHtml("<font color='#7F7F7F'>Your Location<br></font>" + place.getName()));
                mTextMyLocationNameHeader.setText(Html.fromHtml("" + place.getName()));

                mFrameLocation.setVisibility(View.GONE);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
                uiHandle.showToast("please provide accurate detail");
            }
        });

        // mRecycleNearByPlaces = (RecyclerView) findViewById(R.id.recycle_near_by_places);
        // mRecycleNearByPlaces.setLayoutManager(new GridLayoutManager(this, 3));

        indicator = (CircleIndicator) findViewById(R.id.indicator);
        pager = (ViewPager) findViewById(R.id.pager);

        // apply listeners method
        applyListenersToViews();


    }


    private void applyListenersToViews() {

        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(mAdapter);
        indicator.setViewPager(pager);

        mImageViewHeaderSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  uiHandle.showToast("Please have Patience, Under Scruitny");
                //uiHandle.explicitIntent(Settings.class);
                MainScreen.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                InitialValueSetUp.searchedLocationName = "";
            }
        });
        mImageViewHeaderStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  uiHandle.showToast("Please have Patience, Under Scruitny");
                /*DialogFragmentForGetFavourites dialog = new DialogFragmentForGetFavourites();
                dialog.show(getSupportFragmentManager(), "");*/
                //  showDialog(mContext);
                // uiHandle.createDialogBox("s","hello");
                uiHandle.explicitIntent(FavouritePlaces.class);
            }
        });
        mEditTextSearch.setFocusable(false);
        mEditTextSearch.setClickable(true);
        mEditTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiHandle.explicitIntent(SearchCategoryScreen.class);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        mTextMyLocationNameHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchLocationFragment.zzJk();
            }
        });


    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.fragment_layout_attach_customer);

        dialog.show();

    }

    @Override
    public void getGoogleSearchPlace(String place_name) {
        //  hide keypad while click on selected place

        uiHandle.hideSoftKeyboard();

        if (TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurret_lat()) && TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurrent_lng())) {
            uiHandle.createDialogBox("c", "Location not found");

        } else {
            if (InitialValueSetUp.mTypeHeading.equals("Home makers & improvers")) {

                homeImproverDialog();
            } else if (InitialValueSetUp.mTypeHeading.equals("Hotels & Accommodation")) {
                hotelAccomodationDialog("Hotels & Accommodation");
            } else if (InitialValueSetUp.mTypeHeading.equals("Things to do")) {
                // set flag for the show sub category
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


    private void mLocationDialog() {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Success!")
                .setContentText("msg")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void getTravelAndTourDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Travel & Tours")
                .items(mPresenter.getTourAndTravels())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getHairBeautyDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Hair & Beauty")
                .items(mPresenter.getHairAndBeauty())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
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
                .items(mPresenter.getSpaList())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
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
                .items(mPresenter.getRetailTherapy())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                })
                .show();
    }

    private void getGymTypes() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Gym")
                .items(mPresenter.getGymLis())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
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
                .items(mPresenter.getNightLifes())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
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
                .items(mPresenter.getThingsToDo())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        // set flag for the show sub category
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void hotelAccomodationDialog(String title) {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title(title)
                .items(mPresenter.getHotelAccommodation())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        // set flag for the show sub category
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);

                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }


    private void homeImproverDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Home makers & improvers")
                .items(mPresenter.getHomeImprovers())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        // set flag for the show sub category
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);

                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void getRestaurantsDialog() {
        new MaterialDialog.Builder(this)
                .titleGravity(GravityEnum.CENTER)
                .title("Restaurants")
                .items(mPresenter.getRestaurantsList())
                //.itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        // uiHandle.showToast(which + ": " + text + ", ID = " + view.getId());
                        String keyValue = mPresenter.getNearByGooglePlaces(text.toString().trim());
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void goForPlaces(String nm) {
        InitialValueSetUp.mType = nm;
        getSessionInstance().setCategoryTabbed(InitialValueSetUp.mTypeHeading); // 1
        Intent intent = new Intent(mContext, SelectedPlaceListActivity.class);
        startActivity(intent);
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


    public void getSearchLocationDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);


        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {

            //  uiHandle.showToast("Please have Patience, Under Scruitny");
            uiHandle.explicitIntent(Settings.class);

        } else if (id == R.id.action_fav) {
            // uiHandle.createDialogBox("s","hello");
            uiHandle.explicitIntent(FavouritePlaces.class);


        }


        return true;
    }

    @Override
    public void onBackPressed() {

        if (mSearchLocationFragment.isVisible()) {
            mFrameLocation.setVisibility(View.GONE);
        }
       /* if (doubleBackToExitPressedOnce) {
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
        }, 2000);*/
    }


}

