package com.example.homework4_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.homework4_1.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    EditText editText;
    String uID = FirebaseAuth.getInstance().getUid();
    ImageView avatarImage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editText = findViewById(R.id.profile_editText);
        avatarImage = findViewById(R.id.profile_avatar);
        progressBar = findViewById(R.id.profile_progressBar);
        progressBar.setVisibility(View.GONE );
        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
        getDataAlt();
    }

    private void getDataAlt(){
        FirebaseFirestore.getInstance().collection("users").document(uID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    editText.setText(user.getName());
                    if (user.getAvatar() != null) {
                        showImage(user.getAvatar());
                    }
                }
            }
        });
    }

    private void showImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(avatarImage);
    }

    private void getData() {
        FirebaseFirestore.getInstance().collection("users").document(uID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    editText.setText(user.getName());
                }
            }
        });
    }

    public void onClick(View view) {
        String name = editText.getText().toString().trim();
        User user = new User(name, null, 1);
        FirebaseFirestore.getInstance().collection("users")
                .document(uID).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    Log.d("lol", task.getException() + "");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                        Glide.with(this).load(data.getData()).circleCrop().into(avatarImage);
                        upload(data.getData());
                }
        }
    }

    private void upload(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(uID + ".jpg");
        UploadTask uploadTask = storageReference.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUrl = task.getResult();
                    Log.d("lol", downloadUrl + "");
                    updateAvatarInfo(downloadUrl);
                } else {
                    Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateAvatarInfo(Uri downloadUrl) {
        FirebaseFirestore.getInstance().collection("users").document(uID)
                .update("avatar", downloadUrl.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                else Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
