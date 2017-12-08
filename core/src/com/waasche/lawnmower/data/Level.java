package com.waasche.lawnmower.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.List;


public class Level {

    private String name;
    private int sizeX;
    private int sizeY;
    private List<FieldPoint> walls;
    private List<LevelTypes> levelTypes;
    private String levelFileName;
    private FieldPoint size;
    private LevelsList levelsList;
    private List<FieldLine> passedLines = new ArrayList<FieldLine>();
    private List<FieldPoint> passedPoints = new ArrayList<FieldPoint>();

    public Level(String name, FieldPoint size, List<FieldPoint> walls, List<LevelTypes> levelTypes) {
        this.name = name;
        this.size = size;
        this.walls = walls;
        this.levelTypes = levelTypes;
    }

    public Level() {
    }

    public Level(String levelFileName) {
        this.levelFileName = levelFileName;
        getLevels(levelFileName);
    }

    private void getLevels(String levelFileName) {
        FileHandle file = Gdx.files.internal(levelFileName);
        Json json = new Json();
        levelsList = json.fromJson(LevelsList.class, file.reader());
    }

    public void addPassedLine(FieldLine passedLine) {
        this.passedLines.add(passedLine);
    }

    public List<FieldLine> getPassedLinesList() {
        return passedLines;
    }

    public List<FieldPoint> getPassedPoints() {
        return passedPoints;
    }

    public void addPassedPoints(FieldPoint point) {
        if(!passedPoints.contains(point)){
         this.passedPoints.add(point);
        }
    }

    public void addLineToPassedLines(FieldLine line){
        passedLines.add(line);
    }

    public void cleanPassedPoints() {
        this.passedLines.clear();
    }

    public LevelsList getLevelsList() {
        return this.levelsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSizeX() {
        return size.getX();
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return this.size.getY();
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public List<FieldPoint> getWalls() {
        return walls;
    }

    public void setWalls(List<FieldPoint> walls) {
        this.walls = walls;
    }

    public List<LevelTypes> getLevelTypes() {
        return levelTypes;
    }

    public void setLevelTypes(List<LevelTypes> levelTypes) {
        this.levelTypes = levelTypes;
    }

    public String getLevelFileName() {
        return levelFileName;
    }

    public void setLevelFileName(String levelFileName) {
        this.levelFileName = levelFileName;
    }

    public FieldPoint getSize() {
        return size;
    }

    public void setSize(FieldPoint size) {
        this.size = size;
    }

    public int getSquareSize(){
        return getSizeX()*getSizeY() - getWalls().size();
    }

    public void setPassedLines(List<FieldLine> passedLines) {
        this.passedLines = passedLines;
    }

    public void setPassedPoints(List<FieldPoint> passedPoints) {
        this.passedPoints = passedPoints;
    }

}
