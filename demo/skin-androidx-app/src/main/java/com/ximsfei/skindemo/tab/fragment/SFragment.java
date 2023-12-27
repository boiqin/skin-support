package com.ximsfei.skindemo.tab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ximsfei.skindemo.R;

/**
 * Created by ximsfei on 17-1-14.
 */

public class SFragment extends Fragment {
    private ProgressBar mHorizontalBar;
    private Button mAdd;
    private Spinner mSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_middle, null);
        mHorizontalBar = view.findViewById(R.id.progress_horizontal);
        mAdd = view.findViewById(R.id.add);
        mAdd.setOnClickListener(view1 -> mHorizontalBar.setProgress(mHorizontalBar.getProgress() + 2));
        mSpinner = view.findViewById(R.id.spinner);
        final CharSequence[] entries = getResources().getStringArray(R.array.languages);
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, entries);
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);
        return view;
    }
}
