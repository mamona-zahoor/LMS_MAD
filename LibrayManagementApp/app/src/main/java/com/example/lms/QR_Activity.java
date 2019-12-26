package com.example.lms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QR_Activity extends AppCompatActivity {
    public static TextView textView;

    String TAG="GenerateQRCode";
    Button btnstart;
    Button btnsave;
    Bitmap bitmap;
    EditText editText;
    //QRGEncoder qrgEncoder;
    String inputvalue;
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
         btnstart=(Button) findViewById(R.id.startbtn);
         btnsave=(Button) findViewById(R.id.savebtn);
         img=(ImageView) findViewById(R.id.qrimagepreview);
         editText=(EditText) findViewById(R.id.edittextqr);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
//                try {
//                    BitMatrix bitMatrix=multiFormatWriter.encode(editText.getText().toString(), BarcodeFormat.QR_CODE,150,150);
//              Barc
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                }
                QRCodeWriter qrCodeWriter=new QRCodeWriter();
                try {
                    BitMatrix bitMatrix=qrCodeWriter.encode(editText.getText().toString(),BarcodeFormat.QR_CODE,200,200);
                    bitmap=Bitmap.createBitmap(200,200,Bitmap.Config.RGB_565);
                    for(int x=0;x<200;x++){
                        for(int y=0;y<200;y++){
                            bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK:Color.WHITE);
                        }
                    }
                    img.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }
        });


    }
    public void scannfun(View view){
         textView=(TextView) findViewById(R.id.txt);
        Intent intent=new Intent(QR_Activity.this, QRScannerActivity.class);
        startActivity(intent);

    }
}
