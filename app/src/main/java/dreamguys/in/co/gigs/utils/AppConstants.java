package dreamguys.in.co.gigs.utils;

import java.util.ArrayList;
import java.util.List;

import dreamguys.in.co.gigs.Model.POSTDetailGig;
import dreamguys.in.co.gigs.Model.POSTMyActivity;

/**
 * Created by Prasad on 10/24/2017.
 */

public class AppConstants {


    public static final String LANGUAGE_SET = "LANGUAGE_SET";
    public static final String MY_LANGUAGE = "MY_LANGUAGE";
    public static final String CASH_AMOUNT = "CASH_AMOUNT";
    public static String BASEURL = "https://websoftquality.com/"; //update your API Base URL here
    public static String ApiURL = BASEURL + "api/";
    public static final String GUEST_TOKEN = "8338d6ff4f0878b222f312494c1621a9";
    public static String DOLLAR_SIGN = "£";
    public static final String COUNTRY_JSON = "COUNTRY_JSON";
    public static final String TIMEZONE_ID = "TIMEZONE_ID";
    public static final String IS_WELCOME_FIRST_TIME = "IS_WELCOME_FIRST_TIME";
    public static final String USER_NAME = "USER_NAME";
    public static final String TOKEN_ID = "TOKEN_ID";
    public static final String USER_IMAGE = "USER_IMAGE";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String PROFESSION = "PROFESSION";
    public static String BASE_URL;
    public static String CAT_ID = "CAT_ID";
    public static String GIGS_ID = "GIGS_ID";
    public static String SUB_ID = "SUB_ID";
    public static String GIGS_TITLE = "GIGS_TITLE";
    public static String CAT_NAME = "CAT_NAME";
    public static String SUB_CAT_ID = "SUB_CAT_ID";
    public static List<POSTMyActivity.My_purchase> My_Purchase_Array = new ArrayList<POSTMyActivity.My_purchase>();
    public static List<POSTMyActivity.My_sale> My_Sales_Array;
    public static List<POSTMyActivity.My_payment> My_Payment_Array;
    public static List<POSTDetailGig.Review> reviewList;
    public static String MY_PURARRAY_KEY = "MY_PURARRAY_KEY";
    public static String MY_SALEARRAY_KEY = "MY_SALEARRAY_KEY";
    public static String MY_PAYMENTARRAY_KEY = "MY_PAYMENTARRAY_KEY";
    public static String WALLET_BALANCE = "WALLET_BALANCE";
    public static final String NOTIFICATION_IDS = "NOTIFICATION_IDS";
    public static String SUPERFAST_CHARGES = "SUPERFAST_CHARGES";
    public static String SUPERFAST_DAYS = "SUPERFAST_DAYS";
    public static String SUPERFAST_DELIVERY_DESC = "SUPERFAST_DELIVERY_DESC";
    public static String passwordMatch = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])[^\\s]{8,15}$";
    public static String daysMatch = "^[0-29]+$";

    public static String cityMatch = "[a-zA-Z]+";
    public static String numberMatch = "^[0-9]+$";
    public static String PayEmail = "PayPalEmail";
    public static String PayPal_ApiKey = "PayPal_ApiKey";
    public static String PayPal_Value = "PayPal_Value";
    public static String PayPal_GatewayType = "PayPal_GatewayType";
    public static String stripe_apiKey = "stripe_apiKey";
    public static String stripeValue = "stripeValue";
    public static String stripeGatewayType = "stripeGatewayType";
    public static String CURRENCY_TYPE;
    public static String DeviceType = "Android";
    public static String GigUserID = "GigUserID";
    public static String PayPal_User_Email = "PayPal_User_Email";
    public static String SellGigsPrice = "SellGigsPrice";
    public static String SellExtraGigsPrice = "SellExtraGigsPrice";
    public static String stripe_Acc_name = "stripe_Acc_name";
    public static String stripe_Acc_num = "stripe_Acc_num";
    public static String stripe_Iban = "stripe_Iban";
    public static String stripe_bank_name = "stripe_bank_name";
    public static String stripe_bank_address = "stripe_bank_address";
    public static String stripe_sort_code = "stripe_sort_code";
    public static String stripe_routing_number = "stripe_routing_number";
    public static String stripe_account_ifsc = "stripe_account_ifsc";
    public static Integer InactiveStatusResponse = 201;
    public static String SellGigsPriceOption = "SellGigsPriceOption";
    public static String TOKEN = "token";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String INVALID_RESPONSE_CODE = "498";
    public static String public_key = "public_key";
    public static String secret_key = "secret_key";
    public static String localeName = "en";

    public static String LOGIN = "login";
    public static String REGISTER = "register";
    public static String FORGOTPASSWORD = "forgot_password";
    public static String COMMONSTRINGS = "common_strings";
    public static String CHANGEPASSWORD = "change_password";
    public static String DETAILGIGS = "detail_gigs";
    public static String STRIPEPAYMENT = "stripe_payment";
    public static String SEARCHGIGS = "search_gigs";
    public static final String HOMESCREEN = "HOMESCREEN";
    public static final String INTROSCREEN = "INTROSCREEN";
    public static final String MYACTIVITY = "MYACTIVITY";
    public static final String NAVSCREEN = "NAVSCREEN";
    public static final String SELLGIGS = "SELLGIGS";
    public static final String SETTINGS = "SETTINGS";
    public static final String TABBAR = "TABBAR";
    public static final String CARTSCREEN = "CARTSCREEN";


    public static String UPDATEPROFILE = "update_profile";
    public static String CREATEGIGS = "create_gigs";
    public static String CHAT = "chat";
    public static String GIGSCART = "cart";
    public static String Language = "Language";
    public static int isFromWhichCard = -1;

}
