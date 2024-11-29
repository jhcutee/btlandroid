package com.example.courseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Lesson;
import com.example.courseapp.database.repository.LessonRepository;

public class LessonActivity extends AppCompatActivity {
    TextView lessonName, lessonContent;
    LessonRepository lessonRepository;
    ImageView back;
    Button quizzBtn, supportBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWidget();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        lessonRepository = new LessonRepository(dbHelper);
        int lessonId = getIntent().getIntExtra("lesson_id",0);
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        lessonName.setText(lesson.getName());
        String htmlContent = lesson.getContent();
        lessonContent.setText(Html.fromHtml(lesson.getContent(),  Html.FROM_HTML_MODE_LEGACY));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        quizzBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonActivity.this, QuizzActivity.class);
                intent.putExtra("lesson_id", lessonId);
                startActivity(intent);
            }
        });
        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getWidget() {
        lessonName = findViewById(R.id.lesson_name);
        lessonContent = findViewById(R.id.lesson_content);
        back = findViewById(R.id.backbtn);
        quizzBtn = findViewById(R.id.quizzBtn);
        supportBtn = findViewById(R.id.supportBtn);
    }
}