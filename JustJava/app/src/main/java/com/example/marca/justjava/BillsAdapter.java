package com.example.marca.justjava;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by marca on 26.09.2017.
 */

public class BillsAdapter extends ArrayAdapter<Bill> {

    public BillsAdapter(Activity context, ArrayList<Bill> rechnungen) {
        super(context, 0, rechnungen);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Bill currentBill = getItem(position);

        Date dateObject = new Date(currentBill.getmDate());
        TextView tv_date = (TextView) listItemView.findViewById(R.id.datum_textview);
        tv_date.setText(dateFormat.format(dateObject));

        TextView tv_comment = (TextView) listItemView.findViewById(R.id.beschreibung_textview);
        tv_comment.setText(currentBill.getmText());

        TextView tv_amount = (TextView) listItemView.findViewById(R.id.betrag_textview);
        tv_amount.setText(currentBill.getmAmount());

        TextView tv_category = (TextView) listItemView.findViewById(R.id.kategorie_textview);
        tv_category.setText(currentBill.getmCategory());

        TextView tv_name = (TextView) listItemView.findViewById(R.id.user_name_list_item);
        tv_name.setText(currentBill.getmName());
        if (currentBill.getmGender() == 0) {
            tv_name.setBackgroundResource(R.color.background_men);
        } else {
            tv_name.setBackgroundResource(R.color.background_women);
        }

        return listItemView;
    }
}
