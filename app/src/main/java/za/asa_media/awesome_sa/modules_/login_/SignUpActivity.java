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
import za.asa_media.awesome_sa.modules_.login_.apicall.FireApiSignUp;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class SignUpActivity extends AppCompatActivity {


    private Activity mContext = this;

    private TextView mTextViewHaveAccount;
    private Button mBtnSignUp;
    private EditText mEditTextFirstname, mEditTextLastname, mEditTextEmail, mEditTextPassword, mEditTextConfirmPassword;
    private UiHandleMethods uihandle;
    private String mFirstname, mLastname, mEmail, mPassword, mConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        removeStatusBar();
        initViews();
    }

    private void removeStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initViews() {
        uihandle = new UiHandleMethods(mContext);

        mTextViewHaveAccount = (TextView) findViewById(R.id.txt_have_account);
        mEditTextFirstname = (EditText) findViewById(R.id.editText_first_name);
        mEditTextLastname = (EditText) findViewById(R.id.editText_last_name);
        mEditTextEmail = (EditText) findViewById(R.id.editText_email_id);
        mEditTextPassword = (EditText) findViewById(R.id.editText_passwordd);
        mEditTextConfirmPassword = (EditText) findViewById(R.id.editText_confirm_pass);

        mBtnSignUp = (Button) findViewById(R.id.btn_signup);
        // apply listener and implemen values

        uihandle.setUnderLine(mTextViewHaveAccount);
        mTextViewHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  uihandle.explicitIntentFromLeft(SignInActivity.class);
                SignUpActivity.this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);


            }
        });


        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFieldForEmpty()) {
                    if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                        uihandle.showToast("Check your Internet Connectivity");
                    } else {
                        // Fire Api
                        new FireApiSignUp(mContext).execute(URLListApis.URL_SIGNUP,
                                mEditTextFirstname.getText().toString().trim(),
                                mEditTextLastname.getText().toString().trim(),
                                mEditTextEmail.getText().toString().trim(),
                                mEditTextPassword.getText().toString().trim());
                    }
                }
            }
        });
    }

    private boolean checkFieldForEmpty() {
        boolean mFlag = false;

        mFirstname = mEditTextFirstname.getText().toString().trim();
        mLastname = mEditTextLastname.getText().toString().trim();
        mEmail = mEditTextEmail.getText().toString().trim();
        mPassword = mEditTextPassword.getText().toString().trim();
        mConfirmPassword = mEditTextConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mFirstname)) {
            mEditTextFirstname.setError("Please Provide Firstname");
            mFlag = true;
        } else if (TextUtils.isEmpty(mLastname)) {
            mEditTextLastname.setError("Please Provide Lastname");
            mFlag = true;
        } else if (TextUtils.isEmpty(mEmail)) {
            mEditTextEmail.setError("Please Provide Email");
            mFlag = true;
        } else if (!UiHandleMethods.isValidEmail(mEmail)) {
            uihandle.showToast("Invalid Email");
        } else if (TextUtils.isEmpty(mPassword)) {
            mEditTextPassword.setError("Password can't be Empty");
            mFlag = true;
        } else if (TextUtils.isEmpty(mConfirmPassword)) {
            mEditTextConfirmPassword.setError("Confirm Pass can't be Empty");
            mFlag = true;
        } else if (!mConfirmPassword.equals(mPassword)) {
            mEditTextConfirmPassword.setError("Password did not matched");
            mFlag = true;
        }

        return mFlag;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        uihandle = null;
        this.finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }
}
