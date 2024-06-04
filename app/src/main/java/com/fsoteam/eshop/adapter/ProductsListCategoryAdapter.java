package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.R;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;

public class ProductsListCategoryAdapter extends RecyclerView.Adapter<ProductsListCategoryAdapter.ViewHolder> {

    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryId);
    }

    private OnCategoryClickListener listener;

    private Context ctx;
    private ArrayList<Category> categoryList;

    public ProductsListCategoryAdapter(Context ctx, ArrayList<Category> categoryList) {
        this.ctx = ctx;
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_products_single_category_item, parent, false);
        return new ViewHolder(categoryView);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category item = categoryList.get(position);
        holder.categoryChip.setText(item.getName());

        if (item.isSelected()) {
            holder.categoryChip.setChecked(true);
        } else {
            holder.categoryChip.setChecked(false);
        }
        holder.categoryChip.setOnClickListener(v -> {
            for (Category c : categoryList) {
                c.setSelected(false);
            }
            item.setSelected(true);
            notifyDataSetChanged();

            if (listener != null) {
                listener.onCategoryClick(item.getCategoryId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Chip categoryChip;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryChip = itemView.findViewById(R.id.category_chip);
        }
    }
}