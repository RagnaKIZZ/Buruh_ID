package ahmedt.buruhid.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.MainActivity;
import ahmedt.buruhid.R;
import ahmedt.buruhid.intro.IntroActivity;
import ahmedt.buruhid.splash.CurrentPriceModel.PriceModel;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progress_bar);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Prefs.getString(SessionPrefs.isLogin, "").matches("1")){
                    getPrice(Prefs.getString(SessionPrefs.U_ID, ""), Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""));
                }else {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                }
            }
        }, 1000);
    }

    private void getPrice(String id, String token){
        AndroidNetworking.post(UrlServer.URL_PRICE)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .build()
                .getAsOkHttpResponseAndObject(PriceModel.class, new OkHttpResponseAndParsedRequestListener<PriceModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PriceModel response) {
                        progressBar.setVisibility(View.GONE);
                        if(okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Prefs.putString(SessionPrefs.CURRENT_PRICE, response.getData().getHarga());
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }else{
                                Toasty.warning(SplashActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        if (anError.getErrorCode() != 0){
                            startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                            finish();
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(SplashActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            finish();
                            Log.d(TAG, "onError: "+anError.getErrorCode());
                            Log.d(TAG, "onError: "+anError.getErrorBody());
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(SplashActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
