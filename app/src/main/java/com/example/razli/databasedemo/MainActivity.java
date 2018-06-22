package com.example.razli.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

// https://www.udemy.com/the-complete-android-oreo-developer-course/learn/v4/t/lecture/8339540?start=1635

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase mDatabase = this.openOrCreateDatabase("Cats", MODE_PRIVATE, null);

        // Create new table in database
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS cats (name VARCHAR, age INT(3))");

        // Add to database
        mDatabase.execSQL("INSERT INTO cats (name, age) VALUES ('Rocky', 14)");
        mDatabase.execSQL("INSERT INTO cats (name, age) VALUES ('Tabbie', 7)");

        Cursor resultSet = mDatabase.rawQuery("SELECT * FROM cats", null);

        int nameIndex = resultSet.getColumnIndex("name");
        int ageIndex = resultSet.getColumnIndex("age");

        // Move cursor to the starting position to start reading data
        resultSet.moveToFirst();

        while(resultSet != null) {
            Log.i(TAG, "onCreate: Name: " + resultSet.getString(nameIndex));
            Log.i(TAG, "onCreate: Age: " + resultSet.getString(ageIndex));

            // Moves to next row
            resultSet.moveToNext();
        }

        resultSet.close();
    }
}
