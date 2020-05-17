package ahmedt.buruhid.make_order.select_worker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ahmedt.buruhid.R;
import ahmedt.buruhid.make_order.select_worker.modelWorker.DataItem;
import ahmedt.buruhid.make_order.select_worker.modelWorker.SelectModel;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class SelectWorkerActivity extends AppCompatActivity {
    private static final String TAG = "SelectWorkerActivity";
    public static final String NAME_WORKER = "name";
    public static final String ADDRESS_WORKER = "address";
    public static final String TYPE_WORKER = "type";
    public static final String RATING_WORKER = "rating";
    public static final String IMAGE_WORKER = "img";
    public static final String ID_TUKANG = "id_tukang";
    private ProgressBar progressBar;
    private ImageButton btnBack;
    private RecyclerView rvSelectWorker;
    private SelectWorkerAdapter mAdapter;
    private ArrayList<DataItem> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        findView();
    }

    private void findView(){
        rvSelectWorker = findViewById(R.id.rv_select_worker);
        btnBack = findViewById(R.id.btn_select_worker_back);
        progressBar = findViewById(R.id.progress_bar);
        mAdapter = new SelectWorkerAdapter(SelectWorkerActivity.this, list);
        rvSelectWorker.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectWorkerActivity.this);
        rvSelectWorker.setLayoutManager(linearLayoutManager);
        rvSelectWorker.setAdapter(mAdapter);
        Intent i = getIntent();
        String kota          = i.getStringExtra("city").toLowerCase().trim();
        String kec           = i.getStringExtra("subdis").toLowerCase().trim();
        String jumlahAnggota = i.getStringExtra("jumlah").trim();
        setAdapter(kota, kec, jumlahAnggota, String.valueOf(1));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setAdapter(String city, String subDis, String anggota, String page){
        AndroidNetworking.post(UrlServer.URL_SELECT_WORKER)
                .addBodyParameter("id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addBodyParameter("anggota", anggota)
                .addBodyParameter("page", page)
                .addBodyParameter("kota", city)
                .addBodyParameter("kecamatan", subDis)
                .build()
                .getAsOkHttpResponseAndObject(SelectModel.class, new OkHttpResponseAndParsedRequestListener<SelectModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, SelectModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setNama(response.getData().get(i).getNama());
                                    items.setAlamat(response.getData().get(i).getAlamat());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    items.setRating(response.getData().get(i).getRating());
                                    items.setFoto(response.getData().get(i).getFoto());
                                    list.add(items);
                                }
                                mAdapter.updateList(list);
                                mAdapter.SetOnItemClickListener(new SelectWorkerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, DataItem model) {
                                        Intent i = new Intent();
                                        i.putExtra(ID_TUKANG, model.getTukangId());
                                        i.putExtra(NAME_WORKER, model.getNama());
                                        i.putExtra(TYPE_WORKER, model.getAnggota());
                                        i.putExtra(ADDRESS_WORKER, model.getAlamat());
                                        i.putExtra(IMAGE_WORKER, model.getFoto());
                                        i.putExtra(RATING_WORKER, model.getRating());
                                        setResult(RESULT_OK, i);
                                        finish();
                                    }
                                });
                            }else{
                                Toasty.warning(SelectWorkerActivity.this, R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                        Toasty.error(SelectWorkerActivity.this, R.string.cek_internet, Toasty.LENGTH_SHORT).show();
                    }
                });

    }
}
