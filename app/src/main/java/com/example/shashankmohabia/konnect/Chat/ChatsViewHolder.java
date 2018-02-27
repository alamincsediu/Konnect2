package com.example.shashankmohabia.konnect.Chat;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shashankmohabia.konnect.R;

import org.w3c.dom.Text;

/**
 * Created by shash on 2/27/2018.
 */

public class ChatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mChatText;
    public LinearLayout mChatContainer;

    public ChatsViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mChatText = (TextView) itemView.findViewById(R.id.chatMessage);
        mChatContainer = (LinearLayout) itemView.findViewById(R.id.chatContainer);
    }

    @Override
    public void onClick(View v) {
    }
}
