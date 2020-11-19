package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import model.User;

public class DangKiTKActivity extends AppCompatActivity {
    Button btnDangKi2;
    TextInputLayout txtfullname,txtUserDk,txtEmail,txtphone,txtPasswordDk;
    private FirebaseAuth mAuth2;
    FirebaseDatabase firebase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_t_k);
        //Anh Xa
        txtfullname=findViewById(R.id.txtfullname);
        txtUserDk=findViewById(R.id.txtUserDk);
        txtEmail=findViewById(R.id.txtEmail);
        txtphone=findViewById(R.id.txtSDT);
        txtPasswordDk=findViewById(R.id.txtPass);
        btnDangKi2=findViewById(R.id.btnDangKi2);


        btnDangKi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise firebase
                firebase = FirebaseDatabase.getInstance();
                reference=firebase.getReference("Users");





                //Get all the values
                String fullname=txtfullname.getEditText().getText().toString();
                String username=txtUserDk.getEditText().getText().toString();
                String email=txtEmail.getEditText().getText().toString();
                String phone=txtphone.getEditText().getText().toString();
                String password=txtPasswordDk.getEditText().getText().toString();



                User user=new User(fullname,username,email,phone,password);
                reference.child(username).setValue(user);
            }
        });



    }





}

