package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.R;
import com.google.android.material.navigation.NavigationView;

public class User_Fragment extends androidx.fragment.app.Fragment {
    DrawerLayout dr_ly;
    Toolbar tb;
    NavigationView nv;
    ActionBarDrawerToggle drawerToggle;
    //    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);


        return view;
    }
}
