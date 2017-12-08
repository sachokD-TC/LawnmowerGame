package com.waasche.lawnmower.data;


public class LevelTypes {

    private int id;
    private FieldPoint start;
    private int complexity;
    private int minLength;
    private int maxLength;

    public LevelTypes(int id, FieldPoint start, int complexity, int minLength, int maxLength){
        this.id = id;
        this.start = start;
        this.complexity = complexity;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public LevelTypes(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FieldPoint getStart() {
        return start;
    }

    public void setStart(FieldPoint start) {
        this.start = start;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

}
