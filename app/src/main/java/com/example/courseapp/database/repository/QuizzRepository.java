package com.example.courseapp.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Lesson;
import com.example.courseapp.database.model.Quizz;

import java.util.ArrayList;
import java.util.List;

public class QuizzRepository extends BaseRepository<Quizz>{
    public QuizzRepository(DatabaseHelper dbHelper) {
        super(dbHelper, "quizzes");
    }

    @Override
    protected Quizz getItemFromCursor(Cursor cursor) {
        Quizz quizz = new Quizz();
        quizz.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        quizz.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow("question")));
        quizz.setLessonId(cursor.getInt(cursor.getColumnIndexOrThrow("lesson_id")));
        quizz.setAnswerA(cursor.getString(cursor.getColumnIndexOrThrow("answer_A")));
        quizz.setAnswerB(cursor.getString(cursor.getColumnIndexOrThrow("answer_B")));
        quizz.setAnswerC(cursor.getString(cursor.getColumnIndexOrThrow("answer_C")));
        quizz.setAnswerD(cursor.getString(cursor.getColumnIndexOrThrow("answer_D")));
        quizz.setCorrectAnswer(cursor.getString(cursor.getColumnIndexOrThrow("correct_answer")));
        return quizz;
    }

    @Override
    protected ContentValues getContentValues(Quizz item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", item.getQuestion());
        contentValues.put("lesson_id", item.getLessonId());
        contentValues.put("answer_A", item.getAnswerA());
        contentValues.put("answer_B", item.getAnswerB());
        contentValues.put("answer_C", item.getAnswerC());
        contentValues.put("answer_D", item.getAnswerD());
        contentValues.put("correct_answer", item.getCorrectAnswer());
        return contentValues;
    }
    public List<Quizz> getQuizzesByLessonId(int lessonId) {
        String selection = "lesson_id = ?";
        String[] selectionArgs = { String.valueOf(lessonId) };
        ArrayList<Quizz> quizzes = this.findLessons(selection, selectionArgs);
        Log.d("QuizzRepository", "Quizzes retrieved for lesson ID " + lessonId + ": " + quizzes.size());
        return quizzes;
    }
}
