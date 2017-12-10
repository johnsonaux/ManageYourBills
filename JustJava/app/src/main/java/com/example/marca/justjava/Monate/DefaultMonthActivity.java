package com.example.marca.justjava.Monate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marca.justjava.BillsAdapter;
import com.example.marca.justjava.EditActivity;
import com.example.marca.justjava.FilterActivity;
import com.example.marca.justjava.MainActivity;
import com.example.marca.justjava.QueryDBUtils;
import com.example.marca.justjava.R;
import com.example.marca.justjava.Bill;
import com.example.marca.justjava.BillsContainer;

/**
 * Created by marca on 02.10.2017.
 */

public class DefaultMonthActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView mTv_sum;
    private BillsAdapter mAdapter;
    private Bill mBill;

    /*
    * selectedMonth == null implies selection of all month
    * otherwise particular month is specified
    */
    private String selectedMonth = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_activities);

        mTv_sum = (TextView) findViewById(R.id.summeMonat);
        mTv_sum.setText(QueryDBUtils.calculateSum(this, selectedMonth));

        mListView = (ListView) findViewById(R.id.liste_monate);
        mAdapter = QueryDBUtils.readDB(this, selectedMonth);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mBill = mAdapter.getItem(position);
                BillsContainer container = BillsContainer.instance();
                container.setmBill(mBill);
                Intent toEditIntent = new Intent(DefaultMonthActivity.this, EditActivity.class);
                startActivity(toEditIntent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = QueryDBUtils.readDB(this, selectedMonth);
        mListView.setAdapter(mAdapter);
        mTv_sum.setText(QueryDBUtils.calculateSum(this, selectedMonth));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filterActivity:
                Intent intent_filter = new Intent(DefaultMonthActivity.this, FilterActivity.class);
                startActivity(intent_filter);
                return true;
            case R.id.action_back_to_mainActivity:
                Intent intent_startseite = new Intent(DefaultMonthActivity.this, MainActivity.class);
                startActivity(intent_startseite);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }
}
