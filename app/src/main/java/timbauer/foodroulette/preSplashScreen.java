package timbauer.foodroulette;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class preSplashScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_splash_screen);
        getSupportActionBar().hide();
        new SimpleEula(this, this).show();
    }

}
