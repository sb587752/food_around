package com.opalfire.orderaround.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.gson.Gson;
import com.orderaround.user.C0709R;
import com.orderaround.user.adapter.FavouritesAdapter;
import com.orderaround.user.build.api.ApiClient;
import com.orderaround.user.build.api.ApiInterface;
import com.orderaround.user.models.Available;
import com.orderaround.user.models.FavListModel;
import com.orderaround.user.models.FavoriteList;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FavouritesActivity extends AppCompatActivity {
    private FavouritesAdapter adapter;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296514)
    LinearLayout errorLayout;
    @BindView(2131296539)
    RecyclerView favoritesRv;
    List<FavListModel> modelList = new ArrayList();
    private List<FavListModel> modelListReference = new ArrayList();
    @BindView(2131296794)
    RelativeLayout rootView;
    private SkeletonScreen skeletonScreen;
    @BindView(2131296914)
    Toolbar toolbar;

    /* renamed from: com.orderaround.user.activities.FavouritesActivity$1 */
    class C07281 implements OnClickListener {
        C07281() {
        }

        public void onClick(View view) {
            FavouritesActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.orderaround.user.activities.FavouritesActivity$2 */
    class C12952 implements Callback<FavoriteList> {
        C12952() {
        }

        public void onResponse(@NonNull Call<FavoriteList> call, @NonNull Response<FavoriteList> response) {
            FavouritesActivity.this.skeletonScreen.hide();
            if (response.isSuccessful() != null) {
                if (((FavoriteList) response.body()).getAvailable().size() == null && ((FavoriteList) response.body()).getUnAvailable().size() == null) {
                    FavouritesActivity.this.errorLayout.setVisibility(null);
                    return;
                }
                FavouritesActivity.this.errorLayout.setVisibility(8);
                call = new FavListModel();
                call.setHeader("available");
                call.setFav(((FavoriteList) response.body()).getAvailable());
                FavouritesActivity.this.modelList.add(call);
                call = new FavListModel();
                call.setHeader("un available");
                List arrayList = new ArrayList();
                for (Object obj : ((FavoriteList) response.body()).getUnAvailable()) {
                    Gson gson = new Gson();
                    arrayList.add((Available) gson.fromJson(gson.toJson(obj), Available.class));
                }
                call.setFav(arrayList);
                FavouritesActivity.this.modelList.add(call);
                FavouritesActivity.this.modelListReference.clear();
                FavouritesActivity.this.modelListReference.addAll(FavouritesActivity.this.modelList);
                FavouritesActivity.this.adapter.notifyDataSetChanged();
            }
        }

        public void onFailure(@NonNull Call<FavoriteList> call, @NonNull Throwable th) {
            FavouritesActivity.this.skeletonScreen.hide();
            Toast.makeText(FavouritesActivity.this, "Something wrong - getFavorites", 1).show();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C0709R.layout.activity_favourites);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) C0709R.drawable.ic_back);
        this.toolbar.setNavigationOnClickListener(new C07281());
        this.favoritesRv.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new FavouritesAdapter(this, this.modelListReference);
        this.favoritesRv.setAdapter(this.adapter);
        getFavorites();
    }

    private void getFavorites() {
        this.skeletonScreen = Skeleton.bind(this.rootView).load(C0709R.layout.skeleton_favorite_list_item).color(C0709R.color.shimmer_color).angle(0).show();
        this.apiInterface.getFavoriteList().enqueue(new C12952());
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(C0709R.anim.anim_nothing, C0709R.anim.slide_out_right);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
