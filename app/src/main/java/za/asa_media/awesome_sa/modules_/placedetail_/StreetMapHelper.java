package za.asa_media.awesome_sa.modules_.placedetail_;

import android.app.Activity;
import android.widget.FrameLayout;

import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;

import za.asa_media.awesome_sa.R;

/**
 * Created by Snow-Dell-05 on 5/19/2017.
 */

public class StreetMapHelper {

    private Activity mContext;
    private SupportMapFragment mMapFragment;
    private StreetViewPanoramaFragment mStreetFragment;
    private FrameLayout mMapLayout, StreetLayout;

    public StreetMapHelper(Activity mContext) {
        this.mContext = mContext;
    }

    public void initFragment() {
        mStreetFragment = (StreetViewPanoramaFragment) mContext.getFragmentManager().findFragmentById(R.id.street_view_fragment);
        mMapFragment = (SupportMapFragment) ((PlaceDetailActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.map);


    }

    public void performOperations() {


    }










/*
    latLng = new LatLng(InitialValueSetUp.lat, InitialValueSetUp.lng);
    //final LatLng latLng1 = new LatLng(-33.87365, 151.20689);
        streetViewPanorama.setPosition(latLng);
        streetViewPanorama.setZoomGesturesEnabled(true);

        streetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
        @Override
        public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
            mUiHandleMethods.showToast("c1");
            //hide map fragment
               /* mFrameMap.setVisibility(View.VISIBLE);
                //   mFrameStreet.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(String.valueOf(InitialValueSetUp.lat)) && !TextUtils.isEmpty(String.valueOf(InitialValueSetUp.lng))) {
                    mSupportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
                    mSupportMapFragment.getMapAsync(PlaceDetailActivity.this);
                } else {
                    mUiHandleMethods.showToast("Sorry, Google has not get location!");
                }
            if (streetViewPanoramaLocation != null && streetViewPanoramaLocation.links != null) {
                mUiHandleMethods.showToast("c2");
                mFrameStreet.setVisibility(View.VISIBLE);
                //  mFrameMap.setVisibility(View.GONE);
                //  mUiHandleMethods.showToast("Inside conditional body");
                // streetViewPanorama.setPosition(latLng1);

            } else {
                mUiHandleMethods.showToast("c3");

                //   mUiHandleMethods.showToast("Street view not available");
                   mFrameMap.setVisibility(View.VISIBLE);
                    mFrameStreet.setVisibility(View.GONE);

                    mSupportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
                    mSupportMapFragment.getMapAsync(PlaceDetailActivity.this);

            }
        }
    });

    */


}




