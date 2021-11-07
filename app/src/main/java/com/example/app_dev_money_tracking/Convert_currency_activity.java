package com.example.app_dev_money_tracking;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Convert_currency_activity extends AppCompatActivity
{
    ArrayAdapter<String> currencies;
    Spinner cur_from;
    Spinner cur_to;
    Button Btn_convert;
    TextView Txt_cur_from;
    TextView Txt_cur_to;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_currency);

        cur_from = findViewById(R.id.DD_cur_from);
        cur_to = findViewById(R.id.DD_cur_to);
        Btn_convert = findViewById(R.id.Btn_convert);
        Txt_cur_from = findViewById(R.id.Txt_conv_from);
        Txt_cur_to = findViewById(R.id.Txt_conv_to);

        currencies = new ArrayAdapter<>(Convert_currency_activity.this,
                                        android.R.layout.simple_list_item_1,
                                        getResources().getStringArray(R.array.currency_names));

        currencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cur_from.setAdapter(currencies);
        cur_to.setAdapter(currencies);

        // Menu navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_converter);
        View header = navigationView.getHeaderView(0);
        TextView emailDisplay = header.findViewById(R.id.userEmailDisplay);
        User_settings settings = User_settings.instanciate("user1", getApplicationContext());
        Database db = new Database(this);
        UserModel user = db.getUserByEmail(settings.getUserEmail());
        emailDisplay.setText(settings.getUserEmail());
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(Convert_currency_activity.this, Home_activity.class));
                    break;
                case R.id.nav_new_record:
                    startActivity(new Intent(Convert_currency_activity.this, NewRecord.class));
                    break;
                case R.id.nav_categories:
                    startActivity(new Intent(Convert_currency_activity.this, CategoriesActivity.class));
                    break;
                case R.id.nav_converter:
                    if (user.getAdmin() == 0) {
                        Toast.makeText(Convert_currency_activity.this, "This feature only available for premium members", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(Convert_currency_activity.this, Convert_currency_activity.class));
                    }
                    break;
                case R.id.nav_tryPremium:
                    startActivity(new Intent(Convert_currency_activity.this, PremiumContent.class));
                    break;
            }
            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Btn_convert.setOnClickListener(v -> {
            String[] currency_codes = getResources().getStringArray(R.array.currency);
            String value_from = currency_codes[cur_from.getSelectedItemPosition()];
            String value_to = currency_codes[cur_to.getSelectedItemPosition()];;
            Expanse_list_adapter.Currency_conversion_data curr = new Expanse_list_adapter.Currency_conversion_data(Convert_currency_activity.this);
            Double amount = Double.parseDouble(Txt_cur_from.getText().toString());
            double converted = curr.convert(value_from, value_to, amount);
            Txt_cur_to.setText(converted + "");
        });
    }

}