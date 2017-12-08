package com.waasche.lawnmower.data;


public class FieldPoint {

    private int x;
    private int y;

    public FieldPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public FieldPoint(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldPoint)) return false;

        FieldPoint that = (FieldPoint) o;

        if (getX() != that.getX()) return false;
        return getY() == that.getY();

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }
}
