package com.opalfire.orderaround.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.opalfire.orderaround.HomeActivity;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.AccountPaymentActivity;
import com.opalfire.orderaround.activities.ChangePasswordActivity;
import com.opalfire.orderaround.activities.EditAccountActivity;
import com.opalfire.orderaround.activities.FavouritesActivity;
import com.opalfire.orderaround.activities.LoginActivity;
import com.opalfire.orderaround.activities.ManageAddressActivity;
import com.opalfire.orderaround.activities.OrdersActivity;
import com.opalfire.orderaround.activities.PromotionActivity;
import com.opalfire.orderaround.activities.WelcomeScreenActivity;
import com.opalfire.orderaround.adapter.ProfileSettingsAdapter;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.helper.SharedHelper;
import com.opalfire.orderaround.utils.ListViewSizeHelper;
import com.opalfire.orderaround.utils.LocaleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment {
    private static final int REQUEST_LOCATION = 1450;
    @BindView(2131296326)
    TextView appVersion;
    @BindView(2131296328)
    ImageView arrowImage;
    @BindView(2131296514)
    RelativeLayout errorLayout;
    @BindView(2131296624)
    RelativeLayout listLayout;
    @BindView(2131296631)
    Button loginBtn;
    @BindView(2131296633)
    Button logout;
    GoogleApiClient mGoogleApiClient;
    @BindView(2131296658)
    LinearLayout myaccountLayout;
    @BindView(2131296750)
    ListView profileSettingLv;
    @BindView(2131296893)
    TextView textLine;
    TextView userEmail;
    ImageView userImage;
    TextView userName;
    TextView userPhone;
    @BindView(2131296960)
    View viewLine;
    private Activity activity;
    private Context context;
    private ViewGroup toolbar;
    private View toolbarLayout;

    public static void expand(final View view) {
        view.measure(-1, -2);
        final int measuredHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);
        Animation c08525 = new Animation() {
            public boolean willChangeBounds() {
                return true;
            }

            protected void applyTransformation(float f, Transformation transformation) {
                view.getLayoutParams().height = f == 1.0f ? Float.NaN : (int) (((float) measuredHeight) * f);
                view.requestLayout();
            }
        };
        c08525.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(c08525);
    }

    public static void collapse(final View view) {
        final int measuredHeight = view.getMeasuredHeight();
        Animation c08536 = new Animation() {
            public boolean willChangeBounds() {
                return true;
            }

            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 1.0f) {
                    view.setVisibility(View.GONE);
                    return;
                }
                view.getLayoutParams().height = measuredHeight - ((int) (((float) measuredHeight) * f));
                view.requestLayout();
            }
        };
        c08536.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(c08536);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getContext();
        this.activity = getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_profile, viewGroup, false);
        ButterKnife.bind((Object) this, inflate);
        return inflate;
    }

    public void onResume() {
        super.onResume();
        HomeActivity.updateNotificationCount(this.context, GlobalData.notificationCount);
        initView();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.toolbar != null) {
            this.toolbar.removeView(this.toolbarLayout);
        }
    }

    private void openSettingPage(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(this.context, ManageAddressActivity.class));
                break;
            case 1:
                startActivity(new Intent(this.context, FavouritesActivity.class));
                break;
            case 2:
                startActivity(new Intent(this.context, AccountPaymentActivity.class).putExtra("is_show_wallet", true).putExtra("is_show_cash", false));
                break;
            case 3:
                startActivity(new Intent(this.context, OrdersActivity.class));
                break;
            case 4:
                startActivity(new Intent(this.context, PromotionActivity.class));
                break;
            case 5:
                startActivity(new Intent(this.context, ChangePasswordActivity.class));
                break;
            default:
                break;
        }
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("ProfileFragment");
        this.toolbar = (ViewGroup) getActivity().findViewById(R.id.toolbar);
        if (GlobalData.profileModel != null) {
            this.toolbarLayout = LayoutInflater.from(this.context).inflate(R.layout.toolbar_profile, this.toolbar, false);
            this.userImage = (ImageView) this.toolbarLayout.findViewById(R.id.user_image);
            this.userName = (TextView) this.toolbarLayout.findViewById(R.id.user_name);
            this.userPhone = (TextView) this.toolbarLayout.findViewById(R.id.user_phone);
            this.userEmail = (TextView) this.toolbarLayout.findViewById(R.id.user_mail);
            initView();
            Button button = (Button) this.toolbarLayout.findViewById(R.id.edit);
            this.userImage.setOnClickListener(new C08481());
            button.setOnClickListener(new C08492());
            this.toolbar.addView(this.toolbarLayout);
            this.toolbar.setVisibility(View.VISIBLE);
            this.errorLayout.setVisibility(View.GONE);
            bundle = Arrays.asList(getResources().getStringArray(R.array.profile_settings));
            List arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(R.drawable.home));
            arrayList.add(Integer.valueOf(R.drawable.heart));
            arrayList.add(Integer.valueOf(R.drawable.payment));
            arrayList.add(Integer.valueOf(R.drawable.ic_myorders));
            arrayList.add(Integer.valueOf(R.drawable.ic_promotion_details));
            arrayList.add(Integer.valueOf(R.drawable.padlock));
            this.profileSettingLv.setAdapter(new ProfileSettingsAdapter(this.context, bundle, arrayList));
            ListViewSizeHelper.getListViewSize(this.profileSettingLv);
            this.profileSettingLv.setOnItemClickListener(new C08503());
            this.arrowImage.setTag(Boolean.valueOf(true));
            HomeActivity.updateNotificationCount(this.context, GlobalData.notificationCount);
            TextView textView = this.appVersion;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("App version ");
            stringBuilder.append("1.0");
            stringBuilder.append(" (");
            stringBuilder.append(String.valueOf(1));
            stringBuilder.append(")");
            textView.setText(stringBuilder.toString());
            return;
        }
        this.toolbar.setVisibility(View.GONE);
        this.errorLayout.setVisibility(View.VISIBLE);
    }

    private void changeLanguage() {
        List asList = Arrays.asList(getResources().getStringArray(R.array.languages));
        Builder builder = new Builder(getActivity());
        View inflate = getLayoutInflater().inflate(R.layout.language_dialog, null);
        builder.setView(inflate);
        builder.setCancelable(true);
        builder.setTitle("Change Language");
        final AlertDialog create = builder.create();
        final ListView listView = (ListView) inflate.findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter(getActivity(), 17367055, asList));
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ProfileFragment.this.setLanguage(listView.getItemAtPosition(i).toString());
                create.dismiss();
            }
        });
        create.show();
    }

    private void setLanguage(String str) {
        SharedHelper.putKey(getActivity(), "language", str);
        int hashCode = str.hashCode();
        if (hashCode != -347177772) {
            if (hashCode != 60895824) {
                if (hashCode == 1969163468) {
                    if (str.equals("Arabic") != null) {
                        str = true;
                        switch (str) {
                            case null:
                                LocaleUtils.setLocale(getActivity(), "en");
                                break;
                            case 1:
                                LocaleUtils.setLocale(getActivity(), "ar");
                                break;
                            case 2:
                                LocaleUtils.setLocale(getActivity(), "es");
                                break;
                            default:
                                LocaleUtils.setLocale(getActivity(), "en");
                                break;
                        }
                        startActivity(new Intent(getActivity(), HomeActivity.class).addFlags(67108864).putExtra("change_language", true));
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            } else if (str.equals("English") != null) {
                str = null;
                switch (str) {
                    case null:
                        LocaleUtils.setLocale(getActivity(), "en");
                        break;
                    case 1:
                        LocaleUtils.setLocale(getActivity(), "ar");
                        break;
                    case 2:
                        LocaleUtils.setLocale(getActivity(), "es");
                        break;
                    default:
                        LocaleUtils.setLocale(getActivity(), "en");
                        break;
                }
                startActivity(new Intent(getActivity(), HomeActivity.class).addFlags(67108864).putExtra("change_language", true));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        } else if (str.equals("Spanish") != null) {
            str = 2;
            switch (str) {
                case null:
                    LocaleUtils.setLocale(getActivity(), "en");
                    break;
                case 1:
                    LocaleUtils.setLocale(getActivity(), "ar");
                    break;
                case 2:
                    LocaleUtils.setLocale(getActivity(), "es");
                    break;
                default:
                    LocaleUtils.setLocale(getActivity(), "en");
                    break;
            }
            startActivity(new Intent(getActivity(), HomeActivity.class).addFlags(67108864).putExtra("change_language", true));
            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        str = -1;
        switch (str) {
            case null:
                LocaleUtils.setLocale(getActivity(), "en");
                break;
            case 1:
                LocaleUtils.setLocale(getActivity(), "ar");
                break;
            case 2:
                LocaleUtils.setLocale(getActivity(), "es");
                break;
            default:
                LocaleUtils.setLocale(getActivity(), "en");
                break;
        }
        startActivity(new Intent(getActivity(), HomeActivity.class).addFlags(67108864).putExtra("change_language", true));
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void initView() {
        if (GlobalData.profileModel != null) {
            Glide.with(this.context).load(GlobalData.profileModel.getAvatar()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder((int) R.drawable.man).error((int) R.drawable.man)).into(this.userImage);
            this.userPhone.setText(GlobalData.profileModel.getPhone());
            this.userName.setText(GlobalData.profileModel.getName());
            TextView textView = this.userEmail;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" - ");
            stringBuilder.append(GlobalData.profileModel.getEmail());
            textView.setText(stringBuilder.toString());
        }
    }

    @OnClick({2131296328, 2131296633, 2131296658, 2131296631})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.login_btn) {
            SharedHelper.putKey(this.context, "logged", "false");
            startActivity(new Intent(this.context, LoginActivity.class).addFlags(67108864));
            getActivity().finish();
        } else if (view == R.id.logout) {
            alertDialog();
        } else if (view == R.id.myaccount_layout) {
            if (this.arrowImage.getTag().equals(Boolean.valueOf(true)) != null) {
                this.arrowImage.animate().setDuration(500).rotation(180.0f).start();
                this.arrowImage.setTag(Boolean.valueOf(false));
                collapse(this.listLayout);
                this.viewLine.setVisibility(View.VISIBLE);
                this.textLine.setVisibility(View.GONE);
                return;
            }
            this.arrowImage.animate().setDuration(500).rotation(360.0f).start();
            this.arrowImage.setTag(Boolean.valueOf(true));
            this.viewLine.setVisibility(View.GONE);
            this.textLine.setVisibility(View.VISIBLE);
            expand(this.listLayout);
        }
    }

    private void signOut() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        this.mGoogleApiClient.connect();
        this.mGoogleApiClient.registerConnectionCallbacks(new C14057());
    }

    public void alertDialog() {
        Builder builder = new Builder(this.context);
        builder.setMessage("Are you sure you want to logout?").setPositiveButton(getResources().getString(R.string.logout), new C08559()).setNegativeButton(getResources().getString(R.string.cancel), new C08548());
        AlertDialog create = builder.create();
        create.show();
        Button button = create.getButton(-2);
        button.setTextColor(ContextCompat.getColor(this.context, R.color.theme));
        button.setTypeface(button.getTypeface(), 1);
        Button button2 = create.getButton(-1);
        button2.setTextColor(ContextCompat.getColor(this.context, R.color.theme));
        button2.setTypeface(button2.getTypeface(), 1);
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$1 */
    class C08481 implements OnClickListener {
        C08481() {
        }

        public void onClick(View view) {
            ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.context, EditAccountActivity.class));
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$2 */
    class C08492 implements OnClickListener {
        C08492() {
        }

        public void onClick(View view) {
            ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.context, EditAccountActivity.class));
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$3 */
    class C08503 implements OnItemClickListener {
        C08503() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ProfileFragment.this.openSettingPage(i);
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$8 */
    class C08548 implements DialogInterface.OnClickListener {
        C08548() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$9 */
    class C08559 implements DialogInterface.OnClickListener {
        C08559() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (SharedHelper.getKey(ProfileFragment.this.context, "login_by").equals("facebook") != null) {
                LoginManager.getInstance().logOut();
            }
            if (SharedHelper.getKey(ProfileFragment.this.context, "login_by").equals("google") != null) {
                ProfileFragment.this.signOut();
            }
            SharedHelper.putKey(ProfileFragment.this.context, "logged", "false");
            ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.context, WelcomeScreenActivity.class).addFlags(67108864));
            GlobalData.profileModel = null;
            GlobalData.addCart = null;
            GlobalData.notificationCount = null;
            ProfileFragment.this.getActivity().finish();
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$7 */
    class C14057 implements ConnectionCallbacks {

        C14057() {
        }

        public void onConnected(@Nullable Bundle bundle) {
            if (ProfileFragment.this.mGoogleApiClient.isConnected() != null) {
                Auth.GoogleSignInApi.signOut(ProfileFragment.this.mGoogleApiClient).setResultCallback(new C14041());
            }
        }

        public void onConnectionSuspended(int i) {
            Log.d("MAin", "Google API Client Connection Suspended");
        }

        /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$7$1 */
        class C14041 implements ResultCallback<Status> {
            C14041() {
            }

            public void onResult(@NonNull Status status) {
                if (status.isSuccess() != null) {
                    Log.d("MainAct", "Google User Logged out");
                }
            }
        }
    }
}
