package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TaiKhoan_Fragment extends androidx.fragment.app.Fragment {
    TextInputEditText fullName,name,email,phone,pass;
    Button btnUpdateUser;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tk, container, false);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        //Anh xa
        fullName = view.findViewById(R.id.fullname);
        name = view.findViewById(R.id.tkUser);
        email = view.findViewById(R.id.emailUser);
        phone =view.findViewById(R.id.sdtUser);
        pass = view.findViewById(R.id.passUser);
        btnUpdateUser=view.findViewById(R.id.btnUpdateUser);
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                String user_username = intent.getStringExtra("username");
                String user_name = intent.getStringExtra("name");
                String user_email = intent.getStringExtra("email");
                String user_phoneNo = intent.getStringExtra("phone");

                if(!fullName.equals(fullName.getText().toString())){
                    reference.child(user_username).child("name").setValue(fullName.getText().toString());
                }
                if(!email.equals(email.getText().toString())){
                    reference.child(user_username).child("email").setValue(email.getText().toString());
                }
                if(!phone.equals(phone.getText().toString())){
                    reference.child(user_username).child("phone").setValue(phone.getText().toString());
                }

            }
        });

      showAllUserData();


        return view;
    }

    private void showAllUserData() {
        Intent intent = getActivity().getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phone");
        String user_password = intent.getStringExtra("password");

        fullName.setText(user_name);
        name.setText(user_username);
        email.setText(user_email);
        phone.setText(user_phoneNo);
        pass.setText(user_password);
    }



}
