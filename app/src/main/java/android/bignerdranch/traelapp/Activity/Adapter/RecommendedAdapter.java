package android.bignerdranch.traelapp.Activity.Adapter;

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

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.Viewholder> {
    private final ArrayList<Item> items;
    private final Context context;

    public RecommendedAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.viewholder_recommended, parent, false);
        return new Viewholder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Item item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvAddress.setText(item.getAddress());
        holder.tvRating.setText(String.valueOf(item.getScore()));
        holder.tvPrice.setText("$" + item.getPrice());

        if (item.getPic() != null && !item.getPic().isEmpty()) {
            Glide.with(context).load(item.getPic()).into(holder.ivPlaceImage);
        }

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

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAddress, tvRating, tvPrice;
        ImageView ivPlaceImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_placeTitle);
            tvAddress = itemView.findViewById(R.id.tv_placeAddress);
            tvRating = itemView.findViewById(R.id.tv_placeRating);
            tvPrice = itemView.findViewById(R.id.tv_placePrice);
            ivPlaceImage = itemView.findViewById(R.id.iv_placeImage);
        }
    }

}
