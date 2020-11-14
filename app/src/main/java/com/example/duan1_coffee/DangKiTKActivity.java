package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import model.User;

public class DangKiTKActivity extends AppCompatActivity {
    Button btnDangKi2;
    EditText txtUserDk,txtPasswordDk,txtName,txtphone;
    private FirebaseAuth mAuth2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_t_k);
        //Anh Xa
        btnDangKi2=findViewById(R.id.btnDangKi2);
        txtUserDk=findViewById(R.id.txtUserDk);
        txtPasswordDk=findViewById(R.id.txtPasswordDk);
        txtName=findViewById(R.id.txtfullname);
        txtphone=findViewById(R.id.txtSDT);


        //Initialise firebase
        mAuth2 = FirebaseAuth.getInstance();

        if(mAuth2.getCurrentUser() !=null){
            Intent i=new Intent(DangKiTKActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

        btnDangKi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emaill=txtUserDk.getText().toString().trim();
                final String password=txtPasswordDk.getText().toString().trim();
                final String namee=txtName.getText().toString().trim();
                final String phone=txtphone.getText().toString().trim();

                if(namee.isEmpty()){
                    txtName.setError("Bạn chưa nhập name");
                    txtName.requestFocus();
                    return;
                }
                if(emaill.isEmpty()){
                    txtUserDk.setError("Bạn chưa nhập email");
                    txtUserDk.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()){
                    txtUserDk.setError("Email sai định dạng");
                    txtUserDk.requestFocus();
                    return;
                }
                if(password.isEmpty() || password.length() <6){
                    txtPasswordDk.setError("Password >= 6 kí tự");
                    txtPasswordDk.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    txtphone.setError("Bạn chưa nhập phone");
                    txtphone.requestFocus();
                    return;
                }
                if(phone.length() !=10){
                    txtphone.setError("SĐT phải là 10 số");
                    txtphone.requestFocus();
                    return;
                }
                mAuth2.createUserWithEmailAndPassword(emaill,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //we will store the additional fields in firebase database
                                    User user=new User(
                                            namee,emaill,password,phone
                                    );
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(DangKiTKActivity.this,"Đăng Kí Thành Công!",Toast.LENGTH_LONG).show();
                                                        Intent i=new Intent(DangKiTKActivity.this,MainActivity.class);
                                                        startActivity(i);
                                                    }
                                                }
                                            }) ;
                                }else {
                                    Toast.makeText(DangKiTKActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });

    }


}
