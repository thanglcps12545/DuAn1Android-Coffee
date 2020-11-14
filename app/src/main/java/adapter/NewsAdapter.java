package adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fragment.ViewFragment;
import model.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    ArrayList<NewsModel> list;
    Context context;

    public NewsAdapter(ArrayList<NewsModel> list, Context context) {
        this.list = list;
        this.context = context; 
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, final int position) {
        NewsModel Newsmodel =list.get(position);
        holder.tv_date.setText(Newsmodel.dateTinTucVne);
        holder.tv_title.setText(Newsmodel.titleTinTucVne);
        Picasso.get().load(Newsmodel.hinhanhTinTucVne).into(holder.imageView);
        holder.cardViewVneTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewFragment View_Fragment = new ViewFragment();
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.fragment_container, View_Fragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("link", list.get(position).getLinkTinTucVne());
                View_Fragment.setArguments(bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date, tv_title;
        ImageView imageView;
        CardView cardViewVneTinTuc;
        public ViewHolder(@NonNull View view) {
            super(view);
            tv_date = view.findViewById(R.id.tv_dateVneTinTuc);
            tv_title = (TextView) view.findViewById(R.id.tv_titleVneTinTuc);
            imageView = view.findViewById(R.id.imgVneTinTuc);
            cardViewVneTinTuc= (CardView) itemView.findViewById(R.id.cvVneTinTuc);
        }
    }
}
