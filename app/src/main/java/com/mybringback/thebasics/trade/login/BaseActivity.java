package com.mybringback.thebasics.trade.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.mybringback.thebasics.trade.R;

/**
 * Created by ALLO on 21.07.2016.
 */
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
