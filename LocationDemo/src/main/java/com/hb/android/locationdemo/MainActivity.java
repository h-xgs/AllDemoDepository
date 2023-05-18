package com.hb.android.locationdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 申请权限
        if (LocationUtil.checkAndBuildPermissions(this)) {
            return;
        }
        showLocation();
    }

    private void showLocation() {
        List<Address> addresses = autoGetLocation(this);
        if (addresses != null) {
            ((TextView) findViewById(R.id.location_text)).setText(addresses.toString());
        }
    }

    @SuppressLint("MissingPermission")
    private List<Address> autoGetLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);

        String locationProvider = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
            LogUtil.d("定位方式GPS");
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
            LogUtil.d("定位方式Network");
        } else {
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            LogUtil.d("没有可用的位置提供器");
        }

        Location location = locationManager.getLastKnownLocation(locationProvider);
        LogUtil.d(String.valueOf(location));
        if (location != null) {
            LogUtil.d(location.toString());
            LogUtil.d("获取上次的位置-经纬度：" + location.getLongitude() + " " + location.getLatitude());
            return getAddress(location, context);
        } else {
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }

        return null;
    }

    //获取地址信息:城市、街道等信息
    // 省 address.adminArea    市 address.locality   区：address.subLocality
    private List<Address> getAddress(Location location, Context c) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(c, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                LogUtil.d("获取地址信息：" + result.toString());
                LogUtil.d("获取地址信息1：" + result.get(0).getSubLocality());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                //如果位置发生变化，重新显示地理位置经纬度
                Log.v("TAG", "监视地理位置变化-经纬度：" + location.getLongitude() + " " + location.getLatitude());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtil.w("onRequestPermissionsResult: permissions:" + Arrays.toString(permissions)
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
            AlertDialog mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle("应用缺少运行所需要的权限！")
                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create();
            // 不能取消弹窗
            mAlertDialog.setCancelable(false);
            mAlertDialog.show();
        } else {
            showLocation();
        }
    }

}
