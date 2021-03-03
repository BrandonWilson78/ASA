package za.asa_media.awesome_sa.modules_.registered_users.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.model.TransactionHistoryModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/9/2017.
 */

public class AdapterTransactionHistory extends RecyclerView.Adapter<AdapterTransactionHistory.CustomViewHolder> {

    private UiHandleMethods uihandle;
    private List<TransactionHistoryModel> feedItemList;
    private Activity mContext;
    private View view;


    public AdapterTransactionHistory(Activity context, List<TransactionHistoryModel> feedItemList) {

        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uihandle = new UiHandleMethods(context);
     /*   if (context instanceof LoggedInUserDashboard) {
            mcallback = (NotificationCallback) context;
        }*/
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_in_users_setting, false);

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_payment_history, parent, false);


        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        TransactionHistoryModel feedItem = feedItemList.get(i);

        // customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
        // customViewHolder.mButtonPrice.setText("$" + feedItem.getSellingPrice());
        // customViewHolder.mTextviewProductName.setText(uihandle.capitalizeString(feedItem.getProductName()));
        //customViewHolder.mTextViewSubTotalValue.setText(feedItem.getQty() + " x " + feedItem.getSellingPrice() + " = " + feedItem.getSubtotal()); // change to sub total
        //  customViewHolder.mTextviewNotificationDate.setText(feedItem.);

        customViewHolder.mTextviewPlaceName.setText(UiHandleMethods.capitalizeString(feedItem.getPlaceName()));
        customViewHolder.mTextViewPlanName.setText("Selected plan: "+feedItem.getPlanName());
        customViewHolder.mTextViewPrice.setText("$" + feedItem.getPrice());

        customViewHolder.mTextViewPaymentStatus.setText("Payment status is: "+ UiHandleMethods.capitalizeString(feedItem.getPaymentStatus()));

        customViewHolder.mTextViewCreatedDate.setText("Purchased: " + feedItem.getPurchaseDate());
        customViewHolder.mTextViewExpiryDate.setText("Expiry: " + feedItem.getExpiryDate());


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

    public void addItem(TransactionHistoryModel data) {
        feedItemList.add(data);
        notifyItemInserted(feedItemList.size());
    }

    public void replaceItem(final TransactionHistoryModel newItem, final int position) {
        feedItemList.set(position, newItem);
        notifyItemChanged(position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView mTextviewPlaceName, mTextViewPlanName, mTextViewPrice,
                mTextViewCreatedDate, mTextViewExpiryDate, mTextViewPaymentStatus;

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

            this.mTextviewPlaceName = view.findViewById(R.id.textview_buisness_name);
            this.mTextViewPlanName = view.findViewById(R.id.textview_month_subscription);
            this.mTextViewPrice = view.findViewById(R.id.textview_subscription_amount);
            this.mTextViewCreatedDate = view.findViewById(R.id.textview_date_posted);
            this.mTextViewExpiryDate = view.findViewById(R.id.txt_expiry);
            this.mTextViewPaymentStatus = view.findViewById(R.id.textview_description);

        }
    }
}