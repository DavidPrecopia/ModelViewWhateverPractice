package com.example.modelviewwhateverpractice.itemlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modelviewwhateverpractice.databinding.ItemListItemBinding;
import com.example.modelviewwhateverpractice.datamodel.Item;

public class ItemAdapter extends ListAdapter<Item, ItemAdapter.ItemViewHolder> {

    ItemAdapter() {
        super(new ItemDiffCallback());
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                ItemListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemListItemBinding binding;

        ItemViewHolder(ItemListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Item item) {
            binding.title.setText(item.getTitle());
            binding.executePendingBindings();
        }
    }
}