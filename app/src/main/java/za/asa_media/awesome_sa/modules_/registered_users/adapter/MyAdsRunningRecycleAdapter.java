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
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.modules_.registered_users.fragment.IRunningAdsCallback;
import za.asa_media.awesome_sa.modules_.registered_users.model.RunningAdsModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/8/2017.
 */

public class MyAdsRunningRecycleAdapter extends RecyclerView.Adapter<MyAdsRunningRecycleAdapter.CustomViewHolder> {

    private UiHandleMethods uihandle;
    private List<RunningAdsModel> feedItemList;
    private Activity mContext;
    private View view;

    // mCallback
    private IRunningAdsCallback mCallback;


    public MyAdsRunningRecycleAdapter(Activity context, List<RunningAdsModel> feedItemList) {

        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uihandle = new UiHandleMethods(context);
        if (context instanceof LoggedInUserDashboard) {
            mCallback = (IRunningAdsCallback) context;

        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_in_users_setting, false);

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_ads_approved, parent, false);


        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final RunningAdsModel feedItem = feedItemList.get(i);

        customViewHolder.mTextviewBusinessName.setText(UiHandleMethods.capitalizeString(feedItem.getPlaceName()));
        customViewHolder.mTextViewSubscriptionName.setText("Selected plan: " + feedItem.getPlanName());
        customViewHolder.mTextViewAmount.setText("$" + feedItem.getPrice());
        customViewHolder.mTextViewDatePurchased.setText("Purchased: " + feedItem.getPurchaseDate());
        customViewHolder.mTextViewExpiryDate.setText("Expiry: " + feedItem.getExpiryDate());

        final String batchs[] = feedItem.getBatchId();
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

        customViewHolder.mImageeCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getRunningAdDetail("1", feedItemList.get(i));
            }
        });
        customViewHolder.mImageEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getRunningAdDetail("2", feedItemList.get(i));
            }
        });
        customViewHolder.mImagePromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getRunningAdDetail("3", feedItemList.get(i));
            }
        });
        customViewHolder.mImageSpecials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getRunningAdDetail("4", feedItemList.get(i));
            }
        });
        customViewHolder.mImageNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getRunningAdDetail("5", feedItemList.get(i));
            }
        });

        customViewHolder.mTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    uihandle.showToast("" + batchs[0]);
                mCallback.getRunningAdDetail("" + batchs[0].trim(), feedItemList.get(i));

            }
        });

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

    public void addItem(RunningAdsModel data) {
        feedItemList.add(data);
        notifyItemInserted(feedItemList.size());
    }

    public void replaceItem(final RunningAdsModel newItem, final int position) {
        feedItemList.set(position, newItem);
        notifyItemChanged(position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView mImageeCatalog, mImageSpecials, mImageEvent, mImagePromotions, mImageNews;
        protected TextView mTextviewBusinessName, mTextViewSubscriptionName,
                mTextViewAmount, mTextViewDatePurchased, mTextViewExpiryDate, mTextEdit;
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
            this.mTextEdit = view.findViewById(R.id.txt_edit);

            mLcata = view.findViewById(R.id.linear_cata);
            mLsp = view.findViewById(R.id.linear_specials);
            mLEv = view.findViewById(R.id.linear_event);
            mLPr = view.findViewById(R.id.linear_promotion);
            mLNe = view.findViewById(R.id.linear_news);


        }
    }
}