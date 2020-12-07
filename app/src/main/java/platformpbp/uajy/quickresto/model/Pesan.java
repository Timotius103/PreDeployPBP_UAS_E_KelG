package platformpbp.uajy.quickresto.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Pesan implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "emailpengguna")
    public String mailpengguna;

    @ColumnInfo(name = "alamat")
    public String alamat;

    @ColumnInfo(name = "namaResto")
    public String namaResto;

    @ColumnInfo(name = "jmlhOrang")
    public String jmlhOrang;

    @ColumnInfo(name = "tanggal")
    public String tanggal;

    @ColumnInfo(name = "waktu")
    public String waktu;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMailpengguna() {
        return mailpengguna;
    }

    public void setMailpengguna(String mailpengguna) {
        this.mailpengguna = mailpengguna;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaResto() {
        return namaResto;
    }

    public void setNamaResto(String namaResto) {
        this.namaResto = namaResto;
    }

    public String getJmlhOrang() {
        return jmlhOrang;
    }

    public void setJmlhOrang(String jmlhOrang) {
        this.jmlhOrang = jmlhOrang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
