package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.utils.CustomUtils;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Category> categoryList;

    public CategoryAdapter(Context ctx, ArrayList<Category> categoryList) {
        this.ctx = ctx;
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single, parent, false);
        return new ViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category item = categoryList.get(position);
        holder.categoryName_CateSingle.setText(item.getName());

        if (ctx instanceof FragmentActivity) {
            CustomUtils.setProductsFragmentFilter((FragmentActivity) ctx, holder.categoryCardTv, item.getCategoryId(), false, false);
        }

        Glide.with(ctx)
                .load(item.getImage())
                .into(holder.categoryImage_CateSingle);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage_CateSingle;
        TextView categoryName_CateSingle;
        View categoryCardTv;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryImage_CateSingle = itemView.findViewById(R.id.categoryImage_CateSingle);
            categoryName_CateSingle = itemView.findViewById(R.id.categoryName_CateSingle);
            categoryCardTv = itemView.findViewById(R.id.category_single_card);
        }
    }
}