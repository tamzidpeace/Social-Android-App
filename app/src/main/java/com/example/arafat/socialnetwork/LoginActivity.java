package com.example.arafat.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button LoginButton;
    private EditText UserEmail, UserPassword;
    private TextView NeedNewAccountLink;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_button);
        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        NeedNewAccountLink = findViewById(R.id.need_an_account);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginIntoAccount();
            }
        });

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegisterActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            SendUserToMainActivity();
        }
    }

    private void loginIntoAccount() {
        loadingBar.setTitle("Account Login");
        loadingBar.setMessage("Login is in progress");
        loadingBar.setCanceledOnTouchOutside(true);

        String email, password;
        email = UserEmail.getText().toString().trim();
        password = UserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                loadingBar.dismiss();
                                Log.d(TAG, "signInWithEmail:success");
                                SendUserToMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                loadingBar.dismiss();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendUserToRegisterActivity() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
