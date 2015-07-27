package Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import YelpApi.Business;
import timbauer.foodroulette.DownVotedList;

/**
 * Created by timbauer on 7/27/15.
 */
public class DbAbstractionLayer {

    private static DbAbstractionLayer dbAbstractionLayer = null;
    private static Context mContext;
    private static RestaurantDatabase restaurantDatabase;
    private static SQLiteDatabase restaurantDb;

    private static String[] tableColumns = new String[]{
            RestaurantDatabase.id,
            RestaurantDatabase.restaurantName,
            RestaurantDatabase.displayPhone,
            RestaurantDatabase.image_url,
            RestaurantDatabase.mobile_url,
            RestaurantDatabase.phone,
            RestaurantDatabase.rating,
            RestaurantDatabase.reviewCount,
    };

    public static DbAbstractionLayer getDbAbstractionLayer(){
        if (dbAbstractionLayer == null){
            dbAbstractionLayer = new DbAbstractionLayer();
            return dbAbstractionLayer;
        }else{
            return dbAbstractionLayer;
        }
    }

    private DbAbstractionLayer(){}

    public static boolean isRestaurantInBlockedList(String restaurantId, Context context){

        mContext = context;
        restaurantDatabase = RestaurantDatabase.getRestaurantDatabase(mContext);
        restaurantDb = restaurantDatabase.getWritableDatabase();

        Cursor restaurantData = restaurantDb.query(
                RestaurantDatabase.dbResTable,
                tableColumns,
                RestaurantDatabase.id + " = ?",
                new String[] {restaurantId},
                null, null, null);
        if (restaurantData.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    public static Business[] getDownVotedList(Context context){
        mContext = context;
        restaurantDatabase = RestaurantDatabase.getRestaurantDatabase(mContext);
        restaurantDb = restaurantDatabase.getWritableDatabase();


        Cursor restData =restaurantDb.rawQuery("SELECT * FROM " + RestaurantDatabase.dbResTable, null);
        restData.moveToFirst();
        int numOfRestaurants = restData.getCount();

        int idColumn = restData.getColumnIndex(RestaurantDatabase.id);
        int restNameColumn = restData.getColumnIndex(RestaurantDatabase.restaurantName);
        int dispPhoneColumn = restData.getColumnIndex(RestaurantDatabase.displayPhone);
        int phoneColumn = restData.getColumnIndex(RestaurantDatabase.phone);
        int rateColumn = restData.getColumnIndex(RestaurantDatabase.rating);
        int revColumn = restData.getColumnIndex(RestaurantDatabase.reviewCount);


        Business[] downVotedList = new Business[numOfRestaurants];

        for (int i = 0; i < numOfRestaurants; i++){
            downVotedList[i] = new Business();
            downVotedList[i].id = restData.getString(idColumn);
            downVotedList[i].name = restData.getString(restNameColumn);
            downVotedList[i].display_phone = restData.getString(dispPhoneColumn);
            downVotedList[i].phone = restData.getString(phoneColumn);
            downVotedList[i].rating = restData.getFloat(rateColumn);
            downVotedList[i].review_count = restData.getInt(revColumn);

            restData.moveToNext();
        }
        restData.close();
        return downVotedList;
    }

    public static void addRestaurant(Business downVotedRestaurant, Context context){
        mContext = context;
        restaurantDatabase = RestaurantDatabase.getRestaurantDatabase(mContext);
        restaurantDb = restaurantDatabase.getWritableDatabase();


        ContentValues newRestaurant = new ContentValues();

        newRestaurant.put(RestaurantDatabase.id, downVotedRestaurant.id);
        newRestaurant.put(RestaurantDatabase.restaurantName, downVotedRestaurant.name);
        newRestaurant.put(RestaurantDatabase.displayPhone, downVotedRestaurant.display_phone);
        newRestaurant.put(RestaurantDatabase.phone, downVotedRestaurant.phone);
        newRestaurant.put(RestaurantDatabase.rating, downVotedRestaurant.rating);
        newRestaurant.put(RestaurantDatabase.reviewCount, downVotedRestaurant.review_count);

        restaurantDb.insert(RestaurantDatabase.dbResTable, null, newRestaurant);

    }

    public static void removeRestaurant(Business restaurant, Context context){
        mContext = context;
        restaurantDatabase = RestaurantDatabase.getRestaurantDatabase(mContext);
        restaurantDb = restaurantDatabase.getWritableDatabase();



        restaurantDb.delete(RestaurantDatabase.dbResTable, RestaurantDatabase.id + " = ?",
                new String[]{restaurant.id});

    }

    public static void removeDvTables (Context context){
        mContext = context;
        restaurantDatabase = RestaurantDatabase.getRestaurantDatabase(mContext);
        restaurantDb = restaurantDatabase.getWritableDatabase();

        mContext.deleteDatabase(RestaurantDatabase.dbName);
    }

}
