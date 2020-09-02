package ahmedt.buruhid.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.Main.MainActivity;
import ahmedt.buruhid.R;
import ahmedt.buruhid.UpdateHelper;
import ahmedt.buruhid.intro.IntroActivity;
import ahmedt.buruhid.utils.SessionPrefs;

public class SplashActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckLinstener {
    private static final String TAG = "SplashActivity";
    private ProgressBar progressBar;
    int param = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();


        Toast.makeText(this, R.string.announcebeta, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String isLogin = Prefs.getString(SessionPrefs.isLogin, "0");
                Log.d(TAG, "run: " + isLogin);
                if (param == 0) {
                    if (isLogin.matches("1")) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                        finish();
                    }
                }
            }
        }, 2000);

    }

    @Override
    public void onUpdateCheckListener(final String urlApp) {
        param = 1;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.newupdate);
        alert.setMessage(R.string.plzupdate)
                .setCancelable(false)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlApp)));
                        }
                    }
                })
                .setNegativeButton(R.string.nothx, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
        alert.show();
    }
}
