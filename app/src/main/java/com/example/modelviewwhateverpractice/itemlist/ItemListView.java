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
import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.itemdetail.ItemDetailActivity;
import com.example.modelviewwhateverpractice.repository.Repository;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;
import com.example.modelviewwhateverpractice.util.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ItemListView extends Fragment
        implements IListViewContract.View {

    private ViewItemListBinding binding;
    private ItemAdapter adapter;

    private IListViewContract.Logic logic;


    public ItemListView() {
    }

    static ItemListView newInstance() {
        return new ItemListView();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        // until I implement DI.
        logic = new ItemListLogic(
                this,
                new ItemListViewModel(getActivity().getApplication()),
                Repository.getInstance(ItemDatabase.getInstance(getActivity().getApplication()).itemDao()),
                new SchedulerProvider(),
                new CompositeDisposable()
        );
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.view_item_list, container, false);
        initView();
        logic.onStart();
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
    public void onDestroy() {
        logic.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setList(List<Item> itemList) {
        adapter.submitList(itemList);
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
