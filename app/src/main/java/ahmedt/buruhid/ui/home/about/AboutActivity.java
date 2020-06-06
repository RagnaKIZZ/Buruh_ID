package ahmedt.buruhid.ui.home.about;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ahmedt.buruhid.R;
import ahmedt.buruhid.utils.HelperClass;

public class AboutActivity extends AppCompatActivity {
    private ImageButton imgAddress, imgCall;
    private LinearLayout ln_address, ln_call, container_call, container_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findView();
    }

    private void findView(){
        imgAddress = findViewById(R.id.img_exp_address);
        imgCall = findViewById(R.id.img_exp_call);
        ln_address = findViewById(R.id.ln_address_about);
        ln_call = findViewById(R.id.ln_call_about);
        container_address = findViewById(R.id.container_address);
        container_call = findViewById(R.id.container_call);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.aboud_us));

        imgAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ln_address.getVisibility() == View.GONE){
//                    TransitionManager.beginDelayedTransition(container_address, new AutoTransition());
//                    ln_address.setVisibility(View.VISIBLE);
                    HelperClass.expand(ln_address);
                    imgAddress.setImageResource(R.drawable.ic_expand_less_black_24dp);
                }else {
//                    TransitionManager.beginDelayedTransition(container_address, new AutoTransition());
//                    ln_address.setVisibility(View.GONE);
                    HelperClass.collapse(ln_address);
                    imgAddress.setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ln_call.getVisibility() == View.GONE){
//                    TransitionManager.beginDelayedTransition(container_call, new AutoTransition());
//                    ln_call.setVisibility(View.VISIBLE);
                    HelperClass.expand(ln_call);
                    imgCall.setImageResource(R.drawable.ic_expand_less_black_24dp);
                }else {
//                    TransitionManager.beginDelayedTransition(container_call, new AutoTransition());
//                    ln_call.setVisibility(View.GONE);
                    HelperClass.collapse(ln_call);
                    imgCall.setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
            }
        });
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
}
