package com.example.app_dev_money_tracking;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

class Expanse_list_adapter extends RecyclerView.Adapter<Expanse_list_adapter.MyViewHolder>
{
    private ArrayList<Exp_inc_record> record_list;

    public Expanse_list_adapter(ArrayList<Exp_inc_record> records)
    {
        this.record_list = records;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView Category;
        private TextView Account_name;
        private TextView Amount;
        private TextView Date;

        public MyViewHolder(final View view)
        {
            super(view);
            Category = view.findViewById(R.id.Txt_home_cat);
            Account_name = view.findViewById(R.id.Txt_home_acc);
            Amount = view.findViewById(R.id.Txt_home_amount);
            Date = view.findViewById(R.id.Txt_home_record_date);

        }

    }

    @NonNull
    @Override
    public Expanse_list_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_expanses_adp, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Expanse_list_adapter.MyViewHolder holder, int position)
    {
        String category = record_list.get(position).getCategory();
        Account account = record_list.get(position).getAccount();
        Date date = record_list.get(position).getDate();
        double amount = record_list.get(position).getAmount();

        holder.Date.setText(DateFormat.format("EE, MM d, yyyy ", date.getTime()));
        holder.Amount.setText(amount + "€");
        holder.Account_name.setText(account.getAccountName());
        holder.Category.setText(category);
    }

    @Override
    public int getItemCount()
    {
        return record_list.size();
    }

    static class Currency_conversion_data
    {
        private static final String TAG = Currency_conversion_data.class.getSimpleName();
        private double res;
        String codeFrom;
        String codeTo;
        String url_to_execute;
        double amount;
        LoadingDialog ldialog;
        Context ctx;

        private class get_data_async extends AsyncTask<String, String, String>
        {

            @Override
            protected String doInBackground(String... strings)
            {
                String String2Json = Currency_conversion_data.http_call(url_to_execute);
                return String2Json;
            }
        }

        public Currency_conversion_data( Context ctx )
        {
            this.ctx = ctx;
            ldialog = new LoadingDialog(ctx);
            ldialog.setCanceledOnTouchOutside(false);
        }

        public double convert(String codeFrom, String codeTo, double amount)
        {
            ldialog.show();
            this.codeTo = codeTo;
            this.codeFrom = codeFrom;
            this.amount = amount;
            url_to_execute = "https://api.exchangerate.host/convert?from="
                    + codeFrom + "&to=" + codeTo + "&amount=" + amount;
            get_data_async async_data = new get_data_async();
            JSONObject jsonObject = null;
            try {
                String js = async_data.execute().get();
                try {
                    jsonObject = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    double a = jsonObject.getDouble("result");
                    res = a;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            ldialog.dismiss();
            return res;
        }

        public static String http_call(String api_url)
        {
            String result = null;
            try {
                URL url = new URL(api_url);
                try {
                    HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream InStream = new BufferedInputStream(connection.getInputStream());
                    result = stream_toString(InStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return result;
        }

        private static String stream_toString(InputStream inputStream)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while (true) {
                try {
                    if ((line = reader.readLine()) != null) builder.append(line + '\n');
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return builder.toString();
            }
        }
    }
}
