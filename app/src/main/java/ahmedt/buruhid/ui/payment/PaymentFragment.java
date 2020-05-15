package ahmedt.buruhid.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ahmedt.buruhid.R;

public class PaymentFragment extends Fragment {
    private Button btnBill, btnHistory;
    private RecyclerView recyclerView;
    private ArrayList<BillItem> list = new ArrayList<>();
    private ArrayList<HistoryPaymentItem> list2 = new ArrayList<>();
    private BillAdapter adapter;
    private HistoryPaymentAdapter adapter2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setAdapter2();
    }

    private void setAdapter2() {
        list2.add(new HistoryPaymentItem("500000", "Individual Worker", "24 Januari 2020"));
        list2.add(new HistoryPaymentItem("700000", "Individual Worker", "24 Januari 2020"));
        adapter2.updateList(list2);
    }

    private void setAdapter() {
        list.add(new BillItem("500000", "Individual Worker", "3 days"));
        list.add(new BillItem("700000", "Individual Worker", "4 days"));
        adapter.updateList(list);
    }

    private void findView(View view){
        btnBill = view.findViewById(R.id.btn_payment);
        btnHistory = view.findViewById(R.id.btn_history_payment);
        recyclerView = view.findViewById(R.id.rc_payment);
        recyclerView.setHasFixedSize(true);
        adapter = new BillAdapter(getActivity(), list);
        adapter2 = new HistoryPaymentAdapter(getActivity(), list2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBill.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_click));
                btnHistory.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_unclick));
                recyclerView.setAdapter(adapter);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBill.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_unclick));
                btnHistory.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_click));
                recyclerView.setAdapter(adapter2);
            }
        });

        adapter.SetOnItemClickListener(new BillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BillItem model) {
                Toast.makeText(getActivity(), model.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter2.SetOnItemClickListener(new HistoryPaymentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HistoryPaymentItem model) {
                Toast.makeText(getActivity(), model.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}