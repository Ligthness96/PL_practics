package com.example.practic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zachetnoe_zadanie.models.Event;
import com.example.zachetnoe_zadanie.models.User;
import com.example.zachetnoe_zadanie.view_holders.events_holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private ConstraintLayout main_page;
    private Button sign_out_button, request_button;
    private ImageButton ranks;

    private RecyclerView events_rec;
    RecyclerView.LayoutManager LayMan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_page = findViewById(R.id.main_page);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://zachetnoe-zadanie-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = db.getReference();
        ranks = findViewById(R.id.imageButtonMedal);
        request_button = findViewById(R.id.request_button);
        sign_out_button = findViewById(R.id.sing_out_button3);
        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        ranks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RankActivity0.class));
                finish();
            }
        });
        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestWindow();
            }
        });
        events_rec = findViewById(R.id.RecyclingEvents);
        events_rec.setHasFixedSize(true);
        LayMan = new LinearLayoutManager(this);
        events_rec.setLayoutManager(LayMan);
    }

    private void showRequestWindow() {
        AlertDialog.Builder request = new AlertDialog.Builder(this);
        request.setTitle("Заполнение заявки");
        LayoutInflater inflater = LayoutInflater.from(this);
        View requestWindow = inflater.inflate(R.layout.activity_request, null);
        request.setView(requestWindow);
        final String usermail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String rightuser = usermail.replaceAll("\\.", ",");
        String unique_key = dbRef.child("Requests").push().getKey();
        final MaterialEditText name = requestWindow.findViewById(R.id.requestNameField);
        request.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        request.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(main_page ,"поле \"Название мероприятия\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
//                String unique_key = dbRef.child("Requests").push().getKey();
//                String usermail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                String rightuser = usermail.replaceAll("\\.", ",");
                dbRef.child("Requests").child(unique_key).child(rightuser).setValue(name.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(main_page, "Заявка отправлена.", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(main_page, "ошибка при отправке заявки!: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        });
            }
        });
        request.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(dbRef.child("Events"), Event.class).build();
        FirebaseRecyclerAdapter<Event, events_holder> adapter = new FirebaseRecyclerAdapter<Event, events_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull events_holder holder, int i, @NonNull Event model) {
                holder.event_name.setText(model.getName());
                holder.date.setText(" Дата: "+ model.getDate());
                holder.direction.setText(" Направление: "+model.getDirection());
                holder.organizer.setText(" Организатор: "+model.getOrganizer());
                holder.place.setText(" Место проведения: "+model.getPlace());
                holder.id.setText(" id: "+model.getId());
            }

            @NonNull
            @Override
            public events_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list, parent, false);
                events_holder holder = new events_holder(view);
                return holder;
            }
        };

        events_rec.setAdapter(adapter);
        adapter.startListening();
    }
}




