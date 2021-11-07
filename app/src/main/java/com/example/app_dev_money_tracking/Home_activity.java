package com.example.app_dev_money_tracking;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev_money_tracking.RecordTypeModel.RecordTypeKey;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import java.util.Calendar;

import java.util.Currency;

import java.util.List;

public class Home_activity extends AppCompatActivity {
    private PieChart pieChart;
    private List<RecordsModel> records;
    private RecyclerView Records_recycler;
    private User_settings user_settings;
    private DrawerLayout drawer;

    private Button show_more;


    UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user_settings = User_settings.instanciate("user1", this);
        Database db = new Database(this);
        user = db.getUserByEmail(user_settings.getUserEmail());
        int balance = user.getBalance();
        String userCurrency = user.getCurrency();
        records = db.getRecords();
        EditText balanceText = findViewById(R.id.homeBalanceDisplay);
        Button adjustBalance = findViewById(R.id.btn_adjust_b);
        show_more = findViewById(R.id.Btn_show_more);
        TextView calculatedBalance = findViewById(R.id.calculatedBalance);
        Button addRecordHomeButton = findViewById(R.id.add_record_home_button);
        addRecordHomeButton.setOnClickListener(onAddRecordButtonClick());


        adjustBalance.setOnClickListener(v -> {
            HelperFunctions.hideSoftKeyboard(Home_activity.this, v);
            user.setBalance(Integer.parseInt(balanceText.getText().toString()));
            boolean inserted = db.updateUser(user);
            if (inserted) {
                Toast.makeText(Home_activity.this, "Balance updated", Toast.LENGTH_SHORT).show();
                calculatedBalance.setText(String.valueOf(calculateCurrentBalance(records, user.getBalance())));
            } else {
                Toast.makeText(Home_activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

        show_more.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Home_activity.this, All_records.class));
            }
        });

        balanceText.setText(String.valueOf(balance));
        Currency c = Currency.getInstance(user.getCurrency());
        String currencySymbol = c.getSymbol();
        calculatedBalance.setText(String.valueOf(calculateCurrentBalance(records, balance)+currencySymbol));

        user_settings.set_currency("EUR");

        pieChart = findViewById(R.id.Piechart_view);
        Records_recycler = findViewById(R.id.Rec_view_expanses);
        SetupPieChart();
        loadData();

        setAdapters();

        // Menu navigation

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        View header = navigationView.getHeaderView(0);
        TextView emailDisplay = header.findViewById(R.id.userEmailDisplay);
        emailDisplay.setText(user_settings.getUserEmail());
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(Home_activity.this, Home_activity.class));
                    break;
                case R.id.nav_new_record:
                    startActivity(new Intent(Home_activity.this, NewRecord.class));
                    break;
                case R.id.nav_categories:
                    startActivity(new Intent(Home_activity.this, CategoriesActivity.class));
                    break;
                case R.id.nav_converter:
                    if (user.getAdmin() == 0) {
                        Toast.makeText(Home_activity.this, "This feature only available for premium members", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(Home_activity.this, Convert_currency_activity.class));
                    }
                    break;
                case R.id.nav_tryPremium:
                    startActivity(new Intent(Home_activity.this, PremiumContent.class));
                    break;
            }
            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //

    }

    private int calculateCurrentBalance(List<RecordsModel> records, int balance) {
        int expences = 0;
        int intakes = 0;
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (records != null) {
            for (RecordsModel record : records) {
                double amount =record.getAmount();
                if (record.getRecordType().equals(RecordTypeKey.E)) {
                    if (!user.getCurrency().equals(record.getCurrency())) {
                        Currency_conversion_data curr = new Currency_conversion_data(Home_activity.this);
                        amount = Double.parseDouble(formatter.format(curr.convert(record.getCurrency(), user.getCurrency(), amount)));
                    }
                    expences += amount;
                } else {
                    if (!user.getCurrency().equals(record.getCurrency())) {
                        Currency_conversion_data curr = new Currency_conversion_data(Home_activity.this);
                        amount = Double.parseDouble(formatter.format(curr.convert(record.getCurrency(), user.getCurrency(), amount)));
                    }
                    intakes += amount;
                }
            }
            return balance - expences + intakes;
        }
        return balance;
    }

    private View.OnClickListener onAddRecordButtonClick() {
        return v -> startActivity(new Intent(Home_activity.this, NewRecord.class));
    }



    private void setAdapters() {
        Expanse_list_adapter adapter_exp = new Expanse_list_adapter(Home_activity.this,records);
        RecyclerView.LayoutManager layout_manager2 = new LinearLayoutManager(getApplicationContext());
        Records_recycler.setLayoutManager(layout_manager2);
        Records_recycler.setItemAnimator(new DefaultItemAnimator());
        Records_recycler.setAdapter(adapter_exp);
    }


    private void SetupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setAlpha(0.85f);
//        pieChart.setEntryLabelColor(Color.BLACK);
//        pieChart.setEntryLabelTextSize(10);
//        pieChart.setCenterText("Expanses");
        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleRadius(15f);
        pieChart.setCenterTextSize(12);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setTextColor(Color.WHITE);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadData() {
        String lbl_food = "Food and drinks";
        String lbl_medical = "Medical";
        String lbl_entertainment = "Entertainment";
        String lbl_gifts = "Gifts";
        String lbl_home = "Home";

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.1f, lbl_food));
        entries.add(new PieEntry(0.2f, lbl_medical));
        entries.add(new PieEntry(0.25f, lbl_entertainment));
        entries.add(new PieEntry(0.25f, lbl_gifts));
        entries.add(new PieEntry(0.2f, lbl_home));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(colors);

        PieData data = new PieData(dataset);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));

        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        data.setHighlightEnabled(true);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}