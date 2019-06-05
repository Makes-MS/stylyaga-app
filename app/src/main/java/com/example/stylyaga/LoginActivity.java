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

import com.example.stylyaga.Model.Users;
import com.example.stylyaga.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputLogin, InputPassword;
    private Button LoginButton;
    private ProgressDialog LoadingBar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputLogin = (EditText) findViewById(R.id.input_login);
        InputPassword = (EditText) findViewById(R.id.input_password);
        LoginButton = (Button) findViewById(R.id.login_button);
        LoadingBar = new ProgressDialog(this);

        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser(){
        String Login = InputLogin.getText().toString();
        String Password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(Login)) {
            Toast.makeText(this, "Write login!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Write password!", Toast.LENGTH_SHORT).show();
        } else {
            LoadingBar.setTitle("Authorization");
            LoadingBar.setMessage("Please wait...");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            AllowAssetsToAccount(Login, Password);
        }
    }

    private void AllowAssetsToAccount(final String Login, final String Password){
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
                                Toast.makeText(LoginActivity.this, "Авторизація успішно виконана!", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "123", Toast.LENGTH_SHORT).show();

                            } else {
                                LoadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Пароль не вірний!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account with this " + Login + " login do not exist.", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}