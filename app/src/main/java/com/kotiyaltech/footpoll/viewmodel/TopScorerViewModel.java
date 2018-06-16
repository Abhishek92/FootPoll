package com.kotiyaltech.footpoll.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kotiyaltech.footpoll.database.TopScorer;

/**
 * Created by hp pc on 15-06-2018.
 */

public class TopScorerViewModel extends ViewModel {
    private MutableLiveData<TopScorer> topScorerMutableLiveData;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("topScorer");

    public LiveData<TopScorer> getTopScorer() {
        if (topScorerMutableLiveData == null) {
            topScorerMutableLiveData = new MutableLiveData<>();
            loadTopScorer();
            databaseReference.keepSynced(true);

        }
        return topScorerMutableLiveData;
    }

    private void loadTopScorer() {
        // Do an asynchronous operation to fetch users.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TopScorer results = dataSnapshot.getValue(TopScorer.class);
                topScorerMutableLiveData.setValue(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }


}


