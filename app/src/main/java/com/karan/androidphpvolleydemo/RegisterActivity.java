package com.karan.androidphpvolleydemo;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText etname, etemail, etusername, etpassword;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etname = (EditText) findViewById(R.id.name);
        etemail = (EditText) findViewById(R.id.email);
        etusername = (EditText) findViewById(R.id.username);
        etpassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtLogin=(TextView)findViewById(R.id.login_click);
        progressDialog = new ProgressDialog(this);
       txtLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
           }
       });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
       final String name=etname.getText().toString().trim();
        final String email=etemail.getText().toString().trim();
        final String username=etusername.getText().toString().trim();
        final String password=etpassword.getText().toString().trim();
        progressDialog.setMessage("Registering User....");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Constant.URL_REGISTER, new Listener<String>() {
         @Override
         public void onResponse(String response) {
                    progressDialog.dismiss();
             try {
                 JSONObject jsonObject=new JSONObject(response);
                 Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
             }
             catch (JSONException e)
             {
                 e.printStackTrace();
             }
            }
        },
             new Response.ErrorListener() {

                 @Override
                 public void onErrorResponse(VolleyError error) {
                  progressDialog.dismiss();
                     Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                 }
             }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map=new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("user_name",username);
                map.put("password",password);
                return map;
            }
        };
      /*
        Simple way to create request for volley creating object and adding it into the RequestQueue
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
      */

      //Singleton Pattern for using RequestHandler as Singleton pattern

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
