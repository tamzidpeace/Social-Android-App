package com.example.arafat.socialnetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class AddNewPost extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView addPostImage;
    private EditText addNewEditText;
    private Button addNewButton;
    private static final int  Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        mToolbar = findViewById(R.id.update_post_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Post");

        addPostImage = findViewById(R.id.add_new_post_image);
        addNewEditText = findViewById(R.id.add_new_post_edit_text);
        addNewButton = findViewById(R.id.add_new_post_button);

        addPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

}
