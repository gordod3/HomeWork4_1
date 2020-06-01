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
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()) {
                        HashMap map = (HashMap) task.getResult().getData();
                        List<Task> t = (List<Task>) map.get("task");
                        Log.d("lol", t.get(0).getTitle());
                        list.addAll(t);
//                        Log.d("lol", list.get(0).getTitle());
                        loadData();
                    }
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
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    list.clear();
                    list.addAll((List<Task>)documentSnapshot.get("task"));
                    adapter.notifyDataSetChanged();
                }
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
        list.remove(adapter.position);
        document.update("task", list);
    }
}