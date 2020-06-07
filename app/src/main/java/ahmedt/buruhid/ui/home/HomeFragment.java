package ahmedt.buruhid.ui.home;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Random;

import ahmedt.buruhid.FirebaseMessagingService;
import ahmedt.buruhid.R;
import ahmedt.buruhid.make_order.MakeOrderActivity;
import ahmedt.buruhid.notification.NotificationActivity;
import ahmedt.buruhid.promotion.PromoActivity;
import ahmedt.buruhid.ui.home.about.AboutActivity;
import ahmedt.buruhid.ui.home.corona.CovidActivity;
import ahmedt.buruhid.ui.home.howtouse.HowToActivity;
import ahmedt.buruhid.ui.home.modelCounter.CounterModel;
import ahmedt.buruhid.ui.home.securetrans.SecureTransActivity;
import ahmedt.buruhid.ui.home.survery.SurveyActivity;
import ahmedt.buruhid.ui.home.wnew.WNewActivity;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView rc_WN, rc_Other;
    private WhatsNewAdapter mAdapter_1;
    private OtherAdapter mAdapter_2;
    private CardView cvSurvey;
    private ArrayList<WhatsNewItem> list = new ArrayList<>();
    private ArrayList<OtherItem> list2 = new ArrayList<>();
    private ImageView imgSolo, imgTeam;
    private TextView txtBadgeNotif, txtBadgePromo;
    public static int countPromo, countNotif;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    private BroadcastReceiver updateBadge = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String param = FirebaseMessagingService.INFO_UPDATE;
            if (intent.getAction().equals(param)) {
                getCount(false);
            }
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(updateBadge);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter1();
        setAdapter2();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(updateBadge, new IntentFilter(FirebaseMessagingService.INFO_UPDATE));
        if (Prefs.getString(SessionPrefs.isLogin, "").isEmpty()) {
            getCount(true);
            Prefs.putString(SessionPrefs.isLogin, "1");
        }
    }

    private void findView(View view) {
        imgSolo = view.findViewById(R.id.img_solo);
        imgTeam = view.findViewById(R.id.img_team);
        rc_WN = view.findViewById(R.id.rc_whats_new);
        cvSurvey = view.findViewById(R.id.cv_survey);
        rc_Other = view.findViewById(R.id.rc_other_content);
        mAdapter_1 = new WhatsNewAdapter(getActivity(), list);
        mAdapter_2 = new OtherAdapter(getActivity(), list2);
        rc_WN.setHasFixedSize(true);
        rc_Other.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rc_WN.setLayoutManager(linearLayoutManager);
        rc_Other.setLayoutManager(linearLayoutManager1);
        rc_WN.setAdapter(mAdapter_1);
        rc_Other.setAdapter(mAdapter_2);
        Log.d(TAG, "findView: " + Prefs.getInt(SessionPrefs.NOTIF_COUNT, 0));
        Log.d(TAG, "findView: " + countNotif);
        if (Prefs.getInt(SessionPrefs.NOTIF_COUNT, 0) > 0) {
            countNotif = Prefs.getInt(SessionPrefs.NOTIF_COUNT, 0);
        }
        cvSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SurveyActivity.class));
            }
        });
        mAdapter_1.SetOnItemClickListener(new WhatsNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, WhatsNewItem model) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), WNewActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), CovidActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), PromoActivity.class));
                        break;
                }
            }
        });

        mAdapter_2.SetOnItemClickListener(new OtherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, OtherItem model) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), SecureTransActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), HowToActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), AboutActivity.class));
                        break;

                }
            }
        });

        imgSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeOrderActivity.class);
                i.putExtra("type", getString(R.string.individu_worker));
                i.putExtra("photo", R.drawable.solo_worker);
                View shareView = imgSolo;
                String transitionName = getString(R.string.img);
                ActivityOptions transOpt = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    transOpt = ActivityOptions.makeSceneTransitionAnimation(getActivity(), shareView, transitionName);
                    startActivity(i, transOpt.toBundle());
                } else {
                    startActivity(i);
                }
            }
        });

        imgTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeOrderActivity.class);
                i.putExtra("type", getString(R.string.team_worker));
                i.putExtra("photo", R.drawable.team_worker);
                View shareView = imgTeam;
                String transitionName = getString(R.string.img);
                ActivityOptions transOpt = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    transOpt = ActivityOptions.makeSceneTransitionAnimation(getActivity(), shareView, transitionName);
                    startActivity(i, transOpt.toBundle());
                } else {
                    startActivity(i);
                }
            }
        });
    }

    private void setAdapter1() {
        list.clear();
        list.add(new WhatsNewItem(R.drawable.whatsnew));
        list.add(new WhatsNewItem(R.drawable.covid));
        list.add(new WhatsNewItem(R.drawable.kodepromo));
        mAdapter_1.updateList(list);
    }

    private void setAdapter2() {
        list2.clear();
        list2.add(new OtherItem(getString(R.string.secure_transc), R.drawable.securetrans));
        list2.add(new OtherItem(getString(R.string.howTouse), R.drawable.load_screen_3));
        list2.add(new OtherItem(getString(R.string.aboud_us), R.drawable.launcherrounded));
        mAdapter_2.updateList(list2);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        final MenuItem notifItem = menu.findItem(R.id.home_notification);
        View actionNotif = notifItem.getActionView();
        txtBadgeNotif = (TextView) actionNotif.findViewById(R.id.notif_badge);
        actionNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(notifItem);
            }
        });
        setupBadgeNotif();
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_notification:
                startActivityForResult(new Intent(getActivity(), NotificationActivity.class), 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                countNotif = 0;
                txtBadgeNotif.setVisibility(View.GONE);
            }
        }
    }

    private void setupBadgeNotif() {
        if (txtBadgeNotif != null) {
            if (countNotif == 0) {
                if (txtBadgeNotif.getVisibility() != View.GONE) {
                    txtBadgeNotif.setVisibility(View.GONE);
                }
            } else {
                txtBadgeNotif.setText(String.valueOf(Math.min(countNotif, 99)));
                if (txtBadgeNotif.getVisibility() != View.VISIBLE) {
                    txtBadgeNotif.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void showNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        Random rand = new Random();
        final int notification_ID = rand.nextInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("My Channel");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            manager.createNotificationChannel(notificationChannel);
        }

        Intent i = new Intent(getActivity(), NotificationActivity.class);

        PendingIntent intent = PendingIntent.getActivity(getActivity(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(intent);

        manager.notify(notification_ID, builder.build());
    }

    private void getCount(final Boolean notif) {
        AndroidNetworking.post(UrlServer.URL_COUNT)
                .addBodyParameter("id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .build()
                .getAsOkHttpResponseAndObject(CounterModel.class, new OkHttpResponseAndParsedRequestListener<CounterModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, CounterModel response) {
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                Log.d(TAG, "onResponse: " + response.getCountNotif());
                                countNotif = response.getCountNotif();
                                Prefs.putInt(SessionPrefs.NOTIF_COUNT, response.getCountNotif());
                                countPromo = response.getCountPromo();
                                setupBadgeNotif();
                                if (notif) {
                                    if (response.getCountNotif() > 0 && txtBadgeNotif.getVisibility() != View.VISIBLE) {
                                        for (int i = 0; i < response.getData().size(); i++) {
                                            showNotification(response.getData().get(i).getTitle(), response.getData().get(i).getMessage());
                                        }
                                    }
                                }

                                Log.d(TAG, "onResponse: suc= " + response.getMsg());
                            } else {
                                Log.d(TAG, "onResponse: " + response.getMsg());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        } else {
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}