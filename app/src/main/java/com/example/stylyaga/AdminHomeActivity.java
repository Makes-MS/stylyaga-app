package com.example.stylyaga;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminHomeActivity extends AppCompatActivity {

    private ImageView cat1, cat2, cat3, cat4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        cat1 = (ImageView) findViewById(R.id.cat1);
        cat2 = (ImageView) findViewById(R.id.cat2);
        cat3 = (ImageView) findViewById(R.id.cat3);
        cat4 = (ImageView) findViewById(R.id.cat4);

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCategoryActivity.class);
                intent.putExtra("Category", "cat1");
                startActivity(intent);
            }
        });

        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCategoryActivity.class);
                intent.putExtra("Category", "cat2");
            }
        });

        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCategoryActivity.class);
                intent.putExtra("Category", "cat3");
                startActivity(intent);
            }
        });

        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCategoryActivity.class);
                intent.putExtra("Category", "cat4");
                startActivity(intent);
            }
        });
    }
}
