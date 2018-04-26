package com.example.android.studyspotapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.MainActivity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;


public class pdfUtils {
    //write method takes two parameter pdf name and content
    //return true if pdf successfully created

    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;
    String state = Environment.getExternalStorageState();

    public void write(String fname, String totalHoursRemaining, String totalHoursCompleted, Boolean hadEnoughHours, Context mContext, java.util.List<StudySession> sessionList) {

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

                PdfWriter writer = PdfWriter.getInstance(document, output);

                //Encrypted PDF with a password the coaches will have. Password right now if for testing
                writer.setEncryption("password1".getBytes(), "password2".getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
                writer.createXmpMetadata();

                document.open();

                //addLogo(document, mContext);

                //Customise the text of the pdf
                float headingFontSize = 18f;
                float subsectionFontSize = 10f;
                float lineSpacing = 10f;

                // Adds title
                Paragraph title = new Paragraph(new Phrase(lineSpacing,"StudySpot Weekly Report",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, headingFontSize)));
                title.setAlignment(Element.ALIGN_CENTER);
                title.setPaddingTop(30f);
                document.add(title);

                // Adds subsection
                Paragraph totalHoursRecorded = new Paragraph(new Phrase(lineSpacing,"\n" + "Total Hours Recorded: " + totalHoursCompleted,
                        FontFactory.getFont(FontFactory.HELVETICA, subsectionFontSize)));
                document.add(totalHoursRecorded);

                if (hadEnoughHours == true) {
                    Paragraph HoursRemaining = new Paragraph(new Phrase(lineSpacing,"Extra Hours Completed: " + totalHoursRemaining,
                            FontFactory.getFont(FontFactory.HELVETICA, subsectionFontSize)));
                    document.add(HoursRemaining);
                } else {
                    Paragraph HoursRemaining = new Paragraph(new Phrase(lineSpacing,"Hours Remaining: " + totalHoursRemaining,
                            FontFactory.getFont(FontFactory.HELVETICA, subsectionFontSize)));
                    document.add(HoursRemaining);
                }


                String enoughHours;

                if (hadEnoughHours == true) {
                    enoughHours = "YES";
                } else {
                    enoughHours = "NO";
                }

                Paragraph completedStudyHall = new Paragraph(new Phrase(lineSpacing,"Completed Study Hall for this week: " + enoughHours,
                        FontFactory.getFont(FontFactory.HELVETICA, subsectionFontSize)));
                document.add(completedStudyHall);

                // Adds Weekly session title
                Paragraph weeklySessionTitle = new Paragraph(new Phrase(lineSpacing,"\n" + "This weeks recorded sessions:",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, subsectionFontSize)));
                document.add(weeklySessionTitle);

                String sessionListString = convertSessionList(sessionList);

                Paragraph sessionListP = new Paragraph(new Phrase(lineSpacing, "\n" + sessionListString,
                        FontFactory.getFont(FontFactory.HELVETICA, subsectionFontSize)));

                document.add(sessionListP);

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


    public void addLogo(Document document, Context context) throws DocumentException {
        try { // Get user Settings GeneralSettings getUserSettings =

            Rectangle rectDoc = document.getPageSize();
            float width = rectDoc.getWidth();
            float height = rectDoc.getHeight();
            float imageStartX = width - document.rightMargin() - 350f;
            float imageStartY = height - document.topMargin() - 180f;

            System.gc();

            InputStream ims = context.getAssets().open("logo_with_name_pdf.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] byteArray = stream.toByteArray();
            // PdfImage img = new PdfImage(arg0, arg1, arg2)

            // Converting byte array into image Image img =
            Image img = Image.getInstance(byteArray); // img.scalePercent(50);
            img.setAlignment(Image.TEXTWRAP);
            img.scaleAbsolute(109f, 76.5f);
            img.setAbsolutePosition(imageStartX, imageStartY); // Adding Image
            document.add(img);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String convertSessionList(java.util.List<StudySession> studySessionList) {

        java.util.List<String> studySessionStringArray = new ArrayList<>(studySessionList.size());

        for (StudySession session : studySessionList) {
            studySessionStringArray.add(session.toString());
        }

        StringBuilder sb = new StringBuilder();
        for (String s : studySessionStringArray)
        {
            sb.append(s);
            sb.append("\n\n");
        }

        return sb.toString();

    }
}

