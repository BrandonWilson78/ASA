package za.asa_media.awesome_sa.paypal_;

import android.app.Activity;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

/**
 * Created by Snow-Dell-05 on 5/23/2017.
 */

public class PayPalPresenter {

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config;
    private Activity mContext;
    private String paymentAmount = "";

    public PayPalPresenter(Activity mContext) {
        this.mContext = mContext;

        /****** Paypal Configuration Object ******/
        config = new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID).acceptCreditCards(true);

        /****** fire intent for paypal configuration ******/
        Intent intent = new Intent(mContext, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        mContext.startService(intent);


    }

    public PayPalConfiguration getPaypalServiceConfig() {
        if (config != null) {
            return config;
        }
        return null;
    }

    public void getPayment(String amount) {
        //Getting the amount from editText
        paymentAmount = amount;
        //Creating a paypal payment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(paymentAmount), "USD", "E-fra", PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(mContext, PaymentActivity.class);
        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        mContext.startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

  /*  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        *//*mContext.startActivity(new Intent(mContext, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));
*//*
                        // for testing purpose after testing uncomment
                        mContext.startActivity(new Intent(mContext, SignInActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }*/

    public void destroy() {
        mContext.stopService(new Intent(mContext, PayPalService.class));

    }

}
