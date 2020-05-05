package com.example.homework4_1.ui.onBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.homework4_1.MainActivity;
import com.example.homework4_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class OnBoardActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.activity_onBoard_tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);
        FloatingActionButton fab = findViewById(R.id.activity_onBoard_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
                intent.putExtra("true", true);
                startActivity(intent);
                finish();
            }
        });
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            BoardFragment fragment = new BoardFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
