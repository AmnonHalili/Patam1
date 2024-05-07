package test;

public class Mul extends BinaryExpression {
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        return this.left.calculate() * this.right.calculate();
    }
}