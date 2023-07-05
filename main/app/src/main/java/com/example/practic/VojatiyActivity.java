package com.example.practic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.zachetnoe_zadanie.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VojatiyActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private Button sign_out_button, add_event_button, add_participant_button;
    private ConstraintLayout vojatiy_page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vojatiy);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://zachetnoe-zadanie-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = db.getReference();
        vojatiy_page = findViewById(R.id.vojatiy_page);
        sign_out_button = findViewById(R.id.sing_out_button2);
        add_event_button = findViewById(R.id.add_event_button);
        add_participant_button = findViewById(R.id.add_participant_button);
        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(VojatiyActivity.this, LoginActivity.class));
                finish();
            }
        });
        add_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEventWindow();
            }
        });
        add_participant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddParticipantWindow();
            }
        });
    }

    private void showAddParticipantWindow() {
        AlertDialog.Builder addPart = new AlertDialog.Builder(this);
        addPart.setTitle("Добавление участника");
        LayoutInflater inflater = LayoutInflater.from(this);
        View addParticipantWindow = inflater.inflate(R.layout.activity_add_participant, null);
        addPart.setView(addParticipantWindow);
        final MaterialEditText participant_name = addParticipantWindow.findViewById(R.id.participantMailField);
        final MaterialEditText event_name = addParticipantWindow.findViewById(R.id.eventNameField2);
        addPart.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        addPart.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(participant_name.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Почта участника\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(event_name.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Название мероприятия\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                String[] participants_help = participant_name.getText().toString().split(", ");
                for (String s : participants_help){
                    s = s.replaceAll("\\.", ",");
                    dbRef.child("Events_Participants").child(event_name.getText().toString())
                        .child("participants").child(s).setValue(s)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(vojatiy_page, "Участник добавлен.", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(vojatiy_page, "Ошибка!: "+ e, Snackbar.LENGTH_LONG).show();
                            }
                        });
                }


            }
        });
        addPart.show();
    }

    private void showAddEventWindow(){
        AlertDialog.Builder addEvent = new AlertDialog.Builder(this);
        addEvent.setTitle("Добавление мероприятия");
        LayoutInflater inflater = LayoutInflater.from(this);
        View addEventWindow = inflater.inflate(R.layout.activity_add_event, null);
        addEvent.setView(addEventWindow);
        final MaterialEditText event_name = addEventWindow.findViewById(R.id.eventNameField);
        final MaterialEditText date = addEventWindow.findViewById(R.id.dateField);
        final MaterialEditText organizer = addEventWindow.findViewById(R.id.organizerField);
        final MaterialEditText place = addEventWindow.findViewById(R.id.placeField);
        final MaterialEditText participants = addEventWindow.findViewById(R.id.participantsField);
        final MaterialEditText direction = addEventWindow.findViewById(R.id.directionField);
        addEvent.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        addEvent.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(event_name.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Название\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(date.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Дата\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(organizer.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Организатор\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(place.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Место проведения\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(direction.getText().toString())) {
                    Snackbar.make(vojatiy_page,"поле \"Направление\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
//                List<String> participants_help = Arrays.asList(participants.getText().toString().split(", "));
//                String partSt = participants.getText().toString();
                String[] participants_help = participants.getText().toString().split(", ");
                String unique_key = dbRef.child("Events").push().getKey();
                Event event = new Event(unique_key, date.getText().toString(), organizer.getText().toString(), place.getText().toString(),
                              direction.getText().toString(), event_name.getText().toString());

                dbRef.child("Events").child(event_name.getText().toString()).setValue(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(vojatiy_page, "Событие добавлено.", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(vojatiy_page, "Ошибка!: "+ e, Snackbar.LENGTH_LONG).show();
                            }
                        });
                for (String s : participants_help) {
                    s = s.replaceAll("\\.", ",");
                    dbRef.child("Events_Participants").child(event_name.getText().toString()).child("participants")
                        .child(s).setValue(s)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(vojatiy_page, "Ошибка при добавлении участников!: "+ e, Snackbar.LENGTH_LONG).show();
                            }
                        });}




            }
        });
    addEvent.show();
    }
}