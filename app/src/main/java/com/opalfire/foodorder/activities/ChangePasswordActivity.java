package com.opalfire.foodorder.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.models.ChangePassword;
import com.opalfire.foodorder.utils.TextUtils;

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
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    Context context;
    CustomDialog customDialog;

    String strConfirmPassword;
    String strNewPassword;
    String strOldPassword;
    @BindView(R.id.toolbar_top)
    Toolbar toolbarTop;
    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.old_password_eye_img)
    ImageView oldPasswordEyeImg;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.password_eye_img)
    ImageView passwordEyeImg;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.confirm_password_eye_img)
    ImageView confirmPasswordEyeImg;
    @BindView(R.id.confirm)
    Button confirm;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_change_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        ButterKnife.bind(this);
        context = this;
        passwordEyeImg.setTag(1);
        confirmPasswordEyeImg.setTag(1);
        oldPasswordEyeImg.setTag(1);
        setSupportActionBar(toolbarTop);
        toolbarTop.setNavigationIcon(R.drawable.ic_back);
        toolbarTop.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @OnClick({R.id.old_password_eye_img, R.id.password_eye_img, R.id.confirm_password_eye_img, R.id.confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.confirm) {
            initValues();
        } else if (view.getId() != R.id.confirm_password_eye_img) {
            if (view.getId() != R.id.old_password_eye_img) {
                if (view.getId() == R.id.password_eye_img) {
                    if (passwordEyeImg.getTag().equals(1)) {
                        newPassword.setTransformationMethod(null);
                        passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_close));
                        passwordEyeImg.setTag(0);
                        return;
                    }
                    passwordEyeImg.setTag(1);
                    newPassword.setTransformationMethod(new PasswordTransformationMethod());
                    passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_open));
                }
            } else if (oldPasswordEyeImg.getTag().equals(1)) {
                oldPassword.setTransformationMethod(null);
                oldPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_close));
                oldPasswordEyeImg.setTag(0);
            } else {
                oldPasswordEyeImg.setTag(1);
                oldPassword.setTransformationMethod(new PasswordTransformationMethod());
                oldPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_open));
            }
        } else if (confirmPasswordEyeImg.getTag().equals(1)) {
            confirmPassword.setTransformationMethod(null);
            confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_close));
            confirmPasswordEyeImg.setTag(0);
        } else {
            confirmPasswordEyeImg.setTag(1);
            confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_open));
        }
    }

    private void initValues() {
        strOldPassword = oldPassword.getText().toString();
        strConfirmPassword = confirmPassword.getText().toString();
        strNewPassword = newPassword.getText().toString();
        if (TextUtils.isEmpty(strOldPassword)) {
            Toast.makeText(this, "Please enter old password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strNewPassword)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strConfirmPassword)) {
            Toast.makeText(this, "Please confirm password", Toast.LENGTH_SHORT).show();
        } else if (strConfirmPassword.equalsIgnoreCase(strNewPassword)) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("password_old", strOldPassword);
            hashMap.put("password", strNewPassword);
            hashMap.put("password_confirmation", strConfirmPassword);
            changePassword(hashMap);
        } else {
            Toast.makeText(this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
        }
    }

    private void changePassword(HashMap<String, String> hashMap) {
        customDialog = new CustomDialog(context);
        customDialog.show();
        apiInterface.changePassword(hashMap).enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(@NonNull Call<ChangePassword> call, @NonNull Response<ChangePassword> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject(response.errorBody().string());
                    if (jSONObject.has("password")) {
                        Toast.makeText(context, jSONObject.optJSONArray("password").get(0).toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, jSONObject.optString("error"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<ChangePassword> call, @NonNull Throwable t) {
                customDialog.dismiss();
                Toast.makeText(context, getResources().getString(R.string.something_went_wrong), 0).show();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

}
