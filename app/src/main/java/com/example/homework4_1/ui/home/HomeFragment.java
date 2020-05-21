package com.example.homework4_1.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4_1.App;
import com.example.homework4_1.dialogAlert.FireMissilesDialogFragment;
import com.example.homework4_1.FormActivity;
import com.example.homework4_1.MainActivity;
import com.example.homework4_1.OnItemClickListener;
import com.example.homework4_1.R;
import com.example.homework4_1.dialogAlert.NoticeDialogFragment;
import com.example.homework4_1.models.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener, NoticeDialogFragment.NoticeDialogListener {
    public TaskAdapter adapter;
    private List<Task> list = new ArrayList<>();
    FireMissilesDialogFragment dialogFragment;
    MainActivity mainActivity;
    public Boolean isSorted = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.FishingHomeFragment(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        dialogFragment = new FireMissilesDialogFragment(this);
        adapter = new TaskAdapter(list, getResources(), this, dialogFragment, (MainActivity) getActivity());
        recyclerView.setAdapter(adapter);
        loadData();
    }

    public void loadData() {
        App.getInstance().getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                if (isSorted) list.addAll(tasks);
                else list.addAll(App.getInstance().getDatabase().taskDao().getSortedAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getContext(), FormActivity.class);
        intent.putExtra("task", list.get(pos));
        intent.putExtra("pos", pos);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick() {
        App.getInstance().getDatabase().taskDao().delete(list.get(adapter.position));
    }

    @Override
    public void onDialogNegativeClick() {

    }
}
