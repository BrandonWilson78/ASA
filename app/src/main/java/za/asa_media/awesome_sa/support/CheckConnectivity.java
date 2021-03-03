package za.asa_media.awesome_sa.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by matrix on 17/6/16.
 */
public class CheckConnectivity {

    private Context mContext;
    private boolean isConnected = false;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mActiveNetwork;

    public CheckConnectivity(Context mContext) {

        this.mContext = mContext;

        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        mActiveNetwork = mConnectivityManager.getActiveNetworkInfo();
    }


    //check for network is available or not true or false would be returned
    public Boolean isNetworkAvailable() {

        boolean isConnected = false;
        try {
            isConnected = mActiveNetwork != null && mActiveNetwork.isConnectedOrConnecting();
            return isConnected;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return isConnected;

    }
    public boolean isWiFi() {
        boolean isWiFi = false;
        try {
            isWiFi = mActiveNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            return isWiFi;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return isWiFi;
    }

    public boolean isMobileNetwork() {
        boolean isMobileNetwork = false;
        try {
            isMobileNetwork = mActiveNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            return isMobileNetwork;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return isMobileNetwork;
    }


    public void getType() {

        if (mActiveNetwork != null) {
            showToast("" + mActiveNetwork.getType());
        } else
            showToast("Your are Offline");
    }

    public void switchOnWifi() {

        try {

            WifiManager mainWifiObj = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            mainWifiObj.setWifiEnabled(true);

        } catch (SecurityException se) {
            se.printStackTrace();

        }

    }

    public void switchOffWifi() {

        try {

            WifiManager mainWifiObj = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            mainWifiObj.setWifiEnabled(false);


        } catch (SecurityException se) {
            se.printStackTrace();

        }

    }
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();


    }

}
