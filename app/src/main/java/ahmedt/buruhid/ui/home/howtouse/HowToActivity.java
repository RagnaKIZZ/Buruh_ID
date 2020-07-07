package ahmedt.buruhid.ui.home.howtouse;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ahmedt.buruhid.R;
import ahmedt.buruhid.utils.ViewPageFixed;

public class HowToActivity extends AppCompatActivity {
    private ViewPageFixed viewPager;
    private HowToPagerAdapter introPagerAdapter;
    private TabLayout tabLayout;
    private int positionTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        final List<HowToItem> list = new ArrayList<>();
        list.add(new HowToItem("(1)", getString(R.string.ht1_desc), R.drawable.ht1));
        list.add(new HowToItem("(2)", getString(R.string.ht2_desc), R.drawable.ht2));
        list.add(new HowToItem("(3)", getString(R.string.ht3_desc), R.drawable.ht3));
        list.add(new HowToItem("(4)", getString(R.string.ht4_desc), R.drawable.ht4));
        list.add(new HowToItem("(5)", getString(R.string.ht5_desc), R.drawable.ht5));
        list.add(new HowToItem("(6)", getString(R.string.ht6_desc), R.drawable.ht6));
        list.add(new HowToItem("(7)", getString(R.string.ht7_desc), R.drawable.ht7));
        list.add(new HowToItem("(8)", getString(R.string.ht8_desc), R.drawable.ht8));
        list.add(new HowToItem("(9)", getString(R.string.ht9_desc), R.drawable.ht9));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.howTouse));

        viewPager = findViewById(R.id.view_pager_intro);
        tabLayout = findViewById(R.id.tab_view_intro);
        introPagerAdapter = new HowToPagerAdapter(this, list);
        viewPager.setAdapter(introPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == list.size()){
                    finish();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
