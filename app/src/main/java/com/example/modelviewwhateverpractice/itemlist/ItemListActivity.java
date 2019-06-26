package com.example.modelviewwhateverpractice.itemlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.databinding.ActivityMainBinding;

public class ItemListActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    private static final String VIEW_ID = "item_list_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initView();
    }

    /**
     * Instead of check whether or not `savedInstanceState` is null,
     * I'll check if the View has the Fragment, if not, I'll init as normal.
     */
    private void initView() {
        Fragment view = fragmentManager.findFragmentByTag(VIEW_ID);
        if (view == null) {
            view = ItemListView.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(binding.fragmentHolder.getId(), view, VIEW_ID)
                .commit();
    }
}
