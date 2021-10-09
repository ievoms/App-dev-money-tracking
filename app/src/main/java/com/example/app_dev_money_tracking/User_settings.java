package com.example.app_dev_money_tracking;

import android.content.Context;
import android.content.SharedPreferences;

class User_settings
{
    final static String cur_setting = "base_currency";
    Context ctx;
    String uname;
    static User_settings singleton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public static User_settings instanciate(String uname, Context ctx)
    {
        if (singleton == null) {
            return singleton = new User_settings(uname, ctx);
        } else {
            return singleton;
        }
    }

    public User_settings(String uname, Context ctx)
    {
        this.uname = uname;
        this.ctx = ctx;
        pref = ctx.getSharedPreferences(uname, Context.MODE_PRIVATE);
    }

    public void set_currency(String currency)
    {
        pref = ctx.getSharedPreferences(uname, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(cur_setting, currency);
    }
    public String get_currency()
    {
        String result = "";
        pref = ctx.getSharedPreferences(uname, Context.MODE_PRIVATE);
        pref.getString(cur_setting,result);
        return result;
    }

}
