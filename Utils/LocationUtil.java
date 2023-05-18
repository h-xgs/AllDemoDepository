package com.hb.android.tapplication.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * 安卓原生获取位置信息的工具类，需要申请权限，尽量不要直接调用此工具类的方法;
 * <p> autoGetLocation()，获取位置信息
 * <p><!-- 申请网络权限 -->
 * <p><uses-permission android:name="android.permission.INTERNET" />
 * <p><!-- 粗略的位置权限 -->
 * <p><uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 * <p><!-- 精确的位置权限 -->
 * <p><uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class LocationUtil {

    private static final String TAG = "LocationUtil测试";
    private static LocationManager locationManager;

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static LocationListener getLocationListener() {
        return locationListener;
    }

    /**
     * 获取位置，获取经纬度
     *
     * @param context context
     * @return List<Address>
     */
    @SuppressLint("MissingPermission")
    public static List<Address> autoGetLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);

        String locationProvider = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
            Log.d(TAG, "定位方式GPS");
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
            Log.d(TAG, "定位方式Network");
        } else {
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "没有可用的位置提供器");
        }

        Location location = locationManager.getLastKnownLocation(locationProvider);
        Log.d(TAG, String.valueOf(location));
        if (location != null) {
            Log.d(TAG, location.toString());
            Log.d(TAG, "获取上次的位置-经纬度：" + location.getLongitude() + " " + location.getLatitude());
            return getAddress(location, context);
        } else {
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
        return null;
    }

    /**
     * 获取地址信息:城市、街道等信息
     * 省 address.adminArea    市 address.locality   区：address.subLocality
     */
    private static List<Address> getAddress(Location location, Context c) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(c, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Log.d(TAG, "获取地址信息：" + result.toString());
                Log.d(TAG, "省：" + result.get(0).getAdminArea());
                Log.d(TAG, "市：" + result.get(0).getLocality());
                Log.d(TAG, "区/县：" + result.get(0).getSubLocality());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 位置变化的监听，需要在onDestroy里面使用locationManager.removeUpdates(locationListener);注销
     */
    private static final LocationListener locationListener = new LocationListener() {
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
                Log.v(TAG, "监视地理位置变化-经纬度：" + location.getLongitude() + " " + location.getLatitude());
            }
        }
    };

}
