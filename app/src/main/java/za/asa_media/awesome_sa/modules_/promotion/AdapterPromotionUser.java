package za.asa_media.awesome_sa.modules_.promotion;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import za.asa_media.awesome_sa.R;

/**
 * Created by Snow-Dell-05 on 7/24/2017.
 */

public class AdapterPromotionUser extends PagerAdapter {
    Activity context;

    String netImages[];
    LayoutInflater layoutInflater;

    public AdapterPromotionUser(Activity context, String netImages[]) {


        this.context = context;

        this.netImages = netImages;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return netImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.layout_item_promotions_images, container, false);

        final ImageView imageView = itemView.findViewById(R.id.image_batch_layout);

             setImage(netImages[position].replaceAll(" ", "%20"), imageView);

     //   new UiHandleMethods(context).setImageWithLazyLoading(imageView, netImages[position].replaceAll(" ", "%20"));
        // set Images to iamgeview
        //  setImage(images[position],imageView);
        container.addView(itemView);


        return itemView;
    }


    public void setImage(String image_path, ImageView mBatchImage) {
        if (!TextUtils.isEmpty(image_path)) {
            Picasso.with(context).load(image_path).error(R.drawable.promotion_banner)
                    .placeholder(R.drawable.promotion_banner)
                    .skipMemoryCache()
                    .into(mBatchImage);

            //  uihandle.setImageWithLazyLoading(mBatchImage,image_path);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}