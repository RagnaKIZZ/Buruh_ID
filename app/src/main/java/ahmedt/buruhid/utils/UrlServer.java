package ahmedt.buruhid.utils;

public class UrlServer {

    public static String BASE_URL = "http://10.10.10.158/BuruhID_API/public/";

    public static String URL_REGISTER   = BASE_URL+"user/register_customer";
    public static String URL_LOGIN      = BASE_URL+"user/login_customer";
    public static String URL_LOGOUT     = BASE_URL+"user/logout_customer";
    public static String URL_FOTO       = BASE_URL+"img_customer/";
    public static String URL_PROMO      = BASE_URL+"user/get_promo";

    public static String URL_GET_ADDRESS = "https://x.rajaapi.com/MeP7c5nearGMAgyCJgfCEwLxlsZk72e1v4uCHIDShk72c61wr1DlMFyjYV/m/wilayah/";
    public static String URL_GET_KAB = URL_GET_ADDRESS+"kabupaten?idpropinsi=";
    public static String URL_GET_KAC = URL_GET_ADDRESS+"kecamatan?idkabupaten=";


}
