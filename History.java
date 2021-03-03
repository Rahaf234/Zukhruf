package com.example.zukhruf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private ArrayList<ItemHistory> itemHistoryArrayList;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String UserID = user.getUid();
    private RecyclerView recyclerView;
    private int[] arrayDB = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclarView);
        itemHistoryArrayList = new ArrayList<>();
        setItemInfo();
        setAdapter();
    }

    private void setItemInfo () {
        retreveDB ();
        if (arrayDB[0] == 1) {
            itemHistoryArrayList.add(new ItemHistory("سرير مزدوج", R.drawable.chair1));
        }
        if (arrayDB[1] == 1) {
            itemHistoryArrayList.add(new ItemHistory("كرسي مفرد", R.drawable.chair1));
        }
        if (arrayDB[2] == 1) {
            itemHistoryArrayList.add(new ItemHistory("شماعة ملابس", R.drawable.chair1));
        }
        if (arrayDB[3] == 1) {
            itemHistoryArrayList.add(new ItemHistory("طاولة جانبية", R.drawable.chair1));
        }
        if (arrayDB[4] == 1) {
            itemHistoryArrayList.add(new ItemHistory("طاولة مكتب", R.drawable.chair1));
        }
        if (arrayDB[5] == 1) {
            itemHistoryArrayList.add(new ItemHistory("طاولة وسط", R.drawable.chair1));
        }
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(itemHistoryArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void retreveDB () {
//        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//                if (userProfile != null) {
//                    arrayDB[0] = userProfile.bed;
//                    arrayDB[1] = userProfile.black_chair;
//                    arrayDB[2] = userProfile.hanger;
//                    arrayDB[3] = userProfile.nightstand;
//                    arrayDB[4] = userProfile.office_table;
//                    arrayDB[5] = userProfile.tables;
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(  History.this, "Something wrong happend !" , Toast.LENGTH_LONG).show();
//            }
//        });
        reference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayDB[0] = (int) snapshot.child("bed").getValue();
                arrayDB[1] = (int) snapshot.child("black_chair").getValue();
                arrayDB[2] = (int) snapshot.child("hanger").getValue();
                arrayDB[3] = (int) snapshot.child("nightstand").getValue();
                arrayDB[4] = (int) snapshot.child("office_table").getValue();
                arrayDB[5] = (int) snapshot.child("tables").getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(  History.this, "Something wrong happend !" , Toast.LENGTH_LONG).show();
            }
        });
    }
}