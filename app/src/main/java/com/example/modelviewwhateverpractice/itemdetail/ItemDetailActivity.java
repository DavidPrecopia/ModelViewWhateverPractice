package com.example.modelviewwhateverpractice.itemdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.modelviewwhateverpractice.R;
import com.example.modelviewwhateverpractice.databinding.ActivityItemDetailBinding;
import com.example.modelviewwhateverpractice.datamodel.Item;
import com.example.modelviewwhateverpractice.repository.IRepositoryContract;
import com.example.modelviewwhateverpractice.repository.Repository;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDao;
import com.example.modelviewwhateverpractice.repository.localrepository.ItemDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ItemDetailActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY = "1234";

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityItemDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail);

        binding.title.setText(
                getIntent().getExtras().getString(INTENT_EXTRA_KEY, "Could not find value")
        );

        this.disposable = new CompositeDisposable();

        binding.fabAdd.setOnClickListener(v -> {
            IRepositoryContract.Repository repository = Repository.getInstance(getDao());
            disposable.add(repository.addItem(new Item("ADDED FROM DETAIL"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onComplete() {
                            binding.title.setText("Completed");
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onError(Throwable e) {
                            binding.title.setText("Error");
                            Timber.e(e);
                        }
                    }));
        });
    }

    private ItemDao getDao() {
        return ItemDatabase.getInstance(getApplication()).itemDao();
    }


    @Override
    protected void onPause() {
        super.onPause();
        disposable.clear();
    }
}
