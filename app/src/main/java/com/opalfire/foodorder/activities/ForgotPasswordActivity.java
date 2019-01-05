package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.opalfire.foodorder.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPasswordActivity extends AppCompatActivity {
    @BindView(2131296508)
    EditText email;
    @BindView(2131296914)
    Toolbar toolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_forgot_password);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07301());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    /* renamed from: com.opalfire.foodorder.activities.ForgotPasswordActivity$1 */
    class C07301 implements OnClickListener {
        C07301() {
        }

        public void onClick(View view) {
            ForgotPasswordActivity.this.onBackPressed();
        }
    }
}
