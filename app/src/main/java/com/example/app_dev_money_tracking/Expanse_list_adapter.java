package com.example.app_dev_money_tracking;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

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
        if(record_list != null)
        return record_list.size();
        else return 0;
    }
}
