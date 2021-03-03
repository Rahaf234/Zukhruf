package com.example.zukhruf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.regex.Pattern;


public class Regsteration extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextTextPersonName2, editTextTextEmailAddress, editTextTextPassword, editTextDate;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button regsterButton;
    private String genderChoose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsteration);
        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(v -> {
            Intent i = new Intent(this, home.class);
            startActivity(i);
        });
        mAuth = FirebaseAuth.getInstance();
        regsterButton = (Button) findViewById(R.id.regsterButton);
        regsterButton.setOnClickListener(this);
        editTextTextPersonName2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        radioGroup = findViewById(R.id.radioGroup);

    }

    @Override
    public void onClick(View v) {
        regsterUser();
    }


    private void findRadioButton (int i) {
        switch (i) {
            case R.id.radioButton2:
                genderChoose = "انثى";
                break;
            case R.id.radioButton:
                genderChoose = "ذكر";
                break;
        }
    }

    public static int getvalues (String s1) {
        if(s1.matches("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}"))
        {
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                Date d1=sdf.parse(s1);
                return 1;
            } catch (ParseException e) {
                return -1;
            }
        }
        else
            return -1;
    }


    private void regsterUser () {
        String email = editTextTextEmailAddress.getText().toString().trim();
        String name = editTextTextPersonName2.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();

      if (name.isEmpty()) {
          editTextTextPersonName2.setError("أدخل الاسم");
          editTextTextPersonName2.requestFocus();
          return;
      }

        if (email.isEmpty()) {
            editTextTextEmailAddress.setError("أدخل البريد الإلكتروني");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextTextEmailAddress.setError("البريد الإلكتروني خاطئ");
            editTextTextEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextTextPassword.setError("أدخل كلمة المرور");
            editTextTextPassword.requestFocus();
            return;
        }

        if (password.length() < 8){
            editTextTextPassword.setError("يجب أن تتكون كلمة المرور من ثمانية أحرف أو أكثر");
            editTextTextPassword.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            editTextDate.setError("أدخل تاريخ الميلاد");
            editTextDate.requestFocus();
            return;
        }
        int redioID = radioGroup.getCheckedRadioButtonId();
        if (redioID == -1)
            Toast.makeText(  Regsteration.this, "اختر الجنس " , Toast.LENGTH_LONG).show();
        else
            findRadioButton (redioID);

        int n = getvalues(date);

        if(n!=1){
            editTextDate.setError("ادخل تاريخ الميلاد بالشكل المطلوب dd/mm/yyyy");
            editTextDate.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email, password , date , genderChoose );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(  Regsteration.this, "تم التسجيل بنجاح" , Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Regsteration.this,chooseSection.class));
                                    }
                                    else {
                                        Toast.makeText(  Regsteration.this, "فشل التسجيل ، أرجو المحاولة مرة أخرى" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(  Regsteration.this, "البريد الإلكتروني مستخدم مسبقاً" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}