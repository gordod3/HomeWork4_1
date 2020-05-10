package com.example.homework4_1.ui.onBoard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homework4_1.MainActivity;
import com.example.homework4_1.R;


public class BoardFragment extends Fragment {

    public BoardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    private void saveIs(){
        SharedPreferences preferences = getActivity()
                .getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown", true).apply();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayout board = view.findViewById(R.id.fragment_board);
        final Button button = view.findViewById(R.id.pager_button_start);
        TextView textTitle = view.findViewById(R.id.pager_text_title), textDes = view.findViewById(R.id.pager_text_des);
        final ImageView image = view.findViewById(R.id.pager_imageView_title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIs();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        int pos = getArguments().getInt("pos");
        switch (pos){
            case 0:
                image.setImageResource(R.drawable.ic_menu_camera);
                textTitle.setText("Добро пожаловать в \"*название*\"!");
                textDes.setText("");
                board.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 1:
                image.setImageResource(R.drawable.ic_menu_send);
                textTitle.setText("");
                textDes.setText("XXX");
                board.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                image.setImageResource(R.drawable.ic_launcher_background);
                textTitle.setText("XXX");
                textDes.setText("XXX");
                button.setVisibility(View.VISIBLE);
                board.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }
}
