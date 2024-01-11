package dz080124;

public class TestTo {
    public int first;
    public double second;

    public void setFirst(int first) {
        this.first = first;
    }
    public void setSecond(double second) {
        this.second = second;
    }
    public void setDisturbance(){

    }
    @Override
    public String toString(){
        return first+" "+second;
    }
}
