package com.androidapp.beconnect.beconnect;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.androidapp.beconnect.beconnect.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfile extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;

    private TextView tvIdentity;
    private TextView tvEmail;
    private EditText etNickname;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etCellphone;
    private EditText etZipcode;
    private EditText etAddress;
    private EditText etCompany;
    private EditText etCompanyAddress;
    private EditText etJobTitle;
    private Button   bEdit;

    SessionManager session;

    String url_validate_token;
    String url_edit_user_profile;
    String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        tvIdentity       = (TextView) findViewById(R.id.tvIdentity);
        tvEmail          = (TextView) findViewById(R.id.tvEmail);
        etNickname       = (EditText) findViewById(R.id.etNickname);
        etLastname       = (EditText) findViewById(R.id.etLastname);
        etFirstname      = (EditText) findViewById(R.id.etFirstname);
        etCellphone      = (EditText) findViewById(R.id.etCellphone);
        etZipcode        = (EditText) findViewById(R.id.etZipcode);
        etAddress        = (EditText) findViewById(R.id.etAddress);
        etCompany        = (EditText) findViewById(R.id.etCompany);
        etCompanyAddress = (EditText) findViewById(R.id.etCompanyAddress);
        etJobTitle       = (EditText) findViewById(R.id.etJobTitle);
        bEdit            = (Button)   findViewById(R.id.bEdit);

        url_validate_token    = getResources().getString(R.string.url_validate_token);
        url_edit_user_profile = getResources().getString(R.string.url_edit_user_profile);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        final String uid          = user.get(SessionManager.KEY_UID);
        final String access_token = user.get(SessionManager.KEY_ACCESS_TOKEN);
        final String client       = user.get(SessionManager.KEY_CLIENT);

        JsonObjectRequest jor = new JsonObjectRequest(url_validate_token, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                          tvIdentity.setText(response.getJSONObject("data").getString("identity"));
                             tvEmail.setText(response.getJSONObject("data").getString("email"));
                          etNickname.setText(response.getJSONObject("data").getString("nickname"));
                         etFirstname.setText(response.getJSONObject("data").getString("firstname"));
                          etLastname.setText(response.getJSONObject("data").getString("lastname"));
                         etCellphone.setText(response.getJSONObject("data").getString("cellphone"));
                           etZipcode.setText(response.getJSONObject("data").getString("zipcode"));
                           etAddress.setText(response.getJSONObject("data").getString("address"));
                           etCompany.setText(response.getJSONObject("data").getString("company"));
                    etCompanyAddress.setText(response.getJSONObject("data").getString("company_address"));
                          etJobTitle.setText(response.getJSONObject("data").getString("job_title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditUserProfile.this, "error", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("access-token", access_token);
                params.put("client",       client);
                params.put("uid",          uid);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jor, tag_string_req);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname        = etNickname.getText().toString();
                final String firstname       = etFirstname.getText().toString();
                final String lastname        = etLastname.getText().toString();
                final String cellphone       = etCellphone.getText().toString();
                final String zipcode         = etZipcode.getText().toString();
                final String address         = etAddress.getText().toString();
                final String company         = etCompany.getText().toString();
                final String company_address = etCompanyAddress.getText().toString();
                final String job_title       = etJobTitle.getText().toString();

                StringRequest sr = new StringRequest(Request.Method.PUT, url_edit_user_profile , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditUserProfile.this, "Update success", Toast.LENGTH_LONG).show();
                        Intent UserProfileIntent = new Intent(EditUserProfile.this, ProfileActivity.class);
                        EditUserProfile.this.startActivity(UserProfileIntent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("nickname",        nickname);
                        params.put("firstname",       firstname);
                        params.put("lastname",        lastname);
                        params.put("cellphone",       cellphone);
                        params.put("zipcode",         zipcode);
                        params.put("address",         address);
                        params.put("company",         company);
                        params.put("company_address", company_address);
                        params.put("job_title",       job_title);

                        return params;
                    }
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("uid",          uid);
                        params.put("client",       client);
                        params.put("access-token", access_token);
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(sr, tag_string_req);
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 檢查是否有可用的藍牙裝置
        if (mBluetoothAdapter == null) {
            // 若無可用裝置時執行
            Toast.makeText(this, "Bluetooth not supported on this Device", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 如果藍牙目前不可用，請求使用者開啟藍芽功能。
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    // 使用onActivityResult 接收其他 Activity回傳的資料
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 接收請求開啟藍芽功能的結果
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 使用者授權藍牙後執行
            Toast.makeText(this, "使用者已授權藍牙使用", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            // 使用者拒絕授權藍牙後執行
            Toast.makeText(this, "使用者拒絕授權藍牙使用", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
}
