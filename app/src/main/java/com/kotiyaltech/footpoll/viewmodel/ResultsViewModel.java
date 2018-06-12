package com.kotiyaltech.footpoll.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kotiyaltech.footpoll.database.Results;

/**
 * Created by hp pc on 12-06-2018.
 */

public class ResultsViewModel extends ViewModel {
    private MutableLiveData<Results> resultsMutableLiveData;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("result");
    public LiveData<Results> getResults() {
        if (resultsMutableLiveData == null) {
            resultsMutableLiveData = new MutableLiveData<>();
            loadResults();
            databaseReference.keepSynced(true);

        }
        return resultsMutableLiveData;
    }

    private void loadResults() {
        // Do an asynchronous operation to fetch users.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Results results = dataSnapshot.getValue(Results.class);
                resultsMutableLiveData.setValue(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }


}

