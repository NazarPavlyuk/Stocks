package com.mybringback.thebasics.trade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class StocksActivity extends AppCompatActivity {

    TextView open, close, low, high, change, changeProc;
    float floatOpenTday , floatOpenYday, floatChange, floatChangeProc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        open = (TextView)findViewById(R.id.open);
        close = (TextView) findViewById(R.id.close);
        low = (TextView) findViewById(R.id.low);
        high = (TextView)findViewById(R.id.high);
        change = (TextView) findViewById(R.id.change);
        changeProc = (TextView) findViewById(R.id.changeProc);

        floatOpenTday = Float.parseFloat(MainActivity.result.getData().get(0).get(1));
        floatOpenYday = Float.parseFloat(MainActivity.result.getData().get(1).get(1));
        floatChange = floatOpenTday - floatOpenYday;
        floatChangeProc =  (floatChange/floatOpenYday)*100;



        open.setText("Open: " + MainActivity.result.getData().get(0).get(1));
        close.setText("Close: " + MainActivity.result.getData().get(0).get(4));
        high.setText("High: " + MainActivity.result.getData().get(0).get(2));
        low.setText("Low: " + MainActivity.result.getData().get(0).get(3));
        change.setText("Change: " + floatChange);
        changeProc.setText("Change: " + floatChangeProc + "%");

    }
}
