package platformpbp.uajy.quickresto.UnitTesting;

import platformpbp.uajy.quickresto.dabase.UserDAO;

public interface ProfileView {
    String getName();
    void showNameError(String message);
    String getPhone();
    void showPhoneError(String message);
    void startMainActivity();
    void startUserProfileActivity(UserDAO user);
    void showProfileError(String message);
    void showErrorResponse(String message);
}
