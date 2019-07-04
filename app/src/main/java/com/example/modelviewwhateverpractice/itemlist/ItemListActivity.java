package com.example.modelviewwhateverpractice.itemlist;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.common.BaseActivity;
import com.example.modelviewwhateverpractice.databinding.ActivityMainBinding;

public class ItemListActivity extends BaseActivity {

    private static final String VIEW_ID = "item_list_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView(binding.fragmentHolder.getId(), ItemListView.newInstance(), VIEW_ID);
    }
}
