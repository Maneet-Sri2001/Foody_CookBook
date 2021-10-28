package com.example.foodycookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.foodycookbook.Adaptor.CategoryAdaptor;
import com.example.foodycookbook.ModelClass.CategoryModel;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView ranMeal, ranCat;
    TextView ranName, ranArea;
    String mealId;

    RecyclerView categoryRec;
    List<CategoryModel> categoryModels;
    CategoryAdaptor categoryAdaptor;
    CategoryModel category;

    LinearLayout linMain;
    SimpleArcLoader loader;

    HashMap<String, Integer> categoryList;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = new Intent(MainActivity.this, RecipeActivity.class);

        linMain = findViewById(R.id.linaer_main);
        linMain.setVisibility(View.INVISIBLE);
        loader = findViewById(R.id.loader_main);

        ranMeal = findViewById(R.id.ran_meal_image);
        ranMeal.setImageTintList(null);
        ranCat = findViewById(R.id.ran_meal_cat);
        ranName = findViewById(R.id.ran_meal_name);
        ranArea = findViewById(R.id.ran_meal_area);

        categoryRec = findViewById(R.id.category_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        /*gridLayoutManager.setReverseLayout(false);
        gridLayoutManager.setStackFromEnd(false);*/
        categoryRec.setLayoutManager(gridLayoutManager);

        categoryList = new HashMap<String, Integer>();
        categoryList.put("Beef", R.drawable.beef);
        categoryList.put("Breakfast", R.drawable.breakfast);
        categoryList.put("Chicken", R.drawable.turkey);
        categoryList.put("Dessert", R.drawable.sweets);
        categoryList.put("Goat", R.drawable.goat);
        categoryList.put("Lamb", R.drawable.lamb);
        categoryList.put("Miscellaneous", R.drawable.food_basket);
        categoryList.put("Pasta", R.drawable.spaguetti);
        categoryList.put("Pork", R.drawable.pork);
        categoryList.put("Seafood", R.drawable.seafood);
        categoryList.put("Side", R.drawable.hamburger);
        categoryList.put("Starter", R.drawable.starters);
        categoryList.put("Vegan", R.drawable.vegetarian);
        categoryList.put("Vegetarian", R.drawable.diet);

        mealId = fetchRandomMeal();

        categoryModels = new ArrayList<>();
        fetchCategory();
//        categoryRec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//
//        });
    }

    private void fetchCategory() {

        loader.start();

        String url = "https://www.themealdb.com/api/json/v1/1/categories.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("categories");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("strCategory"),
                                id = jsonObject.getString("idCategory"),
                                img = jsonObject.getString("strCategoryThumb"),
                                des = jsonObject.getString("strCategoryDescription");

                        category = new CategoryModel(id, name, img, des);
                        //Toast.makeText(MainActivity.this, id+"\t"+name+"\t"+img+"\t"+des, Toast.LENGTH_LONG).show();
                        categoryModels.add(category);
                        loader.stop();
                        linMain.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.INVISIBLE);
                    }

                    categoryAdaptor = new CategoryAdaptor(getApplicationContext(), categoryModels, categoryList);
                    categoryRec.setAdapter(categoryAdaptor);

                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.stop();
                    linMain.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loader.stop();
                linMain.setVisibility(View.VISIBLE);
                loader.setVisibility(View.INVISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private String fetchRandomMeal() {
        final String[] id = new String[1];
        String url = "https://www.themealdb.com/api/json/v1/1/random.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONArray("meals").getJSONObject(0);
                    i.putExtra("mealId", jsonObject.get("idMeal").toString());
                    //Toast.makeText(MainActivity.this,jsonObject.get("idMeal").toString() , Toast.LENGTH_SHORT).show();
                    ranName.setText(jsonObject.getString("strMeal"));
                    ranArea.setText(jsonObject.getString("strArea"));
                    Glide.with(ranMeal).load(jsonObject.getString("strMealThumb")).into(ranMeal);
                    ranCat.setImageResource(categoryList.get(jsonObject.getString("strCategory")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loader.stop();
                //linMain.setVisibility(View.VISIBLE);
                loader.setVisibility(View.INVISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        return id[0];
    }

    public void OpenRecipe(View view) {
        startActivity(i);
    }

}