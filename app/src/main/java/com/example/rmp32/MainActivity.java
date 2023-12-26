package com.example.rmp32;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rmp32.DatabaseHandler;
import com.example.rmp32.activity_add_to_db;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);
    }

    public void viewData(View view) {
        Intent intent = new Intent(this, activity_show_db.class);
        startActivity(intent);
    }

    public void openAddRecordActivity(View view) {
        Intent intent = new Intent(this, activity_add_to_db.class);
        startActivityForResult(intent, 1);
    }

    public void updateLastRecord(View view) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.COLUMN_LAST_NAME, "Иванов");
        values.put(DatabaseHandler.COLUMN_FIRST_NAME, "Иван");
        values.put(DatabaseHandler.COLUMN_MIDDLE_NAME, "Иванович");

        String selection = DatabaseHandler.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(getLastRecordId())};

        int count = db.update(DatabaseHandler.TABLE_NAME, values, selection, selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Произошла ошибка!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getLastRecordId() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + DatabaseHandler.COLUMN_ID + ") FROM "
                + DatabaseHandler.TABLE_NAME, null);

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Запись добавлена!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Произошла ошибка!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}