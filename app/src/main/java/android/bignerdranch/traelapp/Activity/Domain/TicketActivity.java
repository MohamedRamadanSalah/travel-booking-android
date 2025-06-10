package android.bignerdranch.traelapp.Activity.Domain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.traelapp.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class TicketActivity extends AppCompatActivity {
    private Item object;
    private ImageView pic1 , pic22 , back , call , message;
    private TextView title , Duration , tourname , timeext , tour , price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        hideSystemUI();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                hideSystemUI();
            }
        });
        pic1 = findViewById(R.id.ticketimage);
        pic22 = findViewById(R.id.imapepicc);
        back = findViewById(R.id.backimage);
        title = findViewById(R.id.titleticket);
        Duration = findViewById(R.id.duration_txt);
        tourname = findViewById(R.id.tour_guide_name);
        timeext = findViewById(R.id.timetextreally);
        tour = findViewById(R.id.timeticket);
        call = findViewById(R.id.call_image);
        message = findViewById(R.id.message_image);
        price = findViewById(R.id.guest_txt);
        getIntentExtra();
        setvariable();
    }

    private void setvariable() {
        Glide.with(TicketActivity.this)
                .load(object.getPic())
                .into(pic1);
        Glide.with(TicketActivity.this)
                .load(object.getPic())
                .into(pic22);
        back.setOnClickListener(v -> finish());
        title.setText(object.getTitle());
        Duration.setText(object.getDuration());
        tourname.setText(object.getTourGuideName());
        timeext.setText(object.getTimeTour());
        tour.setText(object.getDateTour());
        price.setText(String.valueOf(object.getPrice()));
        message.setOnClickListener(v -> {
            try {
                Intent sendintent = new Intent(Intent.ACTION_VIEW);
                sendintent.setData(Uri.parse("sms:" + object.getTourGuidePhone())); // أضف النقطتين هنا
                sendintent.putExtra("sms_body", "type your message");


                if (sendintent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendintent);
                } else {
                    Toast.makeText(this, "No SMS app found", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error opening SMS app", Toast.LENGTH_SHORT).show();
            }
        });

        call.setOnClickListener(v -> {
            try {
                String Phone = object.getTourGuidePhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + Phone));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No dialer app found", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error opening dialer", Toast.LENGTH_SHORT).show();
            }
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
    private void getIntentExtra() {
        object = (Item) getIntent().getSerializableExtra("object");
    }
}