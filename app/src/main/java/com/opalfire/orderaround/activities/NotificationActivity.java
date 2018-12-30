package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.adapter.NotificationAdapter;
import com.opalfire.orderaround.models.NotificationItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NotificationActivity extends AppCompatActivity {
    Context context;
    @BindView(2131296674)
    RecyclerView notificationRv;
    @BindView(2131296914)
    Toolbar toolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_notification);
        ButterKnife.bind((Activity) this);
        this.context = this;
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07441());
        this.toolbar.setContentInsetsAbsolute(this.toolbar.getContentInsetLeft(), 0);
        bundle = new ArrayList();
        bundle.add(new NotificationItem("10% offer for veg orders", "Sep 08,2017", "Use Code AD123"));
        bundle.add(new NotificationItem("5% offer for Non-veg orders", "Sep 15,2017", "Use Code NV124"));
        this.notificationRv.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
        this.notificationRv.setItemAnimator(new DefaultItemAnimator());
        this.notificationRv.setHasFixedSize(true);
        this.notificationRv.setAdapter(new NotificationAdapter(bundle, this.context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    /* renamed from: com.entriver.orderaround.activities.NotificationActivity$1 */
    class C07441 implements OnClickListener {
        C07441() {
        }

        public void onClick(View view) {
            NotificationActivity.this.onBackPressed();
        }
    }
}
