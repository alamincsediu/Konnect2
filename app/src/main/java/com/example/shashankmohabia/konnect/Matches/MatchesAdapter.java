package com.example.shashankmohabia.konnect.Matches;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shashankmohabia.konnect.R;

import java.util.List;

/**
 * Created by shash on 2/27/2018.
 */

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<MatchesObject> matchesObjectList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesObjectList, Context context) {
        this.matchesObjectList = matchesObjectList;
        this.context = context;
    }

    @Override
    public MatchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rcv = new MatchesViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchesViewHolder holder, int position) {
        holder.mMatchId.setText(matchesObjectList.get(position).getUserID());
        holder.mMatchName.setText(matchesObjectList.get(position).getName());
        if (!matchesObjectList.get(position).getprofilePicUrl().equals("default")) {
            Glide.with(context).load(matchesObjectList.get(position).getprofilePicUrl()).into(holder.mMatchProfilePic);
        }
    }

    @Override
    public int getItemCount() {
        return this.matchesObjectList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
