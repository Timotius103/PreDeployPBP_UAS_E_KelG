package platformpbp.uajy.quickresto.dabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import platformpbp.uajy.quickresto.model.Pesan;
import platformpbp.uajy.quickresto.model.Ratting;
import platformpbp.uajy.quickresto.model.User;
@Database(entities = {User.class, Pesan.class, Ratting.class}, version = 1)
public abstract class AppDatabaseClient extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ReservationDAO reservDAO();
    public abstract RattingDAO rateDAO();
}



