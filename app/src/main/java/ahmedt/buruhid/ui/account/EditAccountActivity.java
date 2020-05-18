package ahmedt.buruhid.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
    }

    private void findView(){
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
        if (code.equals("1")){
            txtTitle.setText("Change Name");
            txtName.setVisibility(View.VISIBLE);
        }else if (code.equals("2")){
            txtTitle.setText("Change Email");
            txtEmail.setVisibility(View.VISIBLE);
        }else if(code.equals("3")){
            txtTitle.setText("Change Phone Number");
            txtPhone.setVisibility(View.VISIBLE);
        }else if (code.equals("4")){
            txtTitle.setText("Change Password");
            txtNewPass.setVisibility(View.VISIBLE);
            txtReNewPass.setVisibility(View.VISIBLE);
        }else{
            Log.d(TAG, "findView: ");
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.equals("1")){
                   changeName();
                }else if (code.equals("2")){
                 changeEmail();
                }else if(code.equals("3")){
                  changePhone();
                }else if (code.equals("4")){
                  changePass();
                }else{
                    Toasty.warning(ctx, R.string.something_wrong, Toast.LENGTH_SHORT, true).show();
                    Log.d(TAG, "findView: ");
                }
            }
        });
    }

    private void changeName(){
        String name = txtName.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!name.isEmpty() && !pass.isEmpty()){
            changeProfile(UrlServer.URL_CHANGE_NAME, "nama", name, pass, SessionPrefs.NAMA);
        }else{
            Toasty.warning(ctx, "Please fill all the form!", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changeEmail(){
        String email = txtEmail.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!email.isEmpty() && !pass.isEmpty()){
            changeProfile(UrlServer.URL_CHANGE_EMAIL, "email", email, pass, SessionPrefs.EMAIL);
        }else{
            Toasty.warning(ctx, "Please fill all the form!", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changePhone(){
        String phone = txtPhone.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!phone.isEmpty() && !pass.isEmpty()){
            changeProfile(UrlServer.URL_CHANGE_TELEPON, "telepon", phone, pass, SessionPrefs.TELEPON);
        }else{
            Toasty.warning(ctx, "Please fill all the form!", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changePass(){
        String newPass = txtNewPass.getEditText().getText().toString().trim();
        String rePass = txtReNewPass.getEditText().getText().toString().trim();
        String pass = txtPass.getEditText().getText().toString().trim();
        if (!newPass.isEmpty() && !pass.isEmpty() && rePass.equals(newPass)){
            changeProfile(UrlServer.URL_CHANGE_TELEPON, "telepon", newPass, pass, SessionPrefs.TELEPON);
        }else if (!rePass.equals(newPass)){
            Toasty.warning(ctx, R.string.doesnt_match, Toast.LENGTH_SHORT, true).show();
        }
        else{
            Toasty.warning(ctx, "Please fill all the form!", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changeProfile(String url, String param, final String value, String pass, final String prefsParam){
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
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Toasty.success(ctx, "Change data success!", Toast.LENGTH_SHORT, true).show();
                                Prefs.putString(prefsParam, value);
                                finish();
                            }else{
                                Toasty.warning(ctx, R.string.something_wrong, Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0){
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(ctx, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d(TAG, "onError: "+anError.getErrorCode());
                            Log.d(TAG, "onError: "+anError.getErrorBody());
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(ctx, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });

    }
}
