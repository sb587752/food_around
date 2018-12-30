package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.opalfire.foodorder.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecommendedListActivity extends AppCompatActivity {
    @BindView(2131296602)
    TextView itemTxt;
    @BindView(2131296393)
    LinearLayout ll;
    @BindView(2131296683)
    TextView openCartTxt;
    @BindView(2131296768)
    ListView recommendedDishesLv;
    @BindView(2131296899)
    TextView titleTxt;
    @BindView(2131296914)
    Toolbar toolbar;

    @OnClick({2131296683})
    public void onViewClicked() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_recommended_list);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_close);
        this.toolbar.setNavigationOnClickListener(new C07571());
    }

    public void addItemToCart() {
        this.ll.setVisibility(View.VISIBLE);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    /* renamed from: com.entriver.orderaround.activities.RecommendedListActivity$1 */
    class C07571 implements OnClickListener {
        C07571() {
        }

        public void onClick(View view) {
            RecommendedListActivity.this.finish();
        }
    }
}
