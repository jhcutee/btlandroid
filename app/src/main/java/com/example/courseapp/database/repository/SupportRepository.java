package com.example.courseapp.database.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Support;
import com.example.courseapp.database.model.User;

public class SupportRepository extends BaseRepository<Support>{
    public SupportRepository(DatabaseHelper dbHelper) {
        super(dbHelper, "supports");
    }

    @Override
    protected Support getItemFromCursor(Cursor cursor) {
        Support support = new Support();
        support.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        support.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
        support.setNote(cursor.getString(cursor.getColumnIndexOrThrow("note")));
        support.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
        support.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
        support.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
        support.setUserId(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
        return support;
    }

    @Override
    protected ContentValues getContentValues(Support item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", item.getAddress());
        contentValues.put("note", item.getNote());
        contentValues.put("time", item.getTime());
        contentValues.put("price", item.getPrice());
        contentValues.put("type", item.getType());
        contentValues.put("user_id", item.getUserId());
        return contentValues;
    }
    public void SaveSchedule(Support support){
        this.create(support) ;
    }
}
