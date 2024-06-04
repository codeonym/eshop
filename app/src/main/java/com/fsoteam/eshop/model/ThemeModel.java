package com.fsoteam.eshop.model;

public class ThemeModel {
    private String themeName;
    private int themeStyle;
    private boolean isSelected = false;

    public ThemeModel(String themeName, int themeStyle) {
        this.themeName = themeName;
        this.themeStyle = themeStyle;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getThemeStyle() {
        return themeStyle;
    }

    public void setThemeStyle(int themeStyle) {
        this.themeStyle = themeStyle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
