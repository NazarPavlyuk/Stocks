package com.mybringback.thebasics.trade.resources.JSONmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ALLO on 19.09.2016.
 */
public class Dataset1 implements Parcelable {


    private List<List<String>> data;
    private String dataset_code;
    private String name;


    public Dataset1(List<List<String>> data, String dataset_code, String name) {
        this.data = data;
        this.dataset_code = dataset_code;
        this.name = name;
    }

    protected Dataset1(Parcel in) {
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

    public static final Creator<Dataset1> CREATOR = new Creator<Dataset1>() {
        @Override
        public Dataset1 createFromParcel(Parcel in) {
            return new Dataset1(in);
        }

        @Override
        public Dataset1[] newArray(int size) {
            return new Dataset1[size];
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
