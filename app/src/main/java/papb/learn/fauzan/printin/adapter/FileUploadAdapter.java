package papb.learn.fauzan.printin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import papb.learn.fauzan.printin.R;
import papb.learn.fauzan.printin.model.UploadFileModel;

public class FileUploadAdapter extends RecyclerView.Adapter<FileUploadAdapter.ViewHolder> {

    private ArrayList<UploadFileModel> fileModelList;
    private Context context;


    public FileUploadAdapter() {
    }

    public FileUploadAdapter(ArrayList<UploadFileModel> fileModelList, Context context) {
        this.fileModelList = fileModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FileUploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_file_upload,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileUploadAdapter.ViewHolder holder, int position) {
        holder.tv_file_name.setText(fileModelList.get(position).getNamaFile());
        holder.tv_type_file.setText(fileModelList.get(position).getTipeFile());
        holder.tv_file_size.setText(fileModelList.get(position).getUkuranFile());
    }

    @Override
    public int getItemCount() {
        return fileModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_file_name,tv_file_size,tv_type_file;
        ImageView iv_img_file;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_file_name = itemView.findViewById(R.id.tv_upload_nama_file);
            tv_file_size = itemView.findViewById(R.id.tv_ukuran_file);
            tv_type_file = itemView.findViewById(R.id.tv_tipe_file);
            iv_img_file = itemView.findViewById(R.id.iv_file_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


}
