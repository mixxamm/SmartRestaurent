package com.smartrestaurant.mixxamm.smartrestaurant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.smartrestaurant.mixxamm.smartrestaurent.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ScanActivity extends AppCompatActivity {
    public static boolean scanned = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        final SurfaceView CameraView;
        final BarcodeDetector barcode;
        final CameraSource cameraSource;
        SurfaceHolder holder;
        final String restaurant, restaurantID, table;

        CameraView = findViewById(R.id.cameraView);
        CameraView.setZOrderMediaOverlay(true);
        holder = CameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this).
                setBarcodeFormats(Barcode.QR_CODE)
                .build();

        if(!barcode.isOperational()) {
            Toast.makeText(getApplicationContext(), "Kan detector niet stoppen!", Toast.LENGTH_LONG).show();
            this.finish();
        }

        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(60)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1940, 1024)
                .build();

        CameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(CameraView.getHolder());
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() > 0 && !scanned) {
                    scanned = true;
                    finish();

                    try {
                        String qr = barcodes.valueAt(0).rawValue;
                        JSONObject jsonobject = new JSONObject(qr);
                        String restaurant = jsonobject.getString("restaurant");
                        String table = jsonobject.getString("table");
                        String restaurantID = jsonobject.getString("restaurantID");
                        Order order = new Order(ScanActivity.this);
                        order.execute("getMenu", restaurantID, table);

                    } catch (JSONException e) {

                        String restaurant = "Niet gevonden";
                        String restaurantID = "Niet gevonden";
                        String table = "Niet gevonden";
                        e.printStackTrace();

                    }


                    }
            }
        });
    }
}
