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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
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
        final LottieAnimationView lottie = view.findViewById(R.id.pager_lottie_title);
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
                lottie.setAnimation(R.raw.animation_view_pager1);
                textTitle.setText("Добро пожаловать в \"Приложение\"!");
                textDes.setText("Здесь вы можете делать все что захотите.");
                board.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case 1:
                lottie.setAnimation(R.raw.animation_view_pager2);
                textTitle.setText("Общайтесь!");
                textDes.setText("Вы можете переписываться со своими друзьями.");
                board.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                lottie.setAnimation(R.raw.animation_view_pager3);
                textTitle.setText("Начинайте сейчас!");
                textDes.setText("Нажмите начать чтобы авторизоваться по номеру телефона:");
                button.setVisibility(View.VISIBLE);
                break;
        }
    }
}
