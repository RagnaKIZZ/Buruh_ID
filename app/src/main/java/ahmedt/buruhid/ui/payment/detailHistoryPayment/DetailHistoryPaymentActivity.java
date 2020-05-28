package ahmedt.buruhid.ui.payment.detailHistoryPayment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.payment.modelPayment.DataItem;
import ahmedt.buruhid.utils.UrlServer;

public class DetailHistoryPaymentActivity extends AppCompatActivity {
    private static final String TAG = "DetailPaymentActivity";
    private TextView txtTgl, txtCode, txtNominal, txtStatus;
    private ImageView img_proof;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_payment);
        findView();
    }

    private void findView(){
        txtTgl = findViewById(R.id.txt_tgl_payment);
        txtCode = findViewById(R.id.txt_code_payment);
        txtNominal = findViewById(R.id.txt_price_payment);
        txtStatus = findViewById(R.id.txt_status_payment);
        btnBack = findViewById(R.id.btn_detail_order_back);
        img_proof = findViewById(R.id.proof_img);
        callMethode();
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
        int color = 0;

        if (item.getStatusPembayaran().matches("2")){
            status = getString(R.string.acc_t_bID);
            color = Color.GREEN;
        }else if (item.getStatusPembayaran().matches("4")){
            status = getString(R.string.ordercancel);
            color = Color.RED;
        }else if (item.getStatusPembayaran().matches("5")){
            status = getString(R.string.acc_t_work);
            color = Color.GREEN;
        }else{
            status = "error!";
            color = Color.RED;
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
}
