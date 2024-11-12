package com.example.courseapp.database.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonRepository extends BaseRepository<Lesson>{
    public LessonRepository(DatabaseHelper dbHelper) {
        super(dbHelper, "lessons");
    }

    @Override
    protected Lesson getItemFromCursor(Cursor cursor) {
        Lesson lesson = new Lesson();
        lesson.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        lesson.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        lesson.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
        lesson.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow("course_id")));
        return lesson;
    }

    @Override
    protected ContentValues getContentValues(Lesson item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("content", item.getContent());
        contentValues.put("course_id", item.getCourseId());
        return contentValues;
    }

    public List<Lesson> getLessonsByCourseId(int courseId) {
        String selection = "course_id = ?";
        String[] selectionArgs = { String.valueOf(courseId) };
        ArrayList<Lesson> lessons = this.findLessons(selection, selectionArgs);
        Log.d("LessonRepository", "Lessons retrieved for course ID " + courseId + ": " + lessons.size());
        return lessons;
    }
    public Lesson getLessonById(int lessonId) {
        return findById(lessonId);
    }
}
