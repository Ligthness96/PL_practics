package com.example.practic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RankActivity0 extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private ImageView copper, bronze, silver, gold, diamond;
    private TextView rank_text1, rank_text2, rank_text3;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank0);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://zachetnoe-zadanie-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = db.getReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        back_button = findViewById(R.id.button_back);
        copper = findViewById(R.id.Copper);
        bronze = findViewById(R.id.Bronze);
        silver = findViewById(R.id.Silver);
        gold = findViewById(R.id.Gold);
        diamond = findViewById(R.id.Diamond);
        rank_text1 = findViewById(R.id.textRanks1);
        rank_text2 = findViewById(R.id.textRanks2);
        rank_text3 = findViewById(R.id.textRanks3);

        dbRef.child("Users").child(userId).child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int points = snapshot.getValue(Integer.class);
                if (points < 201){
                    int last = 201 - points;
                    copper.setVisibility(View.VISIBLE);
                    bronze.setVisibility(View.GONE);
                    silver.setVisibility(View.GONE);
                    gold.setVisibility(View.GONE);
                    diamond.setVisibility(View.GONE);
                    rank_text1.setText("Ваши очки: "+points);
                    rank_text2.setText("До следующего ранга (Бронза) осталось: "+last);
                    rank_text3.setText("Ваш ранг: Медь");
                }
                if ((points > 200) & (points < 401)){
                    int last = 401 - points;
                    copper.setVisibility(View.GONE);
                    bronze.setVisibility(View.VISIBLE);
                    silver.setVisibility(View.GONE);
                    gold.setVisibility(View.GONE);
                    diamond.setVisibility(View.GONE);
                    rank_text1.setText("Ваши очки: "+points);
                    rank_text2.setText("До следующего ранга (Серебро) осталось: "+last);
                    rank_text3.setText("Ваш ранг: Бронза");
                }
                if ((points > 400) & (points < 601)){
                    int last = 601 - points;
                    copper.setVisibility(View.GONE);
                    bronze.setVisibility(View.GONE);
                    silver.setVisibility(View.VISIBLE);
                    gold.setVisibility(View.GONE);
                    diamond.setVisibility(View.GONE);
                    rank_text1.setText("Ваши очки: "+points);
                    rank_text2.setText("До следующего ранга (Золото) осталось: "+last);
                    rank_text3.setText("Ваш ранг: Серебро");
                }
                if ((points > 600) & (points < 801)){
                    int last = 801 - points;
                    copper.setVisibility(View.GONE);
                    bronze.setVisibility(View.GONE);
                    silver.setVisibility(View.GONE);
                    gold.setVisibility(View.VISIBLE);
                    diamond.setVisibility(View.GONE);
                    rank_text1.setText("Ваши очки: "+points);
                    rank_text2.setText("До следующего ранга (Бриллиант) осталось: "+last);
                    rank_text3.setText("Ваш ранг: Золото");
                }
                if (points > 800){
                    copper.setVisibility(View.GONE);
                    bronze.setVisibility(View.GONE);
                    silver.setVisibility(View.GONE);
                    gold.setVisibility(View.GONE);
                    diamond.setVisibility(View.VISIBLE);
                    rank_text1.setText("Ваши очки: "+points);
                    rank_text2.setText("Поздравляем! У вас максимальный ранг!");
                    rank_text3.setText("Ваш ранг: Бриллиант");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RankActivity0.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RankActivity0.this, MainActivity.class));
                finish();
            }
        });
    }
}