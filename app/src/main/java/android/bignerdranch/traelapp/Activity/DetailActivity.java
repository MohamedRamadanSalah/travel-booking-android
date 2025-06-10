package android.bignerdranch.traelapp.Activity;

import android.annotation.SuppressLint;
import android.bignerdranch.traelapp.Activity.Domain.Item;
import android.bignerdranch.traelapp.Activity.Domain.TicketActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.traelapp.R;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private Item object;
    private TextView title, decr, min, distance, bed, price, address, texrate;
    private RatingBar rate;
    private Button  cartButton;
    private ImageView pic ,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        hideSystemUI();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                hideSystemUI();
            }
        });

        initViews();
        getIntentExtra();
        setVariables();
    }

    private void initViews() {
        title = findViewById(R.id.Detailtitle);
        decr = findViewById(R.id.textView15);
        distance = findViewById(R.id.distance);
        bed = findViewById(R.id.bed_btn);
        min = findViewById(R.id.min);
        pic = findViewById(R.id.detailimage);
        price = findViewById(R.id.pricebtn);
        back = findViewById(R.id.back_btn);
        address = findViewById(R.id.Detailaddress);
        rate = findViewById(R.id.ratingBar);
        texrate = findViewById(R.id.ratingew10);
        cartButton = findViewById(R.id.addtocartbutton);
    }

    private void getIntentExtra() {
        if (getIntent() != null && getIntent().getSerializableExtra("object") != null) {
            object = (Item) getIntent().getSerializableExtra("object");
        } else {
            finish();
        }
    }

    private void setVariables() {
        if (object == null) return;

        title.setText(object.getTitle());
        decr.setText(object.getDescription());
        distance.setText(object.getDistance());
        bed.setText(String.valueOf(object.getBed()));
        price.setText("$" + object.getPrice());
        min.setText(object.getDuration());
        address.setText(object.getAddress());
        rate.setRating((float) object.getScore());
        texrate.setText("Rating: " + object.getScore() + " ★");

        Glide.with(this)
                .load(object.getPic())
                .into(pic);

        back.setOnClickListener(v -> finish());

        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, TicketActivity.class);
            intent.putExtra("object",object);
            startActivity(intent);
        });
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
