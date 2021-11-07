package com.example.app_dev_money_tracking;

import static com.example.app_dev_money_tracking.RecordTypeModel.*;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

class Expanse_list_adapter extends RecyclerView.Adapter<Expanse_list_adapter.MyViewHolder> {
    private List<RecordsModel> record_list;

    public Expanse_list_adapter(List<RecordsModel> records) {
        this.record_list = records;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public BreakIterator RecordType;
        private TextView Category;
        private TextView Account_name;
        private TextView Amount;
        private TextView Date;

        public MyViewHolder(final View view) {
            super(view);
            Category = view.findViewById(R.id.Txt_home_cat);
            Amount = view.findViewById(R.id.Txt_home_amount);
            Date = view.findViewById(R.id.Txt_home_record_date);

        }

    }

    @NonNull
    @Override
    public Expanse_list_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_expanses_adp, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Expanse_list_adapter.MyViewHolder holder, int position) {
        int categoryId = record_list.get(position).getCategoryId();
        String date = record_list.get(position).getDate();
        double amount = record_list.get(position).getAmount();
        RecordTypeKey recordType = record_list.get(position).getRecordType();

        holder.Date.setText(date);
        holder.Amount.setText(amount + "€");
        if (recordType.equals(RecordTypeKey.E)) {
            holder.Amount.setTextColor(Color.RED);
        } else {
            holder.Amount.setTextColor(Color.GREEN);
        }
//        holder.Category.setText();
    }

    @Override
    public int getItemCount() {
        return record_list == null ? 0 : record_list.size();
    }

}
