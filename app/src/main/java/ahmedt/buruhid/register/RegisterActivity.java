package ahmedt.buruhid.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.regex.Pattern;

import ahmedt.buruhid.R;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private TextView txtToLogin;
    private TextInputLayout txtName, txtEmail, txtPhone, txtPass, txtRePass;
    private CheckBox checkBox;
    private Button btnRegister;
    private Context ctx;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$"
            );

    private static final Pattern PHONE_NUMB
            = Pattern.compile(
            "^[+]?[08][0-9]{10,13}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ctx = this;
        findView();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegister();
            }
        });

        txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void findView() {
        txtToLogin = findViewById(R.id.txt_login_register);
        btnRegister = findViewById(R.id.btn_regist_register);
        txtEmail = findViewById(R.id.txt_email_register);
        txtPhone = findViewById(R.id.txt_hp_register);
        txtName = findViewById(R.id.txt_name_register);
        txtPass = findViewById(R.id.txt_password_register);
        txtRePass = findViewById(R.id.txt_repassword_register);
        checkBox = findViewById(R.id.checkbox_register);
    }

    private void validateRegister() {
        String name, email, phone, password, rePassword;
        name = txtName.getEditText().getText().toString().trim();
        email = txtEmail.getEditText().getText().toString().trim().toLowerCase();
        phone = txtPhone.getEditText().getText().toString().trim();
        password = txtPass.getEditText().getText().toString().trim();
        rePassword = txtRePass.getEditText().getText().toString().trim();

        if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phone.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                PHONE_NUMB.matcher(phone).matches() && PASSWORD_PATTERN.matcher(password).matches() && !rePassword.isEmpty() && rePassword.matches(password) && checkBox.isChecked()) {
            registerUser(name, email, phone, password);
        }

        if (!checkBox.isChecked()) {
            checkBox.setTextColor(Color.RED);
        } else {
            checkBox.setTextColor(Color.BLACK);
        }

        if (name.isEmpty()) {
            txtName.setError(getString(R.string.cant_empty));
        } else {
            txtName.setErrorEnabled(false);
        }

        if (email.isEmpty()) {
            txtEmail.setError(getString(R.string.cant_empty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError(getString(R.string.valid_email));
        } else {
            txtEmail.setErrorEnabled(false);
        }

        if (phone.isEmpty()) {
            txtPhone.setError(getString(R.string.cant_empty));
        } else if (!PHONE_NUMB.matcher(phone).matches()) {
            txtPhone.setError(getString(R.string.valid_number));
        } else {
            txtPhone.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            txtPass.setError(getString(R.string.cant_empty));
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            txtPass.setError(getString(R.string.password_must_contains));
        } else {
            txtPass.setErrorEnabled(false);
        }

        if (rePassword.isEmpty()) {
            txtRePass.setError(getString(R.string.cant_empty));
        } else if (!rePassword.matches(password)) {
            txtRePass.setError(getString(R.string.doesnt_match));
        } else {
            txtRePass.setErrorEnabled(false);
        }
    }

    private void registerUser(String nama, String email, String nope, String password) {
        final KProgressHUD hud = new KProgressHUD(RegisterActivity.this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_REGISTER)
                .addBodyParameter("nama", nama)
                .addBodyParameter("email", email)
                .addBodyParameter("telepon", nope)
                .addBodyParameter("password", password)
                .build()
                .getAsOkHttpResponseAndObject(RegisterModel.class, new OkHttpResponseAndParsedRequestListener<RegisterModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, RegisterModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                Toasty.success(ctx, response.getMsg(), Toast.LENGTH_SHORT, true).show();
                                finish();
                            } else {
                                Toasty.warning(ctx, response.getMsg(), Toast.LENGTH_SHORT, true).show();
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Toasty.error(ctx, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        Log.d(TAG, "onError: " + anError.getErrorDetail());
                    }
                });
    }
}
