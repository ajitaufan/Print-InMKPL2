package papb.learn.fauzan.printin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import papb.learn.fauzan.printin.R;
import papb.learn.fauzan.printin.model.OrderCriteriaModel;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private ArrayList<OrderCriteriaModel> orderCriteriaModelList;
    private Context context;

    public OrderSummaryAdapter() {

    }

    public OrderSummaryAdapter(ArrayList<OrderCriteriaModel> orderCriteriaModelList,Context context) {
        this.orderCriteriaModelList = orderCriteriaModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_summary_order,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder holder, int position) {
        holder.tv_filename.setText(orderCriteriaModelList.get(position).getFileName());
        if(orderCriteriaModelList.get(position).isColored()){
            String isColored = "Warna";
            holder.tv_is_colored.setText(isColored);
        } else {

        }
        holder.tv_paper_type.setText(orderCriteriaModelList.get(position).getPaperType());

        if(orderCriteriaModelList.get(position).isBinded()){
            String isBinded = "Jilid";
            holder.tv_is_binded.setText(isBinded);
        } else {

        }
        holder.tv_front_bind.setText(orderCriteriaModelList.get(position).getFrontColor());
        holder.tv_back_bind.setText(orderCriteriaModelList.get(position).getBackColor());

    }

    @Override
    public int getItemCount() {
        return orderCriteriaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_filename,tv_is_colored,tv_paper_type;
        private TextView tv_is_binded,tv_front_bind,tv_back_bind;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            tv_filename = itemView.findViewById(R.id.tv_summary_filename);
            tv_is_colored = itemView.findViewById(R.id.tv_summary_is_colored);
            tv_paper_type = itemView.findViewById(R.id.tv_summary_paper_type);

            tv_is_binded = itemView.findViewById(R.id.tv_summary_is_binded);
            tv_front_bind = itemView.findViewById(R.id.tv_summary_front_bind);
            tv_back_bind = itemView.findViewById(R.id.tv_summary_back_bind);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
