package za.asa_media.awesome_sa.modules_.place_.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.data.InitialValueSetUp;
import za.asa_media.awesome_sa.modules_.data.NearByPlacesData;
import za.asa_media.awesome_sa.modules_.place_.callback.ISearchFilterCallback;
import za.asa_media.awesome_sa.modules_.place_.MainPresenter;
import za.asa_media.awesome_sa.modules_.place_.SearchCategoryScreen;

/**
 * Created by Snow-Dell-05 on 10/30/2017.
 */

public class AdapterSearchCatagoryFilter extends RecyclerView.Adapter<AdapterSearchCatagoryFilter.MyViewHolder> {

    private Activity mContext;
    //  private String[] nameArray;
    //  private int[] icons_id;

    private ISearchFilterCallback mCallback;
    private MainPresenter mPresenter;
    private List<NearByPlacesData> mListData;

    public AdapterSearchCatagoryFilter(Activity mContext, List<NearByPlacesData> mListData) {
        this.mContext = mContext;
        //   this.nameArray = nameArray;
        //   this.icons_id = icons_id;
        this.mListData = mListData;
        mPresenter = new MainPresenter(InitialValueSetUp.mContext);

        if (mContext instanceof SearchCategoryScreen) {
            mCallback = (ISearchFilterCallback) mContext;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item_search_filter, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NearByPlacesData mData = mListData.get(position);
        holder.setIsRecyclable(false);

        holder.img_grid_item.setImageResource(mData.getDrawableName());
        holder.txt_heading_grid.setText(mData.getPlaceName());

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                InitialValueSetUp.mTypeHeading = mData.getPlaceName();
                InitialValueSetUp.mTypeHeadingForListing = mListData.get(position).getPlaceName();

                String placeWithKeyword = mPresenter.getNearByGooglePlaces(InitialValueSetUp.mTypeHeading);
                if (placeWithKeyword != null) {
                    mCallback.getGoogleSearchKeyword("1", placeWithKeyword);
                } else {
                    //   String text = holder.txt_heading_grid.getText().toString().trim() + "&keyword=" + holder.txt_heading_grid.getText().toString().trim();
                    String text = "keyword=" + holder.txt_heading_grid.getText().toString().trim();

                    mCallback.getGoogleSearchKeyword("1", text.trim());
                }
                // Thingstodo&keyword=Thingstodo

            }
        });

    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }

    // update list with searched data
    public void updateList(List<NearByPlacesData> list) {
        mListData = list;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_grid_item;
        TextView txt_heading_grid;
        LinearLayout mLinearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }


        private void init(View view) {
            mLinearLayout = view.findViewById(R.id.layout_linear);
            img_grid_item = view.findViewById(R.id.img_near_by_place);
            txt_heading_grid = view.findViewById(R.id.textview_near_by_place);
        }
    }


}

