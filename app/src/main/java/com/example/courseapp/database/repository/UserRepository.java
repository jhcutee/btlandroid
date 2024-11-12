package com.example.courseapp.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class UserRepository extends BaseRepository<User>{
    public UserRepository(DatabaseHelper dbHelper) {
        super(dbHelper, "users");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected User getItemFromCursor(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow("full_name")));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        user.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
        user.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow("avatar")));
        user.setDob(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
        user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
        user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
        return user;
    }

    @Override
    protected ContentValues getContentValues(User item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("full_name", item.getFullName());
        contentValues.put("email", item.getEmail());
        contentValues.put("gender", item.getGender());
        contentValues.put("avatar", item.getAvatar());
        contentValues.put("address", item.getAddress());
        contentValues.put("username", item.getUsername());
        contentValues.put("password", item.getPassword());
        contentValues.put("dob", item.getDob());
        return contentValues;
    }
    //    Phuong thuc viet them
    public User getUserByUsername(String username) {
        ArrayList<User> users = this.find(String.format("username = '%s'", username));
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public void registerUser(User user){
        this.create(user) ;
    }

    public void updatedProfile(User user, String username){
        String whereClause = "username = ?";
        String[] whereArgs = {username};
        this.updateProfile(user, whereClause, whereArgs);
    }

}