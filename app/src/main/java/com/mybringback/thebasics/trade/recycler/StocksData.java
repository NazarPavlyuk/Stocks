package com.mybringback.thebasics.trade.recycler;

/**
 * Created by ALLO on 23.07.2016.
 */
public class StocksData {

    private String stock_symbol, current_value, current_date;



    public StocksData() {}

    public StocksData(String stock_symbol, String current_value, String current_date) {
        this.stock_symbol = stock_symbol;
        this.current_value = current_value;
        this.current_date = current_date;
    }

    public String getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(String current_value) {
        this.current_value = current_value;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
}
