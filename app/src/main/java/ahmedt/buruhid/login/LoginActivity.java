package ahmedt.buruhid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.MainActivity;
import ahmedt.buruhid.R;
import ahmedt.buruhid.register.RegisterActivity;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TextView txtToRegist, txtToForgot;
    private Button btnLogin;
    private TextInputLayout edtEmail, edtPassword;
    private TextInputEditText txtDone;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        findView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txtToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void findView(){
        txtToForgot = findViewById(R.id.txt_forgot_password);
        txtToRegist = findViewById(R.id.txt_register_login);
        btnLogin = findViewById(R.id.btn_login_login);
        edtEmail = findViewById(R.id.txt_email_login);
        edtPassword = findViewById(R.id.txt_password_login);
        txtDone = findViewById(R.id.edt_pswd_login);

        txtDone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    login();
                    return true;
                }
                return false;
            }
        });


    }

    private void login(){
        String email = edtEmail.getEditText().getText().toString().trim();
        String password = edtPassword.getEditText().getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           loginUser(email, password);
        }

        if (email.isEmpty()){
            edtEmail.setError(getString(R.string.cant_empty));
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError(getString(R.string.valid_email));
        }else {
            edtEmail.setErrorEnabled(false);
            edtEmail.setError(null);
        }

        if (password.isEmpty()){
            edtPassword.setError(getString(R.string.cant_empty));
        }else {
            edtPassword.setErrorEnabled(false);
        }
    }

    private void loginUser(String email, String password){
        final KProgressHUD hud = new KProgressHUD(context);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .build()
                .getAsOkHttpResponseAndObject(LoginModel.class, new OkHttpResponseAndParsedRequestListener<LoginModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, LoginModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Prefs.putString(SessionPrefs.U_ID, response.getData().getUserId());
                                Prefs.putString(SessionPrefs.NAMA, response.getData().getNama());
                                Prefs.putString(SessionPrefs.EMAIL, response.getData().getEmail());
                                Prefs.putString(SessionPrefs.TELEPON, response.getData().getTelepon());
                                Prefs.putString(SessionPrefs.FOTO, response.getData().getFoto());
                                Prefs.putString(SessionPrefs.TOKEN_LOGIN, response.getData().getTokenLogin());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }else{
                                Toasty.warning(context, response.getMsg(), Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Toasty.warning(context, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });
    }
}
