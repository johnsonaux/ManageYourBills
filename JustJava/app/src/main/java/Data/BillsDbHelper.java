package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Data.DBContract.DBEntry;

/**
 * Created by marca on 03.09.2017.
 */

public class BillsDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BillsDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "bills.db";
    private static final int DATABASE_VERSION = 1;

    public BillsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_BILLS_TABLE = "CREATE TABLE " + DBEntry.TABLE_NAME_BILLS + " ("
                + DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBEntry.COLUMN_BILLS_WHO + " TEXT NOT NULL, "
                + DBEntry.COLUMN_BILLS_AMOUNT + " REAL NOT NULL, "
                + DBEntry.COLUMN_BILLS_DATE + " INTEGER NOT NULL, "
                + DBEntry.COLUMN_BILLS_COMMENT + " TEXT, "
                + DBEntry.COLUMN_BILLS_GENDER + " INTEGER NOT NULL, "
                + DBEntry.COLUMN_BILLS_CATEGORY + " TEXT NOT NULL);";

        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DBEntry.TABLE_NAME_USER + " ("
                + DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBEntry.COLUMN_USER_NAME + " TEXT NOT NULL, "
                + DBEntry.COLUMN_USER_GENDER + " INTEGER NOT NULL);";


        //create tables statements
        db.execSQL(SQL_CREATE_BILLS_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //implemented later

    }
}
