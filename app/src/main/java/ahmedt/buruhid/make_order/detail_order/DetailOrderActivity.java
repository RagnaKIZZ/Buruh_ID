package ahmedt.buruhid.make_order.detail_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.NumberFormat;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.make_order.OrderDoneActivity;
import ahmedt.buruhid.make_order.detail_order.model.PromoModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class DetailOrderActivity extends AppCompatActivity {
    private static final String TAG = "DetailOrderActivity";
    private TextInputEditText edtType, edtCount, edtStartDate, edtEndDate, edtStartHour,
    edtTotalDays, edtCity, edtAddress, edtNameWorker, edtJobdesk, edtPromo;
    private TextInputLayout txtPromo;
    private TextView txtTotalPayment;
    private ImageButton btnBack;
    private Button btnDone;
    private Button btnPromo;
    private Context ctx;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");
    int workerAndDays;
    int promo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_detail_order);
        findView();
    }

    private void findView(){
        edtType = findViewById(R.id.edt_detail_type);
        edtCount = findViewById(R.id.edt_detail_count_worker);
        edtStartDate = findViewById(R.id.edt_detail_startdate);
        edtEndDate = findViewById(R.id.edt_detail_enddate);
        edtStartHour = findViewById(R.id.edt_detail_starthour);
        edtTotalDays = findViewById(R.id.edt_detail_day);
        edtCity = findViewById(R.id.edt_detail_city);
        edtAddress = findViewById(R.id.edt_detail_address);
        edtNameWorker = findViewById(R.id.edt_detail_worker);
        edtJobdesk = findViewById(R.id.edt_detail_jobdesk);
        edtPromo = findViewById(R.id.edt_detail_promotion);
        txtTotalPayment = findViewById(R.id.txt_total_payment);
        txtPromo = findViewById(R.id.txt_detail_promotion);
        btnBack = findViewById(R.id.btn_detail_order_back);
        btnDone = findViewById(R.id.btn_detail_done);
        btnPromo = findViewById(R.id.btn_promo);


        Intent i = getIntent();
        String type = i.getStringExtra("type");
        String startDate = i.getStringExtra("startDate");
        String endDate = i.getStringExtra("endDate");
        String startHour = i.getStringExtra("startHour");
        String nameWorker = i.getStringExtra("name_worker");
        String jobdesk = i.getStringExtra("jobdesk");
        String amountDays = i.getStringExtra("amountDays");
        String city = i.getStringExtra("city");
        String address = i.getStringExtra("address");

        String day = " day";

        if (Integer.parseInt(amountDays) > 1){
            day = " days";
        }

        if (i.getBooleanExtra("isTeam", true)){
            String countWorker = i.getStringExtra("counter");
            workerAndDays = Integer.parseInt(countWorker)*Integer.parseInt(amountDays);
            edtCount.setVisibility(View.VISIBLE);
            edtCount.setText(countWorker+ " People");
        }else {
            edtCount.setVisibility(View.GONE);
            workerAndDays = Integer.parseInt(amountDays);
        }

        edtType.setText(type);
        edtStartDate.setText(startDate);
        edtEndDate.setText(endDate);
        edtStartHour.setText(startHour);
        edtTotalDays.setText(amountDays+day);
        edtCity.setText(city);
        edtAddress.setText(address);
        edtNameWorker.setText(nameWorker);
        edtJobdesk.setText(jobdesk);


        edtType.setEnabled(false);
        edtStartDate.setEnabled(false);
        edtEndDate.setEnabled(false);
        edtStartHour.setEnabled(false);
        edtTotalDays.setEnabled(false);
        edtCity.setEnabled(false);
        edtAddress.setEnabled(false);
        edtNameWorker.setEnabled(false);
        edtJobdesk.setEnabled(false);


        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        final double totalPayment = workerAndDays* Prefs.getDouble(SessionPrefs.CURRENT_PRICE, 0);

        txtTotalPayment.setText(format.format(totalPayment));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtPromo.getText().toString().trim();
                if (!code.isEmpty()){
                    txtPromo.setErrorEnabled(false);
                    getPromotion(id, token, code, String.valueOf(totalPayment));
                }else {
                    txtPromo.setError(getString(R.string.cant_empty));
                }

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailOrderActivity.this, OrderDoneActivity.class));
                finish();
            }
        });

    }

    private void getPromotion(String id, String token_login, String code, String nominal){
        final KProgressHUD hud = new KProgressHUD(ctx);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_LOGOUT)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token_login)
                .addBodyParameter("kode", code)
                .addBodyParameter("nominal", nominal)
                .build()
                .getAsOkHttpResponseAndObject(PromoModel.class, new OkHttpResponseAndParsedRequestListener<PromoModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PromoModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Toasty.warning(ctx,R.string.suc_logout, Toast.LENGTH_SHORT, true).show();
                            }else{
                                Toasty.warning(ctx, R.string.something_wrong, Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Toasty.warning(ctx, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });
    }
}
