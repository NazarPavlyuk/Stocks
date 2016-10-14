package com.mybringback.thebasics.trade.JSONmodelForCurrencies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by ALLO on 29.09.2016.
 */

public class CurrencyListModel extends RealmObject implements Parcelable {

    private String name;
    private String source;
    /*private List<String> quotes;*/

    public CurrencyListModel() {
    }

    public CurrencyListModel(String name, String source) {
        this.name = name;
        this.source = source;
    }

    protected CurrencyListModel(Parcel in) {
        name = in.readString();
        source = in.readString();
        /*quotes = in.createStringArrayList();*/
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /*public List<String> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<String> quotes) {
        this.quotes = quotes;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(source);
        /*dest.writeStringList(quotes);*/
    }
}
