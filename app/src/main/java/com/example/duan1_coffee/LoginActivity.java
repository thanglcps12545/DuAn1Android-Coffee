package com.example.duan1_coffee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {
    Button btnsignin,btndangnhap;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    LoginButton btnfb;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Anh xa
        btnsignin=findViewById(R.id.btnsignin);
        btndangnhap=findViewById(R.id.btnDangNhap);
        btnfb=findViewById(R.id.btnfacebook);

        //Facebook dang bi loi
        callbackManager=CallbackManager.Factory.create();
        btnfb.setReadPermissions(Arrays.asList("public_profile","email"));
        setLogin_Button();


        //add app fb vao android
        try {
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.example.duan1_coffee",//Insert your own package name.
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {

        }


        //Test nut dang nhap chuyen qua New Fragment(bam nut nay cho nhanh thay vi bam google)
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);

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
//        callbackManager.onActivityResult(requestCode,resultCode,data);

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
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            //Animation Intent
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }



    //su kien login button fb
    private void setLogin_Button() {
        btnfb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //Login thanh cong
            public void onSuccess(LoginResult loginResult) {
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                //lay du lieu ve
                result();

            }
            //Huy
            @Override
            public void onCancel() {

            }
            //Login that bai
            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //Goi su kien facebook
    private void result() {
        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON",response.getJSONObject().toString());
            }
        });
        //Xin quyen cho phep cung cap thong tin
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    //continute fb and log out
    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }


}
