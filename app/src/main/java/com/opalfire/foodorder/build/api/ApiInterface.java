package com.opalfire.foodorder.build.api;

import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.AddMoney;
import com.opalfire.foodorder.models.Address;
import com.opalfire.foodorder.models.Card;
import com.opalfire.foodorder.models.ChangePassword;
import com.opalfire.foodorder.models.ClearCart;
import com.opalfire.foodorder.models.Cuisine;
import com.opalfire.foodorder.models.DisputeMessage;
import com.opalfire.foodorder.models.Favorite;
import com.opalfire.foodorder.models.FavoriteList;
import com.opalfire.foodorder.models.ForgotPassword;
import com.opalfire.foodorder.models.LoginModel;
import com.opalfire.foodorder.models.Message;
import com.opalfire.foodorder.models.Order;
import com.opalfire.foodorder.models.Otp;
import com.opalfire.foodorder.models.PromotionResponse;
import com.opalfire.foodorder.models.Promotions;
import com.opalfire.foodorder.models.RegisterModel;
import com.opalfire.foodorder.models.ResetPassword;
import com.opalfire.foodorder.models.RestaurantsData;
import com.opalfire.foodorder.models.Search;
import com.opalfire.foodorder.models.ShopDetail;
import com.opalfire.foodorder.models.User;
import com.opalfire.foodorder.models.WalletHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("api/user/card")
    Call<Message> addCard(@Field("stripe_token") String str);

    @FormUrlEncoded
    @POST("api/user/add/money")
    Call<AddMoney> addMoney(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/wallet/promocode")
    Call<PromotionResponse> applyWalletPromoCode(@Field("promocode_id") String str);

    @DELETE("api/user/order/{id}")
    Call<Order> cancelOrder(@Path("id") int i, @Query("reason") String str);

    @FormUrlEncoded
    @POST("api/user/profile/password")
    Call<ChangePassword> changePassword(@FieldMap HashMap<String, String> hashMap);

    @GET("api/user/clear/cart")
    Call<ClearCart> clearCart();

    @DELETE("api/user/address/{id}")
    Call<Message> deleteAddress(@Path("id") int i);

    @DELETE("api/user/card/{id}")
    Call<Message> deleteCard(@Path("id") int i);

    @DELETE("api/user/favorite/{id}")
    Call<Favorite> deleteFavorite(@Path("id") int i);

    @FormUrlEncoded
    @POST("api/user/favorite")
    Call<Favorite> doFavorite(@Field("shop_id") int i);

    @FormUrlEncoded
    @POST("api/user/forgot/password")
    Call<ForgotPassword> forgotPassword(@Field("phone") String str);

    @GET("api/user/address")
    Call<List<Address>> getAddresses();

    @GET("api/user/card")
    Call<List<Card>> getCardList();

    @GET("api/user/categories")
    Call<ShopDetail> getCategories(@QueryMap HashMap<String, String> hashMap);

    @GET("api/user/disputehelp")
    Call<List<DisputeMessage>> getDisputeList();

    @GET("api/user/favorite")
    Call<FavoriteList> getFavoriteList();

    @GET("api/user/notification")
    Call<FavoriteList> getNotification();

    @GET("api/user/ongoing/order")
    Call<List<Order>> getOngoingOrders();

    @GET("api/user/order/{id}")
    Call<Order> getParticularOrders(@Path("id") int i);

    @GET("api/user/order")
    Call<List<Order>> getPastOders();

    @GET("api/user/profile")
    Call<User> getProfile(@QueryMap HashMap<String, String> hashMap);

    @GET("json?")
    Call<ResponseBody> getResponse(@Query("latlng") String str, @Query("key") String str2);

    @GET("api/user/search")
    Call<Search> getSearch(@QueryMap HashMap<String, String> hashMap);

    @GET("api/user/cart")
    Call<AddCart> getViewCart();

    @GET("api/user/wallet")
    Call<List<WalletHistory>> getWalletHistory();

    @GET("api/user/wallet/promocode")
    Call<List<Promotions>> getWalletPromoCode();

    @GET("api/user/cuisines")
    Call<List<Cuisine>> getcuCuisineCall();

    @GET("api/user/shops")
    Call<RestaurantsData> getshops(@QueryMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/cart")
    Call<AddCart> postAddCart(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/order")
    Call<Order> postCheckout(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/dispute")
    Call<Order> postDispute(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("oauth/token")
    Call<LoginModel> postLogin(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/otp")
    Call<Otp> postOtp(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/register")
    Call<RegisterModel> postRegister(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/social/login")
    Call<LoginModel> postSocialLogin(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/rating")
    Call<Message> rate(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/reorder")
    Call<AddCart> reOrder(@FieldMap HashMap<String, String> hashMap);

    @FormUrlEncoded
    @POST("api/user/reset/password")
    Call<ResetPassword> resetPassword(@FieldMap HashMap<String, String> hashMap);

    @POST("api/user/address")
    Call<Address> saveAddress(@Body Address address);

    @PATCH("api/user/address/{id}")
    Call<Address> updateAddress(@Path("id") int i, @Body Address address);

    @POST("api/user/profile")
    @Multipart
    Call<User> updateProfileWithImage(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part part);
}
