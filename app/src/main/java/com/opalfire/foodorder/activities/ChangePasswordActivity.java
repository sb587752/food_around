package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodorder.user.R;
import com.foodorder.user.build.api.ApiClient;
import com.foodorder.user.build.api.ApiInterface;
import com.foodorder.user.helper.CustomDialog;
import com.foodorder.user.models.ChangePassword;
import com.foodorder.user.utils.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends AppCompatActivity {
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296432)
    Button confirm;
    @BindView(2131296433)
    EditText confirmPassword;
    @BindView(2131296434)
    ImageView confirmPasswordEyeImg;
    Context context;
    CustomDialog customDialog;
    @BindView(2131296665)
    EditText newPassword;
    @BindView(2131296681)
    EditText oldPassword;
    @BindView(2131296682)
    ImageView oldPasswordEyeImg;
    @BindView(2131296719)
    ImageView passwordEyeImg;
    String strConfirmPassword;
    String strNewPassword;
    String strOldPassword;
    @BindView(2131296917)
    Toolbar toolbarTop;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_change_password);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.passwordEyeImg.setTag(Integer.valueOf(1));
        this.confirmPasswordEyeImg.setTag(Integer.valueOf(1));
        this.oldPasswordEyeImg.setTag(Integer.valueOf(1));
        setSupportActionBar(this.toolbarTop);
        this.toolbarTop.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbarTop.setNavigationOnClickListener(new C07171());
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @OnClick({2131296682, 2131296719, 2131296434, 2131296432})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.confirm) {
            initValues();
        } else if (view != R.id.confirm_password_eye_img) {
            if (view != R.id.old_password_eye_img) {
                if (view == R.id.password_eye_img) {
                    if (this.passwordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
                        this.newPassword.setTransformationMethod(null);
                        this.passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_eye_close));
                        this.passwordEyeImg.setTag(Integer.valueOf(0));
                        return;
                    }
                    this.passwordEyeImg.setTag(Integer.valueOf(1));
                    this.newPassword.setTransformationMethod(new PasswordTransformationMethod());
                    this.passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_eye_open));
                }
            } else if (this.oldPasswordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
                this.oldPassword.setTransformationMethod(null);
                this.oldPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_eye_close));
                this.oldPasswordEyeImg.setTag(Integer.valueOf(0));
            } else {
                this.oldPasswordEyeImg.setTag(Integer.valueOf(1));
                this.oldPassword.setTransformationMethod(new PasswordTransformationMethod());
                this.oldPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_eye_open));
            }
        } else if (this.confirmPasswordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
            this.confirmPassword.setTransformationMethod(null);
            this.confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_eye_close));
            this.confirmPasswordEyeImg.setTag(Integer.valueOf(0));
        } else {
            this.confirmPasswordEyeImg.setTag(Integer.valueOf(1));
            this.confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            this.confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_eye_open));
        }
    }

    private void initValues() {
        this.strOldPassword = this.oldPassword.getText().toString();
        this.strConfirmPassword = this.confirmPassword.getText().toString();
        this.strNewPassword = this.newPassword.getText().toString();
        if (TextUtils.isEmpty(this.strOldPassword)) {
            Toast.makeText(this, "Please enter old password", 0).show();
        } else if (TextUtils.isEmpty(this.strNewPassword)) {
            Toast.makeText(this, "Please enter password", 0).show();
        } else if (TextUtils.isEmpty(this.strConfirmPassword)) {
            Toast.makeText(this, "Please confirm password", 0).show();
        } else if (this.strConfirmPassword.equalsIgnoreCase(this.strNewPassword)) {
            HashMap hashMap = new HashMap();
            hashMap.put("password_old", this.strOldPassword);
            hashMap.put("password", this.strNewPassword);
            hashMap.put("password_confirmation", this.strConfirmPassword);
            changePassword(hashMap);
        } else {
            Toast.makeText(this, "Password and confirm password doesn't match", 0).show();
        }
    }

    private void changePassword(HashMap<String, String> hashMap) {
        this.customDialog = new CustomDialog(this.context);
        this.customDialog.show();
        this.apiInterface.changePassword(hashMap).enqueue(new C12842());
    }

    /* renamed from: com.foodorder.user.activities.ChangePasswordActivity$1 */
    class C07171 implements OnClickListener {
        C07171() {
        }

        public void onClick(View view) {
            ChangePasswordActivity.this.onBackPressed();
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    /* renamed from: com.foodorder.user.activities.ChangePasswordActivity$2 */
    class C12842 implements Callback<ChangePassword> {
        C12842() {
        }

        public void onResponse(@NonNull Call<ChangePassword> call, @NonNull Response<ChangePassword> response) {
            ChangePasswordActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                Toast.makeText(ChangePasswordActivity.this.context, ((ChangePassword) response.body()).getMessage(), 0).show();
                ChangePasswordActivity.this.finish();
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(response.errorBody().string());
                if (jSONObject.has("password") != null) {
                    Toast.makeText(ChangePasswordActivity.this.context, jSONObject.optJSONArray("password").get(0).toString(), 1).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this.context, jSONObject.optString("error"), 1).show();
                }
            } catch (Response<ChangePassword> response2) {
                Toast.makeText(ChangePasswordActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<ChangePassword> call, @NonNull Throwable th) {
            ChangePasswordActivity.this.customDialog.dismiss();
            Toast.makeText(ChangePasswordActivity.this.context, ChangePasswordActivity.this.getResources().getString(R.string.something_went_wrong), 0).show();
        }
    }
}
