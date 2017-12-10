package com.example.marca.justjava;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class MonthListFragment extends Fragment {


    public MonthListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.month_list, container, false);

        TextView tv_august = (TextView) rootView.findViewById(R.id.august);
        tv_august.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), August.class);
                startActivity(intent);
            }
        });

        TextView tv_jan = (TextView) rootView.findViewById(R.id.januar);
        tv_jan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), January.class);
                startActivity(intent);
            }
        });

        TextView tv_feb = (TextView) rootView.findViewById(R.id.februar);
        tv_feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), February.class);
                startActivity(intent);
            }
        });

        TextView tv_marz = (TextView) rootView.findViewById(R.id.märz);
        tv_marz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), March.class);
                startActivity(intent);
            }
        });

        TextView tv_april = (TextView) rootView.findViewById(R.id.april);
        tv_april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), April.class);
                startActivity(intent);
            }
        });

        TextView tv_mai = (TextView) rootView.findViewById(R.id.mai);
        tv_mai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), May.class);
                startActivity(intent);
            }
        });

        TextView tv_juni = (TextView) rootView.findViewById(R.id.juni);
        tv_juni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), June.class);
                startActivity(intent);
            }
        });

        TextView tv_juli = (TextView) rootView.findViewById(R.id.juli);
        tv_juli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), July.class);
                startActivity(intent);
            }
        });

        TextView tv_sept = (TextView) rootView.findViewById(R.id.september);
        tv_sept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), September.class);
                startActivity(intent);
            }
        });

        TextView tv_oct = (TextView) rootView.findViewById(R.id.oktober);
        tv_oct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), October.class);
                startActivity(intent);
            }
        });

        TextView tv_nov = (TextView) rootView.findViewById(R.id.november);
        tv_nov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), November.class);
                startActivity(intent);
            }
        });

        TextView tv_dez = (TextView) rootView.findViewById(R.id.dezember);
        tv_dez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), December.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
