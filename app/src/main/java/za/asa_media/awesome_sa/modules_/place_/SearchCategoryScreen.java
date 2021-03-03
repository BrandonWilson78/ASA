package za.asa_media.awesome_sa.modules_.place_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.common_util_.CommonActivity;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.NearByPlacesData;
import za.asa_media.awesome_sa.modules_.place_.adapter.AdapterCategorySearch;
import za.asa_media.awesome_sa.modules_.place_.adapter.AdapterSearchCatagoryFilter;
import za.asa_media.awesome_sa.modules_.place_.callback.ISearchFilterCallback;
import za.asa_media.awesome_sa.modules_.place_.callback.MainCallback;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;

public class SearchCategoryScreen extends CommonActivity implements MainCallback, ISearchFilterCallback {

    NearByPlacesData dataa = null;
    private Activity mContext = this;
    private RecyclerView mRecylerViewDashboard;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterCategorySearch mAdapter;
    private AdapterSearchCatagoryFilter mAdapterFilter;
    private boolean mFlagCheck = false;
    private EditText mEditTextSearch;
    private TextView mTextSorryMsg;
    private UiHandleMethods uihandle;
    private List<NearByPlacesData> mListData, mListDataa;
    private MainPresenter mPresenter;
    private boolean mFlagNewEntry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category_screen);

        initViews();
    }

    private void initViews() {

        uihandle = new UiHandleMethods(mContext);
        mListData = new ArrayList<>();
        mListDataa = new ArrayList<>();
        mPresenter = new MainPresenter(mContext);

        mEditTextSearch = (EditText) findViewById(R.id.edittext_search);
        //   mTextSorryMsg = (TextView) findViewById(R.id.textview_sorry_msg);
        mRecylerViewDashboard = (RecyclerView) findViewById(R.id.recyclerview_search_category);
        mLayoutManager = new GridLayoutManager(mContext, 3);
        mRecylerViewDashboard.setLayoutManager(mLayoutManager);

        String[] names = getCategories();
        int[] drawables = getCategoryDrawables();
        for (int i = 0; i < names.length; i++) {
            mListData.add(new NearByPlacesData(names[i], drawables[i]));
        }

        // ....... for search with in sub category also...........//
        String[] namess = mPresenter.getAllCatagory();
        int[] drawabless = mPresenter.getCategoryDrawables();

        for (int i = 0; i < namess.length; i++) {
            if (i >= drawabless.length - 1) {
                mListDataa.add(new NearByPlacesData(namess[i], R.mipmap.blue_circle_));
            } else {
                mListDataa.add(new NearByPlacesData(namess[i], drawabless[i]));
            }

        }


        mAdapter = new AdapterCategorySearch(mContext, mListData);
        mRecylerViewDashboard.setAdapter(mAdapter);

        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // filter your list from your input
                filter(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public String[] getCategories() {

        String[] nearByPlaces = {
                "Things to do", "Attractions", "Hotels & Accommodation", "Cheap Flights",
                "Car Rentals", "Restaurants", "Sushi", "Pizza", "Craft Beer", "Coffee",
                "Burger Joints", "Nightlife", "Vegan & Vegetarian", "Game reserves", "Vineyards & Tastings",
                "Art & Crafts", "Events & Concerts", "Retail therapy",
                "Shopping malls", "Organic shops", "Kids Activities", "Transport & Taxi", "Golf Venues",
                "Tennis courts", "Yoga Studios", "Conference Venues",
                "Travel & Tours", "Hair & Beauty", "Spas", "Men's Grooming & Barber", "Home makers & improvers",
                "Electricians", "Plumbers", "Handyman", "Banks",
                "ATM Machines", "Embassies", "Hospitals", "Pharmacies", "Wedding Venues",
                "Ticket Purchase", "Movie Theaters",
                "Gym", "Gas Stations"


        };
        return nearByPlaces;
    }

    public int[] getCategoryDrawables() {
        int[] iconSet = {
                R.mipmap.menu_icon_24, R.mipmap.menu_icon_43, R.mipmap.menu_icon_26, R.mipmap.menu_icon_25,
                R.mipmap.menu_icon_21, R.mipmap.menu_icon_20, R.mipmap.menu_icon_44, R.mipmap.menu_icon_45,
                R.mipmap.menu_icon_19, R.mipmap.menu_icon_41, R.mipmap.menu_icon_40, R.mipmap.menu_icon_15,
                R.mipmap.menu_icon_42, R.mipmap.menu_icon_39,
                R.mipmap.menu_icon_27, R.mipmap.menu_icon_38, R.mipmap.menu_icon_37, R.mipmap.menu_icon_52, R.mipmap.menu_icon_16,
                R.mipmap.menu_icon_organic, R.mipmap.menu_icon_12, R.mipmap.menu_icon_11, R.mipmap.menu_icon_01,
                R.mipmap.menu_icon_36, R.mipmap.menu_icon_35, R.mipmap.menu_icon_10,
                R.mipmap.menu_icon_09, R.mipmap.menu_icon_34, R.mipmap.menu_icon_08, R.mipmap.menu_icon_33, R.mipmap.menu_icon_07, R.mipmap.menu_icon_31,
                R.mipmap.ic_plumber, R.mipmap.menu_icon_32,
                R.mipmap.menu_icon_30, R.mipmap.menu_icon_29, R.mipmap.menu_icon_18,
                R.mipmap.menu_icon_02, R.mipmap.menu_icon_48, R.mipmap.menu_icon_14,
                R.mipmap.menu_icon_23, R.mipmap.menu_icon_47,
                R.mipmap.menu_icon_49, R.mipmap.menu_icon_51};

        return iconSet;
    }

    @Override
    public void getGoogleSearchPlace(String place_name) {
        uihandle.hideSoftKeyboard();
        if (TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurret_lat()) && TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurrent_lng())) {
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
            } else {
                getSessionInstance().setCategoryFlagHavingSubcategory(false);

                goForPlaces(place_name);
            }
        }
    }

    public void filter(String text) {
        if (!mFlagCheck) {

            mLayoutManager = new LinearLayoutManager(mContext);
            mRecylerViewDashboard.setLayoutManager(mLayoutManager);
            //  mAdapter = new AdapterCategorySearch(mContext, mListData);
            //  mRecylerViewDashboard.setAdapter(mAdapter);

            mAdapterFilter = new AdapterSearchCatagoryFilter(mContext, mListDataa);
            mRecylerViewDashboard.setAdapter(mAdapterFilter);

            dataa = new NearByPlacesData(text, R.mipmap.blue_circle_);

            mFlagCheck = true;

        }

        List<NearByPlacesData> temp = new ArrayList<>();
        int textLength = text.length();

        if (TextUtils.isEmpty(text)) {
            mLayoutManager = new GridLayoutManager(mContext, 3);
            mRecylerViewDashboard.setLayoutManager(mLayoutManager);
            mAdapter = new AdapterCategorySearch(mContext, mListData);
            mRecylerViewDashboard.setAdapter(mAdapter);
            mFlagCheck = false;

        } else {
            temp.clear();

            for (NearByPlacesData d : mListDataa) {
                if (textLength <= d.getPlaceName().length()) {
                    // compare the String in EditText with Names in the ArrayList
                    if (text.equalsIgnoreCase(d.getPlaceName().substring(0, textLength))) {
                        temp.add(d);
                    }
                } else {
                    dataa.setPlaceName(text);
                }
            }

            temp.add(dataa);
            //update recyclerview
            mAdapterFilter.updateList(temp);
        }
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
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
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
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
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
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
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
                        getSessionInstance().setCategoryFlagHavingSubcategory(true);
                        InitialValueSetUp.mTypeHeading = text.toString().trim();
                        goForPlaces(keyValue);
                        //  goForPlaces("Doors&Windows&Glass&keyword=Doors&Windows&Glass");
                    }
                }).show();
    }

    private void goForPlaces(String nm) {

        InitialValueSetUp.mType = nm;
        getSessionInstance().setCategoryTabbed(InitialValueSetUp.mTypeHeading);   //  1

        Intent intent = new Intent(mContext, SelectedPlaceListActivity.class);
        startActivity(intent);
        mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @Override
    public void getGoogleSearchKeyword(String code, String place) {
        uihandle.hideSoftKeyboard();

        if (TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurret_lat()) && TextUtils.isEmpty(ReservedLocation.getSingletonInstance().getCurrent_lng())) {
            uihandle.createDialogBox("c", "Location not found");
        } else {
            // InitialValueSetUp.mTypeHeading.equals("Retail therapy")) {
            goForPlaces(place);


        }
    }
}
