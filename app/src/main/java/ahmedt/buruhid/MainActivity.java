package ahmedt.buruhid;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;

import ahmedt.buruhid.modelToken.FirebaseModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        Prefs.putInt(SessionPrefs.YEAR, calendar.get(Calendar.YEAR));
        Prefs.putInt(SessionPrefs.MONTH, calendar.get(Calendar.MONTH));
        Prefs.putInt(SessionPrefs.DATE, calendar.get(Calendar.DAY_OF_MONTH));
        Prefs.putString(SessionPrefs.isLogin, "1");
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        FirebaseMessaging.getInstance().subscribeToTopic("buruhID");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                if (Prefs.getString(SessionPrefs.TOKEN_FIREBASE, "").isEmpty()){
                    getFirebaseToken(token);
                    Log.d(TAG, "onSuccess: token kosong = "+token);
                }else if (!token.equals(Prefs.getString(SessionPrefs.TOKEN_FIREBASE, ""))){
                    getFirebaseToken(token);
                    Log.d(TAG, "onSuccess: token beda = "+token);
                }else{
                    Log.d(TAG, "onSuccess: token masih sama = "+token);
                }
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_order, R.id.navigation_order, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void getFirebaseToken(final String firebase){
        final KProgressHUD hud = new KProgressHUD(this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_UPDATE_FIREBASE)
                .addBodyParameter("id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addBodyParameter("token_firebase", firebase)
                .build()
                .getAsOkHttpResponseAndObject(FirebaseModel.class, new OkHttpResponseAndParsedRequestListener<FirebaseModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, FirebaseModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Prefs.putString(SessionPrefs.TOKEN_FIREBASE, firebase);
                                Log.d(TAG, "onResponse: suc= "+response.getMsg());
                            }else {
                                Log.d(TAG, "onResponse: "+response.getMsg());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    hud.dismiss();
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(MainActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(MainActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
