package ahmedt.buruhid.utils;

public class UrlServer {

    public static String BASE_URL = "https://buruhidd.000webhostapp.com/";
    public static String URL_COVID = "https://indonesia-covid-19.mathdro.id/api";

    public static String URL_REGISTER               = BASE_URL+"user/register_customer";
    public static String URL_LOGIN                  = BASE_URL+"user/login_customer";
    public static String URL_LOGOUT                 = BASE_URL+"user/logout_customer";
    public static String URL_FOTO                   = BASE_URL+"img_customer/";
    public static String URL_FOTO_PAYMENT           = BASE_URL+"img_payment/";
    public static String URL_PROMO                  = BASE_URL+"user/get_promo";
    public static String URL_MAKE_ORDER             = BASE_URL+"user/make_order";
    public static String URL_LIST_ORDER             = BASE_URL+"user/list_order";
    public static String URL_LIST_ORDER_HISTORY     = BASE_URL+"user/list_order_history";
    public static String URL_LIST_PAYMENT           = BASE_URL+"user/list_payment";
    public static String URL_LIST_PAYMENT_HISTORY   = BASE_URL+"user/list_payment_history";
    public static String URL_PRICE                  = BASE_URL+"user/getCurrentPrice";
    public static String URL_CHANGE_NAME            = BASE_URL+"user/update_name";
    public static String URL_CHANGE_PASSWORD        = BASE_URL+"user/update_password";
    public static String URL_CHANGE_EMAIL           = BASE_URL+"user/update_email";
    public static String URL_CHANGE_TELEPON         = BASE_URL+"user/update_telepon";
    public static String URL_CHANGE_FOTO            = BASE_URL+"user/update_foto";
    public static String URL_DELETE_FOTO            = BASE_URL+"user/hapus_foto";
    public static String URL_UPDATE_FIREBASE        = BASE_URL+"user/update_firebase_token";
    public static String URL_CANCEL_ORDER           = BASE_URL+"user/cancel_order";
    public static String URL_FINISH_ORDER           = BASE_URL+"user/finish_order";
    public static String URL_GIVE_RATING            = BASE_URL+"user/give_rating";
    public static String URL_GIVE_SARAN            = BASE_URL+"user/give_apprespon";
    public static String URL_PAY_ORDER              = BASE_URL+"user/bayar_order";
    public static String URL_COUNT                  = BASE_URL+"user/getCountPromoAndNotif";
    public static String URL_NOTIF                  = BASE_URL+"user/getNotif";
    public static String URL_LIST_PROMO             = BASE_URL+"user/getListPromo";


    public static String URL_GET_ADDRESS    = "https://x.rajaapi.com/MeP7c5ne";
    public static String URL_GET_KAB        = "/m/wilayah/kabupaten?idpropinsi=";
    public static String URL_GET_KAC        = "/m/wilayah/kecamatan?idkabupaten=";
    public static String URL_GET_VIL        = "/m/wilayah/kelurahan?idkecamatan=";

    //tukang
    public static String URL_FOTO_TUKANG        = BASE_URL+"img_tukang/";
    public static String URL_SELECT_WORKER      = BASE_URL+"user/select_worker";

    public static String URL_GET_UNICODE = "https://x.rajaapi.com/poe";


}
