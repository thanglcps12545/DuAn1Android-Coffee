package fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class User_Fragment extends androidx.fragment.app.Fragment {
    DrawerLayout dr_ly;
    Toolbar tb;
    NavigationView nv;
    ActionBarDrawerToggle drawerToggle;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        TextView txtName = (TextView)view.findViewById(R.id.namenav);
        TextView txtgmail = (TextView)view.findViewById(R.id.gmailnav);
        ImageView imgnav = (ImageView) view.findViewById(R.id.imgnav);


        NavigationView navigationView=view.findViewById(R.id.nv_view);
        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);


        dr_ly=view.findViewById(R.id.dr_ly);
        tb=view.findViewById(R.id.tg_bar);
        nv=view.findViewById(R.id.nv_view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(tb);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerToggle=setupDrawerToogle();

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        dr_ly.addDrawerListener(drawerToggle);

        if(savedInstanceState == null){
            nv.setCheckedItem(R.id.abc);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new TaiKhoan_Fragment()).commit();
        }

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragments=null;
//                Class fragmentClass = null;
                switch (item.getItemId()){
                    case R.id.abc:
////                    fragmentClass = Fragment_thu.class;
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new TaiKhoan_Fragment()).commit();
                        break;
                    case R.id.abcd:
////                        fragmentClass = Fragment_chi.class;
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new DoiMatKhau_Fragment()).commit();
                        break;
                    case R.id.abcde:
                        signOut();
                        Intent i=new Intent(getContext(),LoginActivity.class);
                        startActivity(i);
////                        fragmentClass = Fragment_chi.class;
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new Fragment_sach()).commit();
                        break;

//                    case R.id.gioithieu:
////                        Toast.makeText(MainActivity.this,"Đây là: giới thiệu",Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new KhoanTCFragment()).commit();
//                        break;

//                    case R.id.thoat:
////                        Toast.makeText(MainActivity.this,"Đây là: Exit",Toast.LENGTH_SHORT).show();
//                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
//                        startActivity(i);
//                        break;



                    default:
////                        fragmentClass=Fragment_thu.class;
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new Fragment_thu()).commit();
                }


                item.setChecked(true);
                getActivity().setTitle(item.getTitle());
                dr_ly.closeDrawers();
                return true;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        return view;
    }
    private ActionBarDrawerToggle setupDrawerToogle(){
        return new ActionBarDrawerToggle(getActivity(),dr_ly,tb,R.string.Open,R.string.Close);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"baibai",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });



    }



}
