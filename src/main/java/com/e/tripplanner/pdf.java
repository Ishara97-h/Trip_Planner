//package com.e.tripplanner;
//
//import android.Manifest;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.e.tripplanner.Common.Common;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class pdf extends AppCompatActivity {
//    Button pdf;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pdf);
//
//        pdf = findViewById(R.id.pdf);
//
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        pdf.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                createPDFFile(Common.getAppPath(pdf.this)+"test_pdf.pdf");
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                    }
//                })
//                .check();
//    }
//
//    private void createPDFFile(String path) {
//        if (new File(path).exists())
//            new File(path).delete();
//        try {
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,new FileOutputStream(path));
//
//            document.open();
//            document.setPageSize(PageSize.A4);
//            document.addCreationDate();
//            document.addAuthor("TripPlanner");
//            document.addCreator("TripPlanner");
//
//            BaseColor colorAccent = new BaseColor(0,153,204,255);
//            float fontSize = 20.0f;
//            float valueFontSize = 26.0f;
//
//            BaseFont fontName =BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8",BaseFont.EMBEDDED);
//
//            Font titleFont = new Font(fontName,36.0f,Font.NORMAL,BaseColor.BLACK);
//            addNewItem(document,"order details", Element.ALIGN_CENTER,titleFont);
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
//        Chunk chunk = new Chunk(text,font);
//        Paragraph paragraph = new Paragraph(chunk);
//        paragraph.setAlignment(align);
//        document.add(paragraph);
//    }
//}
