package za.asa_media.awesome_sa.modules_.registered_users.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import za.asa_media.awesome_sa.modules_.registered_users.fragment.FragmentBusinessView;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.MyAdsFragment;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.MyProfileFragment;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.NotificationsFragment;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.PostNewAdFragment;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.TransactionHistoryFragment;

import static za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard.navItemIndex;

/**
 * Created by Snow-Dell-05 on 5/8/2017.
 */

public class DashboardPresenter {

    // tags used to attach the fragments
    public static final String TAG_HOME = "home";
    public static final String TAG_POST_NEW_AD = "post_new_ad";
    public static final String TAG_MY_ADS = "my_ads";
    public static final String TAG_NOTIFICATIONS = "notifications";
    public static final String TAG_TRANSACTION_HISTORY = "transaction_history";
    public static final String TAG_TERMS_AND_CONDITIONS = "terms_conditons";
    public static final String TAG_PROFILE = "profile";
    public static String CURRENT_TAG = TAG_HOME;

    private Activity mContext;

    public DashboardPresenter(Activity mContext) {
        this.mContext = mContext;

    }

    public Fragment getHomeFragment() {
        Fragment mFragment = null;
        switch (navItemIndex) {
            case 0:
                // home
                mFragment = new FragmentBusinessView();
                break;

            case 1:
                // my profile
                mFragment = new MyProfileFragment();
                break;
            case 2:
                // post new ads
                mFragment = new PostNewAdFragment();
                break;

            case 3:
                // my ads fragment
                mFragment = new MyAdsFragment();
                break;
            case 4:
                // notifications fragment
                mFragment = new NotificationsFragment();
                break;

            case 5:

                // notifications fragment
                mFragment = new TransactionHistoryFragment();
                break;
            default:
                mFragment = new FragmentBusinessView();
                break;
        }
        return mFragment;
    }
}
