package com.example.modelviewwhateverpractice.itemlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.databinding.ViewItemListBinding;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailActivity;
import com.example.modelviewwhateverpractice.itemlist.buildlogic.DaggerItemListComponent;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class ItemListView extends Fragment
        implements IListViewContract.View {

    private ViewItemListBinding binding;

    @Inject
    IListViewContract.Logic logic;


    public ItemListView() {
    }

    static ItemListView newInstance() {
        return new ItemListView();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        inject();
        super.onAttach(context);
    }

    private void inject() {
        DaggerItemListComponent.builder()
                .view(this)
                .application(getActivity().getApplication())
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.view_item_list, container, false);
        initView();
        logic.onStart();
        return binding.getRoot();
    }

    private void initView() {
        initFab();
        initRecyclerView();
    }

    private void initFab() {
        binding.fabAddList.setOnClickListener(v -> logic.addItem());
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(logic.getAdapter());
    }


    @Override
    public void openDetailView(String title) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.INTENT_EXTRA_KEY, title);
        getActivity().startActivity(intent);
    }


    @Override
    public void onDestroy() {
        logic.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setUiStateLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.error.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUiStateDisplayList() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.error.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUiStateError(String message) {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
        TextView error = binding.error;
        error.setText(message);
        error.setVisibility(View.VISIBLE);
    }
}
