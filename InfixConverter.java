import java.util.Scanner;
import java.util.Stack;

public class InfixConverter {
    
    // Method to get precedence of operators
    static int getPrecedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
    
    // Method to check if character is an operator
    static boolean isOperator(char ch) {
        return (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^');
    }
    
    // Method to check if character is an operand
    static boolean isOperand(char ch) {
        return Character.isLetterOrDigit(ch);
    }
    
    // Convert infix to postfix
    static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        java.util.Stack<Character> stack = new java.util.Stack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            // If operand, add to output
            if (isOperand(c)) {
                result.append(c);
            }
            // If '(', push to stack
            else if (c == '(') {
                stack.push(c);
            }
            // If ')', pop until '('
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); // Remove '('
            }
            // If operator
            else if (isOperator(c)) {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        // Pop remaining operators
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        
        return result.toString();
    }
    
    // Convert infix to prefix
    static String infixToPrefix(String infix) {
        // Step 1: Reverse the infix expression
        StringBuilder reversed = new StringBuilder(infix).reverse();
        
        // Step 2: Swap parentheses
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (c == '(') {
                reversed.setCharAt(i, ')');
            } else if (c == ')') {
                reversed.setCharAt(i, '(');
            }
        }
        
        // Step 3: Convert to postfix (modified)
        StringBuilder prefix = new StringBuilder();
        java.util.Stack<Character> stack = new java.util.Stack<>();
        
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            
            if (isOperand(c)) {
                prefix.append(c);
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    prefix.append(stack.pop());
                }
                stack.pop();
            }
            else if (isOperator(c)) {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) > getPrecedence(c)) {
                    prefix.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            prefix.append(stack.pop());
        }
        
        // Step 4: Reverse the result
        return prefix.reverse().toString();
    }
    
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        System.out.println("Infix Expression Converter");
        System.out.println("Converts Infix to Postfix and Prefix");
        System.out.println();
        
        System.out.print("Enter an infix expression: ");
        String infix = scanner.nextLine();
        
        // Remove spaces if any
        infix = infix.replaceAll("\\s+", "");
        
        System.out.println();
        System.out.println("Results:");
        System.out.println("Infix: " + infix);
        System.out.println("Postfix: " + infixToPostfix(infix));
        System.out.println("Prefix: " + infixToPrefix(infix));
        
        // Optional: Ask if user wants to test more expressions
        System.out.println();
        System.out.print("Do you want to test more expressions? (yes/no): ");
        String response = scanner.nextLine();
        
        if (response.equalsIgnoreCase("yes")) {
            System.out.println();
            System.out.println("Test Cases:");
            
            String[] testCases = {
                "A+B*C",
                "(A+B)*C",
                "A*B+C/D",
                "A+B-C*D/E",
                "A*(B+C)/D"
            };
            
            for (String test : testCases) {
                System.out.println();
                System.out.println("Infix: " + test);
                System.out.println("Postfix: " + infixToPostfix(test));
                System.out.println("Prefix: " + infixToPrefix(test));
            }
        }
        
        scanner.close();
    }
}