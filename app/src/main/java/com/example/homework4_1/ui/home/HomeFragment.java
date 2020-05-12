package com.example.homework4_1.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4_1.App;
import com.example.homework4_1.MainActivity;
import com.example.homework4_1.OnItemClickListener;
import com.example.homework4_1.R;
import com.example.homework4_1.models.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public TaskAdapter adapter;
    private List<Task> list = new ArrayList<>();
    public OnItemClickListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter = new TaskAdapter(list, getResources(), listener);
        recyclerView.setAdapter(adapter);
        MainActivity mainActivity = (MainActivity) getActivity();
        loadData();

    }

    private void loadData() {
        App.getInstance().getDatabase().taskDao().getAllive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
