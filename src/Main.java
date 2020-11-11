import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        String test1ExpressionInvalid = "AaBbCcc";
        String test2ExpressionValid = "AaBb";
        String test3ExpressionInvalid = "AAaBbB";
        String test4ExpressionInvalid = "aAbB";
        String test5ExpressionValid = "ACABbBbaca";
        String test6ExpressionInvalid = "ABab";
        String test7ExpressionInvalid = "BCAcaB";
        String test8ExpressionInvalid = "AabB";
        String test9ExpressionInvalid = "AaBbDd";
        String test10ExpressionInvalid = "";

        String expression = test1ExpressionInvalid;
        boolean isValid = isValidExpression(expression);

        System.out.println(expression);
        System.out.print(isValid);
    }

    public static boolean isValidExpression(String expression) {
        // unspecified if those are the only valid clauses, but including just in case, easy to remove check
        HashSet<Character> validClauses = new HashSet<>(Arrays.asList('a', 'A', 'b', 'B', 'c', 'C'));
        Stack<Character> stillOpenClauses = new Stack<Character>();

        // a valid expression must contain at least one phrase
        if (expression.length() == 0) {
            return false;
        }

        for (int i = 0; i < expression.length(); i++) {
            char currentClause = expression.charAt(i);
            if (!validClauses.contains(currentClause)) {
                return false;
            }

            // no open clauses, add only if the next one is an opening clause
            if (stillOpenClauses.empty()) {

                // lowercase = close clause, a closing clause before an opening clause is an invalid expression
                if (Character.isLowerCase(currentClause)) {
                    return false;
                }
                stillOpenClauses.push(currentClause);
            } else {
                char lastOpenClause = stillOpenClauses.peek();
                boolean isPhraseClosed = isProperlyClosed(lastOpenClause, currentClause);
                if (isPhraseClosed) {
                    stillOpenClauses.pop();
                } else {
                    stillOpenClauses.push(currentClause);
                }
            }
        }
        return stillOpenClauses.empty();
    }

    public static boolean isProperlyClosed(char firstClause, char secondClause) {
        // a properly closed phrase has an opening clause(uppercase) first and a closing clause(lowercase) second
        return Character.isUpperCase(firstClause) && Character.isLowerCase(secondClause) &&
                Character.toUpperCase(firstClause) == Character.toUpperCase(secondClause);
    }
}
