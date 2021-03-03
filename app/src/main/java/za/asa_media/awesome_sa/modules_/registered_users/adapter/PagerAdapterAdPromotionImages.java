package za.asa_media.awesome_sa.modules_.registered_users.adapter;

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
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdPromotions;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdPromotion;
import za.asa_media.awesome_sa.modules_.registered_users.activity.IAdsImageCallback;

/**
 * Created by Snow-Dell-05 on 7/21/2017.
 */

public class PagerAdapterAdPromotionImages extends PagerAdapter {
    Activity context;
    int images[];
    String netImages[];
    LayoutInflater layoutInflater;
    IAdsImageCallback mCallback;
    int identityCode = 0;


    public PagerAdapterAdPromotionImages(Activity context, String netImages[], int images[], int identityCode) {


        this.context = context;
        this.identityCode = identityCode;
        this.images = images;
        this.netImages = netImages;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (identityCode == 1) {
            if (context instanceof CreateAdPromotions) {
                mCallback = (IAdsImageCallback) context;
            }
        } else if (identityCode == 2) {
            if (context instanceof EditAdPromotion) {
                mCallback = (IAdsImageCallback) context;
            }
        }

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.layout_item_promotions_images, container, false);

        final ImageView imageView = itemView.findViewById(R.id.image_batch_layout);


        if (identityCode == 1) {
            imageView.setImageResource(images[position]);

        } else if (identityCode == 2) {
            setImage(netImages[position].replaceAll(" ", "%20"), imageView);
        }
        // set Images to iamgeview
        //  setImage(images[position],imageView);
        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getImagePosition(position + 1, imageView);
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    public void setImage(String image_path, ImageView mBatchImage) {
        if (!TextUtils.isEmpty(image_path)) {
            Picasso.with(context).load(image_path).error(R.drawable.img_promotion)
                    .placeholder(R.drawable.img_promotion)
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