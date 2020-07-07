package ahmedt.buruhid.ui.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ahmedt.buruhid.FirebaseMessagingService;
import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.order.detailHistoryOrder.HistoryOrderActivity;
import ahmedt.buruhid.ui.order.detailOrder.YourOrderActivity;
import ahmedt.buruhid.ui.order.modelHistoryOrder.OrderHistoryModel;
import ahmedt.buruhid.ui.order.modelOrder.DataItem;
import ahmedt.buruhid.ui.order.modelOrder.OrderModel;
import ahmedt.buruhid.ui.order.modelResponOrder.ResponOrderModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";
    private Button btnYourOrder, btnHistoryOrder;
    private RecyclerView rv_order;
    private ArrayList<DataItem> list = new ArrayList<>();
    private ArrayList<ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem> list2 = new ArrayList<>();
    private YourOrderAdapter adapter;
    private HistoryOrderAdapter adapter2;
    private ProgressBar progressBar;
    private LinearLayout lay_include;
    private TextInputLayout txtKomen;
    private FloatingActionButton btnRefresh;
    private View view;
    Button btnSend;
    TextView txtWorker, txtJenis, txtDate, txtCode, txtMsg;
    ImageView imgSheet, imgMsg;
    RatingBar ratingBar;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");
    int param = 1;
    int param_history;
    int param_yo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter2(id, token, "1", false);
        setAdapter(id, token, "1", false);
        param = 1;
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(updateBadge, new IntentFilter(FirebaseMessagingService.INFO_UPDATE));
    }

    private void findView(View view) {
//        Set<YourOrderItem> set = new HashSet<>();
//        set.addAll(list);
        btnHistoryOrder = view.findViewById(R.id.btn_history_order);
        btnYourOrder = view.findViewById(R.id.btn_your_order);
        imgMsg = view.findViewById(R.id.img_message);
        txtMsg = view.findViewById(R.id.txt_msg);
        rv_order = view.findViewById(R.id.rc_order);
        progressBar = view.findViewById(R.id.progress_bar);
        lay_include = view.findViewById(R.id.include_lay);
        btnRefresh = view.findViewById(R.id.floatingActionButton);
        adapter = new YourOrderAdapter(getActivity(), list);
        adapter2 = new HistoryOrderAdapter(getActivity(), list2);
        rv_order.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order.setLayoutManager(linearLayoutManager);
        rv_order.setAdapter(adapter);


        adapter.SetOnItemClickListener(new YourOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Intent intent = new Intent(getActivity(), YourOrderActivity.class);
                intent.putExtra("data_item", list.get(position));
                startActivityForResult(intent, 1);
            }
        });

        adapter2.SetOnItemClickListener(new HistoryOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem model) {
                Intent intent = new Intent(getActivity(), HistoryOrderActivity.class);
                intent.putExtra("data_item", list2.get(position));
                startActivity(intent);
            }
        });

        btnYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param = 1;
                lay_include.setVisibility(View.GONE);
                if (list.isEmpty()) {
                    if (lay_include.getVisibility() == View.GONE) {
                        if (param_yo == 1) {
                            HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                        } else if (param_yo == 2) {
                            HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                        } else if (param_yo == 3) {
                            HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                        }
                    }
                }
                btnYourOrder.setTextColor(Color.WHITE);
                btnHistoryOrder.setTextColor(Color.BLACK);
                btnYourOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_click));
                btnHistoryOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_unclick));
                rv_order.setAdapter(adapter);

            }
        });

        btnHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param = 2;
                lay_include.setVisibility(View.GONE);
                if (list2.isEmpty()) {
                    if (lay_include.getVisibility() == View.GONE) {
                        if (param_history == 1) {
                            HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                        } else if (param_history == 2) {
                            HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                        } else if (param_history == 3) {
                            HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                        }
                    }
                }
                btnHistoryOrder.setTextColor(Color.WHITE);
                btnYourOrder.setTextColor(Color.BLACK);
                btnYourOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_unclick));
                btnHistoryOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_click));
                rv_order.setAdapter(adapter2);

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (param) {
                    case 1:
                        lay_include.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        setAdapter(id, token, String.valueOf(1), false);
                        break;
                    case 2:
                        lay_include.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        setAdapter2(id, token, String.valueOf(1), false);
                        break;
                }
            }
        });
    }

    private BroadcastReceiver updateBadge = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String param = FirebaseMessagingService.INFO_UPDATE;
            if (intent.getAction().equals(param)) {
                setAdapter(id, token, String.valueOf(1), true);
                setAdapter2(id, token, String.valueOf(1), true);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(updateBadge);
    }


    private void setAdapter(String id, String token, String page, final boolean isBackground) {
        lay_include.setVisibility(View.GONE);
        AndroidNetworking.post(UrlServer.URL_LIST_ORDER)
                .setTag("adapter1")
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(OrderModel.class, new OkHttpResponseAndParsedRequestListener<OrderModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, OrderModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                list.clear();
                                adapter.updateList(list);
                                if (param == 1) {
                                    lay_include.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    items.setNama(response.getData().get(i).getNama());
                                    items.setAlamat(response.getData().get(i).getAlamat());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setJobdesk(response.getData().get(i).getJobdesk());
                                    items.setStartDate(response.getData().get(i).getStartDate());
                                    items.setEndDate(response.getData().get(i).getEndDate());
                                    items.setHarga(response.getData().get(i).getHarga());
                                    items.setFoto(response.getData().get(i).getFoto());
                                    items.setStatusOrder(response.getData().get(i).getStatusOrder());
                                    items.setRating(response.getData().get(i).getRating());
                                    items.setOrderDate(response.getData().get(i).getOrderDate());
                                    items.setTelepon(response.getData().get(i).getTelepon());
                                    items.setCodeOrder(response.getData().get(i).getCodeOrder());
                                    items.setHargaPromo(response.getData().get(i).getHargaPromo());
                                    items.setAngkaUnik(response.getData().get(i).getAngkaUnik());
                                    list.add(items);
                                }
                                adapter.updateList(list);
                            } else {
                                param_yo = 3;
                                if (param == 1) {
                                    if (isBackground) {
                                        list.clear();
                                        adapter.updateList(list);
                                        HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                                    } else {
                                        HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                                    }
                                }
//                                Toasty.warning(getActivity(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        if (anError.getErrorCode() != 0) {
                            param_yo = 1;
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 1) {
                                if (isBackground) {
                                    Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                                } else {
                                    HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                                }
                            }
                        } else {
                            param_yo = 2;
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 1) {
                                if (isBackground) {
                                    Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                                } else {
                                    HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                                }
                            }
                        }
                    }
                });
    }

    private void setAdapter2(String id, String token, String page, final boolean isBackground) {
        AndroidNetworking.post(UrlServer.URL_LIST_ORDER_HISTORY)
                .setTag("adapter2")
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(OrderHistoryModel.class, new OkHttpResponseAndParsedRequestListener<OrderHistoryModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, OrderHistoryModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                list2.clear();
                                adapter2.updateList(list2);
                                if (param == 2) {
                                    lay_include.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem items = new ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    items.setNama(response.getData().get(i).getNama());
                                    items.setAlamat(response.getData().get(i).getAlamat());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setJobdesk(response.getData().get(i).getJobdesk());
                                    items.setFoto(response.getData().get(i).getFoto());
                                    items.setHarga(response.getData().get(i).getHarga());
                                    items.setHargaPromo(response.getData().get(i).getHargaPromo());
                                    items.setStartDate(response.getData().get(i).getStartDate());
                                    items.setEndDate(response.getData().get(i).getEndDate());
                                    items.setPromoId(response.getData().get(i).getPromoId());
                                    items.setStatusOrder(response.getData().get(i).getStatusOrder());
                                    items.setRating(response.getData().get(i).getRating());
                                    items.setTelepon(response.getData().get(i).getTelepon());
                                    items.setCodeOrder(response.getData().get(i).getCodeOrder());
                                    items.setOrderDate(response.getData().get(i).getOrderDate());
                                    items.setAngkaUnik(response.getData().get(i).getAngkaUnik());
                                    items.setFinishDate(response.getData().get(i).getFinishDate());
                                    list2.add(items);
                                }
                                adapter2.updateList(list2);
                            } else {
                                param_history = 3;
                                if (param == 2) {
                                    if (isBackground) {
                                        list2.clear();
                                        adapter2.updateList(list2);
                                        HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                                    } else {
                                        HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                                    }
                                }
//                                Toasty.warning(getActivity(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        if (anError.getErrorCode() != 0) {
                            param_history = 1;
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 2) {
                                if (isBackground) {
                                    Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                                } else {
                                    HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                                }
                            }
                        } else {
                            param_history = 2;
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 2) {
                                if (isBackground) {
                                    Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                                } else {
                                    HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                                }
                            }
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String extra = data.getStringExtra("extra");
                list.clear();
                adapter.updateList(list);
                setAdapter(id, token, "1", false);
                setAdapter2(id, token, String.valueOf(1), false);
                if (extra.equals("2")) {
                    String order_id = data.getStringExtra("order_id");
                    String tukang_id = data.getStringExtra("tukang_id");
                    String foto_tukang = data.getStringExtra("foto_tukang");
                    String code_order = data.getStringExtra("code_order");
                    String anggota = data.getStringExtra("type_tukang");
                    String nama = data.getStringExtra("nama_tukang");
                    String type = "";
                    Date date = Calendar.getInstance().getTime();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String waktu = format.format(date);

                    if (anggota.matches("1")) {
                        type = getString(R.string.individu_worker);
                    } else {
                        type = getString(R.string.tim_work) + anggota + getString(R.string.people);
                    }
                    showBottomSheet(view, code_order, waktu, tukang_id, order_id, foto_tukang, type, nama);
                }
            }
        }
    }

    private void showBottomSheet(View view, String kode, String date, final String tukang_id, final String order_id, String foto, String type, String nama) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet, (RelativeLayout) view.findViewById(R.id.bottom_sheet_container));
        btnSend = bottomSheetView.findViewById(R.id.btn_action);
        txtWorker = bottomSheetView.findViewById(R.id.txt_nama_sheet);
        txtJenis = bottomSheetView.findViewById(R.id.txt_jenis_sheet);
        txtDate = bottomSheetView.findViewById(R.id.txt_date_sheet);
        txtCode = bottomSheetView.findViewById(R.id.txt_code_sheet);
        imgSheet = bottomSheetView.findViewById(R.id.img_sheet);
        ratingBar = bottomSheetView.findViewById(R.id.rating_sheet);
        txtKomen = bottomSheetView.findViewById(R.id.txt_komen);
        txtWorker.setText(nama);
        txtCode.setText(kode);
        txtDate.setText(date);
        txtJenis.setText(type);

        if (!foto.isEmpty()) {
            Glide.with(getActivity())
                    .load(UrlServer.URL_FOTO_TUKANG + foto)
                    .into(imgSheet);
        } else {
            Glide.with(getActivity())
                    .load(R.drawable.blank_profile)
                    .into(imgSheet);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() > 0) {
                    giveRating(order_id, tukang_id, String.valueOf(ratingBar.getRating()), txtKomen.getEditText().getText().toString().trim(), bottomSheetDialog);
                } else {
                    Toasty.warning(getActivity(), getString(R.string.plz_gv_rat), Toasty.LENGTH_SHORT).show();
                }
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
    }

    private void giveRating(final String order_id, final String tukang_id, final String rating, final String komen, final BottomSheetDialog dialog) {
        final KProgressHUD hud = new KProgressHUD(getActivity());
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_GIVE_RATING)
                .addBodyParameter("user_id", Prefs.getString(SessionPrefs.U_ID, ""))
                .addBodyParameter("token_login", Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""))
                .addBodyParameter("tukang_id", tukang_id)
                .addBodyParameter("order_id", order_id)
                .addBodyParameter("rating", rating)
                .addBodyParameter("komentar", komen)
                .build()
                .getAsOkHttpResponseAndObject(ResponOrderModel.class, new OkHttpResponseAndParsedRequestListener<ResponOrderModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ResponOrderModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                dialog.dismiss();
                            } else {
                                Toasty.warning(getActivity(), response.getMsg(), Toasty.LENGTH_SHORT).show();
                            }
                            Log.d(TAG, "onResponse: " + tukang_id);
                            Log.d(TAG, "onResponse: " + order_id);
                            Log.d(TAG, "onResponse: " + komen);
                            Log.d(TAG, "onResponse: " + rating);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.d(TAG, "onError: " + anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        } else {
                            Log.d(TAG, "onError: " + anError.getErrorCode());
                            Log.d(TAG, "onError: " + anError.getErrorBody());
                            Log.d(TAG, "onError: " + anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}