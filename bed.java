package com.example.zukhruf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bed extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseUser user;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed);

        Button button1 = findViewById(R.id.buttonback20);
        button1.setOnClickListener(v -> {
            Intent i = new Intent(this, bedroom_category.class);
            startActivity(i);
        });

        Button button2 = findViewById(R.id.buttonar);
        button2.setOnClickListener(v -> {
            Intent i = new Intent(this, Bed_AR.class);
            startActivity(i);
        });
        save();
    }

    public void save () {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        reference.child(UserID).child("bed").setValue(1);
    }
}