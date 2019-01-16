package com.example.arafat.socialnetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, CountryName;
    private Button SaveInformaitonButton;
    private CircleImageView ProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        UserName = findViewById(R.id.setting_username);
        FullName = findViewById(R.id.setting_full_name);
        CountryName = findViewById(R.id.setting_country);
        SaveInformaitonButton = findViewById(R.id.setting_save_btn);
        ProfileImage = findViewById(R.id.setting_circle_profile);
    }
}
