package com.androidapp.beconnect.beconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.androidapp.beconnect.beconnect.app.AppController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button   bLogin;
    private TextView tvRegisterLink;

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    SessionManager session;
    String url;
    String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        url = getResources().getString(R.string.url_login);

        etEmail        = (EditText) findViewById(R.id.etEmail);
        etPassword     = (EditText) findViewById(R.id.etPassword);
        bLogin         = (Button)   findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        session = new SessionManager(getApplicationContext());

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email    =    etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                hideKeyboard();

                if (!validateEmail(email)) {
                    etEmail.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    etPassword.setError("Not a valid password!");
                } else {
                    etEmail.setError(null);
                    etPassword.setError(null);

                    StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                            LoginActivity.this.startActivity(profileIntent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            Log.d("onErrorResponse: ", error.toString());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("email",    email);
                            params.put("password", password);

                            return params;
                        }
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String jsonString;
                            String access_token;
                            String client;
                            String uid;
                            try {
                                jsonString   = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                access_token = response.headers.get("access-token");
                                client       = response.headers.get("client");
                                uid          = response.headers.get("uid");

                                session.createLoginSession(uid, access_token, client);

                            } catch (UnsupportedEncodingException e) {
                                jsonString = new String(response.data);
                            }
                            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };
                    AppController.getInstance().addToRequestQueue(sr, tag_string_req);

                }
            }
        });

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

}
