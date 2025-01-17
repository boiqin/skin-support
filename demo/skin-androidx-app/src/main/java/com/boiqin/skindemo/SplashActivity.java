package com.boiqin.skindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.boiqin.skindemo.actionbar.ActionbarTestActivity;
import com.boiqin.skindemo.alert.AlertDialogActivity;
import com.boiqin.skindemo.constraint.ConstraintLayoutActivity;
import com.boiqin.skindemo.flycotablayout.ui.SimpleHomeActivity;
import com.boiqin.skindemo.mdtab.MaterialDesignActivity;
import com.boiqin.skindemo.picker.ColorPickerActivity;
import com.boiqin.skindemo.picker.DrawablePickerActivity;
import com.boiqin.skindemo.tab.MainActivity;
import com.boiqin.skindemo.test.TestActivity;
import com.boiqin.skindemo.window.WindowManagerActivity;
import com.boiqin.skindemo.zip.ZipActivity;

/**
 * Created by ximsf on 2017/3/8.
 */

public class SplashActivity extends BaseActivity {
    private ListView mListView;
    private Context mContext = this;
    private final String[] mItems = {
            "基础控件",
            "Material Design",
            "ConstraintLayout",
            "FlycoTabLayout",
            "AlertDialog",
            "WindowManager",
            "Test",
            "Actionbar",
            "Color Picker",
            "Drawable Picker",
            "Zip包资源加载"
    };
    private final Class<?>[] mClasses = {
            MainActivity.class,
            MaterialDesignActivity.class,
            ConstraintLayoutActivity.class,
            SimpleHomeActivity.class,
            AlertDialogActivity.class,
            WindowManagerActivity.class,
            TestActivity.class,
            ActionbarTestActivity.class,
            ColorPickerActivity.class,
            DrawablePickerActivity.class,
            ZipActivity.class
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initToolbar();

        mListView = findViewById(R.id.list);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFadingEdgeLength(0);
        mListView.setAdapter(new HomeAdapter(mContext, mItems));

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(mContext, mClasses[position]);
            startActivity(intent);
        });
    }

    public class HomeAdapter extends BaseAdapter {
        private String[] mItems;
        private DisplayMetrics mDisplayMetrics;

        public HomeAdapter(Context context, String[] items) {
            this.mItems = items;
            mDisplayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        }

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int padding = (int) (mDisplayMetrics.density * 10);


            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.simple_spinner_item, null);
            tv.setText(mItems[position]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv.setTextAppearance(SplashActivity.this, R.style.SkinCompatTextAppearance);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(padding, padding, padding, padding);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(lp);
            return tv;
        }
    }
}
