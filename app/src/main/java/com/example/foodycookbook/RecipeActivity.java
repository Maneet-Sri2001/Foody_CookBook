package com.example.foodycookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RecipeActivity extends AppCompatActivity {

    ImageView showIng, showIns, recipeCat, recipeImg;
    TextView recipeName, recipeArea, recipeIns;
    boolean buttonOn, isButtonOn;
    SimpleArcLoader arcLoader;
    RelativeLayout relRecipe;

    String id;
    HashMap<String, Integer> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        relRecipe = findViewById(R.id.relative_recipe);
        relRecipe.setVisibility(View.INVISIBLE);
        showIng = findViewById(R.id.show_ing);
        showIns = findViewById(R.id.show_ins);
        recipeImg = findViewById(R.id.recipe_image);
        recipeImg.setImageTintList(null);
        recipeCat = findViewById(R.id.recipe_cat);
        recipeName = findViewById(R.id.recipe_name);
        recipeArea = findViewById(R.id.recipe_area);
        recipeIns = findViewById(R.id.recipe_instruction);
        arcLoader = findViewById(R.id.loader);

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


        Intent intent = getIntent();
        id = intent.getStringExtra("mealId");
        //Toast.makeText(RecipeActivity.this, id.toString(), Toast.LENGTH_SHORT).show();
        getCompleteDetail(id);
    }

    private void getCompleteDetail(String id) {

        arcLoader.start();

        String u = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=%s";
        String url = String.format(u, id);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONArray("meals").getJSONObject(0);
                    recipeName.setText(jsonObject.getString("strMeal"));
                    recipeArea.setText(jsonObject.getString("strArea"));
                    Glide.with(recipeImg).load(jsonObject.getString("strMealThumb")).into(recipeImg);
                    recipeCat.setImageResource(categoryList.get(jsonObject.getString("strCategory")));
                    recipeIns.setText(jsonObject.get("strInstructions").toString());

                    for (int i = 0; i < 20; i++) {

                    }

                    arcLoader.stop();
                    relRecipe.setVisibility(View.VISIBLE);
                    arcLoader.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    arcLoader.stop();
                    arcLoader.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                    Toast.makeText(RecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                arcLoader.stop();
                arcLoader.setVisibility(View.INVISIBLE);
                Toast.makeText(RecipeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(RecipeActivity.this);
        requestQueue.add(stringRequest);
    }

    public void closeRecipeUI(View view) {
        finish();
        //startActivity(new Intent(RecipeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void showIngridients(View view) {
        if (!buttonOn) {
            buttonOn = true;
            showIng.setImageResource(R.drawable.ic_remove_circle);
        } else {
            buttonOn = false;
            showIng.setImageResource(R.drawable.ic_add_circle);
        }
    }

    public void showInstruction(View view) {
        if (!isButtonOn) {
            isButtonOn = true;
            showIns.setImageResource(R.drawable.ic_remove_circle);
            findViewById(R.id.recipe_instruction).setVisibility(View.VISIBLE);
        } else {
            isButtonOn = false;
            showIns.setImageResource(R.drawable.ic_add_circle);
            findViewById(R.id.recipe_instruction).setVisibility(View.INVISIBLE);
        }
    }
}