package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResulteActivity extends AppCompatActivity {
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resulte);
        textView = findViewById(R.id.textView);

        int score = getIntent().getIntExtra("score",0);
        int lessonId = getIntent().getIntExtra("lessonId",0);
        textView.setText("Điểm số : " + score*2 +"/10");

        findViewById(R.id.btn_restart).setOnClickListener(
                restart->{
                    Intent intent  = new Intent(ResulteActivity.this , LessonActivity.class);
                    intent.putExtra("lesson_id", lessonId);
                    startActivity(intent);
                    finish();
                }
        );
    }
}