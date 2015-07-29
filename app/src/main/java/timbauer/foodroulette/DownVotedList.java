package timbauer.foodroulette;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import YelpApi.Business;
import Database.DbAbstractionLayer;



public class DownVotedList extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_voted_list);

        getSupportActionBar().hide();

        Business[] downVotedRestaurants = DbAbstractionLayer.getDownVotedList(this);
        String[] restaurantNames = new String[downVotedRestaurants.length];

        for(int i = 0; i < downVotedRestaurants.length; i++){
            restaurantNames[i] = downVotedRestaurants[i].name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, restaurantNames);

        ListView listView = (ListView) findViewById(R.id.downVotedList);
        listView.setAdapter(adapter);



    }

    public void resetDb(View view) {
        DbAbstractionLayer.removeDvTables(this);
    }
}
