package context;
import strategies.OperationStrategy;

public class CalculatorContext {
    private OperationStrategy strategy;

    public CalculatorContext(OperationStrategy initialStrategy) {
        this.strategy = initialStrategy;
    }
    public void setStrategy(OperationStrategy strategy) {
        this.strategy = strategy;
    }
    public double calculate(double a, double b) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy is not set.");
        }
        return strategy.execute(a, b);
    }
}