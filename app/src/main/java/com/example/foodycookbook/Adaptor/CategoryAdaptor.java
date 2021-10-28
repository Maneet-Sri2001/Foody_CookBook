package com.example.foodycookbook.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodycookbook.MainActivity;
import com.example.foodycookbook.ModelClass.CategoryModel;
import com.example.foodycookbook.R;
import com.example.foodycookbook.RecipeActivity;
import com.example.foodycookbook.categoryMeal;

import java.util.HashMap;
import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {

    private Context context;
    private List<CategoryModel> categoryModelList;
    private HashMap<String, Integer> categoryType;

    public CategoryAdaptor(Context context, List<CategoryModel> categoryModelList, HashMap<String, Integer> categoryType) {
        this.context = context;
        this.categoryModelList = categoryModelList;
        this.categoryType = categoryType;
    }

    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, null, true);
        return new CategoryAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelList.get(position);
        holder.catName.setText(categoryModel.getName());
        holder.catType.setImageResource(categoryType.get(categoryModel.getName()));
        Glide.with(context).load(categoryModel.getThumb()).into(holder.catImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, Integer.toString(position), Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context.getApplicationContext(), categoryMeal.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImage, catType;
        TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImage = itemView.findViewById(R.id.category_image);
            catImage.setImageTintList(null);
            catType = itemView.findViewById(R.id.category_type);
            //catType.setImageTintList(null);
            catName = itemView.findViewById(R.id.category_name);
        }
    }
}
