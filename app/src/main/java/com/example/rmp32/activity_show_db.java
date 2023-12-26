package com.example.rmp32;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_show_db extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db);

        dbHandler = new DatabaseHandler(this);
        displayText = findViewById(R.id.display_text);

        displayData();
    }

    private void displayData() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_NAME, null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID));
            String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_LAST_NAME));
            String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_FIRST_NAME));
            String middleName = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_MIDDLE_NAME));
            String timestamp = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_TIMESTAMP));

            stringBuilder.append("ID: ").append(id)
                    .append(", Фамилия: ").append(lastName)
                    .append(", Имя: ").append(firstName)
                    .append(", Отчество: ").append(middleName)
                    .append(", Время добавления: ").append(timestamp)
                    .append("\n");
        }

        cursor.close();
        db.close();

        displayText.setText(stringBuilder.toString());
    }

}