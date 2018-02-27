package com.example.shashankmohabia.konnect.Matches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.shashankmohabia.konnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mlayoutManager;
    private MatchesAdapter mAdapter;
    private String currentUser;

    private ArrayList<MatchesObject> matchesObjectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);


        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getMatchesDataset();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(MatchesActivity.this);
        mRecyclerView.setLayoutManager(mlayoutManager);

        mAdapter = new MatchesAdapter(getmatchesObjectArrayList(), MatchesActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getMatchesDataset() {
        DatabaseReference matchsDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Connections").child("Matches");
        matchsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot match : dataSnapshot.getChildren()){
                        FetchMatchInfo(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchMatchInfo(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userid = dataSnapshot.getKey();
                    String name = "";
                    String profilePicUrl = "";
                    if(dataSnapshot.child("name").getValue()!=null){
                        name = dataSnapshot.child("name").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageDownloadUri").getValue()!=null){
                        profilePicUrl = dataSnapshot.child("profileImageDownloadUri").getValue().toString();
                    }


                    MatchesObject obj = new MatchesObject(userid, name, profilePicUrl);
                    matchesObjectArrayList.add(obj);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private List<MatchesObject> getmatchesObjectArrayList() {
        return matchesObjectArrayList;
    }
}
