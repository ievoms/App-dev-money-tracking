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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

class Expanse_list_adapter extends RecyclerView.Adapter<Expanse_list_adapter.MyViewHolder> {
    private List<RecordsModel> record_list;
    private Context context;

    public Expanse_list_adapter(Context context, List<RecordsModel> records) {
        this.record_list = records;
        this.context = context;
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
        User_settings user_settings = User_settings.instanciate("user1", context);
        Database db = new Database(context);
        UserModel user = db.getUserByEmail(user_settings.getUserEmail());
        String currency = record_list.get(position).getCurrency();

        Currency c = Currency.getInstance(user.getCurrency());
        String currencySymbol = c.getSymbol();

        int categoryId = record_list.get(position).getCategoryId();
        String date = record_list.get(position).getDate();
        double amount = record_list.get(position).getAmount();
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (!user.getCurrency().equals(currency.toString())) {
            Currency_conversion_data curr = new Currency_conversion_data(context);
            amount = Double.parseDouble(formatter.format(curr.convert(currency.toString(), user.getCurrency(), amount)));
        }
        RecordTypeKey recordType = record_list.get(position).getRecordType();

        holder.Date.setText(date);
        holder.Amount.setText(amount + currencySymbol);
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

    static class Currency_conversion_data {
        private static final String TAG = Currency_conversion_data.class.getSimpleName();
        private double res;
        String codeFrom;
        String codeTo;
        String url_to_execute;
        double amount;
        LoadingDialog ldialog;
        Context ctx;

        private class get_data_async extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... strings) {
                String String2Json = Currency_conversion_data.http_call(url_to_execute);
                return String2Json;
            }
        }

        public Currency_conversion_data(Context ctx) {
            this.ctx = ctx;
            ldialog = new LoadingDialog(ctx);
            ldialog.setCanceledOnTouchOutside(false);
        }

        public double convert(String codeFrom, String codeTo, double amount) {
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

        public static String http_call(String api_url) {
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

        private static String stream_toString(InputStream inputStream) {
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
