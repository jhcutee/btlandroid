package com.example.courseapp.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.QuizzOfCourse;

import java.util.ArrayList;
import java.util.List;

public class QuizzOfCourseRepository extends BaseRepository<QuizzOfCourse>{
    public QuizzOfCourseRepository(DatabaseHelper dbHelper) {
        super(dbHelper, "quizzesOfCourse");
    }

    @Override
    protected QuizzOfCourse getItemFromCursor(Cursor cursor) {
        QuizzOfCourse quizz = new QuizzOfCourse();
        quizz.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        quizz.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow("question")));
        quizz.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow("course_id")));
        quizz.setAnswerA(cursor.getString(cursor.getColumnIndexOrThrow("answer_A")));
        quizz.setAnswerB(cursor.getString(cursor.getColumnIndexOrThrow("answer_B")));
        quizz.setAnswerC(cursor.getString(cursor.getColumnIndexOrThrow("answer_C")));
        quizz.setAnswerD(cursor.getString(cursor.getColumnIndexOrThrow("answer_D")));
        quizz.setCorrectAnswer(cursor.getString(cursor.getColumnIndexOrThrow("correct_answer")));
        return quizz;
    }

    @Override
    protected ContentValues getContentValues(QuizzOfCourse item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", item.getQuestion());
        contentValues.put("course_id", item.getCourseId());
        contentValues.put("answer_A", item.getAnswerA());
        contentValues.put("answer_B", item.getAnswerB());
        contentValues.put("answer_C", item.getAnswerC());
        contentValues.put("answer_D", item.getAnswerD());
        contentValues.put("correct_answer", item.getCorrectAnswer());
        return contentValues;
    }
    public List<QuizzOfCourse> getQuizzesByCourseId(int courseId) {
        String selection = "course_id = ?";
        String[] selectionArgs = { String.valueOf(courseId) };
        ArrayList<QuizzOfCourse> quizzes = this.findLessons(selection, selectionArgs);
        Log.d("QuizzOfCourseRepository", "Quizzes retrieved for lesson ID " + courseId + ": " + quizzes.size());
        return quizzes;
    }
}
