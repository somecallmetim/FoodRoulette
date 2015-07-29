package timbauer.foodroulette;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.Float;

import java.util.ArrayList;

import YelpApi.Business;
import YelpApi.BusinessList;
import YelpApi.Yelp;


public class YelpLoadingScreen extends ActionBarActivity {


    Context mContext;
    AsyncTask mAsyncTask;
    int genreSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_loading_screen);

        getSupportActionBar().hide();

        Intent getGenre = getIntent();
        genreSelector = getGenre.getExtras().getInt("restaurantGenre");

        mAsyncTask = new GetYelpData(this, genreSelector).execute();
    }

    protected void startRouletteScreen(ArrayList<Business> result){

        Intent goToPostRouletteScreen = new Intent(this, PostRouletteScreen.class);
        if(result == null){
            result = new ArrayList<Business>(1);
            result.add(0, new Business());
            result.get(0).name = "no business available";
        }

        BusinessList.getBusinessList(result);
        startActivity(goToPostRouletteScreen);
        finish();

    }



    class GetYelpData extends AsyncTask<Void, Void, ArrayList<Business>> {
        Context context;
        int genreSelector;
        String genre;

        GetYelpData(Context context, int genreSelector) {
            this.context = context;
            this.genreSelector = genreSelector;



            Handler yelpDataTimeout = new Handler() {
                @Override
                public void handleMessage(Message message){
                    super.handleMessage(message);
                    mAsyncTask.cancel(true);
                }
            };

            Message stopAsyncTask = Message.obtain();

            yelpDataTimeout.sendMessageDelayed(stopAsyncTask, 10000);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

       }

        @Override
        protected ArrayList<Business> doInBackground(Void... params) {
            switch (genreSelector){
                case 1:
                    genre = "Mexican Food";
                    break;
                case 2:
                    genre = "Chinese Food";
                    break;
                case 3:
                    genre = "Fast Food";
                    break;
                case 4:
                    genre = "Steak House";
                    break;
                case 5:
                    genre = "Korean Food";
                    break;
                case 6:
                    genre = "Sushi";
                    break;
                default:
                    genre = "Food";
                    break;
            }


            Yelp yelp = Yelp.getYelp(YelpLoadingScreen.this);
            String businesses = yelp.search(genre, 37.788022, -122.399797);


            try {
                ArrayList<Business> yelpResults = processJson(businesses);
                return yelpResults;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(ArrayList<Business> result) {
            super.onPostExecute(result);

            startRouletteScreen(result);
        }


       public ArrayList<Business> processJson(String jsonStuff) throws JSONException {
           JSONObject fullJsonString = new JSONObject(jsonStuff);
           JSONArray businesses = fullJsonString.getJSONArray("businesses");
           double lat = 0.0;
           double lon = 0.0;

           ArrayList<Business> businessList = new ArrayList<Business>(businesses.length());

           for (int i = 0; i < businesses.length(); i++) {
               JSONObject business = businesses.getJSONObject(i);
               businessList.add(new Business());

               businessList.get(i).display_phone = business.getString("display_phone");
               businessList.get(i).id = business.getString("id");
               businessList.get(i).name = business.getString("name");
               businessList.get(i).phone = business.getString("phone");
               businessList.get(i).url = business.getString("url");
               businessList.get(i).rating = business.getDouble("rating");
               businessList.get(i).review_count = business.getInt("review_count");
               businessList.get(i).distanceToUser = business.getDouble("distance");

               JSONObject location = business.getJSONObject("location");
               JSONObject coordinate = location.getJSONObject("coordinate");
               lat = coordinate.getDouble("latitude");
               lon = coordinate.getDouble("longitude");

               businessList.get(i).latLng = new LatLng(lat, lon);

           }
           return businessList;
       }
    }
}
