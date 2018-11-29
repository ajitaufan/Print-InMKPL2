package papb.learn.fauzan.printin.model;

import java.util.Date;

public class OrderModel {
    private String dokumen;
    private String tanggal;

    public OrderModel(String dokumen, String tanggal) {
        this.dokumen = dokumen;
        this.tanggal = tanggal;
    }

    public String getDokumen() {
        return dokumen;
    }

    public void setDokumen(String dokumen) {
        this.dokumen = dokumen;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
