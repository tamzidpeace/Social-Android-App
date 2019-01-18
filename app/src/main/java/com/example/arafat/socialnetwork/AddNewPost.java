package com.example.arafat.socialnetwork;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddNewPost extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView addPostImage;
    private EditText addNewEditText;
    private Button addNewButton;
    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    private StorageReference PostImageRef;
    private DatabaseReference PostRef;
    private String saveCurrentDate, saveCurrentTime, postRandromName;

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

        PostImageRef = FirebaseStorage.getInstance().getReference();
        PostRef = FirebaseDatabase.getInstance().getReference();

        addPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePost();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    private void updatePost() {
        String post = addNewEditText.getText().toString();
        if (TextUtils.isEmpty(post) && ImageUri == null) {
            Toast.makeText(this, "complete the fields", Toast.LENGTH_SHORT).show();
        } else {
            storingImageToFirebase();
        }
    }

    private void storingImageToFirebase() {
        String post = addNewEditText.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calForTime.getTime());
        postRandromName = saveCurrentDate + saveCurrentTime;
        StorageReference filePath = PostImageRef.child("PostImages")
                .child(ImageUri.getLastPathSegment() + postRandromName + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AddNewPost.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddNewPost.this, MainActivity.class));
                } else {
                    Toast.makeText(AddNewPost.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            addPostImage.setImageURI(ImageUri);
        }
    }
}
