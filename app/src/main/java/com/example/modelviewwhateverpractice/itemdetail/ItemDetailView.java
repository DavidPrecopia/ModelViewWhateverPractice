package com.example.modelviewwhateverpractice.itemdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.databinding.ItemDetailViewBinding;
import com.example.modelviewwhateverpractice.repository.Repository;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;
import com.example.modelviewwhateverpractice.util.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;

public class ItemDetailView extends Fragment implements IDetailViewContract.View {

    private ItemDetailViewBinding binding;
    private IDetailViewContract.Logic logic;

    private static final String ARG_KEY_TITLE = "ARG_KEY_TITLE";


    public ItemDetailView() {
    }

    static ItemDetailView getInstance(String title) {
        ItemDetailView fragment = new ItemDetailView();
        Bundle args = new Bundle();
        args.putString(ARG_KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // util I implement DI.
        logic = new ItemDetailLogic(
                this,
                new DetailViewModel(),
                Repository.getInstance(ItemDatabase.getInstance(getActivity().getApplication()).itemDao()),
                new SchedulerProvider(),
                new CompositeDisposable()
        );
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_detail_view, container, false);
        initView();
        logic.onStart(getArguments().getString(ARG_KEY_TITLE));
        return binding.getRoot();
    }

    private void initView() {
        binding.fabAddDetail.setOnClickListener(v -> logic.addItem());
    }


    @Override
    public void setViewData(String title) {
        binding.tvTitle.setText(title);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        logic.onDestroy();
    }
}
