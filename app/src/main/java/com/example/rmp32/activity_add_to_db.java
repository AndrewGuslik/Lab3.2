package com.example.rmp32;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class activity_add_to_db extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private EditText inputLastName, inputFirstName, inputMiddleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_db);

        dbHandler = new DatabaseHandler(this);
        inputLastName = findViewById(R.id.input_last_name);
        inputFirstName = findViewById(R.id.input_first_name);
        inputMiddleName = findViewById(R.id.input_middle_name);
    }

    public void saveRecord(View view) {
        String lastName = inputLastName.getText().toString().trim();
        String firstName = inputFirstName.getText().toString().trim();
        String middleName = inputMiddleName.getText().toString().trim();

        if (!lastName.isEmpty() && !firstName.isEmpty() && !middleName.isEmpty()) {
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.COLUMN_LAST_NAME, lastName);
            values.put(DatabaseHandler.COLUMN_FIRST_NAME, firstName);
            values.put(DatabaseHandler.COLUMN_MIDDLE_NAME, middleName);

            long newRowId = db.insert(DatabaseHandler.TABLE_NAME, null, values);

            if (newRowId != -1) {
                setResult(RESULT_OK);
                finish();
            } else {
                setResult(RESULT_CANCELED);
                finish();
            }

            db.close();
        }
    }
}