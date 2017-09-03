package com.theminimalists.rahul.sys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class register extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextaddress;
    private EditText editTextbloodgroup;
    private EditText editTexttrustedcontact;
    private EditText editTextdob;
    private EditText editTextEmail;
    private EditText editTextphno;
    private EditText editTextUsername;
    private LinearLayout tologin;
    private EditText editTextPassword;


    private Button buttonRegister;

    private static final String REGISTER_URL = "http://rahulkrishnan221.esy.es/UserRegistration/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextbloodgroup = (EditText) findViewById(R.id.editTextbloodgroup);
        editTexttrustedcontact = (EditText) findViewById(R.id.editTexttrustedcontact);
        editTextphno = (EditText) findViewById(R.id.editTextphno);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        tologin=(LinearLayout)findViewById(R.id.tologin);
        tologin.setOnClickListener(this);


        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
        if(v==tologin){
            Intent in1=new Intent(register.this,Login.class);
            startActivity(in1);
        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String bloodgroup = editTextbloodgroup.getText().toString().trim().toLowerCase();
        String trustedcontact = editTexttrustedcontact.getText().toString().trim().toLowerCase();
        String phno = editTextphno.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();

        register(name,bloodgroup,trustedcontact,phno,password);
    }

    private void register(String name, String bloodgroup, String trustedcontact, String phno,String password) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(register.this, "Hold On We're Setting things up for you.....",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                Intent in1=new Intent(register.this,Login.class);
                startActivity(in1);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("bloodgroup",params[1]);
                data.put("trustedcontact",params[2]);
                data.put("phno",params[3]);
                data.put("password",params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,bloodgroup,trustedcontact,phno,password);
    }

}