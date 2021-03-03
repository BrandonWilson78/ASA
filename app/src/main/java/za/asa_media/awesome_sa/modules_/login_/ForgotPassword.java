package za.asa_media.awesome_sa.modules_.login_;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.login_.apicall.FireApiForgotPassword;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class ForgotPassword extends AppCompatActivity {

    private Activity mContext = this;

    private TextView mTextViewBack;
    private Button mBtnResePassword;
    private EditText mEditTextEmail;
    private UiHandleMethods uihandle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeStatusBar();
        setContentView(R.layout.activity_forgot_password);
        initViews();

    }

    private void removeStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initViews() {

        uihandle = new UiHandleMethods(mContext);

        mTextViewBack = (TextView) findViewById(R.id.txt_forgot_password_back);
        mEditTextEmail = (EditText) findViewById(R.id.editText_forgot_password_email_id);
        mBtnResePassword = (Button) findViewById(R.id.btn_forgot_password_reset_password);

        // apply listener and implemen values

        mBtnResePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mEditTextEmail.getText().toString().trim())) {

                    if (UiHandleMethods.isValidEmail(mEditTextEmail.getText().toString().trim())) {
                        if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                            uihandle.showToast("Check your Internet Connectivity");
                        } else {
                            // Todo: Fire SignUp api here
                            new FireApiForgotPassword(mContext).
                                    execute(URLListApis.URL_FORGOT_PASSWORD, mEditTextEmail.getText().toString().trim());
                        }
                    } else {
                        uihandle.showToast("Invalid Email");
                    }
                } else {
                    uihandle.showToast("Please provide Email");
                }
            }
        });


        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgotPassword.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ForgotPassword.this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}
