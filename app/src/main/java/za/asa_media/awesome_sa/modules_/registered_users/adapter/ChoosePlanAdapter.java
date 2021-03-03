package za.asa_media.awesome_sa.modules_.registered_users.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.ChoosePlan;
import za.asa_media.awesome_sa.modules_.registered_users.IChoosePlane;
import za.asa_media.awesome_sa.modules_.registered_users.model.ChoosePlanModel;
import za.asa_media.awesome_sa.support.UiHandleMethods;

/**
 * Created by Snow-Dell-05 on 5/24/2017.
 */

public class ChoosePlanAdapter extends RecyclerView.Adapter<ChoosePlanAdapter.CustomViewHolder> {

    // radio selection
    private static AppCompatRadioButton lastCheckedRadio = null;
    private static int lastCheckedPos = 0;
    private UiHandleMethods uihandle;
    private List<ChoosePlanModel> feedItemList;
    private Activity mContext;
    private View view;
    private int clickedPos = 0;
    // listeners
    private IChoosePlane mListenerPlan;

    public ChoosePlanAdapter(Activity context, List<ChoosePlanModel> feedItemList) {

        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uihandle = new UiHandleMethods(context);

        if (mContext instanceof ChoosePlan) {
            mListenerPlan = (IChoosePlane) mContext;
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
  //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_in_users_setting, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chooser_plan, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {
        final ChoosePlanModel feedItem = feedItemList.get(position);

        //  customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
        //  customViewHolder.mButtonPrice.setText("$" + feedItem.getSellingPrice());
        //  customViewHolder.mTextviewProductName.setText(uihandle.capitalizeString(feedItem.getProductName()));
        //  customViewHolder.mTextViewSubTotalValue.setText(feedItem.getQty() + " x " + feedItem.getSellingPrice() + " = " + feedItem.getSubtotal()); // change to sub total
        //  customViewHolder.mTextviewNotificationDate.setText(feedItem.);


        //for default check in first item
        if (position == 0 && feedItemList.get(0).isChecked() && customViewHolder.mRadioButton.isChecked()) {
            lastCheckedRadio = customViewHolder.mRadioButton;
            lastCheckedPos = 0;
        }

        customViewHolder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatRadioButton cb = (AppCompatRadioButton) v;
                clickedPos = position;

                if (cb.isChecked()) {
                    if (lastCheckedRadio != null) {
                        lastCheckedRadio.setChecked(false);
                        feedItemList.get(lastCheckedPos).setChecked(false);
                    }

                    lastCheckedRadio = cb;
                    lastCheckedPos = clickedPos;
                } else
                    lastCheckedRadio = null;

                feedItemList.get(clickedPos).setChecked(cb.isChecked());
                mListenerPlan.getDetailOfPlan(feedItemList.get(clickedPos));
                // getClikedPosition(clickedPos);
            }
        });

        customViewHolder.mTextviewPlanName.setText(feedItem.getPlanName());
        customViewHolder.mTextViewAmount.setText("$" + feedItem.getPlanPrice());
        customViewHolder.mTextViewCategoryTitle.setText(feedItem.getPlanSubtitle());

        if (feedItem.getPlanBatch().equals("one")) {
            customViewHolder.clearBatchs();
        }

        //  uihandle.setUnderLine(customViewHolder.mTextviewBusinessName);
        // customViewHolder.mTextViewNotification.setText(feedItem.getmDescription());

    }

    public void getClikedPosition(int pos) {
        uihandle.showToast("" + pos);
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

    public void addItem(ChoosePlanModel data) {
        feedItemList.add(data);
        notifyItemInserted(feedItemList.size());
    }

    public void replaceItem(final ChoosePlanModel newItem, final int position) {
        feedItemList.set(position, newItem);
        notifyItemChanged(position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView mImageeCatalog, mImageSpecials, mImageEvent, mImagePromotions, mImageNews;
        protected TextView mTextviewPlanName, mTextViewCategoryTitle,
                mTextViewAmount;
        protected AppCompatRadioButton mRadioButton;

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

            this.mTextviewPlanName = view.findViewById(R.id.txt_month_subscription);
            this.mTextViewCategoryTitle = view.findViewById(R.id.txt_categories_month);
            this.mTextViewAmount = view.findViewById(R.id.txt_price_month_subscription);
            this.mRadioButton = view.findViewById(R.id.radio_month_subscription);

            this.mImageeCatalog = view.findViewById(R.id.batch_img_catalog);
            this.mImageSpecials = view.findViewById(R.id.batch_img_special_1);
            this.mImageEvent = view.findViewById(R.id.batch_img_events_1);
            this.mImagePromotions = view.findViewById(R.id.batch_img_promotion_1);
            this.mImageNews = view.findViewById(R.id.batch_img_news_1);


        }

        void clearBatchs() {
            mImageeCatalog.setImageResource(R.drawable.grey_catalog);
            mImageSpecials.setImageResource(R.drawable.grey_specials);
            mImageEvent.setImageResource(R.drawable.grey_events);
            mImagePromotions.setImageResource(R.drawable.grey_promotions);
            mImageNews.setImageResource(R.drawable.grey_news);
        }

    }
}