package papb.learn.fauzan.printin.model;

import android.net.Uri;

import java.io.Serializable;

public class UploadFileModel implements Serializable {
    private String namaFile,tipeFile,ukuranFile;

    Uri uriFile;

    public UploadFileModel() {
    }

    public UploadFileModel(String namaFile, String tipeFile, String ukuranFile) {
        this.namaFile = namaFile;
        this.tipeFile = tipeFile;
        this.ukuranFile = ukuranFile;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getTipeFile() {
        return tipeFile;
    }

    public void setTipeFile(String tipeFile) {
        this.tipeFile = tipeFile;
    }

    public String getUkuranFile() {
        return ukuranFile;
    }

    public void setUkuranFile(String ukuranFile) {
        this.ukuranFile = ukuranFile;
    }

    public Uri getUriFile() {
        return uriFile;
    }

    public void setUriFile(Uri uriFile) {
        this.uriFile = uriFile;
    }
}
