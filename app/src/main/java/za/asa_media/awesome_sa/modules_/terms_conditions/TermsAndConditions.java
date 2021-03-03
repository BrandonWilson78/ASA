package za.asa_media.awesome_sa.modules_.terms_conditions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class TermsAndConditions extends AppCompatActivity {


    private Activity mContext = this;
    private TextView mTextViewHeading;

    private UiHandleMethods uihandle;
    private Toolbar mToolbar;
    private ImageView mImageBackArrow;
    private SessionCityOculus mSession;

    private TextView mTextViewPrivacyTerms, mTextViewTermsFullText;
    /*
  {
  "status": true,
  "message": "Terms & Condition",
  "data": [
  {
  "id": "1",
  "terms_detail": "terms & conditions"
  }]}   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.new_app_blue_color));
        setContentView(R.layout.activity_terms_and_conditions);


        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        mToolbar = (Toolbar) findViewById(R.id.myToolbar);
        mTextViewHeading = mToolbar.findViewById(R.id.textView_header_center);
        mImageBackArrow = mToolbar.findViewById(R.id.img_back_header);
        mTextViewPrivacyTerms = (TextView) findViewById(R.id.terms_privacy);
        mTextViewTermsFullText = (TextView) findViewById(R.id.txt_post_new_ads_description);


        mTextViewHeading.setText("Terms & Conditions");
        // mTextViewPrivacyTerms.setText("Privacy & Terms");

        mTextViewHeading.setGravity(Gravity.CENTER_HORIZONTAL);
        mImageBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TermsAndConditions.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        //  Calling Terms and Conditions Api
        new FireApiGetTermConditions(mContext, mTextViewTermsFullText)
                .execute(URLListApis.URL_TERMS_AND_CONDITIONS);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSession.setAdsDirectTag(false);
        Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mContext.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
