package za.asa_media.awesome_sa.modules_.registered_users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;


public class PaymentByPaygate extends AppCompatActivity {

    private Activity mContext = this;
    private WebView webView;
    private WebSettings webSettings;
    private SessionCityOculus mSession;
    private UiHandleMethods uihandle;
    private String purchaseId, userId, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_stripe);

        // calling initloading for initialsetup
        initLoading();

    }

    private void initLoading() {

        mSession = new SessionCityOculus(mContext);
        uihandle = new UiHandleMethods(mContext);

        // webview initialsetup
        webView = (WebView) findViewById(R.id.webView1);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());

        // fetching values to transfer to api
        purchaseId = mSession.getPurchaseId();
        userId = mSession.getLoggedId();
        token = mSession.getToken();

//http://112.196.5.114/asa/api/payment?userid=35&token=984a060b4b535012cd7a410e0718fe&purchaseid=165
        webView.loadUrl("http://ec2-34-212-227-45.us-west-2.compute.amazonaws.com/adminpanel/asa/api/payment?userid=" + userId + "&token=" + token + "&purchaseid=" + purchaseId);

    }

    @Override
    public void onBackPressed() {
        createWarningDialog("Are you sure you want to cancel this transaction?");
    }

    public void createWarningDialog(String msg) {
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!")
                .setContentText(msg)
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // for testing purpose after testing uncomment
                        //  startActivity(new Intent(mContext, PaymentSuccessful.class));
                        // uihandle.explicitIntent(PaymentSuccessful.class);

                        mSession.setAdsDirectTag(false);

                        Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                        mContext.finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //    uihandle.showToast("" + Uri.parse(url).getHost());
            uihandle.showToast("" + url);

            if (url.trim().equals("http://www.done.com/".trim())) {

                mSession.setAdsDirectTag(true);
                Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                mContext.finish();
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

            } else {
                uihandle.showToast("" + url);
                view.loadUrl(url);
            }


            // return true;*/
/*
            if (Uri.parse(url).getHost().equals("http://www.final.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            //   Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            mSession.setAdsDirectTag(true);
            Intent intent = new Intent(mContext, LoggedInUserDashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            mContext.finish();*/
            return true;

        }
    }
}
