package com.example.android.studyspotapp.ListStudySessions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.R;
import com.example.android.studyspotapp.pdfUtils;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

        Snackbar.make(this.mView, studySession.toString(),
                Snackbar.LENGTH_LONG)
                .show();

        /**
        * Creates a PDF file: hello.pdf
        * @param    args    no arguments needed
        */

        Uri path = Uri.fromFile(filelocation);


        String TO, SUBJECT, MESSAGE;
        Intent intent;

        SUBJECT = "Test Subject";
        MESSAGE = "Test Message";
        TO = "brandonwatkinsnz@gmail.com";
        intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
        intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);


        intent.setType("message/rfc822");


        try {
            Document document = new pdfUtils().createPdf("Test.pdf");
            //intent.putExtra(Intent.EXTRA_STREAM, path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
        } catch (DocumentException d) {
            d.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        startActivity(Intent.createChooser(intent, "Select Email Sending App:"));

    }



}
