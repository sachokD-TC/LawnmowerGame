package com.waasche.lawnmower.data;

import com.badlogic.gdx.graphics.Color;
import com.waasche.lawnmower.services.LevelService;

public class LevelTypeMetaData {
    private Color color;
    private int id;
    private String subtitle;
    private String title;
    private int totalLevels;

    public LevelTypeMetaData(int id, String title, String subtitle, int totalLevels, Color color) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.totalLevels = totalLevels;
        this.color = color;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public boolean isCompleted() {
        return getTotalLevels() == getCompletedLevels();
    }

    public int getTotalLevels() {
        return this.totalLevels;
    }

    public int getCompletedLevels() {
        return LevelService.countCompletedLevels(id);
    }

    public Color getColor() {
        return this.color;
    }
}
