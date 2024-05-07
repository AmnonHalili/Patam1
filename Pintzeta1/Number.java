package test;

public class Number implements Expression {
    protected final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double calculate() {
        return this.value;
    }

}