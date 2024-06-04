package com.fsoteam.eshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ContextThemeWrapper;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.MainActivity;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.ThemeModel;

import java.util.List;

// ThemeAdapter.java
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
    private List<ThemeModel> themeList;
    private Context context;

    public ThemeAdapter(List<ThemeModel> themeList, Context context) {
        this.themeList = themeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_theme_card_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThemeModel theme = themeList.get(holder.getAdapterPosition());
        holder.themeName.setText(theme.getThemeName());

        // Create a ContextThemeWrapper and inflate the layout using this context
        ContextThemeWrapper themeContext = new ContextThemeWrapper(context, theme.getThemeStyle());
        View themedView = LayoutInflater.from(themeContext).inflate(R.layout.single_theme_card, holder.themeCard, false);

        // Remove all views from the themeCard and add the themedView
        holder.themeCard.removeAllViews();
        holder.themeCard.addView(themedView);
        RelativeLayout cardBorder = themedView.findViewById(R.id.single_theme_card_theme_card_border);

        if(theme.isSelected()) {

            cardBorder.setBackground(context.getDrawable(R.drawable.theme_main_card_selected));
            holder.checkMark.setVisibility(View.VISIBLE);
        }

        holder.themeCard.setOnClickListener(v -> {
            // Save the selected theme in SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("ThemePref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("themeStyle", theme.getThemeStyle());
            editor.apply();

            // Change the theme of the application
            if (context instanceof Activity) {
                ((Activity) context).setTheme(theme.getThemeStyle());
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView themeName;
        LinearLayout themeCard;
        ImageView checkMark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            themeName = itemView.findViewById(R.id.single_theme_card_themeName);
            themeCard = itemView.findViewById(R.id.single_theme_card_theme_card);
            checkMark = itemView.findViewById(R.id.single_theme_card_check_mark);
        }
    }
}
