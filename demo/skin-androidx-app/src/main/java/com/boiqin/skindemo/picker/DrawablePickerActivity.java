package com.boiqin.skindemo.picker;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boiqin.skindemo.BaseActivity;
import com.boiqin.skindemo.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import skin.support.SkinCompatManager;
import skin.support.async.AsyncTask;
import skin.support.content.res.SkinCompatUserThemeManager;

public class DrawablePickerActivity extends BaseActivity {
    private DrawablePickerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_picker);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Drawable Picker");
        mToolbar.setSubtitle("Define your exclusive application.");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new DrawablePickerAdapter();
        mAdapter.setOnItemClickListener((parent, view, position, id) -> {
            SkinCompatUserThemeManager.get().addDrawablePath(R.drawable.windowBackground, mAdapter.getItem(position));
            SkinCompatManager.getInstance().notifyUpdateSkin();
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(DrawablePickerActivity.this, 3));
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> SkinCompatUserThemeManager.get().clearDrawables());
        Button apply = findViewById(R.id.apply);
        apply.setOnClickListener(v -> SkinCompatUserThemeManager.get().apply());
        if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
        } else {
            loadData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadData();
            }
        }
    }

    private void loadData() {
        new ResolvePicTask(mAdapter).execute();
    }

    private static class ResolvePicTask extends AsyncTask<Void, Void, List<String>> {
        private final DrawablePickerAdapter mAdapter;

        public ResolvePicTask(DrawablePickerAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        protected List<String> doInBackground(Void unused) throws Exception {
            String cameraDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM" + File.separator + "Camera";
            File[] files = new File(cameraDirPath).listFiles(
                    pathname -> pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".png"));
            List<String> list = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    list.add(file.getAbsolutePath());
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> pathList) {
            super.onPostExecute(pathList);
            if (pathList != null && pathList.size() > 0) {
                mAdapter.setItems(pathList);
            }
        }

        @Override
        protected void onBackgroundError(Exception e) {

        }
    }

    private class DrawablePickerAdapter extends BaseRecyclerAdapter<String, DrawablePickerViewHolder> {

        @NonNull
        @Override
        public DrawablePickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_drawable_picker_layout, parent, false);
            return new DrawablePickerViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(@NonNull DrawablePickerViewHolder holder, int position) {
            Glide.with(DrawablePickerActivity.this).load(getItem(position)).into(holder.mImage);
        }
    }

    private static class DrawablePickerViewHolder extends BaseRecyclerAdapter.BaseViewHolder<DrawablePickerAdapter> {
        private final ImageView mImage;

        public DrawablePickerViewHolder(View itemView, DrawablePickerAdapter adapter) {
            super(itemView, adapter);
            mImage = (ImageView) itemView;
        }
    }
}
