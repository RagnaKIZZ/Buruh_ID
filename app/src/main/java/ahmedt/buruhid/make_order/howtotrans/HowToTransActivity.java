package ahmedt.buruhid.make_order.howtotrans;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ahmedt.buruhid.R;
import ahmedt.buruhid.utils.HelperClass;

public class HowToTransActivity extends AppCompatActivity {
    private ImageButton imgCall;
    private LinearLayout ln_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_trans);
        imgCall = findViewById(R.id.img_exp_call);
        ln_call = findViewById(R.id.ln_call_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.how_to_transfer));

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ln_call.getVisibility() == View.GONE) {
                    HelperClass.expand(ln_call);
                    imgCall.setImageResource(R.drawable.ic_expand_less_black_24dp);
                } else {
                    HelperClass.collapse(ln_call);
                    imgCall.setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
