package platformpbp.uajy.quickresto.UnitTesting;

import platformpbp.uajy.quickresto.dabase.UserDAO;

public interface ProfileCallback {
    void onSuccess(boolean value, UserDAO user);
    void onError();
}
