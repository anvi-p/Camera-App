package com.example.padiyaranvi.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileDisplay extends AppCompatActivity {
    private TextView profile, photos, cam, back;
    private Button logout;
    private TextView text;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        mAuth = FirebaseAuth.getInstance();
        profile = findViewById(R.id.profileNav);
        profile.setBackgroundColor(getResources().getColor(R.color.cream));
        back = findViewById(R.id.backNav);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        photos = findViewById(R.id.photosNav);
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoDisplay.class);
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
        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        text = findViewById(R.id.usernameEmail);
        text.setText(mAuth.getCurrentUser().getEmail());
    }
}
