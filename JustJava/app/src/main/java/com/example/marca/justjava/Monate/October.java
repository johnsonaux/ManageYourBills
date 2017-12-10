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

import com.example.marca.justjava.Bill;
import com.example.marca.justjava.BillsAdapter;
import com.example.marca.justjava.EditActivity;
import com.example.marca.justjava.FilterActivity;
import com.example.marca.justjava.MainActivity;
import com.example.marca.justjava.QueryDBUtils;
import com.example.marca.justjava.R;
import com.example.marca.justjava.BillsContainer;

/**
 * Created by marca on 28.09.2017.
 */

public class October extends DefaultMonthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setSelectedMonth("10");
        super.onCreate(savedInstanceState);
    }


}
