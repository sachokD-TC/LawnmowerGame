package com.waasche.lawnmower.data;


public enum Moves  {
    UP(new FieldPoint(0, -1)),
    DOWN(new FieldPoint(0, 1)),
    RIGHT(new FieldPoint(1, 0)),
    LEFT(new FieldPoint(-1, 0)),
    NONE(new FieldPoint(0,0));

    private FieldPoint fieldPoint;

    Moves(FieldPoint fieldPoint){
        this.fieldPoint = fieldPoint;
    }

    public FieldPoint getFieldPoint(){
        return fieldPoint;
    }
}
