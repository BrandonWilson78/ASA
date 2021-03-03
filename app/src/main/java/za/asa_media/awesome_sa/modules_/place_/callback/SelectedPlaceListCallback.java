package za.asa_media.awesome_sa.modules_.place_.callback;

import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 4/25/2017.
 */

public interface SelectedPlaceListCallback {
    void getPlaceId(HashMap<String, String[]> mHashmap, String id, String distance, float rating);
    void getNextPageData(int position);
}
