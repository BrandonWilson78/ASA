package za.asa_media.awesome_sa.modules_.place_.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 6/2/2017.
 */

public class DialogFragmentForGetFavourites extends DialogFragment {
    // for favourite list

    private Activity mContext;
    private UiHandleMethods uiHandle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        uiHandle = new UiHandleMethods(activity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_layout_attach_customer, container, false);
        // View mView = inflater.inflate(R.layout.test_2, container, false);


        //to hide keyboard when showing dialog fragment
        getDialog().getWindow().setWindowAnimations(R.style.DialogAnimation);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getDialog().setTitle("Favourite Places");


        // put adapter
        // get favourite items


        //   mSearchInterface.getFavouritePlace(mView, getDialog());

        return mView;
    }


}

