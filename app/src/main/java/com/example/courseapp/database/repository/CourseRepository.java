package com.example.courseapp.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository extends BaseRepository<Course>{
    public CourseRepository(DatabaseHelper dbHelper) {
        super(dbHelper, "courses");
    }

    @Override
    protected Course getItemFromCursor(Cursor cursor) {
        Course course = new Course();
        course.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        course.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        course.setCourseImage(cursor.getString(cursor.getColumnIndexOrThrow("course_image")));
        return course;
    }

    @Override
    protected ContentValues getContentValues(Course item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("course_image", item.getCourseImage());
        return contentValues;
    }

    public ArrayList<Course> getAll() {
        return this.find();
    }

}
