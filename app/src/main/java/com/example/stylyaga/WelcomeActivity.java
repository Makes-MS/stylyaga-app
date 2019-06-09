package com.example.stylyaga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stylyaga.Model.Users;
import com.example.stylyaga.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class WelcomeActivity extends AppCompatActivity {

    private Button JoinButton, LoginButton;
    private ProgressDialog LoadingBar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        JoinButton = (Button)findViewById(R.id.join_button);
        LoginButton = (Button) findViewById(R.id.login_button);
        LoadingBar = new ProgressDialog(this);

        Paper.init(this);

        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent join = new Intent(WelcomeActivity.this, JoinActivity.class);
                startActivity(join);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent login = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        String UserLoginKey = Paper.book().read(Prevalent.UserLoginKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserLoginKey != "" && UserPasswordKey != ""){
            if (!TextUtils.isEmpty(UserLoginKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserLoginKey, UserPasswordKey);

                LoadingBar.setTitle("Authorization");
                LoadingBar.setMessage("Please wait...");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();
            }
        }
    }

    private void AllowAccess(final String Login, final String Password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(Login).exists()){
                    Users usersData = dataSnapshot.child(parentDbName).child(Login).getValue(Users.class);
                    if (usersData.getLogin().equals(Login)){
                        if (usersData.getPassword().equals(Password)){
                            if (parentDbName.equals(parentDbName)){
                                Toast.makeText(WelcomeActivity.this, "Авторизація успішно виконана!", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                Toast.makeText(WelcomeActivity.this, "123", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            } else {
                                LoadingBar.dismiss();
                                Toast.makeText(WelcomeActivity.this, "Пароль не вірний!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(WelcomeActivity.this, "Account with this " + Login + " login do not exist.", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}