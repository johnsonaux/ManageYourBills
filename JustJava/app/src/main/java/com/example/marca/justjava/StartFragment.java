package com.example.marca.justjava;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marca.justjava.Monate.April;
import com.example.marca.justjava.Monate.August;
import com.example.marca.justjava.Monate.December;
import com.example.marca.justjava.Monate.February;
import com.example.marca.justjava.Monate.January;
import com.example.marca.justjava.Monate.July;
import com.example.marca.justjava.Monate.June;
import com.example.marca.justjava.Monate.March;
import com.example.marca.justjava.Monate.May;
import com.example.marca.justjava.Monate.November;
import com.example.marca.justjava.Monate.October;
import com.example.marca.justjava.Monate.September;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Data.BillsDbHelper;
import Data.DBContract;

public class StartFragment extends Fragment {


    public static final String EXTRA_MESSAGE = "com.example.marca.justjava.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.marca.justjava.MESSAGE2";
    public static final String GENDER_MEN = "men";
    public static final String GENDER_WOMEN = "women";
    public static TextView mEmptyView;
    private BillsDbHelper mDbHelper;
    private TextView mTv_sum;
    private ListView mListView;
    private UserAdapter mAdapter;
    private User mUser;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        mEmptyView = (TextView) rootView.findViewById(R.id.emptyView);

        mListView = (ListView) rootView.findViewById(R.id.liste_user);
        mAdapter = QueryDBUtils.loadUsers(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mUser = (User) mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), AddNewBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mUser.getmName());
                if (mUser.getmGender() == 0) {
                    intent.putExtra(EXTRA_MESSAGE2, GENDER_MEN);
                } else {
                    intent.putExtra(EXTRA_MESSAGE2, GENDER_WOMEN);
                }
                startActivity(intent);
            }
        });


        LinearLayout currentMonth = (LinearLayout) rootView.findViewById(R.id.current_expenses);
        currentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = monthIntent();
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });

        TextView tv_currentMonth = (TextView) rootView.findViewById(R.id.aktueller_Monat);
        tv_currentMonth.setText(getMonth() + ":");

        mTv_sum = (TextView) rootView.findViewById(R.id.ausgaben_monat);
        mTv_sum.setText(readNewestData());

        Button btn_table_view = (Button) rootView.findViewById(R.id.btn_ansicht_tabelle);
        btn_table_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TableViewActivity.class);
                startActivity(intent);
            }
        });

        Button btn_new_user = (Button) rootView.findViewById(R.id.btn_new_user);
        btn_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewUserActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

    public void onStart() {
        super.onStart();
        mTv_sum.setText(readNewestData());
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = QueryDBUtils.loadUsers(getActivity());
        mListView.setAdapter(mAdapter);
    }

    public String readNewestData() {

        double sum = 0.0;

        mDbHelper = new BillsDbHelper(getActivity());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.DBEntry.COLUMN_BILLS_DATE,
                DBContract.DBEntry.COLUMN_BILLS_WHO,
                DBContract.DBEntry.COLUMN_BILLS_AMOUNT,
                DBContract.DBEntry.COLUMN_BILLS_CATEGORY,
                DBContract.DBEntry.COLUMN_BILLS_COMMENT
        };

        Cursor cursor = db.query(
                DBContract.DBEntry.TABLE_NAME_BILLS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        int dateIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_DATE);
        int amountIndex = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_BILLS_AMOUNT);


        while (cursor.moveToNext()) {

            long currentDate = cursor.getLong(dateIndex);
            double currentAmount = cursor.getDouble(amountIndex);


            Date dateObject = new Date(currentDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dateSplitter = dateFormat.format(dateObject);
            String[] date = dateSplitter.split("\\.");
            String month = date[1];
            String test = getMonthToCompare();

            if (month.equals(test)) {
                sum = sum + currentAmount;
            }
        }

        sum = Math.floor(sum * 100) / 100;

        db.close();
        return "€ " + sum;

    }

    public String getMonthToCompare() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month++;
        if (month >= 10) {
            return "" + month;
        }
        return "0" + month;
    }

    public String getMonth() {
        String monthString = "";
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month++;
        switch (month) {
            case 1:
                monthString = "Jan.";
                break;
            case 2:
                monthString = "Feb.";
                break;
            case 3:
                monthString = "März";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "Mai";
                break;
            case 6:
                monthString = "Juni";
                break;
            case 7:
                monthString = "Juli";
                break;
            case 8:
                monthString = "Aug.";
                break;
            case 9:
                monthString = "Sept.";
                break;
            case 10:
                monthString = "Okt.";
                break;
            case 11:
                monthString = "Nov.";
                break;
            case 12:
                monthString = "Dez.";
                break;
        }
        return monthString;
    }

    public Intent monthIntent() {
        Intent intent = null;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        month++;
        switch (month) {
            case 1:
                intent = new Intent(getActivity(), January.class);
                break;
            case 2:
                intent = new Intent(getActivity(), February.class);
                break;
            case 3:
                intent = new Intent(getActivity(), March.class);
                break;
            case 4:
                intent = new Intent(getActivity(), April.class);
                break;
            case 5:
                intent = new Intent(getActivity(), May.class);
                break;
            case 6:
                intent = new Intent(getActivity(), June.class);
                break;
            case 7:
                intent = new Intent(getActivity(), July.class);
                break;
            case 8:
                intent = new Intent(getActivity(), August.class);
                break;
            case 9:
                intent = new Intent(getActivity(), September.class);
                break;
            case 10:
                intent = new Intent(getActivity(), October.class);
                break;
            case 11:
                intent = new Intent(getActivity(), November.class);
                break;
            case 12:
                intent = new Intent(getActivity(), December.class);
                break;
        }

        return intent;
    }

}
