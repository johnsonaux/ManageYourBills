package com.example.marca.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * hard-coded for Marc and Laura yet
 */


public class FilterActivity extends AppCompatActivity {

    private static boolean radioBoolLaura = false;
    private static boolean radioBoolMarc = false;
    private static boolean switchBoolLeisure = false;
    private static boolean switchBoolVacation = false;
    private static boolean switchBoolHome = false;
    private static boolean switchBoolFoods = false;
    private FilterContainer mContainer;
    private RadioButton radioButton_laura;
    private RadioButton radioButton_marc;
    private RadioButton radioButton_both;
    private SwitchCompat switch_foods;
    private SwitchCompat switch_vacation;
    private SwitchCompat switch_leisure;
    private SwitchCompat switch_home;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        radioButton_laura = (RadioButton) findViewById(R.id.radio_laura);
        radioButton_laura.setChecked(radioBoolLaura);
        radioButton_marc = (RadioButton) findViewById(R.id.radio_marc);
        radioButton_marc.setChecked(radioBoolMarc);
        radioButton_both = (RadioButton) findViewById(R.id.radio_beide);
        switch_foods = (SwitchCompat) findViewById(R.id.switch_lebensmittel);
        switch_foods.setChecked(switchBoolFoods);
        switch_vacation = (SwitchCompat) findViewById(R.id.switch_urlaub);
        switch_vacation.setChecked(switchBoolVacation);
        switch_leisure = (SwitchCompat) findViewById(R.id.switch_freizeit);
        switch_leisure.setChecked(switchBoolLeisure);
        switch_home = (SwitchCompat) findViewById(R.id.switch_wohnung);
        switch_home.setChecked(switchBoolHome);

        Button btn_filter = (Button) findViewById(R.id.btn_filterAnwenden);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFilter();
                Toast toast = Toast.makeText(getApplicationContext(), "Neuer Filter festgelegt", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

        Button btn_removeFilter = (Button) findViewById(R.id.btn_filterZurücksetzen);
        btn_removeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noFilterAnymore();
                Toast toast = Toast.makeText(getApplicationContext(), "Alle Filter zurückgesetzt", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }

    public void toFilter() {
        mContainer = FilterContainer.instance();

        if (radioButton_laura.isChecked()) {
            mContainer.setmPersonSelected(true);
            mContainer.setmWho("Laura");
            radioBoolLaura = true;
            radioBoolMarc = false;
        } else if (radioButton_marc.isChecked()) {
            mContainer.setmPersonSelected(true);
            mContainer.setmWho("Marc");
            radioBoolLaura = false;
            radioBoolMarc = true;
        } else if (radioButton_both.isChecked()) {
            mContainer.setmPersonSelected(false);
            radioBoolLaura = false;
            radioBoolMarc = false;
        } else {
            mContainer.setmPersonSelected(false);
            radioBoolLaura = false;
            radioBoolMarc = false;
        }

        if (switch_leisure.isChecked()) {
            mContainer.setmCategorySelected(true);
            mContainer.setWhichCat("F");
            switchBoolLeisure = true;
            switchBoolFoods = false;
            switchBoolVacation = false;
            switchBoolHome = false;
        } else if (switch_foods.isChecked()) {
            mContainer.setmCategorySelected(true);
            mContainer.setWhichCat("L");
            switchBoolLeisure = false;
            switchBoolFoods = true;
            switchBoolVacation = false;
            switchBoolHome = false;
        } else if (switch_vacation.isChecked()) {
            mContainer.setmCategorySelected(true);
            mContainer.setWhichCat("U");
            switchBoolLeisure = false;
            switchBoolFoods = false;
            switchBoolVacation = true;
            switchBoolHome = false;
        } else if (switch_home.isChecked()) {
            mContainer.setmCategorySelected(true);
            mContainer.setWhichCat("W");
            switchBoolLeisure = false;
            switchBoolFoods = false;
            switchBoolVacation = false;
            switchBoolHome = true;
        } else {
            mContainer.setmCategorySelected(false);
            switchBoolLeisure = false;
            switchBoolFoods = false;
            switchBoolVacation = false;
            switchBoolHome = false;
        }
    }

    public void noFilterAnymore() {

        mContainer = FilterContainer.instance();
        mContainer.setmCategorySelected(false);
        mContainer.setmPersonSelected(false);

        radioBoolLaura = false;
        radioBoolMarc = false;
        switchBoolLeisure = false;
        switchBoolFoods = false;
        switchBoolVacation = false;
        switchBoolHome = false;
    }

}
