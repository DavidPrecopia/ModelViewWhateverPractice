package com.example.modelviewwhateverpractice.itemlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

final class ItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;

    ItemViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemListViewModel.class)) {
            //noinspection unchecked
            return (T) new ItemListViewModel(application);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel");
        }
    }
}
