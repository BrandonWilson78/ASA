package za.asa_media.awesome_sa.modules_.placedetail_.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.placedetail_.BeanPlaceDetailReviews;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/18/2017.
 */

public class AdapterReviews extends RecyclerView.Adapter<AdapterReviews.MyViewHolder> implements View.OnClickListener {
    private Activity mContext;
    private List<BeanPlaceDetailReviews> lstNearbyPlaces = null;
    private UiHandleMethods uihandle;


    public AdapterReviews(Activity mContext, List<BeanPlaceDetailReviews> lstNearbyPlaces) {
        this.mContext = mContext;
        this.lstNearbyPlaces = lstNearbyPlaces;
        uihandle = new UiHandleMethods(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_reviews_section, parent, false);
        MyViewHolder objmyViewHolder = new MyViewHolder(view);
        view.setTag(objmyViewHolder);
        return objmyViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mLayoutNA.setVisibility(View.INVISIBLE);

        if (lstNearbyPlaces.size() > 0) {
            holder.txt_name_place.setText(lstNearbyPlaces.get(position).getPdreview_author_name());
            holder.txt_address.setText(lstNearbyPlaces.get(position).getPdreview_text());
            //format it to get 2 digit after decimal
            holder.txt_distance.setVisibility(View.INVISIBLE);
            holder.txt_status.setVisibility(View.INVISIBLE);

            holder.nearby_rating.setRating((float) lstNearbyPlaces.get(position).getPdreview_raing());

        }
    }

    @Override
    public void onClick(View v) {

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
        LinearLayout mLayoutNA;
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
            mLayoutNA = view.findViewById(R.id.linearLayout);
            status_view = view.findViewById(R.id.view_circle_open_closed);

        }
    }


}
