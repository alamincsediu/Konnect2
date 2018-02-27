package com.example.shashankmohabia.konnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shashankmohabia.konnect.Card.Cards;
import com.example.shashankmohabia.konnect.Card.arrayAdapter;
import com.example.shashankmohabia.konnect.Matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cards card_data[];
    private com.example.shashankmohabia.konnect.Card.arrayAdapter arrayAdapter;
    private Button signout;
    private String userSex, oppositeUserSex, currentUserId;

    private FirebaseAuth mAuth;
    private DatabaseReference userdb;

    ListView listView;
    List<Cards> row_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userdb = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        signout = (Button) findViewById(R.id.signout);

        checkUserSex();


        row_items = new ArrayList<Cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, row_items);


        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                row_items.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards item = (Cards) dataObject;
                String userId = item.getUserID();
                userdb.child(userId).child("Connections").child("nope").child(currentUserId).setValue("true");
                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards item = (Cards) dataObject;
                String userId = item.getUserID();
                userdb.child(userId).child("Connections").child("yup").child(currentUserId).setValue("true");
                isMatched(userId);
                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "click!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isMatched(final String userId) {
        DatabaseReference userConnectionsdb = userdb.child(currentUserId).child("Connections").child("yup").child(userId);
        userConnectionsdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "Its a match", Toast.LENGTH_SHORT).show();
                    String key = FirebaseDatabase.getInstance().getReference().child("Chats").push().getKey();

                    userdb.child(dataSnapshot.getKey()).child("Connections").child("Matches").child(currentUserId).child("ChatId").setValue(key);
                    userdb.child(currentUserId).child("Connections").child("Matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void checkUserSex() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = userdb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("gender").getValue() != null) {
                        userSex = dataSnapshot.child("gender").getValue().toString();
                        switch (userSex) {
                            case "Male":
                                oppositeUserSex = "Female";
                                break;
                            case "Female":
                                oppositeUserSex = "Male";
                        }
                        getOppositeSexData();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }

    public void getOppositeSexData() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("gender").getValue() != null) {
                    if (dataSnapshot.exists() && !dataSnapshot.child("Connections").child("nope").hasChild(currentUserId) && !dataSnapshot.child("Connections").child("yup").hasChild(currentUserId) && dataSnapshot.child("gender").getValue().toString().equals(oppositeUserSex)) {
                        String profileImageDownloadLink = "default";
                        if (!dataSnapshot.child("profileImageDownloadUri").getValue().equals("default")) {
                            profileImageDownloadLink = dataSnapshot.child("profileImageDownloadUri").getValue().toString();
                        }
                        Cards item = new Cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), profileImageDownloadLink);
                        row_items.add(item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        return;
    }

    public void signout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void gotoMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }
}