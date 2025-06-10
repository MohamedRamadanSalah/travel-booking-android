package android.bignerdranch.traelapp.Activity.Adapter;

import android.bignerdranch.traelapp.Activity.Domain.SliderItems;
import android.bignerdranch.traelapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Slider_Adapter extends RecyclerView.Adapter<Slider_Adapter.SliderViewholder> {
    private ArrayList<SliderItems> Sliders;
    private ViewPager2 mViewPager2;
    private Context context;

    public Slider_Adapter(ArrayList<SliderItems> sliders, ViewPager2 viewPager2) {
        this.Sliders = sliders;
        this.mViewPager2 = viewPager2;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Sliders.addAll(Sliders);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item_container, parent, false);
        return new SliderViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewholder holder, int position) {
        holder.setImage(Sliders.get(position));
        if (position == Sliders.size() - 2) {
            mViewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return Sliders.size();
    }

    public class SliderViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SliderViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageslider);
        }

        void setImage(SliderItems sliderItem) {
            Glide.with(context)
                    .load(sliderItem.getUrl())
                    .into(imageView);
        }
    }
}
