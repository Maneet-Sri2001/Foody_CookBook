package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodycookbook.Adaptor.CategoryAdaptor;
import com.example.foodycookbook.ModelClass.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class categoryMeal extends AppCompatActivity {

    RecyclerView catMealRec;
    List<CategoryModel> catMealList;
    CategoryAdaptor categoryAdaptor;
    CategoryModel categoryModel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_meal);

        catMealRec = findViewById(R.id.category_meal_list);
        catMealList = new ArrayList<>();

    }
}