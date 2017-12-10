package com.example.marca.justjava;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by marca on 04.10.2017.
 */

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    BillsContainer mBillsContainer;
    private Button btn_save;
    private TextView tv_name;
    private EditText edit_expenses;
    private EditText edit_comment;
    private CheckBox check_leisure;
    private CheckBox check_food;
    private CheckBox check_home;
    private CheckBox check_vacation;
    private String mWho;
    private int mId;
    private int mGender;
    private DatePicker mDatePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);

        mBillsContainer = BillsContainer.instance();

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setText(R.string.button_save_update);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDb();
                finish();
            }
        });

        Button btn_delete = (Button) findViewById(R.id.delete_button);
        btn_delete.setVisibility(View.VISIBLE);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFrag = DeleteBillsDialog.newInstance(R.string.title_dialog);
                newFrag.show(getSupportFragmentManager(), "dialog");
            }
        });

        tv_name = (TextView) findViewById(R.id.aktuell_Zahlender);
        tv_name.setText("Rechnung von " + mBillsContainer.getmBill().getmName() + " bearbeiten");
        this.mWho = mBillsContainer.getmBill().getmName();
        this.mId = mBillsContainer.getmBill().getmId();
        this.mGender = mBillsContainer.getmBill().getmGender();

        mDatePicker = (DatePicker) findViewById(R.id.datepicker_view);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mBillsContainer.getmBill().getmDate());

        mDatePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        //onDateSet(mDatePicker, getDatePickerYear(), getDatePickerMonth(), getDatePickerDay());
        edit_expenses = (EditText) findViewById(R.id.editText_ausgabe);
        edit_comment = (EditText) findViewById(R.id.editText_comment);
        edit_expenses.setText(mBillsContainer.getmBill().getmAmount());
        edit_comment.setText(mBillsContainer.getmBill().getmText());

        check_vacation = (CheckBox) findViewById(R.id.checkbox_Urlaub);
        check_leisure = (CheckBox) findViewById(R.id.checkbox_Freizeit);
        check_home = (CheckBox) findViewById(R.id.checkbox_Wohnung);
        check_food = (CheckBox) findViewById(R.id.checkbox_Lebensmittel);

        String cat = mBillsContainer.getmBill().getmCategory();
        if (cat.equals("F")) {
            check_leisure.setChecked(true);
        } else if (cat.equals("W")) {
            check_home.setChecked(true);
        } else if (cat.equals("L")) {
            check_food.setChecked(true);
        } else {
            check_vacation.setChecked(true);
        }

    }


    private boolean updateDb() {

        Date dateObject = null;
        BillsDbHelper mDbHelper = new BillsDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String category;
        String amountString = edit_expenses.getText().toString().trim();
        String comment = edit_comment.getText().toString().trim();
        String dateString;
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        month++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (month < 10) {
            dateString = day + ".0" + month + "." + year;
            try {
                dateObject = dateFormat.parse(dateString);
            } catch (ParseException e) {
                Toast.makeText(this, "Problem Parsing Datum", Toast.LENGTH_SHORT).show();
            }
        } else {
            dateString = day + "." + month + "." + year;
            try {
                dateObject = dateFormat.parse(dateString);
            } catch (ParseException e) {
                Toast.makeText(this, "Problem Parsing Datum", Toast.LENGTH_SHORT).show();
            }
        }


        if (check_leisure.isChecked()) {
            category = "F";
        } else if (check_food.isChecked()) {
            category = "L";
        } else if (check_vacation.isChecked()) {
            category = "U";
        } else if (check_home.isChecked()) {
            category = "W";
        } else {
            Toast.makeText(this, "Bitte eine Kategorie auswählen :-)", Toast.LENGTH_LONG).show();
            return false;
        }

        double betrag = Double.parseDouble(amountString);
        betrag = Math.floor(betrag * 100) / 100;

        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_BILLS_WHO, this.mWho);
        values.put(DBContract.DBEntry.COLUMN_BILLS_DATE, dateObject.getTime());
        values.put(DBContract.DBEntry.COLUMN_BILLS_AMOUNT, betrag);
        values.put(DBContract.DBEntry.COLUMN_BILLS_CATEGORY, category);
        values.put(DBContract.DBEntry.COLUMN_BILLS_COMMENT, comment);
        values.put(DBContract.DBEntry.COLUMN_BILLS_GENDER, this.mGender);

        String selection = DBContract.DBEntry._ID + " = " + this.mId;

        int count = db.update(
                DBContract.DBEntry.TABLE_NAME_BILLS,
                values,
                selection,
                null
        );

        Toast.makeText(this, count + " Rechnung wurde erfolgreich bearbeitet", Toast.LENGTH_SHORT).show();

        db.close();

        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Log.v("Datum neu: ", year + "-" + month + "-" + dayOfMonth);
        mDatePicker.updateDate(year, month, dayOfMonth);
        int i = mDatePicker.getYear();
        Log.v("Datum after change:", Integer.toString(i));
    }
}
