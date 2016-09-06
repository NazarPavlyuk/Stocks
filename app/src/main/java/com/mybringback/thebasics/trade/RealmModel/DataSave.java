package com.mybringback.thebasics.trade.RealmModel;

import com.mybringback.thebasics.trade.JSONmodel.Dataset;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by ALLO on 25.08.2016.
 */
public class DataSave extends RealmObject implements RealmModel {

    private String data;
    private String dataset_code;
    private String backgroundColor;

    public DataSave(){}

    public DataSave(String dataset_code, String data) {
        this.dataset_code = dataset_code;
        this.data = data;
        this.backgroundColor = backgroundColor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataset_code() {
        return dataset_code;
    }

    public Dataset setDataset_code(String dataset_code) {
        this.dataset_code = dataset_code;
        return null;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
