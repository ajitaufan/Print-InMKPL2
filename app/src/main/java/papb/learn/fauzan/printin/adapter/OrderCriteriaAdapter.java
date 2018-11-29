package papb.learn.fauzan.printin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import papb.learn.fauzan.printin.R;
import papb.learn.fauzan.printin.model.OrderCriteriaModel;

public class OrderCriteriaAdapter extends RecyclerView.Adapter<OrderCriteriaAdapter.ViewHolder> {

    private ArrayList<OrderCriteriaModel> orderCriteriaModelList;
    private Context context;

    private static final String[] list_paper_type = {"A4-70gr", "A4-80gr"};
    private static final String[] list_bind_type = {"Lakban Hitam", "Spiral"};
    private static final String[] list_front_color = {"Transparent", "Blue","Red"};
    private static final String[] list_back_color = {"Blue", "Red","Green"};

    public OrderCriteriaAdapter(){

    }

    public OrderCriteriaAdapter(ArrayList<OrderCriteriaModel> dataSet,Context context) {
        this.orderCriteriaModelList = dataSet;
        this.context = context;
    }

    @Override
    public OrderCriteriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_kriteria_order,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCriteriaAdapter.ViewHolder holder, int position) {
        holder.tv_file_name.setText(orderCriteriaModelList.get(position).getFileName());
        if(!orderCriteriaModelList.get(position).isColored()){
            holder.rb_colored.setActivated(true);
        }
        holder.cb_bind.setActivated(orderCriteriaModelList.get(position).isBinded());

        ArrayAdapter<String> paper_type_adapter = new ArrayAdapter<>(this.context,
                R.layout.support_simple_spinner_dropdown_item,list_paper_type);
        ArrayAdapter<String> bind_type_adapter = new ArrayAdapter<>(this.context,
                R.layout.support_simple_spinner_dropdown_item,list_bind_type);
        ArrayAdapter<String> front_color_adapter = new ArrayAdapter<>(this.context,
                R.layout.support_simple_spinner_dropdown_item,list_front_color);
        ArrayAdapter<String> back_color_adapter = new ArrayAdapter<>(this.context,
                R.layout.support_simple_spinner_dropdown_item,list_back_color);

        holder.sp_paper_type.setAdapter(paper_type_adapter);
        holder.sp_bind_type.setAdapter(bind_type_adapter);
        holder.sp_front_bind.setAdapter(front_color_adapter);
        holder.sp_back_bind.setAdapter(back_color_adapter);
    }


    @Override
    public int getItemCount() {
        return orderCriteriaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_file_name;
        private RadioButton rb_colored,rb_monochrome;

        private CheckBox cb_bind;
        private Spinner sp_bind_type,sp_front_bind,sp_back_bind,sp_paper_type;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
            rb_colored = itemView.findViewById(R.id.rb_colored);
            rb_monochrome = itemView.findViewById(R.id.rb_monochrome);
            sp_paper_type = itemView.findViewById(R.id.sp_paper_type);

            cb_bind = itemView.findViewById(R.id.cb_is_binded);
            sp_bind_type = itemView.findViewById(R.id.sp_bind_type);
            sp_front_bind = itemView.findViewById(R.id.sp_front_bind);
            sp_back_bind = itemView.findViewById(R.id.sp_back_bind);

            rb_colored.setChecked(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
