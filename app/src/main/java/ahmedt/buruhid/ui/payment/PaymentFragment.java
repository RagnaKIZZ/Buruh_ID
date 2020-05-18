package ahmedt.buruhid.ui.payment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.payment.modelPayment.DataItem;
import ahmedt.buruhid.ui.payment.modelPayment.PaymentModel;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class PaymentFragment extends Fragment {
    private Button btnBill, btnHistory;
    private RecyclerView recyclerView;
    private ArrayList<DataItem> list = new ArrayList<>();
    private ArrayList<DataItem> list2 = new ArrayList<>();
    private BillAdapter adapter;
    private ProgressBar progressBar;
    private HistoryPaymentAdapter adapter2;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(id, token, String.valueOf(1));
        setAdapter2(id, token, String.valueOf(1));
    }

    private void setAdapter2(String id, String token, String page) {
        AndroidNetworking.post(UrlServer.URL_LIST_PAYMENT_HISTORY)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(PaymentModel.class, new OkHttpResponseAndParsedRequestListener<PaymentModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PaymentModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setAnggota(response.getData().get(i).getAnggota());
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
                            }else{
                                Toasty.warning(getActivity(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });

    }

    private void setAdapter(String id, String token, String page) {
        AndroidNetworking.post(UrlServer.URL_LIST_PAYMENT)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(PaymentModel.class, new OkHttpResponseAndParsedRequestListener<PaymentModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, PaymentModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
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
                            }else{
                                Toasty.warning(getActivity(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        if (anError.getErrorCode() != 0){
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        }else{
                            Log.d("ERR", "onError: "+anError.getErrorCode());
                            Log.d("ERR", "onError: "+anError.getErrorBody());
                            Log.d("ERR", "onError: "+anError.getErrorDetail());
                            Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    private void findView(View view) {
        btnBill = view.findViewById(R.id.btn_payment);
        btnHistory = view.findViewById(R.id.btn_history_payment);
        recyclerView = view.findViewById(R.id.rc_payment);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setHasFixedSize(true);
        adapter = new BillAdapter(getActivity(), list);
        adapter2 = new HistoryPaymentAdapter(getActivity(), list2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Toast.makeText(getActivity(), model.getNominal(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter2.SetOnItemClickListener(new HistoryPaymentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Toast.makeText(getActivity(), model.getNominal(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}