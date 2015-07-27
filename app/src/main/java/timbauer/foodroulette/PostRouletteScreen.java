package timbauer.foodroulette;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class PostRouletteScreen extends ActionBarActivity {

    static final LatLng SFSU = new LatLng(37.7208562,-122.4779366);

    private GoogleMap yelpMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__roulette__screen);

        try{
            if (yelpMap == null){
                yelpMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            }

            yelpMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            yelpMap.setMyLocationEnabled(true);
            Marker SanFranState = yelpMap.addMarker(new MarkerOptions().position(SFSU));
            SanFranState.setTitle("SFSU");

        }catch (Exception e){
            e.printStackTrace();
        }
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
