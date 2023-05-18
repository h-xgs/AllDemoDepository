package com.hb.example.viewpager2demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import com.hb.example.viewpager2demo.adapter.MyFragmentAdapter;
import com.hb.example.viewpager2demo.adapter.MyViewAdapter;
import com.hb.example.viewpager2demo.fragment.TextFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 page2 = findViewById(R.id.page);
        // 设置成竖直滑动
        page2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        /*// 向Fragment传递数据方法一（不建议使用构造方法，否则横竖屏切换会空指针异常）
        Bundle bundle = new Bundle();
        bundle.putString("title", "a");
        TextFragment textFragment = new TextFragment();
        textFragment.setArguments(bundle);*/
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TextFragment.newInstance("页面1"));
        fragments.add(TextFragment.newInstance("页面2"));
        fragments.add(TextFragment.newInstance("页面3"));
        page2.setAdapter(new MyFragmentAdapter(this, fragments));

        /*List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.DKGRAY);
        colors.add(Color.LTGRAY);
        page2.setAdapter(new MyViewAdapter(colors));*/

        // 设置滑动监听
        page2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            /**
             * 页面滑动状态停止前一直调用
             * @param position 当前点击滑动页面的位置
             * @param positionOffset 当前页面偏移的百分比
             * @param positionOffsetPixels 当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // LogUtil.d(TAG + "vp：滑动中 —-> position:" + position + "   positionOffset:" + positionOffset + "   positionOffsetPixels:" + positionOffsetPixels);
            }

            /**
             * 滑动后显示的页面和滑动前不同，调用
             * @param position 选中显示页面的位置
             */
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // LogUtil.e(TAG + "vp：显示页改变 —-> postion:" + position + " " + countyNames.get(position));
            }

            /**
             * 页面状态改变时调用
             * SCROLL_STATE_IDLE：空闲状态
             * SCROLL_STATE_DRAGGING：滑动状态
             * SCROLL_STATE_SETTLING：滑动后滑翔的状态
             * @param state 当前页面的状态
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        // LogUtil.d(TAG + "vp：状态改变 —-> SCROLL_STATE_IDLE ==== 静止状态");
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        // LogUtil.d(TAG + "vp：状态改变 —-> SCROLL_STATE_DRAGGING == 滑动状态");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        // LogUtil.d(TAG + "vp：状态改变 —-> SCROLL_STATE_SETTLING == 滑翔状态");
                        break;
                }
            }
        });
    }

}
