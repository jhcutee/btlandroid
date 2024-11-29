package com.example.courseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Lesson;
import com.example.courseapp.database.repository.LessonRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailSubjectActivity extends AppCompatActivity {
    private LessonRepository lessonRepository;
    private RecyclerView recyclerView;
    private LessonAdapter lessonsAdapter;
    private List<Lesson> lessonList;
    private TextView txtLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subject);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        lessonRepository = new LessonRepository(dbHelper);

        recyclerView = findViewById(R.id.lesson_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        int courseId = intent.getIntExtra("course_id", -1);
        String subjectName = intent.getStringExtra("subject_name");


        txtLesson = findViewById(R.id.txt_lessons);
        txtLesson.setText(subjectName);

        lessonList = lessonRepository.getLessonsByCourseId(courseId);
        Log.d("LessonList", "Size: " + lessonList.size());
        if (lessonList.isEmpty()) {
        } else {
            lessonsAdapter = new LessonAdapter(this, lessonList);
            recyclerView.setAdapter(lessonsAdapter);
        }

        lessonsAdapter = new LessonAdapter(this, lessonList);
        recyclerView.setAdapter(lessonsAdapter);


        ImageView arrowImageView = findViewById(R.id.img_detail_subject_arrow);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailSubjectActivity.this, HomeActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }

}
