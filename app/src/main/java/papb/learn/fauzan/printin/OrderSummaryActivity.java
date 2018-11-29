package papb.learn.fauzan.printin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import papb.learn.fauzan.printin.adapter.OrderSummaryAdapter;
import papb.learn.fauzan.printin.model.OrderCriteriaModel;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CHANNEL_ID ="PRINTIN_ID_NOTIF_CHANNEL";

    private RecyclerView rvOrderSummary;
    private OrderSummaryAdapter orderSummaryAdapter;
    private RecyclerView.LayoutManager lmOrderSummary;

    private Button btn_checkout_order;

    private NotificationCompat.Builder notifyOrderBuilder;
    private NotificationManager notifyOrderManager;
    private static final int NOTIFICATION_ORDER_ID = 0;

    private TextView tv_deliv_fee,tv_deliv_adress,tv_deliv_distance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        createNotificationChannel();

        Intent getFromMap = getIntent();

        btn_checkout_order = findViewById(R.id.btn_checkout_order);
        btn_checkout_order.setOnClickListener(this);

        tv_deliv_adress = findViewById(R.id.tv_shipping_adress);
        tv_deliv_distance = findViewById(R.id.tv_adress_distance);
        tv_deliv_fee = findViewById(R.id.tv_shipping_fee);
        tv_deliv_adress.setText(getFromMap.getStringExtra("deliveryAdress"));
        tv_deliv_distance.setText(getFromMap.getStringExtra("deliveryDistance"));
        tv_deliv_fee.setText(getFromMap.getStringExtra("deliveryFee"));

        notifyOrderManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        showListOrderSummary();
    }

    private void showListOrderSummary(){
        rvOrderSummary = findViewById(R.id.rv_summary_order);
        rvOrderSummary.setHasFixedSize(true);

        lmOrderSummary = new LinearLayoutManager(this);
        rvOrderSummary.setLayoutManager(lmOrderSummary);

        orderSummaryAdapter = new OrderSummaryAdapter(initializeOrderSummary(),getApplicationContext());
        rvOrderSummary.setAdapter(orderSummaryAdapter);
    }

    private ArrayList<OrderCriteriaModel> initializeOrderSummary() {
        ArrayList<OrderCriteriaModel> orderSummaryModels = new ArrayList<>();
        OrderCriteriaModel orderTest1 = new OrderCriteriaModel("Laporan Sisop","A4-80","Lakban","Transparan","Biru",true,true);
        orderSummaryModels.add(orderTest1);

        return orderSummaryModels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_checkout_order:
                Intent intent = new Intent(this,OrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

                notifyOrderBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setContentTitle(getString(R.string.order_diproses))
                        .setContentText(getString(R.string.tekan_lihat_order))
                        .setSmallIcon(R.drawable.ic_local_shipping_black_24dp)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                Notification orderNotification = notifyOrderBuilder.build();
                notifyOrderManager.notify(NOTIFICATION_ORDER_ID,orderNotification);
                break;
        }
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
