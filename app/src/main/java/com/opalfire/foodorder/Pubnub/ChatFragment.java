package com.opalfire.orderaround.Pubnub;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.opalfire.orderaround.BuildConfig;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.helper.GlobalData;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryItemResult;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatFragment
        extends Fragment
        implements OnClickListener {
    String TAG = "ChatFragment";
    Context context;
    Gson gson;
    ChatMessageAdapter mAdapter;
    PubNub pubnub;
    String username;
    private ImageView btnSend;
    private EditText etMessage;
    private ListView mChatView;
    private ChatMessage mChatMessage;

    private void addToReceiveMessage(final String paramString, final int paramInt) {
        mChatMessage = new ChatMessage(paramString, false, false);
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (paramInt >= 0) {
                    mAdapter.insert(mChatMessage, paramInt);
                    return;
                }
                mAdapter.add(mChatMessage);
            }
        });
    }

    private void addToSendMessage(String paramString, int paramInt) {
        mChatMessage = new ChatMessage(paramString, true, false);
        if (paramInt >= 0) {
            mAdapter.insert(mChatMessage, paramInt);
            return;
        }
        mAdapter.add(mChatMessage);
    }

    private void sendMessage(String paramString) {
        mChatMessage = new ChatMessage(paramString, true, false);
        mAdapter.add(mChatMessage);
    }

    @Override
    public void onClick(View paramView) {
        if (btnSend.getId() == paramView.getId()) {
            String msg = etMessage.getText().toString().trim();
            if (msg.length() != 0) {
                sendMessage(msg);
                etMessage.setText("");
                JsonObject localJsonObject = new JsonObject();
                localJsonObject.addProperty("type", "user");
                localJsonObject.addProperty("message", msg);
                pubnub.publish().channel(BuildConfig.PUBNUB_CHANNEL_NAME).message(localJsonObject).async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult pnPublishResult, PNStatus pnStatus) {
                        pnStatus.isError();
                    }
                });
                return;
            }
            Toast.makeText(context, "Please enter message", Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = layoutInflater.inflate(R.layout.chat_fragment, paramViewGroup, false);
        setHasOptionsMenu(true);
        gson = new Gson();
        context = getActivity();
        if (GlobalData.profileModel != null) {
            username = GlobalData.profileModel.getName();
            this.mChatView = view.findViewById(R.id.chat_view);

            List<ChatMessage> chatMessages = new ArrayList<>();
            this.mAdapter = new ChatMessageAdapter(getActivity(), chatMessages);
            this.mChatView.setAdapter(this.mAdapter);
            this.etMessage = view.findViewById(R.id.et_message);
            this.btnSend = view.findViewById(R.id.btn_send);
            this.btnSend.setOnClickListener(this);
            PNConfiguration pnConfiguration = new PNConfiguration();
            pnConfiguration.setPublishKey(BuildConfig.PUBNUB_PUBLISH_KEY);
            pnConfiguration.setSubscribeKey(BuildConfig.PUBNUB_SUBSCRIBE_KEY);
            String selOrder = GlobalData.isSelectedOrder.getId().toString();
            pubnub = new PubNub(pnConfiguration);
            pubnub.history().channel(selOrder).count(20).async(new PNCallback<PNHistoryResult>() {
                @Override
                public void onResponse(PNHistoryResult pnHistoryResult, PNStatus pnStatus) {

                    String localStringBuilder = String.valueOf(pnHistoryResult) +
                            " " +
                            pnStatus;

                    if ((!pnStatus.isError()) && (pnHistoryResult != null)) {
                        List<PNHistoryItemResult> mPnHistoryItemResults = pnHistoryResult.getMessages();
                        Collections.reverse(mPnHistoryItemResults);
                        while (mPnHistoryItemResults.iterator().hasNext()) {
                            PNHistoryItemResult pnHistoryItemResult = mPnHistoryItemResults.iterator().next();

                            try {
                                String entryStr = pnHistoryItemResult.getEntry().toString();
                                if (entryStr.contains("nameValuePairs")) {
                                    try {
                                        String pnHislt = new JSONObject(entryStr).optJSONObject("nameValuePairs").toString();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                MyMessage myMessage = gson.fromJson(entryStr, MyMessage.class);
                                if (myMessage.getType().equals("user")) {
                                    addToSendMessage(myMessage.getMessage(), 0);
                                } else {
                                    addToReceiveMessage(myMessage.getMessage(), 0);
                                }
                            } catch (IllegalStateException | JsonSyntaxException paramAnonymousPNHistoryResult) {
                                paramAnonymousPNHistoryResult.printStackTrace();
                            }
                        }
                    }
                }
            });
            pubnub.addListener(new SubscribeCallback() {
                @Override
                public void message(final PubNub pubnub, PNMessageResult pnMessageResult) {
                    if (pnMessageResult.getChannel() != null) {
                        try {
                            String string = pnMessageResult.getMessage().toString();
                            final MyMessage myMessage = gson.fromJson(string, MyMessage.class);
                            if (!myMessage.getType().equals("user")) {
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    public void run() {
                                        addToReceiveMessage(myMessage.getMessage(), -1);
                                    }
                                });
                            }
                        } catch (IllegalStateException | JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void presence(PubNub paramAnonymousPubNub, PNPresenceEventResult paramAnonymousPNPresenceEventResult) {
                }

                @Override
                public void status(PubNub pubnub, PNStatus pnStatus) {
                    if (pnStatus.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                        return;
                    }
                    if (pnStatus.getCategory() == PNStatusCategory.PNConnectedCategory) {
                        pnStatus.getCategory();
                        PNStatusCategory pnConnectedCategory = PNStatusCategory.PNConnectedCategory;
                        return;
                    }
                    if (pnStatus.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                        return;
                    }
                    pnStatus.getCategory();
                    PNStatusCategory pnConnectedCategory = PNStatusCategory.PNDecryptionErrorCategory;
                }
            });
            pubnub.subscribe().channels(Collections.singletonList(BuildConfig.PUBNUB_CHANNEL_NAME)).execute();
        }
        return view;
    }

    public void onPause() {
        super.onPause();
        pubnub.unsubscribe().channels(Collections.singletonList(BuildConfig.PUBNUB_CHANNEL_NAME)).execute();
        Log.d(TAG, "Un subscribed");
    }

    public void onResume() {
        super.onResume();
        pubnub.subscribe().channels(Arrays.asList(BuildConfig.PUBNUB_CHANNEL_NAME)).execute();
        Log.d(TAG, "subscribed");
    }
}


/* Location:              C:\APK Extractor\Order_Around-dex2jar.jar!\com\orderaround\user\Pubnub\ChatFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */