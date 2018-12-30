package com.opalfire.foodorder.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.fragments.AddonBottomSheetFragment;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.Addon;
import com.opalfire.foodorder.models.Cart;
import com.opalfire.foodorder.models.CartAddon;
import com.opalfire.foodorder.models.Product;
import com.opalfire.foodorder.models.Shop;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.PrintStream;
import java.util.List;

public class CartAddOnsAdapter extends Adapter<MyViewHolder> {
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    public static List<Addon> list;
    Runnable action;
    AddCart addCart;
    Addon addon;
    AnimatedVectorDrawableCompat avdProgress;
    boolean dataResponse = false;
    Dialog dialog;
    int discount = 0;
    int itemCount = 0;
    int itemQuantity = 0;
    int priceAmount = 0;
    Cart productList;
    Shop selectedShop = GlobalData.selectedShop;
    private Context context;

    public CartAddOnsAdapter(List<Addon> list, Context context) {
        list = list;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_ons_item, viewGroup, false));
    }

    public void add(Addon addon, int i) {
        list.add(i, addon);
        notifyItemInserted(i);
    }

    public void remove(Cart cart) {
        cart = list.indexOf(cart);
        list.remove(cart);
        notifyItemRemoved(cart);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        this.addon = (Addon) list.get(i);
        myViewHolder.cardTextValueTicker.setCharacterList(NUMBER_LIST);
        this.addon.setQuantity(Integer.valueOf(1));
        myViewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
        myViewHolder.cardAddDetailLayout.setVisibility(View.GONE);
        myViewHolder.cardTextValue.setText(this.addon.getQuantity().toString());
        myViewHolder.cardTextValueTicker.setText(this.addon.getQuantity().toString());
        this.addon.getAddon().setChecked(false);
        if (GlobalData.cartAddons != null) {
            for (CartAddon cartAddon : GlobalData.cartAddons) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("addon ");
                stringBuilder.append(this.addon.getAddon().getId());
                stringBuilder.append(" ==");
                stringBuilder.append(cartAddon.getAddonProduct().getAddon().getId());
                printStream.println(stringBuilder.toString());
                if (this.addon.getAddon().getId().equals(cartAddon.getAddonProduct().getAddon().getId())) {
                    printStream = System.out;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.addon.getAddon().getId());
                    stringBuilder.append(" = ");
                    stringBuilder.append(cartAddon.getAddonProduct().getAddon().getId());
                    printStream.println(stringBuilder.toString());
                    myViewHolder.addonName.setChecked(true);
                    this.addon.setQuantity(cartAddon.getQuantity());
                    this.addon.getAddon().setChecked(true);
                    myViewHolder.cardTextValue.setText(cartAddon.getQuantity().toString());
                    myViewHolder.cardTextValueTicker.setText(cartAddon.getQuantity().toString());
                    myViewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                    myViewHolder.cardAddTextLayout.setVisibility(View.GONE);
                }
            }
        }
        CheckBox checkBox = myViewHolder.addonName;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.addon.getAddon().getName());
        stringBuilder2.append(" ");
        stringBuilder2.append(GlobalData.currencySymbol);
        stringBuilder2.append(((Addon) list.get(i)).getPrice());
        checkBox.setText(stringBuilder2.toString());
        myViewHolder.addonName.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CartAddOnsAdapter.this.addon = (Addon) CartAddOnsAdapter.list.get(i);
                if (z) {
                    myViewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                    myViewHolder.cardAddTextLayout.setVisibility(View.GONE);
                    CartAddOnsAdapter.this.addon.getAddon().setChecked(true);
                    CartAddOnsAdapter.this.addon.setQuantity(Integer.valueOf(1));
                    myViewHolder.cardTextValue.setText(CartAddOnsAdapter.this.addon.getQuantity().toString());
                    myViewHolder.cardTextValueTicker.setText(CartAddOnsAdapter.this.addon.getQuantity().toString());
                    CartAddOnsAdapter.this.setAddOnsText();
                    return;
                }
                myViewHolder.cardAddDetailLayout.setVisibility(View.GONE);
                myViewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
                CartAddOnsAdapter.this.addon.getAddon().setChecked(false);
                CartAddOnsAdapter.this.setAddOnsText();
            }
        });
        myViewHolder.cardAddTextLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CartAddOnsAdapter.this.addon = (Addon) CartAddOnsAdapter.list.get(i);
                myViewHolder.cardAddDetailLayout.setVisibility(View.VISIBLE);
                myViewHolder.cardAddTextLayout.setVisibility(View.GONE);
                myViewHolder.cardTextValue.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                myViewHolder.cardTextValueTicker.setText(AppEventsConstants.EVENT_PARAM_VALUE_YES);
                CartAddOnsAdapter.this.addon.setQuantity(Integer.valueOf(1));
                myViewHolder.addonName.setChecked(true);
                CartAddOnsAdapter.this.addon.getAddon().setChecked(true);
                CartAddOnsAdapter.this.setAddOnsText();
            }
        });
        myViewHolder.cardAddBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Log.e("access_token2", GlobalData.accessToken);
                CartAddOnsAdapter.this.addon = (Addon) CartAddOnsAdapter.list.get(i);
                CartAddOnsAdapter.this.addon.getAddon().setChecked(true);
                view = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) + 1;
                TextView access$100 = myViewHolder.cardTextValue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(view);
                access$100.setText(stringBuilder.toString());
                TickerView tickerView = myViewHolder.cardTextValueTicker;
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(view);
                tickerView.setText(stringBuilder.toString());
                CartAddOnsAdapter.this.addon.setQuantity(Integer.valueOf(view));
                CartAddOnsAdapter.this.setAddOnsText();
            }
        });
        myViewHolder.cardMinusBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CartAddOnsAdapter.this.addon = (Addon) CartAddOnsAdapter.list.get(i);
                if (myViewHolder.cardTextValue.getText().toString().equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES) != null) {
                    myViewHolder.cardAddDetailLayout.setVisibility(View.GONE);
                    myViewHolder.cardAddTextLayout.setVisibility(View.VISIBLE);
                    myViewHolder.addonName.setChecked(false);
                    CartAddOnsAdapter.this.addon.getAddon().setChecked(false);
                } else {
                    view = Integer.parseInt(myViewHolder.cardTextValue.getText().toString()) - 1;
                    TextView access$100 = myViewHolder.cardTextValue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    access$100.setText(stringBuilder.toString());
                    TickerView tickerView = myViewHolder.cardTextValueTicker;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(view);
                    tickerView.setText(stringBuilder.toString());
                    CartAddOnsAdapter.this.addon.setQuantity(Integer.valueOf(view));
                }
                CartAddOnsAdapter.this.setAddOnsText();
            }
        });
        setAddOnsText();
    }

    private void setAddOnsText() {
        AddonBottomSheetFragment.addons.setText("");
        if (AddonBottomSheetFragment.selectedCart != null) {
            Product product = AddonBottomSheetFragment.selectedCart.getProduct();
            int intValue = AddonBottomSheetFragment.selectedCart.getQuantity().intValue();
            this.priceAmount = product.getPrices().getPrice().intValue() * intValue;
            for (Addon addon : list) {
                if (addon.getAddon().getChecked()) {
                    if (AddonBottomSheetFragment.addons.getText().toString().equalsIgnoreCase("")) {
                        AddonBottomSheetFragment.addons.append(addon.getAddon().getName());
                    } else {
                        TextView textView = AddonBottomSheetFragment.addons;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(", ");
                        stringBuilder.append(addon.getAddon().getName());
                        textView.append(stringBuilder.toString());
                    }
                    this.priceAmount += (addon.getQuantity().intValue() * addon.getPrice().intValue()) * intValue;
                }
            }
            TextView textView2;
            StringBuilder stringBuilder2;
            if (intValue == 1) {
                textView2 = AddonBottomSheetFragment.price;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(String.valueOf(intValue));
                stringBuilder2.append(" Item | ");
                stringBuilder2.append(GlobalData.currencySymbol);
                stringBuilder2.append(this.priceAmount);
                textView2.setText(stringBuilder2.toString());
                return;
            }
            textView2 = AddonBottomSheetFragment.price;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(String.valueOf(intValue));
            stringBuilder2.append(" Items | ");
            stringBuilder2.append(GlobalData.currencySymbol);
            stringBuilder2.append(this.priceAmount);
            textView2.setText(stringBuilder2.toString());
        }
    }

    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends ViewHolder {
        RelativeLayout addButtonRootLayout;
        CheckBox addonName;
        RelativeLayout cardAddDetailLayout;
        RelativeLayout cardAddTextLayout;
        RelativeLayout cardInfoLayout;
        TickerView cardTextValueTicker;
        private ImageView animationLineCartAdd;
        private ImageView cardAddBtn;
        private TextView cardAddInfoText;
        private TextView cardAddOutOfStock;
        private ImageView cardMinusBtn;
        private TextView cardTextValue;
        private ImageView dishImg;
        private ImageView foodImageType;

        private MyViewHolder(View view) {
            super(view);
            this.foodImageType = (ImageView) this.itemView.findViewById(R.id.food_type_image);
            this.animationLineCartAdd = (ImageView) this.itemView.findViewById(R.id.animation_line_cart_add);
            this.addonName = (CheckBox) this.itemView.findViewById(R.id.dish_name_text);
            this.cardAddDetailLayout = (RelativeLayout) this.itemView.findViewById(R.id.add_card_layout);
            this.addButtonRootLayout = (RelativeLayout) this.itemView.findViewById(R.id.add_button_root_layout);
            this.cardAddTextLayout = (RelativeLayout) this.itemView.findViewById(R.id.add_card_text_layout);
            this.cardAddInfoText = (TextView) this.itemView.findViewById(R.id.avialablity_time);
            this.cardAddOutOfStock = (TextView) this.itemView.findViewById(R.id.out_of_stock);
            this.cardAddBtn = (ImageView) this.itemView.findViewById(R.id.card_add_btn);
            this.cardMinusBtn = (ImageView) this.itemView.findViewById(R.id.card_minus_btn);
            this.cardTextValue = (TextView) this.itemView.findViewById(R.id.card_value);
            this.cardTextValueTicker = (TickerView) this.itemView.findViewById(R.id.card_value_ticker);
        }
    }
}
