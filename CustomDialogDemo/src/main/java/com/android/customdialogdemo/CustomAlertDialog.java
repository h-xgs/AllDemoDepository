package com.android.customdialogdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.StringRes;

public class CustomAlertDialog extends AlertDialog {

    private final Context c;
    private final AlertDialog alertDialog;
    private View layout;
    private final TextView dialogTitle;
    private final TextView dialogMessage;
    private final Button cancelButton;
    private final Button okButton;

    protected CustomAlertDialog(Context context) {
        super(context);
        c = context;
        alertDialog = new AlertDialog.Builder(context).create();
        layout = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null, false);
        dialogTitle = layout.findViewById(R.id.dialog_title);
        dialogMessage = layout.findViewById(R.id.dialog_message);
        cancelButton = layout.findViewById(R.id.dialog_cancel_button);
        okButton = layout.findViewById(R.id.dialog_ok_button);
        alertDialog.setView(layout);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);
    }

    protected CustomAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        c = context;
        alertDialog = new AlertDialog.Builder(context).create();
        layout = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null, false);
        dialogTitle = layout.findViewById(R.id.dialog_title);
        dialogMessage = layout.findViewById(R.id.dialog_message);
        cancelButton = layout.findViewById(R.id.dialog_cancel_button);
        okButton = layout.findViewById(R.id.dialog_ok_button);
        alertDialog.setView(layout);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);
    }

    @Override
    public void setTitle(int titleId) {
        dialogTitle.setText(c.getString(titleId));
    }

    public CustomAlertDialog setTitle(String title) {
        dialogTitle.setText(title);
        return this;
    }

    public CustomAlertDialog setMessage(String message) {
        dialogMessage.setText(message);
        return this;
    }

    public CustomAlertDialog setMessage(@StringRes int messageResId) {
        dialogMessage.setText(c.getString(messageResId));
        return this;
    }

    public CustomAlertDialog setPositiveButton(String text, DialogInterface.OnClickListener listener) {
        okButton.setText(text);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(alertDialog, DialogInterface.BUTTON_POSITIVE);
                }
                alertDialog.dismiss();
            }
        });
        return this;
    }

    public CustomAlertDialog setNegativeButton(String text, DialogInterface.OnClickListener listener) {
        cancelButton.setText(text);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(alertDialog, DialogInterface.BUTTON_NEGATIVE);
                }
                alertDialog.dismiss();
            }
        });
        return this;
    }

    @Override
    public void setView(View view) {
        layout = view;
    }

    @Override
    public void show() {
        alertDialog.show();
    }

    public interface OnDialogButtonClickListener {
        /**
         * 当点击弹窗的"取消"按钮时调用此方法
         */
        void onCancelButtonClick();

        /**
         * 当点击弹窗的"确认"按钮时调用此方法
         */
        void onOkButtonClick();
    }
}
