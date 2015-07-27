package timbauer.foodroulette;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import Database.DbAbstractionLayer;
import Database.RestaurantDatabase;


public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
    private RestaurantDatabase restaurantDatabase;
    private SQLiteDatabase restaurantDb;
    private boolean firstRun = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DbAbstractionLayer dbAbstractionLayer = DbAbstractionLayer.getDbAbstractionLayer();

        restaurantDatabase = RestaurantDatabase.getRestaurantDatabase(this);
        restaurantDb = restaurantDatabase.getWritableDatabase();


        Cursor restData = restaurantDb.rawQuery("SELECT * FROM " + RestaurantDatabase.dbResTable, null);

        if (!(restData.getCount() > 0)){

            firstRun = true;

            String[] tableColumns = new String[]{
                    RestaurantDatabase.id,
                    RestaurantDatabase.restaurantName,
                    RestaurantDatabase.displayPhone,
                    RestaurantDatabase.image_url,
                    RestaurantDatabase.mobile_url,
                    RestaurantDatabase.phone,
                    RestaurantDatabase.rating,
                    RestaurantDatabase.reviewCount,
            };

            ContentValues dummyRestaurant = new ContentValues();

            dummyRestaurant.put(tableColumns[0], "-1");
            dummyRestaurant.put(tableColumns[1], "dummyRestaurant");

            for(int i = 2; i < tableColumns.length; i++){
                dummyRestaurant.put(tableColumns[i], "");
            }

            restaurantDb.insert(RestaurantDatabase.dbResTable, null, dummyRestaurant);



        }
        restaurantDb.close();
        restaurantDatabase.close();
        restData.close();


        /*
         * used http://stackoverflow.com/questions/25175522/how-to-enable-location-access-programatically-in-android
         * and http://stackoverflow.com/questions/11437327/android-locationmanager-isproviderenabled-always-returns-false
         * to produce the following code
         */

        //figure out if location services are enabled
        LocationManager locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsOn = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //if location services are not enabled, prompt the user to enable them
        if(!isGpsOn){

            if(firstRun){
                showEULAmessage();
            }

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("For best results, please enable location services. Would you like to do this now?")
                    .setCancelable(false)
                    //if user clicks yes, stops foodroulette and opens the devices settings app/screen
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog,  final int id) {

                            Intent turnOnLocServ = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(turnOnLocServ);
                        }
                    })
                    //if user clicks no, moves user to the home screen
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                            Intent moveToHomeScreen = new Intent(SplashScreen.this, HomeScreen.class);
                            startActivity(moveToHomeScreen);
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();

        }else if(firstRun){
            showEULAmessage();
        //is location services are enabled, show the splash screen for a bit and move to the home screen
        }else {
            //the following code forces the program to show the splash screen for 4 seconds
            // got this from http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent moveToHomeScreen = new Intent(SplashScreen.this, HomeScreen.class);
                    moveToHomeScreen.getExtras();
                    startActivity(moveToHomeScreen);

                    //close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }
    //if the app is stopped during the loading screen, the user is taken to the home screen when it restarts
    @Override
    protected void onRestart() {
        super.onRestart();
        if (!firstRun){
            Intent moveToHomeScreen = new Intent(SplashScreen.this, HomeScreen.class);
            startActivity(moveToHomeScreen);
            //close this activity
            finish();
        }
    }

    private void showEULAmessage(){
        new SimpleEula(this, this).show();
    }
}

