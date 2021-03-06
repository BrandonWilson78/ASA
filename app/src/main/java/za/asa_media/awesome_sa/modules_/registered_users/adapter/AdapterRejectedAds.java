package za.asa_media.awesome_sa.modules_.registered_users.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.model.RejectedAdModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/26/2017.
 */

public class AdapterRejectedAds extends RecyclerView.Adapter<AdapterRejectedAds.CustomViewHolder> {

    private UiHandleMethods uihandle;
    private List<RejectedAdModel> feedItemList;
    private Activity mContext;
    private View view;


    public AdapterRejectedAds(Activity context, List<RejectedAdModel> feedItemList) {

        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uihandle = new UiHandleMethods(context);

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_in_users_setting, false);

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_my_ads_all, parent, false);


        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        RejectedAdModel feedItem = feedItemList.get(i);

        customViewHolder.mTextviewBusinessName.setText(UiHandleMethods.capitalizeString(feedItem.getPlaceName()));
        customViewHolder.mTextViewSubscriptionName.setText("Selected plan: " + feedItem.getPlanName());
        customViewHolder.mTextViewAmount.setText("$" + feedItem.getPrice());
        customViewHolder.mTextViewDatePurchased.setText("Purchased: " + feedItem.getPurchaseDate());
        customViewHolder.mTextViewExpiryDate.setText("Expiry: " + feedItem.getExpiryDate());

        String batchs[] = feedItem.getBatchId();
        if (batchs.length > 0) {
            customViewHolder.mLinearBatchs.setVisibility(View.VISIBLE);
            for (String batch : batchs) {
                if (batch.equals("1")) {
                    customViewHolder.mLcata.setVisibility(View.VISIBLE);
                }
                if (batch.equals("2")) {
                    customViewHolder.mLEv.setVisibility(View.VISIBLE);
                }
                if (batch.equals("3")) {
                    customViewHolder.mLPr.setVisibility(View.VISIBLE);
                }
                if (batch.equals("4")) {
                    customViewHolder.mLsp.setVisibility(View.VISIBLE);
                }
                if (batch.equals("5")) {
                    customViewHolder.mLNe.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public void onItemDismiss(int position) {
        if (position != -1 && position < feedItemList.size()) {
            feedItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public void removeItem(int position) {
        feedItemList.remove(position);
        notifyItemRemoved(position);
        // notifyItemRangeChanged(position, feedItemList.size());
    }

    public void addItem(RejectedAdModel data) {
        feedItemList.add(data);
        notifyItemInserted(feedItemList.size());
    }

    public void replaceItem(final RejectedAdModel newItem, final int position) {
        feedItemList.set(position, newItem);
        notifyItemChanged(position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView mImageeCatalog, mImageSpecials, mImageEvent, mImagePromotions, mImageNews;
        protected TextView mTextviewBusinessName,  mTextViewSubscriptionName,
                mTextViewAmount, mTextViewDatePurchased, mTextViewExpiryDate;
        protected LinearLayout mLinearBatchs, mLcata, mLsp, mLEv, mLPr, mLNe;
        public CustomViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("POS", "Element " + getPosition() + " clicked.");
                    //  mINotificationCallback.deleteNotification(getPosition());
                    //  feedItemList.remove(getPosition());
                    //   notifyItemRemoved(getPosition());
                }
            });

            this.mImageeCatalog = view.findViewById(R.id.catalog_batch);
            this.mImageSpecials = view.findViewById(R.id.specials_batch);
            this.mImageEvent = view.findViewById(R.id.event_batch);
            this.mImagePromotions = view.findViewById(R.id.promotions_batch);
            this.mImageNews = view.findViewById(R.id.news_batch);
            this.mLinearBatchs = view.findViewById(R.id.linear_1);
            this.mTextviewBusinessName = view.findViewById(R.id.textview_buisness_name);

            this.mTextViewSubscriptionName = view.findViewById(R.id.textview_month_subscription);
            this.mTextViewAmount = view.findViewById(R.id.textview_subscription_amount);
            this.mTextViewDatePurchased = view.findViewById(R.id.textview_date_purchased);
            this.mTextViewExpiryDate = view.findViewById(R.id.txt_expiry);

            mLcata = view.findViewById(R.id.linear_cata);
            mLsp = view.findViewById(R.id.linear_specials);
            mLEv = view.findViewById(R.id.linear_event);
            mLPr = view.findViewById(R.id.linear_promotion);
            mLNe = view.findViewById(R.id.linear_news);

        }
    }
}