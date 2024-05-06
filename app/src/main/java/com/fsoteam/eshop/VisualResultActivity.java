package com.fsoteam.eshop;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.adapter.VisualSearchResultAdapter;
import com.fsoteam.eshop.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VisualResultActivity extends AppCompatActivity {

    private TextView predictNameTV;
    private ArrayList<Product> resultProduct;

    private VisualSearchResultAdapter visualSearchResultAdapter;
    private RecyclerView predictedRecView;

    private String PredictName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_result);

        PredictName = getIntent().getStringExtra("PredictName");

        predictedRecView = findViewById(R.id.predictedRecView);
        predictNameTV = findViewById(R.id.predictNameTV);
        predictNameTV.setText("Search for " + PredictName);

        resultProduct = new ArrayList<>();

        setResultData();

        predictedRecView.setLayoutManager(new GridLayoutManager(this, 2));
        predictedRecView.setHasFixedSize(true);
        visualSearchResultAdapter = new VisualSearchResultAdapter(resultProduct, this);
        predictedRecView.setAdapter(visualSearchResultAdapter);
    }

    private String getJsonData(Context context, String fileName) {
        StringBuilder jsonString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                jsonString.append(mLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString.toString();
    }

    private void setResultData() {
        String jsonFileString = getJsonData(this, "NewProducts.json");
        Gson gson = new Gson();

        Type listCoverType = new TypeToken<List<Product>>() {}.getType();

        List<Product> coverD = gson.fromJson(jsonFileString, (Type) listCoverType);

        for (Product person : coverD) {
            if (person.getProductCategory().equals(PredictName) || person.getProductName().equals(PredictName)) {
                resultProduct.add(person);
            }
        }
    }
}