package com.example.modelviewwhateverpractice.itemdetail;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.common.BaseActivity;
import com.example.modelviewwhateverpractice.databinding.ActivityItemDetailBinding;

public class ItemDetailActivity extends BaseActivity {

    public static final String VIEW_ID = "";
    public static final String INTENT_EXTRA_KEY = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityItemDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail);
        initView(binding.fragmentHolder.getId(), ItemDetailView.getInstance(getArgumentTitle()), VIEW_ID);
    }

    /**
     * Cannot use `getTitle` because it clashes with a method in Activity.
     */
    private String getArgumentTitle() {
        return getIntent().getStringExtra(INTENT_EXTRA_KEY);
    }
}
