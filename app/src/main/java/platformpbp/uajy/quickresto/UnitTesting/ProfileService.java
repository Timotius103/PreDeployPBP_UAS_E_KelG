//package platformpbp.uajy.quickresto.UnitTesting;
//
//
//import platformpbp.uajy.quickresto.dabase.UserDAO;
//
//public class ProfileService {
//    public void profile(final ProfileView view, String nim, String password,
//                      final ProfileCallback callback){
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//        Call<UserResponse> userDAOCall = apiService.ProfileEdit(nim, password);
//        userDAOCall.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call,
//                                   Response<UserResponse> response) {
//                if(response.body().getMessage().equalsIgnoreCase("berhasil edit" )){
//                    callback.onSuccess(true,
//                            response.body().getUsers().get(0));
//                }
//                else{
//                    callback.onError();
//                    view.showProfileError(response.body().getMessage());
//                }
//            }
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                view.showErrorResponse(t.getMessage());
//                callback.onError();
//            }
//        });
//    }
//    public Boolean getValid(final ProfileView view, String name, String
//            phone){
//        final Boolean[] bool = new Boolean[1];
//        profile(view, name, phone, new ProfileCallback() {
//
//            @Override
//            public void onSuccess(boolean value, UserDAO user) {
//                bool[0] = true;
//            }
//            @Override
//            public void onError() {
//                bool[0] = false;
//            }
//        });
//        return bool[0];
//    }
//}
