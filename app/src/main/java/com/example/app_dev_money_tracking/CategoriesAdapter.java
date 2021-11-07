package com.example.app_dev_money_tracking;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_dev_money_tracking.Categories;
import com.example.app_dev_money_tracking.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {

    private ArrayList<Category> categoriesList;
    private Context context;
    private OnItemClickListener listener;

    public CategoriesAdapter(ArrayList<Category> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false);
        return new CategoriesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.setData(category);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoriesHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
//            categoryName = (TextView) itemView.findViewById(R.id.categories_item_name);
//            categoryImage = (ImageView) itemView.findViewById(R.id.categories_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(categoriesList.get(position), position);
                    }
                }
            });
        }

        public void setData(Category categories) {
            this.categoryName.setText(categories.getName());
            this.categoryImage.setBackgroundResource(categories.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Category categories, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}