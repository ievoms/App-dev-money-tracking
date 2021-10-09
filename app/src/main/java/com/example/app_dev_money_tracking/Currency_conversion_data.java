package com.example.app_dev_money_tracking;

import android.os.AsyncTask;

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
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

class Currency_conversion_data
{
    private static final String TAG = Currency_conversion_data.class.getSimpleName();
    private double res;
    String codeFrom;
    String codeTo;
    String url_to_execute;
    double amount;

//    double rate;
//    Currency_conversion_data singleton;

//    public Currency_conversion_data instanciate(String codeFrom, String codeTo)
//    {
//        if (singleton == null) {
//            return singleton = new Currency_conversion_data(codeFrom, codeTo);
//        } else {
//            return singleton;
//        }
//    }

    private class get_data_async extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            Currency_conversion_data curr = new Currency_conversion_data();
            String String2Json = curr.http_call(url_to_execute);
            return String2Json;
        }
    }

    public Currency_conversion_data()
    { }

    public double convert(String codeFrom, String codeTo, double amount)
    {
        this.codeTo = codeTo;
        this.codeFrom = codeFrom;
        this.amount = amount;
        url_to_execute = "https://api.exchangerate.host/convert?from="
                + codeFrom + "&to=" + codeTo + "&amaount=1";
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
        return res;
    }

    public String http_call(String api_url)
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

    private String stream_toString(InputStream inputStream)
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
