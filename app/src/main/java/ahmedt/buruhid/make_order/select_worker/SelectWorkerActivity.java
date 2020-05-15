package ahmedt.buruhid.make_order.select_worker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ahmedt.buruhid.R;

public class SelectWorkerActivity extends AppCompatActivity {
    public static final String NAME_WORKER = "name";
    public static final String ADDRESS_WORKER = "address";
    public static final String TYPE_WORKER = "type";
    public static final String RATING_WORKER = "rating";
    public static final String IMAGE_WORKER = "img";
    private ImageButton btnBack;
    private RecyclerView rvSelectWorker;
    private SelectWorkerAdapter mAdapter;
    private ArrayList<SelectWorkerItem> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        findView();
    }

    private void findView(){
        rvSelectWorker = findViewById(R.id.rv_select_worker);
        btnBack = findViewById(R.id.btn_select_worker_back);
        mAdapter = new SelectWorkerAdapter(SelectWorkerActivity.this, list);
        rvSelectWorker.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectWorkerActivity.this);
        rvSelectWorker.setLayoutManager(linearLayoutManager);
        rvSelectWorker.setAdapter(mAdapter);
        setAdapter();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAdapter.SetOnItemClickListener(new SelectWorkerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, SelectWorkerItem model) {
                Intent i = new Intent();
                i.putExtra(NAME_WORKER, model.getName());
                i.putExtra(TYPE_WORKER, model.getType());
                i.putExtra(ADDRESS_WORKER, model.getAddress());
                i.putExtra(IMAGE_WORKER, model.getImg());
                i.putExtra(RATING_WORKER, model.getRating());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private void setAdapter(){
        list.add(new SelectWorkerItem("Ilham", "Individu Worker", "Kapuk Muara, Jakarta Utara", R.drawable.ppkuli,  4.5));
        list.add(new SelectWorkerItem("Oji", "Individu Worker", "Kapuk Muara, Jakarta Utara", R.drawable.ppkuli2,  4.0));
        list.add(new SelectWorkerItem("Rusdani", "Individu Worker", "Kapuk Muara, Jakarta Utara", R.drawable.ppkuli,  5));
        list.add(new SelectWorkerItem("Mika", "Individu Worker", "Kapuk Muara, Jakarta Utara", R.drawable.ppkuli, 4.2));
        list.add(new SelectWorkerItem("Pakong", "Individu Worker", "Kapuk Muara, Jakarta Utara", R.drawable.ppkuli2,  4.4));
        list.add(new SelectWorkerItem("Panjul", "Individu Worker", "Kapuk Muara, Jakarta Utara", R.drawable.ppkuli2,  4.8));
        mAdapter.updateList(list);
    }
}
