package ahmedt.buruhid.utils;

import com.pixplicity.easyprefs.library.Prefs;

public class UrlServer {

    public static String BASE_URL = "http://10.10.10.158/BuruhID_API/public/";

    public static String URL_REGISTER   = BASE_URL+"user/register_customer";
    public static String URL_LOGIN      = BASE_URL+"user/login_customer";
    public static String URL_LOGOUT     = BASE_URL+"user/logout_customer";
    public static String URL_FOTO       = BASE_URL+"img_customer/";
    public static String URL_PROMO      = BASE_URL+"user/get_promo";
    public static String URL_MAKE_ORDER = BASE_URL+"user/make_order";
    public static String URL_LIST_ORDER = BASE_URL+"user/list_order";
    public static String URL_LIST_ORDER_HISTORY = BASE_URL+"user/list_order_history";
    public static String URL_PRICE       = BASE_URL+"user/getCurrentPrice";

    public static String URL_GET_ADDRESS = "https://x.rajaapi.com/MeP7c5ne";
    public static String URL_GET_KAB = "/m/wilayah/kabupaten?idpropinsi=";
    public static String URL_GET_KAC = "/m/wilayah/kecamatan?idkabupaten=";
    public static String URL_GET_VIL = "/m/wilayah/kelurahan?idkecamatan=";

    //tukang
    public static String URL_FOTO_TUKANG        = BASE_URL+"img_tukang/";
    public static String URL_SELECT_WORKER      = BASE_URL+"user/select_worker";

    public static String URL_GET_UNICODE = "https://x.rajaapi.com/poe";


}
