package ahmedt.buruhid.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.MainActivity;
import ahmedt.buruhid.R;
import ahmedt.buruhid.intro.IntroActivity;
import ahmedt.buruhid.utils.SessionPrefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Prefs.getString(SessionPrefs.isLogin, "").matches("1")){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                }
            }
        }, 2000);


    }
}
