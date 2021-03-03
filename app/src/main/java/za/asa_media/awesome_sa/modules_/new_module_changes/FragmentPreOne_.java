package za.asa_media.awesome_sa.modules_.new_module_changes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 18-Apr-18.
 */

public class FragmentPreOne_  extends Fragment {

    private View mView = null;
    // private EditText mEditTextSearch;
    private UiHandleMethods uihandle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uihandle = new UiHandleMethods(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_pre_one, container, false);
        }

        init(mView);
        return mView;
    }

    private void init(View v) {


    }


}
