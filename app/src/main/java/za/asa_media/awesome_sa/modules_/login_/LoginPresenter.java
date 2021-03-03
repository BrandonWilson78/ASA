package za.asa_media.awesome_sa.modules_.login_;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import za.asa_media.awesome_sa.support.check_permissions.CheckNetworkState;

/**
 * Created by Snow-Dell-05 on 5/10/2017.
 */

public class LoginPresenter {

    private Activity mContext;
    private LocationManager manager = null;

    public LoginPresenter(Activity mContext) {
        this.mContext = mContext;

        manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean onStartCheck() {

        if (!new CheckNetworkState(mContext).checkNetwork()) {                   //weather provider is connected
            new CheckNetworkState(mContext).alert();

        } else if (!new CheckNetworkState(mContext).isOnline()) {                 //check provider provides data

            Toast.makeText(mContext, "Internet not working!", Toast.LENGTH_SHORT).show();

            // Ok about internet
            // Check GPS ENABLED

        } else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            new AlertDialog.Builder(mContext)
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            mContext.finish();
                            mContext.moveTaskToBack(true);

                        }
                    }).show();

        } else {
            //Everything is enabled
            return true;
        }
        return false;
    }


}
