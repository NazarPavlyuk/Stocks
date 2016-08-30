package com.mybringback.thebasics.trade.JSONmodel;


import java.util.List;

import io.realm.RealmModel;

public class Dataset {


    private List<List<String>> data;
    private String dataset_code;

    public Dataset(List<List<String>> data, String dataset_code) {
        this.data = data;
        this.dataset_code = dataset_code;
    }

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



