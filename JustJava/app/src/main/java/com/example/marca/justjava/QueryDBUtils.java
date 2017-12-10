package com.example.marca.justjava;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Data.BillsDbHelper;
import Data.DBContract;

public final class QueryDBUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final String orderBy = DBContract.DBEntry.COLUMN_BILLS_DATE + " DESC";
    private static final String[] projection = new String[]{
            DBContract.DBEntry._ID,
            DBContract.DBEntry.COLUMN_BILLS_DATE,
            DBContract.DBEntry.COLUMN_BILLS_WHO,
            DBContract.DBEntry.COLUMN_BILLS_AMOUNT,
            DBContract.DBEntry.COLUMN_BILLS_CATEGORY,
            DBContract.DBEntry.COLUMN_BILLS_COMMENT,
            DBContract.DBEntry.COLUMN_BILLS_GENDER
    };
    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static SQLiteDatabase database;


    private QueryDBUtils() {
    }

    public static BillsAdapter readDB(Activity activity, String month) {


        BillsDbHelper dbHelper = new BillsDbHelper(activity);
        database = dbHelper.getReadableDatabase();
        String selection = null;
        String[] whereClauseArgs = null;
        FilterContainer filter = FilterContainer.instance();
        BillsAdapter adapter;


        ArrayList<Bill> rechnungen = new ArrayList<>();
        String betragString;


        /*
        * Check current filter
        */
        if (filter.ismPersonSelected() && filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ? AND " +
                    DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";

            whereClauseArgs = new String[]{filter.getmWho(), filter.getmWhichCategory()};
        } else if (filter.ismPersonSelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ?";
            whereClauseArgs = new String[]{filter.getmWho()};
        } else if (filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";
            whereClauseArgs = new String[]{filter.getmWhichCategory()};
        }

        /*
        Cursor cursor = getContentResolver().query(DBContract.DBEntry.CONTENT_URI,
                projection,
                selection,
                whereClauseArgs,
                orderBy);

        */

        Cursor cursor = database.query(
                DBContract.DBEntry.TABLE_NAME_BILLS,
                projection,
                selection,
                whereClauseArgs,
                null,
                null,
                orderBy
        );

        int idIndex = cursor.getColumnIndex(DBContract.DBEntry._ID);
        int dateIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_DATE);
        int whoIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_WHO);
        int amountIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_AMOUNT);
        int catIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_CATEGORY);
        int commentIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_COMMENT);
        int genderIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_GENDER);


        while (cursor.moveToNext()) {

            int billsId = cursor.getInt(idIndex);
            long currentDate = cursor.getLong(dateIndex);
            double currentAmount = cursor.getDouble(amountIndex);
            String currentComment = cursor.getString(commentIndex);
            String currentCategory = cursor.getString(catIndex);
            String currentWho = cursor.getString(whoIndex);
            int currentGender = cursor.getInt(genderIndex);

            //for Bill constructor amount as String
            betragString = Double.toString(currentAmount);

            Date dateObject = new Date(currentDate);
            String dateSplitter = DATE_FORMAT.format(dateObject);
            String[] date = dateSplitter.split("\\.");
            String monthSelected = date[1];

            //null indicates selection of all bills in database
            if (month == null) {
                Bill bill = new Bill(billsId, currentWho, currentDate, currentComment, betragString, currentCategory, currentGender);
                rechnungen.add(bill);

                //otherwise month is specified
            } else {
                if (month.equals(monthSelected)) {
                    Bill bill = new Bill(billsId, currentWho, currentDate, currentComment, betragString, currentCategory, currentGender);
                    rechnungen.add(bill);
                }
            }


        }


        //BillsAdapter erstellen mit der erhaltenen ArrayList und zurückgeben
        adapter = new BillsAdapter(activity, rechnungen);
        cursor.close();
        database.close();
        return adapter;

    }

    /*
    private static ContentResolver getContentResolver() {

        ContentResolver resolver = getContentResolver();
        return resolver;
    }
    */
    public static String calculateSum(Activity activity, String month) {

        BillsDbHelper dbHelper = new BillsDbHelper(activity);
        database = dbHelper.getReadableDatabase();
        String selection = null;
        String[] whereClauseArgs = null;
        FilterContainer filter = FilterContainer.instance();
        double sum = 0.0;


        if (filter.ismPersonSelected() && filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ? AND " +
                    DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";

            whereClauseArgs = new String[]{filter.getmWho(), filter.getmWhichCategory()};
        } else if (filter.ismPersonSelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ?";
            whereClauseArgs = new String[]{filter.getmWho()};
        } else if (filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";
            whereClauseArgs = new String[]{filter.getmWhichCategory()};
        }


        Cursor cursor = database.query(
                DBContract.DBEntry.TABLE_NAME_BILLS,
                projection,
                selection,
                whereClauseArgs,
                null,
                null,
                orderBy
        );

        int dateIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_DATE);
        int amountIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_AMOUNT);

        while (cursor.moveToNext()) {


            long currentDate = cursor.getLong(dateIndex);
            double currentAmount = cursor.getDouble(amountIndex);

            Date dateObject = new Date(currentDate);
            String dateSplitter = DATE_FORMAT.format(dateObject);
            String[] date = dateSplitter.split("\\.");
            String monthSelected = date[1];

            if (month == null) {
                sum = sum + currentAmount;
            } else {
                if (month.equals(monthSelected)) {
                    sum = sum + currentAmount;
                }
            }

        }
        cursor.close();
        database.close();
        return DECIMAL_FORMAT.format(sum);
    }

    protected static String calculateSingleUserOrCat(Activity activity, String month, String category, String who) {

        BillsDbHelper dbHelper = new BillsDbHelper(activity);
        database = dbHelper.getReadableDatabase();
        String selection = null;
        String[] whereClauseArgs = null;
        FilterContainer filter = FilterContainer.instance();
        double sum = 0.0;


        if (filter.ismPersonSelected() && filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ? AND " +
                    DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";

            whereClauseArgs = new String[]{filter.getmWho(), filter.getmWhichCategory()};
        } else if (filter.ismPersonSelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ?";
            whereClauseArgs = new String[]{filter.getmWho()};
        } else if (filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";
            whereClauseArgs = new String[]{filter.getmWhichCategory()};
        }

        Cursor cursor = database.query(
                DBContract.DBEntry.TABLE_NAME_BILLS,
                projection,
                selection,
                whereClauseArgs,
                null,
                null,
                orderBy
        );


        int dateInex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_DATE);
        int whoindex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_WHO);
        int amountIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_AMOUNT);
        int catIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_CATEGORY);

        while (cursor.moveToNext()) {

            long currentDate = cursor.getLong(dateInex);
            String currentWho = cursor.getString(whoindex);
            double currentAmount = cursor.getDouble(amountIndex);
            String currentCategory = cursor.getString(catIndex);

            Date dateObject = new Date(currentDate);
            String dateSplitter = DATE_FORMAT.format(dateObject);
            String[] date = dateSplitter.split("\\.");
            String monthSelected = date[1];

            //Getting sum of specified category only
            if (who == null && category != null) {
                if ((category.equals(currentCategory))) {

                    if (month == null) {
                        sum = sum + currentAmount;

                    } else {
                        if (month.equals(monthSelected)) {
                            sum = sum + currentAmount;
                        }
                    }
                }

            }

            //Getting sum of specified user only
            else if (who != null && category == null) {
                if (who.equals(currentWho)) {

                    if (month == null) {
                        sum = sum + currentAmount;

                    } else {
                        if (month.equals(monthSelected)) {
                            sum = sum + currentAmount;
                        }
                    }
                }
            }

            //Getting sum of both specified category and user
            else if (category != null && who != null) {
                if (who.equals(currentWho) && category.equals(currentCategory)) {

                    if (month == null) {
                        sum = sum + currentAmount;

                    } else {
                        if (month.equals(monthSelected)) {
                            sum = sum + currentAmount;
                        }
                    }
                }

            }


        }
        cursor.close();
        database.close();
        return DECIMAL_FORMAT.format(sum);
    }

    /*
    * used when sending data via email - just needed until firebase connection is realized
    */
    protected static ArrayList<Bill> getArrayList(Activity activity, String month) {

        BillsDbHelper dbHelper = new BillsDbHelper(activity);
        database = dbHelper.getReadableDatabase();
        String selection = null;
        String[] whereClauseArgs = null;
        FilterContainer filter = FilterContainer.instance();


        ArrayList<Bill> billsList = new ArrayList<>();


        if (filter.ismPersonSelected() && filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ? AND " +
                    DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";

            whereClauseArgs = new String[]{filter.getmWho(), filter.getmWhichCategory()};
        } else if (filter.ismPersonSelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_WHO + " = ?";
            whereClauseArgs = new String[]{filter.getmWho()};
        } else if (filter.ismCategorySelected()) {
            selection = DBContract.DBEntry.COLUMN_BILLS_CATEGORY + " = ?";
            whereClauseArgs = new String[]{filter.getmWhichCategory()};
        }

        Cursor cursor = database.query(
                DBContract.DBEntry.TABLE_NAME_BILLS,
                projection,
                selection,
                whereClauseArgs,
                null,
                null,
                orderBy
        );


        int idIndex = cursor.getColumnIndex(DBContract.DBEntry._ID);
        int dateIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_DATE);
        int whoIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_WHO);
        int amountIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_AMOUNT);
        int catIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_CATEGORY);
        int commentIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_COMMENT);

        while (cursor.moveToNext()) {

            int rechnungId = cursor.getInt(idIndex);
            long currentDate = cursor.getLong(dateIndex);
            String currentWho = cursor.getString(whoIndex);
            double currentAmount = cursor.getDouble(amountIndex);
            String currentCategory = cursor.getString(catIndex);
            String currentComment = cursor.getString(commentIndex);

            String amountString = DECIMAL_FORMAT.format(currentAmount);

            Date dateObject = new Date(currentDate);
            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dateSplitter = DATE_FORMAT.format(dateObject);
            String[] date = dateSplitter.split("\\.");
            String monthSelected = date[1];

            if (month == null) {
                Bill bill = new Bill(rechnungId, currentWho, currentDate, currentComment, amountString, currentCategory);
                billsList.add(bill);

            } else {
                if (month.equals(monthSelected)) {
                    Bill bill = new Bill(rechnungId, currentWho, currentDate, currentComment, amountString, currentCategory);
                    billsList.add(bill);
                }
            }

        }
        cursor.close();
        database.close();
        return billsList;
    }

    protected static UserAdapter loadUsers(Activity activity) {

        ArrayList<User> userList = new ArrayList<>();
        BillsDbHelper dbHelper = new BillsDbHelper(activity);
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


        int werIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_USER_NAME);
        int genderIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_USER_GENDER);

        while (cursor.moveToNext()) {

            int gender = cursor.getInt(genderIndex);
            String name = cursor.getString(werIndex);
            User user = new User(name, gender);
            userList.add(user);
        }
        UserAdapter userAdapter = new UserAdapter(activity, userList);
        if (!userAdapter.isEmpty()) {
            StartFragment.mEmptyView.setVisibility(View.GONE);
        }
        cursor.close();
        return userAdapter;

    }
}
