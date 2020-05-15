package ahmedt.buruhid.ui.order;

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
import java.util.HashSet;
import java.util.Set;

import ahmedt.buruhid.R;

public class OrderFragment extends Fragment {
    private Button btnYourOrder, btnHistoryOrder;
    private RecyclerView rv_order;
    private ArrayList<YourOrderItem> list = new ArrayList<>();
    private ArrayList<HistoryOrderItem> list2 = new ArrayList<>();
    private YourOrderAdapter adapter;
    private HistoryOrderAdapter adapter2;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setAdapter2();
    }

    private void findView(View view){
//        Set<YourOrderItem> set = new HashSet<>();
//        set.addAll(list);
        btnHistoryOrder = view.findViewById(R.id.btn_history_order);
        btnYourOrder = view.findViewById(R.id.btn_your_order);
        rv_order = view.findViewById(R.id.rc_order);
        adapter = new YourOrderAdapter(getActivity(), list);
        adapter2 = new HistoryOrderAdapter(getActivity(), list2);
        rv_order.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order.setLayoutManager(linearLayoutManager);
        rv_order.setAdapter(adapter);



        adapter.SetOnItemClickListener(new YourOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, YourOrderItem model) {
                Toast.makeText(getActivity(), model.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter2.SetOnItemClickListener(new HistoryOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HistoryOrderItem model) {
                Toast.makeText(getActivity(), model.getType(), Toast.LENGTH_SHORT).show();
            }
        });

        btnYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYourOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_click));
                btnHistoryOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_unclick));
                rv_order.setAdapter(adapter);

            }
        });

        btnHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYourOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_left_unclick));
                btnHistoryOrder.setBackground(getResources().getDrawable(R.drawable.bg_btn_right_click));
                rv_order.setAdapter(adapter2);

            }
        });
    }

    private void setAdapter(){
        list.add(new YourOrderItem("Ilham", "Individual Worker", "Cat rumah", "Working", "Rp 500000", 4, R.drawable.ppkuli2));
        list.add(new YourOrderItem("Oji", "Individual Worker", "Cat rumah", "Working", "Rp 500000", 4, R.drawable.ppkuli));
        adapter.updateList(list);
    }

    private void setAdapter2(){
        list2.add(new HistoryOrderItem("Individual Worker", "Cat Rumah", "Rp 500000", "24 Januari 2024", R.drawable.ppkuli2));
        list2.add(new HistoryOrderItem("Individual Worker", "Cat Rumah", "Rp 500000", "24 Januari 2024", R.drawable.ppkuli));
        adapter2.updateList(list2);
    }
}