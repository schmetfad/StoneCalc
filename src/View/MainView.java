package View;

import Models.Calculator;
import com.sun.javafx.fxml.expression.Expression;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.HeaderTokenizer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import Defs.Defs;
import Models.Token;
import Models.Token.input;
import Controller.CalculatorController;

import java.awt.*;


import static Models.Token.input.*;
import static javafx.application.Application.launch;

/**
 * Created by stefa on 2017-02-28.
 */
public class MainView extends Application{

    private
        GridPane grid;
        Button btnDeg, btn2nd, btnSin, btnCos, btnTan;
        CalculatorController cctrl;
        Label lPrevAnswer;
        Label lAnswer;

    @Override
    public void start(Stage primareStage) {
        primareStage.setTitle("StoneCalc");
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, Defs.width, Defs.height);
        primareStage.setScene(scene);

        buildGUI();
         cctrl = new CalculatorController(this);

        primareStage.show();

    }

    public void setBtnDeg(String text) {
        btnDeg.setText(text);
    }

    public void setBtnSin(String text) {
        btnSin.setText(text);
    }

    public void setBtnCos(String text) {
        btnCos.setText(text);
    }

    public void setBtnTan(String text) {
        btnTan.setText(text);
    }

    public void setBtn2nd(String text) {
        btn2nd.setText(text);
    }

    public void setlAnswer(String text) {
        lAnswer.setText(text);
    }

    public void setlPrevAnswer(String text) {
        lPrevAnswer.setText(text);
    }

    private void buildGUI() {
        int colIdx = 0;
        int rowIdx = 0;
        // first row with "prev answer" text
        lPrevAnswer = new Label("answer");
        HBox hbPrevAnswer = new HBox(0);
        hbPrevAnswer.setAlignment(Pos.BOTTOM_RIGHT);
        hbPrevAnswer.getChildren().add(lPrevAnswer);
        grid.add(hbPrevAnswer, colIdx,rowIdx, 8, 1);
        rowIdx++;

        // second row with "answer" text
        lAnswer = new Label("answer");
        lAnswer.setFont(Font.font("Tahoma",FontWeight.NORMAL,35));
        HBox hbAnswer = new HBox(0);
        hbAnswer.setAlignment(Pos.TOP_RIGHT);
        hbAnswer.getChildren().add(lAnswer);
        grid.add(hbAnswer, colIdx,rowIdx, 8, 1);
        rowIdx++;


        Button btn;
        // first column
        btnDeg = createButton("deg", colIdx, rowIdx, Deg, Deg->cctrl.clicked(Deg));
        rowIdx++;

        createButton(Token.pi, colIdx,rowIdx, Pi, Pi->cctrl.clicked(Pi));
        rowIdx++;

        btnSin = createButton("sin",colIdx,rowIdx, Sin, Sin->cctrl.clicked(Sin));
        rowIdx++;

        btnCos = createButton("cos", colIdx, rowIdx, Cos, Cos->cctrl.clicked(Cos));
        rowIdx++;

        btnTan = createButton("tan", colIdx, rowIdx, Tan, Tan->cctrl.clicked(Tan));

        // second column
        rowIdx = 2;
        colIdx = 1;

        btn2nd = createButton("2nd", colIdx, rowIdx, Second, Second->cctrl.clicked(Second));
        rowIdx++;

        createButton(" e ", colIdx, rowIdx, E, E->cctrl.clicked(E));
        rowIdx++;

        createButton("ln", colIdx, rowIdx, Ln, Ln->cctrl.clicked(Ln));
        rowIdx++;

        createButton("10^", colIdx, rowIdx, TenPow, TenPow->cctrl.clicked(TenPow));
        rowIdx++;

        createButton("log", colIdx, rowIdx, Log, Log->cctrl.clicked(Log));

        // third column
        rowIdx = 2;
        colIdx++;

        createButton(Token.sqrt, colIdx, rowIdx, Sqrt, Sqrt->cctrl.clicked(Sqrt));
        rowIdx++;

        String s = "x" +(char) 0x00B2;
        createButton(s, colIdx, rowIdx, TwoPow, TwoPow->cctrl.clicked(TwoPow));
        rowIdx++;

        createButton(" ^ ", colIdx, rowIdx, Pow, Pow->cctrl.clicked(Pow));
        rowIdx++;

        createButton("1/x", colIdx, rowIdx, Inv, Inv->cctrl.clicked(Inv));
        rowIdx++;

        createButton("n!", colIdx, rowIdx, NFac, NFac->cctrl.clicked(NFac));

        // forth column
        rowIdx = 2;
        colIdx++;

        createButton(" ( ", colIdx, rowIdx, LeftPara, LeftPara->cctrl.clicked(LeftPara));
        rowIdx++;

        createButton(" 7 ", colIdx, rowIdx, Seven, Seven->cctrl.clicked(Seven));
        rowIdx++;

        createButton(" 4 ", colIdx, rowIdx, Four, Four->cctrl.clicked(Four));
        rowIdx++;

        createButton(" 1 ",colIdx, rowIdx, One, One->cctrl.clicked(One));
        rowIdx++;

        createButton(" 0 ", colIdx, rowIdx, 2, 1, Zero, Zero->cctrl.clicked(Zero));

        // fifth column
        rowIdx = 2;
        colIdx++;

        createButton(" ) ", colIdx, rowIdx, RightPara, RightPara->cctrl.clicked(RightPara));
        rowIdx++;

        createButton(" 8 ", colIdx, rowIdx, Eight, Eight->cctrl.clicked(Eight));
        rowIdx++;

        createButton(" 5 ", colIdx, rowIdx, Five, Five->cctrl.clicked(Five));
        rowIdx++;

        createButton(" 2 ", colIdx, rowIdx, Two, Two->cctrl.clicked(Two));

        // sixth column
        rowIdx = 2;
        colIdx++;

        createButton("Ans", colIdx, rowIdx, Answer,Answer->cctrl.clicked(Answer));
        rowIdx++;

        createButton(" 9 " , colIdx, rowIdx, Nine, Nine->cctrl.clicked(Nine));
        rowIdx++;

        createButton(" 6 ", colIdx, rowIdx, Six, Six->cctrl.clicked(Six));
        rowIdx++;

        createButton(" 3 ", colIdx, rowIdx, Three, Three->cctrl.clicked(Three));
        rowIdx++;

        createButton(" . ", colIdx, rowIdx, Dot, Dot->cctrl.clicked(Dot));

        // seventh column
        rowIdx = 2;
        colIdx++;

        s = ""+(char)0x21d0;
        createButton(s, colIdx, rowIdx, Back, Bakc->cctrl.clicked(Back));
        rowIdx++;

        createButton(" / ", colIdx, rowIdx, Divide, Divide->cctrl.clicked(Divide));
        rowIdx++;

        createButton(" * ", colIdx, rowIdx, Multiply, Multiply->cctrl.clicked(Multiply));
        rowIdx++;

        createButton(" - ", colIdx, rowIdx, Minus, Minus->cctrl.clicked(Minus));
        rowIdx++;

        createButton(" + ", colIdx, rowIdx, Plus, Plus->cctrl.clicked(Plus));

        // eight column
        rowIdx = 2;
        colIdx++;

        createButton(" C ", colIdx, rowIdx, Clear, Clear->cctrl.clicked(Clear));
        rowIdx++;

        createButton("CE", colIdx, rowIdx, ClearEntry, ClearEntry->cctrl.clicked(ClearEntry));
        rowIdx++;

        String plusMinus = ""+(char)0x00B1;
        createButton(plusMinus, colIdx, rowIdx, PlusMinus, PlusMinus->cctrl.clicked(PlusMinus));
        rowIdx++;

        createButton(" = ", colIdx, rowIdx, 1, 2, Equal, Equal->cctrl.clicked(Equal));



    }

    interface IClicked {
        void click(Token.input t);
    }

    private Button createButton(String sym, int col, int row,Token.input t, IClicked c) {
        return createButton(sym, col, row, 1,1, t, c);
    }

    private Button createButton(String sym, int col, int row, int colSpan, int rowSpan, Token.input t, IClicked c) {
        Button btn = new Button(sym);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c.click(t);
            }
        });
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        grid.add(btn, col, row, colSpan, rowSpan);
        return btn;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
