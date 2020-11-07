package fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.duan1_coffee.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class New_Fragment extends androidx.fragment.app.Fragment {
    Button logout;
    GoogleSignInClient mGoogleSignInClient;
    ImageView imgGG;
    TextView nameGG,gmailGG;
    String email,name;
    LoginButton logoutFB;
    CallbackManager callbackManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new, container, false);
        //Anh xa
        imgGG=view.findViewById(R.id.imgGG);
        nameGG=view.findViewById(R.id.nameGG);
        gmailGG=view.findViewById(R.id.gmailGG);

        //Xuat thong tin nguoi dung tu gmail
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            nameGG.setText("Welcome!!! " +personName);
            gmailGG.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imgGG);

        }

        //Button Logout gmail
        logout=view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.btnLogout:
                        signOut();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        return view;
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
