package ahmedt.buruhid.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ahmedt.buruhid.FirebaseMessagingService;
import ahmedt.buruhid.R;
import ahmedt.buruhid.notification.modelNotification.DataItem;
import ahmedt.buruhid.notification.modelNotification.NotificationModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView rv_notif;
    private ProgressBar progressBar;
    private NotifAdapter adapter;
    private ArrayList<DataItem> list = new ArrayList<>();
    private LinearLayout lay_include;
    private FloatingActionButton btnRefresh;
    private TextView txtMsg;
    private ImageView imgMsg;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findView();
    }

    private void findView(){
        imgMsg = findViewById(R.id.img_message);
        txtMsg = findViewById(R.id.txt_msg);
        rv_notif = findViewById(R.id.rc_notif);
        progressBar = findViewById(R.id.progress_bar);
        lay_include =  findViewById(R.id.include_lay);
        lay_include.setVisibility(View.GONE);
        btnRefresh = findViewById(R.id.floatingActionButton);
        rv_notif.setHasFixedSize(true);
        adapter = new NotifAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_notif.setLayoutManager(linearLayoutManager);
        rv_notif.setAdapter(adapter);
        setAdapter(id, token, false);
        adapter.SetOnItemClickListener(new NotifAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Toast.makeText(NotificationActivity.this, model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_include.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                setAdapter(id, token, false);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_notifications);
    }

    private BroadcastReceiver updateList = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String param = FirebaseMessagingService.INFO_UPDATE;
            if (intent.getAction().equals(param)){
                setAdapter(id, token, true);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(NotificationActivity.this).registerReceiver(updateList, new IntentFilter(FirebaseMessagingService.INFO_UPDATE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(NotificationActivity.this).unregisterReceiver(updateList);
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

    private void setAdapter(String id, final String token, final boolean isBackground) {
        AndroidNetworking.post(UrlServer.URL_NOTIF)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .build()
                .getAsOkHttpResponseAndObject(NotificationModel.class, new OkHttpResponseAndParsedRequestListener<NotificationModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, NotificationModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                list.clear();
                                lay_include.setVisibility(View.GONE);
                                Prefs.putInt(SessionPrefs.NOTIF_COUNT, 0);
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setCreateDate(response.getData().get(i).getCreateDate());
                                    items.setMessage(response.getData().get(i).getMessage());
                                    items.setTitle(response.getData().get(i).getTitle());
                                    list.add(items);
                                }
                                setResult(RESULT_OK);
                                adapter.updateList(list);
                            }else{
                                if (isBackground){

                                }else {
                                    HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blmnotif));
                                }
//                                Toasty.warning(getActivity(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            if (isBackground){
                                Toasty.error(NotificationActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                            }else {
                                HelperClass.serverError(NotificationActivity.this, lay_include, imgMsg, txtMsg);
                            }
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            if (isBackground){
                                Toasty.error(NotificationActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                            }else {
                                HelperClass.InetError(NotificationActivity.this, lay_include, imgMsg, txtMsg);
                            }
                        }
                    }
                });
    }
}
