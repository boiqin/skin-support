package com.boiqin.skindemo.mdtab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.boiqin.skindemo.BaseActivity;
import com.boiqin.skindemo.R;
import com.boiqin.skindemo.settings.SettingsActivity;
import com.boiqin.skindemo.tab.fragment.TabFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ximsfei on 17-3-1.
 */

public class MaterialDesignActivity extends BaseActivity {
    private TabFragmentPagerAdapter mTabFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        initToolbar();
        configFragments();
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialDesignActivity.this, SettingsActivity.class));
            }
        });
    }

    private void configFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(new MDFirstFragment());
//        list.add(new SFragment());
//        list.add(new LastFragment());
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        List<String> listTitle = new ArrayList<>();
        listTitle.add("系统组件");
//        listTitle.add("自定义View");
//        listTitle.add("第三方库控件");
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list, listTitle);
        viewPager.setAdapter(mTabFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
