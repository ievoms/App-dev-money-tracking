package com.example.app_dev_money_tracking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewRecord extends AppCompatActivity {

    private ImageView imgViewClose, ImgViewDone;
    private TextView txtHeader, txtOperationRecord, txtCurrencyRecord, txtCategoryChoose, txtChosenCategory;
    private TextView txtAccountChoose, txtChosenAccount;
    private Button btnIncome, btnExpense, btn_open_category_input;
    private EditText editTxtAmount;
    private TextView categoryName;

    private LinearLayout category_text_linear;

    private RecyclerView categoryRecycler;
    private CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        getSupportActionBar().hide();  // To hide default activity bar

        btn_open_category_input = (Button) findViewById(R.id.open_category_input);
        imgViewClose = (ImageView)findViewById(R.id.imageClose);
        ImgViewDone = (ImageView)findViewById(R.id.imageDone);
        txtHeader = (TextView)findViewById(R.id.textHeader);
        btnIncome = (Button)findViewById(R.id.btnRecordTypeIncome);
        btnExpense = (Button)findViewById(R.id.btnRecordTypeExpense);
        editTxtAmount = (EditText)findViewById(R.id.editAmount);
        txtOperationRecord = (TextView)findViewById(R.id.operationSignRecord);
        txtCategoryChoose = (TextView)findViewById(R.id.categoryChooseHeading);
        txtChosenCategory = (TextView)findViewById(R.id.chosenCategory);
        category_text_linear = (LinearLayout)findViewById(R.id.text_layout_category_item);
        categoryName = (TextView)findViewById(R.id.categories_item_name);

        btnExpense.setBackgroundColor(Color.rgb(153, 50, 204));
        editTxtAmount.setMovementMethod(null);


        categoryRecycler = (RecyclerView)findViewById(R.id.CategoriesRecycle);
        adapter = new CategoriesAdapter(Categories.getData(), this);
        categoryRecycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryRecycler.setLayoutManager(manager);
        categoryRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Categories categories, int position) {
                Toast.makeText(getApplicationContext(), "Category " + categories.getCategoryName() + " was chosen", Toast.LENGTH_SHORT).show();
                txtChosenCategory.setText(categories.getCategoryName());
            }
        });


        // Actions for user creating new category
        btn_open_category_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewRecord.this);
                final View new_categ_layout = getLayoutInflater().inflate(R.layout.user_category_input_layout, null);
                builder.setView( new_categ_layout);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText cat_name_input = new_categ_layout.findViewById(R.id.category_input);
                        if(cat_name_input.getText().toString().length() != 0) {
                            Toast.makeText(NewRecord.this, "Your category was created", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(NewRecord.this, "Category name can't be empty. Try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    Toast.makeText(NewRecord.this, "", Toast.LENGTH_SHORT);
                });

                builder.setTitle("New Category");
                builder.setMessage("Add your new category");


                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExpense.setBackgroundColor(Color.rgb(153,50,204));
                btnIncome.setBackgroundColor(Color.rgb(121,60,201));
                txtOperationRecord.setText("-");
                txtCategoryChoose.setText("Category");
                txtChosenCategory.setText("Select Category Below");
//              txtAccountChoose.setText("Account");
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIncome.setBackgroundColor(Color.rgb(153,50,204));
                btnExpense.setBackgroundColor(Color.rgb(121,60,201));
                txtOperationRecord.setText("+");
                txtCategoryChoose.setText("Category");
                txtChosenCategory.setText("Select Category Below");
//                txtAccountChoose.setText("Account");
            }
        });

        editTxtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int txtLength = editTxtAmount.getText().length();
                if(txtLength > 5 && txtLength < 8) {
                    editTxtAmount.setTextSize(60);
                } else if(txtLength >= 8 && txtLength < 11) {
                    editTxtAmount.setTextSize(40);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}