package com.example.stylyaga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class JoinActivity extends AppCompatActivity {

    private EditText InputLogin, InputPassword;
    private Button JoinButton;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        FirebaseApp.initializeApp(this);

        InputLogin = (EditText) findViewById(R.id.input_login);
        InputPassword = (EditText) findViewById(R.id.input_password);
        JoinButton = (Button) findViewById(R.id.button_join);
        LoadingBar = new ProgressDialog(this);

        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String Login = InputLogin.getText().toString();
        String Password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(Login)){
            Toast.makeText(this, "Write login!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Write password!", Toast.LENGTH_SHORT).show();
        } else {
            LoadingBar.setTitle("Create account");
            LoadingBar.setMessage("Please wait...");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            ValidateData(Login, Password);
        }
    }

    private void ValidateData(final String Login, final String Password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(Login).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("Login", Login);
                    userDataMap.put("Password", Password);

                    RootRef.child("Users").child(Login).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(JoinActivity.this, "Вітаємо!", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                                Intent join = new Intent(JoinActivity.this, LoginActivity.class);
                                startActivity(join);
                            } else {
                                LoadingBar.dismiss();
                                Toast.makeText(JoinActivity.this, "Помилка!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(JoinActivity.this, "This " + Login + " already exist.", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(JoinActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent join = new Intent(JoinActivity.this, WelcomeActivity.class);
                    startActivity(join);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
