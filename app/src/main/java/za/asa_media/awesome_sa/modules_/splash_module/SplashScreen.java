package za.asa_media.awesome_sa.modules_.splash_module;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.modules_.login_.PreLoginActivity;
import za.asa_media.awesome_sa.support.UiHandleMethods;
import za.asa_media.awesome_sa.support.session.SessionCityOculus;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 3000;
    private UiHandleMethods uihandle;
    private SessionCityOculus mSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeStatusBar();
        setContentView(R.layout.activity_splash_screen);

        uihandle = new UiHandleMethods(this);
        mSession = new SessionCityOculus(this);

        goToIntialScreen();

    }

    private void removeStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void goToIntialScreen() {
        boolean isLogin = false;
        try {
            //  isLogin = mSession.isLogged();
            // userType = csfm.getUserType();
            /*
            if (isLogin) {
            uihandle.explicitIntent(LoggedInUserDashboard.class);
            this.finish();
            }
            else {
            */

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    //ActivityUnRegisteredDefault
                    Intent intent = new Intent(SplashScreen.this, PreLoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    finish();
                }


            }, SPLASH_TIME_OUT);
            //   }

            // }

          /*  Log.d("b",""+isLogin+""+csfm.getUserType()+""+csfm.getUserId());
           // startActivity(new Intent(this, activityClass));
            Toast.makeText(this,""+isLogin+""+csfm.getUserType()+""+csfm.getUserId(),Toast.LENGTH_LONG).show();
*/
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }


    @Override
    public void onBackPressed() {


    }
}
