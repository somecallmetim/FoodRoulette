package timbauer.foodroulette;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;
import java.util.Random;

import Database.DbAbstractionLayer;
import YelpApi.Business;
import YelpApi.BusinessList;


public class PostRouletteScreen extends ActionBarActivity {

    LatLng restLoc;

    private GoogleMap yelpMap;
    int restaurantSelector;
    public Random randomNumberGenerator;
    String restName;
    TextView viewRestaurant;
    Business[] yelpResults;
    Business selectedRestaurant;
    Marker currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__roulette__screen);

        yelpResults = BusinessList.businesses;

        randomNumberGenerator = new Random();
        restaurantSelector = randomNumberGenerator.nextInt(yelpResults.length);

        selectedRestaurant = yelpResults[restaurantSelector];
        viewRestaurant = (TextView) findViewById(R.id.selected_restaurant);

        restLoc = selectedRestaurant.latLng;
        restName = selectedRestaurant.name;
        viewRestaurant.setText(restName);

        try{
            if (yelpMap == null){
                yelpMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            }

            yelpMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            yelpMap.setMyLocationEnabled(true);
            currentRestaurant = yelpMap.addMarker(new MarkerOptions().position(restLoc));
            yelpMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restLoc, 15));
            currentRestaurant.setTitle(restName);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getNextRestaurant(View view) {
        yelpMap.clear();
        randomNumberGenerator = new Random();
        restaurantSelector = randomNumberGenerator.nextInt(yelpResults.length);

        selectedRestaurant = yelpResults[restaurantSelector];
        viewRestaurant = (TextView) findViewById(R.id.selected_restaurant);

        restLoc = selectedRestaurant.latLng;
        restName = selectedRestaurant.name;
        viewRestaurant.setText(restName);

        try{
            if (yelpMap == null){
                yelpMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            }

            yelpMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            yelpMap.setMyLocationEnabled(true);
            currentRestaurant = yelpMap.addMarker(new MarkerOptions().position(restLoc));
            yelpMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restLoc, 15));
            currentRestaurant.setTitle(restName);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getDirections(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" +
                        Double.toString(selectedRestaurant.latLng.latitude) + "," +
                        Double.toString(selectedRestaurant.latLng.longitude)));
        startActivity(intent);
    }

    public void addRestaurantToDvList(View view) {
        DbAbstractionLayer.getDbAbstractionLayer();
        DbAbstractionLayer.addRestaurant(selectedRestaurant, this);

        Intent moveToDvList = new Intent(this, DownVotedList.class);
        startActivity(moveToDvList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post__roulette__screen, menu);
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


}
