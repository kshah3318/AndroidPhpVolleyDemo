package com.karan.androidphpvolleydemo;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword=(EditText) findViewById(R.id.password);
        etUsername=(EditText) findViewById(R.id.username);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               UserLogin();
           }
       });
    }

    private void UserLogin() {
        final String username=etUsername.getText().toString().trim();
        final String password=etPassword.getText().toString().trim();
        progressDialog.setMessage("Please  Wait....");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Constant.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    if(!jsonObject.getBoolean("error"))
                    {
                        SharedPrefManager.getmInstance(getApplicationContext()).userLogin(
                                jsonObject.getInt("id"),
                        jsonObject.getString("email"),
                        jsonObject.getString("username")
                        );
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login UnSuccessful",Toast.LENGTH_LONG).show();
                    }
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
