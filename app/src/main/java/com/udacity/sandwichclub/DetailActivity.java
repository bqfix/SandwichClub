package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Description
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        descriptionTextView.setText(sandwich.getDescription());

        //Ingredients
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        for (int ingredientPos = 0; ingredientPos < ingredients.size(); ingredientPos++) {
            ingredientsTextView.append(ingredients.get(ingredientPos));
            //Check if more ingredients, append a comma and a space
            if (ingredientPos + 1 < ingredients.size()) {
                ingredientsTextView.append(", ");
            }
        }

        //Origin (If exists, else hide)
        String origin = sandwich.getPlaceOfOrigin();
        if (!origin.isEmpty()) {
            TextView originTextView = (TextView) findViewById(R.id.origin_tv);
            originTextView.setText(origin);
        } else {
            LinearLayout originLayout = (LinearLayout) findViewById(R.id.origin_layout);
            originLayout.setVisibility(View.GONE);
        }

        //Alternate names (If exists, else hide)
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (!alsoKnownAs.isEmpty()) {
            TextView alsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
            for (int position = 0; position < alsoKnownAs.size(); position++) {
                alsoKnownAsTextView.append(alsoKnownAs.get(position));
                //Check if other names, append a comma and a space
                if (position + 1 < alsoKnownAs.size()) {
                    alsoKnownAsTextView.append(", ");
                }
            }
        } else {
            LinearLayout alsoKnownAsLayout = (LinearLayout) findViewById(R.id.also_known_layout);
            alsoKnownAsLayout.setVisibility(View.GONE);
        }



    }
}
