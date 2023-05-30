package pl.wsei.mobilne.reminderapp;

public class CheckMat {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Argument 'b' cannot be zero.");
        }
        return (double) a / b;
    }
}
