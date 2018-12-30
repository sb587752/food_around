package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opalfire.orderaround.HomeActivity;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.ResetPassword;
import com.opalfire.orderaround.utils.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPasswordActivity extends AppCompatActivity {
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296406)
    Button changeBtn;
    @BindView(2131296433)
    EditText confirmPassword;
    @BindView(2131296434)
    ImageView confirmPasswordEyeImg;
    Context context;
    CustomDialog customDialog;
    @BindView(2131296621)
    LinearLayout linearLayout;
    @BindView(2131296665)
    EditText newPassword;
    @BindView(2131296719)
    ImageView passwordEyeImg;
    @BindView(2131296845)
    LinearLayout siginLayout;
    @BindView(2131296847)
    TextView signInHere;
    String strConfirmPassword;
    String strNewPassword;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_reset_password);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.customDialog = new CustomDialog(this.context);
        this.passwordEyeImg.setTag(Integer.valueOf(1));
        this.confirmPasswordEyeImg.setTag(Integer.valueOf(1));
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @OnClick({2131296406, 2131296847, 2131296719, 2131296434})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.change_btn) {
            initValues();
        } else if (view != R.id.confirm_password_eye_img) {
            if (view == R.id.password_eye_img) {
                if (this.passwordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
                    this.newPassword.setTransformationMethod(null);
                    this.passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close));
                    this.passwordEyeImg.setTag(Integer.valueOf(0));
                    return;
                }
                this.passwordEyeImg.setTag(Integer.valueOf(1));
                this.newPassword.setTransformationMethod(new PasswordTransformationMethod());
                this.passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open));
            }
        } else if (this.confirmPasswordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
            this.confirmPassword.setTransformationMethod(null);
            this.confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close));
            this.confirmPasswordEyeImg.setTag(Integer.valueOf(0));
        } else {
            this.confirmPasswordEyeImg.setTag(Integer.valueOf(1));
            this.confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            this.confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open));
        }
    }

    private void initValues() {
        this.strConfirmPassword = this.confirmPassword.getText().toString();
        this.strNewPassword = this.newPassword.getText().toString();
        if (TextUtils.isEmpty(this.strNewPassword)) {
            Toast.makeText(this, "Please enter password", 0).show();
        } else if (TextUtils.isEmpty(this.strConfirmPassword)) {
            Toast.makeText(this, "Please confirm password", 0).show();
        } else if (this.strConfirmPassword.equalsIgnoreCase(this.strNewPassword)) {
            HashMap hashMap = new HashMap();
            hashMap.put("id", String.valueOf(GlobalData.profileModel.getId()));
            hashMap.put("password", this.strNewPassword);
            hashMap.put("password_confirmation", this.strConfirmPassword);
            resetPassword(hashMap);
        } else {
            Toast.makeText(this, "Password and confirm password doesn't match", 0).show();
        }
    }

    private void resetPassword(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.resetPassword(hashMap).enqueue(new C13431());
    }

    /* renamed from: com.entriver.orderaround.activities.ResetPasswordActivity$1 */
    class C13431 implements Callback<ResetPassword> {
        C13431() {
        }

        public void onFailure(@NonNull Call<ResetPassword> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<ResetPassword> call, @NonNull Response<ResetPassword> response) {
            ResetPasswordActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                Toast.makeText(ResetPasswordActivity.this, ((ResetPassword) response.body()).getMessage(), 0).show();
                ResetPasswordActivity.this.startActivity(new Intent(ResetPasswordActivity.this.context, HomeActivity.class));
                ResetPasswordActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                return;
            }
            try {
                Toast.makeText(ResetPasswordActivity.this.context, new JSONObject(response.errorBody().string()).optString("error"), 1).show();
            } catch (Response<ResetPassword> response2) {
                Toast.makeText(ResetPasswordActivity.this.context, response2.getMessage(), 1).show();
            }
        }
    }
}
