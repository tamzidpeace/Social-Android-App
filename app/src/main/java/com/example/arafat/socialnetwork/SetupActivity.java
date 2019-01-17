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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, CountryName;
    private Button SaveInformaitonButton;
    private CircleImageView ProfileImage;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private ProgressDialog loadingBar;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        UserName = findViewById(R.id.setting_username);
        FullName = findViewById(R.id.setting_full_name);
        CountryName = findViewById(R.id.setting_country);
        SaveInformaitonButton = findViewById(R.id.setting_save_btn);
        ProfileImage = findViewById(R.id.setting_circle_profile);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        SaveInformaitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        });
    }

    private void SaveAccountSetupInformation() {
        String username = UserName.getText().toString().trim();
        String fullname = FullName.getText().toString().trim();
        String country = CountryName.getText().toString().trim();

        loadingBar.setTitle("Saving Information");
        loadingBar.setMessage("Please wait, information is saving to your database");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(fullname) || TextUtils.isEmpty(country)) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        } else {
            HashMap userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("fullname", fullname);
            userMap.put("country", country);
            userMap.put("status","Hey There, I am good");
            userMap.put("gender", "none");
            userMap.put("dob", "none");
            userMap.put("relationshipstatus", "none");
            UserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(SetupActivity.this, "Information saved", Toast.LENGTH_SHORT).show();
                        SendUserToMainActivity();
                    } else {
                        loadingBar.dismiss();
                        String error = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
