package com.theminimalists.rahul.sys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;

public class check extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;



    private SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {

        /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
               // Start your app main activity
                session = new SessionManagement(getApplicationContext());

                redirect();

                 // close this activity
                  finish();
       }
     }, SPLASH_TIME_OUT);
   }




    private void redirect() {

        if (session.introDone()) {

            Intent go = new Intent(this,Login.class);

            finish();

            startActivity(go);

        }

        else {

            Intent go = new Intent(this, Intro_Activity.class);

            finish();

            startActivity(go);

        }

    }


}
