package com.example.shashankmohabia.konnect.Matches;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shashankmohabia.konnect.Chat.ChatActivity;
import com.example.shashankmohabia.konnect.R;

/**
 * Created by shash on 2/27/2018.
 */

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId, mMatchName;
    public ImageView mMatchProfilePic;

    public MatchesViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.matchid);
        mMatchName = (TextView) itemView.findViewById(R.id.matchname);
        mMatchProfilePic = (ImageView) itemView.findViewById(R.id.matchpic);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        intent.putExtra("matchId", mMatchId.getText().toString());
        v.getContext().startActivity(intent);
    }
}
