package platformpbp.uajy.quickresto.dabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import platformpbp.uajy.quickresto.model.Pesan;

@Dao
public interface ReservationDAO {
    @Query("SELECT * FROM pesan")
    List<Pesan> getAll();

    @Query("SELECT * FROM pesan WHERE emailpengguna LIKE :cari")
    List<Pesan> getCari(String cari);

    @Insert
    void insert(Pesan pesan);

    @Update
    void update(Pesan pesan);

    @Delete
    void delete(Pesan pesan);
}
