package com.example.android.studyspotapp.ListStudySessions;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.R;

import java.util.ArrayList;

/**
 * Fragment to list all of the debts owed to the user
 * Created by Brandon Watkins
 */

public abstract class ListStudySessionFragment extends Fragment implements
        View.OnLongClickListener,
        View.OnClickListener {

    public final static String WHO_OWES_WHOM =  "cs421.cs.mhu.edu.iou.who.owes.whom";
    public final static String I_OWE =          "cs421.cs.mhu.edu.iou.i.owe";
    public final static String THEY_OWE =       "cs421.cs.mhu.edu.iou.they.owe";

    StudySession lastDeletedStudySession;

    StudySessionListViewModel studySessionListViewModel;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    FloatingActionButton addButton;
    FloatingActionButton addDebt;
    FloatingActionButton addPayment;

    View mView;

    //boolean flag to know if main FAB is in open or closed state.
    boolean fabExpanded = false;

    //Linear layout holding Add Session
    LinearLayout layoutFabAddSession;

    //Linear layout holding Add Payment
    LinearLayout layoutFabAddPayment;

    FrameLayout fabFrame;
    CoordinatorLayout fragmentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_view_sessions, container, false);

        layoutFabAddSession = mView.findViewById(R.id.layoutFabAddDebt);
        layoutFabAddPayment = mView.findViewById(R.id.layoutFabAddPayment);
        //layoutFabPhoto  = mView.findViewById(R.id.layoutFabPhoto);

        fabFrame        = mView.findViewById(R.id.fabFrame);
        fragmentLayout  = mView.findViewById(R.id.coordLayout);

        addDebt     = mView.findViewById(R.id.fabAddDebt);
        addPayment  = mView.findViewById(R.id.fabAddPayment);

        //When main Fab (Settings) is clicked, it expands if not expanded already.
        //Collapses if main FAB was open already.
        //This gives FAB (Settings) open/close behavior
        addButton = mView.findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        recyclerView = mView.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<StudySession>(),
                this,
                this);
        mLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setContext(mView.getContext());

        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        closeSubMenusFab();
    }

    @Override
    public boolean onLongClick(View view) { final StudySession studySession = (StudySession) view.getTag();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setTitle("Delete Session");
        builder.setMessage("Are you sure you'd like to delete this session?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar deleteAndUndo = Snackbar.make(
                                ListStudySessionFragment.this.getActivity().findViewById(R.id.fab),
                                "Deleting session",
                                Snackbar.LENGTH_SHORT);
                        deleteAndUndo.setAction(R.string.undo_string, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                studySessionListViewModel.addDebt(lastDeletedStudySession);
                            }
                        });
                        deleteAndUndo.show();
                        lastDeletedStudySession = studySession;
                        studySessionListViewModel.deleteDebt(studySession);
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing...
                    }
                });
        builder.show();
        return true;
    }

    @Override
    public void onClick(View view) {
        StudySession studySession = (StudySession) view.getTag();

        //Toast.makeText(this, d.toString(), Toast.LENGTH_LONG).show();
        //Snackbar.make(this.getActivity().findViewById(R.id.fab), d.toString(),
        Snackbar.make(this.mView, studySession.toString(),
                Snackbar.LENGTH_SHORT)
                .show();
    }
    //closes FAB submenus
    void closeSubMenusFab(){

        int x = fabFrame.getRight();
        int y = fabFrame.getBottom();

        int endRadius = 0;
        int startRadius = (int) Math.hypot(fragmentLayout.getWidth(), fragmentLayout.getHeight());

        Animator animAll = ViewAnimationUtils.createCircularReveal(fabFrame, x, y, startRadius, endRadius);
        animAll.setDuration(500);

        animAll.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                layoutFabAddSession.setVisibility(View.INVISIBLE);
                layoutFabAddPayment.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animAll.start();

        //set icon to '+'
        addButton.setImageResource(R.drawable.common_google_signin_btn_icon_light_focused);
        fabExpanded = false;
    }

    //Opens FAB submenus
    void openSubMenusFab(){
        int x = fabFrame.getRight();
        int y = fabFrame.getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(fragmentLayout.getWidth(), fragmentLayout.getHeight());

        Animator animAll = ViewAnimationUtils.createCircularReveal(fabFrame, x, y, startRadius, endRadius);
        animAll.setDuration(500);

        layoutFabAddSession.setVisibility(View.VISIBLE);
        layoutFabAddPayment.setVisibility(View.VISIBLE);
        animAll.start();

        //Change settings icon to check icon
        addButton.setImageResource(R.drawable.common_google_signin_btn_icon_light_focused);
        fabExpanded = true;
    }

}
