package com.android.customdialogdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * AlertDialogUtil的工具类
 * <p>需要以下文件：
 * <p>    弹窗背景 drawable\custom_dialog_bg.xml,
 * <p>    弹窗按钮背景 drawable\custom_dialog_button_bg.xml,
 * <p>    弹窗的布局 layout\custom_alert_dialog.xml.
 */
public class AlertDialogUtil {

    public static void showAlertDialog(Context context, String title, String msg, OnDialogButtonClickListener dialogButtonClickListener) {
        showAlertDialog(context, title, msg, context.getString(android.R.string.cancel), context.getString(android.R.string.ok), dialogButtonClickListener);
    }

    public static void showAlertDialog(Context context, int titleResId, int msgResId, OnDialogButtonClickListener dialogButtonClickListener) {
        showAlertDialog(context, context.getString(titleResId), context.getString(msgResId), context.getString(android.R.string.cancel), context.getString(android.R.string.ok), dialogButtonClickListener);
    }

    public static void showAlertDialog(Context context, int titleResId, int msgResId, int cancelButtonTextResId, int okButtonTextResId, OnDialogButtonClickListener dialogButtonClickListener) {
        showAlertDialog(context, context.getString(titleResId), context.getString(msgResId), context.getString(cancelButtonTextResId), context.getString(okButtonTextResId), dialogButtonClickListener);
    }

    /**
     * 显示一个标准的AlertDialog
     *
     * @param context                   context
     * @param title                     标题
     * @param msg                       内容
     * @param cancelButtonText          取消(消极)按钮的文字
     * @param okButtonText              确认(积极)按钮的文字
     * @param dialogButtonClickListener 点击按钮的监听器
     */
    public static void showAlertDialog(Context context, String title, String msg, String cancelButtonText, String okButtonText, @Nullable OnDialogButtonClickListener dialogButtonClickListener) {
        try {
            AlertDialog mAlertDialog = new AlertDialog.Builder(context).create();
            View view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null, false);
            TextView dialogTitle = view.findViewById(R.id.dialog_title);
            TextView dialogMessage = view.findViewById(R.id.dialog_message);
            Button cancelButton = view.findViewById(R.id.dialog_cancel_button);
            Button okButton = view.findViewById(R.id.dialog_ok_button);
            if (title != null) {
                dialogTitle.setText(title);
            }
            if (msg != null) {
                dialogMessage.setText(msg);
            }
            if (cancelButtonText != null) {
                cancelButton.setText(cancelButtonText);
            }
            if (okButtonText != null) {
                okButton.setText(okButtonText);
            }
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialogButtonClickListener != null) {
                        dialogButtonClickListener.onCancelButtonClick();
                    }
                    mAlertDialog.dismiss();
                }
            });
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialogButtonClickListener != null) {
                        dialogButtonClickListener.onOkButtonClick();
                    }
                    mAlertDialog.dismiss();
                }
            });
            mAlertDialog.setView(view);
            mAlertDialog.show();
            float scale = context.getResources().getDisplayMetrics().density;
            int width = (int) (458 * scale + 0.5f);
            mAlertDialog.getWindow().setLayout(width, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
