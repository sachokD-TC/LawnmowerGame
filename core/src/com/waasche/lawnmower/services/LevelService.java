package com.waasche.lawnmower.services;


import com.badlogic.gdx.graphics.Color;
import com.waasche.lawnmower.data.*;
import com.waasche.lawnmower.resources.Assets;

import java.util.*;

public class LevelService {

    public static Color[] colors = {Color.LIGHT_GRAY, Color.BLUE, Color.CORAL, Color.BROWN, Color.CYAN, Color.FIREBRICK, Color.FOREST, Color.GOLD, Color.MAGENTA, Color.CHARTREUSE};

    public static boolean isWall(Level level, FieldPoint point) {
        List<FieldPoint> walls = level.getWalls();
        for (FieldPoint wall : walls) {
            if (wall.equals(point)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllowToMove(Level level, FieldPoint point) {
        List<FieldPoint> walls = level.getWalls();
        for (FieldPoint wall : walls) {
            if (wall.equals(point)) {
                return false;
            }
        }
        if (point.getX() < level.getSizeX() && point.getX() >= 0 && point.getY() < level.getSizeY() && point.getY() >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isFail(Level level, FieldLine line){
        if(level.getPassedLinesList().contains(line)){
            return true;
        }
        return false;
    }

    public static boolean isOver(Level level, int complexityIndex){
        return (level.getPassedLinesList().size()+1) == level.getLevelTypes().get(complexityIndex).getMinLength() && level.getPassedPoints().size() == level.getSquareSize();
    }

    public static int countCompletedLevels(int boardSize){
        List<Level> levels = Assets.levelsList.get(boardSize).getLevels();
        int count =0;
        for (Level level : levels) {
            if(GameProgress.isCompleted("" + boardSize, level.getName())){
                count++;
            }
        }
        return count;
    }

    public static List<LevelTypeMetaData> listLevelPacks() {
        Random random = new Random();
        List<LevelTypeMetaData> levelTypes = new ArrayList<LevelTypeMetaData>();
        Set<Integer> levelTypesSet = Assets.levelsList.keySet();
        for (Integer levelType : levelTypesSet) {
            levelTypes.add(new LevelTypeMetaData(levelType, "Level" + levelType, "easyHint",  Assets.levelsList.get(levelType).getLevels().size(), colors[levelType]));
        }
        Collections.sort(levelTypes);
        return levelTypes;
    }
}
