package Controller;

import Models.Calculator;
import Models.Token;
import Models.Token.input;
import View.MainView;



/**
 * Created by stefa on 2017-03-02.
 */
public class CalculatorController {


    private MainView mv;
    private Calculator calc;
    private boolean rad = false;
    private boolean sec = false;

    public CalculatorController(MainView mv) {
        this.mv = mv;
        calc = new Calculator();
    }



    public void clicked(input sym) {
        switch (sym) {
            case Deg:
                if(rad) {
                    mv.setBtnDeg("deg");
                    rad =!rad;
                    calc.setRad(rad);
                }
                else {
                    mv.setBtnDeg("rad ");
                    rad=!rad;
                    calc.setRad(rad);
                }
                break;
            case Pi:
                calc.buildToken(Token.pi);
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Sin:
                if(!sec) {
                    calc.buildToken("sin");
                }
                else {
                    calc.buildToken("asin");
                }
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Cos:
                if(!sec) {
                    calc.buildToken("cos");
                }
                else {
                    calc.buildToken("acos");
                }
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Tan:
                if(!sec) {
                    calc.buildToken("tan");
                }
                else {
                    calc.buildToken("atan");
                }
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Second:
                if(!sec) {
                    mv.setBtn2nd("1st");
                    mv.setBtnSin("asin");
                    mv.setBtnCos("acos");
                    mv.setBtnTan("atan");
                }
                else {
                    mv.setBtn2nd("2nd");
                    mv.setBtnSin("sin");
                    mv.setBtnCos("cos");
                    mv.setBtnTan("tan");
                }
                sec = !sec;
                break;
            case E:
                calc.buildToken("e");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Ln:
                calc.buildToken("ln");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case TenPow:
                calc.add10Pow();
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Log:
                calc.buildToken("log");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Sqrt:
                calc.buildToken(Token.sqrt);
                mv.setlAnswer(calc.getStrFormat());
                break;
            case TwoPow:
                calc.buildToken("^");
                calc.buildToken("2");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Pow:
                calc.buildToken("^");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Inv:
                calc.buildToken("inv");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case NFac:
                calc.buildToken("n!");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case LeftPara:
                calc.buildToken("(");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Seven:
                calc.buildToken("7");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Four:
                calc.buildToken("4");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case One:
                calc.buildToken("1");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Zero:
                calc.buildToken("0");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case RightPara:
                calc.buildToken(")");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Eight:
                calc.buildToken("8");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Five:
                calc.buildToken("5");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Answer:
                calc.answer();
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Nine:
                calc.buildToken("9");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Six:
                calc.buildToken("6");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Three:
                calc.buildToken("3");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Dot:
                calc.buildToken(".");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Two:
                calc.buildToken("2");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Back:
                calc.stepBack();
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Divide:
                calc.buildToken("/");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Multiply:
                calc.buildToken("*");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Minus:
                calc.buildToken("-");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Plus:
                calc.buildToken("+");
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Clear:
                calc.clear();
                mv.setlAnswer(calc.getStrFormat());
                break;
            case ClearEntry:
                calc.clearEntry();
                mv.setlAnswer(calc.getStrFormat());
                break;
            case PlusMinus:
                calc.changeSign();
                mv.setlAnswer(calc.getStrFormat());
                break;
            case Equal:
                if(calc.readyToEvaluate()) {
                    String s = calc.evaluate();
                    String[] strings = s.split("=");
                    if(strings.length ==2) {
                        mv.setlPrevAnswer(strings[0]+" =");
                        mv.setlAnswer(strings[1]);
                    }
                    else {
                        mv.setlAnswer(s);
                    }
                }


        }
    }

    public void clickedDeg() {
        out("deg");
    }

    public void clickedPi() {
        out("deg");
    }
    public void clickedSin() {
        out("deg");
    }
    public void clickedCos() {
        out("deg");
    }
    public void clickedTan() {
        out("deg");
    }public void clicked2nd() {
        out("deg");
    }public void clickedE() {
        out("deg");
    }
    public void clickedLn() {
        out("deg");
    }
    public void clicked10Pow() {
        out("deg");
    }
    public void clickedLog() {
        out("deg");
    }
    public void clickedSqrt() {
        out("deg");
    }
    public void clicked2Pow() {
        out("deg");
    }
    public void clickedPow() {
        out("deg");
    }
    public void clickedInv() {
        out("deg");
    }
    public void clickedNFac() {
        out("deg");
    }
    public void clickedLeftPara() {
        out("deg");
    }
    public void clicked7() {
        out("deg");
    }
    public void clicked4() {
        out("deg");
    }
    public void clicked1() {
        out("deg");
    }
    public void clicked0() {
        out("deg");
    }
    public void clickedRightPara() {
        out("deg");
    }
    public void clicked8() {
        out("deg");
    }
    public void clicked5() {
        out("deg");
    }
    public void clicked2() {
        out("deg");
    }
    public void clickedAnswer() {
        out("deg");
    }
    public void clicked9() {
        out("deg");
    }
    public void clicked6() {
        out("deg");
    }
    public void clicked3() {
        out("deg");
    }

    public void clickedDot() {
        out("deg");
    }
    public void clickedBack() {
        out("deg");
    }
    public void clickedDivide() {
        out("deg");
    }
    public void clickedMultiply() {
        out("deg");
    }
    public void clickedMinus() {
        out("deg");
    }
    public void clickedPlus() {
        out("deg");
    }
    public void clickedClear() {
        out("deg");
    }
    public void clickedClearEntry() {
        out("deg");
    }
    public void clickedPlusMinus() {
        out("deg");
    }
    public void clickedEqual() {
        out("deg");
    }



    private void out(String s) {
        System.out.println(s);
    }
}
