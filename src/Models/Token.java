package Models;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Token {
    public enum TokenType {
        number,
        operator,
        parantheses,
        function,
        symbol
    };

    public enum input {
        Deg,
        Pi,
        Sin,
        Cos,
        Tan,
        Second,
        E,
        Ln,
        TenPow,
        Log,
        Sqrt,
        TwoPow,
        Pow,
        Inv,
        NFac,
        LeftPara,
        Seven,
        Four,
        One,
        Zero,
        RightPara,
        Eight,
        Five,
        Two,
        Answer,
        Nine,
        Six,
        Three,
        Dot,
        Back,
        Divide,
        Multiply,
        Minus,
        Plus,
        Clear,
        ClearEntry,
        PlusMinus,
        Equal
    }
    public static String pi = ""+(char) 0x03c0;
    public static String sqrt = ""+(char) 0x221A;

    private static String[] operators = {"+","-","*","/","^"};
    private static String[] functions = {sqrt,"cbrt","inv","n!","log","ln","sin","cos","tan","asin","acos","atan"};
    private static String[] symbols = 	{"e",pi,"-e","-"+pi};


    public String item;
    public TokenType type;

    public Token(TokenType type, String item) {
        this.type = type;
        this.item = item;
    }

    public Token(Token t) {
        this.type = t.type;
        this.item = t.item;
    }

    public boolean compare(Token t) {
        if(type == t.type && compare(t.item))
            return true;
        else
            return false;
    }

    public boolean compare(String s) {
        if(item.compareTo(s) == 0)
            return true;
        else
            return false;
    }


    public double calcOperator(double n1, double n2) {
        if(type != TokenType.operator) {
            assert(false);
            return Double.NEGATIVE_INFINITY;
        }
        if(compare("+")) {
            return n1+n2;
        }
        else if(compare("-")) {
            return n2-n1;
        }
        else if(compare("*")) {
            return n1*n2;
        }
        else if(compare("/")) {
            return n2/n1;
        }
        else if(compare("^") ) {
            return Math.pow(n2, n1);
        }


        return Double.NEGATIVE_INFINITY;
    }

    private double round15(double x){
        DecimalFormat twoDForm = new DecimalFormat("0.##############E0");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        twoDForm.setDecimalFormatSymbols(dfs);
        String str = twoDForm.format(x);
        return Double.valueOf(str);
    }

    public double calcFunction(double n1, boolean rad) {
        double answer;

        if(type != TokenType.function){
            assert(false);
            return Double.NEGATIVE_INFINITY;
        }

        if(compare(sqrt)) {
            return Math.sqrt(n1);
        }
        else if(compare("cbrt")) {
            return Math.cbrt(n1);
        }
        else if(compare("inv")) {
            return(1/n1);
        }
        else if(compare("n!")) {
            if(n1%1 != 0) {
                return Faculty.gamma(1+n1);
            }
            else {
                return Faculty.faculty(n1);
            }

        }
        else if(compare("log")) {
            return Math.log10(n1);
        }
        else if(compare("ln")) {
            return Math.log(n1);
        }

        else if(compare("sin")) {
            if(rad) {
                return round15( Math.sin(n1)+1)-1;
            }
            else
                return round15( Math.sin(Math.toRadians(n1))+1)-1;

        }
        else if(compare("cos")) {
            if(rad) {
                return round15(Math.cos(n1)+1)-1;
            }
            else
                return round15(Math.cos(Math.toRadians(n1))+1)-1;

        }
        else if(compare("tan")) {
            if(rad) {
                return round15(Math.tan(n1)+1)-1;
            }
            else
                return round15(Math.tan(Math.toRadians(n1))+1)-1;
        }
        else if(compare("asin")) {
            if(rad) {
                return round15(Math.asin(n1)+1)-1;
            }
            else
                return round15(Math.asin(Math.toRadians(n1))+1)-1;
        }
        else if(compare("acos")) {
            if(rad) {
                return round15(Math.acos(n1)+1)-1;
            }
            else
                return round15(Math.acos(Math.toRadians(n1))+1)-1;
        }
        else if(compare("atan")) {
            if(rad) {
                return round15(Math.atan(n1)+1)-1;
            }
            else
                return round15(Math.atan(Math.toRadians(n1))+1)-1;
        }

        return Double.NEGATIVE_INFINITY;


    }

    public double calcSymbol() {
        if(type != TokenType.symbol) {
            assert(false);
            return Double.NEGATIVE_INFINITY;
        }
        if(compare("e"))
            return Math.E;
        else if(compare("-e"))
            return -Math.E;
        else if(compare(pi))
            return Math.PI;
        else if(compare("-"+pi))
            return -Math.PI;

        return Double.NEGATIVE_INFINITY;
    }

    public static String doubleToString(double x) {

        if(Double.isNaN(x)  || Double.isInfinite(x))
            return String.format("%.0f",x);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en","UK"));
        symbols.setDecimalSeparator('.');
        //symbols.setExponentSeparator("*10^");
        symbols.setGroupingSeparator(' ');
        String pattern = "#########.#########";

        if(Math.abs(x) < 0.000001 && x != 0) {			//  10^-6
            pattern = "#.#########E0";
        }
        else if (Math.abs(x) > 1000000000) {   // 10^9
            pattern = "#.#########E0";
        }

        DecimalFormat df = new DecimalFormat(pattern,symbols);
        df.setGroupingUsed(false);
        //df.setMaximumFractionDigits(6);

        String number = df.format(x);

        return number;


    }

    public static boolean isNumber(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            else if(s.charAt(i) == '.') {
                continue;
            }
            if(Character.digit(s.charAt(i),10) < 0 ) return false;
        }
        return true;
    }

    public static boolean isFunction(String s) {
        List<String> fn = Arrays.asList(functions);
        return fn.contains(s);

    }

    public static boolean isOperator(String s) {
        List<String> op = Arrays.asList(operators);
        return op.contains(s);
    }

    public static boolean isParantheses(String s) {
        if(s.compareTo("(")== 0 || s.compareTo(")") == 0)
            return true;
        else
            return false;
    }
    public static boolean isSymbol(String s) {
        List<String> sym = Arrays.asList(symbols);
        return sym.contains(s);
    }

    public boolean isLeftParantheses() {
        if(type == TokenType.parantheses && compare("("))
            return true;
        else
            return false;
    }

    public boolean isRightParantheses() {
        if(type == TokenType.parantheses && compare(")"))
            return true;
        else
            return false;
    }

    public boolean isZero() {
        if(type == TokenType.number && compare("0"))
            return true;
        else
            return false;
    }

    public int precedence() {
        if(type != TokenType.operator)
            return -1;
        if(	compare("+") || compare("-"))
            return 1;
        else if(compare("*") || compare("/") )
            return 2;
        else if(compare("^") || compare("sqrt"))
            return 3;
        else if(compare("(") || compare(")") ) {
            return 0;
        }
        return -1;
    }

    public boolean isLeftAss() {
        if(compare("-")  || compare("/")) {
            return true;
        }
        else
            return false;
    }

    public double getDouble() {
        if(type == TokenType.number) {
            return Double.parseDouble(item);
        }
        else {
            return Double.NEGATIVE_INFINITY;
        }
    }

}
