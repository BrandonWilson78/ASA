package za.asa_media.awesome_sa.modules_.registered_users.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.model.NotificationModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/8/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomViewHolder> {

    private UiHandleMethods uihandle;
    private List<NotificationModel> feedItemList;
    private Activity mContext;
    private View view;

    public NotificationAdapter(Activity context, List<NotificationModel> feedItemList) {

        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uihandle = new UiHandleMethods(context);

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_in_users_setting, false);

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_notifications, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        NotificationModel feedItem = feedItemList.get(i);

      /* 1. You have just purchased 'plane name' for the "Place name" on '28-06-17'. valid up to 'expire date'.
         2. Your purchased "Plan name" for the "place name" is about to expire on '28-07-17'.
         3. Your "Plan name " for the "Place name" is expired on '28-07-17'. To continue service subscribed plan.*/

        //    customViewHolder.mTextviewPlaceName.setText(uihandle.capitalizeString(feedItem.getPlanName()));

        //  customViewHolder.mTextviewPlaceName.setText();
        // customViewHolder.mTextViewPlanName.setText("Selected plan: " + feedItem.getPlanName());
        customViewHolder.mTextViewNotificationDate.setText("27-06-2017");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Html.fromHtml("<h2>Title</h2><br><p>Description here</p>", Html.FROM_HTML_MODE_COMPACT)
            customViewHolder.mTextviewNotification.setText(Html.fromHtml(feedItem.getmDetail(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            customViewHolder.mTextviewNotification.setText(Html.fromHtml(feedItem.getmDetail()));
        }


        // customViewHolder.mTextViewPrice.setText("27-06-2107");
        // customViewHolder.mTextViewPaymentStatus.setText(feedItem.getmDetail());

        //  customViewHolder.mTextViewCreatedDate.setText("Activation: " + feedItem.getActivationDate());
        // customViewHolder.mTextViewExpiryDate.setText("Expiry: " + feedItem.getExpiryDate());


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

    public void addItem(NotificationModel data) {
        feedItemList.add(data);
        notifyItemInserted(feedItemList.size());
    }

    public void replaceItem(final NotificationModel newItem, final int position) {
        feedItemList.set(position, newItem);
        notifyItemChanged(position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView mTextviewNotification, mTextViewNotificationDate;


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

            this.mTextviewNotification = view.findViewById(R.id.notification);
            this.mTextViewNotificationDate = view.findViewById(R.id.notification_date);


            this.mTextViewNotificationDate.setVisibility(View.GONE);


        }
    }

}