package com.opalfire.foodorder.Pubnub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.opalfire.foodorder.R;

import java.util.List;

public class ChatMessageAdapter
        extends ArrayAdapter<ChatMessage> {
    private static final int MY_IMAGE = 2;
    private static final int MY_MESSAGE = 0;
    private static final int OTHER_IMAGE = 3;
    private static final int OTHER_MESSAGE = 1;

    public ChatMessageAdapter(Context paramContext, List<ChatMessage> paramList) {
        super(paramContext, R.layout.item_mine_message, paramList);
    }

    public int getItemViewType(int paramInt) {
        ChatMessage localChatMessage = getItem(paramInt);
        if ((localChatMessage.isMine()) && (!localChatMessage.isImage())) {
            return 0;
        }
        if ((!localChatMessage.isMine()) && (!localChatMessage.isImage())) {
            return 1;
        }
        if ((localChatMessage.isMine()) && (localChatMessage.isImage())) {
            return 2;
        }
        return 3;
    }

    public View getView(int paramInt, View paramView, @NonNull ViewGroup paramViewGroup) {
        int i = getItemViewType(paramInt);
        View view = null;
        if (i == 0) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_mine_message, paramViewGroup, false);
            ((TextView) view.findViewById(R.id.text)).setText(getItem(paramInt).getContent());
            return view;
        }
        if (i == 1) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_other_image, paramViewGroup, false);
            ((TextView) view.findViewById(R.id.text)).setText(getItem(paramInt).getContent());
            return view;
        }
        if (i == 2) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_mine_image, paramViewGroup, false);
        }
        return LayoutInflater.from(getContext()).inflate(R.layout.item_other_message, paramViewGroup, false);
    }

    public int getViewTypeCount() {
        return 4;
    }
}
