package com.example.modelviewwhateverpractice.itemlist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.modelviewwhateverpractice.datamodel.Item;

/**
 * TODO Test this as well.
 */
class ItemDiffCallback extends DiffUtil.ItemCallback<Item> {
    @Override
    public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
        return oldItem.toString().equals(newItem.toString());
    }
}
