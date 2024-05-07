package test;

public class Div extends BinaryExpression {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        return this.left.calculate() / this.right.calculate();
    }
}