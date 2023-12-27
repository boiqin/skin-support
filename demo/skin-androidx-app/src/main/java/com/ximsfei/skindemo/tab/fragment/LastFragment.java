package com.ximsfei.skindemo.tab.fragment;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.ximsfei.skindemo.R;

/**
 * Created by ximsfei on 17-1-14.
 */

public class LastFragment extends Fragment {
    private EditText mEdit;
    private EditText mEdit1;
    private TextView mText1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);
        mEdit = view.findViewById(R.id.edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mEdit.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.drawable_left_selector,
                    R.drawable.drawable_top_selector,
                    R.drawable.drawable_right_selector,
                    R.drawable.drawable_bottom_selector);
        }
        mEdit1 = view.findViewById(R.id.edit1);
        mEdit1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.drawable_left_selector,
                R.drawable.drawable_top_selector,
                R.drawable.drawable_right_selector,
                R.drawable.drawable_bottom_selector);
        mText1 = view.findViewById(R.id.text1);
        final Resources resources = getResources();
        final Resources.Theme theme = view.getContext().getTheme();
        mText1.setCompoundDrawablesWithIntrinsicBounds(
                ResourcesCompat.getDrawable(
                        resources, R.drawable.drawable_left_selector, theme),
                ResourcesCompat.getDrawable(
                        resources, R.drawable.drawable_top_selector, theme),
                ResourcesCompat.getDrawable(
                        resources, R.drawable.drawable_right_selector, theme),
                ResourcesCompat.getDrawable(
                        resources, R.drawable.drawable_bottom_selector, theme));
        return view;
    }
}
