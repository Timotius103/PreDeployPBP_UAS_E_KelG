package platformpbp.uajy.quickresto.dabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import platformpbp.uajy.quickresto.model.Ratting;

@Dao
public interface RattingDAO {
    @Query("SELECT * FROM ratting")
    List<Ratting> getAll();

    @Query("SELECT * FROM ratting WHERE mailRattingPengguna LIKE :cari")
    List<Ratting> getCari(String cari);

    @Insert
    void insert(Ratting ratting);

    @Update
    void update(Ratting ratting);

    @Delete
    void delete(Ratting ratting);
}
