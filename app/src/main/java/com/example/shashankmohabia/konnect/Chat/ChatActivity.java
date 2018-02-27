package com.example.shashankmohabia.konnect.Chat;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shashankmohabia.konnect.Matches.MatchesActivity;
import com.example.shashankmohabia.konnect.Matches.MatchesAdapter;
import com.example.shashankmohabia.konnect.Matches.MatchesObject;
import com.example.shashankmohabia.konnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mlayoutManager;
    private ChatsAdapter mAdapter;
    private String currentUser, matchId, chatId;

    private EditText mMessage;
    private Button mSend;

    DatabaseReference mUserDatabase, mChatDatabase;


    private ArrayList<ChatsObject> chatsObjectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        matchId = getIntent().getExtras().getString("matchId");

        mMessage = (EditText) findViewById(R.id.writemessage);
        mSend = (Button) findViewById(R.id.send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Connections").child("Matches").child(matchId).child("ChatId");
        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");

        getChatId();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        mlayoutManager = new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(mlayoutManager);

        mAdapter = new ChatsAdapter(getChatsObjectArrayList(), ChatActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void sendMessage() {
        String message = mMessage.getText().toString();

        if (!message.isEmpty()) {
            DatabaseReference newMessageDb = mChatDatabase.push();

            Map newMessage = new HashMap();
            newMessage.put("createdBy", currentUser);
            newMessage.put("text", message);

            newMessageDb.setValue(newMessage);
        }
        mMessage.setText(null);
    }

    private void getChatId() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    chatId = dataSnapshot.getValue().toString();
                    mChatDatabase = mChatDatabase.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getChatMessages() {
        mChatDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    String message = null;
                    String createdByUser = null;
                    if (dataSnapshot.child("text").getValue() != null) {
                        message = dataSnapshot.child("text").getValue().toString();
                    }
                    if (dataSnapshot.child("createdBy").getValue() != null) {
                        createdByUser = dataSnapshot.child("createdBy").getValue().toString();
                    }
                    if (message != null && createdByUser != null){
                        Boolean currentUserBoolean = false;
                        if(createdByUser.equals(currentUser)){
                            currentUserBoolean = true;
                        }

                        ChatsObject chat = new ChatsObject(message, currentUserBoolean);
                        chatsObjectArrayList.add(chat);
                        mAdapter.notifyDataSetChanged();

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


    private List<ChatsObject> getChatsObjectArrayList() {
        return chatsObjectArrayList;
    }
}
