package ahmedt.buruhid.ui.home.survery;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hsalf.smileyrating.SmileyRating;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.R;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class SurveyActivity extends AppCompatActivity {
    private TextInputLayout txtComment;
    private Button btnDone;
    private ImageButton btnBack;
    private SmileyRating rating;
    private TextInputEditText edtKomen;
    int count_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        findView();
    }

    private void findView(){
        txtComment = findViewById(R.id.txt_comment);
        btnDone = findViewById(R.id.btn_done);
        btnBack = findViewById(R.id.btn_back);
        rating =  findViewById(R.id.smile_rating);
        edtKomen = findViewById(R.id.edt_komen);

        rating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                count_rating = type.getRating();
            }
        });

        edtKomen.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if (!txtComment.getEditText().getText().toString().trim().isEmpty() && count_rating > 0){
                        sendRating(edtKomen.getText().toString().trim(), String.valueOf(count_rating));
                    }else{
                        Toasty.warning(SurveyActivity.this, getString(R.string.fill_all)).show();
                    }
                    return true;
                }
                return false;
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtComment.getEditText().getText().toString().trim().isEmpty() && count_rating > 0){
                    sendRating(txtComment.getEditText().getText().toString().trim(), String.valueOf(count_rating));
                }else{
                    Toasty.warning(SurveyActivity.this, getString(R.string.fill_all)).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void sendRating(String komen, String rating){
        final KProgressHUD hud = new KProgressHUD(this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_GIVE_SARAN)
                .addBodyParameter("user_id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addBodyParameter("komen", komen)
                .addBodyParameter("rating", rating)
                .build()
                .getAsOkHttpResponseAndObject(SurveyModel.class, new OkHttpResponseAndParsedRequestListener<SurveyModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, SurveyModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Toasty.success(SurveyActivity.this, getString(R.string.thanks)).show();
                                finish();
                            }else{
                                Toasty.warning(SurveyActivity.this, getString(R.string.fill_all)).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.warning(SurveyActivity.this, getString(R.string.server_error)).show();
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.warning(SurveyActivity.this, getString(R.string.cek_internet)).show();
                        }
                    }
                });
    }
}
