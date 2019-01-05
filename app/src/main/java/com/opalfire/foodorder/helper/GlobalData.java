package com.opalfire.foodorder.helper;

import android.location.Location;
import android.view.View;

import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.Address;
import com.opalfire.foodorder.models.AddressList;
import com.opalfire.foodorder.models.Card;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.models.CartAddon;
import com.opalfire.foodorder.models.Category;
import com.opalfire.foodorder.models.Cuisine;
import com.opalfire.foodorder.models.DisputeMessage;
import com.opalfire.foodorder.models.Order;
import com.opalfire.foodorder.models.Otp;
import com.opalfire.foodorder.models.Product;
import com.opalfire.foodorder.models.Shop;
import com.opalfire.foodorder.models.User;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class GlobalData {
    private static final GlobalData ourInstance = new GlobalData();
    public static Location CURRENT_LOCATION = null;
    public static List<String> ORDER_STATUS = Arrays.asList("ORDERED", "RECEIVED", "ASSIGNED", "PROCESSING", "REACHED", "PICKEDUP", "ARRIVED", "COMPLETED");
    public static String accessToken = "";
    public static String access_token = null;
    public static AddCart addCart = null;
    public static int addCartShopId = 0;
    public static String address = "";
    public static String addressHeader = "";
    public static AddressList addressList = null;
    public static ArrayList<Card> cardArrayList = null;
    public static List<CartAddon> cartAddons = null;
    public static List<Category> categoryList = null;
    public static ArrayList<Integer> cuisineIdArrayList = null;
    public static List<Cuisine> cuisineList = null;
    public static String currencySymbol = "$";
    public static List<DisputeMessage> disputeMessageList = null;
    public static String email = null;
    public static ArrayList<HashMap<String, String>> foodCart = null;
    public static String imageUrl = null;
    public static boolean isCardChecked = false;
    public static boolean isOfferApplied = false;
    public static boolean isPureVegApplied = false;
    public static Cart isSelctedCart = null;
    public static DisputeMessage isSelectedDispute = null;
    public static Order isSelectedOrder = null;
    public static Product isSelectedProduct = null;
    public static double latitude = 0.0d;
    public static String loginBy = "manual";
    public static double longitude = 0.0d;
    public static String mobile = "";
    public static String mobileNumber = null;
    public static String name = null;
    public static int notificationCount = 0;
    public static List<Order> onGoingOrderList = null;
    public static int otpValue = 0;
    public static List<Order> pastOrderList = null;
    public static User profileModel = null;
    public static List<Product> searchProductList = null;
    public static List<Shop> searchShopList = null;
    public static Address selectedAddress = null;
    public static Shop selectedShop = null;
    public static List<Shop> shopList = null;
    public static boolean shouldContinueService = false;
    public Otp otpModel = null;

    private GlobalData() {
    }

    public static GlobalData getInstance() {
        return ourInstance;
    }

    public static NumberFormat getNumberFormat() {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault());
        currencyInstance.setCurrency(Currency.getInstance("INR"));
        currencyInstance.setMinimumFractionDigits(View.VISIBLE);
        return currencyInstance;
    }
}
