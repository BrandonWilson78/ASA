package za.asa_media.awesome_sa.support.check_permissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import java.io.IOException;

/**
 * Created by Snow-Dell-07 on 3/27/2017.
 */

public class CheckNetworkState {

    Activity c;

    public CheckNetworkState(Activity c) {
        this.c = c;

    }

    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public void alert() {
        new AlertDialog.Builder(c)
                .setTitle("")
                .setMessage("Sorry! Not connected to internet")

                .setPositiveButton("Accept".toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        c.startActivity(new Intent(Settings.ACTION_SETTINGS));
                        dialog.dismiss();

                    }
                }).setNegativeButton("Cancel".toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                c.finish();
                c.moveTaskToBack(true);

                dialog.cancel();
            }
        }).show();

    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

}
