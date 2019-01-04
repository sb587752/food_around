package com.opalfire.foodorder.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.ConnectionHelper;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.User;
import com.opalfire.foodorder.utils.TextUtils;
import com.opalfire.foodorder.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditAccountActivity extends AppCompatActivity {
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    String TAG = "EditAccountActivity";
    Activity activity;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    ConnectionHelper connectionHelper;
    Context context;
    CustomDialog customDialog;
    String device_UDID;
    String device_token;

    Utils utils = new Utils();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_profile)
    CircleImageView userProfile;
    @BindView(R.id.edit_user_profile)
    ImageView editUserProfile;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.update)
    Button update;
    File imgFile;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_edit_account);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind(this);
        customDialog = new CustomDialog(this);
        context = this;
        activity = this;
        initProfile();
        connectionHelper = new ConnectionHelper(context);
        if (connectionHelper.isConnectingToInternet()) {
            getProfile();
        } else {
            Utils.displayMessage(activity, context, getString(R.string.oops_connect_your_internet));
        }
        getProfile();
        getDeviceToken();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initProfile() {
        if (GlobalData.profileModel != null) {
            name.setText(GlobalData.profileModel.getName());
            email.setText(GlobalData.profileModel.getEmail());
            phone.setText(GlobalData.profileModel.getPhone());
            System.out.println(GlobalData.profileModel.getAvatar());
            Glide.with(context).load(GlobalData.profileModel.getAvatar()).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).
                            placeholder(R.drawable.man).error(R.drawable.man))
                    .into(userProfile);
        }
    }

    @SuppressLint("HardwareIds")
    public void getDeviceToken() {
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
        r4 = this;
        r0 = r4.mContext;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.entriver.foodorder.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        r1 = "";	 Catch:{ Exception -> 0x0091 }
        r0 = r0.equals(r1);	 Catch:{ Exception -> 0x0091 }
        if (r0 != 0) goto L_0x003d;	 Catch:{ Exception -> 0x0091 }
    L_0x0010:
        r0 = r4.mContext;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.entriver.foodorder.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        if (r0 == 0) goto L_0x003d;	 Catch:{ Exception -> 0x0091 }
    L_0x001a:
        r0 = r4.mContext;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.entriver.foodorder.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        r4.device_token = r0;	 Catch:{ Exception -> 0x0091 }
        r0 = r4.TAG;	 Catch:{ Exception -> 0x0091 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r1.<init>();	 Catch:{ Exception -> 0x0091 }
        r2 = "GCM Registration Token: ";	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r2 = r4.device_token;	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0091 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x0091 }
        goto L_0x009c;	 Catch:{ Exception -> 0x0091 }
    L_0x003d:
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r0.<init>();	 Catch:{ Exception -> 0x0091 }
        r1 = "";	 Catch:{ Exception -> 0x0091 }
        r0.append(r1);	 Catch:{ Exception -> 0x0091 }
        r1 = com.google.firebase.iid.FirebaseInstanceId.getInstance();	 Catch:{ Exception -> 0x0091 }
        r1 = r1.getToken();	 Catch:{ Exception -> 0x0091 }
        r0.append(r1);	 Catch:{ Exception -> 0x0091 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0091 }
        r4.device_token = r0;	 Catch:{ Exception -> 0x0091 }
        r0 = r4.mContext;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r2.<init>();	 Catch:{ Exception -> 0x0091 }
        r3 = "";	 Catch:{ Exception -> 0x0091 }
        r2.append(r3);	 Catch:{ Exception -> 0x0091 }
        r3 = com.google.firebase.iid.FirebaseInstanceId.getInstance();	 Catch:{ Exception -> 0x0091 }
        r3 = r3.getToken();	 Catch:{ Exception -> 0x0091 }
        r2.append(r3);	 Catch:{ Exception -> 0x0091 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0091 }
        com.entriver.foodorder.helper.SharedHelper.putKey(r0, r1, r2);	 Catch:{ Exception -> 0x0091 }
        r0 = r4.TAG;	 Catch:{ Exception -> 0x0091 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r1.<init>();	 Catch:{ Exception -> 0x0091 }
        r2 = "Failed to complete token refresh: ";	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r2 = r4.device_token;	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0091 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x0091 }
        goto L_0x009c;
    L_0x0091:
        r0 = "COULD NOT GET FCM TOKEN";
        r4.device_token = r0;
        r0 = r4.TAG;
        r1 = "Failed to complete token refresh";
        android.util.Log.d(r0, r1);
    L_0x009c:
        r0 = r4.getContentResolver();	 Catch:{ Exception -> 0x00c1 }
        r1 = "android_id";	 Catch:{ Exception -> 0x00c1 }
        r0 = android.provider.Settings.Secure.getString(r0, r1);	 Catch:{ Exception -> 0x00c1 }
        r4.device_UDID = r0;	 Catch:{ Exception -> 0x00c1 }
        r0 = r4.TAG;	 Catch:{ Exception -> 0x00c1 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c1 }
        r1.<init>();	 Catch:{ Exception -> 0x00c1 }
        r2 = "Device UDID:";	 Catch:{ Exception -> 0x00c1 }
        r1.append(r2);	 Catch:{ Exception -> 0x00c1 }
        r2 = r4.device_UDID;	 Catch:{ Exception -> 0x00c1 }
        r1.append(r2);	 Catch:{ Exception -> 0x00c1 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00c1 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x00c1 }
        goto L_0x00d0;
    L_0x00c1:
        r0 = move-exception;
        r1 = "COULD NOT GET UDID";
        r4.device_UDID = r1;
        r0.printStackTrace();
        r0 = r4.TAG;
        r1 = "Failed to complete device UDID";
        android.util.Log.d(r0, r1);
    L_0x00d0:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.foodorder.activities.EditAccountActivity.getDeviceToken():void");
    }

    private void getProfile() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("device_type", "android");
        hashMap.put("device_id", device_UDID);
        hashMap.put("device_token", device_token);
        apiInterface.getProfile(hashMap).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    GlobalData.profileModel = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void updateProfile() {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_username), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_your_email), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isValidEmail(email.getText().toString())) {
            if (customDialog != null) {
                customDialog.show();
            }
            Map<String, RequestBody> hashMap = new HashMap<>();
            hashMap.put("name", RequestBody.create(MediaType.parse("text/plain"), name.getText().toString()));
            hashMap.put("email", RequestBody.create(MediaType.parse("text/plain"), email.getText().toString()));
            Part part = null;
            if (imgFile != null) {
                part = Part.createFormData("avatar", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile));
            }
            apiInterface.updateProfileWithImage(hashMap, part).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        GlobalData.profileModel = response.body();
                        finish();
                        Toast.makeText(context, getResources().getString(R.string.profile_updated_successfully), Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        Toast.makeText(context, new JSONObject(Objects.requireNonNull(response.errorBody()).toString()).optString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_email), Toast.LENGTH_LONG).show();
        }
    }

    public void goToImageIntent() {
        startActivityForResult(
                new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == PICK_IMAGE_REQUEST && i2 == -1 && intent != null && intent.getData() != null) {
            String[] str = new String[]{"_data"};
            Cursor cursor = getContentResolver().query(intent.getData(), str, null, null, null);
            cursor.moveToFirst();
            String string = cursor.getString(cursor.getColumnIndex(str[i]));
            cursor.close();
            Glide.with(this).load(string)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.man).error(R.drawable.man))
                    .into(editUserProfile);
            imgFile = new File(string);
        }
    }

    @OnClick({R.id.edit_user_profile, R.id.update})
    public void onViewClicked(View view) {
        if (view.getId() != R.id.edit_user_profile) {
            if (view.getId() == R.id.update) {
                if (connectionHelper.isConnectingToInternet()) {
                    updateProfile();
                } else {
                    Utils.displayMessage(activity, context, getString(R.string.oops_connect_your_internet));
                }
            }
        } else if (VERSION.SDK_INT < 23) {
            goToImageIntent();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") > 0 && ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") > 0) {
            goToImageIntent();
        } else {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 0);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 0) {
            if (iArr.length > 0) {
                i = 1;
                strArr = iArr[1] == null ? 1 : null;
                if (iArr[0] != null) {
                    i = 0;
                }
                if (strArr == null || r2 == 0) {
                    Snackbar.make(findViewById(16908290), (CharSequence) "Please Grant Permissions to upload Profile", (int) -2).setAction((CharSequence) "ENABLE", new C07274()).show();
                } else {
                    goToImageIntent();
                }
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.EditAccountActivity$1 */
    class C07261 implements OnClickListener {
        C07261() {
        }

        public void onClick(View view) {
            EditAccountActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.EditAccountActivity$4 */
    class C07274 implements OnClickListener {
        C07274() {
        }

        public void onClick(View view) {
            ActivityCompat.requestPermissions(EditAccountActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 0);
        }
    }

    /* renamed from: com.entriver.foodorder.activities.EditAccountActivity$2 */
    class C12912 implements Callback<User> {
        C12912() {
        }

        public void onFailure(@NonNull Call<User> call, @NonNull Throwable th) {
        }
        if (paramArrayOfInt.length > 0) {
            int i = 1;
            if (paramArrayOfInt[1] == 0) {
                paramInt = 1;
            } else {
                paramInt = 0;
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.EditAccountActivity$3 */
    class C12923 implements Callback<User> {
        C12923() {
        }

        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            EditAccountActivity.this.customDialog.cancel();
            if (response.isSuccessful() != null) {
                GlobalData.profileModel = (User) response.body();
                EditAccountActivity.this.finish();
                Toast.makeText(EditAccountActivity.this.context, EditAccountActivity.this.getResources().getString(R.string.profile_updated_successfully), 0).show();
                return;
            }
            if ((paramInt != 0) && (i != 0)) {
                goToImageIntent();
                return;
            }
            Snackbar.make(findViewById(android.R.id.content), "Please Grant Permissions to upload Profile", -2).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View paramAnonymousView) {
                    ActivityCompat.requestPermissions(EditAccountActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
                }
            }).show();
        }
    }


}
