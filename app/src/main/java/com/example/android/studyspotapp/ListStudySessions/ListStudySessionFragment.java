package com.example.android.studyspotapp.ListStudySessions;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.R;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
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


        Document doc = new Document();


        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "//pdf";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);


            File file = new File(dir, "sample1.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph("Hi! I am generating my first PDF using iText library");
            Font paraFont= new Font(Font.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            //p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

            Paragraph p2 = new Paragraph("This is an example of a simple paragraph");
            Font paraFont2= new Font(Font.COURIER,14.0f, Color.GREEN);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
           // p2.setFont(paraFont2);

            doc.add(p2);


            //set footer
            Phrase footerText = new Phrase("This is an example of a footer");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
            doc.setFooter(pdfFooter);



        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }

        startActivity(Intent.createChooser(intent, "Select Email Sending App:"));

    }



}
