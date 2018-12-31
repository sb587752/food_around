package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.adapter.ManageAddressAdapter;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ManageAddressActivity extends AppCompatActivity {
    public static RelativeLayout errorLayout;
    ManageAddressAdapter adapter;
    @BindView(2131296296)
    Button addNewAddress;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296515)
    TextView errorLayoutDescription;
    boolean isSuccessDelete = false;
    List<Address> locations;
    @BindView(2131296636)
    RecyclerView manageAddressRv;
    @BindView(2131296794)
    ScrollView rootView;
    @BindView(2131296914)
    Toolbar toolbar;
    private SkeletonScreen skeletonScreen;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_manage_address);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07381());
        errorLayout = (RelativeLayout) findViewById(R.id.error_layout);
        this.locations = new ArrayList();
        this.adapter = new ManageAddressAdapter(this.locations, this);
        this.manageAddressRv.setLayoutManager(new LinearLayoutManager(this));
        this.manageAddressRv.setItemAnimator(new DefaultItemAnimator());
        this.manageAddressRv.setAdapter(this.adapter);
    }

    public void onResume() {
        super.onResume();
        getAddress();
    }

    private void getAddress() {
        this.skeletonScreen = Skeleton.bind(this.rootView).load(R.layout.skeloton_address_list_item).color(R.color.shimmer_color).angle(0).show();
        this.apiInterface.getAddresses().enqueue(new C13152());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @OnClick({2131296296})
    public void onViewClicked() {
        startActivity(new Intent(this, SaveDeliveryLocationActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    /* renamed from: com.entriver.orderaround.activities.ManageAddressActivity$1 */
    class C07381 implements OnClickListener {
        C07381() {
        }

        public void onClick(View view) {
            ManageAddressActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.ManageAddressActivity$2 */
    class C13152 implements Callback<List<Address>> {
        C13152() {
        }

        public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
            ManageAddressActivity.this.skeletonScreen.hide();
            if (response.isSuccessful() != null) {
                ManageAddressActivity.this.locations.clear();
                ManageAddressActivity.this.locations.addAll((Collection) response.body());
                GlobalData.profileModel.setAddresses((List) response.body());
                if (ManageAddressActivity.this.locations.size() == null) {
                    ManageAddressActivity.errorLayout.setVisibility(null);
                    return;
                }
                ManageAddressActivity.errorLayout.setVisibility(View.GONE);
                ManageAddressActivity.this.adapter.notifyDataSetChanged();
            }
        }

        public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable th) {
            ManageAddressActivity.this.skeletonScreen.hide();
            Toast.makeText(ManageAddressActivity.this, "Something went wrong", 0).show();
        }
    }
}
