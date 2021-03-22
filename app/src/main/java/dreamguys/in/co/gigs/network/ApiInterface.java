package dreamguys.in.co.gigs.network;

import java.util.HashMap;
import java.util.List;

import dreamguys.in.co.gigs.Model.GETAllGigs;
import dreamguys.in.co.gigs.Model.GETCategory;
import dreamguys.in.co.gigs.Model.GETCountry;
import dreamguys.in.co.gigs.Model.GETFooterInformation;
import dreamguys.in.co.gigs.Model.GETFooterMenu;
import dreamguys.in.co.gigs.Model.GETHomeGigs;
import dreamguys.in.co.gigs.Model.GETLanguageList;
import dreamguys.in.co.gigs.Model.GETLogoutResponse;
import dreamguys.in.co.gigs.Model.GETMyGigs;
import dreamguys.in.co.gigs.Model.GETPaymentGatewayKeys;
import dreamguys.in.co.gigs.Model.GETPriceDetails;
import dreamguys.in.co.gigs.Model.GETProfession;
import dreamguys.in.co.gigs.Model.GETState;
import dreamguys.in.co.gigs.Model.GETStripeConfig;
import dreamguys.in.co.gigs.Model.POSTSubscriptionModel;
import dreamguys.in.co.gigs.Model.GETTermsAndConditions;
import dreamguys.in.co.gigs.Model.POSTAcceptBuyRequest;
import dreamguys.in.co.gigs.Model.POSTAcceptSellerDeclineRequest;
import dreamguys.in.co.gigs.Model.POSTAddFav;
import dreamguys.in.co.gigs.Model.POSTBuyNow;
import dreamguys.in.co.gigs.Model.POSTCancelGigs;
import dreamguys.in.co.gigs.Model.POSTChangePassword;
import dreamguys.in.co.gigs.Model.POSTChatHistory;
import dreamguys.in.co.gigs.Model.POSTCreategigs;
import dreamguys.in.co.gigs.Model.POSTDetailGig;
import dreamguys.in.co.gigs.Model.POSTEditGigs;
import dreamguys.in.co.gigs.Model.POSTFavGigs;
import dreamguys.in.co.gigs.Model.POSTForgotPassword;
import dreamguys.in.co.gigs.Model.POSTGigsReview;
import dreamguys.in.co.gigs.Model.POSTLanguageModel;
import dreamguys.in.co.gigs.Model.POSTLastVisitedGigs;
import dreamguys.in.co.gigs.Model.POSTLeaveFeedback;
import dreamguys.in.co.gigs.Model.POSTLogin;
import dreamguys.in.co.gigs.Model.POSTMessages;
import dreamguys.in.co.gigs.Model.POSTMyActivity;
import dreamguys.in.co.gigs.Model.POSTPaymentSuccess;
import dreamguys.in.co.gigs.Model.POSTPaypalSettings;
import dreamguys.in.co.gigs.Model.POSTPurchaseCompletedAcceptStatus;
import dreamguys.in.co.gigs.Model.POSTPurchaseSeeFedBck;
import dreamguys.in.co.gigs.Model.POSTRegister;
import dreamguys.in.co.gigs.Model.POSTRejectResponse;
import dreamguys.in.co.gigs.Model.POSTRemoveFav;
import dreamguys.in.co.gigs.Model.POSTSearchGigs;
import dreamguys.in.co.gigs.Model.POSTSellerReviews;
import dreamguys.in.co.gigs.Model.POSTSubCategory;
import dreamguys.in.co.gigs.Model.POSTSubscriptionSuccess;
import dreamguys.in.co.gigs.Model.POSTUpdateProfile;
import dreamguys.in.co.gigs.Model.POSTUserChat;
import dreamguys.in.co.gigs.Model.POSTViewProfile;
import dreamguys.in.co.gigs.Model.POSTVisitGig;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("gigs/messages")
    Call<POSTMessages> postmessages(@Field("page") String page, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("user/login")
    Call<POSTLogin> postLogin(@FieldMap HashMap<String, String> loginData, @Header("token") String token, @Header("language") String Language);

    @GET("gigs")
    Call<GETHomeGigs> getHomeGigs(@Header("token") String user_id, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/categories")
    Call<GETAllGigs> getUserGigs(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/gigs_details")
    Call<POSTDetailGig> getDetailGigs(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/last_visit")
    Call<POSTVisitGig> postVisitedGigs(@FieldMap HashMap<String, String> visitedGigs, @Header("token") String token, @Header("language") String Language);

    @GET("user/country")
    Call<List<GETCountry>> getCountry(@Header("token") String token, @Header("language") String Language);

    @GET("user/state/{stateid}")
    Call<List<GETState>> getState(@Path(value = "stateid", encoded = true) String stateid, @Header("token") String token, @Header("language") String Language);

    @Multipart
    @POST("user/registration")
    Call<POSTRegister> postRegister(@Part("email") RequestBody email,
                                    @Part("username") RequestBody username,
                                    @Part("password") RequestBody password,
                                    @Part("state") RequestBody state,
                                    @Part("country") RequestBody country,
                                    @Part("fullname") RequestBody fullname,
                                    @Part("user_timezone") RequestBody user_timezone,
                                    @Part MultipartBody.Part image,
                                    @Header("token") String token, @Header("language") String Language);


    @FormUrlEncoded
    @POST("user/forgot_password")
    Call<POSTForgotPassword> postForgot(@FieldMap HashMap<String, String> forgotPassword, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("user/change_password")
    Call<POSTChangePassword> postChangePassword(@FieldMap HashMap<String, String> changePassword, @Header("token") String token, @Header("language") String Language);

    @GET("user/profession")
    Call<List<GETProfession>> getProfession(@Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/favourites_gigs")
    Call<POSTFavGigs> getFavLists(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("user/paypal_setting")
    Call<POSTPaypalSettings> postPaypalSettings(@FieldMap HashMap<String, String> paypalsettings, @Header("token") String token, @Header("language") String Language);

    @Multipart
    @POST("user/profile")
    Call<POSTUpdateProfile> postUpdateProfile(@Part("user_contact") RequestBody user_contact,
                                              @Part("user_zip") RequestBody user_zip,
                                              @Part("user_city") RequestBody user_city,
                                              @Part("user_addr") RequestBody user_addr,
                                              @Part("user_desc") RequestBody user_desc,
                                              @Part("country_id") RequestBody country_id,
                                              @Part("state_id") RequestBody state_id,
                                              @Part("profession") RequestBody profession,
                                              @Part("user_name") RequestBody user_name,
                                              @Part("language_tags") RequestBody language_tags,
                                              @Part MultipartBody.Part image,
                                              @Header("token") String token, @Header("language") String Language);

    @GET("gigs/categories")
    Call<GETCategory> getCategories(@Header("token") String token, @Header("language") String Language);

    @GET("gigs/categories/{type}")
    Call<GETCategory> getCategoriesAll(@Path(value = "type", encoded = true) String type, @Header("token") String token, @Header("language") String Language);

    @GET("gigs/payment_gateways")
    Call<GETPaymentGatewayKeys> getPaymentKeys(@Header("token") String token, @Header("language") String Language);

    @GET("gigs/footer")
    Call<GETFooterInformation> getInformation(@Header("token") String token, @Header("language") String Language);

    @GET("gigs/footer_menu")
    Call<GETFooterMenu> getHelpInformation(@Header("token") String token, @Header("language") String Language);


    @GET("gigs/terms")
    Call<GETTermsAndConditions> getTermsandConditions(@Header("token") String token, @Header("language") String Language);

    @GET("gigs/gigs_list/{user_id}")
    Call<GETAllGigs> getAllGigs(@Path(value = "user_id", encoded = true) String stateid, @Header("token") String token, @Header("language") String Language);


    @GET("gigs/my_gigs/{user_id}")
    Call<GETMyGigs> getMyGigs(@Path(value = "user_id", encoded = true) String userid, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/my_gigs")
    Call<GETMyGigs> postMyGigs(@FieldMap HashMap<String, String> sellerReviews, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/seller_reviews")
    Call<POSTSellerReviews> getSellerReviews(@FieldMap HashMap<String, String> sellerReviews, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/search_gig")
    Call<POSTSearchGigs> postSearchGigs(@FieldMap HashMap<String, String> searchGigs, @Header("token") String token, @Header("language") String Language);


    @FormUrlEncoded
    @POST("gigs/seller_buyer_review")
    Call<POSTGigsReview> getGigsReviews(@FieldMap HashMap<String, String> sellerReviews, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("user/profile_details")
    Call<POSTViewProfile> postViewProfile(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/categories")
    Call<POSTSubCategory> getSubCategory(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);


    @FormUrlEncoded
    @POST("gigs/add_favourites")
    Call<POSTAddFav> addFav(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/remove_favourites")
    Call<POSTRemoveFav> removeFav(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);


    /*@Multipart
    @POST("gigs/create_gigs")
    Call<POSTCreategigs> createGigs(@FieldMap HashMap<String, String> updateProfile);*/
    @Multipart
    @POST("gigs/create_gigs")
    Call<POSTCreategigs> createGigs(/*@Part("user_id") RequestBody user_id,*/
                                    @Part("title") RequestBody title,
                                    @Part("delivering_time") RequestBody delivering_time,
                                    @Part("gig_price") RequestBody gig_price,
                                    @Part("gig_tags") RequestBody gig_tags,
                                    @Part("gig_details") RequestBody gig_details,
                                    @Part("super_fast_delivery") RequestBody super_fast_delivery,
                                    @Part("super_fast_delivery_desc") RequestBody super_fast_delivery_desc,
                                    @Part("super_fast_delivery_date") RequestBody super_fast_delivery_date,
                                    @Part("super_fast_charges") RequestBody super_fast_charges,
                                    @Part("requirements") RequestBody requirements,
                                    @Part("work_option") RequestBody work_option,
                                    @Part("terms_conditions") RequestBody terms_conditions,
                                    @Part("extra_gigs") RequestBody extra_gigs,
                                    @Part("time_zone") RequestBody time_zone,
                                    @Part("category_id") RequestBody category_id,
                                    @Part("cost_type") RequestBody price_option,
                                    @Part MultipartBody.Part image,
                                    @Header("token") String token, @Header("language") String Language
    );

    @Multipart
    @POST("gigs/update_gigs")
    Call<POSTCreategigs> updateGigs(@Part("gig_id") RequestBody gig_id,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("title") RequestBody title,
                                    @Part("delivering_time") RequestBody delivering_time,
                                    @Part("gig_price") RequestBody gig_price,
                                    @Part("gig_tags") RequestBody gig_tags,
                                    @Part("gig_details") RequestBody gig_details,
                                    @Part("super_fast_delivery") RequestBody super_fast_delivery,
                                    @Part("super_fast_delivery_desc") RequestBody super_fast_delivery_desc,
                                    @Part("super_fast_delivery_date") RequestBody super_fast_delivery_date,
                                    @Part("super_fast_charges") RequestBody super_fast_charges,
                                    @Part("requirements") RequestBody requirements,
                                    @Part("work_option") RequestBody work_option,
                                    @Part("terms_conditions") RequestBody terms_conditions,
                                    @Part("extra_gigs") RequestBody extra_gigs,
                                    @Part("category_id") RequestBody category_id,
                                    @Part MultipartBody.Part image, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/last_visited_gigs")
    Call<POSTLastVisitedGigs> getLastVisitedGigs(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);


    @FormUrlEncoded
    @POST("gigs/edit_gigs")
    Call<POSTEditGigs> editGigs(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);


    @POST("gigs/my_gig_activity")
    Call<POSTMyActivity> getMyActivity(@Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/buyer_cancel")
    Call<POSTCancelGigs> purchaseCancelGigs(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/leave_feedback")
    Call<POSTLeaveFeedback> postLeaveFeedback(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/seefeedback")
    Call<POSTPurchaseSeeFedBck> postpurchsseFedBck(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/accept_buyer_request")
    Call<POSTAcceptBuyRequest> postacceptbuyrequest(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/sale_order_status")
    Call<POSTAcceptBuyRequest> postorderStatus(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/withdram_payment_request")
    Call<POSTAcceptBuyRequest> postWithdrawRequest(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);


    @FormUrlEncoded
    @POST("gigs/chat_details")
    Call<POSTChatHistory> getChatHistory(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/buyer_chat")
    Call<POSTAcceptBuyRequest> postMessage(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/user_chat")
    Call<POSTUserChat> postUserChat(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/save_device_id")
    Call<POSTAcceptBuyRequest> postPushDetails(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/buy_now")
    Call<POSTBuyNow> postBuyingGigsdetails(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/paypal_success")
    Call<POSTPaymentSuccess> postPaymentSuccess(@FieldMap HashMap<String, String> updateProfile, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/stripe_account_details")
    Call<POSTPaymentSuccess> postStripeCancel(@FieldMap HashMap<String, String> cancelPayment, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/stripe_account_details")
    Call<POSTPaymentSuccess> postRegisterStripeDetails(@FieldMap HashMap<String, String> cancelPayment, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/change_gigs_status")
    Call<POSTPurchaseCompletedAcceptStatus> postPurchaseCompletedAcceptStatus(@FieldMap HashMap<String, String> cancelPayment, @Header("token") String token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/accept_seller_decline_request")
    Call<POSTAcceptSellerDeclineRequest> postAcceptSellerDeclineRequest(@FieldMap HashMap<String, String> cancelPayment, @Header("token") String token, @Header("language") String Language);

    @GET("gigs/price_details")
    Call<GETPriceDetails> getPriceDetails(@Header("token") String token, @Header("language") String Language);

    @GET("user/logout")
    Call<GETLogoutResponse> getLogoutResponse(@Header("token") String Token, @Header("language") String Language);

    @GET("gigs/language_list")
    Call<GETLanguageList> getLanguageList();

    @FormUrlEncoded
    @POST("gigs/rejected_orders")
    Call<POSTRejectResponse> postRejectResponse(@FieldMap HashMap<String, String> rejectOrder, @Header("token") String token, @Header("language") String Language);

    @GET("gigs/stripe_private_key")
    Call<GETStripeConfig> getStripeConfiDetails(@Header("token") String Token, @Header("language") String Language);

    @FormUrlEncoded
    @POST("gigs/language")
    Call<POSTLanguageModel> languageData(@Field("language") String language);

    @FormUrlEncoded
    @POST("user/check_sell_Service")
    Call<POSTSubscriptionModel> postSubscription(@Header("token") String token, @Field("timezone") String timezone);

    @FormUrlEncoded
    @POST("gigs/stripe_subscription_success")
    Call<POSTSubscriptionSuccess> postSubscriptionPayment(@FieldMap HashMap<String, String> subscriptionData, @Header("token") String token, @Header("language") String Language);
}