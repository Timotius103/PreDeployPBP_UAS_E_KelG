//package platformpbp.uajy.quickresto.UnitTesting;
//
//public class ProfilePresenter {
//    private LoginView view;
//    private LoginService service;
//    private LoginCallback callback;
//    public LoginPresenter(LoginView view, LoginService service) {
//        this.view = view;
//        this.service = service;
//    }
//    public void onLoginClicked() {
//        if (view.getNim().isEmpty()) {
//            view.showNimError("Nim tidak boleh kosong");
//            return;
//        }else if (view.getPassword().isEmpty()) {
//            view.showPasswordError("Password tidak boleh kosong");
//            return;
//        }else{
//            service.login(view, view.getNim(), view.getPassword(), new
//                    LoginCallback() {
//                        @Override
//                        public void onSuccess(boolean value, UserDAO user) {
//                            if(user.getNama().equalsIgnoreCase("admin")){
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
