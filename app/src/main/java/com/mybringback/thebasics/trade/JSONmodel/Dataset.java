package com.mybringback.thebasics.trade.JSONmodel;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmModel;

public class Dataset implements Parcelable {


    private List<List<String>> data;
    private String dataset_code;
    private String name;


    public Dataset(List<List<String>> data, String dataset_code, String name) {
        this.data = data;
        this.dataset_code = dataset_code;
        this.name = name;
    }

    protected Dataset(Parcel in) {
        dataset_code = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataset_code);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dataset> CREATOR = new Creator<Dataset>() {
        @Override
        public Dataset createFromParcel(Parcel in) {
            return new Dataset(in);
        }

        @Override
        public Dataset[] newArray(int size) {
            return new Dataset[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



