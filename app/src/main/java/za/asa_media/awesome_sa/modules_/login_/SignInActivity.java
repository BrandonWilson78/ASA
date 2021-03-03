package za.asa_media.awesome_sa.modules_.login_;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.login_.apicall.FireApiGenerateTokenAsync;
import za.asa_media.awesome_sa.modules_.login_.apicall.FireApiLogin;
import za.asa_media.awesome_sa.modules_.login_.apicall.ResponseCallback;
import za.asa_media.awesome_sa.modules_.registered_users.LoggedInUserDashboard;
import za.asa_media.awesome_sa.support.CheckConnectivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;
import za.asa_media.awesome_sa.support.url_keys.URLListApis;

public class SignInActivity extends AppCompatActivity implements ResponseCallback {

    private Activity mContext = this;
    private TextView mTextViewForgetPassword;
    private Button mTextViewDontHaveAccount, mBtnSignIn;
    private EditText mEditTextEmail, mEditTextPassword;
    private TextView mTextRegister;


    private UiHandleMethods uihandle;
    private String web_token;
    //  session object decalration
    private SessionCityOculus mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeStatusBar();
        setContentView(R.layout.activity_sign_in);

        init();
        implementListeners();


    }

    private void removeStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void implementListeners() {
        mTextViewDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uihandle.showToast("Under scruitny");
                uihandle.explicitIntent(SignUpActivity.class);
                //   mContext.finish();

                //  uihandle.explicitIntent(BasicInfo.class);

                //SignInActivity.this.finish();
            }
        });

        mTextViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uihandle.showToast("under Scruitny");
                uihandle.explicitIntent(ForgotPassword.class);
                // SignInActivity.this.finish();
            }
        });

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mEditTextEmail.getText().toString().trim())) {
                    uihandle.showToast("Email Can't be empty");
                } else if (TextUtils.isEmpty(mEditTextPassword.getText().toString().trim())) {
                    uihandle.showToast("Please provide Password");
                } else if (!UiHandleMethods.isValidEmail(mEditTextEmail.getText().toString().trim())) {
                    uihandle.showToast("Please provide valid Email");
                } else if (!(new CheckConnectivity(mContext)).isNetworkAvailable()) {
                    uihandle.showToast("Please Provide Internet Connectivity");
                } else {
                    //uihandle.showToast("Fire Signin Api");
                    try {
                        //     new FireApiGenerateToken(mContext).requestPostJson(URLListApis.URL_GENERATE_TOKEN, new HashMap<String, String>(), "");

                        new FireApiGenerateTokenAsync(mContext).execute(URLListApis.URL_GENERATE_TOKEN);
                        // uihandle.showToast("" + FireApiGenerateToken.getJsonResponse());
                        //  uihandle.explicitIntent(LoggedInUserDashboard.class);
                        //  SignInActivity.this.finish();

                    } catch (Exception e) {
                        uihandle.showToast(e.toString());
                    }
                }
            }
        });
    }

    private void init() {
        String mB = "How to <font color='#1D375E'>register your Business on Google?</font>";
        uihandle = new UiHandleMethods(mContext);
        mSession = new SessionCityOculus(mContext);

        mTextViewDontHaveAccount = (Button) findViewById(R.id.txt_dont_have_account);
        mTextViewForgetPassword = (TextView) findViewById(R.id.txt_forget_password);

        mEditTextEmail = (EditText) findViewById(R.id.editText_email);
        mEditTextPassword = (EditText) findViewById(R.id.editText_passwordd);
        mTextRegister = (TextView) findViewById(R.id.text_how_to_register);
        mBtnSignIn = (Button) findViewById(R.id.btn_signIn);

        // Apply listeners and values to views
        //   uihandle.setUnderLine(mTextViewDontHaveAccount);
        mTextRegister.setText(Html.fromHtml(mB));

        mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       uihandle.explicitIntent(ActivityHowToRegister.class);
                uihandle.openWebLink("https://www.google.com/intl/en/business/");
            }
        });
        mTextViewForgetPassword.setText(Html.fromHtml("<font color='#7F7F7F'>Forgot password? </font><font color='#1D375E'>Click here</font>"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        this.finish();
    }

    @Override
    public void haveResponse(JSONObject response) {
        if (response.has("token")) {
            web_token = response.optString("token").trim();
            mSession.setToken(web_token);

            // fire api for login
            new FireApiLogin(mContext) {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    uihandle.startProgress("trying to login...");

                }

                @Override
                protected void onPostExecute(JSONObject response) {
                    super.onPostExecute(response);
                    try {
                        uihandle.stopProgressDialog();
                        if (response != null) {
                            Log.d("login", response.toString());
                            if (response.has("status")) {
                                uihandle.showToast(response.optString(("message")));

                                if (response.has("data")) {

                                    JSONObject jObjData = response.optJSONObject("data");
                                    String mBusinessStatus = jObjData.optString("business_status");

                                    JSONObject jObj = jObjData.optJSONObject("0");
                                    //         String mImgUrl = jObjData.optString("image_url");
                                    mSession.setIsLogged(true);
                                    mSession.setLoggedStatus(jObj.optString("status"));
                                    mSession.setLoggedId(jObj.optString("id"));
                                    mSession.setLoggedEmail(jObj.optString("email"));
                                    mSession.setUserImageUrl(jObj.optString("image"));
                                    mSession.setLoggedFirstname(jObj.optString("firstname"));
                                    mSession.setLoggedLastname(jObj.optString("lastname"));
                                    mSession.setLoggedCreatedOn(jObj.optString("created_on"));

                                    // InitialValueSetUp.mPlaceId
                                    JSONObject jObj2 = jObjData.optJSONObject("1");
                                    if (jObj2 != null) {
                                        mSession.setBName(jObj2.optString("name"));
                                        mSession.setBCategory(jObj2.optString("category"));
                                        mSession.setBLat(jObj2.optString("latitude"));
                                        mSession.setBLon(jObj2.optString("longitude"));
                                        mSession.setBAddress(jObj2.optString("address"));
                                        mSession.setBPlaceId(jObj2.optString("businessid"));
                                        mSession.setBPhone(jObj2.optString("number"));
                                        mSession.setBWebsiteLink(jObj2.optString("website"));

                                    }

                                    if (mBusinessStatus.equals("1")) {

                                        uihandle.explicitIntent(LoggedInUserDashboard.class);

                                    } else {
                                        uihandle.explicitIntent(SignUpActivityPart2.class);
                                    }
                                    mContext.finish();
                                }


                            } else {
                                uihandle.showToast(response.optString("message"));
                            }
                        }

                    } catch (Exception e) {
                        uihandle.showToast(e.toString());

                    }

                }

            }.execute(URLListApis.URL_LOGIN, mEditTextEmail.getText().toString().trim(), mEditTextPassword.getText().toString().trim(), mSession.getToken());

        }


    }
}
