package platformpbp.uajy.quickresto.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistAPI {
    public static final String ROOT_URL = "http://reviewing.website/";
    public static final String ROOT_API   = ROOT_URL+ "api/";
    public static final String URL_REGIST  = ROOT_API+"register";
    public static final String URL_UPDATEUSER =  ROOT_API+"profile/";
}
