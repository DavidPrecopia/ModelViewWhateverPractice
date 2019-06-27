package com.example.modelviewwhateverpractice.itemlist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.modelviewwhateverpractice.repository.IRepositoryContract;

final class ItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final IRepositoryContract.Repository repository;

    ItemViewModelFactory(IRepositoryContract.Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemListViewModel.class)) {
            //noinspection unchecked
            return (T) new ItemListViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel");
        }
    }
}
