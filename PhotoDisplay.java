package com.example.padiyaranvi.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class PhotoDisplay extends AppCompatActivity implements View.OnTouchListener {
    private TextView profile, photo, cam, back;
    private ImageView saveBtn, imageView;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private float downx = 0, downy = 0, upx = 0, upy = 0, count = 0;
    private Bitmap alteredBitmap;
    private Canvas canvas;
    private Paint paint;
    private Matrix matrix;
    private Path path;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos);
        Bundle extras = getIntent().getExtras();
        imageView = (ImageView) findViewById(R.id.imageView2);
        downx = 0;
        downy = 0;
        upx = 0;
        upy = 0;
        path = new Path();
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.blue));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        matrix = new Matrix();
        if (extras != null) {
            byte[] byteArray = extras.getByteArray("picture");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            alteredBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
            canvas = new Canvas(alteredBitmap);
            canvas.drawBitmap(bmp, matrix, paint);
            imageView.setImageBitmap(alteredBitmap);
            imageView.setOnTouchListener(this);
            count++;
        }
        photo = findViewById(R.id.photosNav);
        photo.setBackgroundColor(getResources().getColor(R.color.cream));
        back = findViewById(R.id.backNav);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        profile = findViewById(R.id.profileNav);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileDisplay.class);
                startActivity(intent);
            }
        });
        cam = findViewById(R.id.camNav);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraPage.class);
                startActivity(intent);
            }
        });
        saveBtn = findViewById(R.id.button2);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                while (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                if (count != 0){
                    imageView.setDrawingCacheEnabled(true);
                    Bitmap b = imageView.getDrawingCache();
                    MediaStore.Images.Media.insertImage(getContentResolver(), b, "image", "description");
                    Toast.makeText(getApplicationContext(), "Photo successfully saved to gallery.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Take a photo first before saving to your gallery.", Toast.LENGTH_LONG).show();
                }
            });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) //grant or deny permission
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Gallery Permission Granted.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Gallery Permission Denied. This app requires permission.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        float eventX = event.getRawX()-350;
        float eventY = event.getRawY()-350;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            default:
                return false;
            }
        canvas.drawPath(path, paint);
        imageView.invalidate();
        return true;

    }

}

