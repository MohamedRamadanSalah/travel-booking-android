package android.bignerdranch.traelapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.bignerdranch.traelapp.R;

public class SiginActivity2 extends AppCompatActivity {

    EditText nameEdt, emailEdt, passEdt, repassEdt;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin2);
        hideSystemUI();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                hideSystemUI();
            }
        });
        nameEdt = findViewById(R.id.nameEdt);
        emailEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.passwordEdt);
        repassEdt = findViewById(R.id.passwordEdt2);
        signupBtn = findViewById(R.id.button);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdt.getText().toString().trim();
                String email = emailEdt.getText().toString().trim();
                String pass = passEdt.getText().toString();
                String repass = repassEdt.getText().toString();

                if (!pass.equals(repass)) {
                    passEdt.setError("Passwords don't match");
                    repassEdt.setError("Passwords don't match");
                    return;
                }
                Intent intent = new Intent(SiginActivity2.this, ProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("password", pass);
                startActivity(intent);
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
}
