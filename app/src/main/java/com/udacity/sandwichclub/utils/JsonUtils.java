package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = "JSON Utils";

    public static Sandwich parseSandwichJson(String json) {
        //Set sandwich to null so that in event of an error, null is returned and the onCreate of DetailsActivity will handle it.
        Sandwich sandwich = null;

        //Attempt to get each variable needed for a Sandwich constructor from the JSON, and create a Sandwich using them
        try {
            JSONObject sandwichJSON = new JSONObject(json);

            JSONObject sandwichName = sandwichJSON.getJSONObject("name");

            String mainName = sandwichName.getString("mainName");

            List<String> alsoKnownAs = new ArrayList<String>();
            JSONArray jsonAlsoKnownAs = sandwichName.getJSONArray("alsoKnownAs");
            if (jsonAlsoKnownAs != null && jsonAlsoKnownAs.length() > 0) {
                int length = jsonAlsoKnownAs.length();
                for (int i = 0; i < length; i++) {
                    alsoKnownAs.add(jsonAlsoKnownAs.getString(i));
                }
            }

            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");

            String description = sandwichJSON.getString("description");

            String image = sandwichJSON.getString("image");

            List<String> ingredients = new ArrayList<String>();
            JSONArray jsonIngredients = sandwichJSON.getJSONArray("ingredients");
            if (jsonIngredients != null && jsonIngredients.length() > 0) {
                int length = jsonIngredients.length();
                for (int i = 0; i < length; i++) {
                    ingredients.add(jsonIngredients.getString(i));
                }
            }

            sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException exception) {
            Log.e(LOG_TAG, exception.getMessage());
        }

        return sandwich;
    }
}