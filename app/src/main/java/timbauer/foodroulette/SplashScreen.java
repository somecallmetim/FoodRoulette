package timbauer.foodroulette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent moveToHomeScreen = new Intent(SplashScreen.this, HomeScreen.class);
                startActivity(moveToHomeScreen);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

