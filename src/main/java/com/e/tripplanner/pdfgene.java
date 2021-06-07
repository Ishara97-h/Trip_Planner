package com.e.tripplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class pdfgene extends AppCompatActivity {

    Button btn_create_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfgene);

        btn_create_pdf = findViewById(R.id.btn_create_pdf);

        ActivityCompat.requestPermissions(this,new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

        createPDF();


    }

    private void createPDF() {
        btn_create_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();

                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250,400,1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

                Canvas canvas = myPage1.getCanvas();

                canvas.drawText("Testing",40,50,myPaint);
                myPdfDocument.finishPage(myPage1);

                File file = new File(Environment.getExternalStorageDirectory(),"/FirstPDF.pdf");
                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPdfDocument.close();
                Intent intent = new Intent(pdfgene.this, sms.class);
                startActivity(intent);
            }
        });
    }
}
