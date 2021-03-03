package za.asa_media.awesome_sa.modules_.place_.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.NearByPlacesData;
import za.asa_media.awesome_sa.modules_.place_.callback.MainCallback;
import za.asa_media.awesome_sa.modules_.place_.MainPresenter;
import za.asa_media.awesome_sa.modules_.place_.MainScreen;

/**
 * Created by Snow-Dell-07 on 5/15/2017.
 */

public class UserDashFragThird extends RecyclerView.Adapter<UserDashFragThird.MyViewHolder> {
    private Activity mContext;
    private String[] nameArray;
    private int[] icons_id;

    private MainCallback mCallback;
    private MainPresenter mPresenter;
    private List<NearByPlacesData> mListData;

    public UserDashFragThird(Activity mContext, List<NearByPlacesData> mListData) {
        this.mContext = mContext;
        //   this.nameArray = nameArray;
        //   this.icons_id = icons_id;
        this.mListData = mListData;
        mPresenter = new MainPresenter(mContext);

        if (mContext instanceof MainScreen) {
            mCallback = (MainCallback) mContext;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item_gridview_near_places, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.img_grid_item.setImageResource(mListData.get(position).getDrawableName());
        holder.txt_heading_grid.setText(mListData.get(position).getPlaceName());

        holder.img_grid_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitialValueSetUp.mTypeHeading = mListData.get(position).getPlaceName();
                InitialValueSetUp.mTypeHeadingForListing = mListData.get(position).getPlaceName();
                mCallback.getGoogleSearchPlace(mPresenter.getNearByGooglePlaces(InitialValueSetUp.mTypeHeading));

                //   Singleton.getSingletonInstance().setType(holder.txt_heading_grid.getText().toString());
                //  mContext.startActivity(new Intent(mContext, NearbyPlacesActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // update list with searched data
    public void updateList(List<NearByPlacesData> list) {
        mListData = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_grid_item;
        TextView txt_heading_grid;

        public MyViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view) {
            img_grid_item = view.findViewById(R.id.img_near_by_place);
            txt_heading_grid = view.findViewById(R.id.textview_near_by_place);
        }
    }

}
