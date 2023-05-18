package com.hb.android.replugindemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();

        findViewById(R.id.install_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });
        findViewById(R.id.start_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForPlugin();
            }
        });
        findViewById(R.id.unInstall_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unInstall();
            }
        });
    }

    /**
     * 预加载插件
     */
    private void loadPlugin() {
        // 插件路径
        /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Oneplugin-debug.jar";
        Log.w("MainActivity hbtest", "loadPlugin path : " + path);
        PluginInfo pluginInfo = RePlugin.install(path);*/
        PluginInfo pluginInfo = RePlugin.getPluginInfo("dialogPlugin");
        Log.w("MainActivity hbtest", "loadPlugin: pluginInfo = " + pluginInfo);

        if (pluginInfo != null) {
            // 预加载插件
            boolean res = RePlugin.preload(pluginInfo);
            Log.w("MainActivity hbtest", "loadPlugin: preload=" + res);
            Toast.makeText(this, "install success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "加载失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 启动插件
     */
    private void startActivityForPlugin() {
        Intent intent = new Intent();
        //参数一： 插件包的包名
        //参数二:  插件包里面需要启动的类
        intent.setComponent(new ComponentName("com.hb.android.oneplugin", "com.hb.android.oneplugin.MainActivity"));
        RePlugin.startActivity(this, intent);
    }

    /**
     * 卸载插件
     */
    private void unInstall() {
        // 此处 dialogPlugin 是定义插件包里面build.gradle repluginPluginConfig#pluginName
        boolean result = RePlugin.uninstall("dialogPlugin");
        Log.w("MainActivity hbtest", "uninstall result : " + result);
    }

    /**
     * 检查并申请权限
     */
    private void checkAndRequestPermissions() {
        boolean requestReadExternalStoragePermission = false;
        boolean requestWriteExternalStoragePermission = false;

        int numPermissionsToRequest = 0;
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestReadExternalStoragePermission = true;
            numPermissionsToRequest++;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestWriteExternalStoragePermission = true;
            numPermissionsToRequest++;
        }

        // 如果已有权限则返回
        if (!requestReadExternalStoragePermission && !requestWriteExternalStoragePermission) {
            return;
        }

        String[] permissions = new String[numPermissionsToRequest];
        int permissionsRequestIndex = 0;
        if (requestReadExternalStoragePermission) {
            permissions[permissionsRequestIndex] = Manifest.permission.READ_EXTERNAL_STORAGE;
            permissionsRequestIndex++;
        }
        if (requestWriteExternalStoragePermission) {
            permissions[permissionsRequestIndex] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }
        Log.w("htest", "checkAndRequestPermissions: permissionsToRequest" + Arrays.toString(permissions));
        requestPermissions(permissions, 200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.w("htest", "onRequestPermissionsResult: permissions:" + Arrays.toString(permissions)
                + ",grantResults:" + Arrays.toString(grantResults));
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != result) {
                        new AlertDialog.Builder(this)
                                .setTitle("拒绝权限无法使用程序！")
                                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkAndRequestPermissions();
                                    }
                                })
                                .setNegativeButton("退出APP", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MainActivity.this.finish();
                                    }
                                }).show();
                    }
                }
            }
        }
    }

}
