package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Arrays;

import model.User;

public class LoginActivity extends AppCompatActivity {
    Button btnsignin,btndangnhap,btnDangKi;
    EditText txtUser,txtPassword;
    TextView tvquenmatkhau;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    CallbackManager mCallbackManager;
    Button btnFacebook;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialise Facebook SDK
        FacebookSdk.sdkInitialize(LoginActivity.this);
        //Initialise firebase
        mAuth = FirebaseAuth.getInstance();
        //Anh xa
        btnsignin=findViewById(R.id.btnsignin);
        btndangnhap=findViewById(R.id.btnDangNhap);
        txtUser=findViewById(R.id.txtUsername);
        txtPassword=findViewById(R.id.txtPassword);
        tvquenmatkhau=findViewById(R.id.tvquenmatkhau);
        tvquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,QuenMatKhauActivity.class);
                startActivity(i);
            }
        });

        btnDangKi=findViewById(R.id.btnDangKi);
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,DangKiTKActivity.class);
                startActivity(i);
            }
        });


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        btnFacebook=findViewById(R.id.btnfacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });


        //Test nut dang nhap chuyen qua New Fragment(bam nut nay cho nhanh thay vi bam google)
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!XacThucUser() | !XacThucPassword()){
                    return;
                }else {
                    isUser();
                }


            }
        });

        //Button sign in Google
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnsignin:
                        //Khoi bao goi private void signIn()
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }





    //Goi du lieu cho sign google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            // updateUI(account);
            if(account != null) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
            //Animation Intent
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }


//Goi du lieu facebook
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(LoginActivity.this, "Please sign in continue.", Toast.LENGTH_SHORT).show();
        }
    }



    private Boolean XacThucUser(){
        String username=txtUser.getText().toString();
        if(username.isEmpty()){
            txtUser.setError("Bạn chưa nhập Username");
            return false;
        }else {
            txtUser.setError(null);
            return true;
        }
    }

    private Boolean XacThucPassword(){
        String password=txtPassword.getText().toString();
        if(password.isEmpty()){
            txtPassword.setError("Bạn chưa nhập Password");
            return false;
        }else {
            txtPassword.setError(null);
            return true;
        }
    }


//    public void loginUser(View v){
//      if(!XacThucUser() | !XacThucPassword()){
//          return;
//      }else {
//          isUser();
//      }
//    }

    private void isUser() {
        final String userEnteredUsername = txtUser.getText().toString().trim();
        final String userEnteredPassword = txtPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    txtUser.setError(null);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        txtUser.setError(null);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);

                    } else {
                        txtPassword.setError("Sai Password");
                        txtPassword.requestFocus();
                    }
                } else {
                    txtUser.setError("Username không tồn tại");
                    txtUser.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
        updateUI(currentUser);

}






}
