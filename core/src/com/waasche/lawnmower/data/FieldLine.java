package com.waasche.lawnmower.data;


public class FieldLine {

    private FieldPoint start;
    private FieldPoint end;

    public FieldLine(FieldPoint start, FieldPoint end){
        this.start = start;
        this.end = end;
    }

    public FieldPoint getStart() {
        return start;
    }

    public void setStart(FieldPoint start) {
        this.start = start;
    }

    public FieldPoint getEnd() {
        return end;
    }

    public void setEnd(FieldPoint end) {
        this.end = end;
    }

    public void addPoint(FieldPoint point){
        if(this.end == null){
            this.setEnd(point);
        } else {
            setStart(point);
            setEnd(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldLine)) return false;

        FieldLine fieldLine = (FieldLine) o;

        if (!getStart().equals(fieldLine.getStart()) && !getEnd().equals(fieldLine.getStart())) return false;
        boolean eq = !(getEnd() != null ? !getEnd().equals(fieldLine.getEnd()) : fieldLine.getEnd() != null);
        if (eq) return true;
        else
        return getStart().equals(fieldLine.getEnd());
    }

    @Override
    public int hashCode() {
        int result = getStart().hashCode();
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        return result;
    }
}
