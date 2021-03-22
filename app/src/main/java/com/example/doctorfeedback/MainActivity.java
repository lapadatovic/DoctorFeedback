package com.example.doctorfeedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.buttonGoToLogin);
        Button buttonRegister = findViewById(R.id.buttonGoToRegister);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGoToLogin:
                Intent pageLogin = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(pageLogin);
                break;
            case R.id.buttonGoToRegister:
                Intent pageRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(pageRegister);
                break;
        }
    }
}