package com.example.app_dev_money_tracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Convert_currency_activity extends AppCompatActivity
{
    ArrayAdapter<String> currencies;
    Spinner cur_from;
    Spinner cur_to;
    Button Btn_convert;
    TextView Txt_cur_from;
    TextView Txt_cur_to;

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

        Btn_convert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String[] currency_codes = getResources().getStringArray(R.array.currency);
                String value_from = currency_codes[cur_from.getSelectedItemPosition()];
                String value_to = currency_codes[cur_to.getSelectedItemPosition()];;
                Expanse_list_adapter.Currency_conversion_data curr = new Expanse_list_adapter.Currency_conversion_data(Convert_currency_activity.this);
                Double amount = Double.parseDouble(Txt_cur_from.getText().toString());
                double converted = curr.convert(value_from, value_to, amount);
                Txt_cur_to.setText(converted + "");
            }
        });
    }

}