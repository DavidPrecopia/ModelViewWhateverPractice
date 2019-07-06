package com.example.modelviewwhateverpractice.itemdetail;

public class ItemDetailViewModel implements IDetailViewContract.ViewModel {

    private String title;

    public ItemDetailViewModel() {
    }


    @Override
    public void setViewData(String title) {
        this.title = title;
    }

    @Override
    public String getViewData() {
        return title;
    }
}
