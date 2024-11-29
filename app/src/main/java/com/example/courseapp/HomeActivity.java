package com.example.courseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Course;
import com.example.courseapp.database.model.TemporaryLoginInfo;
import com.example.courseapp.database.repository.CourseRepository;
import com.example.courseapp.database.repository.UserRepository;

import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private List<Course> courseList;
    private ImageView imgHomeAvatar;
    private TextView txtHomeAvatar;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWidget();
        recyclerView = findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        courseRepository = new CourseRepository(dbHelper);
        userRepository = new UserRepository(dbHelper);

        recyclerView = findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        String username = TemporaryLoginInfo.getInstance().getUsername();
        String name = userRepository.getUserByUsername(username).getFullName();
        String avatarUrl = userRepository.getUserByUsername(username).getAvatar();
        Log.d("aaaa", "avatar aaaaaaaaaaaaaa:" + avatarUrl);
        if (avatarUrl != null) {
            Glide.with(this)
                    .load( "file:///android_asset/" + avatarUrl)
                    .into(imgHomeAvatar);
        }
        txtHomeAvatar.setText(name);

        courseList = courseRepository.getAll();

        subjectAdapter = new SubjectAdapter(this, courseList);
        recyclerView.setAdapter(subjectAdapter);

        imgHomeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        txtHomeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getWidget(){
        imgHomeAvatar = findViewById(R.id.img_home_avatar);
        txtHomeAvatar = findViewById(R.id.txt_home_username);
    }

}