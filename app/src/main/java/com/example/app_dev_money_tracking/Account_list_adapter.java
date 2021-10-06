package com.example.app_dev_money_tracking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class Account_list_adapter extends RecyclerView.Adapter<Account_list_adapter.MyViewHolder>
{
    private ArrayList<Account> account_list;

    public Account_list_adapter(ArrayList<Account> accounts)
    {
        this.account_list = accounts;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private EditText balance_text;
        private Button btn_adjust_balance;
        private TextView Txt_acc_name;

        public MyViewHolder(final View view)
        {
            super(view);
            balance_text = view.findViewById(R.id.Txt_e_balance);
            btn_adjust_balance = view.findViewById(R.id.Btn_adjust_balance);
            Txt_acc_name = view.findViewById(R.id.Txt_acc_list_name);
        }
    }

    @NonNull
    @Override
    public Account_list_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_acc_adp, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Account_list_adapter.MyViewHolder holder, int position)
    {
        String account_name = account_list.get(position).getAccountName();
        double balance = account_list.get(position).getBalance();
        holder.balance_text.setText(balance + "");
        holder.Txt_acc_name.setText(account_name);
    }

    @Override
    public int getItemCount()
    {
        return account_list.size();
    }
}
