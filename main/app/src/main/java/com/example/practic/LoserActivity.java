package com.example.practic;



import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LoserActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button sign_out_button;
    private ConstraintLayout loser_page;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loser);
        loser_page = findViewById(R.id.page_loser);
        auth = FirebaseAuth.getInstance();
        sign_out_button = findViewById(R.id.button_sign_out);
        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(LoserActivity.this, LoginActivity.class));
                finish();
            }
        });




    }
}