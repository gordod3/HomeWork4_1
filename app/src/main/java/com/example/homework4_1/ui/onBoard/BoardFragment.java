package com.example.homework4_1.ui.onBoard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button button = view.findViewById(R.id.pager_button_start);
        final OnBoardActivity onBoardActivity = (OnBoardActivity) getActivity();
        TextView textTitle = view.findViewById(R.id.pager_text_title), textDes = view.findViewById(R.id.pager_text_des);
        final ImageView image = view.findViewById(R.id.pager_imageView_title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(onBoardActivity, MainActivity.class);
                intent.putExtra("true", true);
                startActivity(intent);
                onBoardActivity.finish();
            }
        });
        int pos = getArguments().getInt("pos");
        switch (pos){
            case 0:
                image.setImageResource(R.drawable.ic_menu_camera);
                textTitle.setText("Привет");
                textDes.setText("Как дела?");
                break;
            case 1:
                image.setImageResource(R.drawable.ic_menu_send);
                textTitle.setText("Хорошо");
                textDes.setText("У меня хорошо");
                break;
            case 2:
                image.setImageResource(R.drawable.ic_launcher_background);
                textTitle.setText("Пока");
                textDes.setText("До свиданье");
                button.setVisibility(View.VISIBLE);
                break;
        }
    }
}
