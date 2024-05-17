package com.fsoteam.eshop.utils;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.fsoteam.eshop.R;
import com.fsoteam.eshop.fragment.ProductsFragment;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.ProductImage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomUtils {

    public static final String PRODUCTS_BY_NEWEST_FIRST = "products_newest_first";
    public static final String PRODUCTS_BY_CATEGORY_ID = "products_by_category_id";
    public static final String PRODUCTS_BY_BEST_SELLING = "products_by_best_selling";

    public static void setProductsFragmentFilter(FragmentActivity activity, View allProductsTv, String categoryId, boolean newestFirst, boolean bestSelling){
        allProductsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsFragment productsFragment = new ProductsFragment();

                Bundle args = new Bundle();
                args.putString(PRODUCTS_BY_NEWEST_FIRST, newestFirst ? "true" : "false");
                args.putString(PRODUCTS_BY_CATEGORY_ID, categoryId);
                args.putString(PRODUCTS_BY_BEST_SELLING, bestSelling ? "true" : "false");
                productsFragment.setArguments(args);

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_fragment, productsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public static void populateDB(FragmentActivity activity) {
        ArrayList<Category> categories = new ArrayList<>();

// Create a new Category object for each category
        Category accessories = new Category();
        accessories.setCategoryId("accessories");
        accessories.setName("Accessories");
        accessories.setCategoryDescription("A variety of accessories for all your needs.");
        categories.add(accessories);

        Category bags = new Category();
        bags.setCategoryId("bags");
        bags.setName("Bags");
        bags.setCategoryDescription("Stylish and durable bags for every occasion.");
        categories.add(bags);
/*

        Category beauty = new Category();
        beauty.setCategoryId("beauty");
        beauty.setName("Beauty");
        beauty.setCategoryDescription("High quality beauty products for your needs.");
        categories.add(beauty);
*/

        Category house = new Category();
        house.setCategoryId("house");
        house.setName("House");
        house.setCategoryDescription("Products for your home and living.");
        categories.add(house);

        Category jewelry = new Category();
        jewelry.setCategoryId("jewelry");
        jewelry.setName("Jewelry");
        jewelry.setCategoryDescription("Elegant jewelry for all occasions.");
        categories.add(jewelry);

        Category kids = new Category();
        kids.setCategoryId("kids");
        kids.setName("Kids");
        kids.setCategoryDescription("Products for your children's needs.");
        categories.add(kids);

        Category men = new Category();
        men.setCategoryId("men");
        men.setName("Men");
        men.setCategoryDescription("Products for men's fashion and needs.");
        categories.add(men);

        /*
        Category women = new Category();
        women.setCategoryId("women");
        women.setName("Women");
        women.setCategoryDescription("Products for women's fashion and needs.");
        categories.add(women);
        */
        Category shoes = new Category();
        shoes.setCategoryId("shoes");
        shoes.setName("Shoes");
        shoes.setCategoryDescription("Stylish and comfortable shoes for all.");
        categories.add(shoes);

        Category electronics = new Category();
        electronics.setCategoryId("electronics");
        electronics.setName("Electronics");
        electronics.setCategoryDescription("High quality electronic products and accessories.");
        categories.add(electronics);

        Category otherProducts = new Category();
        otherProducts.setCategoryId("other");
        otherProducts.setName("Other");
        otherProducts.setCategoryDescription("A variety of other products.");
        categories.add(otherProducts);

        ArrayList<Product> products = new ArrayList<>();
        Resources res = activity.getResources();
        ArrayList<Product> accessoriesProducts = readProductsFromCsv(res, R.raw.accessories, accessories);
        ArrayList<Product> bagsProducts = readProductsFromCsv(res, R.raw.bags, bags);
        //ArrayList<Product> beautyProducts = readProductsFromCsv(res, R.raw.beauty, beauty);
        ArrayList<Product> houseProducts = readProductsFromCsv(res, R.raw.house, house);
        ArrayList<Product> jewelryProducts = readProductsFromCsv(res, R.raw.jewelry, jewelry);
        ArrayList<Product> kidsProducts = readProductsFromCsv(res, R.raw.kids, kids);
        ArrayList<Product> menProducts = readProductsFromCsv(res, R.raw.men,men);
        //ArrayList<Product> womenProducts = readProductsFromCsv(res, R.raw.women,women);
        ArrayList<Product> shoesProducts = readProductsFromCsv(res, R.raw.shoes,shoes);
        products.addAll(accessoriesProducts);
        products.addAll(bagsProducts);
        //products.addAll(beautyProducts);
        products.addAll(houseProducts);
        products.addAll(jewelryProducts);
        products.addAll(kidsProducts);
        products.addAll(menProducts);
        //products.addAll(womenProducts);
        products.addAll(shoesProducts);
        Log.d("CustomUtils", "populateDB: " + products.size() + " products added to the database.");

        // Get a DatabaseReference to the root of your Firebase Realtime Database
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // Save categories to the database
        for (Category category : categories) {
            dbRef.child(DbCollections.CATEGORIES).child(category.getCategoryId()).setValue(category);
        }

        // Save products to the database
        for (Product product : products) {
            dbRef.child(DbCollections.PRODUCTS).child(product.getProductId()).setValue(product);
        }

        Log.d("CustomUtils", "populateDB: " +products.get(1).toString() + " lab3ir z*mel");

    }
    private static ArrayList<Product> readProductsFromCsv(Resources res, int resourceId, Category category) {
        ArrayList<Product> products = new ArrayList<>();
        try (InputStream inputStream = res.openRawResource(resourceId);
             Reader reader = new InputStreamReader(inputStream)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            int count = 0;
            Random rand = new Random();
            for (CSVRecord record : records) {
                if (count >= 50) break;
                if(record.get("image_url").isEmpty() || record.get("variation_0_thumbnail").isEmpty() || record.get("variation_0_image").isEmpty() || record.get("variation_1_image").isEmpty() || record.get("brand").isEmpty()) continue;

                Product product = new Product();
                product.setProductId(record.get("id"));
                product.setProductName(record.get("name"));
                product.setProductDes(record.get("subcategory") + ": " + record.get("name") +" Model: " + record.get("model") + " Brand: " + record.get("brand"));
                product.setProductBrand(record.get("brand"));
                product.setProductModel(record.get("model"));
                product.setProductCategory(category);
                product.setProductNote(record.get("subcategory"));
                product.setProductSubCategory(record.get("subcategory"));
                float price = Float.parseFloat(record.get("current_price")) * 8.66f;
                String formattedPrice = String.format("%.2f", price);
                product.setProductPrice(Float.parseFloat(formattedPrice));
                price = Float.parseFloat(record.get("raw_price")) * 8.66f;
                formattedPrice = String.format("%.2f", price);
                product.setProductPriceRaw(Float.parseFloat(formattedPrice));
                float r = rand.nextFloat() + 4;
                product.setProductRating(r);
                product.setProductDisCount(record.get("discount"));
                product.setProductHave(false);
                List<ProductImage> productImages = new ArrayList<>();
                productImages.add((new ProductImage(record.get("variation_0_image"))));
                productImages.add((new ProductImage(record.get("variation_1_image"))));
                productImages.add((new ProductImage(record.get("image_url"))));
                product.setProductImage(record.get("image_url"));
                product.setProductImages(productImages);
                product.setProductThumbnail(record.get("variation_0_thumbnail"));
                int b = rand.nextInt(1000) + 2000;
                product.setProductQuantity(b);
                product.setLikesCount(Integer.parseInt(record.get("likes_count")));
                b = rand.nextInt(801) + 100;
                product.setProductSales(b);
                b = rand.nextInt(31) + 1;
                product.setProductMaxPurchasePerUser(b);
                product.setProductCurrency("DH");
                product.setProductAddDate(System.currentTimeMillis());
                long c = rand.nextInt(1702228229) + (1714151429 - 1702228229);
                product.setProductAddDate(c);
                products.add(product);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}