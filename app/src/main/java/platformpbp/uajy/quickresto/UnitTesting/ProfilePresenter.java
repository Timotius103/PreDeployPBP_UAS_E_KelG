//package platformpbp.uajy.quickresto.UnitTesting;
//
//import platformpbp.uajy.quickresto.dabase.UserDAO;
//
//public class ProfilePresenter {
//    private ProfileView view;
//    private ProfileService service;
//    private ProfileCallback callback;
//    public ProfilePresenter(ProfileView view, ProfileService service) {
//        this.view = view;
//        this.service = service;
//    }
//    public void onLoginClicked() {
//        if (view.getName().isEmpty()) {
//            view.showNameError("Nama tidak boleh kosong");
//            return;
//        }else if (view.getPhone().isEmpty()) {
//            view.showPhoneError("Password tidak boleh kosong");
//            return;
//        }else{
//            service.profile(view, view.getName(), view.getPhone(), new
//                    ProfileCallback() {
//                        @Override
//                        public void onSuccess(boolean value, UserDAO user) {
//                            if(user.getName().equalsIgnoreCase("admin")){
//                                view.startMainActivity();
//                            }else{
//                                view.startUserProfileActivity(user);
//                            }
//                        }
//                        @Override
//                        public void onError() {
//                        }
//                    });
//            return;
//        }
//    }
//}
