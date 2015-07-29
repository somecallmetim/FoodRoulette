package timbauer.foodroulette;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.Random;


public class RouletteScreen extends ActionBarActivity {

    public TextView businesses;
    public Random randomNumberGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette_screen);

        randomNumberGenerator = new Random();

        getSupportActionBar().hide();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roulette__screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playFoodRoulette(View view) {
        Intent goToYelpLoadingScreen = new Intent(this, YelpLoadingScreen.class);
        int restaurantGenre = randomNumberGenerator.nextInt(6) + 1;
        goToYelpLoadingScreen.putExtra("restaurantGenre", restaurantGenre);
        startActivity(goToYelpLoadingScreen);
    }
}
