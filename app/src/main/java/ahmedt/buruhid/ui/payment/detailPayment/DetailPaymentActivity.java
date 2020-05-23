package ahmedt.buruhid.ui.payment.detailPayment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.payment.detailPayment.modelPay.PayModel;
import ahmedt.buruhid.ui.payment.modelPayment.DataItem;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class DetailPaymentActivity extends AppCompatActivity {
    private static final String TAG = "DetailPaymentActivity";
    private TextView txtTgl, txtCode, txtNominal, txtStatus, txtUntil;
    private CardView cd_select;
    private ImageView img_proof;
    private Button btnHelp, btnPay;
    private ImageButton btnBack;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_payment);
        findView();
        callMethode();
    }

    private void findView(){
        txtTgl = findViewById(R.id.txt_tgl_payment);
        txtCode = findViewById(R.id.txt_code_payment);
        txtNominal = findViewById(R.id.txt_price_payment);
        txtStatus = findViewById(R.id.txt_status_payment);
        txtUntil = findViewById(R.id.txt_workuntil_payment);
        cd_select = findViewById(R.id.cd_proof);
        btnHelp = findViewById(R.id.btn_help);
        btnPay = findViewById(R.id.btn_pay);
        btnBack = findViewById(R.id.btn_detail_order_back);
        img_proof = findViewById(R.id.proof_img);
    }

    private void callMethode(){
        Intent i = getIntent();
        DataItem item = i.getParcelableExtra("data_item");
        final String payment_id = item.getId();
        String nominal = item.getNominal();
        String tanggal = item.getCreateDate();
        String until = item.getEndDate();
        String status = "";
        String bukti = item.getBuktiPembayaran();
        String orderID = item.getOrderId();
        String code = item.getCodePembayaran();
        int pay ;
        boolean isClick;
        int bg ;
        int color = 0;

        if (item.getStatusPembayaran().matches("0")){
            status = "Waiting for payment..";
            color = Color.GRAY;
            pay = R.string.pay;
            isClick = true;
            bg = R.drawable.bg_call;
        }else if (item.getStatusPembayaran().matches("1")){
            status = "On process...";
            color = Color.parseColor("#ffd600");
            pay = R.string.proses;
            isClick = false;
            bg = R.drawable.bg_message;
        }else if (item.getStatusPembayaran().matches("2")){
            status = "Accepted to workers";
            color = Color.GREEN;
            pay = R.string.pay;
            bg = R.drawable.bg_call;
            isClick = true;
        }else if (item.getStatusPembayaran().matches("3")){
            status = "Rejected! Please send clear proof of payment!";
            color = Color.RED;
            pay = R.string.pay;
            bg = R.drawable.bg_call;
            isClick = true;
        }else{
            status = "error!";
            color = Color.RED;
            pay = R.string.pay;
            isClick = false;
            bg = R.drawable.bg_message;
        }

        if (bukti.isEmpty()){
            Glide.with(this)
                    .load(R.drawable.blankimg)
                    .into(img_proof);
        }else {
            Glide.with(this)
                    .load(UrlServer.URL_FOTO_PAYMENT+bukti)
                    .into(img_proof);
        }

        cd_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(DetailPaymentActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(720, 720)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        btnPay.setText(pay);
        btnPay.setBackground(getDrawable(bg));
        btnPay.setEnabled(isClick);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bayarOrder(payment_id);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailPaymentActivity.this, "Help", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        double harga = Double.parseDouble(nominal);

        txtStatus.setText(status);
        txtStatus.setTextColor(color);
        txtNominal.setText(format.format(harga));
        txtCode.setText(code);
        parseDate("yyyy-MM-dd HH:mm:ss", "dd MMM yyyy", tanggal, txtTgl);
        parseDate("yyyy-MM-dd HH:mm:ss", "dd MMM yyyy HH:mm", until, txtUntil);
    }

    private void parseDate(String pattern, String pattern2,String date, TextView edt){
        SimpleDateFormat time = new SimpleDateFormat(pattern);
        try{
            Date waktu = time.parse(date);
            SimpleDateFormat format1 = new SimpleDateFormat(pattern2);
            String realTime = format1.format(waktu);
            edt.setText(realTime);
        }catch(Exception e){
            Log.d("ASD", "onBindViewHolder: "+e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ImagePicker.REQUEST_CODE){
                file = ImagePicker.Companion.getFile(data);
                img_proof.setImageURI(Uri.fromFile(file));
            }
        }
    }

    private void bayarOrder(String id){
        final KProgressHUD hud = new KProgressHUD(this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.upload(UrlServer.URL_PAY_ORDER)
                .addMultipartParameter("user_id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addMultipartParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addMultipartParameter("payment_id", id)
                .addMultipartFile("foto", file)
                .build()
                .getAsOkHttpResponseAndObject(PayModel.class, new OkHttpResponseAndParsedRequestListener<PayModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PayModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                setResult(RESULT_OK);
                                finish();
                                Toasty.success(DetailPaymentActivity.this, response.getMsg(), Toasty.LENGTH_SHORT).show();
                            }else{
                                Toasty.warning(DetailPaymentActivity.this, response.getMsg(), Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0){
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(DetailPaymentActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d(TAG, "onError: "+anError.getErrorCode());
                            Log.d(TAG, "onError: "+anError.getErrorBody());
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(DetailPaymentActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
