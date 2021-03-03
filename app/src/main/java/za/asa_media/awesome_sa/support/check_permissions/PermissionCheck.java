package za.asa_media.awesome_sa.support.check_permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Snow-Dell-07 on 3/27/2017.
 */

public class PermissionCheck {
    public static int PERMISSION_CODE = 101;
    Activity c;

    public PermissionCheck(Activity c) {
        this.c = c;
    }


    public boolean check() {
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION };

        if (!hasPermissions(c, PERMISSIONS)) {
            ActivityCompat.requestPermissions(c, PERMISSIONS, PERMISSION_CODE);
            return true;
        }
        return false;
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
