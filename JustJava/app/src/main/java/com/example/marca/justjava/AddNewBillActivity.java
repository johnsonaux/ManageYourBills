package com.example.marca.justjava;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Data.BillsDbHelper;
import Data.DBContract;

public class AddNewBillActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final Calendar calendar = Calendar.getInstance();
    private String mName = null;
    private int mGender;
    private DatePicker mDatePickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        Intent intent = getIntent();
        setmName(intent.getStringExtra(StartFragment.EXTRA_MESSAGE));
        if (intent.getStringExtra(StartFragment.EXTRA_MESSAGE2).equals(StartFragment.GENDER_MEN)) {
            setmGender(0);
        } else {
            setmGender(1);
        }
        mDatePickerView = (DatePicker) findViewById(R.id.datepicker_view);
        DatePickerFragment newFrag = new DatePickerFragment();
        newFrag.show(getSupportFragmentManager(), "datePicker");
        TextView tv_name = (TextView) findViewById(R.id.aktuell_Zahlender);
        tv_name.setText("Neue Rechnung von " + getmName());
        Button btn_addBill = (Button) findViewById(R.id.btn_save);
        btn_addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveData())
                    finish();

            }
        });

    }

    public boolean saveData() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date dateObject = null;

        String infoStringCat;
        String category;
        String amountString;
        String comment;
        String datum;
        CheckBox check_leisure = (CheckBox) findViewById(R.id.checkbox_Freizeit);
        CheckBox check_foods = (CheckBox) findViewById(R.id.checkbox_Lebensmittel);
        CheckBox check_vacation = (CheckBox) findViewById(R.id.checkbox_Urlaub);
        CheckBox check_home = (CheckBox) findViewById(R.id.checkbox_Wohnung);
        if (check_leisure.isChecked()) {
            category = "F";
            infoStringCat = "Freizeit";
        } else if (check_foods.isChecked()) {
            category = "L";
            infoStringCat = "Lebensmittel";
        } else if (check_vacation.isChecked()) {
            category = "U";
            infoStringCat = "Urlaub";
        } else if (check_home.isChecked()) {
            category = "W";
            infoStringCat = "Wohnung";
        } else {
            Toast.makeText(this, "Bitte eine Kategorie auswählen :-)", Toast.LENGTH_LONG).show();
            return false;
        }

        EditText edit_expenses = (EditText) findViewById(R.id.editText_ausgabe);
        amountString = edit_expenses.getText().toString().trim();
        EditText edit_comment = (EditText) findViewById(R.id.editText_comment);
        comment = edit_comment.getText().toString().trim();

        int year = mDatePickerView.getYear();
        int month = mDatePickerView.getMonth();
        int day = mDatePickerView.getDayOfMonth();
        month++;

        //create date as String
        if (month < 10) {
            datum = day + ".0" + month + "." + year;
            try {
                dateObject = dateFormat.parse(datum);
            } catch (ParseException e) {
                Toast.makeText(this, "Fehler beim Datum Parsing :-)", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        } else {
            datum = day + "." + month + "." + year;
            try {
                dateObject = dateFormat.parse(datum);
            } catch (ParseException e) {
                Toast.makeText(this, "Fehler beim Datum Parsing :-)", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        double betrag = Double.parseDouble(amountString);
        betrag = Math.floor(betrag * 100) / 100;

        BillsDbHelper mDbHelper = new BillsDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_BILLS_WHO, getmName());
        values.put(DBContract.DBEntry.COLUMN_BILLS_DATE, dateObject.getTime());
        values.put(DBContract.DBEntry.COLUMN_BILLS_AMOUNT, betrag);
        values.put(DBContract.DBEntry.COLUMN_BILLS_CATEGORY, category);
        values.put(DBContract.DBEntry.COLUMN_BILLS_COMMENT, comment);
        values.put(DBContract.DBEntry.COLUMN_BILLS_GENDER, getmGender());

        long newRowId = db.insert(DBContract.DBEntry.TABLE_NAME_BILLS, null, values);

        //Uri uri = getContentResolver().insert(DBContract.DBEntry.CONTENT_URI, values);

        //long newRowId = ContentUris.parseId(uri);

        if (newRowId == -1) {
            Toast.makeText(this, "Fehler beim speichern", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Rechnung von " + getmName() + " \nin " + infoStringCat + " gespeichert (" + newRowId + ")", Toast.LENGTH_LONG).show();
        }
        return true;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmGender() {
        return mGender;
    }

    public void setmGender(int mGender) {
        this.mGender = mGender;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDatePickerView.updateDate(year, month, dayOfMonth);
        month++;

    }
}
