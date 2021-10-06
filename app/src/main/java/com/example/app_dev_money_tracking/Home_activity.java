package com.example.app_dev_money_tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;

public class Home_activity extends AppCompatActivity
{

    private PieChart pieChart;
    private ArrayList<Account> accounts;
    private RecyclerView Accounts_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        pieChart = findViewById(R.id.Piechart_view);
        Accounts_recycler = findViewById(R.id.Rec_view_list_of_acc);
        SetupPieChart();
        loadData();

        accounts = new ArrayList<>();
        setAccountInfo();
        setAdapter();
    }

    private void setAdapter()
    {
         Account_list_adapter adapter = new Account_list_adapter(accounts);
         RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(getApplicationContext());
         Accounts_recycler.setLayoutManager(layout_manager);
         Accounts_recycler.setItemAnimator(new DefaultItemAnimator());
         Accounts_recycler.setAdapter(adapter);
    }

    private void setAccountInfo()
    {
        accounts.clear();
        accounts.add(new Account(1000.2, "Cash"));
        accounts.add(new Account(3000.2, "Bank"));
        accounts.add(new Account(5000, "Savings"));
    }

    private void SetupPieChart()
    {
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

    private void loadData()
    {
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