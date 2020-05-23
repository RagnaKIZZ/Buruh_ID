package ahmedt.buruhid.ui.payment.detailPayment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.payment.modelPayment.DataItem;

public class DetailPaymentActivity extends AppCompatActivity {
    private TextView txtTgl, txtCode, txtNominal, txtStatus, txtUntil;
    private CardView cd_select;
    private ImageView img_proof;
    private Button btnHelp, btnPay;
    private ImageButton btnBack;
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
    }

    private void callMethode(){
        Intent i = getIntent();
        DataItem item = i.getParcelableExtra("data_item");
        String payment_id = item.getId();
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

        btnPay.setText(pay);
        btnPay.setBackground(getDrawable(bg));
        btnPay.setEnabled(isClick);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailPaymentActivity.this, "Help", Toast.LENGTH_SHORT).show();
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
}
