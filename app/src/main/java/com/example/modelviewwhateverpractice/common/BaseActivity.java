package com.example.modelviewwhateverpractice.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class BaseActivity extends AppCompatActivity {

    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentManager = getSupportFragmentManager();
    }

    /**
     * Instead of check whether or not `savedInstanceState` is null,
     * I'll check if the View has the Fragment, if not, I'll init as normal.
     */
    protected void initView(int containerViewId, Fragment fragment, String viewId) {
        Fragment view = fragmentManager.findFragmentByTag(viewId);
        if (view == null) {
            view = fragment;
        }

        fragmentManager.beginTransaction()
                .replace(containerViewId, view, viewId)
                .commit();
    }
}
