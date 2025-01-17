package com.boiqin.skindemo.flycotablayout.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.boiqin.skindemo.R;
import com.boiqin.skindemo.flycotablayout.utils.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;

import skin.support.flycotablayout.widget.SkinMsgView;

public class SegmentTabActivity extends FlycoActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "消息"};
    private String[] mTitles_2 = {"首页", "消息", "联系人"};
    private String[] mTitles_3 = {"首页", "消息", "联系人", "更多"};
    private View mDecorView;
    private SegmentTabLayout mTabLayout_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_tab);

        for (String title : mTitles_3) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }

        for (String title : mTitles_2) {
            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }

        mDecorView = getWindow().getDecorView();

        SegmentTabLayout tabLayout_1 = ViewFindUtils.find(mDecorView, R.id.t3_1);
        SegmentTabLayout tabLayout_2 = ViewFindUtils.find(mDecorView, R.id.t3_2);
        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.t3_3);
        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.t3_4);
        SegmentTabLayout tabLayout_5 = ViewFindUtils.find(mDecorView, R.id.t3_5);

        tabLayout_1.setTabData(mTitles);
        tabLayout_2.setTabData(mTitles_2);
        tl_3();
        tabLayout_4.setTabData(mTitles_2, this, R.id.fl_change, mFragments2);
        tabLayout_5.setTabData(mTitles_3);

        //显示未读红点
        tabLayout_1.showDot(2);
        MsgView rtv_1_2 = tabLayout_1.getMsgView(2);
        if (rtv_1_2 != null) {
            if (rtv_1_2 instanceof SkinMsgView) {
                ((SkinMsgView) rtv_1_2).setBackgroundColorResource(R.color.msg_background_color);
            }
        }
        tabLayout_2.showDot(2);
        MsgView rtv_2_2 = tabLayout_2.getMsgView(2);
        if (rtv_2_2 != null) {
            if (rtv_2_2 instanceof SkinMsgView) {
                ((SkinMsgView) rtv_2_2).setBackgroundColorResource(R.color.msg_background_color);
            }
        }
        mTabLayout_3.showDot(1);
        tabLayout_4.showDot(1);
        MsgView rtv_4_1 = tabLayout_4.getMsgView(1);
        if (rtv_4_1 != null) {
            if (rtv_4_1 instanceof SkinMsgView) {
                ((SkinMsgView) rtv_4_1).setBackgroundColorResource(R.color.msg_background_color);
            }
        }

        //设置未读消息红点
        mTabLayout_3.showDot(2);
        MsgView rtv_3_2 = mTabLayout_3.getMsgView(2);
        if (rtv_3_2 != null) {
            if (rtv_3_2 instanceof SkinMsgView) {
                ((SkinMsgView) rtv_3_2).setBackgroundColorResource(R.color.msg_background_color);
                ((SkinMsgView) rtv_3_2).setStrokeColorResource(R.color.msg_stroke_color);
            } else {
                rtv_3_2.setBackgroundColor(Color.parseColor("#6D8FB0"));
            }
        }
    }

    private void tl_3() {
        final ViewPager vp_3 = ViewFindUtils.find(mDecorView, R.id.vp_2);
        vp_3.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTabLayout_3.setTabData(mTitles_3);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(1);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_3[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
