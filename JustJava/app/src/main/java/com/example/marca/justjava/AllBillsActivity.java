package com.example.marca.justjava;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class AllBillsActivity extends AppCompatActivity {

    BillsAdapter mAdapter;
    private ListView mListView;
    private TextView mTv_sum;
    private Bill mBill;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_activities);

        mListView = (ListView) findViewById(R.id.liste_monate);

        mAdapter = QueryDBUtils.readDB(this, null);
        mListView.setAdapter(mAdapter);

        mTv_sum = (TextView) findViewById(R.id.summeMonat);
        mTv_sum.setText(QueryDBUtils.calculateSum(this, null));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mBill = mAdapter.getItem(position);
                BillsContainer container = BillsContainer.instance();
                container.setmBill(mBill);
                Intent toEditIntent = new Intent(AllBillsActivity.this, EditActivity.class);
                startActivity(toEditIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = QueryDBUtils.readDB(this, null);
        mListView.setAdapter(mAdapter);
        mTv_sum.setText(QueryDBUtils.calculateSum(this, null));
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = QueryDBUtils.readDB(this, null);
        mListView.setAdapter(mAdapter);
        mTv_sum.setText(QueryDBUtils.calculateSum(this, null));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filterActivity:
                Intent intent_filter = new Intent(AllBillsActivity.this, FilterActivity.class);
                startActivity(intent_filter);
                return true;
            case R.id.action_back_to_mainActivity:
                Intent intent_startseite = new Intent(AllBillsActivity.this, MainActivity.class);
                startActivity(intent_startseite);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
