package com.example.homework4_1.ui.firestore;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homework4_1.App;
import com.example.homework4_1.FormActivity;
import com.example.homework4_1.MainActivity;
import com.example.homework4_1.OnItemClickListener;
import com.example.homework4_1.R;
import com.example.homework4_1.dialogAlert.FireMissilesDialogFragment;
import com.example.homework4_1.dialogAlert.NoticeDialogFragment;
import com.example.homework4_1.models.Task;
import com.example.homework4_1.ui.home.TaskAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreFragment extends Fragment implements OnItemClickListener, NoticeDialogFragment.NoticeDialogListener {
    public TaskAdapter adapter;
    private List<Task> list = new ArrayList<>();
    FireMissilesDialogFragment dialogFragment;
    RecyclerView recyclerView;
    DocumentReference document = FirebaseFirestore.getInstance().collection("data").document(FirebaseAuth.getInstance().getUid());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_firestore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fragmentFirestore_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        document.collection("task").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                        list.addAll(task.getResult().toObjects(Task.class));
                        loadData();
                } else {
                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                    Log.e("lol", task.getException() + "");
                }
            }
        });
    }

    public void loadData() {
        dialogFragment = new FireMissilesDialogFragment(this);
        adapter = new TaskAdapter(list, getResources(), this, dialogFragment, (MainActivity) getActivity());
        recyclerView.setAdapter(adapter);
        document.collection("task").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                    list.clear();
                    if (!documentSnapshots.toObjects(Task.class).isEmpty()) list.addAll((documentSnapshots.toObjects(Task.class)));
                    adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getContext(), FormActivity.class);
        intent.putExtra("task", list.get(pos));
        getActivity().startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick() {
    }
}