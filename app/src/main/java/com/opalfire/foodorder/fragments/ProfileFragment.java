package com.opalfire.foodorder.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.activities.AccountPaymentActivity;
import com.opalfire.foodorder.activities.ChangePasswordActivity;
import com.opalfire.foodorder.activities.EditAccountActivity;
import com.opalfire.foodorder.activities.FavouritesActivity;
import com.opalfire.foodorder.activities.LoginActivity;
import com.opalfire.foodorder.activities.ManageAddressActivity;
import com.opalfire.foodorder.activities.OrdersActivity;
import com.opalfire.foodorder.activities.PromotionActivity;
import com.opalfire.foodorder.activities.WelcomeScreenActivity;
import com.opalfire.foodorder.adapter.ProfileSettingsAdapter;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.helper.SharedHelper;
import com.opalfire.foodorder.utils.ListViewSizeHelper;
import com.opalfire.foodorder.utils.LocaleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {
    private static final int REQUEST_LOCATION = 1450;

    GoogleApiClient mGoogleApiClient;

    TextView userEmail;
    ImageView userImage;
    TextView userName;
    TextView userPhone;
    @BindView(R.id.arrow_image)
    ImageView arrowImage;
    @BindView(R.id.text_line)
    TextView textLine;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.myaccount_layout)
    LinearLayout myaccountLayout;
    @BindView(R.id.profile_setting_lv)
    ListView profileSettingLv;
    @BindView(R.id.list_layout)
    RelativeLayout listLayout;
    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;

    private Activity activity;
    private Context context;
    private ViewGroup toolbar;
    private View toolbarLayout;
    private Unbinder unbinder;

    public static void expand(final View view) {
        view.measure(-1, -2);
        final int measuredHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);
        Animation animation = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                view.getLayoutParams().height = interpolatedTime == 1.0f ? 0 : (int) (measuredHeight * interpolatedTime);
                view.requestLayout();
            }
        };

        animation.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(animation);
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
        context = getContext();
        activity = getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_profile, viewGroup, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    public void onResume() {
        super.onResume();
        HomeActivity.updateNotificationCount(context, GlobalData.notificationCount);
        initView();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        context = context;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (toolbar != null) {
            toolbar.removeView(toolbarLayout);
        }
        unbinder.unbind();
    }

    private void openSettingPage(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(context, ManageAddressActivity.class));
                break;
            case 1:
                startActivity(new Intent(context, FavouritesActivity.class));
                break;
            case 2:
                startActivity(new Intent(context, AccountPaymentActivity.class).putExtra("is_show_wallet", true).putExtra("is_show_cash", false));
                break;
            case 3:
                startActivity(new Intent(context, OrdersActivity.class));
                break;
            case 4:
                startActivity(new Intent(context, PromotionActivity.class));
                break;
            case 5:
                startActivity(new Intent(context, ChangePasswordActivity.class));
                break;
            default:
                break;
        }
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("ProfileFragment");
        toolbar = getActivity().findViewById(R.id.toolbar);
        if (GlobalData.profileModel != null) {
            toolbarLayout = LayoutInflater.from(context).inflate(R.layout.toolbar_profile, toolbar, false);
            userImage = toolbarLayout.findViewById(R.id.user_image);
            userName = toolbarLayout.findViewById(R.id.user_name);
            userPhone = toolbarLayout.findViewById(R.id.user_phone);
            userEmail = toolbarLayout.findViewById(R.id.user_mail);
            initView();
            Button button = toolbarLayout.findViewById(R.id.edit);
            userImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, EditAccountActivity.class));
                }
            });
            button.setOnClickListener(new C08492());
            toolbar.addView(toolbarLayout);
            toolbar.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            List<String> profileSettingArr = Arrays.asList(getResources().getStringArray(R.array.profile_settings));
            List<Integer> arrayList = new ArrayList<>();
            arrayList.add(R.drawable.home);
            arrayList.add(R.drawable.heart);
            arrayList.add(R.drawable.payment);
            arrayList.add(R.drawable.ic_myorders);
            arrayList.add(R.drawable.ic_promotion_details);
            arrayList.add(R.drawable.padlock);
            profileSettingLv.setAdapter(new ProfileSettingsAdapter(context, profileSettingArr, arrayList));
            ListViewSizeHelper.getListViewSize(profileSettingLv);
            profileSettingLv.setOnItemClickListener(new C08503());
            arrowImage.setTag(Boolean.TRUE);
            HomeActivity.updateNotificationCount(context, GlobalData.notificationCount);
            TextView textView = appVersion;
            String appVer = "App version " +
                    "1.0" +
                    " (" +
                    1 +
                    ")";
            textView.setText(appVer);
            return;
        }
        toolbar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    private void changeLanguage() {
        List<String> asList = Arrays.asList(getResources().getStringArray(R.array.languages));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = getLayoutInflater().inflate(R.layout.language_dialog, null);
        builder.setView(inflate);
        builder.setCancelable(true);
        builder.setTitle("Change Language");
        final AlertDialog alertDialog = builder.create();
        final ListView listView = inflate.findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.profile_settings_list_item, asList));
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                setLanguage(listView.getItemAtPosition(i).toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setLanguage(String str) {
        SharedHelper.putKey(getActivity(), "language", str);
        switch (str) {
            case "Arabic":
                LocaleUtils.setLocale(getActivity(), "ar");
                break;
            case "English":
                LocaleUtils.setLocale(getActivity(), "en");
                break;
            case "Spanish":
                LocaleUtils.setLocale(getActivity(), "es");
                break;
            default:
                LocaleUtils.setLocale(getActivity(), "en");
                break;
        }
        startActivity(new Intent(getActivity(), HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("change_language", true));
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void initView() {
        if (GlobalData.profileModel != null) {
            Glide.with(context).load(GlobalData.profileModel.getAvatar()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.man).error(R.drawable.man)).into(userImage);
            userPhone.setText(GlobalData.profileModel.getPhone());
            userName.setText(GlobalData.profileModel.getName());
            String userEmailStr = " - " +
                    GlobalData.profileModel.getEmail();
            userEmail.setText(userEmailStr);
        }
    }

    @OnClick({R.id.login_btn, R.id.logout, R.id.myaccount_layout, R.id.login_btn})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.login_btn) {
            SharedHelper.putKey(context, "logged", "false");
            startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            getActivity().finish();
        } else if (view.getId() == R.id.logout) {
            alertDialog();
        } else if (view.getId() == R.id.myaccount_layout) {
            if (arrowImage.getTag().equals(Boolean.TRUE)) {
                arrowImage.animate().setDuration(500).rotation(180.0f).start();
                arrowImage.setTag(Boolean.FALSE);
                collapse(listLayout);
                viewLine.setVisibility(View.VISIBLE);
                textLine.setVisibility(View.GONE);
                return;
            }
            arrowImage.animate().setDuration(500).rotation(360.0f).start();
            arrowImage.setTag(Boolean.TRUE);
            viewLine.setVisibility(View.GONE);
            textLine.setVisibility(View.VISIBLE);
            expand(listLayout);
        }
    }

    private void signOut() {
        GoogleSignInOptions localGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API, localGoogleSignInOptions).build();
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            public void onConnected(@Nullable Bundle paramAnonymousBundle) {
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status paramAnonymous2Status) {
                            if (paramAnonymous2Status.isSuccess()) {
                                Log.d("MainAct", "Google User Logged out");
                            }
                        }
                    });
                }
            }

            public void onConnectionSuspended(int paramAnonymousInt) {
                Log.d("MAin", "Google API Client Connection Suspended");
            }
        });
    }

    public void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to logout?").setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (SharedHelper.getKey(context, "login_by").equals("facebook")) {
                    LoginManager.getInstance().logOut();
                }
                if (SharedHelper.getKey(context, "login_by").equals("google")) {
                    signOut();
                }
                SharedHelper.putKey(context, "logged", "false");
                startActivity(new Intent(context, WelcomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                GlobalData.profileModel = null;
                GlobalData.addCart = null;
                GlobalData.notificationCount = 0;
                getActivity().finish();
            }
        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button button = alertDialog.getButton(-2);
        button.setTextColor(ContextCompat.getColor(context, R.color.theme));
        button.setTypeface(button.getTypeface(), Typeface.BOLD);
        Button button2 = alertDialog.getButton(-1);
        button2.setTextColor(ContextCompat.getColor(context, R.color.theme));
        button2.setTypeface(button2.getTypeface(), Typeface.BOLD);
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$1 */
    class C08481 implements OnClickListener {
        C08481() {
        }

        public void onClick(View view) {
            startActivity(new Intent(context, EditAccountActivity.class));
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$2 */
    class C08492 implements OnClickListener {
        C08492() {
        }

        public void onClick(View view) {
            startActivity(new Intent(context, EditAccountActivity.class));
        }
    }

    /* renamed from: com.entriver.orderaround.fragments.ProfileFragment$3 */
    class C08503 implements OnItemClickListener {
        C08503() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            openSettingPage(i);
        }
    }

}
