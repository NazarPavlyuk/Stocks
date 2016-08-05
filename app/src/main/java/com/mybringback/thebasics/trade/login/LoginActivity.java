package com.mybringback.thebasics.trade.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mybringback.thebasics.trade.MainActivity;
import com.mybringback.thebasics.trade.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static String TAG = "EmailPassword";

    SharedPreferences sPref;

    final String SAVED_PASSWORD = "saved_password";
    final String SAVED_EMAIL="saved_email";

    private EditText mEmailField;
    private EditText mPasswordField;

    public static class Singleton {
        private static FirebaseAuth mAuth;

        public static FirebaseAuth instance(){
            if (mAuth==null) {
                mAuth = FirebaseAuth.getInstance();
            }
            return mAuth;
        }
    }
    public FirebaseAuth mAuth;
    public FirebaseUser user;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);

        mAuth = Singleton.instance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    sPref = getPreferences(MODE_PRIVATE);
                    boolean savedUser = sPref.getBoolean(user.getUid(), true);
                    if(savedUser) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                /*updateUI(user);*/
            }

        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(String email, String password){
        Log.d(TAG, "createAccount"+email);
        if(!validateForm()){
            return;
        }

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:onComplete " + task.isSuccessful());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                if(!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

                hideProgressDialog();
            }
        });
    }

    public void signIn(String email, String password){
        Log.d(TAG, "signIn"+email);
        if(!validateForm()){
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.e(TAG, "signInWithEmail:onComplete" + task.isSuccessful());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else if(!task.isSuccessful()) {
                    Log.e(TAG,"signInWithEmail", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }
        });
    }

    /*private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }*/

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    void saveUser() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        if (user != null){
            ed.putBoolean(user.getUid(), true);
    }
        ed.commit();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    /*private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_create_account_button:
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                saveUser();
                break;
            case R.id.email_sign_in_button:
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                saveUser();
                break;
            /*case R.id.sign_out_button:
                signOut();
                break;*/
        }
    }
}

