package za.asa_media.awesome_sa.modules_.place_;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.place_.api.FireApiSetting;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class Settings extends AppCompatActivity {

    private Activity mContext = this;
    private Switch mSwitchNotification;
    private ImageView mImageBack;

    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;
    private TextView mTextViewheader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        // calling check notification
        checkNotification();


    }

    private void initViews() {



        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        mTextViewheader = (TextView) findViewById(R.id.textView_header_center);
        mSwitchNotification = (Switch) findViewById(R.id.switch_notification);
        mImageBack = (ImageView) findViewById(R.id.img_back_header);

        // setting values to header
        mTextViewheader.setText("Setting");

        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

            }
        });
        mSwitchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    goForNotificationOnOff("1");
                }
                if (!isChecked) {
                    goForNotificationOnOff("0");
                }
            }
        });


    }

    public void goForNotificationOnOff(String code) {
        new FireApiSetting(mContext).execute(URLListApis.URL_SAVE_NOTIFICATION, code);
    }


    public void checkNotification() {

        if (mSession.getNotificationEnable()) {
            mSwitchNotification.setChecked(true);
        }
        if (!mSession.getNotificationEnable()) {
            mSwitchNotification.setChecked(false);
        }

      /*  new FireApiCheckForNotification(mContext) {
            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                try {
                    Log.d("info", response.toString());
                    if (response != null) {
                        if (response.has("status")) {
                            if (response.optBoolean("status")) {
                                //uihandle.showToast(response.optString(("message")));
                                //   mUiHandleMethods.showToast("" + InitialValueSetUp.favFlag);
                                if (response.optString("message").equals("0")) {
                                    mSwitchNotification.setChecked(false);
                                }
                                if (response.optString("message").equals("1")) {
                                    mSwitchNotification.setChecked(true);
                                }
                            } else {
                                uihandle.showToast(response.optString("message"));
                            }
                        }
                    }

                } catch (Exception e) {
                    uihandle.showToast(e.toString());

                }
            }
        }
                .execute(URLListApis.URL_CHECK_NOTIFICATION, mSession.getDeviceToken());
*/
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
