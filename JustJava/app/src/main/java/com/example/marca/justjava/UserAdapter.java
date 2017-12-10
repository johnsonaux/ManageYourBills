package com.example.marca.justjava;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by marca on 27.11.2017.
 */

public class UserAdapter extends ArrayAdapter {

    public UserAdapter(Activity context, ArrayList<User> userList) {
        super(context, 0, userList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_user_item, parent, false);
        }

        final User currentUser = (User) getItem(position);

        TextView nameUser = (TextView) listItemView.findViewById(R.id.tv_user_name);
        nameUser.setText(currentUser.getmName());

        //set background of TextView nameUser
        int gender = currentUser.getmGender();
        if (gender == 0) {
            nameUser.setBackgroundResource(R.color.background_men);
        } else {
            nameUser.setBackgroundResource(R.color.background_women);
        }

        return listItemView;

    }
}
