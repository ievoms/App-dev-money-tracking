package com.example.app_dev_money_tracking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateCategory extends AppCompatActivity {

    // Basic variables
    private EditText et_category_name;
    private TextView tv_category_choose_icon_text;
    private ImageView iv_chosen_category_icon, checkingImgView;
    private Button btn_save_category;
//    private ListView lv_categoriesName;

    // Related to gridView and alert Box
    int[] pictures = {R.drawable.charity, R.drawable.clotheshanger, R.drawable.commission, R.drawable.debt, R.drawable.buss, R.drawable.dumbbell,
            R.drawable.family, R.drawable.fastfoodd, R.drawable.giftboxwithbow, R.drawable.governance, R.drawable.graduationcapp,
            R.drawable.healthcare, R.drawable.house, R.drawable.housedecorationn, R.drawable.insurance, R.drawable.investment,
            R.drawable.key, R.drawable.movies, R.drawable.pawprint, R.drawable.piggybankk, R.drawable.salary, R.drawable.subscription,
            R.drawable.utilities, R.drawable.car, R.drawable.shoppingbag};  // drawable references
    GridView gridView;
    int default_category_pic;

    // Saving to DB
    Database db;


    public void init() {
        // Initializing variables
        et_category_name = (EditText) findViewById(R.id.edittext_category_name_input);
        tv_category_choose_icon_text = (TextView) findViewById(R.id.textview_choose_category_text);
        iv_chosen_category_icon = (ImageView) findViewById(R.id.imageview_choose_category_chosen_image);
        btn_save_category = (Button) findViewById(R.id.button_save_category);
//        lv_categoriesName = (ListView) findViewById(R.id.lv_categories_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        init();

        tv_category_choose_icon_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCategory.this);
                final View chooseIcon = getLayoutInflater().inflate(R.layout.iconsdashboard, null);
                builder.setView(chooseIcon); // setting icons view in the alert box
                gridView = chooseIcon.findViewById(R.id.simpleGridView);
                builder.setTitle("New category icon");
                builder.setMessage("Choose icon for your category");
                AlertDialog dialog = builder.create();
                dialog.setContentView(R.layout.iconsdashboard);
                dialog.setCancelable(true);
                MyAdapter myAdapter = new MyAdapter(CreateCategory.this, pictures);
                gridView.setAdapter(myAdapter);

                // Click listener for gridview item
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        default_category_pic = pictures[i];
//                        System.out.println(default_category_pic);
                        Drawable drawable = getResources().getDrawable(default_category_pic);
                        iv_chosen_category_icon.setImageDrawable(drawable);
                        tv_category_choose_icon_text.setText("Click here to change chosen icon");
                        dialog.dismiss();

                        if(et_category_name != null || iv_chosen_category_icon.getDrawable() != null) {
                            btn_save_category.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Database db = new Database(CreateCategory.this);
                                    db.addCategory(et_category_name.getText().toString(), default_category_pic);

//
//                                    arrayList.clear();
//                                    displayData(arrayList);
//                                    CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, R.layout.list_row, arrayList);
//                                    lv_categoriesName.setAdapter(categoryAdapter);
//                                    categoryAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}