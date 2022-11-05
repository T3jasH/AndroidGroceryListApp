package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerylist.datamodels.User;
import com.example.grocerylist.helpers.UserDbAdapter;

public class MainActivity extends AppCompatActivity {
    UserDbAdapter userDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDbAdapter = new UserDbAdapter(this);
        userDbAdapter.open();
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(l -> {
            EditText email = findViewById(R.id.email_field);
            EditText password = findViewById(R.id.password_field);
            if(userDbAdapter.validateUser(
                    new User(
                            email.getText().toString(),
                            password.getText().toString()
                    )
            )){
                Intent intent = new Intent(MainActivity.this, GroceryList.class);
                startActivity(intent);
                return;
            }
            Toast.makeText(getApplicationContext(), "Invalid login credentials", Toast.LENGTH_SHORT).show();
        });
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(l -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        userDbAdapter.close();
    }
}