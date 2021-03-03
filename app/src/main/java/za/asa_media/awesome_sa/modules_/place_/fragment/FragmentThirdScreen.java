package za.asa_media.awesome_sa.modules_.place_.fragment;

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

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.NearByPlacesData;
import za.asa_media.awesome_sa.modules_.place_.adapter.UserDashFragThird;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-07 on 5/12/2017.
 */

public class FragmentThirdScreen extends Fragment {

    private RecyclerView mRecylerViewDashboard;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mView = null;
    private EditText mEditTextSearch;
    private UiHandleMethods uihandle;
    private List<NearByPlacesData> mListData;
    private UserDashFragThird mdap;
    private boolean mFlagCheck = false;

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
            mView = inflater.inflate(R.layout.fragment_user_screen_3, container, false);
        }

        init(mView);
        return mView;
    }

    private void init(View v) {
        mEditTextSearch = getActivity().findViewById(R.id.edittext_search);
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
        mdap = new UserDashFragThird(getActivity(), mListData);
        mRecylerViewDashboard.setAdapter(mdap);

    /*    mEditTextSearch.addTextChangedListener(new TextWatcher() {
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
                R.mipmap.menu_icon_07, R.mipmap.menu_icon_31,
                R.mipmap.ic_plumber, R.mipmap.menu_icon_32,
                R.mipmap.menu_icon_30, R.mipmap.menu_icon_29, R.mipmap.menu_icon_18,
                R.mipmap.menu_icon_02, R.mipmap.menu_icon_48, R.mipmap.menu_icon_14,
                R.mipmap.menu_icon_23, R.mipmap.menu_icon_47,
                R.mipmap.menu_icon_49, R.mipmap.menu_icon_51

        };
        return iconSet;
    }


    private String[] getNearByPlacesNames() {
        String[] nameSet = {"Home makers & improvers", "Electricians", "Plumbers","Handyman", "Banks",
                "ATM Machines", "Embassies", "Hospitals", "Pharmacies", "Wedding Venues",
                "Ticket Purchase", "Movie Theaters",
                "Gym", "Gas Stations"};
        return nameSet;

    }

    void filter(String text) {

        if (!mFlagCheck) {
            mdap = new UserDashFragThird(getActivity(), mListData);
            mRecylerViewDashboard.setAdapter(mdap);
            mFlagCheck = true;
        }

        List<NearByPlacesData> temp = new ArrayList<>();
        int textLength = text.length();


        if (TextUtils.isEmpty(text)) {
            mdap = new UserDashFragThird(getActivity(), mListData);
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

}