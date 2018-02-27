package com.example.shashankmohabia.konnect.Chat;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shashankmohabia.konnect.R;

import java.util.List;

/**
 * Created by shash on 2/27/2018.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsViewHolder> {
    private List<ChatsObject> chatsObjectList;
    private Context context;

    public ChatsAdapter(List<ChatsObject> chatsObjectList, Context context) {
        this.chatsObjectList = chatsObjectList;
        this.context = context;
    }

    @Override
    public ChatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatsViewHolder rcv = new ChatsViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ChatsViewHolder holder, int position) {
        holder.mChatText.setText(chatsObjectList.get(position).getMessage());
        if(chatsObjectList.get(position).getCurrentUserBool()){
            holder.mChatText.setGravity(Gravity.END);
            holder.mChatText.setTextColor(Color.parseColor("#000000"));
            holder.mChatContainer.setBackgroundColor(Color.parseColor("#a7e4ba"));
        }else{
            holder.mChatText.setGravity(Gravity.START);
            holder.mChatText.setTextColor(Color.parseColor("#000000"));
            holder.mChatContainer.setBackgroundColor(Color.parseColor("#ffffff"));

        }
    }

    @Override
    public int getItemCount() {
        return this.chatsObjectList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
