import java.util.*; // scanner: used to read input from the keyboard
import java.util.Stack; // Stack: used to hold operators temporarily during conversion

public class InfixToPostfix {
    
    // Method to get precedence of operators
    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    
    // Method to check if character is an operator
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }
    
    // Method to check if character is an operand (letter or digit)
    private static boolean isOperand(char ch) {
        return Character.isLetterOrDigit(ch);
    }
    
    // Method to check if operator is left associative
    private static boolean isLeftAssociative(char operator) {
        return operator != '^'; // Exponentiation is right associative
    }
    
    // Method to convert infix to postfix
    public static String convertToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        
        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            
            // If character is an operand, add to output
            if (isOperand(ch)) {
                // Handle multi-digit numbers
                StringBuilder operand = new StringBuilder();
                while (i < infix.length() && isOperand(infix.charAt(i))) {
                    operand.append(infix.charAt(i));
                    i++;
                }
                postfix.append(operand).append(" ");
                i--; // Adjust index
            }
            // If character is '(', push to stack
            else if (ch == '(') {
                stack.push(ch);
            }
            // If character is ')', pop until '(' is found
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Remove '('
                }
            }
            // If character is an operator
            else if (isOperator(ch)) {
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       (getPrecedence(stack.peek()) > getPrecedence(ch) ||
                        (getPrecedence(stack.peek()) == getPrecedence(ch) && 
                         isLeftAssociative(ch)))) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(ch);
            }
        }
        
        // Pop remaining operators from stack
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }
        
        return postfix.toString().trim();
    }
    
    // Simplified version for single character operands
    public static String convertSimple(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        
        for (char ch : infix.toCharArray()) {
            // If operand, add to output
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            }
            // If '(', push to stack
            else if (ch == '(') {
                stack.push(ch);
            }
            // If ')', pop until '('
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Remove '('
            }
            // If operator
            else if (isOperator(ch)) {
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       getPrecedence(stack.peek()) >= getPrecedence(ch)) {
                    postfix.append(stack.pop());
                }
                stack.push(ch);
            }
        }
        
        // Pop remaining operators
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        
        return postfix.toString();
    }
    
    // Method to evaluate postfix expression (optional)
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        
        for (String token : tokens) {
            if (token.matches("\\d+")) { // Number
                stack.push(Integer.parseInt(token));
            } else if (token.length() == 1 && isOperator(token.charAt(0))) {
                int b = stack.pop();
                int a = stack.pop();
                char op = token.charAt(0);
                
                switch (op) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                    case '^': stack.push((int)Math.pow(a, b)); break;
                }
            }
        }
        
        return stack.pop();
    }
    
    // Main method with test cases
    public static void main(String[] args) {
        // Test expressions
        String[] expressions = {
            "A+B*C",
            "(A+B)*C",
            "A+B*C+D",
            "A*B+C/D",
            "A*(B+C)/D",
            "A+B*(C-D)/E",
            "A^B^C",
            "10+20*3",
            "(A+B)*(C-D)",
            "A+B-C*D/E"
        };
        
        System.out.println("Infix to Postfix Conversion");
        System.out.println("=".repeat(50));
        
        for (String expr : expressions) {
            String postfix = convertToPostfix(expr);
            System.out.printf("%-20s -> %s%n", expr, postfix);
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Simple version (single characters only):");
        System.out.println("=".repeat(50));
        
        for (String expr : expressions) {
            // Remove spaces for simple version
            String cleanExpr = expr.replace(" ", "");
            String postfix = convertSimple(cleanExpr);
            System.out.printf("%-20s -> %s%n", cleanExpr, postfix);
        }
        
        // Test with numeric expressions
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Numeric expression evaluation:");
        System.out.println("=".repeat(50));
        
        String numericExpr = "10+20*3";
        String postfix = convertToPostfix(numericExpr);
        int result = evaluatePostfix(postfix);
        System.out.printf("%s -> %s = %d%n", numericExpr, postfix, result);
        
        // Interactive version
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Interactive Mode:");
        System.out.println("=".repeat(50));
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter infix expression (or 'quit' to exit): ");
            String input = scanner.nextLine();
            
            while (!input.equalsIgnoreCase("quit")) {
                try {
                    String postfixResult = convertToPostfix(input);
                    System.out.println("Postfix: " + postfixResult);
                    
                    // If expression contains numbers, evaluate it
                    if (postfixResult.matches(".*\\d.*")) {
                        int value = evaluatePostfix(postfixResult);
                        System.out.println("Result: " + value);
                    }
                } catch (Exception e) {
                    System.out.println("Error: Invalid expression");
                }
                
                System.out.print("\nEnter infix expression (or 'quit' to exit): ");
                input = scanner.nextLine();
            }
        }
    }
}