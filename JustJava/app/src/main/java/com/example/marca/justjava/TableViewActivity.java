package com.example.marca.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * not implemented for all kind of users - hard-coded for users Marc and Laura yet
 */

public class TableViewActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        TextView foodsMarc = (TextView) findViewById(R.id.lebensmittel_marc);
        TextView leisureMarc = (TextView) findViewById(R.id.freizeit_marc);
        TextView homeMarc = (TextView) findViewById(R.id.wohnung_marc);
        TextView vacationMarc = (TextView) findViewById(R.id.urlaub_marc);

        TextView foodsLaura = (TextView) findViewById(R.id.lebensmittel_laura);
        TextView leisureLaura = (TextView) findViewById(R.id.freizeit_laura);
        TextView homeLaura = (TextView) findViewById(R.id.wohnung_laura);
        TextView vacationLaura = (TextView) findViewById(R.id.urlaub_laura);

        TextView foodsTotal = (TextView) findViewById(R.id.tableSumme_lebensmittel);
        TextView leisureTotal = (TextView) findViewById(R.id.tableSumme_freizeit);
        TextView homeTotal = (TextView) findViewById(R.id.tableSumme_wohnung);
        TextView vacationTotal = (TextView) findViewById(R.id.tableSumme_urlaub);

        TextView totalMarc = (TextView) findViewById(R.id.tableSumme_marc);
        TextView totalLaura = (TextView) findViewById(R.id.tableSumme_laura);
        TextView totalAll = (TextView) findViewById(R.id.tableSumme_gesamt);

        foodsMarc.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "L", "Marc"));
        leisureMarc.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "F", "Marc"));
        homeMarc.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "W", "Marc"));
        vacationMarc.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "U", "Marc"));

        foodsLaura.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "L", "Laura"));
        leisureLaura.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "F", "Laura"));
        homeLaura.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "W", "Laura"));
        vacationLaura.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "U", "Laura"));

        foodsTotal.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "L", null));
        leisureTotal.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "F", null));
        homeTotal.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "W", null));
        vacationTotal.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, "U", null));

        totalMarc.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, null, "Marc"));
        totalLaura.setText(QueryDBUtils.calculateSingleUserOrCat(this, null, null, "Laura"));
        totalAll.setText(QueryDBUtils.calculateSum(this, null));

        LinearLayout showBills = (LinearLayout) findViewById(R.id.push_for_details);
        showBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableViewActivity.this, AllBillsActivity.class);
                startActivity(intent);
            }
        });

    }
}
