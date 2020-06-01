package com.example.homework4_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homework4_1.models.User;
import com.example.homework4_1.phone.PhoneActivity;
import com.example.homework4_1.ui.home.HomeFragment;
import com.example.homework4_1.ui.onBoard.OnBoardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private HomeFragment homeFragment;
    private ImageView avatar;
    private TextView name;

    private boolean isShown(){
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("isShown", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isShown()){
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, PhoneActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( MainActivity.this, FormActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), 1);
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        avatar = navigationView.getHeaderView(0).findViewById(R.id.header_imageView_avatar);
        name = navigationView.getHeaderView(0).findViewById(R.id.header_textView_name);
        loadData();
    }

    private void loadData() {
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    if (user.getAvatar() != null && user.getName() != null) {
                    name.setText(user.getName());
                    showAvatar(user.getAvatar());
                    }
                }
            }
        });
    }

    private void showAvatar(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(this.avatar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void ItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_exit:
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("isShown", false).apply();
                finish();
                break;
            case R.id.action_settings:
                break;
            case R.id.action_sort:
                homeFragment.isSorted = !homeFragment.isSorted;
                homeFragment.loadData();
                break;

        }
    }

    public void FishingFragment(HomeFragment fragment){
        this.homeFragment = fragment;
    }
}
