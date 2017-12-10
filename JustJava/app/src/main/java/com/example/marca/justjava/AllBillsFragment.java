package com.example.marca.justjava;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class AllBillsFragment extends Fragment {

    private BillsAdapter mAdapter;
    private ListView mListView;
    private TextView mTv_sum;
    private Bill mBill;

    // Required empty public constructor
    public AllBillsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.month_activities, container, false);

        mListView = (ListView) rootView.findViewById(R.id.liste_monate);

        mAdapter = QueryDBUtils.readDB(getActivity(), null);
        mListView.setAdapter(mAdapter);

        mTv_sum = (TextView) rootView.findViewById(R.id.summeMonat);
        mTv_sum.setText(QueryDBUtils.calculateSum(getActivity(), null));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mBill = mAdapter.getItem(position);
                BillsContainer container = BillsContainer.instance();
                container.setmBill(mBill);
                Intent toEditIntent = new Intent(getActivity(), EditActivity.class);
                startActivity(toEditIntent);
            }
        });

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        mAdapter = QueryDBUtils.readDB(getActivity(), null);
        mListView.setAdapter(mAdapter);
        mTv_sum.setText(QueryDBUtils.calculateSum(getActivity(), null));
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = QueryDBUtils.readDB(getActivity(), null);
        mListView.setAdapter(mAdapter);
        mTv_sum.setText(QueryDBUtils.calculateSum(getActivity(), null));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filterActivity:
                Intent intent_filter = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent_filter);
                return true;
            case R.id.action_back_to_mainActivity:
                Intent intent_startseite = new Intent(getActivity(), MainActivity.class);
                startActivity(intent_startseite);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
