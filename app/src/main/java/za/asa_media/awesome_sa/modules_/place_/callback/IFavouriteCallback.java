package za.asa_media.awesome_sa.modules_.place_.callback;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 6/17/2017.
 */

public interface IFavouriteCallback {
     void getPlaceDetail(HashMap<String,String[]> mHashmap, String place_id, String distance, float rating);
}
