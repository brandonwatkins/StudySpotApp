package com.example.android.studyspotapp;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class pdfUtils {
    //write method takes two parameter pdf name and content
    //return true if pdf successfully created

    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;
    String state = Environment.getExternalStorageState();

    public void write(String fname, String totalHoursRemaining, String totalHoursCompleted, Boolean hadEnoughHours) {

        //Check if the permissions
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        if (mExternalStorageWriteable == true && mExternalStorageAvailable == true) {

            try {
                //Creates folder for PDF's
                File StudySpotFolder = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS), "StudySpotPDF");
                if (!StudySpotFolder.exists()) {
                    StudySpotFolder.mkdir();
                    Log.i("PDFUTILS", "Pdf Directory created");
                }

                File myPDF = new File(StudySpotFolder + "/" + fname);

                OutputStream output = new FileOutputStream(myPDF);

                //Create an instance of iText document
                Document document = new Document();

                PdfWriter.getInstance(document, output);

                document.open();

                //Customise the text of the pdf
                Font bfBold18 = new Font(Font.FontFamily.TIMES_ROMAN,18, Font.BOLD, new BaseColor(0, 0, 0));
                Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

                // Adds title
                Paragraph title = new Paragraph("StudySpot Weekly Report");
                title.setAlignment(Element.ALIGN_CENTER);
                title.setFont(bfBold18);
                document.add(title);

                Paragraph totalHoursRecorded = new Paragraph("Total Hours Recorded: " + totalHoursCompleted);
                title.setFont(bf12);
                document.add(totalHoursRecorded);

                Paragraph HoursRemaining = new Paragraph("Hours Remaining: " + totalHoursRemaining);
                title.setFont(bf12);
                document.add(HoursRemaining);

                String enoughHours;

                if (hadEnoughHours == true) {
                    enoughHours = "YES";
                } else {
                    enoughHours = "NO";
                }

                Paragraph completedStudyHall = new Paragraph("Completed Study Hall for this week: " + enoughHours);
                title.setFont(bf12);
                document.add(completedStudyHall);

                // close document
                document.close();
                Log.d("PDFUTILS", "PDF created");
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("PDFUTILS", "PDF NOT created (IOException)");
                return;
            } catch (DocumentException e) {
                e.printStackTrace();
                Log.d("PDFUTILS", "PDF created (DocumentException)");
                return;
            }
        }

    }
}

