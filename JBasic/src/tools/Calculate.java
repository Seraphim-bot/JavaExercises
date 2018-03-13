package tools;

/**
 * tools.Calculate - convert infix to postfix
 *
 * @author Sergey Iryupin
 * @version 0.3.3 dated Mar 13, 2018
 */

import java.util.LinkedList;
import java.util.List;

import static tools.IConstants.*;

import model.Variables;

public class Calculate {
    private static List<String> list;
    private static LinkedList<Character> stackOper;
    private Variables variables;
    
    public Calculate(Variables variables) {
		this.variables = variables;
	}

    public float calculatePostfix(List<String> list) {
        LinkedList<Float> stack = new LinkedList<>();
        float second;
        //System.out.println(list);
        for (String str : list) {
            switch (str) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "-":
                    second = stack.pop();
                    stack.push(stack.pop() - second);
                    break;
                case "/":
                    second = stack.pop();
                    stack.push(stack.pop() / second);
                    break;
                default:
					try {
                       stack.push(Float.parseFloat(str));
                    } catch (NumberFormatException ex) {
						stack.push(variables.get(str));
					}
            }
        }
        return stack.pop();
    }

    public static List<String> convertInfixToPostfix(String input) {
        list = new LinkedList<>();
        stackOper = new LinkedList<>();
        String part = "";
        for (int i = 0; i < input.length(); i++)
            switch (input.charAt(i)) {
                case '+':
                case '-':
                    if (!part.isEmpty()) {
                        list.add(part);
                        part = "";
                    }
                    getOperator(input.charAt(i), 1);
                    break;
                case '*':
                case '/':
                    if (!part.isEmpty()) {
                        list.add(part);
                        part = "";
                    }
                    getOperator(input.charAt(i), 2);
                    break;
                case '(':
                    if (!part.isEmpty()) {
                        list.add(part);
                        part = "";
                    }
                    stackOper.push(input.charAt(i));
                    break;
                case ')':
                    if (!part.isEmpty()) {
                        list.add(part);
                        part = "";
                    }
                    getRightParenthesis();
                    break;
                case ' ':
                    break;
                default:
                    part += input.charAt(i);
            }
        if (!part.isEmpty())
            list.add(part);
        while (stackOper.size() > 0)
            list.add(stackOper.pop().toString());
        return list;
    }

    private static void getOperator(char operator, int precedence) {
        while (stackOper.size() > 0) {
            Character opTop = stackOper.pop();
            if (opTop == '(') {
                stackOper.push(opTop);
                break;
            } else {
                int prec = (opTop == '+' || opTop == '-') ? 1 : 2;
                if (prec < precedence) {
                    stackOper.push(opTop);
                    break;
                } else
                    list.add(opTop.toString());
            }
        }
        stackOper.push(operator);
    }

    private static void getRightParenthesis() {
        while (stackOper.size() > 0) {
            Character chx = stackOper.pop();
            if (chx == '(')
                break;
            else {
                list.add(chx.toString());
            }
        }
    }

    public static boolean isComparison(String expression) {
        return expression.matches("(.*)(" + SIGN_EQU + "|" +
			SIGN_LSS + "|" + SIGN_GRT + ")(.*)");
    }

    public boolean calculateBoolean(String expression) {
		int posEQU = expression.indexOf(SIGN_EQU);
		if (posEQU > -1) {
			float left = 
				calculatePostfix(
					Calculate.convertInfixToPostfix(
						expression.substring(0, posEQU)));
			float right = 
				calculatePostfix(
					Calculate.convertInfixToPostfix(
						expression.substring(posEQU + 1)));
			return (Float.compare(left, right) == 0);		
		}
		return false;
	}
}
