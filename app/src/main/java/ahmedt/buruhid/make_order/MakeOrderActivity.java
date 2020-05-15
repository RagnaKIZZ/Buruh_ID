package ahmedt.buruhid.make_order;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ahmedt.buruhid.R;
import ahmedt.buruhid.make_order.detail_order.DetailOrderActivity;
import ahmedt.buruhid.make_order.modelCity.CityModel;
import ahmedt.buruhid.make_order.modelCity.DataItem;
import ahmedt.buruhid.make_order.select_worker.SelectWorkerActivity;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class MakeOrderActivity extends AppCompatActivity {
    private static final String TAG = "MakeOrderActivity";
    public static String TOTAL_DAY = "total";
    public static String ID_WORKER = "id";
    public static Activity makeOrderActivity;
    private TextInputLayout edtCity, edtAddress, edtJobdesk, edtSubdis;
    private ImageView imgWorker, imgSelectWorker;
    private TextView txtType, txtName, txtTypeWorker, txtAddress, txtRating;
    private RatingBar ratingWorker;
    private RelativeLayout rlNoWorker, rlIsWorker;
    private CardView cvSelectWorker;
    private ImageButton btnImgBack, btnHour, btnEndDate, btnCity, btnSubdis;
    private EditText edtStartDate, edtStartHour, edtEndDate, edtAdd, edtCit, edtJob, edtSubs;
    private Button btnPlus, btnMinus, btnMakeOrder;
    private TextView txtCounter;
    private RelativeLayout lnCounter;
    private int counter = 1;

    String id_tukang = "";
    String id_prov = "31";
    String id_kota = "";
    long start, end;
    int th ;
    int bl ;
    int hr ;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        makeOrderActivity = this;
        findView();
    }

    private void findView(){
        imgWorker = findViewById(R.id.img_make_order);
        txtType = findViewById(R.id.txt_type_worker);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        txtCounter = findViewById(R.id.txt_counter);
        btnImgBack = findViewById(R.id.btn_make_order_back);
        edtStartDate = findViewById(R.id.edt_mo_start);
        edtStartHour = findViewById(R.id.edt_mo_start_hour);
        edtEndDate = findViewById(R.id.edt_mo_end);
        btnEndDate = findViewById(R.id.btn_end_date);
        btnHour = findViewById(R.id.btn_hour);
        btnMakeOrder = findViewById(R.id.btn_make_order);
        edtAddress = findViewById(R.id.txt_mo_address);
        edtCity = findViewById(R.id.txt_mo_city);
        edtJobdesk = findViewById(R.id.txt_mo_jobdesk);
        edtCit = findViewById(R.id.edt_mo_city);
        edtSubs = findViewById(R.id.edt_mo_subdis);
        btnCity = findViewById(R.id.btn_city);
        btnSubdis = findViewById(R.id.btn_subdis);
        edtSubdis = findViewById(R.id.txt_mo_subdis);

        //include layout
        ratingWorker = findViewById(R.id.rating_selected_worker);
        txtName = findViewById(R.id.txt_name_worker);
        txtTypeWorker = findViewById(R.id.txt_type_of_worker);
        txtAddress = findViewById(R.id.txt_address_worker);
        txtRating = findViewById(R.id.txt_rating_selected_worker);
        rlIsWorker = findViewById(R.id.rl_is_worker);
        rlNoWorker = findViewById(R.id.rl_no_worker);
        cvSelectWorker = findViewById(R.id.cv_select_worker);
        imgSelectWorker = findViewById(R.id.img_selected_worker);
        rlNoWorker.setVisibility(View.VISIBLE);
        rlIsWorker.setVisibility(View.GONE);

        lnCounter = findViewById(R.id.rv_counter);

        Intent i = getIntent();
        type = i.getStringExtra("type");
        if (type.matches("Individu Worker")){
            lnCounter.setVisibility(View.GONE);
        }else {
            counter = 3;
            lnCounter.setVisibility(View.VISIBLE);
        }

        Glide.with(MakeOrderActivity.this)
                .load(i.getIntExtra("photo", 0))
                .into(imgWorker);
        txtType.setText(type);

        txtCounter.setText(String.valueOf(counter));

        edtCit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rlNoWorker.setVisibility(View.VISIBLE);
                rlIsWorker.setVisibility(View.GONE);
                id_tukang = "";
                txtName.setText("");
                txtTypeWorker.setText("");
                txtAddress.setText("");
                txtRating.setText("");
                ratingWorker.setRating(0);
                imgSelectWorker.setImageDrawable(null);
            }
        });

        edtSubs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rlNoWorker.setVisibility(View.VISIBLE);
                rlIsWorker.setVisibility(View.GONE);
                id_tukang = "";
                txtName.setText("");
                txtTypeWorker.setText("");
                txtAddress.setText("");
                txtRating.setText("");
                id_kota = "";
                ratingWorker.setRating(0);
                imgSelectWorker.setImageDrawable(null);
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter >= 3 && counter < 7){
                    counter++;
                    txtCounter.setText(String.valueOf(counter));
                }else if (counter >= 7){
                    Toast.makeText(MakeOrderActivity.this, "Reach number of maximum worker", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 3 && counter <= 7){
                    counter--;
                    txtCounter.setText(String.valueOf(counter));
                }else if (counter <= 3){
                    Toast.makeText(MakeOrderActivity.this, "Reach number of minimum worker", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cvSelectWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtCity.getEditText().getText().toString().trim();
                String subdis = edtSubdis.getEditText().getText().toString().trim();
                String address = edtAddress.getEditText().getText().toString().trim();
                if (!city.isEmpty() && !subdis.isEmpty() && !address.isEmpty()){
                    Intent i = new Intent(MakeOrderActivity.this, SelectWorkerActivity.class);
                    i.putExtra("jumlah", String.valueOf(counter));
                    i.putExtra("city", city);
                    i.putExtra("subdis", subdis);
                    startActivityForResult(i, 111);
                }
            }
        });

        edtStartDate.setEnabled(false);
        edtStartHour.setEnabled(false);
        edtEndDate.setEnabled(false);
        edtCity.getEditText().setEnabled(false);
        edtSubdis.getEditText().setEnabled(false);
        getTomorrow(edtStartDate);
        th = Prefs.getInt(SessionPrefs.YEAR, 0);
        bl = Prefs.getInt(SessionPrefs.MONTH, 0);
        hr =  Prefs.getInt(SessionPrefs.DATE, 0);

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(edtEndDate);
            }
        });

        btnHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourDialog(edtStartHour);
            }
        });

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityDialog(edtCit, UrlServer.URL_GET_KAB, id_prov);
            }
        });

        btnSubdis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtCit.getText().toString().isEmpty()){
                    showSubsDialog(edtSubs, UrlServer.URL_GET_KAC, id_kota);
                }
            }
        });

        btnImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              conditionNotEmpty();
            }
        });
    }

    private void conditionNotEmpty(){
        String startDate = edtStartDate.getText().toString().trim();
        String startHour = edtStartHour.getText().toString().trim();
        String endDate = edtEndDate.getText().toString().trim();
        String city = edtCity.getEditText().getText().toString().trim();
        String subdis = edtSubdis.getEditText().getText().toString().trim();
        String address = edtAddress.getEditText().getText().toString().trim();
        String nameWorker = txtName.getText().toString();
        String jobdesk = edtJobdesk.getEditText().getText().toString().trim();
        if (!startDate.isEmpty() && !startHour.isEmpty() && !endDate.isEmpty() && !city.isEmpty()
                && !address.isEmpty() && !nameWorker.isEmpty() && !jobdesk.isEmpty() && !subdis.isEmpty()){

            Intent i = new Intent(MakeOrderActivity.this, DetailOrderActivity.class);
            if (type.matches("Individu Worker")){
                i.putExtra("isTeam", false);
                i.putExtra("counter", String.valueOf(counter));
            }else {
                i.putExtra("isTeam", true);
                i.putExtra("counter", txtCounter.getText());
            }
            i.putExtra("type", txtType.getText().toString().trim());
            i.putExtra("startDate", startDate);
            i.putExtra("startHour", startHour);
            i.putExtra("endDate", endDate);
            i.putExtra("city", city);
            i.putExtra("subdis", subdis);
            i.putExtra("address", address);
            i.putExtra("name_worker", nameWorker);
            i.putExtra("jobdesk", jobdesk);
            i.putExtra("amountDays",TOTAL_DAY);
            startActivity(i);
        }else{
            Toast.makeText(makeOrderActivity, "Please check your order again", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Keterangan :
     * to get result from selectworkeractivity
     * AMD
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 111){
                if (data != null){

                    String name = data.getStringExtra(SelectWorkerActivity.NAME_WORKER);
                    String type = data.getStringExtra(SelectWorkerActivity.TYPE_WORKER);
                    String address = data.getStringExtra(SelectWorkerActivity.ADDRESS_WORKER);
                    double rating = data.getDoubleExtra(SelectWorkerActivity.RATING_WORKER, 0);
                    int img = data.getIntExtra(SelectWorkerActivity.IMAGE_WORKER, 0);
                    rlNoWorker.setVisibility(View.GONE);
                    rlIsWorker.setVisibility(View.VISIBLE);

                    txtName.setText(name);
                    txtTypeWorker.setText(type);
                    txtAddress.setText(address);
                    txtRating.setText("("+String.valueOf(rating)+")");
                    ratingWorker.setRating((float) rating);
                    Glide.with(MakeOrderActivity.this)
                            .load(img)
                            .centerCrop()
                            .into(imgSelectWorker);
                }
            }
        }
    }

    /**
     * Keterangan :
     * to create hourdialog
     * AMD
     **/
    private void showHourDialog(final EditText edtStartHour) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                if (timeOfDay >= 0 && timeOfDay < 8){
                    Toast.makeText(MakeOrderActivity.this, R.string.wrong_time, Toast.LENGTH_LONG).show();
                }else if(timeOfDay >= 15 && timeOfDay < 24){
                    Toast.makeText(MakeOrderActivity.this, R.string.wrong_time, Toast.LENGTH_LONG).show();
                }
                else{
                    edtStartHour.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        };

        new TimePickerDialog(MakeOrderActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void showCityDialog(final EditText edt, String jenis, String id){
        final Dialog dialog = new Dialog(MakeOrderActivity.this);
        dialog.setContentView(R.layout.dialog_address);
        RecyclerView rv_address;
        Button btnCancel;
        final AddressAdapter adapter;
        final ProgressBar progressBar;
        final ArrayList<DataItem> list = new ArrayList<>();
        rv_address = dialog.findViewById(R.id.rv_alamat);
        progressBar = dialog.findViewById(R.id.progress_bar);
        btnCancel = dialog.findViewById(R.id.btn_cancel_address);

        adapter = new AddressAdapter(this, list);
        rv_address.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_address.setLayoutManager(linearLayoutManager);
        rv_address.setAdapter(adapter);

        AndroidNetworking.get(jenis+id)
                .setTag("city")
                .build()
                .getAsOkHttpResponseAndObject(CityModel.class, new OkHttpResponseAndParsedRequestListener<CityModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, CityModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size() ; i++) {
                                    final DataItem item = new DataItem();
                                    item.setName(response.getData().get(i).getName());
                                    item.setId(response.getData().get(i).getId());
                                    list.add(item);
                                }
                                adapter.updateList(list);
                                adapter.SetOnItemClickListener(new AddressAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, DataItem model) {
                                        edt.setText(model.getName());
                                        id_kota = String.valueOf(model.getId());
                                    }
                                });
                            }else {
                                Toasty.warning(MakeOrderActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toasty.warning(MakeOrderActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.cancel("city");
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void showSubsDialog(final EditText edt, String jenis, String id){
        final Dialog dialog = new Dialog(MakeOrderActivity.this);
        dialog.setContentView(R.layout.dialog_address);
        RecyclerView rv_address;
        Button btnCancel;
        final AddressAdapter adapter;
        final ProgressBar progressBar;
        final ArrayList<DataItem> list = new ArrayList<>();
        rv_address = dialog.findViewById(R.id.rv_alamat);
        progressBar = dialog.findViewById(R.id.progress_bar);
        btnCancel = dialog.findViewById(R.id.btn_cancel_address);

        adapter = new AddressAdapter(this, list);
        rv_address.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_address.setLayoutManager(linearLayoutManager);
        rv_address.setAdapter(adapter);

        AndroidNetworking.get(jenis+id)
                .setTag("city")
                .build()
                .getAsOkHttpResponseAndObject(CityModel.class, new OkHttpResponseAndParsedRequestListener<CityModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, CityModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size() ; i++) {
                                    final DataItem item = new DataItem();
                                    item.setName(response.getData().get(i).getName());
                                    item.setId(response.getData().get(i).getId());
                                    list.add(item);
                                }
                                adapter.updateList(list);
                                adapter.SetOnItemClickListener(new AddressAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, DataItem model) {
                                        edt.setText(model.getName());
                                    }
                                });
                            }else {
                                Toasty.warning(MakeOrderActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toasty.warning(MakeOrderActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.cancel("city");
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }



    /**
     * Keterangan :
     * to create datepicker dialog
     * AMD
     **/
    private void showDateDialog(final EditText edtEndDate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.d(TAG, "onDateSet: "+String.valueOf(th));
                Log.d(TAG, "onDateSet: "+String.valueOf(hr));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date endDate = calendar.getTime();
                /**
                 * Keterangan :
                 * to make condition that not allowed end date
                 * AMD
                 **/
                if (year < th){
                    Toast.makeText(MakeOrderActivity.this, R.string.worng_end_date, Toast.LENGTH_SHORT).show();
                }else if (year == th){
                    if (month < bl){
                        Toast.makeText(MakeOrderActivity.this, R.string.worng_end_date, Toast.LENGTH_SHORT).show();
                    }else if (month == bl){
                        if (dayOfMonth<=hr){
                            Toast.makeText(MakeOrderActivity.this, R.string.worng_end_date, Toast.LENGTH_SHORT).show();
                        }else {
                            edtEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                            end = endDate.getTime();
                        }
                    }else if (month > bl){
                        edtEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                        end = endDate.getTime();
                    }
                }else if (year > th){
                    edtEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                    end = endDate.getTime();
                }
                /**
                 * Keterangan :
                 * to calculate total days
                 * AMD
                 **/
                long total = end - start;
                long difTotal = (total / (24 * 60 * 60 * 1000))+1;
                TOTAL_DAY = String.valueOf(difTotal);
                Log.d(TAG, "onDateSet: "+TOTAL_DAY);
            }
        };
        new DatePickerDialog(MakeOrderActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Keterangan :
     * to get tommorow date
     * AMD
     **/
    private void getTomorrow(EditText edtStartDate){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tomorrow = calendar.getTime();
        String getDate = simpleDateFormat.format(tomorrow);
        edtStartDate.setText(getDate);
        start = tomorrow.getTime();
    }


}
