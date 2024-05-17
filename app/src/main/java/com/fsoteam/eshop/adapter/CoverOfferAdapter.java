package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.OfferDetailsActivity;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.ProductDetailsActivity;
import java.util.ArrayList;

public class CoverOfferAdapter extends RecyclerView.Adapter<CoverOfferAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<Offer> coverOfferList;

    public CoverOfferAdapter(Context ctx, ArrayList<Offer> coverOfferList) {
        this.ctx = ctx;
        this.coverOfferList = coverOfferList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_single, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Offer coverOffer = coverOfferList.get(position);

        holder.offerNoteCover.setText(coverOffer.getOfferTitle());
        Glide.with(ctx)
                .load(coverOffer.getOfferImage())
                .into(holder.offerImage_coverPage);
        holder.offerCheck_coverPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    goDetailsPage(position);
                }
            }
        });

        holder.offerCheck_coverPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    goDetailsPage(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return coverOfferList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView offerImage_coverPage;
        TextView offerNoteCover;
        Button offerCheck_coverPage;

        public ViewHolder(View itemView) {
            super(itemView);
            offerImage_coverPage = itemView.findViewById(R.id.offerImage_coverPage);
            offerNoteCover = itemView.findViewById(R.id.offerNoteCover);
            offerCheck_coverPage = itemView.findViewById(R.id.offerCheck_coverPage);
        }
    }

    private void goDetailsPage(int position) {
        Intent intent = new Intent(ctx, OfferDetailsActivity.class);
        intent.putExtra("OfferId", coverOfferList.get(position).getOfferId());
        intent.putExtra("OfferFrom", "Cover");
        ctx.startActivity(intent);
    }
}
