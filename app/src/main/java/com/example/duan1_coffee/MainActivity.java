package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fragment.Home_Fragment;
import fragment.Info_Fragment;
import fragment.Menu_Fragment;
import fragment.New_Fragment;
import fragment.User_Fragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Anh xa

//       logoutFB=findViewById(R.id.btnfacebookk);
        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);



        //Selected icon bottom navigation(add may tam hinh icon vao bottom navigation)
        BottomNavigationView navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setItemIconTintList(null);


        //Su kien cho bottom navigation khi bam vao chuyen qua fragment rieng
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //Khi login vao se chuyen vao fragment dau tien la new nha
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new New_Fragment()).commit();

    }






    //Bat su kien click cho cac bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFrament =null;

                    switch (item.getItemId()){
                        //Bam vao id news se vao fragment news(tuong tu ben duoi)
                        case R.id.news:
                            selectedFrament =new New_Fragment();
                            break;
                        case R.id.menu:
                            selectedFrament =new Menu_Fragment();
                            break;
                        case R.id.home:
                            selectedFrament =new Home_Fragment();
                            break;
                        case R.id.info:
                            selectedFrament =new Info_Fragment();
                            break;
                        case R.id.user:
                            selectedFrament =new User_Fragment();
                            break;

                    }
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrament).commit();
                    return true;
                }
            };
}
