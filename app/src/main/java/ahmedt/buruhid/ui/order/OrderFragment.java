package ahmedt.buruhid.ui.order;

import android.graphics.Color;
import android.os.Bundle;
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
import java.util.HashSet;
import java.util.Set;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.order.modelHistoryOrder.OrderHistoryModel;
import ahmedt.buruhid.ui.order.modelOrder.DataItem;
import ahmedt.buruhid.ui.order.modelOrder.OrderModel;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class OrderFragment extends Fragment {
    private Button btnYourOrder, btnHistoryOrder;
    private RecyclerView rv_order;
    private ArrayList<DataItem> list = new ArrayList<>();
    private ArrayList<ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem> list2 = new ArrayList<>();
    private YourOrderAdapter adapter;
    private HistoryOrderAdapter adapter2;
    private ProgressBar progressBar;
    String id = Prefs.getString(SessionPrefs.U_ID, "");
    String token = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(id, token, "1");
        setAdapter2(id, token, "1");
    }

    private void findView(View view){
//        Set<YourOrderItem> set = new HashSet<>();
//        set.addAll(list);
        btnHistoryOrder = view.findViewById(R.id.btn_history_order);
        btnYourOrder = view.findViewById(R.id.btn_your_order);
        rv_order = view.findViewById(R.id.rc_order);
        progressBar = view.findViewById(R.id.progress_bar);
        adapter = new YourOrderAdapter(getActivity(), list);
        adapter2 = new HistoryOrderAdapter(getActivity(), list2);
        rv_order.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order.setLayoutManager(linearLayoutManager);
        rv_order.setAdapter(adapter);


        adapter.SetOnItemClickListener(new YourOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataItem model) {
                Toast.makeText(getActivity(), model.getNama(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter2.SetOnItemClickListener(new HistoryOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem model) {
                Toast.makeText(getActivity(), model.getNama(), Toast.LENGTH_SHORT).show();
            }
        });

        btnYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                btnHistoryOrder.setTextColor(Color.WHITE);
                btnYourOrder.setTextColor(Color.BLACK);
                btnYourOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_unclick));
                btnHistoryOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_click));
                rv_order.setAdapter(adapter2);

            }
        });
    }

    private void setAdapter(String id, String token, String page){
        AndroidNetworking.post(UrlServer.URL_LIST_ORDER)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(OrderModel.class, new OkHttpResponseAndParsedRequestListener<OrderModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, OrderModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final DataItem items = new DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    items.setNama(response.getData().get(i).getNama());
                                    items.setAlamat(response.getData().get(i).getAlamat());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setJobdesk(response.getData().get(i).getJobdesk());
                                    items.setFoto(response.getData().get(i).getFoto());
                                    items.setStatusOrder(response.getData().get(i).getStatusOrder());
                                    items.setRating(response.getData().get(i).getRating());
                                    items.setTelepon(response.getData().get(i).getTelepon());
                                    items.setCodeOrder(response.getData().get(i).getCodeOrder());
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
                        Toasty.error(getActivity(), R.string.cek_internet, Toasty.LENGTH_SHORT).show();
                    }
                });
    }

    private void setAdapter2(String id, String token, String page){
        AndroidNetworking.post(UrlServer.URL_LIST_ORDER_HISTORY)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .addBodyParameter("page", page)
                .build()
                .getAsOkHttpResponseAndObject(OrderHistoryModel.class, new OkHttpResponseAndParsedRequestListener<OrderHistoryModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, OrderHistoryModel response) {
                        progressBar.setVisibility(View.GONE);
                        if (okHttpResponse.isSuccessful()){
                            if (response.getCode() == 200){
                                for (int i = 0; i < response.getData().size(); i++) {
                                    final ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem items = new ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem();
                                    items.setId(response.getData().get(i).getId());
                                    items.setTukangId(response.getData().get(i).getTukangId());
                                    items.setNama(response.getData().get(i).getNama());
                                    items.setAlamat(response.getData().get(i).getAlamat());
                                    items.setAnggota(response.getData().get(i).getAnggota());
                                    items.setJobdesk(response.getData().get(i).getJobdesk());
                                    items.setFoto(response.getData().get(i).getFoto());
                                    items.setStatusOrder(response.getData().get(i).getStatusOrder());
                                    items.setRating(response.getData().get(i).getRating());
                                    items.setTelepon(response.getData().get(i).getTelepon());
                                    items.setCodeOrder(response.getData().get(i).getCodeOrder());
                                    items.setOrderDate(response.getData().get(i).getOrderDate());
                                    items.setFinishDate(response.getData().get(i).getFinishDate());
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
                        Toasty.error(getActivity(), R.string.cek_internet, Toasty.LENGTH_SHORT).show();
                    }
                });

    }
}