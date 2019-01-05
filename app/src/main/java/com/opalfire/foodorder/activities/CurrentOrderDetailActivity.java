package com.opalfire.foodorder.activities;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.OrderFlowAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.fragments.OrderViewFragment;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.DataParser;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Message;
import com.opalfire.foodorder.models.Order;
import com.opalfire.foodorder.models.OrderFlow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CurrentOrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, OnMarkerDragListener, ConnectionCallbacks, OnConnectionFailedListener, OnCameraMoveListener {
    public static TextView orderCancelTxt;
    OrderFlowAdapter adapter;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    Context context;
    String currency = "";
    CustomDialog customDialog;
    FragmentManager fragmentManager;
    Handler handler;
    boolean isOrderPage = false;
    int itemQuantity = 0;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    Fragment orderFullViewFragment;

    Runnable orderStatusRunnable;
    String previousStatus = "";
    Double priceAmount = 0.0d;
    @BindView(R.id.order_id_txt)
    TextView orderIdTxt;
    @BindView(R.id.order_item_txt)
    TextView orderItemTxt;
    @BindView(R.id.order_otp)
    TextView orderOtp;
    @BindView(R.id.order_cancel)
    TextView orderCancel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.map)
    Fragment map;
    @BindView(R.id.transparent_image)
    ImageView transparentImage;
    @BindView(R.id.map_touch_rel)
    RelativeLayout mapTouchRel;
    @BindView(R.id.order_flow_rv)
    RecyclerView orderFlowRv;
    @BindView(R.id.order_succeess_image)
    ImageView orderSucceessImage;
    @BindView(R.id.order_status_txt)
    TextView orderStatusTxt;
    @BindView(R.id.order_status_layout)
    RelativeLayout orderStatusLayout;
    @BindView(R.id.order_id_txt_2)
    TextView orderIdTxt2;
    @BindView(R.id.order_placed_time)
    TextView orderPlacedTime;
    @BindView(R.id.order_detail_fargment)
    FrameLayout orderDetailFargment;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    private LatLng destLatLng;
    private Marker destinationMarker;
    private GoogleApiClient mGoogleApiClient;
    private BroadcastReceiver mReceiver;
    private Marker providerMarker;
    private LatLng sourceLatLng;
    private Marker sourceMarker;

    private static float computeRotation(float f, float f2, float f3) {
        f3 = ((f3 - f2) + 360.0f) % 360.0f;
        if ((f3 > 180.0f ? -1.0f : 1.0f) <= 0.0f) {
            f3 -= 360.0f;
        }
        return (((f * f3) + f2) + 360.0f) % 360.0f;
    }

    public static void animateMarker(final Location location, final Marker marker) {
        if (marker != null) {
            final LatLng position = marker.getPosition();
            final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            final float rotation = marker.getRotation();
            final LatLngInterpolator linearFixed = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(1000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float f = animation.getAnimatedFraction();
                        LatLng latLng1 = linearFixed.interpolate(f, position, latLng);
                        marker.setPosition(latLng1);
                        marker.setRotation(computeRotation(f, f, location.getBearing()));
                        return;
                    } catch (Exception paramAnonymousValueAnimator) {
                    }
                }
            });
            valueAnimator.start();
        }
    }

    public void onConnected(@Nullable Bundle bundle) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void onConnectionSuspended(int i) {
    }

    public void onLocationChanged(Location location) {
    }

    public void onMarkerDrag(Marker marker) {
    }

    public void onMarkerDragEnd(Marker marker) {
    }

    public void onMarkerDragStart(Marker marker) {
    }

    @SuppressLint({"ClickableViewAccessibility"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_current_order_detail);
        ButterKnife.bind(this);
        context = this;
        isOrderPage = getIntent().getBooleanExtra("is_order_page", false);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        orderCancelTxt = findViewById(R.id.order_cancel);
        orderCancelTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        handler = new Handler();
        orderStatusRunnable = new Runnable() {
            @Override
            public void run() {
                getParticularOrders(GlobalData.isSelectedOrder.getId());
                handler.postDelayed(orderStatusRunnable, 5000);
            }
        };
        List<OrderFlow> orderFlowList = new ArrayList<>();
        orderFlowList.add(new OrderFlow(getString(R.string.order_placed), getString(R.string.description_1), R.drawable.ic_order_placed, GlobalData.ORDER_STATUS.get(0)));
        orderFlowList.add(new OrderFlow(getString(R.string.order_confirmed), getString(R.string.description_2), R.drawable.ic_order_confirmed, GlobalData.ORDER_STATUS.get(1)));
        String string = getString(R.string.order_processed);
        String string2 = getString(R.string.description_3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GlobalData.ORDER_STATUS.get(2));
        stringBuilder.append(GlobalData.ORDER_STATUS.get(3));
        stringBuilder.append(GlobalData.ORDER_STATUS.get(4));
        orderFlowList.add(new OrderFlow(string, string2, R.drawable.ic_order_processed, stringBuilder.toString()));
        string = getString(R.string.order_pickedup);
        string2 = getString(R.string.description_4);
        stringBuilder = new StringBuilder();
        stringBuilder.append(GlobalData.ORDER_STATUS.get(5));
        stringBuilder.append(GlobalData.ORDER_STATUS.get(6));
        orderFlowList.add(new OrderFlow(string, string2, R.drawable.ic_order_picked_up, stringBuilder.toString()));
        orderFlowList.add(new OrderFlow(getString(R.string.order_delivered), getString(R.string.description_5), R.drawable.ic_order_delivered, GlobalData.ORDER_STATUS.get(7)));
        orderFlowRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderFlowAdapter(orderFlowList, this);
        orderFlowRv.setAdapter(adapter);
        orderFlowRv.setHasFixedSize(false);
        orderFlowRv.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.item_animation_slide_right));
        orderFlowRv.scheduleLayoutAnimation();
        transparentImage.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    default:
                        nestedScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;
                    case 2:
                        nestedScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;
                    case 1:
                        nestedScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }

        });
        if (GlobalData.isSelectedOrder != null) {
            orderIdTxt.setText("ORDER #000" +
                    GlobalData.isSelectedOrder.getId().toString());
            itemQuantity = GlobalData.isSelectedOrder.getInvoice().getQuantity();
            priceAmount = GlobalData.isSelectedOrder.getInvoice().getPayable();
            currency = GlobalData.isSelectedOrder.getItems().get(0).getProduct().getPrices().getCurrency();
            if (itemQuantity == 1) {
                orderItemTxt.setText(itemQuantity + " Item, " + currency + priceAmount);
            } else {
                orderItemTxt.setText(itemQuantity + " Item, " + currency + priceAmount);
            }
            orderIdTxt2.setText("ORDER #000" +
                    GlobalData.isSelectedOrder.getId().toString());
            orderOtp.setText("Otp : " + GlobalData.isSelectedOrder.getOrderOtp());
            orderPlacedTime.setText(getTimeFromString(GlobalData.isSelectedOrder.getCreatedAt()));
            orderFullViewFragment = new OrderViewFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.order_detail_fargment, orderFullViewFragment).commit();
            if (VERSION.SDK_INT < 23) {
                buildGoogleApiClient();
            } else {
                buildGoogleApiClient();
            }
            ((SupportMapFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map))).getMapAsync(this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    public void onCameraMove() {
        nestedScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            if (!googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.context, R.raw.style_json))) {
                Log.i("Map:Style", "Style parsing failed.");
            } else {
                Log.i("Map:Style", "Style Applied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Map:Style", "Can't find style. Error: ");
        this.mMap = googleMap;
        setupMap();
    }

    void setupMap() {
        if (mMap != null) {
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.setBuildingsEnabled(true);
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                mMap.setMyLocationEnabled(false);
                mMap.setOnMarkerDragListener(this);
                mMap.setOnCameraMoveListener(this);
                mMap.getUiSettings().setRotateGesturesEnabled(false);
                mMap.getUiSettings().setTiltGesturesEnabled(false);
                String url = getUrl(GlobalData.isSelectedOrder.getAddress().getLatitude(), GlobalData.isSelectedOrder.getAddress().getLongitude(),
                        GlobalData.isSelectedOrder.getShop().getLatitude(), GlobalData.isSelectedOrder.getShop().getLongitude());
                new FetchUrl().execute(url);
            }
        }
    }

    private String downloadUrl(String str) throws IOException {
        InputStream inputStream = null;
        String str2 = null;
        Exception exception;
        Throwable th;
        String str3 = "";
        HttpURLConnection httpURLConnection = null;
        try {
            BufferedReader bufferedReader;
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "";
                } catch (Throwable th2) {
                    th2.printStackTrace();
                    inputStream.close();
                    httpURLConnection.disconnect();
                }
            } catch (Throwable th3) {
                th3.printStackTrace();
                inputStream.close();
                httpURLConnection.disconnect();
            }
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                }
                str2 = stringBuilder.toString();
            } catch (Exception e2) {
                e2.printStackTrace();
                inputStream.close();
                httpURLConnection.disconnect();
                return str2;
            }
            try {
                Log.d("downloadUrl", str2);
                bufferedReader.close();
            } catch (Exception e3) {
                exception = e3;
                Log.d("Exception", exception.toString());
                inputStream.close();
                httpURLConnection.disconnect();
                return str2;
            }
        } catch (Exception e) {
            inputStream.close();
            httpURLConnection.disconnect();
            return str2;
        } catch (Throwable th4) {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        inputStream.close();
        httpURLConnection.disconnect();
        return str2;
    }

    private String getUrl(double d, double d2, double d3, double d4) {
        return "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + d + "," + d2 +
                "destination=" + d3 + "," + d4 + d + "&" + "sensor=false";
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.order_cancel_dialog, null);
        builder.setView(view);
        final EditText editText = view.findViewById(R.id.reason_edit);
        builder.setTitle(orderIdTxt.getText().toString());
        builder.setMessage("Are you sure want to cancel this order ?");
        builder.setPositiveButton("Submit", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog create = builder.create();
        create.setCancelable(false);
        create.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialogInterface) {
                create.getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(context, "Please enter reason", 0).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        cancelOrder(editText.getText().toString());
                    }
                });
            }
        });
        create.show();
    }

    private void cancelOrder(String str) {
        customDialog = new CustomDialog(context);
        customDialog.setCancelable(false);
        apiInterface.cancelOrder(GlobalData.isSelectedOrder.getId().intValue(), str).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (response.isSuccessful()) {
                    onBackPressed();
                    return;
                }
                customDialog.dismiss();
                try {
                    Toast.makeText(context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable th) {
                customDialog.dismiss();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (isOrderPage) {
            finish();
            return;
        }
        startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    private void getParticularOrders(int i) {
        apiInterface.getParticularOrders(i).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    GlobalData.isSelectedOrder = response.body();
                    Log.i("isSelectedOrder : ", GlobalData.isSelectedOrder.toString());
                    if (!(GlobalData.isSelectedOrder.getStatus().equals("PICKEDUP")
                            && GlobalData.isSelectedOrder.getStatus().equals("ARRIVED")
                            && GlobalData.isSelectedOrder.getStatus().equals("ASSIGNED"))) {
                        liveNavigation(GlobalData.isSelectedOrder.getTransporter().getLatitude(),
                                GlobalData.isSelectedOrder.getTransporter().getLongitude());
                    }
                    if (GlobalData.isSelectedOrder.getStatus().equalsIgnoreCase(previousStatus)) {
                        previousStatus = GlobalData.isSelectedOrder.getStatus();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                try {
                    Toast.makeText(context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            }
        });
    }

    public void liveNavigation(Double d, Double d2) {
        Log.e("Livenavigation", "ProLat" +
                d +
                " ProLng" +
                d2);
        if (d != null && d2 != null) {
            Location location = new Location("providerlocation");
            location.setLatitude(d);
            location.setLongitude(d2);
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(d, d2)).rotation(0.0f).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_marker));
            if (providerMarker != null) {
                animateMarker(location, providerMarker);
            } else {
                providerMarker = mMap.addMarker(markerOptions);
            }
        }
    }

    public float getBearing(LatLng latLng, LatLng latLng2) {
        double d = latLng2.longitude - latLng.longitude;
        double d2 = latLng2.latitude - latLng.latitude;
        double atan = 1.5707963267948966d - Math.atan(d2 / d);
        if (d > 0.0d) {
            return (float) atan;
        }
        if (d < 0.0d) {
            return (float) (atan + 1413754136);
        }
        return d2 < 0.0d ? 3.1415927f : null;
    }

    private String getTimeFromString(String str) {
        String str2 = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            if (str != null) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
            return str2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void rateTransporter(HashMap<String, String> hashMap) {
        System.out.println(hashMap.toString());
        apiInterface.rate(hashMap).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.errorBody() != null) {
                    finish();
                } else if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), 0).show();
                    startActivity(new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable th) {
                Toast.makeText(context, "Something wrong - rateTransporter", 0).show();
                startActivity(new Intent(context, OrdersActivity.class));
                finish();
            }
        });
    }

    public void rate() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View frameLayout = getLayoutInflater().inflate(R.layout.feedback_popup, null);
            builder.setView(frameLayout);
            final AlertDialog create = builder.create();
            create.show();
            final Integer[] numArr = new Integer[]{5};
            RadioGroup radioGroup = frameLayout.findViewById(R.id.rate_radiogroup);
            ((RadioButton) radioGroup.getChildAt(4)).setChecked(true);
            radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    numArr[0] = i;
                }
            });
            final EditText editText = frameLayout.findViewById(R.id.comment);
            frameLayout.findViewById(R.id.feedback_submit);
            frameLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (GlobalData.isSelectedOrder != null && GlobalData.isSelectedOrder.getId() != null) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("order_id", String.valueOf(GlobalData.isSelectedOrder.getId()));
                        hashMap.put("rating", String.valueOf(numArr[0]));
                        hashMap.put("comment", editText.getText().toString());
                        hashMap.put("type", "transporter");
                        rateTransporter(hashMap);
                        create.dismiss();
                    }
                }
            });
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(orderStatusRunnable);
    }

    protected void onResume() {
        super.onResume();
        handler.postDelayed(orderStatusRunnable, 500);
    }

    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(orderStatusRunnable);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private interface LatLngInterpolator {

        LatLng interpolate(float f, LatLng latLng, LatLng latLng2);

        class LinearFixed implements LatLngInterpolator {
            public LatLng interpolate(float f, LatLng latLng, LatLng latLng2) {
                double d = latLng2.latitude - latLng.latitude;
                double d2 = (double) f;
                d = (d * d2) + latLng.latitude;
                double d3 = latLng2.longitude - latLng.longitude;
                if (Math.abs(d3) > 180.0d) {
                    d3 -= Math.signum(d3) * 360.0d;
                }
                return new LatLng(d, (d3 * d2) + latLng.longitude);
            }
        }
    }


    private class FetchUrl extends AsyncTask<String, Void, String> {
        private FetchUrl() {
        }

        @Override
        protected String doInBackground(String... strArr) {
            String str = "";
            try {
                str = downloadUrl(strArr[0]);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            try {
                if (new JSONObject(str).optString("status").equalsIgnoreCase("ZERO_RESULTS")) {
                    Toast.makeText(context, "No Route", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ParserTask().execute(str);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ParserTask() {
        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strArr) {
            List<List<HashMap<String, String>>> listList = null;
            try {
                JSONObject jSONObject = new JSONObject(strArr[0]);
                Log.d("ParserTask", strArr[0]);
                DataParser datar = new DataParser();
                Log.d("ParserTask", strArr.toString());
                listList = datar.parse(jSONObject);
                try {
                    Log.d("ParserTask", "Executing routes");
                    Log.d("ParserTask", listList.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return listList;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> list) {
            PolylineOptions polylineOptions;
            if (list != null) {
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        ArrayList<LatLng> asd = new ArrayList<>();
                        polylineOptions = new PolylineOptions();
                        List<HashMap<String, String>> list2 = list.get(i);
                        for (int i2 = 0; i2 < list2.size(); i2++) {
                            HashMap<String, String> hashMap = list2.get(i2);
                            asd.add(new LatLng(Double.parseDouble(Objects.requireNonNull(hashMap.get("lat"))), Double.parseDouble(Objects.requireNonNull(hashMap.get("lng")))));
                        }
                        sourceMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(GlobalData.isSelectedOrder.getAddress().getLatitude(),
                                GlobalData.isSelectedOrder.getAddress().getLongitude()))
                                .title("Source").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hoem_marker)));
                        destLatLng = new LatLng(GlobalData.isSelectedOrder.getShop().getLatitude(),
                                GlobalData.isSelectedOrder.getShop().getLongitude());
                        if (destinationMarker != null) {
                            destinationMarker.remove();
                        }
                        destinationMarker = mMap.addMarker(new MarkerOptions().position(destLatLng).title("Destination").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_marker)));
                        Builder builder = new Builder();
                        builder.include(sourceMarker.getPosition());
                        builder.include(destinationMarker.getPosition());
                        LatLngBounds build = builder.build();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(build, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, (int) (getResources().getDisplayMetrics().widthPixels * 0.2d)));
                        polylineOptions.addAll(asd);
                        polylineOptions.width(5.0f);
                        polylineOptions.color(ViewCompat.MEASURED_STATE_MASK);
                        Log.d("onPostExecute", "onPostExecute lineoptions decoded");
                    }
                    Log.d("onPostExecute", "without Polylines drawn");
                }
                mMap.clear();
            }
            Log.d("onPostExecute", "without Polylines drawn");
        }
    }


}
