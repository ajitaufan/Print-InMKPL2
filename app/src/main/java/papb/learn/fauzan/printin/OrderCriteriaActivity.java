package papb.learn.fauzan.printin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import papb.learn.fauzan.printin.adapter.OrderCriteriaAdapter;
import papb.learn.fauzan.printin.model.OrderCriteriaModel;
import papb.learn.fauzan.printin.model.UploadFileModel;

public class OrderCriteriaActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvOrder;
    private OrderCriteriaAdapter adapterOrderCriteria;
    private RecyclerView.LayoutManager lmOrderCriteria;

    private Button btn_save_criteria;

    private ArrayList<UploadFileModel> uploadFileModels = new ArrayList<>();
    ArrayList<OrderCriteriaModel> orderCriteriaModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_criteria);
        btn_save_criteria = findViewById(R.id.btn_simpan_kriteria);

        btn_save_criteria.setOnClickListener(this);

        uploadFileModels = (ArrayList<UploadFileModel>) getIntent().getSerializableExtra("listFile");

        showListOrder();
    }

    private void showListOrder(){
        rvOrder = findViewById(R.id.rv_order_criteria);

        rvOrder.setHasFixedSize(true);

        lmOrderCriteria = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(lmOrderCriteria);

        adapterOrderCriteria=new OrderCriteriaAdapter(initializeData(),getApplicationContext());
        rvOrder.setAdapter(adapterOrderCriteria);
    }

    private ArrayList<OrderCriteriaModel> initializeData(){
        for(int i = 0; i < uploadFileModels.size(); i++){
            OrderCriteriaModel model = new OrderCriteriaModel();
            model.setFileName(uploadFileModels.get(i).getNamaFile());
            orderCriteriaModels.add(model);
        }

        return orderCriteriaModels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simpan_kriteria :
                // TODO: 29/11/18 uncomment this for next version
//                Intent toMapDelivery = new Intent(this,OrderMapsActivity.class);
//              //  toMapDelivery.putExtra("listOrder",orderCriteriaModels);
//                startActivity(toMapDelivery);
                break;
        }
    }
}
