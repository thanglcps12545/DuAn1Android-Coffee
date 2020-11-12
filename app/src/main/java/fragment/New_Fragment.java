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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class New_Fragment extends androidx.fragment.app.Fragment {
    GoogleSignInClient mGoogleSignInClient;
    ImageView imgGG;
    TextView nameGG,gmailGG;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new, container, false);
        //Initialise firebase
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser=mAuth.getCurrentUser();
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);



        //xuat thong tin nguoi dung gmail
        if(mUser != null){
            String name= mUser.getDisplayName();
            String email= mUser.getEmail();
            String photoURL= mUser.getPhotoUrl().toString();
            Glide.with(getActivity()).load(photoURL).into(imgGG);
            nameGG.setText(name);
            gmailGG.setText(email);
        }

        return view;
    }
}
