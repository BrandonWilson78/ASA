package za.asa_media.awesome_sa.support.fusedlocationapi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import za.asa_media.awesome_sa.modules_.login_.PreLoginActivity;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;


/**
 * Created by Snow-Dell-07 on 4/20/2017.
 */

public class LocationFinder implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Activity mContext;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient = null;
    private SessionCityOculus mSession;
    private UiHandleMethods uihandle;

    public LocationFinder(Activity mContext) {
        this.mContext = mContext;
        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);
        initClient();
    }

    public void initClient() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //    InitialValueSetUp.mConectivityCheck = true;

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.e("LCheck", "" + mLastLocation);


        if (mLastLocation != null) {

            //  pdLocationDialog.hide();
            ReservedLocation.getSingletonInstance().setCurret_lat(Double.toString(mLastLocation.getLatitude()));
            ReservedLocation.getSingletonInstance().setCurrent_lng(Double.toString(mLastLocation.getLongitude()));

            Log.e("Lon", "" + ReservedLocation.getSingletonInstance().getCurrent_lng());
            Log.e("Lat", "" + ReservedLocation.getSingletonInstance().getCurret_lat());


            if (PreLoginActivity.mLocationDialog.isShowing()) {
                PreLoginActivity.mLocationDialog.dismiss();
            }

            // check for login access
            boolean isLogin = false;
            try {
                isLogin = mSession.isLogged();
                // userType = csfm.getUserType();
                if (isLogin) {
                    uihandle.explicitIntent(LoggedInUserDashboard.class);
                    mContext.finish();

                }
            } catch (Exception e) {
    //   pdLocationDialog.hide();

           }
        } else {
            if (PreLoginActivity.mLocationDialog.isShowing()) {
                PreLoginActivity.mLocationDialog.dismiss();
            }

            //     startApiClient();
        }
    }

    public void refreshScreen() {

        Intent intent = mContext.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        mContext.finish();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(mContext, "Connection Failed: " + connectionResult, Toast.LENGTH_SHORT).show();
    }

    //  private ProgressDialog pdLocationDialog;
    public void startApiClient() {

        mGoogleApiClient.connect();
    /*    pdLocationDialog = ProgressDialog.show(mContext,"Please wait","Location is being fetched",false,false);*/
        Log.d("ajeeb", "onCall");
    }

    public void stopApiClient() {
        mGoogleApiClient.disconnect();

    }


    @Override
    public void onLocationChanged(Location location) {

        ReservedLocation.getSingletonInstance().setCurret_lat(Double.toString(location.getLatitude()));
        ReservedLocation.getSingletonInstance().setCurrent_lng(Double.toString(location.getLongitude()));
    }
}
