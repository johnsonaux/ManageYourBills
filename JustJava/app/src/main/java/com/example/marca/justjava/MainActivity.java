package com.example.marca.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("mainActivity", "onCreate()");
        setContentView(R.layout.main_viewpager);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filterActivity:
                Intent intent_filter = new Intent(this, FilterActivity.class);
                startActivity(intent_filter);
                return true;
            case R.id.action_back_to_mainActivity:
                Intent intent_startseite = new Intent(this, MainActivity.class);
                startActivity(intent_startseite);
                return true;
            case R.id.send_email:
                sendEmail();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void sendEmail() {
        ArrayList<Bill> billsList = QueryDBUtils.getArrayList(this, null);
        StringBuilder builder = new StringBuilder();
        builder.append("ID / Datum / Name / Betrag / Kommentar / Kategorie").append(System.getProperty("line.separator"));

        for (Bill bill : billsList) {

            builder.append(System.getProperty("line.separator"));
            //get Date from long value
            Date dateObject = new Date(bill.getmDate());

            //select category and build full word instead of just one letter stored in db
            String category = "";
            if (bill.getmCategory().equals("F")) {
                category = "Freizeit";
            } else if (bill.getmCategory().equals("L")) {
                category = "Lebensmittel";
            } else if (bill.getmCategory().equals("U")) {
                category = "Urlaub";
            } else if (bill.getmCategory().equals("W")) {
                category = "Wohnung";
            }

            //one bill as one String
            String neueRechnung = Integer.toString(bill.getmId()) + " / " +
                    DATE_FORMAT.format(dateObject) + " / " +
                    bill.getmName() + " / " +
                    bill.getmAmount() + " / " +
                    bill.getmText() + " / " +
                    category;

            builder.append(neueRechnung);
        }

        Log.v("Text Email: \n", builder.toString());

        String[] addresses = new String[]{"marc.aumueller@outlook.com"};
        //Email Intent
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Auflistung aller Rechnungen");
        emailIntent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        boolean a = emailIntent.resolveActivity(getPackageManager()) != null;
        Log.v("boolean: ", "" + a);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }


}
