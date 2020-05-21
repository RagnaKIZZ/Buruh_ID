package ahmedt.buruhid.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ahmedt.buruhid.R;
import ahmedt.buruhid.make_order.MakeOrderActivity;

public class HomeFragment extends Fragment {
    private RecyclerView rc_WN, rc_Other;
    private WhatsNewAdapter mAdapter_1;
    private OtherAdapter mAdapter_2;
    private ArrayList<WhatsNewItem> list = new ArrayList<>();
    private ArrayList<OtherItem> list2 = new ArrayList<>();
    private ImageView imgSolo, imgTeam;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter1();
        setAdapter2();
    }

    private void findView(View view){
        imgSolo = view.findViewById(R.id.img_solo);
        imgTeam = view.findViewById(R.id.img_team);
        rc_WN = view.findViewById(R.id.rc_whats_new);
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
        mAdapter_1.SetOnItemClickListener(new WhatsNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, WhatsNewItem model) {
                Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter_2.SetOnItemClickListener(new OtherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, OtherItem model) {
                Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        imgSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeOrderActivity.class);
                i.putExtra("type", "Individu Worker");
                i.putExtra("photo", R.drawable.solo_worker);
                View shareView = imgSolo;
                String transitionName = getString(R.string.img);
                ActivityOptions transOpt = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    transOpt = ActivityOptions.makeSceneTransitionAnimation(getActivity(), shareView, transitionName);
                    startActivity(i, transOpt.toBundle());
                }else {
                    startActivity(i);
                }
            }
        });

        imgTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeOrderActivity.class);
                i.putExtra("type", "Team Worker");
                i.putExtra("photo", R.drawable.team_worker);
                View shareView = imgTeam;
                String transitionName = getString(R.string.img);
                ActivityOptions transOpt = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    transOpt = ActivityOptions.makeSceneTransitionAnimation(getActivity(), shareView, transitionName);
                    startActivity(i, transOpt.toBundle());
                }else {
                    startActivity(i);
                }
            }
        });

    }

    private void setAdapter1(){
        list.add(new WhatsNewItem("Bug Fixed", "Memperbaiki bug", R.drawable.virus));
        list.add(new WhatsNewItem("Update Covid-19", "Info virus Indonesia", R.drawable.virus));
        list.add(new WhatsNewItem("Bug Fixed", "Memperbaiki bug", R.drawable.virus));
        mAdapter_1.updateList(list);
    }

    private void setAdapter2(){
        list2.add(new OtherItem("Secured Transaction", R.drawable.load_screen_2));
        list2.add(new OtherItem("How to use", R.drawable.load_screen_3));
        list2.add(new OtherItem("About us", R.drawable.logoburuh));
        mAdapter_2.updateList(list2);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}