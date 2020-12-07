//package platformpbp.uajy.quickresto.UnitTesting;
//
//import android.content.Context;
//import android.content.Intent;
//
//import platformpbp.uajy.quickresto.MainActivity;
//import platformpbp.uajy.quickresto.dabase.ReservationDAO;
//import platformpbp.uajy.quickresto.dabase.UserDAO;
//
//public class ActivityUtil {
//    private Context context;
//    public ActivityUtil(Context context) {
//        this.context = context;
//    }
//    public void startMainActivity() {
//        context.startActivity(new Intent(context, MainActivity.class));
//    }
//    public void startUserProfile(UserDAO user) {
//        Intent i = new Intent(context, UserProfileActivity.class);
//        i.putExtra("id",user.getId());
//        i.putExtra("name",user.getNama());
//        i.putExtra("nim",user.getNim());
//        i.putExtra("prodi",user.getProdi());
//        i.putExtra("fakultas",user.getFakultas());
//        i.putExtra("jenis_kelamin",user.getJenis_kelamin());
//        context.startActivity(i);
//    }
//}
