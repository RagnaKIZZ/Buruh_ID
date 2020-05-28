package ahmedt.buruhid.promotion;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ahmedt.buruhid.R;
import ahmedt.buruhid.promotion.modelPromo.DataItem;
import ahmedt.buruhid.promotion.modelPromo.PromoModel;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class PromoActivity extends AppCompatActivity {
    private RecyclerView rv_notif;
    private ProgressBar progressBar;
    private PromoAdapter adapter;
    private ArrayList<DataItem> list = new ArrayList<>();
    private LinearLayout lay_in;
    private FloatingActionButton btnRefresh;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        findView();
    }

    private void findView(){
        rv_notif = findViewById(R.id.rc_promo);
        progressBar = findViewById(R.id.progress_bar);
        lay_in =  findViewById(R.id.include_lay);
        lay_in.setVisibility(View.GONE);
        btnRefresh = findViewById(R.id.floatingActionButton);
        rv_notif.setHasFixedSize(true);
        adapter = new PromoAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_notif.setLayoutManager(linearLayoutManager);
        rv_notif.setAdapter(adapter);
        setAdapter(id, token, false);
        adapter.SetOnItemClickListener(new PromoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Toast.makeText(PromoActivity.this, model.getNamaPromo(), Toast.LENGTH_SHORT).show();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_in.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                setAdapter(id, token, false);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.promotion);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter(String id, String token, final boolean isBackground) {
        AndroidNetworking.post(UrlServer.URL_LIST_PROMO)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .build()
                .getAsOkHttpResponseAndObject(PromoModel.class, new OkHttpResponseAndParsedRequestListener<PromoModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PromoModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                list.clear();
                                lay_in.setVisibility(View.GONE);
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setStartDate(response.getData().get(i).getStartDate());
                                    items.setNamaPromo(response.getData().get(i).getNamaPromo());
                                    items.setEndDate(response.getData().get(i).getEndDate());
                                    items.setIsiPromo(response.getData().get(i).getIsiPromo());
                                    items.setKodePromo(response.getData().get(i).getKodePromo());
                                    items.setMinHarga(response.getData().get(i).getMinHarga());
                                    list.add(items);
                                }
                                setResult(RESULT_OK);
                                adapter.updateList(list);
                            }else{
                                if (isBackground){

                                }else {
                                    lay_in.setVisibility(View.VISIBLE);
                                }
//                                Toasty.warning(getActivity(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        lay_in.setVisibility(View.VISIBLE);
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(PromoActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(PromoActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
