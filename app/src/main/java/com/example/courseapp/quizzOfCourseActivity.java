package com.example.courseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Quizz;
import com.example.courseapp.database.model.QuizzOfCourse;
import com.example.courseapp.database.repository.QuizzOfCourseRepository;
import com.example.courseapp.database.repository.QuizzRepository;

import java.util.List;

public class quizzOfCourseActivity extends AppCompatActivity {
    private QuizzOfCourseRepository quizzOfCourseRepository;
    TextView cpt_question , text_question;
    Button btn_choose1 , btn_choose2 , btn_choose3 , btn_choose4 , btn_next;
    int currentQuestion =  0  ;
    int scorePlayer =  0  ;
    boolean isclickBtn = false;
    String valueChoose = "",correctAnswer="";
    Button btn_click;
    private List<QuizzOfCourse> quizzList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quizz_of_course);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        quizzOfCourseRepository = new QuizzOfCourseRepository(dbHelper);


        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);

        int courseId = getIntent().getIntExtra("course_id", -1);
        String subjectName = getIntent().getStringExtra("subject_name");

        quizzList = quizzOfCourseRepository.getQuizzesByCourseId(courseId);

        findViewById(R.id.image_back).setOnClickListener(
                a-> finish()
        );
        loadQuizz();

        btn_next.setOnClickListener(
                view -> {
                    if (isclickBtn){
                        isclickBtn = false;

                        if(!valueChoose.equals(quizzList.get(currentQuestion).getCorrectAnswer())){
                            Toast.makeText(quizzOfCourseActivity.this , "Bạn đã trả lời sai",Toast.LENGTH_LONG).show();
                            btn_click.setBackgroundResource(R.drawable.background_btn_erreur);

                            if (btn_choose1.getText().toString().equals(correctAnswer)) {
                                btn_choose1.setBackgroundResource(R.drawable.background_btn_correct);
                            } else if (btn_choose2.getText().toString().equals(correctAnswer)) {
                                btn_choose2.setBackgroundResource(R.drawable.background_btn_correct);
                            } else if (btn_choose3.getText().toString().equals(correctAnswer)) {
                                btn_choose3.setBackgroundResource(R.drawable.background_btn_correct);
                            } else if (btn_choose4.getText().toString().equals(correctAnswer)) {
                                btn_choose4.setBackgroundResource(R.drawable.background_btn_correct);
                            }

                        }else {
                            Toast.makeText(quizzOfCourseActivity.this , "Bạn đã trả lời đúng",Toast.LENGTH_LONG).show();
                            btn_click.setBackgroundResource(R.drawable.background_btn_correct);

                            scorePlayer++;
                        }

                        new Handler().postDelayed(() -> {
                            if(currentQuestion !=quizzList.size()-1){
                                currentQuestion = currentQuestion + 1;
                                loadQuizz();
                                valueChoose = "";
                                btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
                                btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
                                btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
                                btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);

                            }else {
                                Intent intent = new Intent(quizzOfCourseActivity.this , ResulteQuizzOfCourseActivity.class);
                                intent.putExtra("score" , scorePlayer);
                                intent.putExtra("courseId" , courseId);
                                intent.putExtra("subject_name" , subjectName);

                                if(scorePlayer*2 > 5){
                                    SharedPreferences sharedPreferences = getSharedPreferences("CoursePrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("course_" + courseId, true); // courseId là ID của khóa học hiện tại
                                    editor.apply();
                                }

                                startActivity(intent);
                                finish();
                            }

                        },2000);

                    }else {
                        Toast.makeText(quizzOfCourseActivity.this ,  "Bạn cần chọn đáp án",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    void loadQuizz(){
        cpt_question.setText((currentQuestion+1) + "/" + (quizzList.size()));
        text_question.setText(quizzList.get(currentQuestion).getQuestion());

        btn_choose1.setText(quizzList.get(currentQuestion).getAnswerA());
        btn_choose2.setText(quizzList.get(currentQuestion).getAnswerB());
        btn_choose3.setText(quizzList.get(currentQuestion).getAnswerC());
        btn_choose4.setText(quizzList.get(currentQuestion).getAnswerD());

    }

    public void ClickChoose(View view) {
        btn_click = (Button)view;

        if (isclickBtn) {
            btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
        }
        chooseBtn();


    }
    void chooseBtn(){
        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
        correctAnswer = quizzList.get(currentQuestion).getCorrectAnswer();
    }
}