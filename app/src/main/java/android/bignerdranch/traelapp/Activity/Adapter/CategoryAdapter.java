package android.bignerdranch.traelapp.Activity.Adapter;

import android.annotation.SuppressLint;
import android.bignerdranch.traelapp.Activity.Domain.Category;
import android.bignerdranch.traelapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    private final List<Category> items;
    private int SelectedPosition = -1;
    private int LastSelectedPosition = -1;
    private Context context;

    public CategoryAdapter(Context context, List<Category> items) {
        this.context = context;
        this.items = items;
    }

    public CategoryAdapter(ArrayList<Category> listCategeory) {
        this.items = listCategeory;
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Category item = items.get(position);
        holder.textv.setText(item.getName());

        Glide.with(holder.itemView.getContext())
                .load(item.getImagePath())
                .into(holder.image);

        holder.textv.setTextColor(context.getResources().getColor(R.color.white));

        if (SelectedPosition == position) {
            holder.image.setBackgroundResource(0);
            holder.mainLayout.setBackgroundResource(R.drawable.purple_bg);
            holder.textv.setVisibility(View.VISIBLE);

        } else {
            holder.image.setBackgroundResource(R.drawable.grey_bg);
            holder.mainLayout.setBackgroundResource(0);
            holder.textv.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            LastSelectedPosition = SelectedPosition;
            SelectedPosition = position;
            notifyItemChanged(LastSelectedPosition);
            notifyItemChanged(SelectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textv;
        LinearLayout mainLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.PIC);
            textv = itemView.findViewById(R.id.titleTXT);
            mainLayout = itemView.findViewById(R.id.mainlayout);
        }
    }
}
