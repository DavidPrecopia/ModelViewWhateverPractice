package com.example.modelviewwhateverpractice.itemdetail;

class DetailViewModel implements IDetailViewContract.ViewModel {

    private String title;

    DetailViewModel() {
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
