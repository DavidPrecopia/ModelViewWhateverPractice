package com.example.modelviewwhateverpractice.itemlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.databinding.ViewItemListBinding;
import com.example.modelviewwhateverpractice.datamodel.Item;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemListView extends Fragment
        implements IViewContract.View {

    private ViewItemListBinding binding;
    private ItemAdapter adapter;

    private IViewContract.Logic logic;


    public ItemListView() {
    }

    static ItemListView newInstance() {
        return new ItemListView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // until I implement DI.
        logic = new ItemListLogic(
                this,
                ViewModelProviders.of(this).get(ItemListViewModel.class)
        );
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.view_item_list, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        logic.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        logic.onPause();
    }

    @Override
    public void setList(List<Item> itemList) {
        adapter.submitList(itemList);
    }

    @Override
    public void uiStateLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void uiStateDisplayList() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void uiStateError(String message) {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
        TextView error = binding.error;
        error.setText(message);
        error.setVisibility(View.VISIBLE);
    }
}
