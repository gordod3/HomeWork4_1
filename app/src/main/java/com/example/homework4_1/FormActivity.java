package com.example.homework4_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework4_1.models.Task;
import com.example.homework4_1.models.User;
import com.example.homework4_1.room.TaskDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle, editDesc;
    private Task editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.form_editTitle);
        editDesc = findViewById(R.id.form_editDesc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        editTask = (Task)intent.getSerializableExtra("task");
        if (editTask != null){
            editTitle.setText(editTask.getTitle());
            editDesc.setText(editTask.getDesc());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    public void onClick(View view) {
        if (!editTitle.getText().toString().isEmpty()) {
            String
                    title = editTitle.getText().toString().trim(),
                    desc = editDesc.getText().toString().trim();
            Task task = new Task(title, desc);
            TaskDao taskDao = App.getInstance().getDatabase().taskDao();
            if (editTask != null) {
                task.setId(editTask.getId());
                taskDao.update(task);
            } else {
                taskDao.insert(task);
                FirebaseFirestore.getInstance().collection("data")
                        .document(FirebaseAuth.getInstance().getUid()).set(task)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                if (task.isSuccessful()) Toast.makeText(FormActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                                else Toast.makeText(FormActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            finish();
        }
    }
}
