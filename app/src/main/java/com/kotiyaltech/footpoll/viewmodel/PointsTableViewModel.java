package com.kotiyaltech.footpoll.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kotiyaltech.footpoll.database.PointItem;
import com.kotiyaltech.footpoll.database.PointsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hp pc on 31-03-2018.
 */

public class PointsTableViewModel extends ViewModel {
    private MutableLiveData<PointsTable> pointsTableMutableLiveData;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("pointTable");
    public LiveData<PointsTable> getPointTable() {
        if (pointsTableMutableLiveData == null) {
            pointsTableMutableLiveData = new MutableLiveData<>();
            loadPointTable();
            databaseReference.keepSynced(true);

        }
        return pointsTableMutableLiveData;
    }

    private void loadPointTable() {
        // Do an asynchronous operation to fetch users.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PointsTable pointsTable = dataSnapshot.getValue(PointsTable.class);
                pointsTableMutableLiveData.setValue(pointsTable);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public Map<String, List<PointItem>> getPointsTableAccordingToGroup(List<PointItem> pointItemList){
        Map<String, List<PointItem>> groupedTeamMap = new HashMap<>();
        for (PointItem pointItem : pointItemList) {
            String group = pointItem.getGroupName();
            if(groupedTeamMap.containsKey(group)){
                List<PointItem> pointItems = groupedTeamMap.get(group);
                pointItems.add(pointItem);
                groupedTeamMap.put(group, pointItems);
            }
            else{
                List<PointItem> pointItems = new ArrayList<>();
                pointItems.add(pointItem);
                groupedTeamMap.put(group, pointItems);
            }
        }

        return groupedTeamMap;
    }
}
