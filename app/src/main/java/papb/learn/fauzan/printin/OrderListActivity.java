package papb.learn.fauzan.printin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        getOrderFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }

    private void getOrderFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new OrderFragment()).commit();
    }
}
