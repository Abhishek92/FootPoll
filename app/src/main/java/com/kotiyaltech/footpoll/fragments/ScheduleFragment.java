package com.kotiyaltech.footpoll.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kotiyaltech.footpoll.R;
import com.kotiyaltech.footpoll.database.Schedule;
import com.kotiyaltech.footpoll.database.ScheduleItem;
import com.kotiyaltech.footpoll.viewmodel.ScheduleViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {


    public static final String TAG = ScheduleFragment.class.getSimpleName();

    private ViewPager scheduleViewPager;
    private TabLayout scheduleTabLayout;
    private ScheduleViewModel scheduleViewModel;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        scheduleTabLayout = view.findViewById(R.id.schedule_tab_layout);
        scheduleViewPager = view.findViewById(R.id.schedule_vp);
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        scheduleViewModel.getSchedule().observe(this, new Observer<Schedule>() {
            @Override
            public void onChanged(@Nullable Schedule schedule) {
                progressBar.setVisibility(View.GONE);
                if(null != schedule)
                    loadSchedule(schedule.getSchedule());
            }
        });


    }

    private void loadSchedule(List<ScheduleItem> scheduleList) {
        SchedulePagerAdapter schedulePagerAdapter = new SchedulePagerAdapter(getChildFragmentManager());
        schedulePagerAdapter.addFragment(ScheduleListFragment.getInstance(scheduleViewModel.filterScheduleBasedOnStage(scheduleList, ScheduleViewModel.GROUP_STAGE)), "Group Stage");
        schedulePagerAdapter.addFragment(ScheduleListFragment.getInstance(scheduleViewModel.filterScheduleBasedOnStage(scheduleList, ScheduleViewModel.ROUND_OF_SIXTEEN)), "Round of 16");
        schedulePagerAdapter.addFragment(ScheduleListFragment.getInstance(scheduleViewModel.filterScheduleBasedOnStage(scheduleList, ScheduleViewModel.QAURTERFINAL)), "Quarterfinal");
        schedulePagerAdapter.addFragment(ScheduleListFragment.getInstance(scheduleViewModel.filterScheduleBasedOnStage(scheduleList, ScheduleViewModel.SEMIFINAL)), "Semifinal");
        schedulePagerAdapter.addFragment(ScheduleListFragment.getInstance(scheduleViewModel.filterScheduleBasedOnStage(scheduleList, ScheduleViewModel.THIRD_PLACE)), "Third Place");
        schedulePagerAdapter.addFragment(ScheduleListFragment.getInstance(scheduleViewModel.filterScheduleBasedOnStage(scheduleList, ScheduleViewModel.FINAL)), "Final");
        scheduleViewPager.setAdapter(schedulePagerAdapter);
        scheduleViewPager.setOffscreenPageLimit(6);
        scheduleTabLayout.setupWithViewPager(scheduleViewPager);
    }

    public class SchedulePagerAdapter extends FragmentPagerAdapter
    {
        private final List<String> mFragmentTitles = new ArrayList<>();
        private List<Fragment> mFragments = new ArrayList<>();

        SchedulePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        void addFragment(Fragment fragment, String title)
        {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitles.get(position);
        }
    }
}
