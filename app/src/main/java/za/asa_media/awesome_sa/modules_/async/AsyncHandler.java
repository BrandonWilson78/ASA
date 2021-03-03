package za.asa_media.awesome_sa.modules_.async;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import za.asa_media.awesome_sa.modules_.data.SelectedPlaceListdata;


/**
 * Created by Snow-Dell-07 on 4/20/2017.
 */

public class AsyncHandler extends AsyncTask<Void, Void, List<SelectedPlaceListdata>> {
    private Context mContext;
    private PlaceNearbyJsonHandler nearbyJsonHandler = null;
    private String web_url;


    public AsyncHandler(Context mContext, String web_url) {
        this.mContext = mContext;
        this.web_url = web_url;
        init();
    }

    @Override
    protected List<SelectedPlaceListdata> doInBackground(Void... params) {
        try {
            return nearbyJsonHandler.get_nearby_places(web_url.replaceAll(" ", "%20"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void init() {
        nearbyJsonHandler = new PlaceNearbyJsonHandler();
    }

}
