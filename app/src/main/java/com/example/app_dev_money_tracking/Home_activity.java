package com.example.app_dev_money_tracking;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home_activity extends AppCompatActivity {
    private PieChart pieChart;

    private ArrayList<Account> accounts;
    private List<RecordsModel> records;
    private RecyclerView Accounts_recycler;
    private RecyclerView Records_recycler;
    private User_settings user_settings;
    private DrawerLayout drawer;
    private TextView balance_text;


//    private Button addRecordHomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database recordsDB =new Database(this);
        records = recordsDB.getRecords();
        Button adjustBalance = (Button) findViewById(R.id.btn_adjust_b);
        Button addRecordHomeButton = findViewById(R.id.add_record_home_button);
//        addRecordHomeButton.setOnClickListener(onAddRecordButtonClick());


//        btn_adjust_balan.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if (accounts.size() > 0)
//                {
//                    accounts.get(0).setBalance(Double.parseDouble(balance_text.getText().toString()));
//                    user_settings.SaveAccounts(accounts);
//                }
//            }
//        });

        balance_text = findViewById(R.id.txt_e_balance);
        user_settings = User_settings.instanciate("user1", this);
        user_settings.set_currency("EUR");
        setContentView(R.layout.activity_home);
        pieChart = findViewById(R.id.Piechart_view);
        Records_recycler = findViewById(R.id.Rec_view_expanses);
        SetupPieChart();
        loadData();

//        records = new ArrayList<>();
        accounts = user_settings.retrieveAccounts();
        if (accounts == null) {
            accounts = new ArrayList<>();
            setAccountInfo();
        }
//        set_record_data();

        setAdapters();


        // Menu navigation

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        View header = navigationView.getHeaderView(0);
        TextView emailDisplay = header.findViewById(R.id.userEmailDisplay);
        User_settings user_settings = User_settings.instanciate("user1", this);
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
                    startActivity(new Intent(Home_activity.this, Convert_currency_activity.class));
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
    private View.OnClickListener onAddRecordButtonClick() {
        return v -> startActivity(new Intent(Home_activity.this, NewRecord.class));
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void On_show_more_click(View view) {
        Toast.makeText(this, "Not Implemented yet", Toast.LENGTH_SHORT).show();
    }

//    private void set_record_data() {
//        records.clear();
//        records.add(new Exp_inc_record(new Date(), 50.0, accounts.get(0), 'E', "Medical"));
//        records.add(new Exp_inc_record(new Date(), 8000.5, accounts.get(0), 'E', "Food"));
//        records.add(new Exp_inc_record(new Date(), 80.53, accounts.get(0), 'E', "Entertainment"));
//    }

    private void setAdapters() {
        Expanse_list_adapter adapter_exp = new Expanse_list_adapter(records);
        RecyclerView.LayoutManager layout_manager2 = new LinearLayoutManager(getApplicationContext());
        Records_recycler.setLayoutManager(layout_manager2);
        Records_recycler.setItemAnimator(new DefaultItemAnimator());
        Records_recycler.setAdapter(adapter_exp);
    }

    private void setAccountInfo() {
        accounts.clear();
        accounts.add(new Account(1000.2, "Cash"));
        user_settings.SaveAccounts(accounts);
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

}