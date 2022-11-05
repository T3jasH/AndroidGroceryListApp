package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocerylist.datamodels.User;
import com.example.grocerylist.helpers.UserDbAdapter;

public class RegisterActivity extends AppCompatActivity {
    UserDbAdapter userDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDbAdapter = new UserDbAdapter(this);
        userDbAdapter.open();
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.register_submit);
        registerButton.setOnClickListener(l -> {
            EditText emailEditText = findViewById(R.id.register_email);
            String email = emailEditText.getText().toString();
            EditText passwordEditText = findViewById(R.id.register_password);
            String password = passwordEditText.getText().toString();
            userDbAdapter.createUser(new User(
                    email,
                    password
            ));
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        userDbAdapter.close();
    }
}