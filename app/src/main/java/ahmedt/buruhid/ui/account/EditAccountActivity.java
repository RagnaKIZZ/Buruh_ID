package ahmedt.buruhid.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.regex.Pattern;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.account.modelEdit.EditModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class EditAccountActivity extends AppCompatActivity {
    private static final String TAG = "EditAccountActivity";
    private TextView txtTitle;
    private TextInputLayout txtName, txtEmail, txtPhone, txtNewPass, txtReNewPass, txtPass;
    private Context ctx;
    private Button btnChange;
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
        setContentView(R.layout.activity_edit_account);
        ctx = this;
        findView();
    }

    private void findView() {
        txtTitle = findViewById(R.id.txt_title_profile);
        txtName = findViewById(R.id.txt_name_profile);
        txtEmail = findViewById(R.id.txt_email_profile);
        txtPhone = findViewById(R.id.txt_hp_profile);
        txtNewPass = findViewById(R.id.txt_new_password_profile);
        txtReNewPass = findViewById(R.id.txt_repassword_profile);
        txtPass = findViewById(R.id.txt_password_profile);
        btnChange = findViewById(R.id.btn_edit_profile);

        Intent i = getIntent();
        final String code = i.getStringExtra("code");
        Log.d(TAG, "findView: " + code);
        if (code.equals("1")) {
            txtTitle.setText(R.string.change_name);
            txtName.setVisibility(View.VISIBLE);
        } else if (code.equals("2")) {
            txtTitle.setText(R.string.change_email);
            txtEmail.setVisibility(View.VISIBLE);
        } else if (code.equals("3")) {
            txtTitle.setText(R.string.change_phone_number);
            txtPhone.setVisibility(View.VISIBLE);
        } else if (code.equals("4")) {
            txtTitle.setText(R.string.change_password);
            txtNewPass.setVisibility(View.VISIBLE);
            txtReNewPass.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "findView: ");
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.equals("1")) {
                    changeName(code);
                } else if (code.equals("2")) {
                    changeEmail(code);
                } else if (code.equals("3")) {
                    changePhone(code);
                } else if (code.equals("4")) {
                    changePass(code);
                } else {
                    Toasty.warning(ctx, R.string.something_wrong, Toast.LENGTH_SHORT, true).show();
                    Log.d(TAG, "findView: ");
                }
            }
        });
    }

    private void changeName(String code) {
        String name = txtName.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!name.isEmpty() && !pass.isEmpty()) {
            changeProfile(UrlServer.URL_CHANGE_NAME, "nama", name, pass, SessionPrefs.NAMA, code);
        } else {
            Toasty.warning(ctx, R.string.fill_all, Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changeEmail(String code) {
        String email = txtEmail.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim().toLowerCase();
        if (!email.isEmpty() && !pass.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            changeProfile(UrlServer.URL_CHANGE_EMAIL, "email", email, pass, SessionPrefs.EMAIL, code);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toasty.warning(ctx, R.string.valid_email, Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.warning(ctx, R.string.fill_all, Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changePhone(String code) {
        String phone = txtPhone.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!phone.isEmpty() && !pass.isEmpty() && PHONE_NUMB.matcher(phone).matches()) {
            changeProfile(UrlServer.URL_CHANGE_TELEPON, "telepon", phone, pass, SessionPrefs.TELEPON, code);
        } else if (!PHONE_NUMB.matcher(phone).matches()) {
            Toasty.warning(ctx, R.string.valid_number, Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.warning(ctx, R.string.fill_all, Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changePass(String code) {
        String newPass = txtNewPass.getEditText().getText().toString().trim();
        String rePass = txtReNewPass.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!newPass.isEmpty() && !pass.isEmpty() && rePass.equals(newPass) && PASSWORD_PATTERN.matcher(newPass).matches()) {
            changeProfile(UrlServer.URL_CHANGE_PASSWORD, "password_baru", newPass, pass, "", code);
        } else if (!PASSWORD_PATTERN.matcher(newPass).matches()) {
            Toasty.warning(ctx, R.string.password_must_contains, Toast.LENGTH_LONG, true).show();
        } else if (!rePass.equals(newPass)) {
            Toasty.warning(ctx, R.string.doesnt_match, Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.warning(ctx, R.string.fill_all, Toast.LENGTH_SHORT, true).show();
        }
    }


    private void changeProfile(String url, String param, final String value, String pass, final String prefsParam, final String code) {
        final KProgressHUD hud = new KProgressHUD(ctx);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(url)
                .addBodyParameter("id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addBodyParameter("password", pass)
                .addBodyParameter(param, value)
                .build()
                .getAsOkHttpResponseAndObject(EditModel.class, new OkHttpResponseAndParsedRequestListener<EditModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, EditModel response) {
                        if (okHttpResponse.isSuccessful()) {
                            hud.dismiss();
                            if (response.getCode() == 200) {
                                Toasty.success(ctx, "Change data success!", Toast.LENGTH_SHORT, true).show();
                                if (code != "4") {
                                    Prefs.putString(prefsParam, value);
                                }
                                Intent i = new Intent();
                                i.putExtra("extra", value);
                                setResult(RESULT_OK, i);
                                finish();
                            } else {
                                Log.d(TAG, "onResponse: " + response.getMsg());
                                Toasty.warning(ctx, response.getMsg(), Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.d(TAG, "onError: " + anError.getErrorDetail());
                            Toasty.error(ctx, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        } else {
                            Log.d(TAG, "onError: " + anError.getErrorCode());
                            Log.d(TAG, "onError: " + anError.getErrorBody());
                            Log.d(TAG, "onError: " + anError.getErrorDetail());
                            Toasty.error(ctx, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
