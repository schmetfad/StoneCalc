package Models;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import Models.Token;
import Models.Token.TokenType;


import com.sun.java_cup.internal.runtime.Symbol;
import com.sun.management.OperatingSystemMXBean;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;

public class Calculator {
    private Queue<Token> infixQueue;
    private Token lastToken;
    private Token answerToken;
    private int paraCount = 0;
    private String strFormat;
    private boolean gotInput = false;
    private boolean rad = false;

    public Calculator() {
        infixQueue = new LinkedList<Token>();
        lastToken = new Token(TokenType.number, "0");
        answerToken = new Token(lastToken);
        strFormat = lastToken.item;
    }

    public void setRad(boolean rad) {
        this.rad = rad;
    }

    public String getStrFormat() {
        strFormat = new String();
        for (Token t : infixQueue) {
            strFormat += t.item;
        }

        strFormat += lastToken.item;

        return strFormat;
    }

    public void answer() {
        if(!gotInput) {
            lastToken = answerToken;
            return;
        }
        switch(lastToken.type){
            case number:
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                lastToken = new Token(answerToken);
                break;
            case operator:
                infixQueue.add(lastToken);
                lastToken = new Token(answerToken);
                break;
            case parantheses:
                if(lastToken.isLeftParantheses()) {
                    infixQueue.add(lastToken);
                    lastToken = new Token(answerToken);
                }
                else {
                    infixQueue.add(lastToken);
                    infixQueue.add(new Token(TokenType.operator,"*"));
                    lastToken = new Token(answerToken);

                }
                break;
            case function:
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.parantheses,"("));
                lastToken = new Token(answerToken);
                break;
            case symbol:
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                lastToken = new Token(answerToken);
                break;
            default:
                assert(false);
        }


        gotInput = true;
    }

    public void buildToken(String s) {

        // take care of only . number
        if (lastToken.type == TokenType.number && lastToken.compare(".")
                && !Token.isNumber(s)) {
            lastToken.item += "0";
        }
        // Did we get a number?
        if(Token.isNumber(s)) {
            buildTokenNumber(s);
        }
        // did we get an operator?
        else if(Token.isOperator(s))
        {
            buildTokenOperator(s);
        }
        // did we get an parantheses?
        else if(Token.isParantheses(s)) {
            buildTokenParantheses(s);
        }
        // did we get a function
        else if(Token.isFunction(s)) {
            buildTokenFunction(s);
        }
        else if(Token.isSymbol(s)) {
            buildTokenSymbol(s);
        }
        gotInput = true;
    }

    private void buildTokenNumber(String s) {
        if (lastToken.type == TokenType.number) { // 55
            if ( !gotInput) { 								// om endast "0" || answer
                lastToken.item = s;									// byt ut "0":an
            } else {
                if(s.compareTo(".") == 0 && lastToken.item.contains(".")) {
                    return;
                }
                else
                    lastToken.item += s;
            }
        } else if (lastToken.type == TokenType.operator) { 			// +5
            infixQueue.add(lastToken);
            lastToken = new Token(TokenType.number, s);
        } else if (lastToken.type == TokenType.parantheses) { 		// (5 || )5
            if (lastToken.compare(")")) { 							// )5
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator, "*")); // make )*5
                lastToken = new Token(TokenType.number, s);
            } else {												// (5
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.number, s);
            }
        } else if (lastToken.type == TokenType.function) {			// h�r ska vi inte vara sqrt5
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.parantheses, "("));
            paraCount++;
            lastToken = new Token(TokenType.function, s);
        }
        else if(lastToken.type == TokenType.symbol) {				// e5 -> e*5
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.operator,"*"));
            lastToken = new Token(TokenType.number,s);
        }

    }

    private void buildTokenOperator(String s) {
        if (lastToken.type == TokenType.number) { 					// 5+
            infixQueue.add(lastToken);
            lastToken = new Token(TokenType.operator, s);
        } else if (lastToken.type == TokenType.operator) { 			// *+
            lastToken.item = s; 									// byt operator
        } else if (lastToken.type == TokenType.parantheses) { 		// (+ || )+
            if (!lastToken.isLeftParantheses()) { 					// NOT (+
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.operator, s);
            }
        } else if (lastToken.type == TokenType.function) {			// sqrt+ h�r ska vi inte vara
            assert (false);
            return;
        }
        else if(lastToken.type == TokenType.symbol) {				// e+
            infixQueue.add(lastToken);
            lastToken = new Token(TokenType.operator,s);
        }
    }

    private void buildTokenParantheses(String s) {
        if (s.compareTo(")") == 0 && paraCount <= 0)			// ) utan matchande (
            return;
        if (lastToken.type == TokenType.number) { 				// 5( || 5)
            //if (lastToken.isZero() && infixQueue.isEmpty()
            if(!gotInput
                    && s.compareTo("(") == 0) { 				// byt endast nollan
                lastToken.item = s;
                lastToken.type = TokenType.parantheses;
                paraCount++; 									// new (
            } else if (s.compareTo("(") == 0) { 				// 5(
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator, "*")); // 5*(
                lastToken = new Token(TokenType.parantheses, s);
                paraCount++; 										// new (
            } else {
                infixQueue.add(lastToken);							// ) all is fine
                lastToken = new Token(TokenType.parantheses, s);
                paraCount--; 										// new )
            }
        } else if (lastToken.type == TokenType.operator) { 			// +( || +)
            if (s.compareTo(")") != 0) { 							// NOT +)
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.parantheses, s);
                paraCount++; 										// new (
            }
        } else if (lastToken.type == TokenType.parantheses) { 		// (( || )) ||
            // () || )(
            if (lastToken.isLeftParantheses() && s.compareTo(")") == 0) {	 // () g�r inget
                return;
            } else if (lastToken.isRightParantheses() && s.compareTo("(") == 0) { // )(
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator, "*")); 			// l�gg till )*(
                lastToken = new Token(TokenType.parantheses, s);
                paraCount++; 													// new (
            } else {															// normalfallet
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.parantheses, s);

                if (s.compareTo("(") == 0)
                    paraCount++; 										// new (
                else
                    paraCount--; 										// new )
            }
        } else if (lastToken.type == TokenType.function) {				// sqrt ( h�r ska vi inte vara egentligen
            if (lastToken.isLeftParantheses()) {
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.parantheses, s);
            }
        }
        else if(lastToken.type == TokenType.symbol) {
            if(s.compareTo("(") == 0)  {						// e( -> e*(
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                lastToken = new Token(TokenType.parantheses,s);
            }
            else {												// e)
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.parantheses,s);
            }
        }
    }

    private void buildTokenFunction(String s) {
        if (lastToken.type == TokenType.number) {					// 5 sqrt
            //if (infixQueue.isEmpty() && lastToken.compare("0")) {	// nollan? byt ut
            if(!gotInput) {
                infixQueue.add(new Token(TokenType.function, s));
                lastToken = new Token(TokenType.parantheses, "(");
                paraCount++;

            } else {												// 5 sqrt l�gg till *
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator, "*"));
                infixQueue.add(new Token(TokenType.function, s));
                lastToken = new Token(TokenType.parantheses, "(");
                paraCount++;
            }
        } else if (lastToken.type == TokenType.operator) {			// +sqrt finemang
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.function, s));
            lastToken = new Token(TokenType.parantheses, "(");
            paraCount++;
        } else if (lastToken.type == TokenType.parantheses) {		// (sqrt || )sqrt
            if (lastToken.isLeftParantheses()) {					// (sqrt finemang
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.function, s));
                lastToken = new Token(TokenType.parantheses, "(");
                paraCount++;
            } else { 												// )sqrt -> )*sqrt(
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator, "*"));
                infixQueue.add(new Token(TokenType.function, s));
                lastToken = new Token(TokenType.parantheses, "(");
                paraCount++;
            }
        } else if (lastToken.type == TokenType.function) {			// sqrt sqrt fel!
            assert (false);
            return;
        }
        else if(lastToken.type == TokenType.symbol) {				// esqrt -> e*sqrt
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.operator,"*"));
            lastToken = new Token(TokenType.function,s);
        }
    }

    private void buildTokenSymbol(String s) {
        if(lastToken.type == TokenType.number) {					// 5e -> 5*e
            //if (infixQueue.isEmpty() && lastToken.compare("0")) {
            if(!gotInput) {
                lastToken.item = s;
                lastToken.type = TokenType.symbol;
            }
            else {
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                lastToken = new Token(TokenType.symbol,s);
            }
        }
        else if(lastToken.type == TokenType.operator) {				// +e
            infixQueue.add(lastToken);
            lastToken = new Token(TokenType.symbol,s);
        }
        else if(lastToken.type == TokenType.parantheses) {			// (e || )e
            if(lastToken.isLeftParantheses()) {
                infixQueue.add(lastToken);
                lastToken = new Token(TokenType.symbol,s);
            }
            else {
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                lastToken = new Token(TokenType.symbol,s);
            }
        }
        else if(lastToken.type == TokenType.function) {				// ska inte h�nda
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.parantheses,"("));
            lastToken = new Token(TokenType.symbol,s);
        }
        else if(lastToken.type == TokenType.symbol) {				// ee -> e*e
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.operator,"*"));
            lastToken = new Token(TokenType.symbol,s);
        }

    }

    public void add10Pow() {
        if(lastToken.type == TokenType.number) {
            //if (infixQueue.isEmpty() && lastToken.compare("0")) {
            if(!gotInput) {
                infixQueue.add(new Token(TokenType.number,"10"));
                lastToken = new Token(TokenType.operator,"^");
            }
            else {
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                infixQueue.add(new Token(TokenType.number,"10"));
                lastToken = new Token(TokenType.operator,"^");
            }
        }
        else if(lastToken.type == TokenType.operator) {
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.number,"10"));
            lastToken = new Token(TokenType.operator,"^");
        }
        else if(lastToken.type == TokenType.parantheses) {
            if(lastToken.isLeftParantheses()) {
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.number,"10"));
                lastToken = new Token(TokenType.operator,"^");
            }
            else {
                infixQueue.add(lastToken);
                infixQueue.add(new Token(TokenType.operator,"*"));
                infixQueue.add(new Token(TokenType.number,"10"));
                lastToken = new Token(TokenType.operator,"^");
            }
        }
        else if(lastToken.type == TokenType.function) {
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.parantheses,"("));
            infixQueue.add(new Token(TokenType.number,"10"));
            lastToken = new Token(TokenType.operator,"^");
        }
        else if(lastToken.type == TokenType.symbol) {
            infixQueue.add(lastToken);
            infixQueue.add(new Token(TokenType.operator,"*"));
            infixQueue.add(new Token(TokenType.number,"10"));
            lastToken = new Token(TokenType.operator,"^");
        }
    }

    public void clearEntry() {

        if (lastToken.type == TokenType.parantheses) {		// om sista �r (||) r�kna om paranteser
            if (lastToken.isLeftParantheses())
                paraCount--;
            else
                paraCount++;
        }
        if (!infixQueue.isEmpty()) {							// ta bort sista item i k�n
            Queue<Token> temp = new LinkedList<Token>(infixQueue);
            infixQueue.clear();
            int size = temp.size();
            for (int i = 0; i < size - 1; i++) {
                infixQueue.add(temp.poll());
            }
            lastToken = temp.poll();
        } else {													// finns endast last entry, "0"
            lastToken = new Token(TokenType.number, "0");
            paraCount = 0;
        }
    }

    public void stepBack() {
        if (infixQueue.isEmpty()) {
            if(lastToken.compare(answerToken) && !gotInput) { 					// endast answser
                lastToken = new Token(TokenType.number,"0");
                return;
            }
            if (lastToken.type == TokenType.number) {
                if (lastToken.item.length() == 1) {				// only 1 digit
                    lastToken.item = "0";
                } else {
                    lastToken.item = lastToken.item.substring(0,	// minska siffra ex. 33 -> 3
                            lastToken.item.length() - 1);
                }
            } else {
                if (lastToken.type == TokenType.parantheses) {		//om para r�kna om para
                    if (lastToken.isLeftParantheses())
                        paraCount--;
                    else
                        paraCount++;
                }
                lastToken.type = TokenType.number;
                lastToken.item = "0";
            }

        }
        else { 												// queue is not empty
            if (lastToken.type == TokenType.number) {
                if (lastToken.item.length() > 1) {
                    lastToken.item = lastToken.item.substring(0,	// 44 -> 4
                            lastToken.item.length() - 1);
                }
                else {
                    Queue<Token> temp = new LinkedList<Token>(infixQueue); // byt ut sista token
                    infixQueue.clear();
                    int size = temp.size();
                    for (int i = 0; i < size - 1; i++) {
                        infixQueue.add(temp.poll());
                    }
                    lastToken = temp.poll();
                }
            }
            else {
                if (lastToken.type == TokenType.parantheses) {			// sista �r (||)
                    if (lastToken.isLeftParantheses()) {
                        paraCount--;
                    } else {
                        paraCount++;
                    }
                }
                Queue<Token> temp = new LinkedList<Token>(infixQueue);	// plocka bort sista
                infixQueue.clear();
                int size = temp.size();
                for (int i = 0; i < size - 1; i++) {
                    infixQueue.add(temp.poll());
                }
                lastToken = temp.poll();
            }

        }

    }

    public void clear() {
        gotInput = false;
        lastToken = new Token(TokenType.number, "0");
        infixQueue.clear();
        paraCount = 0;
    }

    public void changeSign() {
        if (lastToken.type == TokenType.number && !lastToken.isZero()) {
            if (lastToken.item.charAt(0) == '-')
                lastToken.item = lastToken.item.substring(1);
            else
                lastToken.item = "-" + lastToken.item;
        }
        else if(lastToken.type == TokenType.symbol){
            if (lastToken.item.charAt(0) == '-')
                lastToken.item = lastToken.item.substring(1);
            else
                lastToken.item = "-" + lastToken.item;
        }
    }

    public String evaluate() {
        gotInput = false;
        String ret = new String();
        try {
            ret = internalEvaluate();
        } catch (Exception e) {
            ret = "ERROR";
            infixQueue.clear();
            lastToken = new Token(TokenType.number, "0");
            answerToken = new Token(lastToken);
        }

        return ret;
    }

    public boolean readyToEvaluate() {
        if (lastToken.type == TokenType.operator)			// 2+
            return false;
        else if(lastToken.type == TokenType.parantheses && lastToken.compare("("))	//3*(
            return false;
        else if(lastToken.type == TokenType.function) 							//2+sqrt ska ej ske
            return false;

        return true;
    }

    private String internalEvaluate() {

        infixQueue.add(lastToken);

        // even out parentheses
        while (paraCount > 0) {
            infixQueue.add(new Token(TokenType.parantheses, ")"));
            paraCount--;
        }

        // bygg upp en str�ng av uttrycket f�r att visa p� sk�rmen
        String expr = new String();
        for (Token t : infixQueue) {
            expr += t.item;
        }

        Stack<Double> stack = new Stack<Double>();
        Queue<Token> RPN = shuntingYard(infixQueue);



        Token token;
        while (!RPN.isEmpty()) {
            token = RPN.poll();
            if (token.type == TokenType.number) {
                stack.push(token.getDouble());
            }
            else if(token.type == TokenType.symbol) {
                stack.push(token.calcSymbol());
            }
            else if (token.type == TokenType.operator) {
                double op1 = stack.pop();
                double op2 = stack.pop();
                stack.push(token.calcOperator(op1, op2)); // calculate(op1,item,op2));
            }
            else if(token.type == TokenType.function) {
                double op1 = stack.pop();
                stack.push(token.calcFunction(op1,rad));
            }
        }
        double answer = stack.pop();
        //lastToken = new Token(TokenType.number, String.valueOf(answer));
        lastToken = new Token(TokenType.number, Token.doubleToString(answer));
        answerToken = new Token(lastToken);

        paraCount = 0;
        return expr += "=" + lastToken.item;
        //return lastToken.item;

    }

    private Queue<Token> shuntingYard(Queue<Token> inFixQueue) {

        Stack<Token> operators = new Stack<Token>();
        Queue<Token> queue = new LinkedList<Token>();

        while (!inFixQueue.isEmpty()) {

            Token token = inFixQueue.poll();
            if (token.type == TokenType.number || token.type == TokenType.symbol) {
                queue.add(token);
            }
            else if (token.type == TokenType.function) {
                operators.add(token);
            } else if (token.type == TokenType.operator) { // its an operator
                while (!operators.isEmpty()) {
                    if (token.isLeftAss()
                            && token.precedence() == operators.peek()
                            .precedence()
                            || token.precedence() <= operators.peek()
                            .precedence()) {
                        queue.add(operators.pop());
                    } else {
                        break;
                    }
                }
                operators.push(token);
            } else if (token.type == TokenType.parantheses) {
                if (token.isLeftParantheses()) {
                    operators.push(token);
                } else {
                    while (!operators.isEmpty()) {
                        Token temp = operators.pop();
                        if (temp.isLeftParantheses())
                            break;
                        else
                            queue.add(temp);
                    }
                    if (!operators.isEmpty() && operators.peek().type == TokenType.function) {
                        queue.add(operators.pop());
                    }

                }
            }

        }

        while (!operators.isEmpty()) {
            queue.add(operators.pop());
        }

        return queue;
    }

}
