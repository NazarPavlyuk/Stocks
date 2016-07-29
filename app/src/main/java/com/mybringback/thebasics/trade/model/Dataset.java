package com.mybringback.thebasics.trade.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Dataset  {


    private List<List<String>> data;
    /*private DataModel dataModel;*/
    /*private String date;
    private String value;*/
    private String dataset_code;
    /*private String date = data.get(0).get(0);
    private String value =  data.get(0).get(1);*/
    /*private String date = dataModel.getDate();
    private String value = dataModel.getValue();*/

    /*public Dataset () {
        date = data.get(0).get(0);
        value =  data.get(0).get(1);
    }*/




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

   /* public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }*/



    /*public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }*/
    /*private void readFromParcel(Parcel in) {
        this.dataset_code = in.readString();
        value = in.readString(); NULLPOINTER HERE
        date = in.readString();
    }*/

}



