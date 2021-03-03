package za.asa_media.awesome_sa.modules_.placedetail_.async;
import android.content.Context;
import android.os.AsyncTask;

import za.asa_media.awesome_sa.modules_.placedetail_.BeanPlaceDetail;
import za.asa_media.awesome_sa.modules_.placedetail_.PlaceDetailJsonHandler;

/**
 * Created by Snow-Dell-05 on 4/26/2017.
 */

public class PlaceDetailCallApi extends AsyncTask<Void, Void, BeanPlaceDetail> {
    private Context mContext;
    private PlaceDetailJsonHandler nearbyJsonHandler = null;

    public PlaceDetailCallApi(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
     nearbyJsonHandler = new PlaceDetailJsonHandler();
    }

    @Override
    protected BeanPlaceDetail doInBackground(Void... params) {
        return nearbyJsonHandler.getPlaceDetailList();
    }


}
