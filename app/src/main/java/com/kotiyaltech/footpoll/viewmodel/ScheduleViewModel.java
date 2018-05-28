package com.kotiyaltech.footpoll.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kotiyaltech.footpoll.database.Schedule;
import com.kotiyaltech.footpoll.database.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 03-04-2018.
 */

public class ScheduleViewModel extends ViewModel {
    public static final String GROUP_STAGE = "group";
    public static final String ROUND_OF_SIXTEEN = "roundOf16";
    public static final String QAURTERFINAL = "quarterfinal";
    public static final String SEMIFINAL = "semifinal";
    public static final String FINAL = "final";
    public static final String THIRD_PLACE = "thirdPlace";
    private MutableLiveData<Schedule> scheduleMutableLiveData;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("schedule");
    public LiveData<Schedule> getSchedule() {
        if (scheduleMutableLiveData == null) {
            scheduleMutableLiveData = new MutableLiveData<>();
            databaseReference.keepSynced(true);
            loadSchedule();

        }
        return scheduleMutableLiveData;
    }



    private void loadSchedule() {
        // Do an asynchronous operation to fetch users.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Schedule schedule = dataSnapshot.getValue(Schedule.class);
                scheduleMutableLiveData.setValue(schedule);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public ArrayList<ScheduleItem> filterScheduleBasedOnStage(List<ScheduleItem> scheduleItemList, String stage){
        ArrayList<ScheduleItem> filteredScheduleItem = new ArrayList<>();
        for (ScheduleItem scheduleItem : scheduleItemList) {
            if(scheduleItem.getStage().equalsIgnoreCase(stage)){
                filteredScheduleItem.add(scheduleItem);
            }

        }
        return filteredScheduleItem;
    }
}
