package com.kotiyaltech.footpoll.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kotiyaltech.footpoll.database.Poll;
import com.kotiyaltech.footpoll.database.Polls;
import com.kotiyaltech.footpoll.database.Response;

/**
 * Created by hp pc on 01-04-2018.
 */

public class PollsViewModel extends ViewModel {
    private MutableLiveData<Polls> pollsMutableLiveData;
    private MutableLiveData<Poll> pollByIdMutableLiveData;
    private MutableLiveData<Response> responseLiveData = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("poll");
    public LiveData<Polls> getPolls() {
        if (pollsMutableLiveData == null) {
            pollsMutableLiveData = new MutableLiveData<>();
            loadPolls();

        }
        return pollsMutableLiveData;
    }

    public LiveData<Poll> getPollById(int id) {
        if (pollByIdMutableLiveData == null) {
            pollByIdMutableLiveData = new MutableLiveData<>();
            loadPollById(id);

        }
        return pollByIdMutableLiveData;
    }

    private void loadPollById(int id) {
        // Do an asynchronous operation to fetch users.
        databaseReference.child("polls").orderByChild("id").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Poll iplPolls = snapshot.getValue(Poll.class);
                        pollByIdMutableLiveData.setValue(iplPolls);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    private void loadPolls() {
        // Do an asynchronous operation to fetch users.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Polls iplPolls = dataSnapshot.getValue(Polls.class);
                pollsMutableLiveData.setValue(iplPolls);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public LiveData<Response> savePollsData(Polls iplPolls){
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("poll").setValue(iplPolls, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Response response = new Response();
                if (databaseError != null) {
                    response.setDatabaseError(databaseError);
                    response.setSuccess(false);
                } else {
                    response.setDatabaseError(null);
                    response.setSuccess(true);
                }

                responseLiveData.setValue(response);
            }
        });

        return responseLiveData;
    }
}
