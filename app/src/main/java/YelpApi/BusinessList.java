package YelpApi;

import java.util.ArrayList;

/**
 * Created by timbauer on 7/26/15.
 */
public class BusinessList {

    private static BusinessList businessList = null;
    public static Business[] businesses;

    public static BusinessList getBusinessList(ArrayList<Business> yelpResults){
        if (businessList == null){
            businessList = new BusinessList();
        }

        businesses = new Business[yelpResults.size()];
        businesses = yelpResults.toArray(businesses);
        return businessList;
    }

    private BusinessList(){

    }
}
