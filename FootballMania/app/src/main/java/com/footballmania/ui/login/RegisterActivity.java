package com.footballmania.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.footballmania.R;
import com.footballmania.database.dbobjects.User;
import com.footballmania.serverConnection.TomcatConnection;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    Button cancelButton;

    EditText nameEditText,loginEditText, emailEditText, passwordEditText, confirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.registerCancelButton);

        nameEditText = findViewById(R.id.registerName);
        emailEditText = findViewById(R.id.registerEmail);
        passwordEditText = findViewById(R.id.registerPassword);
        confirmEditText = findViewById(R.id.registerPasswordConfirm);
        loginEditText = findViewById(R.id.registerLogin);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPasswords()) {
                    User user = new User(-1, loginEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString(), nameEditText.getText().toString(), 0, 0);
                    TomcatConnection.registerUser(RegisterActivity.this, user);
                    onBackPressed();
                }
                else
                    Toast.makeText(RegisterActivity.this, R.string.register_password_missmatch, Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private boolean checkPasswords() {
        return passwordEditText.getText().toString().equals(confirmEditText.getText().toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
