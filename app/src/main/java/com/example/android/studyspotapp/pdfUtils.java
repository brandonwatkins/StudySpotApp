package com.example.android.studyspotapp;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pdfUtils {
    //write method takes two parameter pdf name and content
    //return true if pdf successfully created
    public Boolean write(String fname, String fcontent) {
        try {
            //Create file path for Pdf
            //String fpath = "sdcard/" + fname + ".pdf";

            Date date = new Date() ;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            String extStorage = Environment.getExternalStorageState();
            String path = extStorage + "/Android/data/com.example.android.studyspotapp/" + fname + ": " + timeStamp + ".pdf";

            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            // To customise the text of the pdf
            // we can use FontFamily
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN,
                    12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN,
                    12);
            // create an instance of itext document
            Document document = new Document();
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            //using add method in document to insert a paragraph
            document.add(new Paragraph("My First Pdf !"));
            document.add(new Paragraph("Hello World"));
            // close document
            document.close();
            Log.d("PDFUTILS", "PDF created");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PDFUTILS", "PDF NOT created (IOException)");

            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            Log.d("PDFUTILS", "PDF created (DocumentException)");
            return false;
        }
    }
}
