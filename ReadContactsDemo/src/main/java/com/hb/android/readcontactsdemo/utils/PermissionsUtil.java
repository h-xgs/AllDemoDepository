package com.hb.android.readcontactsdemo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Arrays;

public class PermissionsUtil {

    /**
     * 申请权限
     *
     * @param activity       传入需要授权的activity即可
     * @param permissionList 需要申请的权限的数组
     * @return 如果已经获得权限则返回true，没有授权返回false
     */
    public static boolean checkAndApplyPermissions(Activity activity, String[] permissionList) {
        boolean isHasPermissions = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int numPermissionsNeedRequest = 0;
            String[] permissionsNeedRequest = null;
            for (String s : permissionList) {
                if (activity.checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
                    isHasPermissions = false;
                    numPermissionsNeedRequest++;
                    permissionsNeedRequest = new String[numPermissionsNeedRequest];
                    permissionsNeedRequest[numPermissionsNeedRequest - 1] = s;
                }
            }
            if (permissionsNeedRequest != null) {
                activity.requestPermissions(permissionsNeedRequest, 1);
            }
        }
        return isHasPermissions;
    }

    /**
     * 检查是否已经授权，在应用的启动类的onRequestPermissionsResult方法中调用
     *
     * @param activity     主activity
     * @param permissions  申请的权限 permissions
     * @param grantResults 申请结果 grantResults
     * @return 如果已经获得权限则返回true，没有授权返回false
     */
    public static boolean checkAuthorized(Activity activity, String[] permissions, int[] grantResults) {
        LogUtil.logW(activity, "onRequestPermissionsResult: permissions:" + Arrays.toString(permissions)
                + ",grantResults:" + Arrays.toString(grantResults));
        boolean isNeedBuildPermissions = false;
        if (grantResults.length > 0) {
            for (int result : grantResults) {
                if (PackageManager.PERMISSION_GRANTED != result) {
                    isNeedBuildPermissions = true;
                    break;
                }
            }
        }
        if (isNeedBuildPermissions) {
            AlertDialog mAlertDialog = new AlertDialog.Builder(activity)
                    .setTitle("应用缺少运行所需要的权限！")
                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    }).create();
            // 不能取消弹窗
            mAlertDialog.setCancelable(false);
            mAlertDialog.show();
            return false;
        } else {
            return true;
        }
    }

}
