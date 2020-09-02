package ahmedt.buruhid.ui.payment;

import android.content.Intent;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.payment.detailHistoryPayment.DetailHistoryPaymentActivity;
import ahmedt.buruhid.ui.payment.detailPayment.DetailPaymentActivity;
import ahmedt.buruhid.ui.payment.modelPayment.DataItem;
import ahmedt.buruhid.ui.payment.modelPayment.PaymentModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class PaymentFragment extends Fragment {
    private Button btnBill, btnHistory;
    private RecyclerView recyclerView;
    private ArrayList<DataItem> list = new ArrayList<>();
    private ArrayList<DataItem> list2 = new ArrayList<>();
    private BillAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout lay_include;
    private FloatingActionButton btnRefresh;
    private HistoryPaymentAdapter adapter2;
    private TextView txtMsg;
    private ImageView imgMsg;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");
    int param;
    int param_bill;
    int param_history;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        param = 1;

        if (isAdded()) {
            setAdapter(id, token, String.valueOf(1));
            setAdapter2(id, token, String.valueOf(1));
        }
    }

    private void setAdapter2(String id, String token, String page) {
        AndroidNetworking.post(UrlServer.URL_LIST_PAYMENT_HISTORY)
                .setTag("adapter2")
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(PaymentModel.class, new OkHttpResponseAndParsedRequestListener<PaymentModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PaymentModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                list2.clear();
                                if (param == 2) {
                                    lay_include.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setOrderId(response.getData().get(i).getOrderId());
                                    items.setCodePembayaran(response.getData().get(i).getCodePembayaran());
                                    items.setEndDate(response.getData().get(i).getEndDate());
                                    items.setCreateDate(response.getData().get(i).getCreateDate());
                                    items.setNominal(response.getData().get(i).getNominal());
                                    items.setStatusPembayaran(response.getData().get(i).getStatusPembayaran());
                                    items.setBuktiPembayaran(response.getData().get(i).getBuktiPembayaran());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    list2.add(items);
                                }
                                adapter2.updateList(list2);
                            } else {
                                param_history = 3;
                                if (param == 2) {

                                    HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));

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
                                HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                            }
                        } else {
                            param_history = 2;
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 2) {
                                HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                            }
                        }
                    }
                });

    }

    private void setAdapter(String id, String token, String page) {
        AndroidNetworking.post(UrlServer.URL_LIST_PAYMENT)
                .setTag("adapter1")
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(PaymentModel.class, new OkHttpResponseAndParsedRequestListener<PaymentModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PaymentModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                list.clear();
                                if (param == 1) {
                                    lay_include.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setOrderId(response.getData().get(i).getOrderId());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setCodePembayaran(response.getData().get(i).getCodePembayaran());
                                    items.setEndDate(response.getData().get(i).getEndDate());
                                    items.setCreateDate(response.getData().get(i).getCreateDate());
                                    items.setNominal(response.getData().get(i).getNominal());
                                    items.setStatusPembayaran(response.getData().get(i).getStatusPembayaran());
                                    items.setBuktiPembayaran(response.getData().get(i).getBuktiPembayaran());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    list.add(items);
                                }
                                adapter.updateList(list);
                            } else {
                                param_bill = 3;
                                if (param == 1) {
                                    if (isAdded()) {
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
                        lay_include.setVisibility(View.VISIBLE);
                        if (anError.getErrorCode() != 0) {
                            param_bill = 1;
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 1) {
                                HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                            }
                        } else {
                            param_bill = 2;
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            if (param == 1) {
                                HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                            }
                        }
                    }
                });
    }

    private void findView(View view) {
        btnBill = view.findViewById(R.id.btn_payment);
        btnHistory = view.findViewById(R.id.btn_history_payment);
        recyclerView = view.findViewById(R.id.rc_payment);
        progressBar = view.findViewById(R.id.progress_bar);
        lay_include = view.findViewById(R.id.include_lay);
        imgMsg = view.findViewById(R.id.img_message);
        txtMsg = view.findViewById(R.id.txt_msg);
        lay_include.setVisibility(View.GONE);
        btnRefresh = view.findViewById(R.id.floatingActionButton);
        recyclerView.setHasFixedSize(true);
        adapter = new BillAdapter(getActivity(), list);
        adapter2 = new HistoryPaymentAdapter(getActivity(), list2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_include.setVisibility(View.GONE);
                param = 1;
                if (list.isEmpty()) {
                    if (lay_include.getVisibility() == View.GONE) {
                        if (param_bill == 1) {
                            HelperClass.serverError(getActivity(), lay_include, imgMsg, txtMsg);
                        } else if (param_bill == 2) {
                            HelperClass.InetError(getActivity(), lay_include, imgMsg, txtMsg);
                        } else if (param_bill == 3) {
                            HelperClass.emptyError(lay_include, imgMsg, txtMsg, getString(R.string.blm_order));
                        }
                    }
                }
                btnBill.setTextColor(Color.WHITE);
                btnHistory.setTextColor(Color.BLACK);
                btnBill.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_click));
                btnHistory.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_unclick));
                recyclerView.setAdapter(adapter);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_include.setVisibility(View.GONE);
                param = 2;
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
                btnBill.setTextColor(Color.BLACK);
                btnHistory.setTextColor(Color.WHITE);
                btnBill.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_unclick));
                btnHistory.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_click));
                recyclerView.setAdapter(adapter2);
            }
        });

        adapter.SetOnItemClickListener(new BillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Intent i = new Intent(getActivity(), DetailPaymentActivity.class);
                i.putExtra("data_item", list.get(position));
                startActivityForResult(i, 1);
            }
        });

        adapter2.SetOnItemClickListener(new HistoryPaymentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Intent i = new Intent(getActivity(), DetailHistoryPaymentActivity.class);
                i.putExtra("data_item", list2.get(position));
                startActivity(i);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (param) {
                    case 1:
                        lay_include.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        setAdapter(id, token, String.valueOf(1));
                        break;
                    case 2:
                        lay_include.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        setAdapter2(id, token, String.valueOf(1));
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                list.clear();
                adapter.updateList(list);
                setAdapter(id, token, String.valueOf(1));
            }
        }
    }

}