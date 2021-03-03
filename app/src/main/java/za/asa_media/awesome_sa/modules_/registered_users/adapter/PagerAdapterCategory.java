package za.asa_media.awesome_sa.modules_.registered_users.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import za.asa_media.awesome_sa.modules_.registered_users.fragment.FragmentOneReg;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.FragmentThreeReg;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.FragmentTwoReg;

/**
 * Created by Snow-Dell-05 on 6/17/2017.
 */

public class PagerAdapterCategory extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapterCategory(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOneReg();
            case 1:
                return new FragmentTwoReg();
            case 2:
                return new FragmentThreeReg();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
