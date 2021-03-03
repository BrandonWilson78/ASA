package za.asa_media.awesome_sa.modules_.adapter;

import android.app.Activity;
import android.location.Location;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.SelectedPlaceListdata;
import za.asa_media.awesome_sa.modules_.place_.SelectedPlaceListActivity;
import za.asa_media.awesome_sa.modules_.place_.callback.SelectedPlaceListCallback;
import za.asa_media.awesome_sa.modules_.place_.model.ActiveAdsModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.fusedlocationapi.ReservedLocation;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

/**
 * Created by Snow-Dell-05 on 4/25/2017.
 */

public class SelectedPlaceListAdapter extends RecyclerView.Adapter<SelectedPlaceListAdapter.MyViewHolder> {
    private View view = null;
    private Activity mContext;
    private List<SelectedPlaceListdata> lstNearbyPlaces = null;
    private UiHandleMethods uihandle;
    private SelectedPlaceListCallback mCallback;
    private List<ActiveAdsModel> mListActiveAds;
    private HashMap<String, String[]> mHashMap;
    private ArrayList<String> battch;
    private SessionCityOculus mSession;


    public SelectedPlaceListAdapter(Activity mContext, List<SelectedPlaceListdata> lstNearbyPlaces, List<ActiveAdsModel> mListActiveAds) {
        this.mContext = mContext;
        this.lstNearbyPlaces = lstNearbyPlaces;
        this.mListActiveAds = mListActiveAds;
        uihandle = new UiHandleMethods(mContext);

        mSession = new SessionCityOculus(mContext);
        mHashMap = new HashMap<>();
        battch = new ArrayList<>();

        if (mContext instanceof SelectedPlaceListActivity) {
            mCallback = (SelectedPlaceListCallback) mContext;
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

        //Distance
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(ReservedLocation.getSingletonInstance().getCurret_lat()));
        startPoint.setLongitude(Double.parseDouble(ReservedLocation.getSingletonInstance().getCurrent_lng()));

        Location endPoint = new Location("locationB");
        endPoint.setLatitude(Double.parseDouble(lstNearbyPlaces.get(position).getPs_lat()));
        endPoint.setLongitude(Double.parseDouble(lstNearbyPlaces.get(position).getPs_lng()));

        //distance in km
        final double distance = (startPoint.distanceTo(endPoint)) / 1000;

        String nameOfPlace = lstNearbyPlaces.get(position).getPs_name();

       /*
       String tokenss[] = nameOfPlace.split(" ");
       String final_name = "";
       if (tokenss.length > 3) {
           tokenss[3] = "\n" + tokenss[3];
        }
        for (int i = 0; i < tokenss.length; i++) {
            final_name += tokenss[i] + " ";
        }
        */

        holder.setIsRecyclable(false);

        holder.txt_name_place.setText(nameOfPlace);
        holder.txt_address.setText(lstNearbyPlaces.get(position).getPs_formatted_address());

        //format it to get 2 digit after decimal
        holder.txt_distance.setText(new DecimalFormat("###.##").format(distance) + " km");
        holder.nearby_rating.setRating((float) (lstNearbyPlaces.get(position).getPs_rating()));


        if (lstNearbyPlaces.get(position).ismFlagCheckTiming()) {
            if (lstNearbyPlaces.get(position).isPs_opening_status()) {

                holder.txt_status.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.txt_status.setText("OPEN");
                holder.status_view.setBackground(mContext.getResources().getDrawable(R.drawable.green_circle));
            } else {
                holder.txt_status.setText("CLOSED");
            }
        }


        holder.card_view_nearby_places.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InitialValueSetUp.mPlaceName = holder.txt_name_place.getText().toString();

                mSession.setBusinessNameTabbed(holder.txt_name_place.getText().toString());


                InitialValueSetUp.lat = Double.parseDouble(lstNearbyPlaces.get(position).getPs_lat());
                InitialValueSetUp.lng = Double.parseDouble(lstNearbyPlaces.get(position).getPs_lng());

                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();

                //    InitialValueSetUp.mPlaceAddress = holder.txt_address.getText().toString();
                InitialValueSetUp.mDistancePlace = new DecimalFormat("###.##").format(distance) + "k.m";

                InitialValueSetUp.mPlaceAddress = lstNearbyPlaces.get(position).getPs_formatted_address();
                InitialValueSetUp.mRating = (float) (lstNearbyPlaces.get(position).getPs_rating());



                mCallback.getPlaceId(mHashMap, lstNearbyPlaces.get(position).getPs_place_id(), new DecimalFormat("###.##").format(distance) + "k.m", (float) (lstNearbyPlaces.get(position).getPs_rating()));

                // InitialValueSetUp.mDistancePlace = holder.txt_distance.getText().toString().trim();
                // InitialValueSetUp.mPlaceName = lstNearbyPlaces.get(position).getPs_name();
                // InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();
            }
        });
/*

        //listner over tokens
        holder.batch_img_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   use async to get detail search for location by sending extras of place id to token activity
                //   lstNearbyPlaces.get(position).getPs_place_id();
                //   mContext.startActivity(new Intent(mContext, ActivityBatchInfo.class));
                InitialValueSetUp.adBatchId = "1";
                InitialValueSetUp.adPlaceId = lstNearbyPlaces.get(position).getPs_place_id();
                InitialValueSetUp.mPlaceObj = lstNearbyPlaces.get(position);

                InitialValueSetUp.mRating = (float) (lstNearbyPlaces.get(position).getPs_rating());
                InitialValueSetUp.mDistancePlace = holder.txt_distance.getText().toString().trim();
                InitialValueSetUp.mPlaceAddress = lstNearbyPlaces.get(position).getPs_formatted_address();
                InitialValueSetUp.mPlaceName = lstNearbyPlaces.get(position).getPs_name();
                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();
                InitialValueSetUp.mBatchs = mHashMap.get(lstNearbyPlaces.get(position).getPs_place_id());

                // uihandle.showToast(""+ InitialValueSetUp.adBatchId+"\n"+ InitialValueSetUp.adPlaceId);

                //  uihandle.explicitIntent(CatalogActivity.class);
                Intent intent = new Intent(mContext, CatalogActivity.class);
                //      intent.putExtra("objPlace", lstNearbyPlaces.get(position));
                //      intent.putExtra("distance", holder.txt_distance.getText().toString().trim());
                mContext.startActivity(intent);

                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        holder.batch_img_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitialValueSetUp.adBatchId = "2";
                InitialValueSetUp.adPlaceId = lstNearbyPlaces.get(position).getPs_place_id();
                InitialValueSetUp.mPlaceObj = lstNearbyPlaces.get(position);

                InitialValueSetUp.mRating = (float) (lstNearbyPlaces.get(position).getPs_rating());
                InitialValueSetUp.mDistancePlace = holder.txt_distance.getText().toString().trim();
                InitialValueSetUp.mPlaceAddress = lstNearbyPlaces.get(position).getPs_formatted_address();
                InitialValueSetUp.mPlaceName = lstNearbyPlaces.get(position).getPs_name();
                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();
                InitialValueSetUp.mBatchs = mHashMap.get(lstNearbyPlaces.get(position).getPs_place_id());

                //  uihandle.showToast(""+ InitialValueSetUp.adBatchId+"\n"+ InitialValueSetUp.adPlaceId);
                Intent intent = new Intent(mContext, EventsActivity.class);
                //   intent.putExtra("objPlace", lstNearbyPlaces.get(position));
                //   intent.putExtra("distance", holder.txt_distance.getText().toString().trim());
                mContext.startActivity(intent);

                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        holder.batch_img_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitialValueSetUp.adBatchId = "5";
                InitialValueSetUp.adPlaceId = lstNearbyPlaces.get(position).getPs_place_id();
                InitialValueSetUp.mPlaceObj = lstNearbyPlaces.get(position);
                InitialValueSetUp.mRating = (float) (lstNearbyPlaces.get(position).getPs_rating());
                InitialValueSetUp.mDistancePlace = holder.txt_distance.getText().toString().trim();
                InitialValueSetUp.mPlaceAddress = lstNearbyPlaces.get(position).getPs_formatted_address();
                InitialValueSetUp.mPlaceName = lstNearbyPlaces.get(position).getPs_name();
                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();
                InitialValueSetUp.mBatchs = mHashMap.get(lstNearbyPlaces.get(position).getPs_place_id());
                //  uihandle.showToast(""+ InitialValueSetUp.adBatchId+"\n"+ InitialValueSetUp.adPlaceId);
                Intent intent = new Intent(mContext, NewsActivity.class);
                //     intent.putExtra("objPlace", lstNearbyPlaces.get(position));
                //     intent.putExtra("distance", holder.txt_distance.getText().toString().trim());
                mContext.startActivity(intent);

                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        holder.batch_img_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitialValueSetUp.adBatchId = "3";
                InitialValueSetUp.adPlaceId = lstNearbyPlaces.get(position).getPs_place_id();
                InitialValueSetUp.mPlaceObj = lstNearbyPlaces.get(position);

                InitialValueSetUp.mRating = (float) (lstNearbyPlaces.get(position).getPs_rating());
                InitialValueSetUp.mDistancePlace = holder.txt_distance.getText().toString().trim();
                InitialValueSetUp.mPlaceAddress = lstNearbyPlaces.get(position).getPs_formatted_address();
                InitialValueSetUp.mPlaceName = lstNearbyPlaces.get(position).getPs_name();
                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();
                InitialValueSetUp.mBatchs = mHashMap.get(lstNearbyPlaces.get(position).getPs_place_id());

                //   uihandle.showToast(""+ InitialValueSetUp.adBatchId+"\n"+ InitialValueSetUp.adPlaceId);
                Intent intent = new Intent(mContext, PromotionActivity.class);
                //   intent.putExtra("objPlace", lstNearbyPlaces.get(position));
                //   intent.putExtra("distance", holder.txt_distance.getText().toString().trim());
                mContext.startActivity(intent);

                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        holder.batch_img_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitialValueSetUp.adBatchId = "4";
                InitialValueSetUp.adPlaceId = lstNearbyPlaces.get(position).getPs_place_id();
                InitialValueSetUp.mPlaceObj = lstNearbyPlaces.get(position);

                InitialValueSetUp.mRating = (float) (lstNearbyPlaces.get(position).getPs_rating());
                InitialValueSetUp.mDistancePlace = holder.txt_distance.getText().toString().trim();
                InitialValueSetUp.mPlaceAddress = lstNearbyPlaces.get(position).getPs_formatted_address();
                InitialValueSetUp.mPlaceName = lstNearbyPlaces.get(position).getPs_name();
                InitialValueSetUp.statusOpening = holder.txt_status.getText().toString();
                InitialValueSetUp.mBatchs = mHashMap.get(lstNearbyPlaces.get(position).getPs_place_id());

                // uihandle.showToast(""+ InitialValueSetUp.adBatchId+"\n"+ InitialValueSetUp.adPlaceId);
                Intent intent = new Intent(mContext, SpecialActivity.class);
                // intent.putExtra("objPlace", lstNearbyPlaces.get(position));
                // intent.putExtra("distance", holder.txt_distance.getText().toString().trim());
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
*/

        // conditon to put icon on the google play list

        if (mListActiveAds.size() > 0) {
            Log.d("adp", "active: " + mListActiveAds.size());
            battch.clear();
            for (int i = 0; i < mListActiveAds.size(); i++) {

     if ((lstNearbyPlaces.get(position).getPs_place_id().trim()).equals(mListActiveAds.get(i).getPlaceId().trim())) {

                //    lstNearbyPlaces.get(position).setmCheck(true);
                    Log.d("match:", "" + i);


                    String batchs[] = mListActiveAds.get(i).getBatchId();

                    if (batchs.length > 0) {
                        holder.mLinearbatch.setVisibility(View.VISIBLE);

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


                }

            }

            mHashMap.put(lstNearbyPlaces.get(position).getPs_place_id(), battch.toArray(new String[battch.size()]));


        }


        // for next page detail setup
        if (position == lstNearbyPlaces.size() - 1) {
            mCallback.getNextPageData(position);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lstNearbyPlaces.size();
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

        }
    }


}
