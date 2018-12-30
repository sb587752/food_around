package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.fragments.OrderViewFragment;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Item;
import com.opalfire.orderaround.models.Ordertiming;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PastOrderDetailActivity extends AppCompatActivity {
    String currency = "";
    @BindView(2131296476)
    ImageView destinationImage;
    @BindView(2131296477)
    RelativeLayout destinationLayout;
    int discount = 0;
    @BindView(2131296500)
    ImageView dotLineImg;
    FragmentManager fragmentManager;
    int itemCount = 0;
    int itemQuantity = 0;
    @BindView(2131296661)
    NestedScrollView nestedScrollView;
    @BindView(2131296687)
    FrameLayout orderDetailFargment;
    Fragment orderFullViewFragment;
    @BindView(2131296689)
    TextView orderIdTxt;
    @BindView(2131296692)
    TextView orderItemTxt;
    @BindView(2131296698)
    RelativeLayout orderStatusLayout;
    @BindView(2131296700)
    TextView orderStatusTxt;
    @BindView(2131296701)
    ImageView orderSucceessImage;
    Double priceAmount = Double.valueOf(0.0d);
    @BindView(2131296778)
    TextView restaurantAddress;
    @BindView(2131296783)
    TextView restaurantName;
    @BindView(2131296860)
    ImageView sourceImage;
    @BindView(2131296861)
    RelativeLayout sourceLayout;
    @BindView(2131296914)
    Toolbar toolbar;
    @BindView(2131296943)
    TextView userAddress;
    @BindView(2131296944)
    TextView userAddressTitle;
    @BindView(2131296961)
    View viewLine2;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_order_detail);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07511());
        if (GlobalData.isSelectedOrder != null) {
            bundle = GlobalData.isSelectedOrder;
            TextView textView = this.orderIdTxt;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ORDER #000");
            stringBuilder.append(bundle.getId().toString());
            textView.setText(stringBuilder.toString());
            this.itemQuantity = bundle.getInvoice().getQuantity().intValue();
            this.priceAmount = bundle.getInvoice().getPayable();
            if (bundle.getStatus().equalsIgnoreCase("CANCELLED")) {
                this.orderStatusTxt.setText(getResources().getString(R.string.order_cancelled));
                this.orderSucceessImage.setImageResource(R.drawable.order_cancelled_img);
                this.dotLineImg.setBackgroundResource(R.drawable.order_cancelled_line);
                this.orderStatusTxt.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
            } else {
                textView = this.orderStatusTxt;
                stringBuilder = new StringBuilder();
                stringBuilder.append(getResources().getString(R.string.order_delivered_successfully_on));
                stringBuilder.append(getFormatTime(((Ordertiming) bundle.getOrdertiming().get(7)).getCreatedAt()));
                textView.setText(stringBuilder.toString());
                this.orderStatusTxt.setTextColor(ContextCompat.getColor(this, R.color.colorGreen));
                this.orderSucceessImage.setImageResource(R.drawable.ic_circle_tick);
                this.dotLineImg.setBackgroundResource(R.drawable.ic_line);
            }
            this.currency = ((Item) bundle.getItems().get(0)).getProduct().getPrices().getCurrency();
            if (this.itemQuantity == 1) {
                textView = this.orderItemTxt;
                stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf(this.itemQuantity));
                stringBuilder.append(" Item, ");
                stringBuilder.append(this.currency);
                stringBuilder.append(String.valueOf(this.priceAmount));
                textView.setText(stringBuilder.toString());
            } else {
                textView = this.orderItemTxt;
                stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf(this.itemQuantity));
                stringBuilder.append(" Items, ");
                stringBuilder.append(this.currency);
                stringBuilder.append(String.valueOf(this.priceAmount));
                textView.setText(stringBuilder.toString());
            }
            this.restaurantName.setText(bundle.getShop().getName());
            this.restaurantAddress.setText(bundle.getShop().getAddress());
            this.userAddressTitle.setText(bundle.getAddress().getType());
            this.userAddress.setText(bundle.getAddress().getMapAddress());
            this.orderFullViewFragment = new OrderViewFragment();
            this.fragmentManager = getSupportFragmentManager();
            this.fragmentManager.beginTransaction().add((int) R.id.order_detail_fargment, this.orderFullViewFragment).commit();
        }
    }

    private String getFormatTime(String str) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time : ");
        stringBuilder.append(str);
        printStream.println(stringBuilder.toString());
        String str2 = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.getDefault());
            if (str != null) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
            return str2;
        } catch (String str3) {
            str3.printStackTrace();
            return str2;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    /* renamed from: com.entriver.orderaround.activities.PastOrderDetailActivity$1 */
    class C07511 implements OnClickListener {
        C07511() {
        }

        public void onClick(View view) {
            PastOrderDetailActivity.this.onBackPressed();
        }
    }
}
