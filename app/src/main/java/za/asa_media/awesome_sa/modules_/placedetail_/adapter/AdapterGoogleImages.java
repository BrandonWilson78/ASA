package za.asa_media.awesome_sa.modules_.placedetail_.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-05 on 03-Apr-18.
 */

public class AdapterGoogleImages extends RecyclerView.Adapter<AdapterGoogleImages.MyViewHolder> implements View.OnClickListener {
    private Activity mContext;
    private List<String> mImageReference = null;
    private UiHandleMethods uihandle;


    public AdapterGoogleImages(Activity mContext, List<String> mImageReference) {
        this.mContext = mContext;
        this.mImageReference = mImageReference;
        uihandle = new UiHandleMethods(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_google_images, parent, false);
        MyViewHolder objmyViewHolder = new MyViewHolder(view);
        view.setTag(objmyViewHolder);
        return objmyViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String mPhotoReference = mImageReference.get(position);
        uihandle.setImageWithPicasso(holder.mImageGoogle, URLListApis.URL_GOOGLE_IMAGES_LINK_.replace("TASVEER", mPhotoReference));

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return mImageReference.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageGoogle;

        public MyViewHolder(View itemView) {
            super(itemView);
            init(itemView);


        }


        private void init(View view) {
            mImageGoogle = view.findViewById(R.id.img_google);
            mImageGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uihandle.onImageClickDialog(mImageGoogle);
                }
            });

        }
    }


}
