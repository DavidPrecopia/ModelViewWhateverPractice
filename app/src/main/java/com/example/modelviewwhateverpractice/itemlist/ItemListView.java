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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.databinding.ViewItemListBinding;
import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailActivity;
import com.example.modelviewwhateverpractice.repository.Repository;

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
        ViewModelProvider.NewInstanceFactory factory
                = new ItemViewModelFactory(new Repository(getActivity().getApplication()));
        logic = new ItemListLogic(
                this,
                ViewModelProviders.of(this, factory).get(ItemListViewModel.class)
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
        adapter = new ItemAdapter(logic);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void openDetailView(String title) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.INTENT_EXTRA_KEY, title);
        getActivity().startActivity(intent);
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
        binding.error.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void uiStateDisplayList() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.error.setVisibility(View.INVISIBLE);
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
