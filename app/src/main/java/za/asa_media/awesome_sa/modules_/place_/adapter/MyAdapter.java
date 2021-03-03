package za.asa_media.awesome_sa.modules_.place_.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import za.asa_media.awesome_sa.modules_.place_.fragment.FragmentFirstScreen;
import za.asa_media.awesome_sa.modules_.place_.fragment.FragmentSecondScreen;
import za.asa_media.awesome_sa.modules_.place_.fragment.FragmentThirdScreen;


/**
 * Created by Snow-Dell-07 on 5/12/2017.
 */

public class MyAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public MyAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentFirstScreen();
            case 1:
                return new FragmentSecondScreen();
            case 2:
                return new FragmentThirdScreen();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
