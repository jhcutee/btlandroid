package com.example.courseapp.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.courseapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BaseRepository<T> {
    private DatabaseHelper dbHelper;
    private String tableName;

    public BaseRepository(DatabaseHelper dbHelper, String tableName) {
        this.dbHelper = dbHelper;
        this.tableName = tableName;
    }

    protected abstract T getItemFromCursor(Cursor cursor);

    protected abstract ContentValues getContentValues(T item);

    public T findById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE id = " + id, null);
        T item = null;
        if (cursor.moveToFirst()) {
            item = getItemFromCursor(cursor);
            cursor.close();
        }

        db.close();
        return item;
    }

    public ArrayList<T> find() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        ArrayList<T> itemList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                T item = getItemFromCursor(cursor);
                itemList.add(item);
            }
            cursor.close();
        }
        db.close();

        return itemList;
    }

    public ArrayList<T> find(String whereQuery) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + whereQuery, null);
        ArrayList<T> itemList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                T item = getItemFromCursor(cursor);
                itemList.add(item);
            }
            cursor.close();
        }
        db.close();

        return itemList;
    }

    public long create(T item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(item);
        long id = db.insert(tableName, null, values);

        db.close();
        return id;
    }

    public int update(T item, long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(item);
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int rowsAffected = db.update(tableName, values, selection, selectionArgs);

        db.close();
        return rowsAffected;
    }

    public int update(ContentValues contentValues, long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int rowsAffected = db.update(tableName, contentValues, selection, selectionArgs);
        Log.d("rowsAffected: ", contentValues.toString() + " " + Arrays.toString(selectionArgs));

        db.close();
        return rowsAffected;
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int rowsAffected = db.delete(tableName, selection, selectionArgs);

        db.close();
        return rowsAffected;
    }

    public Cursor rawQuery(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //get Lesson
    public ArrayList<T> findLessons(String selection, String[] selectionArgs) {
        ArrayList<T> items = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().query(
                tableName, null, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                T item = getItemFromCursor(cursor);
                items.add(item);
            }
            cursor.close();
        } else {
            Log.e("BaseRepository", "Cursor is null for selection: " + selection);
        }
        return items;
    }

    public ArrayList<T> findAll() {
        return findLessons(null, null);
    }

    //update Profile

    public int updateProfile(T item, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(item);
        int rowsAffected = db.update(tableName, values, whereClause, whereArgs);
        db.close();
        return rowsAffected;
    }
}
