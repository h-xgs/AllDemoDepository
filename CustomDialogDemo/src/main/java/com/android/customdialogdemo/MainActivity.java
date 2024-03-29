package com.android.customdialogdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_custom_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        findViewById(R.id.show_custom_dialog2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomAlertDialog(MainActivity.this)
                        .setTitle("标题")
                        .setMessage("内容")
                        .setNegativeButton("消极按钮", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("积极按钮", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击了确认按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle)
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("标题")
                        .setMessage("内容")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", null)
                        .show();
            }
        });
        findViewById(R.id.show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Toast", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog() {
        AlertDialogUtil.showAlertDialog(this, "标题", "内容", new AlertDialogUtil.OnDialogButtonClickListener() {
            @Override
            public void onCancelButtonClick() {
                Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOkButtonClick() {
                Toast.makeText(MainActivity.this, "点击了确认按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }

}