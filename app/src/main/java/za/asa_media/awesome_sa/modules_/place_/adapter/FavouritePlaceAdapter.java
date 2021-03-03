package za.asa_media.awesome_sa.modules_.place_.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.place_.FavouritePlaces;
import za.asa_media.awesome_sa.modules_.place_.callback.IFavouriteCallback;
import za.asa_media.awesome_sa.modules_.place_.model.FavouritePlaceInfoModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 6/2/2017.
 */

public class FavouritePlaceAdapter extends RecyclerView.Adapter<FavouritePlaceAdapter.MyViewHolder> {

    private View view = null;
    private Activity mContext;

    private UiHandleMethods uihandle;
    private IFavouriteCallback mCallback;
    private List<FavouritePlaceInfoModel> mListFavourite;
    private HashMap<String, String[]> mHashMap;
    private ArrayList<String> battch;


    public FavouritePlaceAdapter(Activity mContext, List<FavouritePlaceInfoModel> mListFavourite) {
        this.mContext = mContext;
        this.mListFavourite = mListFavourite;
        uihandle = new UiHandleMethods(mContext);
        mHashMap = new HashMap<>();
        battch = new ArrayList<>();

        if (mContext instanceof FavouritePlaces) {
            mCallback = (IFavouriteCallback) mContext;

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_selected_place, parent, false);


        MyViewHolder objmyViewHolder = new MyViewHolder(view);
        view.setTag(objmyViewHolder);
        return objmyViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final FavouritePlaceInfoModel info = mListFavourite.get(position);

        holder.txt_name_place.setText(info.getPlace_name());
        holder.txt_address.setText(info.getAddress());

        holder.nearby_rating.setRating(Float.parseFloat(info.getRating()));
        holder.txt_distance.setText(info.getDistance());

        // conditon to put icon on the google play list
        String batchs[] = info.getBatchId();
        if (batchs != null) {
            battch.clear();
            if (batchs.length > 0) {
                for (String batch : batchs) {
                    Log.d("batch", "" + batch);
                    battch.add(batch);
                    if (batch.equals("1")) {
                        holder.batch_img_catalog.setVisibility(View.VISIBLE);

                    }
                    if (batch.equals("2")) {
                        holder.batch_img_events.setVisibility(View.VISIBLE);

                    }
                    if (batch.equals("3")) {
                        holder.batch_img_promotion.setVisibility(View.VISIBLE);

                    }

                    if (batch.equals("4")) {
                        holder.batch_img_special.setVisibility(View.VISIBLE);

                    }
                    if (batch.equals("5")) {
                        holder.batch_img_news.setVisibility(View.VISIBLE);

                    }
                }
            } else {
                holder.mLinearbatch.setVisibility(View.GONE);
            }

            mHashMap.put(info.getPlaceid(), battch.toArray(new String[battch.size()]));

        }
        holder.card_view_nearby_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.getPlaceDetail(mHashMap, info.getPlaceid(), info.getDistance(), Float.parseFloat(info.getRating()));

                InitialValueSetUp.mPlaceName = holder.txt_name_place.getText().toString();

                InitialValueSetUp.mPlaceId = info.getPlaceid();
                InitialValueSetUp.lat = Double.parseDouble(info.getLatitude());
                InitialValueSetUp.lng = Double.parseDouble(info.getLongitude());

                InitialValueSetUp.mDistancePlace = info.getDistance();
                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();

                InitialValueSetUp.mBatchs = info.getBatchId();

                //  InitialValueSetUp.mPlaceAddress = holder.txt_address.getText().toString();
                //  InitialValueSetUp.mDistancePlace = new DecimalFormat("###.##").format(distance) + "k.m";

                InitialValueSetUp.mPlaceAddress = info.getAddress();
                InitialValueSetUp.mRating = Float.parseFloat(info.getRating());


            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mListFavourite.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name_place, txt_status;
        TextView txt_distance, txt_address;
        RatingBar nearby_rating;
        CardView card_view_nearby_places;
        LinearLayout mLinearbatch;
        ImageView batch_img_catalog, batch_img_events, batch_img_special, batch_img_news, batch_img_promotion;
        View status_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view) {
            txt_name_place = view.findViewById(R.id.txt_nearby_place_name);
            txt_address = view.findViewById(R.id.txt_nearby_address);
            txt_distance = view.findViewById(R.id.txt_nearby_distance);
            txt_status = view.findViewById(R.id.txt_nearby_status);
            nearby_rating = view.findViewById(R.id.ratingBar_nearby);
            card_view_nearby_places = view.findViewById(R.id.card_view_nearby_places);


            mLinearbatch = view.findViewById(R.id.linear_badges);
            batch_img_catalog = view.findViewById(R.id.batch_img_catalog);
            batch_img_events = view.findViewById(R.id.batch_img_events);
            batch_img_special = view.findViewById(R.id.batch_img_special);
            batch_img_news = view.findViewById(R.id.batch_img_news);
            batch_img_promotion = view.findViewById(R.id.batch_img_promotion);
            status_view = view.findViewById(R.id.view_circle_open_closed);

            //
            txt_status.setVisibility(View.GONE);
            status_view.setVisibility(View.GONE);


        }
    }
   /*
{
  "status": true,
  "message": "Favourites Ads",
  "data": [
    {
      "place_name": "Pizza Hut",
      "place_id": "ChIJ2S7cDBzsDzkRCCVJrI3FdPg",
      "category": "Pizza",
      "country": "India",
      "address": "2468",
      "city": "Sahibzada Ajit Singh Nagar",
      "state": "Punjab",
      "pincode": "160062",
      "number": "+91 172 398 839",
      "website": "http://restaurants.pizzahut.co.in/pizza-hut-pizza-",
      "deviceid": "lskdvnho2398ry689wufgc8237tg",
      "batchid": [
        "4"
      ]
    }
  ]
}
*/

}
