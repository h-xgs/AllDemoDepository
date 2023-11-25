package com.hb.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
3.8.3 自定义组合控件
 */
public class TitleBar extends RelativeLayout {

    private ImageView iv_titlebar_left;
    private ImageView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private RelativeLayout layout_titlebar_rootlayout;
    private int mColor = Color.BLUE;
    private int mTextColor = Color.WHITE;
    private String titleName = "";

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mColor = mTypedArray.getColor(R.styleable.TitleBar_title_bg, Color.BLUE);
        mTextColor = mTypedArray.getColor(R.styleable.TitleBar_title_text_color, Color.WHITE);
        titleName = mTypedArray.getString(R.styleable.TitleBar_title_text);
        mTypedArray.recycle();
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_customtitle, this, true);
        layout_titlebar_rootlayout = findViewById(R.id.layout_titlebar_rootlayout);
        iv_titlebar_left = findViewById(R.id.iv_titlebar_left);
        tv_titlebar_title = findViewById(R.id.tv_titlebar_title);
        iv_titlebar_right = findViewById(R.id.iv_titlebar_right);
        //设置背景颜色
        layout_titlebar_rootlayout.setBackgroundColor(mColor);
        //设置标题文字颜色
        tv_titlebar_title.setTextColor(mTextColor);
        //设置标题文字
        tv_titlebar_title.setText(titleName);
        iv_titlebar_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "左", Toast.LENGTH_SHORT).show();
            }
        });
        iv_titlebar_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "右", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setTitle(String tName) {
        if (!TextUtils.isEmpty(tName)) {
            tv_titlebar_title.setText(tName);
        }
    }

    public void setLeftListener(OnClickListener onClickListener) {
        iv_titlebar_left.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        iv_titlebar_right.setOnClickListener(onClickListener);
    }

}
