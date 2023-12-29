package com.boiqin.skindemo.flycotablayout.ui;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.boiqin.skindemo.R;
import com.boiqin.skindemo.settings.SettingsActivity;

/**
 * Created by pengfengwang on 2017/3/9.
 */

public class FlycoActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlycoActivity.this, SettingsActivity.class));
            }
        });
    }
}
