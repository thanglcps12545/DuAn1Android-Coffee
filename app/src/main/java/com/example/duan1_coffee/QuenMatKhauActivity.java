package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QuenMatKhauActivity extends AppCompatActivity {
    Button btnNext;
    TextInputLayout btnPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        btnPhone=findViewById(R.id.forget_password_phone_number);
        btnNext=findViewById(R.id.forget_password_next_btn);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber=btnPhone.getEditText().getText().toString();
//                if(phoneNumber.charAt(0)=='0'){
//
//                }

                Query checkUser=FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(phoneNumber);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            btnPhone.setError(null);
                            btnPhone.setErrorEnabled(false);

                            Intent i=new Intent(QuenMatKhauActivity.this,XacNhanPassActivity.class);
                            i.putExtra("phone",phoneNumber);
                            i.putExtra("whatToDO","updateData");
                            startActivity(i);
                            finish();
                        }else {
                            btnPhone.setError("Sai Phone");
                            btnPhone.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });




    }
}
