package za.asa_media.awesome_sa.modules_.logged_in_user_dashboard_.payment_module_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.support.UiHandleMethods;

public class PaymentSuccessful extends AppCompatActivity {

    private Activity mContext = this;
    private UiHandleMethods uihandle;
    private ImageView mImageCross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_successful);

        initViews();
    }


    private void initViews() {

        uihandle = new UiHandleMethods(mContext);

        mImageCross = (ImageView) findViewById(R.id.img_cross_payment);
        mImageCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, LoggedInUserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle,R.anim.to_middle);
                mContext.finish();


            }
        });

    }


}
