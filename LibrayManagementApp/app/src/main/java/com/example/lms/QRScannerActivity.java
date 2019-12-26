package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),"result is"+result.getText(),Toast.LENGTH_SHORT).show();
        IssueBook.ISBN.setText(result.getText());
      //  onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
