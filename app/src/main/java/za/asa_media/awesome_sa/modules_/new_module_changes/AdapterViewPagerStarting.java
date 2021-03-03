package za.asa_media.awesome_sa.modules_.new_module_changes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Snow-Dell-05 on 27-Mar-18.
 */

public class AdapterViewPagerStarting extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public AdapterViewPagerStarting(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new FragmentPreOne_();
            case 1:
                return new FragmentPreOne();
            case 2:
                return new FragmentPreSecond();
            case 3:
                return new FragmentPreTwo_();


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

