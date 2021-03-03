package za.asa_media.awesome_sa.modules_.place_;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.relex.circleindicator.CircleIndicator;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.place_.adapter.MyAdapter;

/**
 * Created by Snow-Dell-07 on 5/12/2017.
 */

public class UserDashboard extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager pager;
    private CircleIndicator indicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard_);


        indicator = (CircleIndicator) findViewById(R.id.indicator);
        pager = (ViewPager) findViewById(R.id.pager);

        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(mAdapter);

        indicator.setViewPager(pager);



    }


  /*  private int[] getNearbyPlacesDrawables() {

    int[] drawables = {
            R.mipmap.menu_icon_24, R.mipmap.menu_icon_43, R.mipmap.menu_icon_25,
            R.mipmap.menu_icon_21, R.mipmap.menu_icon_20, R.mipmap.menu_icon_44,
            R.mipmap.menu_icon_45, R.mipmap.menu_icon_42, R.mipmap.menu_icon_41,
            R.mipmap.menu_icon_40, R.mipmap.menu_icon_27, R.mipmap.menu_icon_19,
            R.mipmap.menu_icon_39, R.mipmap.menu_icon_15, R.mipmap.menu_icon_38,
            R.mipmap.menu_icon_26, R.mipmap.menu_icon_37, R.mipmap.menu_icon_16,
            R.mipmap.menu_icon_12, R.mipmap.menu_icon_11, R.mipmap.menu_icon_01,
            R.mipmap.menu_icon_36, R.mipmap.menu_icon_35, R.mipmap.menu_icon_10,
            R.mipmap.menu_icon_34, R.mipmap.menu_icon_09, R.mipmap.menu_icon_08,
            R.mipmap.menu_icon_32, R.mipmap.menu_icon_07, R.mipmap.menu_icon_32,
            R.mipmap.menu_icon_52,  R.mipmap.menu_icon_30,
            R.mipmap.menu_icon_29, R.mipmap.menu_icon_18, R.mipmap.menu_icon_05,
            R.mipmap.menu_icon_28, R.mipmap.menu_icon_02, R.mipmap.menu_icon_14,
            R.mipmap.menu_icon_23, R.mipmap.menu_icon_47, R.mipmap.menu_icon_48,
            R.mipmap.menu_icon_49, R.mipmap.menu_icon_50, R.mipmap.menu_icon_51,
            R.mipmap.menu_icon_52
    };

    return drawables;
}


    private String[] getNearByPlacesNames() {

        String nearByPlaces[] = {
                "Things to do", "Attractions", "Cheap Flights",
                "Car Rentals", "Restaurants", "Sushi",
                "Pizza", "Vegetarian", "Coffee",
                "Burger Joints", "Wine Farms", "Craft Beer",
                "Safari Parks", "Nightlife", "Art & Crafts",
                "Hotles", "Events", "Shopping",
                "Kids Activities", "Transport & Taxi", "Golf Vanues",
                "Tennis", "Yoga Studios", "Conference Venues",
                "Travel & Tours", "Beauty Therapist", "Spas",
                "Mens Barber", "HomeFragment Improvers", "Handyman",
                "Electricians", "Plumbers", "Banks",
                "ATM Machines", "Embassies", "Currency Converter",
                "Nearby", "Hospitals", "Wedding Venues",
                "Ticket Purchase", "Movie Theaters", "Pharmacies",
                "Gym", "Retail", "Gas Stations"
        };
        return nearByPlaces;

    }*/
}