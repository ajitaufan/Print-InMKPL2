package papb.learn.fauzan.printin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import papb.learn.fauzan.printin.R;
import papb.learn.fauzan.printin.model.OrderModel;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.OrderViewHolder> {

    private Context mContext;
    private ArrayList<OrderModel> orderList;

    public OrderViewAdapter(Context mContext, ArrayList<OrderModel> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        OrderModel order = orderList.get(position);

        holder.tvNama.setText(order.getDokumen());
        holder.tvTanggal.setText(order.getTanggal());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView tvNama;
        TextView tvTanggal;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_file);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal_order);
        }
    }
}
