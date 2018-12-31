package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    String TAG = "EditAccountActivity";
    Activity activity;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    ConnectionHelper connectionHelper;
    Context context;
    CustomDialog customDialog;
    String device_UDID;
    String device_token;
    @BindView(2131296507)
    ImageView editUserProfileImg;
    @BindView(2131296508)
    EditText email;
    File imgFile;
    @BindView(2131296659)
    EditText name;
    @BindView(2131296729)
    EditText phone;
    @BindView(2131296914)
    Toolbar toolbar;
    @BindView(2131296940)
    Button updateBtn;
    @BindView(2131296949)
    CircleImageView userProfileImg;
    Utils utils = new Utils();
    private int PICK_IMAGE_REQUEST = 1;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_edit_account);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        this.customDialog = new CustomDialog(this);
        this.context = this;
        this.activity = this;
        initProfile();
        this.connectionHelper = new ConnectionHelper(this.context);
        if (this.connectionHelper.isConnectingToInternet() != null) {
            getProfile();
        } else {
            Utils.displayMessage(this.activity, this.context, getString(R.string.oops_connect_your_internet));
        }
        getProfile();
        getDeviceToken();
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07261());
    }

    private void initProfile() {
        if (GlobalData.profileModel != null) {
            this.name.setText(GlobalData.profileModel.getName());
            this.email.setText(GlobalData.profileModel.getEmail());
            this.phone.setText(GlobalData.profileModel.getPhone());
            System.out.println(GlobalData.profileModel.getAvatar());
            Glide.with(this.context).load(GlobalData.profileModel.getAvatar()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.man).error((int) R.drawable.man)).into(this.userProfileImg);
        }
    }

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
        HashMap hashMap = new HashMap();
        hashMap.put("device_type", "android");
        hashMap.put("device_id", this.device_UDID);
        hashMap.put("device_token", this.device_token);
        this.apiInterface.getProfile(hashMap).enqueue(new C12912());
    }

    private void updateProfile() {
        if (this.name.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_username), 0).show();
        } else if (TextUtils.isEmpty(this.email.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_your_email), 0).show();
        } else if (TextUtils.isValidEmail(this.email.getText().toString())) {
            if (this.customDialog != null) {
                this.customDialog.show();
            }
            Map hashMap = new HashMap();
            hashMap.put("name", RequestBody.create(MediaType.parse("text/plain"), this.name.getText().toString()));
            hashMap.put("email", RequestBody.create(MediaType.parse("text/plain"), this.email.getText().toString()));
            Part part = null;
            if (this.imgFile != null) {
                part = Part.createFormData("avatar", this.imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), this.imgFile));
            }
            this.apiInterface.updateProfileWithImage(hashMap, part).enqueue(new C12923());
        } else {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_email), 0).show();
        }
    }

    public void goToImageIntent() {
        startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), this.PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.PICK_IMAGE_REQUEST && i2 == -1 && intent != null && intent.getData() != 0) {
            i = new String[]{"_data"};
            i2 = getContentResolver().query(intent.getData(), i, null, null, null);
            i2.moveToFirst();
            String string = i2.getString(i2.getColumnIndex(i[null]));
            i2.close();
            Glide.with((FragmentActivity) this).load(string).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.man).error((int) R.drawable.man)).into(this.userProfileImg);
            this.imgFile = new File(string);
        }
    }

    @OnClick({2131296507, 2131296940})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view != R.id.edit_user_profile) {
            if (view == R.id.update) {
                if (this.connectionHelper.isConnectingToInternet() != null) {
                    updateProfile();
                } else {
                    Utils.displayMessage(this.activity, this.context, getString(R.string.oops_connect_your_internet));
                }
            }
        } else if (VERSION.SDK_INT < 23) {
            goToImageIntent();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == null && ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == null) {
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

        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            if (response.isSuccessful() != null) {
                GlobalData.profileModel = (User) response.body();
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
            try {
                Toast.makeText(EditAccountActivity.this.context, new JSONObject(response.errorBody().toString()).optString("error"), 1).show();
            } catch (Response<User> response2) {
                Toast.makeText(EditAccountActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<User> call, @NonNull Throwable th) {
            EditAccountActivity.this.customDialog.cancel();
            Toast.makeText(EditAccountActivity.this.context, EditAccountActivity.this.getResources().getString(R.string.network_error_toast), 0).show();
        }
    }
}
