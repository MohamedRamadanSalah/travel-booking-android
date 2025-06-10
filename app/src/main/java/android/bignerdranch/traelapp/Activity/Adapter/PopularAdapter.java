package android.bignerdranch.traelapp.Activity.Adapter;

import static androidx.core.content.ContextCompat.startActivity;
import android.annotation.SuppressLint;
import android.bignerdranch.traelapp.Activity.DetailActivity;
import android.bignerdranch.traelapp.Activity.Domain.Item;
import android.bignerdranch.traelapp.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {
    ArrayList<Item> items;
    Context context;

    public PopularAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.viewholder_popular, parent, false);
        return new Viewholder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {
        Item item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.address.setText(item.getAddress());
        holder.score.setText(String.valueOf(item.getScore()));
        holder.price.setText(  "$" + item.getPrice());
        Glide.with(context).load(item.getPic()).into(holder.imagepic);
        holder.itemView.setOnClickListener(v -> {

          Intent intent = new Intent(context , DetailActivity.class);
          intent.putExtra("object",items.get(position));
          context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title, address, score, price;
        ImageView imagepic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titile_TXT);
            address = itemView.findViewById(R.id.address_TXt);
            score = itemView.findViewById(R.id.score_TXT);
            price = itemView.findViewById(R.id.price_txt);
            imagepic = itemView.findViewById(R.id.pic);
        }
    }
}
