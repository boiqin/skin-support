package com.boiqin.skindemo.tab;

import android.os.Bundle;
import android.view.Menu;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.boiqin.skindemo.BaseActivity;
import com.boiqin.skindemo.R;
import com.boiqin.skindemo.tab.fragment.FirstFragment;
import com.boiqin.skindemo.tab.fragment.LastFragment;
import com.boiqin.skindemo.tab.fragment.SFragment;
import com.boiqin.skindemo.tab.fragment.TFragment;
import com.boiqin.skindemo.tab.fragment.TabFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import skin.support.annotation.Skinable;
import skin.support.widget.SkinCompatSupportable;

/**
 * Created by ximsfei on 2017/1/9.
 */

@Skinable
public class MainActivity extends BaseActivity implements SkinCompatSupportable {
    private TabFragmentPagerAdapter mTabFragmentPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        configFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    private void configFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(new FirstFragment());
        list.add(new SFragment());
        list.add(new TFragment());
        list.add(new LastFragment());
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), list));
        List<String> listTitle = new ArrayList<>();
        listTitle.add("系统组件");
        listTitle.add("自定义View");
        listTitle.add("List");
        listTitle.add("第三方库控件");
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list, listTitle);
        viewPager.setAdapter(mTabFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void applySkin() {

    }
}
