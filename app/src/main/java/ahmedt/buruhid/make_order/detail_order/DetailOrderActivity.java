package ahmedt.buruhid.make_order.detail_order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import ahmedt.buruhid.R;
import ahmedt.buruhid.make_order.OrderDoneActivity;
import ahmedt.buruhid.make_order.detail_order.model.MakeOrderModel;
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
    private TextView txtTotalPayment, txtNamePromo, txtIsiPromo, txtMinPromo, txtAfterPromo, txtBefore;
    private ImageButton btnBack;
    private LinearLayout ln_after;
    private CardView cv_promo;
    private Button btnDone;
    private Button btnPromo;
    private Context ctx;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");
    String id_promo = "";
    String kode_promo = "";
    String nama_promo = "";
    String isi_promo = "";
    double totalafter = 0;
    int workerAndDays;
    int select;

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
        cv_promo = findViewById(R.id.cv_promo);
        txtNamePromo = findViewById(R.id.txt_promo_name);
        txtIsiPromo = findViewById(R.id.txt_promo_percent);
        txtMinPromo = findViewById(R.id.txt_promo_min);
        ln_after = findViewById(R.id.ln_promo);
        txtAfterPromo = findViewById(R.id.txt_total_payment_after);
        txtBefore = findViewById(R.id.txt_before_promo);

        ln_after.setVisibility(View.GONE);
        cv_promo.setVisibility(View.GONE);
        txtBefore.setVisibility(View.GONE);

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        final String tukang_id = i.getStringExtra("tukang_id");
        String startDate = i.getStringExtra("startDate");
        final String endDate = i.getStringExtra("endDate");
        String startHour = i.getStringExtra("startHour");
        String nameWorker = i.getStringExtra("name_worker");
        final String jobdesk = i.getStringExtra("jobdesk");
        String amountDays = i.getStringExtra("amountDays");
        String city = i.getStringExtra("city");
        String subdis = i.getStringExtra("subdis");
        String vill = i.getStringExtra("vill");
        String address = i.getStringExtra("address");
        String todayPrice = i.getStringExtra("todayPrice");
        Log.d(TAG, "findView: "+id+ " " +token);
        String day = " day";

        if (Integer.parseInt(amountDays) > 1){
            day = " days";
        }
        String countWorker;
        if (i.getBooleanExtra("isTeam", true)){
            countWorker = i.getStringExtra("counter");
            workerAndDays = Integer.parseInt(countWorker)*Integer.parseInt(amountDays);
            edtCount.setVisibility(View.VISIBLE);
            edtCount.setText(countWorker+ " People");
        }else {
            countWorker = i.getStringExtra("counter");
            edtCount.setVisibility(View.GONE);
            workerAndDays = Integer.parseInt(amountDays);
        }

        final String alamat = address+", "+vill+", "+subdis+", "+city;
        final String startdate = startDate+" "+startHour;

        edtType.setText(type);
        edtStartDate.setText(startDate);
        edtEndDate.setText(endDate);
        edtStartHour.setText(startHour);
        edtTotalDays.setText(amountDays+day);
        edtCity.setVisibility(View.GONE);
        edtAddress.setText(address+", "+vill+", "+subdis+", "+city);
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

        final double totalPayment = workerAndDays* Double.parseDouble(todayPrice);
        Random rand = new Random();
        int selected = rand.nextInt(999);
        select = selected;
        final double totalPaymentNew = totalPayment+((double) selected);

        txtTotalPayment.setText(format.format(totalPaymentNew));

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
                    getPromotion(id, token, code, String.valueOf(totalPayment), totalPayment);
                }else {
                    txtPromo.setError(getString(R.string.cant_empty));
                }

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder(id, token, tukang_id, alamat, jobdesk, startdate, endDate, String.valueOf(totalPaymentNew), String.valueOf(totalafter),id_promo);
            }
        });

    }

    private void getPromotion(String id, String token_login, String code, String nominal, final double total_before){
        Locale locale = new Locale("in", "ID");
        final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        final KProgressHUD hud = new KProgressHUD(ctx);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_PROMO)
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
                                id_promo = response.getData().getId();
                                nama_promo = response.getData().getNamaPromo();
                                isi_promo = response.getData().getIsiPromo();
                                kode_promo = response.getData().getKodePromo();
                                String min = response.getData().getMinHarga();
                                double isipromo = 100*Double.parseDouble(isi_promo);
                                totalafter = total_before-(total_before*Double.parseDouble(isi_promo))+((double) select);
                                double minPro = Double.parseDouble(min);
                                String form = format.format(minPro);
                                String afterPromo = format.format(totalafter);
                                int isiipromo = (int) isipromo;
                                cv_promo.setVisibility(View.VISIBLE);
                                txtNamePromo.setText(nama_promo);
                                txtTotalPayment.setPaintFlags(txtTotalPayment.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                txtAfterPromo.setText(afterPromo);
                                txtBefore.setVisibility(View.VISIBLE);
                                ln_after.setVisibility(View.VISIBLE);
                                txtMinPromo.setText(R.string.min_trans+form);
                                txtIsiPromo.setText(R.string.disc+String.valueOf(isiipromo)+"%");
                                Toasty.success(ctx,response.getMsg(), Toast.LENGTH_SHORT, true).show();
                            }else{
                                Toasty.warning(ctx, response.getMsg(), Toast.LENGTH_SHORT, true).show();
                                txtBefore.setVisibility(View.GONE);
                                ln_after.setVisibility(View.GONE);
                                cv_promo.setVisibility(View.GONE);
                                txtTotalPayment.setPaintFlags(txtTotalPayment.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        txtBefore.setVisibility(View.GONE);
                        ln_after.setVisibility(View.GONE);
                        cv_promo.setVisibility(View.GONE);
                        txtTotalPayment.setPaintFlags(txtTotalPayment.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
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

    private void makeOrder(String id, String token, String tukang_id, String alamat, String jobdesk, String start, String end,
    String nominal, String angka, String promoid){
        final KProgressHUD hud  = new KProgressHUD(ctx);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_MAKE_ORDER)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("tukang_id", tukang_id)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("jobdesk", jobdesk)
                .addBodyParameter("start_date", start)
                .addBodyParameter("end_date", end)
                .addBodyParameter("nominal", nominal)
                .addBodyParameter("nominal_promo", angka)
                .addBodyParameter("promo_id", promoid)
                .build()
                .getAsOkHttpResponseAndObject(MakeOrderModel.class, new OkHttpResponseAndParsedRequestListener<MakeOrderModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, MakeOrderModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                Toasty.success(ctx, response.getMsg(), Toast.LENGTH_SHORT, true).show();
                                startActivity(new Intent(DetailOrderActivity.this, OrderDoneActivity.class));
                                finish();
                            }else{
                                Toasty.warning(ctx, response.getMsg(), Toast.LENGTH_SHORT, true).show();
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
