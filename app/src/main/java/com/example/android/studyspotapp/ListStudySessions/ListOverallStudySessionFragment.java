package com.example.android.studyspotapp.ListStudySessions;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.studyspotapp.Database.StudySession;

import java.util.List;

/**
 * Created by marty on 12/14/17.
 */

public class ListOverallStudySessionFragment extends ListStudySessionFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        View v = super.onCreateView(inflater, container, savedInstanceState);

        studySessionListViewModel = ViewModelProviders.of(this).get(StudySessionListViewModel.class);

        studySessionListViewModel.getThisWeekStudySessionList().observe(this,
                new Observer<List<StudySession>>(){
                    @Override
                    public void onChanged(@Nullable List<StudySession> studySessions){
                        recyclerViewAdapter.addItems(studySessions);
                    }

                });


        //I will be adding a study session when they leave a geofence. Not on a button
      /*  addDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mView.getContext(), AddDebtActivity.class);
                i.putExtra(ListDebtsFragment.WHO_OWES_WHOM, ListDebtsFragment.I_OWE);
                startActivity(i);
            }
        });

        addPayment.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mView.getContext(), AddPayment.class);
                i.putExtra(ListDebtsFragment.WHO_OWES_WHOM, ListDebtsFragment.I_OWE);
                startActivity(i);
            }
        });*/
        return v;
    }
}
