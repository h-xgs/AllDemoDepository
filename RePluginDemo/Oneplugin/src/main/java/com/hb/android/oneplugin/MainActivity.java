package com.hb.android.oneplugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Toast", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    public void showDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        AlertDialog mAlertDialog = mBuilder.create();

        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null, false);
        // View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null, false);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        // dialogTitle.setText();
        // dialogTitle.setTextColor();
        view.findViewById(R.id.dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAlertDialog.dismiss();
            }
        });
        view.findViewById(R.id.dialog_ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.setView(view);
        // 点击弹窗外区域不消失
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

}