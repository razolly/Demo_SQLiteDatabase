package com.example.razli.databasedemo;

import android.database.Cursor;
import android.database.SQLException;
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

        try {
            SQLiteDatabase mDatabase = this.openOrCreateDatabase("Cats", MODE_PRIVATE, null);

            // Create new table in database
            mDatabase.execSQL("CREATE TABLE IF NOT EXISTS kitties (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");

            // Add to database. Note: Primary key is not included in the brackets. SQLite handles it automatically
            mDatabase.execSQL("INSERT INTO kitties (name, age) VALUES ('Rocky', 14)");
            mDatabase.execSQL("INSERT INTO kitties (name, age) VALUES ('Tabbie', 7)");
            mDatabase.execSQL("INSERT INTO kitties (name, age) VALUES ('Mindie', 7)");
            mDatabase.execSQL("INSERT INTO kitties (name, age) VALUES ('Lucky', 18)");

            /*
            Queries
            SELECT * FROM kitties WHERE age < 18
            SELECT * FROM kitties WHERE name = 'Rocky'
            SELECT * FROM kitties WHERE name = 'Rocky' AND age = 14
            SELECT * FROM kitties WHERE name LIKE 'R%'              // Starts with R
            SELECT * FROM kitties WHERE name LIKE '%r%'             // Has an r
            SELECT * FROM kitties WHERE name LIKE '%r%' LIMIT 1     // Limit to only 1 result

            DELETE FROM kitties WHERE name = 'Rocky'
            DELETE FROM kitties WHERE id = 2                        // Note this is the primary key
            */

            Cursor resultSet = mDatabase.rawQuery("SELECT * FROM kitties", null);

            int nameIndex = resultSet.getColumnIndex("name");
            int ageIndex = resultSet.getColumnIndex("age");
            int idIndex = resultSet.getColumnIndex("id");

            // Move cursor to the starting position to start reading data
            resultSet.moveToFirst();

            while (resultSet != null) {
                Log.i(TAG, "onCreate: Name: " + resultSet.getString(nameIndex));
                Log.i(TAG, "onCreate: Age: " + resultSet.getString(ageIndex));
                Log.i(TAG, "onCreate: ID: " + resultSet.getString(idIndex));

                // Moves to next row
                resultSet.moveToNext();
            }

            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
