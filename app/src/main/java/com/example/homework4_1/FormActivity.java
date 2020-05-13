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

import java.io.Serializable;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle, editDesc;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.form_editTitle);
        editDesc = findViewById(R.id.form_editDesc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            task = (Task)getIntent().getSerializableExtra("task");
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }catch (Exception e){}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    public void onClick(View view) {
        if ((editTitle != null || editTitle.getText().toString().trim() != "") && (editDesc != null || editDesc.getText().toString().trim() != "")) {
            try {
                App.getInstance().getDatabase().taskDao().delete(task);
            }catch (Exception e){}
            String
                    title = editTitle.getText().toString().trim(),
                    desc = editDesc.getText().toString().trim();
            Task task = new Task(title, desc);
            App.getInstance().getDatabase().taskDao().insert(task);
            finish();
        }
    }
}
