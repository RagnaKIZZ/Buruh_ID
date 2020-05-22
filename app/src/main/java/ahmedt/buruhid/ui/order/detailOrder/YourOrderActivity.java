package ahmedt.buruhid.ui.order.detailOrder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.order.modelCancel.CancelModel;
import ahmedt.buruhid.ui.order.modelOrder.DataItem;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class YourOrderActivity extends AppCompatActivity {
    private static final String TAG = "YourOrderActivity";
    private TextView txtName, txtType, txtStatus, txtCode, txtPrice;
    private TextInputEditText edtStart, edtEnd, edtAddress, edtJob, edtHour, edtTotal;
    private Button btnCall, btnMessage, btnAction;
    private ImageButton btnBack;
    private ImageView imgWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_your_order);
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
        btnCall = findViewById(R.id.btn_call);
        btnMessage = findViewById(R.id.btn_message);
        btnAction = findViewById(R.id.btn_action);
        imgWorker = findViewById(R.id.img_your_order);
        edtHour = findViewById(R.id.edt_start_hour);
        edtTotal = findViewById(R.id.edt_detail_day);
        btnBack = findViewById(R.id.btn_detail_order_back);
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
        String type = "";
        int color = 0;

        if (anggota.matches("1")){
            type = "Individu worker";
        }else{
            type = "Team worker : "+anggota+" people";
        }

        if (item.getStatusOrder().matches("1")){
            status = "Waiting confirmation from worker...";
            color = Color.parseColor("#ffd600");
            btnAction.setBackground(getDrawable(R.drawable.bg_cancel));
        }else if (item.getStatusOrder().matches("2")){
            status = "Order accepted!, waiting to start job...";
            color = Color.GREEN;
            btnAction.setBackground(getDrawable(R.drawable.bg_cancel));
        }else if (item.getStatusOrder().matches("3")){
            status = "Working...";
            color = Color.GREEN;
            btnAction.setBackground(getDrawable(R.drawable.bg_call));
            btnAction.setText("Finish order");
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

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telepon, null));
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("vnd.android-dir/mms-sms");
                intent.putExtra("address", telepon);
                startActivity(intent);
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getStatusOrder().equals("3")){

                }else{
                    cancelOrderan(item.getId());
                }
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

    private void cancelOrder(String id){
        final KProgressHUD hud = new KProgressHUD(this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_CANCEL_ORDER)
                .addBodyParameter("user_id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addBodyParameter("order_id", id)
                .build()
                .getAsOkHttpResponseAndObject(CancelModel.class, new OkHttpResponseAndParsedRequestListener<CancelModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, CancelModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                setResult(RESULT_OK);
                                finish();
                                Toasty.success(YourOrderActivity.this, R.string.ordercancel, Toasty.LENGTH_SHORT).show();
                            }else{
                                Toasty.warning(YourOrderActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    hud.dismiss();
                        if (anError.getErrorCode() != 0){
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(YourOrderActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d(TAG, "onError: "+anError.getErrorCode());
                            Log.d(TAG, "onError: "+anError.getErrorBody());
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                            Toasty.error(YourOrderActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    private void cancelOrderan(final String id){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.cancel_order);
        alert.setMessage(R.string.sure_cancel)
                .setIcon(R.drawable.ic_cancel_black_24dp)
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            cancelOrder(id);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert.show();
    }
}
