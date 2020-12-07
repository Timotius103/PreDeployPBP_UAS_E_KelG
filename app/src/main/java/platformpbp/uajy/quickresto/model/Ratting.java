package platformpbp.uajy.quickresto.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Ratting implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "mailRattingPengguna")
    public String mailRattingPengguna;

    @ColumnInfo(name = "ratting")
    public float ratting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMailRattingPengguna() {
        return mailRattingPengguna;
    }

    public void setMailRattingPengguna(String mailRattingPengguna) {
        this.mailRattingPengguna = mailRattingPengguna;
    }

    public float getRatting() {
        return ratting;
    }

    public void setRatting(float ratting) {
        this.ratting = ratting;
    }
}
