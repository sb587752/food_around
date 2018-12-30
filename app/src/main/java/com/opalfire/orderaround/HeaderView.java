package com.opalfire.orderaround;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderView
        extends LinearLayout {
    @BindView(R.id.header_view_sub_title)
    TextView subTitle;
    @BindView(R.id.header_view_title)
    TextView title;

    public HeaderView(Context paramContext) {
        super(paramContext);
    }

    public HeaderView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public HeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @TargetApi(21)
    public HeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    }

    private void hideOrSetText(TextView paramTextView, String paramString) {
        if ((paramString != null) && (!paramString.equals(""))) {
            paramTextView.setText(paramString);
            return;
        }
        paramTextView.setVisibility(GONE);
    }

    public void bindTo(String paramString) {
        bindTo(paramString, "");
    }

    public void bindTo(String paramString1, String paramString2) {
        hideOrSetText(this.title, paramString1);
        hideOrSetText(this.subTitle, paramString2);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}

