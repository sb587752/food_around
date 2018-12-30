package com.opalfire.orderaround.adapter;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.activities.AccountPaymentActivity;
import com.opalfire.orderaround.activities.AddMoneyActivity;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;

public class AccountPaymentAdapter extends BaseAdapter {
    private Context context_;
    private boolean isDeleteAvailable;
    private List<Card> list;

    public AccountPaymentAdapter(Context context, List<Card> list, boolean z) {
        this.context_ = context;
        this.list = list;
        this.isDeleteAvailable = z;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int i) {
        return this.list.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final Card card = (Card) this.list.get(i);
        if (view != null) {
            viewGroup = (ViewHolder) view.getTag();
        } else {
            view = ((LayoutInflater) this.context_.getSystemService("layout_inflater")).inflate(R.layout.payment_method_list_item, viewGroup, false);
            viewGroup = new ViewHolder(view);
            view.setTag(viewGroup);
        }
        if (this.isDeleteAvailable) {
            viewGroup.paymentLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            viewGroup.deleteTxt.setVisibility(View.VISIBLE);
        } else {
            viewGroup.deleteTxt.setVisibility(View.GONE);
        }
        viewGroup.paymentLabel.setChecked(card.isChecked());
        viewGroup.paymentLabel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AccountPaymentAdapter.this.isDeleteAvailable == null && viewGroup.paymentLabel.isChecked() != null) {
                    for (int i = 0; i < GlobalData.cardArrayList.size(); i++) {
                        if (((Card) GlobalData.cardArrayList.get(i)).getCardId().equals(card.getCardId())) {
                            ((Card) GlobalData.cardArrayList.get(i)).setChecked(true);
                        } else {
                            ((Card) GlobalData.cardArrayList.get(i)).setChecked(false);
                        }
                    }
                    if (AccountPaymentActivity.accountPaymentAdapter != null) {
                        AccountPaymentActivity.proceedToPayBtn.setVisibility(View.VISIBLE);
                        AccountPaymentActivity.cashCheckBox.setChecked(false);
                        GlobalData.isCardChecked = true;
                        AccountPaymentActivity.accountPaymentAdapter.notifyDataSetChanged();
                    }
                    if (AddMoneyActivity.accountPaymentAdapter != null) {
                        AddMoneyActivity.accountPaymentAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        viewGroup.deleteTxt.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                view = new Builder(AccountPaymentAdapter.this.context_);
                view.setMessage("Are you sure you want to delete?").setPositiveButton(AccountPaymentAdapter.this.context_.getResources().getString(R.string.yes), new C07732()).setNegativeButton(AccountPaymentAdapter.this.context_.getResources().getString(R.string.cancel), new C07721());
                view = view.create();
                view.show();
                Button button = view.getButton(-2);
                button.setTextColor(ContextCompat.getColor(AccountPaymentAdapter.this.context_, R.color.theme));
                button.setTypeface(button.getTypeface(), 1);
                view = view.getButton(-1);
                view.setTextColor(ContextCompat.getColor(AccountPaymentAdapter.this.context_, R.color.theme));
                view.setTypeface(view.getTypeface(), 1);
            }

            /* renamed from: com.entriver.orderaround.adapter.AccountPaymentAdapter$2$1 */
            class C07721 implements DialogInterface.OnClickListener {
                C07721() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            /* renamed from: com.entriver.orderaround.adapter.AccountPaymentAdapter$2$2 */
            class C07732 implements DialogInterface.OnClickListener {
                C07732() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    AccountPaymentActivity.deleteCard(card.getId().intValue());
                }
            }
        });
        RadioButton radioButton = viewGroup.paymentLabel;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("XXXX-XXXX-XXXX");
        stringBuilder.append(card.getLastFour());
        radioButton.setText(stringBuilder.toString());
        viewGroup.icon.setImageResource(R.drawable.ic_credit_card);
        return view;
    }

    private void setIcon(ImageView imageView, Integer num) {
        switch (num.intValue()) {
            case null:
                imageView.setImageResource(R.drawable.ic_debit_card);
                return;
            case 1:
                imageView.setImageResource(R.drawable.ic_cash);
                return;
            default:
                imageView.setImageResource(R.drawable.ic_cash);
                return;
        }
    }

    static class ViewHolder {
        @BindView(2131296466)
        TextView deleteTxt;
        @BindView(2131296587)
        ImageView icon;
        @BindView(2131296722)
        RadioButton paymentLabel;

        ViewHolder(View view) {
            ButterKnife.bind((Object) this, view);
        }
    }

    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.icon = (ImageView) Utils.findRequiredViewAsType(view, R.id.icon, "field 'icon'", ImageView.class);
            viewHolder.paymentLabel = (RadioButton) Utils.findRequiredViewAsType(view, R.id.payment_label, "field 'paymentLabel'", RadioButton.class);
            viewHolder.deleteTxt = (TextView) Utils.findRequiredViewAsType(view, R.id.delete_txt, "field 'deleteTxt'", TextView.class);
        }

        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder != null) {
                this.target = null;
                viewHolder.icon = null;
                viewHolder.paymentLabel = null;
                viewHolder.deleteTxt = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }
}
