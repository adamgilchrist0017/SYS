package com.theminimalists.rahul.sys;

        import android.content.Intent;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Toast;

        import com.github.paolorotolo.appintro.AppIntro;

public class Intro_Activity extends AppIntro {


    private SessionManagement session;



    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.activity_intro_));
        addSlide(SampleSlide.newInstance(R.layout.introtwo));
        addSlide(SampleSlide.newInstance(R.layout.introthree));
        addSlide(SampleSlide.newInstance(R.layout.introfour));

        session = new SessionManagement(getApplicationContext());

        hideStatusBar();


        setFlowAnimation();

    }



    private void hideStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

    }


    @Override
    public void onSkipPressed() {

        session.createSplashSession();

        Intent go = new Intent(Intro_Activity.this, Login.class);

        finish();

        startActivity(go);

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {

        session.createSplashSession();

        Intent go = new Intent(Intro_Activity.this, Login.class);

        finish();

        startActivity(go);

    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v) {
        Toast.makeText(getApplicationContext(), getString(R.string.app_name), Toast.LENGTH_SHORT).show();
    }
}
