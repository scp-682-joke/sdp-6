import java.util.Scanner;
import context.CalculatorContext;
import strategies.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Calculator ===");
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        System.out.println("\nSelect an operation:");
        System.out.println("1. Add (+)");
        System.out.println("2. Subtract (-)");
        System.out.println("3. Multiply (*)");
        System.out.println("4. Divide (/)");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        // The Client uses switch/if ONLY to select and pass the strategy to the Context
        OperationStrategy selectedStrategy = switch (choice) {
            case 1 -> new AddStrategy();
            case 2 -> new SubtractStrategy();
            case 3 -> new MultiplyStrategy();
            case 4 -> new DivideStrategy();
            default -> {
                System.out.println("Invalid choice! Defaulting to Addition.");
                yield new AddStrategy();
            }
        };

        // Pass the chosen strategy to the Context[cite: 1]
        CalculatorContext calculator = new CalculatorContext(selectedStrategy);

        try {
            double result = calculator.calculate(num1, num2);
            System.out.println("\nResult: " + result);
        } catch (ArithmeticException e) {
            System.out.println("\nError: " + e.getMessage());
        }

        scanner.close();
    }
}