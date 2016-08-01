package com.mybringback.thebasics.trade.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Dataset  {


    private List<List<String>> data;
    private String dataset_code;



    public String getDataset_code() {
        return dataset_code;
    }

    public void setDataset_code(String dataset_code) {
        this.dataset_code = dataset_code;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }


}



