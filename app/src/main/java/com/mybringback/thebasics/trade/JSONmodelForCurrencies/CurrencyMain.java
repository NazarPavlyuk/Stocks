package com.mybringback.thebasics.trade.JSONmodelForCurrencies;

/**
 * Created by ALLO on 28.09.2016.
 */

public class CurrencyMain {

    private CurrencyModel CurrencyModel;

    public CurrencyMain(com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyModel currencyModel) {
        CurrencyModel = currencyModel;
    }

    public com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyModel getCurrencyModel() {
        return CurrencyModel;
    }

    public void setCurrencyModel(com.mybringback.thebasics.trade.JSONmodelForCurrencies.CurrencyModel currencyModel) {
        CurrencyModel = currencyModel;
    }
}
