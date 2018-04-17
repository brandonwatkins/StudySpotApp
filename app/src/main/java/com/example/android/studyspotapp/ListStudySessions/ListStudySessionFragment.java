package com.example.android.studyspotapp.ListStudySessions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.R;
import com.example.android.studyspotapp.pdfUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Fragment to list all of the study sessions
 * Created by Brandon Watkins
 */

public abstract class ListStudySessionFragment extends Fragment implements
        View.OnLongClickListener,
        View.OnClickListener {

    StudySession lastDeletedStudySession;

    StudySessionListViewModel studySessionListViewModel;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_view_sessions, container, false);

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
    }

    @Override
    public boolean onLongClick(View view) {
        final StudySession studySession = (StudySession) view.getTag();
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
                                studySessionListViewModel.addSession(lastDeletedStudySession);
                            }
                        });
                        deleteAndUndo.show();
                        lastDeletedStudySession = studySession;
                        studySessionListViewModel.deleteSession(studySession);
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

//        Snackbar.make(this.mView, studySession.toString(),
//                Snackbar.LENGTH_SHORT)
//                .show();

        Snackbar.make(this.mView, studySession.toString(),
                Snackbar.LENGTH_LONG)
                .show();

        //new SimplePdf().createPdf(DEST);
        new pdfUtils().write("test", "null");

        //studySession.setSent(true);

        String TO, SUBJECT, MESSAGE;

        String filename="test.pdf";
        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
        Uri path = Uri.fromFile(filelocation);

        Intent emailIntent;

        SUBJECT = "Test Subject";
        MESSAGE = "Test Message";
        TO = "brandonwatkinsnz@gmail.com";
        emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Select Email Sending App:"));
    }


}
