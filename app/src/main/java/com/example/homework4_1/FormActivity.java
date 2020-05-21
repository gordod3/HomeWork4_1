package com.example.homework4_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.homework4_1.models.Task;
import com.example.homework4_1.room.TaskDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle, editDesc;
    private Task editTask;
    private String e = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.form_editTitle);
        editDesc = findViewById(R.id.form_editDesc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            Intent intent = getIntent();
            editTask = (Task)intent.getSerializableExtra("task");
            editTitle.setText(editTask.getTitle());
            editDesc.setText(editTask.getDesc());
        }catch (Exception e){}
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
            List<Task> tasks = taskDao.getAll();
            if (editTask != null) {
                task.setId(editTask.getId());
                taskDao.update(task);
            } else {
                taskDao.insert(task);
            }
            finish();
        }
    }
}
