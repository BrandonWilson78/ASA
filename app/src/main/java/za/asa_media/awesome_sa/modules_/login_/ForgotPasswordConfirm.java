package za.asa_media.awesome_sa.modules_.login_;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.login_.apicall.FireApForgotPassConfirm;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

/**
 * Created by Snow-Dell-05 on 5/19/2017.
 */

public class ForgotPasswordConfirm extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextPasswordCode;
    private EditText mEditTextConfirmPassword, mEditTextNewPassword;
    private Button mButtonUpdatePassword;
    private TextView mTextViewBack;
    private UiHandleMethods mUiHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeStatusBar();
        setContentView(R.layout.layout_item_forgot_password_code);
        init();
    }

    private void removeStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void init() {
        mUiHandler = new UiHandleMethods(this);
        mEditTextPasswordCode = (EditText) findViewById(R.id.editText_forgot_password_code);
        mEditTextNewPassword = (EditText) findViewById(R.id.editText_forgot_password_new_pass);
        mEditTextConfirmPassword = (EditText) findViewById(R.id.editText_forgot_password_confirm_pass);
        mButtonUpdatePassword = (Button) findViewById(R.id.btn_forgot_password_update);
        mTextViewBack = (TextView) findViewById(R.id.txt_forgot_password_back);

        //set Listner
        mButtonUpdatePassword.setOnClickListener(this);
        mTextViewBack.setOnClickListener(this);

    }


    private void validateFields() {

        String mCode = mEditTextPasswordCode.getText().toString();
        String mNewPassword = mEditTextNewPassword.getText().toString();
        String mConfirmPassword = mEditTextConfirmPassword.getText().toString();


        if (!TextUtils.isEmpty(mCode) && !TextUtils.isEmpty(mNewPassword) && !TextUtils.isEmpty(mConfirmPassword)) {
            if (mNewPassword.equals(mConfirmPassword)) {
                new FireApForgotPassConfirm(this).execute(URLListApis.URL_FORGOT_PASSWORD, mNewPassword, mConfirmPassword, mCode);
            } else {
                mEditTextConfirmPassword.setError("Password not matches");
            }
        } else {
            mUiHandler.showToast("Please Fill all fields");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_forgot_password_back:
                mUiHandler.explicitIntentFromLeft(ForgotPassword.class);
                this.finish();
                break;
            case R.id.btn_forgot_password_update:
                validateFields();
                break;
        }

    }
}
