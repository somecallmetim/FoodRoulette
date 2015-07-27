package YelpApi;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by timbauer on 7/26/15.
 */
public class Business {
    public String display_phone;
    public String id;
    public String name;
    public String phone;
    public String url;
    public double rating;
    public double distanceToUser;
    public int review_count;
    public LatLng latLng;
    
//    public Location location;
//    public String rating_img_url;
//    public String rating_img_url_large;
//    public String rating_img_url_small;
//    public String snippet_image_url;
//    public String snippet_text;
//    public String image_url;
//    public String mobile_url;
    
    public String[] toArray(){
        String[] businessAttributes = new String[]{
            id,
            name,
            display_phone,
            phone,
            url,
        };
        return businessAttributes;
    }

}
