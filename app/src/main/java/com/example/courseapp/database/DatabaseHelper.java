package com.example.courseapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "courseapp.db";
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DatabaseHelper", "DatabaseHelper");
        this.context = context;
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DatabaseHelper", "onCreate");
        try {
            InputStream inputStream = context.getAssets().open("database.sql");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            String createScript = stringBuilder.toString();
            Log.i("DatabaseHelper", createScript);
            bufferedReader.close();
            String[] statements = createScript.split(";");

            for (String statement : statements) {
                Log.i("DatabaseHelper", statement);
                if (statement.trim().length() > 0)
                    db.execSQL(statement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
