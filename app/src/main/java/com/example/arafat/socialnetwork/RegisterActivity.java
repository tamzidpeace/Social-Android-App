package com.example.arafat.socialnetwork;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText UserEmail, UserPassword, UserConfirmPassword;
    private Button CreateAccountButton;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        UserConfirmPassword = findViewById(R.id.register_confirm_password);
        CreateAccountButton = findViewById(R.id.register_button);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        
        String email, password, confirmPassword;
        
        email = UserEmail.getText().toString().trim();
        password = UserPassword.getText().toString().trim();
        confirmPassword = UserConfirmPassword.getText().toString().trim();
        
        if(TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(confirmPassword)) {

            Toast.makeText(this, "Please confirm password", Toast.LENGTH_SHORT).show();
        }

        else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("create new account");
            loadingBar.setMessage("Please wait, while we are creating your accounting");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Your are authenticated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(RegisterActivity.this, SetupActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().getMessage();
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
