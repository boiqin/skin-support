package com.boiqin.skindemo.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.boiqin.skindemo.BaseActivity;
import com.boiqin.skindemo.R;
import com.boiqin.skindemo.settings.SettingsActivity;

/**
 * Created by pengfengwang on 2017/6/19.
 */

public class ConstraintLayoutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConstraintLayoutActivity.this, SettingsActivity.class));
            }
        });
    }
}
