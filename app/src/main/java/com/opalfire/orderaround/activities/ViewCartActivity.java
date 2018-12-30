package com.opalfire.orderaround.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.fragments.CartFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ViewCartActivity extends AppCompatActivity {
    private Fragment fragment;
    private FragmentManager fragmentManager;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_view_cart);
        this.fragmentManager = getSupportFragmentManager();
        this.fragment = new CartFragment();
        this.fragmentManager.beginTransaction().add((int) R.id.view_cart_container, this.fragment).commit();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
