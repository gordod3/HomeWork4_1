package com.example.homework4_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework4_1.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    EditText editText;
    String uID = FirebaseAuth.getInstance().getUid(); //d

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editText = findViewById(R.id.profile_editText);
        getDataAlt();
    }

    private void getDataAlt(){
        FirebaseFirestore.getInstance().collection("users").document(uID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    editText.setText(user.getName());
                }
            }
        });
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
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Aleksey");
        User user = new User(name, null, 1);
        FirebaseFirestore.getInstance().collection("users")
                .document(uID).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                else Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
