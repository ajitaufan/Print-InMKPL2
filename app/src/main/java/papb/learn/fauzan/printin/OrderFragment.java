package papb.learn.fauzan.printin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import papb.learn.fauzan.printin.adapter.OrderViewAdapter;
import papb.learn.fauzan.printin.model.OrderModel;

public class OrderFragment extends Fragment implements View.OnClickListener {

    private FloatingActionButton myFab;
    ArrayList<OrderModel> orderList;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);

        orderList = new ArrayList<>();

//        RecyclerView recyclerView = v.findViewById(R.id.rv_orderlist);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        OrderViewAdapter adapter = new OrderViewAdapter(getContext(), orderList);
//        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myFab = view.findViewById(R.id.floatingActionButton);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG).show();
//            }
//        });
        myFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatingActionButton:
                // TODO: 29/11/18 uncomment this for next version
//                Intent toUploadFile = new Intent(this.getContext(),UploadFileActivity.class);
//                startActivity(toUploadFile);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        initListOrder();
    }

    private void initListOrder() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Order");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        mDatabase.child(currentFirebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot pasienSnapshot : dataSnapshot.getChildren()){
                    OrderModel orderModel = pasienSnapshot.getValue(OrderModel.class);
                    orderList.add(orderModel);
                }

                RecyclerView recyclerView = getView().findViewById(R.id.rv_orderlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                OrderViewAdapter adapter = new OrderViewAdapter(getContext(), orderList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
