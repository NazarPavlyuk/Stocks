package com.mybringback.thebasics.trade.JSONmodelForCurrencies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by ALLO on 27.09.2016.
 */

public class CurrencyParceModel extends RealmObject implements Parcelable  {
    private String name;
    private String source;

    public CurrencyParceModel() {
    }

    public CurrencyParceModel(String source, String name) {
        this.source = source;
        this.name = name;
    }

    protected CurrencyParceModel(Parcel in) {
        source = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurrencyListModel> CREATOR = new Creator<CurrencyListModel>() {
        @Override
        public CurrencyListModel createFromParcel(Parcel in) {
            return new CurrencyListModel(in);
        }

        @Override
        public CurrencyListModel[] newArray(int size) {
            return new CurrencyListModel[size];
        }
    };

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


