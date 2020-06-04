package ahmedt.buruhid.ui.home.corona;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;

import java.util.ArrayList;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.home.corona.modelcorona.CoronaModel;
import ahmedt.buruhid.ui.home.corona.modelprevention.PreventionModel;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class CovidActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<PreventionModel> listPrevention = new ArrayList<>();
    private PreventionAdapter adapterPrevention;
    private TextView txtKonfirm, txtSembuh, txtMeninggal, txtPengawasan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        findView();

    }

    private void findView(){
        txtKonfirm = findViewById(R.id.txt_terkonfirmasi);
        txtSembuh = findViewById(R.id.txt_sembuh);
        txtMeninggal = findViewById(R.id.txt_meninggal);
        txtPengawasan = findViewById(R.id.txt_perawatan);
        recyclerView = findViewById(R.id.rv_prevention);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 3);
        adapterPrevention = new PreventionAdapter(this, listPrevention);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setAdapter(adapterPrevention);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.covidd));
        setAdapter();
        getCountCorona();
    }

    private void setAdapter(){
        listPrevention.add(new PreventionModel(getString(R.string.cucitangan), R.drawable.img1));
        listPrevention.add(new PreventionModel(getString(R.string.tetapdirumah), R.drawable.img2));
        listPrevention.add(new PreventionModel(getString(R.string.pakaimasker), R.drawable.img3));
        listPrevention.add(new PreventionModel(getString(R.string.socialdistancing), R.drawable.img4));
        listPrevention.add(new PreventionModel(getString(R.string.jagakebersihan), R.drawable.img5));
        listPrevention.add(new PreventionModel(getString(R.string.hindariberkumpul), R.drawable.img6));
        adapterPrevention.updateList(listPrevention);
    }

    private void getCountCorona(){
        AndroidNetworking.get(UrlServer.URL_COVID)
                .build()
                .getAsOkHttpResponseAndObject(CoronaModel.class, new OkHttpResponseAndParsedRequestListener<CoronaModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, CoronaModel response) {
                        if (okHttpResponse.isSuccessful()){
                            txtKonfirm.setText(String.valueOf(response.getJumlahKasus()));
                            txtMeninggal.setText(String.valueOf(response.getMeninggal()));
                            txtPengawasan.setText(String.valueOf(response.getPerawatan()));
                            txtSembuh.setText(String.valueOf(response.getSembuh()));
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(CovidActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(CovidActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
