package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResulteQuizzOfCourseActivity extends AppCompatActivity {
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resulte_quizz_of_course);

        textView = findViewById(R.id.textView);

        int score = getIntent().getIntExtra("score",0);
        int courseId = getIntent().getIntExtra("courseId",0);
        String subjectName = getIntent().getStringExtra("subject_name");

        textView.setText("Điểm số : " + score*2 +"/10");

        findViewById(R.id.btn_restart).setOnClickListener(
                restart->{
                    if((score*2) > 5) {
                        Intent intent = new Intent(ResulteQuizzOfCourseActivity.this, DetailSubjectActivity.class);
                        intent.putExtra("course_id", courseId);
                        intent.putExtra("subject_name", subjectName);
                        startActivity(intent);
                    }else{
                        Intent intentHome = new Intent(ResulteQuizzOfCourseActivity.this, HomeActivity.class);
                        startActivity(intentHome);
                    }
                }
        );
    }

}