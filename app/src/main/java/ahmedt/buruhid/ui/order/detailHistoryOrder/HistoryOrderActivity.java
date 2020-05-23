package ahmedt.buruhid.ui.order.detailHistoryOrder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem;
import ahmedt.buruhid.utils.UrlServer;

public class HistoryOrderActivity extends AppCompatActivity {
    private static final String TAG = "HistoryOrderActivity";
    private TextView txtName, txtType, txtStatus, txtCode, txtPrice, txtTgl;
    private TextInputEditText edtStart, edtEnd, edtAddress, edtJob, edtHour, edtTotal;
    private ImageButton btnBack;
    private ImageView imgWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        findView();
        callMethode();
    }

    private void findView(){
        txtName = findViewById(R.id.txt_nama_your_order);
        txtType = findViewById(R.id.txt_jenis_your_order);
        txtStatus = findViewById(R.id.txt_status_order);
        txtCode = findViewById(R.id.txt_code_order);
        txtPrice = findViewById(R.id.txt_total_payment);
        edtStart = findViewById(R.id.edt_detail_startdate);
        edtEnd = findViewById(R.id.edt_detail_enddate);
        edtAddress = findViewById(R.id.edt_detail_address);
        edtJob = findViewById(R.id.edt_detail_jobdesk);
        imgWorker = findViewById(R.id.img_your_order);
        edtHour = findViewById(R.id.edt_start_hour);
        edtTotal = findViewById(R.id.edt_detail_day);
        btnBack = findViewById(R.id.btn_detail_order_back);
        txtTgl = findViewById(R.id.txt_tgl_order);
    }

    private void callMethode(){
        Intent i = getIntent();
        final DataItem item = i.getParcelableExtra("data_item");
        String nama = item.getNama();
        String alamat = item.getAlamat();
        String anggota = item.getAnggota();
        String status = "";
        String foto = item.getFoto();
        final String telepon = item.getTelepon();
        String code = item.getCodeOrder();
        String price = "";
        if (item.getHargaPromo().equals("0")){
            price = item.getHarga();
        }else{
            price = item.getHargaPromo();
        }
        String startDate = item.getStartDate();
        String endDate = item.getEndDate();
        String jobdesk = item.getJobdesk();
        String orderdate = item.getOrderDate();
        String type = "";
        int color = 0;

        if (anggota.matches("1")){
            type = "Individu worker";
        }else{
            type = "Team worker : "+anggota+" people";
        }

        if (item.getStatusOrder().matches("0")){
            status = "Canceled!";
            color = Color.RED;
        }else if (item.getStatusOrder().matches("4")){
            status = "Finish!";
            color = Color.GREEN;
        }else{
            status = "error!";
            color = Color.RED;
        }

        if (foto.isEmpty()){
            Glide.with(this)
                    .load(R.drawable.blank_profile)
                    .apply(new RequestOptions().override(170,170))
                    .into(imgWorker);
        }else{
            Glide.with(this)
                    .load(UrlServer.URL_FOTO_TUKANG+foto)
                    .apply(new RequestOptions().override(170,170))
                    .into(imgWorker);
        }
        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        parseDate("yyyy-MM-dd HH:mm:ss", "dd MMM yyyy",startDate, edtStart);
        parseDate("yyyy-MM-dd", "dd MMM yyyy",endDate, edtEnd);
        parseDate("yyyy-MM-dd HH:mm:ss", "HH:mm", startDate, edtHour);
        parseDateOrder("yyyy-MM-dd HH:mm:ss","dd MMM yyyy", orderdate, txtTgl );

        getTotaldays(startDate, endDate);
        double harga = Double.parseDouble(price);

        txtName.setText(nama);
        txtType.setText(type);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);
        txtPrice.setText(format.format(harga));
        txtCode.setText(code);
        edtAddress.setText(alamat);
        edtJob.setText(jobdesk);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void parseDate(String pattern, String pattern2,String date, EditText edt){
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

    private void parseDateOrder(String pattern, String pattern2,String date, TextView edt){
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

    private void getTotaldays(String date, String date2){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat time2 = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Date waktu = time.parse(date);
            Date waktu2 = time2.parse(date2);
            long end, start;
            end = waktu2.getTime();
            start = waktu.getTime();
            long totalDays = end - start;
            long total = (totalDays/(24 * 60 * 60 * 1000)+1);
            String day;
            String realTime = String.valueOf(total);
            if (totalDays > 1){
                day = " days";
            }else {
                day = " day";
            }
            edtTotal.setText(realTime+day);
        }catch(Exception e){
            Log.d("ASD", "onBindViewHolder: "+e.getMessage());
        }
    }
}
