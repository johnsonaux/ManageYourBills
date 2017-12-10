package com.example.marca.justjava;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Data.BillsDbHelper;
import Data.DBContract;

/**
 * Created by marca on 27.11.2017.
 */

public class NewUserActivity extends AppCompatActivity {

    CheckBox mCheckMen, mCheckWomen;
    String mUsername;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        mEditText = (EditText) findViewById(R.id.edit_new_name);

        mCheckMen = (CheckBox) findViewById(R.id.checkbox_new_men);
        mCheckWomen = (CheckBox) findViewById(R.id.checkbox_new_women);

        TextView currentUsers = (TextView) findViewById(R.id.tv_registered_users);
        currentUsers.setText(loadcurrentUsers());

        Button btn_new_user = (Button) findViewById(R.id.btn_add_new);
        btn_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mEditText.getText().toString().trim();
                if (storeNewUser()) {
                    Toast.makeText(NewUserActivity.this, "Neuer User angelegt", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NewUserActivity.this, "Fehler beim Anlegen - kein neuer User angelegt", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean storeNewUser() {

        int gender;
        if (mUsername == null) {
            return false;
        }
        if (mCheckMen.isChecked()) {
            gender = 0;
        } else if (mCheckWomen.isChecked()) {
            gender = 1;
        } else {
            Toast.makeText(this, "Bitte Geschlecht auswählen", Toast.LENGTH_SHORT).show();
            return false;
        }
        BillsDbHelper mDbHelper = new BillsDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_USER_NAME, mUsername);
        values.put(DBContract.DBEntry.COLUMN_USER_GENDER, gender);

        long newRowId = db.insert(DBContract.DBEntry.TABLE_NAME_USER, null, values);

        //Uri uri = getContentResolver().insert(DBContract.DBEntry.CONTENT_URI, values);

        //long newRowId = ContentUris.parseId(uri);

        if (newRowId == -1) {
            return false;
        }
        return true;

    }

    private String loadcurrentUsers() {

        String currentUsers = "";
        BillsDbHelper dbHelper = new BillsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = new String[]{
                DBContract.DBEntry._ID,
                DBContract.DBEntry.COLUMN_USER_NAME,
                DBContract.DBEntry.COLUMN_USER_GENDER
        };

        Cursor cursor = db.query(
                DBContract.DBEntry.TABLE_NAME_USER,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        int idIndex = cursor.getColumnIndex(DBContract.DBEntry._ID);
        int whoIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_USER_NAME);
        int genderIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_USER_GENDER);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(idIndex);
            int gender = cursor.getInt(genderIndex);
            String name = cursor.getString(whoIndex);
            String genderString;
            if (gender == 0) {
                genderString = "männlich";
            } else {
                genderString = "weiblich";
            }
            currentUsers = currentUsers + id + ": " + name + ", " + genderString + "\n";
        }
        return currentUsers;
    }
}
