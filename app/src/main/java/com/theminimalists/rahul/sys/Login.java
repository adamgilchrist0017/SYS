package com.theminimalists.rahul.sys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener{

    public static final String USER_NAME = "USER_NAME";

    public static final String PASSWORD = "PASSWORD";

    private static final String LOGIN_URL = "http://rahulkrishnan221.esy.es/signin.php";

    private EditText editTextphno;
    private EditText editTextPassword;

    private Button buttonLogin;
    private Button buttonSignup;
    private LinearLayout toregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextphno = (EditText) findViewById(R.id.phno);
        editTextPassword = (EditText) findViewById(R.id.password);
        toregister=(LinearLayout)findViewById(R.id.toregister);

        setTitle("Login");

        buttonLogin = (Button) findViewById(R.id.buttonUserLogin);

        buttonLogin.setOnClickListener(this);
        toregister.setOnClickListener(this);
    }


    private void login(){
        String phno = editTextphno.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        userLogin(phno,password);
    }

    private void userLogin(final String phno, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"We welcome you back! ","Hold on.....",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(Login.this,MapsActivity.class);
                    /*intent.putExtra(USER_NAME,username); */
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("phno",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(phno,password);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            login();
        }
        if(v==toregister){
            Intent in1=new Intent(Login.this,register.class);
            startActivity(in1);
        }


    }
}