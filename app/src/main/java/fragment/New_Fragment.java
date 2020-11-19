package fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.duan1_coffee.R;
import com.example.duan1_coffee.news.XML_DOMParser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.NewsAdapter;
import model.NewsModel;

public class New_Fragment extends androidx.fragment.app.Fragment {
    RecyclerView rcvNews;
    adapter.NewsAdapter NewsAdapter;
    ArrayList<NewsModel> arrayTitle,arrayLink;
    ArrayList<NewsModel> list;
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
        rcvNews =view.findViewById(R.id.rcvNews);
        list = new ArrayList<NewsModel>();

        //url
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Readata().execute("https://cafeauto.vn/thi-truong/thi-truong-nuoc-ngoai/100.rss");
            }
        });

        //Xuat thong tin nguoi dung tu gmail
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
//            Uri personPhoto = acct.getPhotoUrl();

            nameGG.setText("Welcome!!! " +personName);
            gmailGG.setText(personEmail);
//            Glide.with(this).load(String.valueOf(personPhoto)).into(imgGG);
            Glide.with(this).load(acct.getPhotoUrl()).into(imgGG);

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
//            Glide.with(getActivity()).load(mUser.getPhotoUrl()).into(imgGG);
//            String imgUrl = "https://graph.facebook.com/";


            nameGG.setText(name);
            gmailGG.setText(email);
        }

        return view;
    }

     private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try {
            //create a urlobject
            URL url = new URL(theUrl);

            //create a url conection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }

    //AsyncTank
    class Readata extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String news) {
            XML_DOMParser parser = new XML_DOMParser();
            Document document = parser.getDocument(news);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String hinhanhVneTT = "";
            String titleVneTT = "";
            String linkVneTT = "";
            String dateVneTT = "";
            String cleanUrlVneTT = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                titleVneTT = "";

                String cdata = nodeListdescription.item(i+1).getTextContent();

                try {
                    if (cdata.contains(".jpg")) {
                        cleanUrlVneTT = cdata.substring(cdata.indexOf("https://i-vnexpress"), cdata.indexOf(".jpg\" >") + 4);
                        hinhanhVneTT = cleanUrlVneTT;
                    }

                    if (cdata.contains(".png")) {
                        cleanUrlVneTT = cdata.substring(cdata.indexOf("https://i-vnexpress"), cdata.indexOf(".png\" >") + 4);
                        hinhanhVneTT = cleanUrlVneTT;
                    }

                } catch (Exception e) {
                    Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher matcher = p.matcher(cdata);
                    if (matcher.find()) {
                        hinhanhVneTT = matcher.group(1);
                    }

                }

                Element element = (Element) nodeList.item(i);
                titleVneTT += parser.getValue(element, "title");
                linkVneTT = parser.getValue(element, "link");
                dateVneTT = parser.getValue(element, "pubDate");
                list.add(new NewsModel(titleVneTT, linkVneTT, hinhanhVneTT, dateVneTT));
            }


            LinearLayoutManager mn = new LinearLayoutManager(getActivity());
            NewsAdapter = new NewsAdapter(list, getActivity());
            rcvNews.setAdapter(NewsAdapter);
            rcvNews.setLayoutManager(mn);
            NewsAdapter.notifyDataSetChanged();
            super.onPostExecute(news);
        }


    }
}
