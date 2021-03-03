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

import java.util.ArrayList;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.NearByPlacesData;
import za.asa_media.awesome_sa.modules_.place_.adapter.UserDashFragSecond;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-07 on 5/12/2017.
 */
public class FragmentSecondScreen extends Fragment {

    private RecyclerView mRecylerViewDashboard;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mView = null;
    private EditText mEditTextSearch;
    private UiHandleMethods uihandle;
    private List<NearByPlacesData> mListData;
    private UserDashFragSecond mdap;
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
            mView = inflater.inflate(R.layout.fragment_user_screen_2, container, false);
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
        mdap = new UserDashFragSecond(getActivity(), mListData);
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
                R.mipmap.menu_icon_38, R.mipmap.menu_icon_37, R.mipmap.menu_icon_52, R.mipmap.menu_icon_16,
                R.mipmap.menu_icon_organic, R.mipmap.menu_icon_12, R.mipmap.menu_icon_11, R.mipmap.menu_icon_01,
                R.mipmap.menu_icon_36, R.mipmap.menu_icon_35, R.mipmap.menu_icon_10,
                R.mipmap.menu_icon_09, R.mipmap.menu_icon_34, R.mipmap.menu_icon_08, R.mipmap.menu_icon_33
        };
        return iconSet;
    }

    private String[] getNearByPlacesNames() {
        String[] nameSet = {
                "Art & Crafts", "Events & Concerts", "Retail therapy",
                "Shopping malls", "Organic shops",
                "Kids Activities", "Transport & Taxi", "Golf Venues",
                "Tennis courts", "Yoga Studios", "Conference Venues",
                "Travel & Tours", "Hair & Beauty", "Spas",
                "Men's Grooming & Barber"
        };

        return nameSet;
    }

    void filter(String text) {

        if (!mFlagCheck) {
            mdap = new UserDashFragSecond(getActivity(), mListData);
            mRecylerViewDashboard.setAdapter(mdap);
            mFlagCheck = true;
        }

        List<NearByPlacesData> temp = new ArrayList<>();
        int textLength = text.length();


        if (TextUtils.isEmpty(text)) {
            mdap = new UserDashFragSecond(getActivity(), mListData);
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