package com.boiqin.skindemo.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.boiqin.skindemo.BaseActivity;
import com.boiqin.skindemo.R;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initToolbar(findViewById(R.id.tool_bar));
        configFragment();
    }

    private void configFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commitAllowingStateLoss();
    }

    private void initToolbar(Toolbar toolBar) {
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolBar.setTitle("设置");
    }
}
