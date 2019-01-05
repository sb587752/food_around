package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.DeliveryLocationAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;
import com.opalfire.foodorder.models.AddressList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SetDeliveryLocationActivity extends AppCompatActivity {
    public static boolean isAddressSelection = false;
    public static boolean isHomePage = false;
    Activity activity;
    @BindView(2131296322)
    ImageView animationLineCartAdd;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    AnimatedVectorDrawableCompat avdProgress;
    @BindView(2131296449)
    LinearLayout currentLocationLl;
    @BindView(2131296468)
    RecyclerView deliveryLocationRv;
    @BindView(2131296550)
    LinearLayout findPlaceLl;
    LinearLayoutManager manager;
    List<AddressList> modelList = new ArrayList();
    @BindView(2131296914)
    Toolbar toolbar;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String TAG = "DeliveryLocationActi";
    private DeliveryLocationAdapter adapter;
    private List<AddressList> modelListReference = new ArrayList();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_set_delivery_location);
        ButterKnife.bind((Activity) this);
        this.activity = this;
        initializeAvd();
        isAddressSelection = getIntent().getBooleanExtra("get_address", false);
        isHomePage = getIntent().getBooleanExtra("home_page", false);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07611());
        this.modelListReference.clear();
        bundle = new AddressList();
        if (GlobalData.profileModel != null) {
            bundle.setHeader(getResources().getString(R.string.saved_addresses));
            bundle.setAddresses(GlobalData.profileModel.getAddresses());
        }
        this.modelListReference.clear();
        this.modelListReference.add(bundle);
        this.manager = new LinearLayoutManager(this);
        this.deliveryLocationRv.setLayoutManager(this.manager);
        this.adapter = new DeliveryLocationAdapter(this, this.activity, this.modelListReference);
        this.deliveryLocationRv.setAdapter(this.adapter);
    }

    public void onResume() {
        super.onResume();
        getAddress();
    }

    private void initializeAvd() {
        this.avdProgress = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.avd_line);
        this.animationLineCartAdd.setBackground(this.avdProgress);
        this.animationLineCartAdd.setVisibility(View.VISIBLE);
        this.avdProgress.start();
    }

    private void getAddress() {
        this.apiInterface.getAddresses().enqueue(new C13592());
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != this.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            return;
        }
        if (i2 == -1) {
            i = PlaceAutocomplete.getPlace(this, intent);
            i2 = new Intent(this, SaveDeliveryLocationActivity.class);
            i2.putExtra("skip_visible", isHomePage);
            i2.putExtra("place_id", i.getId());
            startActivity(i2);
        } else if (i2 == 2) {
            Log.i(this.TAG, PlaceAutocomplete.getStatus(this, intent).getStatusMessage());
        }
    }

    @OnClick({2131296550, 2131296449})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.current_location_ll) {
            startActivity(new Intent(this, SaveDeliveryLocationActivity.class).putExtra("skip_visible", isHomePage));
            overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
        } else if (view == R.id.find_place_ll) {
            findPlace();
        }
    }

    private void findPlace() {
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
        r0 = new com.google.android.gms.location.places.ui.PlaceAutocomplete$IntentBuilder;	 Catch:{ GooglePlayServicesRepairableException -> 0x000f, GooglePlayServicesRepairableException -> 0x000f }
        r1 = 1;	 Catch:{ GooglePlayServicesRepairableException -> 0x000f, GooglePlayServicesRepairableException -> 0x000f }
        r0.<init>(r1);	 Catch:{ GooglePlayServicesRepairableException -> 0x000f, GooglePlayServicesRepairableException -> 0x000f }
        r0 = r0.build(r2);	 Catch:{ GooglePlayServicesRepairableException -> 0x000f, GooglePlayServicesRepairableException -> 0x000f }
        r1 = r2.PLACE_AUTOCOMPLETE_REQUEST_CODE;	 Catch:{ GooglePlayServicesRepairableException -> 0x000f, GooglePlayServicesRepairableException -> 0x000f }
        r2.startActivityForResult(r0, r1);	 Catch:{ GooglePlayServicesRepairableException -> 0x000f, GooglePlayServicesRepairableException -> 0x000f }
    L_0x000f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.foodorder.activities.SetDeliveryLocationActivity.findPlace():void");
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    /* renamed from: com.entriver.foodorder.activities.SetDeliveryLocationActivity$1 */
    class C07611 implements OnClickListener {
        C07611() {
        }

        public void onClick(View view) {
            SetDeliveryLocationActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.SetDeliveryLocationActivity$2 */
    class C13592 implements Callback<List<Address>> {
        C13592() {
        }

        public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
            if (response.isSuccessful() != null) {
                SetDeliveryLocationActivity.this.modelList.clear();
                SetDeliveryLocationActivity.this.animationLineCartAdd.setVisibility(View.GONE);
                SetDeliveryLocationActivity.this.avdProgress.stop();
                call = new AddressList();
                call.setHeader(SetDeliveryLocationActivity.this.getResources().getString(R.string.saved_addresses));
                call.setAddresses((List) response.body());
                GlobalData.profileModel.setAddresses((List) response.body());
                SetDeliveryLocationActivity.this.modelList.add(call);
                SetDeliveryLocationActivity.this.modelListReference.clear();
                SetDeliveryLocationActivity.this.modelListReference.addAll(SetDeliveryLocationActivity.this.modelList);
                SetDeliveryLocationActivity.this.adapter.notifyDataSetChanged();
            }
        }
    }
}
