package papb.learn.fauzan.printin;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import papb.learn.fauzan.printin.adapter.FileUploadAdapter;
import papb.learn.fauzan.printin.model.UploadFileModel;

public class UploadFileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_FOR_ON_ACTIVITY_RESULT = 86;

    private Button btn_upload_file,btn_select_file,btn_simpan_file;
    private ProgressBar pb_upload_file;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;

    private RecyclerView rvFile;
    private FileUploadAdapter adapterFile;
    private RecyclerView.LayoutManager lmFileUpload;

    private Uri[] pdfUri;
    ArrayList<UploadFileModel> uploadFileModels  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        btn_upload_file = findViewById(R.id.btn_upload_file);
        btn_select_file = findViewById(R.id.btn_select_file);
        btn_simpan_file = findViewById(R.id.btn_simpan_file);

        btn_upload_file.setOnClickListener(this);
        btn_select_file.setOnClickListener(this);
        btn_simpan_file.setOnClickListener(this);

        pb_upload_file = findViewById(R.id.pb_upload_file);
        pb_upload_file.setVisibility(View.INVISIBLE);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    private void setRecyclerView (){
        rvFile = findViewById(R.id.rv_file);
        rvFile.setHasFixedSize(true);

        lmFileUpload = new LinearLayoutManager(this);
        rvFile.setLayoutManager(lmFileUpload);

        if(adapterFile != null){
            rvFile.setAdapter(adapterFile);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_select_file:
                if (ContextCompat.checkSelfPermission(UploadFileActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) {
                    selectPDF();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.requestPermissions(UploadFileActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                    }
                }
                break;
            case R.id.btn_upload_file:
                if (pdfUri != null){
                    uploadFile(pdfUri);
                } else {
                    Toast.makeText(UploadFileActivity.this,"please select a file...",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_simpan_file:
                // TODO: 11/29/2018 untuk versi 2 
//                Intent toCriteriaActivity = new Intent(this,OrderCriteriaActivity.class);
//                toCriteriaActivity.putExtra("listFile",uploadFileModels);
//                startActivity(toCriteriaActivity);
                break;
        }
    }

    private void selectPDF(){
        Intent filesIntent;
        filesIntent = new Intent(Intent.ACTION_GET_CONTENT);
        filesIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        filesIntent.addCategory(Intent.CATEGORY_OPENABLE);
        filesIntent.setType("*/*");  //use image/* for photos, etc.
        startActivityForResult(filesIntent, REQUEST_CODE_FOR_ON_ACTIVITY_RESULT);
    }

    private void uploadFile(Uri[] pdfUri){
        pb_upload_file.setVisibility(View.VISIBLE);

        for (int i = 0; i < pdfUri.length; i++){
            final String[] filename = new String[pdfUri.length];
                    filename[i]= System.currentTimeMillis()+"";
            final int indexFile = i;
            mStorageReference.child("Uploads").child(filename[i]).putFile(pdfUri[i])
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String url = taskSnapshot.getUploadSessionUri().toString();
                            mDatabaseReference.child(filename[indexFile]).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        pb_upload_file.setProgress(0);
                                        Toast.makeText(UploadFileActivity.this, "Upload file "+ indexFile +" success...!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(UploadFileActivity.this, "Upload file "+indexFile +" failed !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadFileActivity.this,"Failed to upload file...",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadFileActivity.this, "Uploading file "+ indexFile +" please wait..", Toast.LENGTH_LONG).show();
                    int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    pb_upload_file.setProgress(currentProgress);
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 86 && resultCode == RESULT_OK && data != null){

            UploadFileModel fileModel = new UploadFileModel();
            if (data.getClipData() != null){
                uploadMultipleFile(fileModel,data);
            } else {
               uploadSingleFile(fileModel,data);
            }
            adapterFile = new FileUploadAdapter(uploadFileModels,this);
            Toast.makeText(UploadFileActivity.this, "Total = "+String.valueOf(uploadFileModels.size()), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UploadFileActivity.this,"please select a file...",Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadSingleFile(UploadFileModel fileModel, Intent data){
        try {
            pdfUri = new Uri[1];
            fileModel = new UploadFileModel();
            Uri pdfUriSingle = data.getData();
            String namaFile = getFileName(data.getData());
            fileModel.setNamaFile(namaFile);

            ContentResolver cr = this.getContentResolver();
            String tipeFile = cr.getType(data.getData());
            fileModel.setTipeFile(tipeFile.substring(tipeFile.lastIndexOf("/")));

            fileModel.setUkuranFile(String.valueOf(new File(data.getData().getPath()).length()));
            uploadFileModels.add(fileModel);
            pdfUri[0] = pdfUriSingle;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void uploadMultipleFile(UploadFileModel fileModel, Intent data){
        pdfUri = new Uri[data.getClipData().getItemCount()];
        try{
            for(int i = 0; i < data.getClipData().getItemCount(); i++){
                fileModel = new UploadFileModel();
                String namaFile = getFileName(data.getClipData().getItemAt(i).getUri());
                fileModel.setNamaFile(namaFile);
                fileModel.setUkuranFile(String.valueOf(new File(data.getClipData().getItemAt(i).getUri().getPath()).length()));
                fileModel.setTipeFile(namaFile.substring(namaFile.lastIndexOf(".")));
                uploadFileModels.add(fileModel);
                pdfUri[i] = data.getClipData().getItemAt(i).getUri();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPDF();
        } else {
            Toast.makeText(UploadFileActivity.this,"please provide information...",Toast.LENGTH_SHORT).show();
        }
    }

    public String getFileName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1){
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
