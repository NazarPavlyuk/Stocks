package com.mybringback.thebasics.trade.DatabaseModel;

import java.util.List;

/**
 * Created by ALLO on 27.08.2016.
 */
public class DataSavior {
    private List<List<String>> data;
    private String dataset_code;

    public DataSavior(List<List<String>> data, String dataset_code) {
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
