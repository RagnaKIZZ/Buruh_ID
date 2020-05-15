package ahmedt.buruhid.make_order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ahmedt.buruhid.R;

public class OrderDoneActivity extends AppCompatActivity {
    private Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_done);
        findView();
    }

    private void findView(){
        btnDone = findViewById(R.id.btn_order_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeOrderActivity.makeOrderActivity.finish();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        MakeOrderActivity.makeOrderActivity.finish();
        finish();
    }
}
