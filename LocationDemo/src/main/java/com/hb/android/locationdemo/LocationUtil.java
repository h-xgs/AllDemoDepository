package com.hb.android.locationdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.Arrays;

public class LocationUtil {

    public static boolean checkAndBuildPermissions(Activity activity) {
        boolean requestInternetPermission = false;
        boolean requestFineLocationPermission = false;
        boolean requestCoarseLocationPermission = false;

        int numPermissionsToRequest = 0;
        if (activity.checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestInternetPermission = true;
            numPermissionsToRequest++;
        }
        if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestFineLocationPermission = true;
            numPermissionsToRequest++;
        }
        if (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestCoarseLocationPermission = true;
            numPermissionsToRequest++;
        }

        if (!requestInternetPermission && !requestFineLocationPermission && !requestCoarseLocationPermission) {
            return false;
        }

        String[] permissionsToRequest = new String[numPermissionsToRequest];
        int permissionsRequestIndex = 0;
        if (requestInternetPermission) {
            permissionsToRequest[permissionsRequestIndex] = Manifest.permission.INTERNET;
            permissionsRequestIndex++;
        }
        if (requestFineLocationPermission) {
            permissionsToRequest[permissionsRequestIndex] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissionsRequestIndex++;
        }
        if (requestCoarseLocationPermission) {
            permissionsToRequest[permissionsRequestIndex] = Manifest.permission.ACCESS_COARSE_LOCATION;
        }
        LogUtil.w("checkAndBuildPermissions: permissionsToRequest" + Arrays.toString(permissionsToRequest));
        activity.requestPermissions(permissionsToRequest, 200);
        return true;
    }

}
