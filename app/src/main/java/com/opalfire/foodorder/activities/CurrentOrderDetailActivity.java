package com.opalfire.foodorder.activities;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Button;
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
import com.opalfire.foodorder.models.Item;
import com.opalfire.foodorder.models.Message;
import com.opalfire.foodorder.models.Order;
import com.opalfire.foodorder.models.OrderFlow;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CurrentOrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, OnMarkerDragListener, ConnectionCallbacks, OnConnectionFailedListener, OnCameraMoveListener {
    public static TextView orderCancelTxt;
    OrderFlowAdapter adapter;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    Context context;
    String currency = "";
    CustomDialog customDialog;
    FragmentManager fragmentManager;
    Handler handler;
    boolean isOrderPage = false;
    int itemQuantity = 0;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    @BindView(2131296639)
    RelativeLayout mapTouchRel;
    @BindView(2131296661)
    NestedScrollView nestedScrollView;
    @BindView(2131296688)
    RecyclerView orderFlowRv;
    Fragment orderFullViewFragment;
    @BindView(2131296689)
    TextView orderIdTxt;
    @BindView(2131296690)
    TextView orderIdTxt2;
    Intent orderIntent;
    @BindView(2131296692)
    TextView orderItemTxt;
    @BindView(2131296693)
    TextView orderOtp;
    @BindView(2131296694)
    TextView orderPlacedTime;
    @BindView(2131296698)
    RelativeLayout orderStatusLayout;
    Runnable orderStatusRunnable;
    @BindView(2131296700)
    TextView orderStatusTxt;
    String previousStatus = "";
    Double priceAmount = Double.valueOf(0.0d);
    @BindView(2131296914)
    Toolbar toolbar;
    @BindView(2131296929)
    ImageView transparentImage;
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

    public static void animateMarker(Location location, Marker marker) {
        if (marker != null) {
            final LatLng position = marker.getPosition();
            final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            final float rotation = marker.getRotation();
            final LatLngInterpolator linearFixed = new LinearFixed();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat.setDuration(1000);
            ofFloat.setInterpolator(new LinearInterpolator());
            final Marker marker2 = marker;
            final Location location2 = location;
            ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(android.animation.ValueAnimator r4) {
                    /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
                    /*
                    r3 = this;
                    r4 = r4.getAnimatedFraction();	 Catch:{ Exception -> 0x0024 }
                    r0 = r1;	 Catch:{ Exception -> 0x0024 }
                    r1 = r2;	 Catch:{ Exception -> 0x0024 }
                    r2 = r3;	 Catch:{ Exception -> 0x0024 }
                    r0 = r0.interpolate(r4, r1, r2);	 Catch:{ Exception -> 0x0024 }
                    r1 = r4;	 Catch:{ Exception -> 0x0024 }
                    r1.setPosition(r0);	 Catch:{ Exception -> 0x0024 }
                    r0 = r4;	 Catch:{ Exception -> 0x0024 }
                    r1 = r5;	 Catch:{ Exception -> 0x0024 }
                    r2 = r6;	 Catch:{ Exception -> 0x0024 }
                    r2 = r2.getBearing();	 Catch:{ Exception -> 0x0024 }
                    r4 = com.entriver.foodorder.activities.CurrentOrderDetailActivity.computeRotation(r4, r1, r2);	 Catch:{ Exception -> 0x0024 }
                    r0.setRotation(r4);	 Catch:{ Exception -> 0x0024 }
                L_0x0024:
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.entriver.foodorder.activities.CurrentOrderDetailActivity.9.onAnimationUpdate(android.animation.ValueAnimator):void");
                }
            });
            ofFloat.start();
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
        setContentView((int) R.layout.activity_current_order_detail);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.isOrderPage = getIntent().getBooleanExtra("is_order_page", false);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07181());
        this.toolbar.setPadding(0, 0, 0, 0);
        this.toolbar.setContentInsetsAbsolute(0, 0);
        orderCancelTxt = (TextView) findViewById(R.id.order_cancel);
        orderCancelTxt.setOnClickListener(new C07192());
        this.handler = new Handler();
        this.orderStatusRunnable = new C07203();
        bundle = new ArrayList();
        bundle.add(new OrderFlow(getString(R.string.order_placed), getString(R.string.description_1), R.drawable.ic_order_placed, (String) GlobalData.ORDER_STATUS.get(0)));
        bundle.add(new OrderFlow(getString(R.string.order_confirmed), getString(R.string.description_2), R.drawable.ic_order_confirmed, (String) GlobalData.ORDER_STATUS.get(1)));
        String string = getString(R.string.order_processed);
        String string2 = getString(R.string.description_3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String) GlobalData.ORDER_STATUS.get(2));
        stringBuilder.append((String) GlobalData.ORDER_STATUS.get(3));
        stringBuilder.append((String) GlobalData.ORDER_STATUS.get(4));
        bundle.add(new OrderFlow(string, string2, R.drawable.ic_order_processed, stringBuilder.toString()));
        string = getString(R.string.order_pickedup);
        string2 = getString(R.string.description_4);
        stringBuilder = new StringBuilder();
        stringBuilder.append((String) GlobalData.ORDER_STATUS.get(5));
        stringBuilder.append((String) GlobalData.ORDER_STATUS.get(6));
        bundle.add(new OrderFlow(string, string2, R.drawable.ic_order_picked_up, stringBuilder.toString()));
        bundle.add(new OrderFlow(getString(R.string.order_delivered), getString(R.string.description_5), R.drawable.ic_order_delivered, (String) GlobalData.ORDER_STATUS.get(7)));
        this.orderFlowRv.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new OrderFlowAdapter(bundle, this);
        this.orderFlowRv.setAdapter(this.adapter);
        this.orderFlowRv.setHasFixedSize(false);
        this.orderFlowRv.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.item_animation_slide_right));
        this.orderFlowRv.scheduleLayoutAnimation();
        this.transparentImage.setOnTouchListener(new C07214());
        if (GlobalData.isSelectedOrder != null) {
            StringBuilder stringBuilder2;
            bundle = GlobalData.isSelectedOrder;
            TextView textView = this.orderIdTxt;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("ORDER #000");
            stringBuilder3.append(bundle.getId().toString());
            textView.setText(stringBuilder3.toString());
            this.itemQuantity = bundle.getInvoice().getQuantity().intValue();
            this.priceAmount = bundle.getInvoice().getPayable();
            this.currency = ((Item) bundle.getItems().get(0)).getProduct().getPrices().getCurrency();
            if (this.itemQuantity == 1) {
                textView = this.orderItemTxt;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(String.valueOf(this.itemQuantity));
                stringBuilder2.append(" Item, ");
                stringBuilder2.append(this.currency);
                stringBuilder2.append(String.valueOf(this.priceAmount));
                textView.setText(stringBuilder2.toString());
            } else {
                textView = this.orderItemTxt;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(String.valueOf(this.itemQuantity));
                stringBuilder2.append(" Items, ");
                stringBuilder2.append(this.currency);
                stringBuilder2.append(String.valueOf(this.priceAmount));
                textView.setText(stringBuilder2.toString());
            }
            textView = this.orderIdTxt2;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("#000");
            stringBuilder2.append(bundle.getId().toString());
            textView.setText(stringBuilder2.toString());
            textView = this.orderOtp;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" : ");
            stringBuilder2.append(GlobalData.isSelectedOrder.getOrderOtp());
            textView.setText(stringBuilder2.toString());
            this.orderPlacedTime.setText(getTimeFromString(bundle.getCreatedAt()));
            this.orderFullViewFragment = new OrderViewFragment();
            this.fragmentManager = getSupportFragmentManager();
            this.fragmentManager.beginTransaction().add((int) R.id.order_detail_fargment, this.orderFullViewFragment).commit();
            if (VERSION.SDK_INT < 23) {
                buildGoogleApiClient();
            } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == null) {
                buildGoogleApiClient();
            }
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).build();
        this.mGoogleApiClient.connect();
    }

    public void onCameraMove() {
        this.nestedScrollView.requestDisallowInterceptTouchEvent(true);
    }

    public void onMapReady(com.google.android.gms.maps.GoogleMap r3) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
        /*
        r2 = this;
        r0 = r2.mContext;	 Catch:{ NotFoundException -> 0x001f }
        r1 = 2131755013; // 0x7f100005 float:1.9140893E38 double:1.0532269173E-314;	 Catch:{ NotFoundException -> 0x001f }
        r0 = com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle(r0, r1);	 Catch:{ NotFoundException -> 0x001f }
        r0 = r3.setMapStyle(r0);	 Catch:{ NotFoundException -> 0x001f }
        if (r0 != 0) goto L_0x0017;	 Catch:{ NotFoundException -> 0x001f }
    L_0x000f:
        r0 = "Map:Style";	 Catch:{ NotFoundException -> 0x001f }
        r1 = "Style parsing failed.";	 Catch:{ NotFoundException -> 0x001f }
        android.util.Log.i(r0, r1);	 Catch:{ NotFoundException -> 0x001f }
        goto L_0x0026;	 Catch:{ NotFoundException -> 0x001f }
    L_0x0017:
        r0 = "Map:Style";	 Catch:{ NotFoundException -> 0x001f }
        r1 = "Style Applied.";	 Catch:{ NotFoundException -> 0x001f }
        android.util.Log.i(r0, r1);	 Catch:{ NotFoundException -> 0x001f }
        goto L_0x0026;
    L_0x001f:
        r0 = "Map:Style";
        r1 = "Can't find style. Error: ";
        android.util.Log.i(r0, r1);
    L_0x0026:
        r2.mMap = r3;
        r2.setupMap();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.foodorder.activities.CurrentOrderDetailActivity.onMapReady(com.google.android.gms.maps.GoogleMap):void");
    }

    void setupMap() {
        if (this.mMap != null) {
            this.mMap.getUiSettings().setCompassEnabled(false);
            this.mMap.setBuildingsEnabled(true);
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                this.mMap.setMyLocationEnabled(false);
                this.mMap.setOnMarkerDragListener(this);
                this.mMap.setOnCameraMoveListener(this);
                this.mMap.getUiSettings().setRotateGesturesEnabled(false);
                this.mMap.getUiSettings().setTiltGesturesEnabled(false);
                String url = getUrl(GlobalData.isSelectedOrder.getAddress().getLatitude().doubleValue(), GlobalData.isSelectedOrder.getAddress().getLongitude().doubleValue(), GlobalData.isSelectedOrder.getShop().getLatitude().doubleValue(), GlobalData.isSelectedOrder.getShop().getLongitude().doubleValue());
                new FetchUrl().execute(new String[]{url});
            }
        }
    }

    private String downloadUrl(String str) throws IOException {
        InputStream inputStream;
        String str2;
        Exception exception;
        Throwable th;
        String str3 = "";
        try {
            BufferedReader bufferedReader;
            str = (HttpURLConnection) new URL(str).openConnection();
            try {
                str.connect();
                inputStream = str.getInputStream();
            } catch (Exception e) {
                str2 = str3;
                Exception exception2 = e;
                inputStream = null;
                exception = exception2;
                try {
                    Log.d("Exception", exception.toString());
                    inputStream.close();
                    str.disconnect();
                    return str2;
                } catch (Throwable th2) {
                    th = th2;
                    inputStream.close();
                    str.disconnect();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                inputStream.close();
                str.disconnect();
                throw th;
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
                exception = e2;
                str2 = str3;
                Log.d("Exception", exception.toString());
                inputStream.close();
                str.disconnect();
                return str2;
            }
            try {
                Log.d("downloadUrl", str2);
                bufferedReader.close();
            } catch (Exception e3) {
                exception = e3;
                Log.d("Exception", exception.toString());
                inputStream.close();
                str.disconnect();
                return str2;
            }
        } catch (String str4) {
            str2 = str3;
            inputStream = null;
            exception = str4;
            str4 = inputStream;
            Log.d("Exception", exception.toString());
            inputStream.close();
            str4.disconnect();
            return str2;
        } catch (Throwable th4) {
            th = th4;
            str4 = null;
            inputStream = str4;
            inputStream.close();
            str4.disconnect();
            throw th;
        }
        inputStream.close();
        str4.disconnect();
        return str2;
    }

    private String getUrl(double d, double d2, double d3, double d4) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("origin=");
        stringBuilder.append(d);
        stringBuilder.append(",");
        stringBuilder.append(d2);
        d = stringBuilder.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("destination=");
        stringBuilder2.append(d3);
        stringBuilder2.append(",");
        stringBuilder2.append(d4);
        String stringBuilder3 = stringBuilder2.toString();
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append(d);
        stringBuilder4.append("&");
        stringBuilder4.append(stringBuilder3);
        stringBuilder4.append("&");
        stringBuilder4.append("sensor=false");
        d = stringBuilder4.toString();
        d2 = new StringBuilder();
        d2.append("https://maps.googleapis.com/maps/api/directions/");
        d2.append("json");
        d2.append("?");
        d2.append(d);
        return d2.toString();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.order_cancel_dialog, null);
        builder.setView(inflate);
        final EditText editText = (EditText) inflate.findViewById(R.id.reason_edit);
        builder.setTitle(this.orderIdTxt.getText().toString());
        builder.setMessage((CharSequence) "Are you sure want to cancel this order ?");
        builder.setPositiveButton((CharSequence) "Submit", null);
        builder.setNegativeButton((CharSequence) "Cancel", new C07225());
        final AlertDialog create = builder.create();
        create.setCancelable(false);
        create.setOnShowListener(new OnShowListener() {
            public void onShow(final DialogInterface dialogInterface) {
                create.getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equalsIgnoreCase("") != null) {
                            Toast.makeText(CurrentOrderDetailActivity.this.context, "Please enter reason", 0).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        CurrentOrderDetailActivity.this.cancelOrder(editText.getText().toString());
                    }
                });
            }
        });
        create.show();
    }

    private void cancelOrder(String str) {
        this.customDialog = new CustomDialog(this.context);
        this.customDialog.setCancelable(false);
        this.apiInterface.cancelOrder(GlobalData.isSelectedOrder.getId().intValue(), str).enqueue(new C12897());
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.isOrderPage) {
            finish();
            return;
        }
        startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    private void getParticularOrders(int i) {
        this.apiInterface.getParticularOrders(i).enqueue(new C12908());
    }

    public void liveNavigation(Double d, Double d2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProLat");
        stringBuilder.append(d);
        stringBuilder.append(" ProLng");
        stringBuilder.append(d2);
        Log.e("Livenavigation", stringBuilder.toString());
        if (d != null && d2 != null) {
            Location location = new Location("providerlocation");
            location.setLatitude(d.doubleValue());
            location.setLongitude(d2.doubleValue());
            d = new MarkerOptions().position(new LatLng(d.doubleValue(), d2.doubleValue())).rotation(Float.valueOf(0.0f).floatValue()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_marker));
            if (this.providerMarker != null) {
                animateMarker(location, this.providerMarker);
            } else {
                this.providerMarker = this.mMap.addMarker(d);
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
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time : ");
        stringBuilder.append(str);
        printStream.println(stringBuilder.toString());
        String str2 = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            if (str != null) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
            return str2;
        } catch (String str3) {
            str3.printStackTrace();
            return str2;
        }
    }

    private void rateTransporter(HashMap<String, String> hashMap) {
        System.out.println(hashMap.toString());
        this.apiInterface.rate(hashMap).enqueue(new Callback<Message>() {
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.errorBody() != null) {
                    CurrentOrderDetailActivity.this.finish();
                } else if (response.isSuccessful() != null) {
                    Toast.makeText(CurrentOrderDetailActivity.this.context, ((Message) response.body()).getMessage(), 0).show();
                    CurrentOrderDetailActivity.this.startActivity(new Intent(CurrentOrderDetailActivity.this.context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    CurrentOrderDetailActivity.this.finish();
                }
            }

            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable th) {
                Toast.makeText(CurrentOrderDetailActivity.this.context, "Something wrong - rateTransporter", 0).show();
                CurrentOrderDetailActivity.this.startActivity(new Intent(CurrentOrderDetailActivity.this.context, OrdersActivity.class));
                CurrentOrderDetailActivity.this.finish();
            }
        });
    }

    public void rate() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View frameLayout = new FrameLayout(this);
            builder.setView(frameLayout);
            final AlertDialog create = builder.create();
            frameLayout = create.getLayoutInflater().inflate(R.layout.feedback_popup, frameLayout);
            create.show();
            final Integer[] numArr = new Integer[]{Integer.valueOf(5)};
            RadioGroup radioGroup = (RadioGroup) frameLayout.findViewById(R.id.rate_radiogroup);
            ((RadioButton) radioGroup.getChildAt(4)).setChecked(true);
            radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    numArr[0] = Integer.valueOf(i);
                }
            });
            final EditText editText = (EditText) frameLayout.findViewById(R.id.comment);
            ((Button) frameLayout.findViewById(R.id.feedback_submit)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (GlobalData.isSelectedOrder != null && GlobalData.isSelectedOrder.getId() != null) {
                        view = new HashMap();
                        view.put("order_id", String.valueOf(GlobalData.isSelectedOrder.getId()));
                        view.put("rating", String.valueOf(numArr[0]));
                        view.put("comment", editText.getText().toString());
                        view.put("type", "transporter");
                        CurrentOrderDetailActivity.this.rateTransporter(view);
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
        this.handler.removeCallbacks(this.orderStatusRunnable);
    }

    protected void onResume() {
        super.onResume();
        this.handler.postDelayed(this.orderStatusRunnable, 500);
    }

    protected void onPause() {
        super.onPause();
        this.handler.removeCallbacks(this.orderStatusRunnable);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private interface LatLngInterpolator {

        LatLng interpolate(float f, LatLng latLng, LatLng latLng2);

        public static class LinearFixed implements LatLngInterpolator {
            public LatLng interpolate(float f, LatLng latLng, LatLng latLng2) {
                double d = latLng2.latitude - latLng.latitude;
                double d2 = (double) f;
                Double.isNaN(d2);
                d = (d * d2) + latLng.latitude;
                double d3 = latLng2.longitude - latLng.longitude;
                if (Math.abs(d3) > 180.0d) {
                    d3 -= Math.signum(d3) * 360.0d;
                }
                Double.isNaN(d2);
                return new LatLng(d, (d3 * d2) + latLng.longitude);
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$1 */
    class C07181 implements OnClickListener {
        C07181() {
        }

        public void onClick(View view) {
            CurrentOrderDetailActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$2 */
    class C07192 implements OnClickListener {
        C07192() {
        }

        public void onClick(View view) {
            CurrentOrderDetailActivity.this.showDialog();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$3 */
    class C07203 implements Runnable {
        C07203() {
        }

        public void run() {
            CurrentOrderDetailActivity.this.getParticularOrders(GlobalData.isSelectedOrder.getId().intValue());
            CurrentOrderDetailActivity.this.handler.postDelayed(this, 5000);
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$4 */
    class C07214 implements OnTouchListener {
        C07214() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case null:
                    CurrentOrderDetailActivity.this.nestedScrollView.requestDisallowInterceptTouchEvent(true);
                    return false;
                case 1:
                    CurrentOrderDetailActivity.this.nestedScrollView.requestDisallowInterceptTouchEvent(false);
                    return true;
                case 2:
                    CurrentOrderDetailActivity.this.nestedScrollView.requestDisallowInterceptTouchEvent(true);
                    return false;
                default:
                    return true;
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$5 */
    class C07225 implements DialogInterface.OnClickListener {
        C07225() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {
        private FetchUrl() {
        }

        protected String doInBackground(String... strArr) {
            String str = "";
            try {
                strArr = CurrentOrderDetailActivity.this.downloadUrl(strArr[0]);
                try {
                    Log.d("Background Task data", strArr);
                    return strArr;
                } catch (Exception e) {
                    Exception exception = e;
                    str = strArr;
                    strArr = exception;
                    Log.d("Background Task", strArr.toString());
                    return str;
                }
            } catch (Exception e2) {
                strArr = e2;
                Log.d("Background Task", strArr.toString());
                return str;
            }
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            try {
                if (new JSONObject(str).optString("status").equalsIgnoreCase("ZERO_RESULTS")) {
                    Toast.makeText(CurrentOrderDetailActivity.this.context, "No Route", 0).show();
                    return;
                }
                new ParserTask().execute(new String[]{str});
            } catch (String str2) {
                str2.printStackTrace();
            }
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ParserTask() {
        }

        protected List<List<HashMap<String, String>>> doInBackground(String... strArr) {
            String[] strArr2 = null;
            try {
                JSONObject jSONObject = new JSONObject(strArr[0]);
                Log.d("ParserTask", strArr[0]);
                strArr = new DataParser();
                Log.d("ParserTask", strArr.toString());
                strArr = strArr.parse(jSONObject);
                try {
                    Log.d("ParserTask", "Executing routes");
                    Log.d("ParserTask", strArr.toString());
                    return strArr;
                } catch (Exception e) {
                    Exception exception = e;
                    strArr2 = strArr;
                    strArr = exception;
                    Log.d("ParserTask", strArr.toString());
                    strArr.printStackTrace();
                    return strArr2;
                }
            } catch (Exception e2) {
                strArr = e2;
                Log.d("ParserTask", strArr.toString());
                strArr.printStackTrace();
                return strArr2;
            }
        }

        protected void onPostExecute(List<List<HashMap<String, String>>> list) {
            Iterable iterable;
            PolylineOptions polylineOptions = null;
            if (list != null) {
                if (list.size() > 0) {
                    iterable = null;
                    PolylineOptions polylineOptions2 = iterable;
                    for (int i = 0; i < list.size(); i++) {
                        int i2;
                        iterable = new ArrayList();
                        polylineOptions2 = new PolylineOptions();
                        List list2 = (List) list.get(i);
                        for (i2 = 0; i2 < list2.size(); i2++) {
                            HashMap hashMap = (HashMap) list2.get(i2);
                            iterable.add(new LatLng(Double.parseDouble((String) hashMap.get("lat")), Double.parseDouble((String) hashMap.get("lng"))));
                        }
                        CurrentOrderDetailActivity.this.sourceMarker = CurrentOrderDetailActivity.this.mMap.addMarker(new MarkerOptions().position(new LatLng(GlobalData.isSelectedOrder.getAddress().getLatitude().doubleValue(), GlobalData.isSelectedOrder.getAddress().getLongitude().doubleValue())).title("Source").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hoem_marker)));
                        CurrentOrderDetailActivity.this.destLatLng = new LatLng(GlobalData.isSelectedOrder.getShop().getLatitude().doubleValue(), GlobalData.isSelectedOrder.getShop().getLongitude().doubleValue());
                        if (CurrentOrderDetailActivity.this.destinationMarker != null) {
                            CurrentOrderDetailActivity.this.destinationMarker.remove();
                        }
                        CurrentOrderDetailActivity.this.destinationMarker = CurrentOrderDetailActivity.this.mMap.addMarker(new MarkerOptions().position(CurrentOrderDetailActivity.this.destLatLng).title("Destination").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_marker)));
                        Builder builder = new Builder();
                        builder.include(CurrentOrderDetailActivity.this.sourceMarker.getPosition());
                        builder.include(CurrentOrderDetailActivity.this.destinationMarker.getPosition());
                        LatLngBounds build = builder.build();
                        i2 = CurrentOrderDetailActivity.this.getResources().getDisplayMetrics().widthPixels;
                        int i3 = CurrentOrderDetailActivity.this.getResources().getDisplayMetrics().heightPixels;
                        double d = (double) i2;
                        Double.isNaN(d);
                        CurrentOrderDetailActivity.this.mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(build, i2, i3, (int) (d * 0.2d)));
                        polylineOptions2.addAll(iterable);
                        polylineOptions2.width(5.0f);
                        polylineOptions2.color(ViewCompat.MEASURED_STATE_MASK);
                        Log.d("onPostExecute", "onPostExecute lineoptions decoded");
                    }
                    polylineOptions = polylineOptions2;
                    if (polylineOptions != null || r2 == null) {
                        Log.d("onPostExecute", "without Polylines drawn");
                    } else {
                        CurrentOrderDetailActivity.this.mMap.addPolyline(polylineOptions);
                        return;
                    }
                }
                CurrentOrderDetailActivity.this.mMap.clear();
            }
            iterable = null;
            if (polylineOptions != null) {
            }
            Log.d("onPostExecute", "without Polylines drawn");
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$7 */
    class C12897 implements Callback<Order> {
        C12897() {
        }

        public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
            if (response.isSuccessful() != null) {
                CurrentOrderDetailActivity.this.onBackPressed();
                return;
            }
            CurrentOrderDetailActivity.this.customDialog.dismiss();
            try {
                Toast.makeText(CurrentOrderDetailActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<Order> response2) {
                Toast.makeText(CurrentOrderDetailActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<Order> call, @NonNull Throwable th) {
            CurrentOrderDetailActivity.this.customDialog.dismiss();
            Toast.makeText(CurrentOrderDetailActivity.this, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.CurrentOrderDetailActivity$8 */
    class C12908 implements Callback<Order> {
        C12908() {
        }

        public void onFailure(@NonNull Call<Order> call, @NonNull Throwable th) {
        }

        @SuppressLint({"SetTextI18n"})
        public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
            if (response.isSuccessful() != null) {
                GlobalData.isSelectedOrder = (Order) response.body();
                Log.i("isSelectedOrder : ", GlobalData.isSelectedOrder.toString());
                if (!(GlobalData.isSelectedOrder.getStatus().equals("PICKEDUP") == null && GlobalData.isSelectedOrder.getStatus().equals("ARRIVED") == null && GlobalData.isSelectedOrder.getStatus().equals("ASSIGNED") == null)) {
                    CurrentOrderDetailActivity.this.liveNavigation(GlobalData.isSelectedOrder.getTransporter().getLatitude(), GlobalData.isSelectedOrder.getTransporter().getLongitude());
                }
                if (GlobalData.isSelectedOrder.getStatus().equalsIgnoreCase(CurrentOrderDetailActivity.this.previousStatus) == null) {
                    CurrentOrderDetailActivity.this.previousStatus = GlobalData.isSelectedOrder.getStatus();
                    CurrentOrderDetailActivity.this.adapter.notifyDataSetChanged();
                    return;
                }
                return;
            }
            try {
                Toast.makeText(CurrentOrderDetailActivity.this.context, new JSONObject(response.errorBody().string()).optString("message"), 1).show();
            } catch (Response<Order> response2) {
                Toast.makeText(CurrentOrderDetailActivity.this.context, response2.getMessage(), 1).show();
            }
        }
    }
}
