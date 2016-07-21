package com.mybringback.thebasics.trade.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALLO on 21.07.2016.
 */
public class Dataset {

    private List<List<String>> data = new ArrayList<List<String>>();

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "data:current date=" + data.get(0).get(0) +
                '}';
    }
}
