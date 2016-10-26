package com.androidapp.beconnect.beconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText etIdentity;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword_confirmation;
    private Button   bRegister;
    private TextView tvLoginLink;

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etIdentity               = (EditText) findViewById(R.id.etIdentity);
        etEmail                  = (EditText) findViewById(R.id.etEmail);
        etPassword               = (EditText) findViewById(R.id.etPassword);
        etPassword_confirmation  = (EditText) findViewById(R.id.etPassword_confirmation);
        bRegister                = (Button)   findViewById(R.id.bRegister);
        tvLoginLink              = (TextView) findViewById(R.id.tvLoginLink);

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String identity              = etIdentity.getText().toString();
                final String email                 = etEmail.getText().toString();
                final String password              = etPassword.getText().toString();
                final String password_confirmation = etPassword_confirmation.getText().toString();

                hideKeyboard();

                if (!validateEmail(email)) {
                    etEmail.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    etPassword.setError("Not a valid password!");
                } else if (!validatePasswordConfirmation(password, password_confirmation)) {
                    etPassword_confirmation.setError("Not equals to password!");
                } else {
                    etEmail.setError(null);
                    etPassword.setError(null);
                    etPassword_confirmation.setError(null);
                }
            }
        });
    }
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }
    public boolean validatePasswordConfirmation(String password, String password_confirmation) {
        return (password.equals(password_confirmation));
    }

}