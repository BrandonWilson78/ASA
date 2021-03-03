package za.asa_media.awesome_sa.modules_.place_.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.NearByPlacesData;
import za.asa_media.awesome_sa.modules_.place_.adapter.UserDashFragFirst;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-07 on 5/12/2017.
 */

public class FragmentFirstScreen extends Fragment {

    boolean mFlagCheck = false;
    private RecyclerView mRecylerViewDashboard;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mView = null;
    private EditText mEditTextSearch;
    private TextView mTextSorryMsg;
    private UiHandleMethods uihandle;
    private List<NearByPlacesData> mListData;
    private UserDashFragFirst mdap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uihandle = new UiHandleMethods(getActivity());
        mListData = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_user_screen_1, container, false);
        }

        init(mView);
        return mView;
    }

    private void init(View v) {

        mEditTextSearch = getActivity().findViewById(R.id.edittext_search);
        mTextSorryMsg = v.findViewById(R.id.textview_sorry_msg);
        mRecylerViewDashboard = v.findViewById(R.id.recyclerview_dashboared);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecylerViewDashboard.setLayoutManager(mLayoutManager);

        String[] names = getNearByPlacesNames();
        int[] drawables = getNearbyPlacesDrawables();

        for (int i = 0; i < names.length; i++) {
            mListData.add(new NearByPlacesData(names[i], drawables[i]));
        }

        //  mAdapterGrid = new NearByPlacesRecycleAdapter(this, mListData);
        //  mRecycleNearByPlaces.setAdapter(mAdapterGrid);
        mdap = new UserDashFragFirst(getActivity(), mListData);
        mRecylerViewDashboard.setAdapter(mdap);

      /*  mEditTextSearch.addTextChangedListener(new TextWatcher() {
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
*/

    }

    private int[] getNearbyPlacesDrawables() {

        int[] iconSet = {
                R.mipmap.menu_icon_24, R.mipmap.menu_icon_43, R.mipmap.menu_icon_26, R.mipmap.menu_icon_25,
                R.mipmap.menu_icon_21, R.mipmap.menu_icon_20, R.mipmap.menu_icon_44, R.mipmap.menu_icon_45,
                R.mipmap.menu_icon_19, R.mipmap.menu_icon_41, R.mipmap.menu_icon_40, R.mipmap.menu_icon_15,
                R.mipmap.menu_icon_42, R.mipmap.menu_icon_39,
                R.mipmap.menu_icon_27};

        return iconSet;
    }

    private String[] getNearByPlacesNames() {

        String[] nearByPlaces = {
                "Things to do", "Attractions", "Hotels & Accommodation", "Cheap Flights",
                "Car Rentals", "Restaurants", "Sushi", "Pizza", "Craft Beer", "Coffee",
                "Burger Joints", "Nightlife", "Vegan & Vegetarian",
                "Game Reserves", "Vineyards & Tastings"};


        return nearByPlaces;
    }

    public void filter(String text) {
        if (!mFlagCheck) {
            mdap = new UserDashFragFirst(getActivity(), mListData);
            mRecylerViewDashboard.setAdapter(mdap);
            mFlagCheck = true;
        }

        List<NearByPlacesData> temp = new ArrayList<>();
        int textLength = text.length();


        if (TextUtils.isEmpty(text)) {
            mdap = new UserDashFragFirst(getActivity(), mListData);
            mRecylerViewDashboard.setAdapter(mdap);

        } else {
            temp.clear();

            for (NearByPlacesData d : mListData) {

                if (textLength <= d.getPlaceName().length()) {
                    //compare the String in EditText with Names in the ArrayList
                    if (text.equalsIgnoreCase(d.getPlaceName().substring(0, textLength))) {
                        temp.add(d);
                    }

                }
            }
            //update recyclerview
            mdap.updateList(temp);
        }
    }

    private Activity mContext;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
