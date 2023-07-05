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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zachetnoe_zadanie.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    private EditText login_f, password_f;
//    private String login, password;
    private Button button_login, button_register;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    private RelativeLayout login_page;
//    ConstraintLayout main_page;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        LayoutInflater inflater_main = LayoutInflater.from(this);
//        View main_page = inflater_main.inflate(R.layout.activity_main, null);
        login_f = findViewById(R.id.login);
//        login = login_f.getText().toString();
        password_f = findViewById(R.id.password);
//        password = password_f.getText().toString();
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);
        login_page = findViewById(R.id.login_page);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://zachetnoe-zadanie-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = db.getReference();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegWindow();
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(login_f.getText().toString())){
                    Snackbar.make(login_page,"поле \"логин\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password_f.getText().toString())){
                    Snackbar.make(login_page,"поле \"пароль\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                signIn();

            }
        });

    }




    private void signIn() {
        auth.signInWithEmailAndPassword(login_f.getText().toString(),password_f.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Snackbar.make(login_page, "Вы успешно вошли!", Snackbar.LENGTH_SHORT).show();
                        //начало
//                        dbRef.child("Users").child(userId).child("post").addValueEventListener(new ValueEventListener() {
                        dbRef.child("Users").child(userId).child("post") .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String value = snapshot.getValue(String.class);
                                        if (value.equals("вожатый")) {
                                            //Snackbar.make(login_page, "Вы вошли как Вожатый!", Snackbar.LENGTH_LONG).show();
                                            startActivity(new Intent(LoginActivity.this, VojatiyActivity.class));
                                            finish();
                                        }
                                        if (value.equals("волонтер")) {
                                            //Snackbar.make(login_page, "Вы вошли как Волонтер!", Snackbar.LENGTH_LONG).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }
                                        if (!(value.equals("вожатый")) & (!(value.equals("волонтер")))) {
                                            //Snackbar.make(login_page, "Вы вошли как ---"+value+"--- !", Snackbar.LENGTH_LONG).show();
                                            startActivity(new Intent(LoginActivity.this, LoserActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(LoginActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        //конец







                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(login_page, "Ошибка авторизации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRegWindow() {
        AlertDialog.Builder register = new AlertDialog.Builder(this);
        register.setTitle("Регистрация");
        LayoutInflater inflater = LayoutInflater.from(this);
        View regWindow = inflater.inflate(R.layout.activity_register, null);
        register.setView(regWindow);
        final MaterialEditText name = regWindow.findViewById(R.id.nameField);
        final MaterialEditText surname = regWindow.findViewById(R.id.surnameField);
        final MaterialEditText patronymic = regWindow.findViewById(R.id.patronymicField);
        final Spinner post = regWindow.findViewById(R.id.postField);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.post_users, android.R.layout.simple_spinner_dropdown_item);
        post.setAdapter(adapter);
        final MaterialEditText division = regWindow.findViewById(R.id.divisionField);
        final MaterialEditText phone = regWindow.findViewById(R.id.phoneField);
        final MaterialEditText mailAndLogin = regWindow.findViewById(R.id.mailField);
        final MaterialEditText password = regWindow.findViewById(R.id.passField);


        register.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        register.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(login_page,"поле \"Имя\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(surname.getText().toString())) {
                    Snackbar.make(login_page,"поле \"Фамилия\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(patronymic.getText().toString())) {
                    Snackbar.make(login_page,"поле \"Отчество\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
//                if(TextUtils.isEmpty(post.getText().toString())) {
//                    Snackbar.make(login_page,"поле \"Волонтер/Вожатый\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
//                    return;
//                }
                if(TextUtils.isEmpty(division.getText().toString())) {
                    Snackbar.make(login_page,"поле \"Дивизион\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(login_page,"поле \"Номер телефона\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mailAndLogin.getText().toString())) {
                    Snackbar.make(login_page,"поле \"Почта\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())) {
                    Snackbar.make(login_page,"поле \"пароль\" должно быть заполнено", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                // регистрация
                auth.createUserWithEmailAndPassword(mailAndLogin.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User(name.getText().toString(), surname.getText().toString(), patronymic.getText().toString(),
                                            post.getSelectedItem().toString(), division.getText().toString(), phone.getText().toString(), mailAndLogin.getText().toString(),
                                            password.getText().toString());

                                dbRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(login_page, "Пользователь добавлен.", Snackbar.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(login_page, "ошибка при добавлении пользователя в базу данных!: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        });
//                                dbRef.child("Participants").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .child("mail").setValue(mailAndLogin.getText().toString())
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Snackbar.make(login_page, "ошибка: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
//                                            }
//                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(login_page, "Пользователь с такой почтой уже существует! " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        });

            }
        });

        register.show();
    }
}