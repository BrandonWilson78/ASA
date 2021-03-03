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
import za.asa_media.awesome_sa.modules_.registered_users.activity.CreateAdSpecials;
import za.asa_media.awesome_sa.modules_.registered_users.activity.EditAdSpecial;
import za.asa_media.awesome_sa.modules_.registered_users.activity.IAdsImageCallback;

/**
 * Created by Snow-Dell-05 on 7/20/2017.
 */

public class MyCustomPagerAdapter extends PagerAdapter {
    Activity context;
    String netImages[];
    int images[];
    LayoutInflater layoutInflater;
    IAdsImageCallback mCallback;
    int identityCode = 0;

    public MyCustomPagerAdapter(Activity context, String netImages[], int images[], int identityCode) {


        this.context = context;
        this.images = images;
        this.netImages = netImages;
        this.identityCode = identityCode;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // set image to view pagers
        if (identityCode == 1) {
            if (context instanceof CreateAdSpecials) {
                mCallback = (IAdsImageCallback) context;
            }
        } else if (identityCode == 2) {
            if (context instanceof EditAdSpecial) {
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
        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        final ImageView imageView = itemView.findViewById(R.id.image_batch_layout);

        // set image to view pagers
        if (identityCode == 1) {
            imageView.setImageResource(images[position]);
        } else if (identityCode == 2) {
            setImage(netImages[position].replaceAll(" ", "%20"), imageView);
        }

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.getImagePosition(position + 1, imageView);
                // Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }


    public void setImage(String image_path, ImageView mBatchImage) {
        if (!TextUtils.isEmpty(image_path)) {
            Picasso.with(context).load(image_path).error(R.drawable.img_specials)
                    .placeholder(R.drawable.img_specials)
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