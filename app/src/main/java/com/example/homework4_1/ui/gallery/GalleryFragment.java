package com.example.homework4_1.ui.gallery;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4_1.R;

import java.io.File;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    ArrayList<String> fileNames;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    private void  getFiles(){
        File folder = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
        fileNames = new ArrayList<>();
        for(File file:folder.listFiles()){
            fileNames.add(file.getName());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPermissions();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GalleryAdapter adapter = new GalleryAdapter(fileNames);
        recyclerView.setAdapter(adapter);
    }

    private void getPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            getFiles();
        }else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            getPermissions();
        }
    }
}
