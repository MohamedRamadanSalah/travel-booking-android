package android.bignerdranch.traelapp.Activity;

import android.annotation.SuppressLint;
import android.bignerdranch.traelapp.Activity.Adapter.CategoryAdapter;
import android.bignerdranch.traelapp.Activity.Adapter.PopularAdapter;
import android.bignerdranch.traelapp.Activity.Adapter.RecommendedAdapter;
import android.bignerdranch.traelapp.Activity.Adapter.Slider_Adapter;
import android.bignerdranch.traelapp.Activity.Domain.Category;
import android.bignerdranch.traelapp.Activity.Domain.Item;
import android.bignerdranch.traelapp.Activity.Domain.Location;
import android.bignerdranch.traelapp.Activity.Domain.SliderItems;
import android.bignerdranch.traelapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Firebase
    private FirebaseDatabase database;

    // Views
    private Spinner spinner;
    private ViewPager2 viewPager;
    private ProgressBar bannerProgress, categoryProgress, popularProgress, recommendedProgress;
    private RecyclerView categoryRecycler, popularRecycler, recommendedRecycler;

    // Lists
    private final ArrayList<Location> locationList = new ArrayList<>();
    private final ArrayList<Category> categoryList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideSystemUI();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                hideSystemUI();
            }
        });

        initViews();
        database = FirebaseDatabase.getInstance();
        loadLocations();
        loadBanners();
        loadCategories();
        loadPopularItems();
        loadRecommendedItems();
        ChipNavigationBar navigationView = findViewById(R.id.bottom_menu);
        navigationView.setOnItemSelectedListener(itemId -> {
            if (itemId == R.id.cart) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return;
            }
        });

    }

    private void initViews() {
        spinner = findViewById(R.id.locationsnipper);
        viewPager = findViewById(R.id.viewPager2);
        bannerProgress = findViewById(R.id.progressBarbanner);
        categoryRecycler = findViewById(R.id.recyclerViewcategory);
        categoryProgress = findViewById(R.id.progressBar3);
        popularRecycler = findViewById(R.id.recyclerView2);
        popularProgress = findViewById(R.id.progressBar2);
        recommendedRecycler = findViewById(R.id.recyclerViewRecommended);
        recommendedProgress = findViewById(R.id.progressBarRecommended);
    }

    private void loadLocations() {
        DatabaseReference ref = database.getReference("Location");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Location location = item.getValue(Location.class);
                    if (location != null) locationList.add(location);
                }

                ArrayAdapter<Location> adapter = new ArrayAdapter<>(
                        MainActivity.this, R.layout.sp_item, locationList
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("Failed to load locations: " + error.getMessage());
            }
        });
    }

    private void loadCategories() {
        categoryProgress.setVisibility(View.VISIBLE);
        DatabaseReference ref = database.getReference("Category");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Category category = item.getValue(Category.class);
                    if (category != null) categoryList.add(category);
                }

                if (!categoryList.isEmpty()) {
                    categoryRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    categoryRecycler.setAdapter(new CategoryAdapter(MainActivity.this, categoryList));
                }
                categoryProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("Failed to load categories: " + error.getMessage());
                categoryProgress.setVisibility(View.GONE);
            }
        });
    }

    private void loadPopularItems() {
        popularProgress.setVisibility(View.VISIBLE);
        DatabaseReference ref = database.getReference("Popular");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Item> popularItems = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Item popular = item.getValue(Item.class);
                    if (popular != null) popularItems.add(popular);
                }

                if (!popularItems.isEmpty()) {
                    popularRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    popularRecycler.setAdapter(new PopularAdapter(popularItems));
                }

                popularProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("Failed to load popular items: " + error.getMessage());
                popularProgress.setVisibility(View.GONE);
            }
        });
    }

    private void loadRecommendedItems() {
        recommendedProgress.setVisibility(View.VISIBLE);
        DatabaseReference ref = database.getReference("Item");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Item> items = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Item recommended = item.getValue(Item.class);
                    if (recommended != null) items.add(recommended);
                }

                if (!items.isEmpty()) {
                    recommendedRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    recommendedRecycler.setAdapter(new RecommendedAdapter(MainActivity.this, items));
                } else {
                    showToast("No recommended items available.");
                }

                recommendedProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("Failed to load recommended items: " + error.getMessage());
                recommendedProgress.setVisibility(View.GONE);
            }
        });
    }

    private void loadBanners() {
        bannerProgress.setVisibility(View.VISIBLE);
        DatabaseReference ref = database.getReference("Banner");
        ArrayList<SliderItems> bannerList = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    SliderItems banner = item.getValue(SliderItems.class);
                    if (banner != null) bannerList.add(banner);
                }

                setupBannerSlider(bannerList);
                bannerProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("Failed to load banners: " + error.getMessage());
                bannerProgress.setVisibility(View.GONE);
            }
        });
    }

    private void setupBannerSlider(ArrayList<SliderItems> banners) {
        viewPager.setAdapter(new Slider_Adapter(banners, viewPager));
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        viewPager.setPageTransformer(transformer);
    }

    private void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}
