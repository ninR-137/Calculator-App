package com.example.emptyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button openParenthesis, closeParenthesis, percentage, allClear,
            seven, eight, nine, divide,
            four, five, six, multiply,
            one, two, three, minus,
            zero, dot, equal, plus;

    TextView resultView;
    public static String tempValue = "";
    public boolean existingInputValue;
    ArrayList<String> InputValues = new ArrayList<>();
    //ArrayList<Integer> prioOpsIndex = new ArrayList<>();
    ArrayList<Integer> openParenthesisIndex = new ArrayList<>();
    ArrayList<Integer> closeParenthesisIndex = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        temporaryDisable();
        setButtonsOnClick();
    }
    public void setupViews() {
        resultView = findViewById(R.id.resultTextView);
        openParenthesis = findViewById(R.id.openParenthesis);
        closeParenthesis = findViewById(R.id.closeParenthesis);
        percentage = findViewById(R.id.percentage);
        allClear = findViewById(R.id.AC);
        seven = findViewById(R.id.numberSeven);
        eight = findViewById(R.id.numberEight);
        nine = findViewById(R.id.numberNine);
        divide = findViewById(R.id.division);
        four = findViewById(R.id.numberFour);
        five = findViewById(R.id.numberFive);
        six = findViewById(R.id.numberSix);
        multiply = findViewById(R.id.multiplication);
        one = findViewById(R.id.numberOne);
        two = findViewById(R.id.numberTwo);
        three = findViewById(R.id.numberThree);
        minus = findViewById(R.id.subtraction);
        zero = findViewById(R.id.numberZero);
        dot = findViewById(R.id.dot);
        equal = findViewById(R.id.equals);
        plus = findViewById(R.id.plus);

        //setButtonsOnClick();
    }

    public void setButtonsOnClick(){
        numberButtons();
        basicOperations();
        complexOperations();
    }

    public void temporaryDisable(){
        //openParenthesis.setEnabled(false);
        //closeParenthesis.setEnabled(false);
        //percentage.setEnabled(false);
        //dot.setEnabled(false);
    }

    public void complexOperations() {
        openParenthesis.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            existingInputValue = false;
            tempValue = "";

            setTextValue(openParenthesis);
            InputValues.add(openParenthesis.getText().toString());
            openParenthesisIndex.add(InputValues.size() - 1);
        });
        closeParenthesis.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            existingInputValue = false;
            tempValue = "";

            setTextValue(closeParenthesis);
            InputValues.add(closeParenthesis.getText().toString());
            closeParenthesisIndex.add(InputValues.size() - 1);
        });
        //TODO : SET ON CLICK
        percentage.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            InputValues.add(percentage.getText().toString());
            existingInputValue = false;
            tempValue = "";
            setTextValue(percentage);
        });
        //TODO : SET ALL CLEAR
        allClear.setOnClickListener(v -> {
            resultView.setText("");
            tempValue = "";
            InputValues.clear();
            //prioOpsIndex.clear();
            closeParenthesisIndex.clear();
            openParenthesisIndex.clear();
            existingInputValue = false;
        });

        dot.setOnClickListener(v -> {
            numberOnclick(dot);
        });

        //TODO : FINISH EQUAL
        equal.setOnClickListener(v -> {
            if(!tempValue.equals("")) InputValues.add(tempValue);
            if(InputValues.isEmpty()) return;
            parenthesisParsing();
            if(!InputValues.isEmpty())resultView.setText(InputValues.get(0));
            //InputValues.clear();
            //prioOpsIndex.clear();
            tempValue = "";
            existingInputValue = true;

            //Toast.makeText(MainActivity.this, String.valueOf(prioOpsIndex.get(0)), Toast.LENGTH_SHORT).show();
        });
    }

    public void basicOperations(){
        //TODO : SET PLUS
        plus.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            InputValues.add(plus.getText().toString());
            existingInputValue = false;
            tempValue = "";
            setTextValue(plus);
        });

        minus.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            InputValues.add(minus.getText().toString());
            existingInputValue = false;
            tempValue = "";
            setTextValue(minus);
        });

        //TODO : SET MULTIPLY
        multiply.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            InputValues.add(multiply.getText().toString());
            existingInputValue = false;
            tempValue = "";
            setTextValue(multiply);
        });

        //TODO : SET DIVIDE
        divide.setOnClickListener(v -> {
            if(!tempValue.equals(""))InputValues.add(tempValue);
            InputValues.add(divide.getText().toString());
            existingInputValue = false;
            tempValue = "";
            setTextValue(divide);
        });
    }
    public void numberButtons() {
        zero.setOnClickListener(v -> {
            numberOnclick(zero);
        });
        one.setOnClickListener(v -> {
            numberOnclick(one);
        });
        two.setOnClickListener(v -> {
            numberOnclick(two);
        });
        three.setOnClickListener(v -> {
            numberOnclick(three);
        });
        four.setOnClickListener(v -> {
            numberOnclick(four);
        });
        five.setOnClickListener(v -> {
            numberOnclick(five);
        });
        six.setOnClickListener(v -> {
            numberOnclick(six);
        });
        seven.setOnClickListener(v -> {
            numberOnclick(seven);
        });
        eight.setOnClickListener(v -> {
            numberOnclick(eight);
        });
        nine.setOnClickListener(v -> {
            numberOnclick(nine);
        });
    }

    public void numberOnclick(Button button){
        if(existingInputValue)allClear.callOnClick();
        tempValue = tempValue.concat(button.getText().toString());
        setTextValue(button);
    }

    public boolean checkErrors(){
        boolean isValidOperation = InputValues.get(0).equals(plus.getText().toString()) ||
                InputValues.get(0).equals(minus.getText().toString());

        //if(tempValue.equals("")) InputValues.add("0");

        //if(isOperation(InputValues.get(0)) && isValidOperation){
        if(isValidOperation){
            float temp;
            try {
                temp = Float.parseFloat(InputValues.get(1));
            }catch (java.lang.NumberFormatException e){
                return true;
            }
            temp = temp * Float.parseFloat(InputValues.get(0).concat("1"));
            InputValues.set(1, String.valueOf(temp));
            InputValues.remove(0);
        }

        //This check would not work if parenthesis is involved
        //So the Parenthesis operation has to happen first
        for (int i = 0; i < InputValues.size(); i++){
            if(isOperation(InputValues.get(i)) && (i%2 == 0)){
                return true;
            }
        }
        return false;
    }
    //WORK IN PROGRESS CALCULATION LOGIC:
    public void calculate(){

        for(int i = 0; i < InputValues.size(); i++){
            if(InputValues.get(i).equals(percentage.getText().toString())) {
                float tempResult;
                try {
                    tempResult = Float.parseFloat(InputValues.get(i-1)) / 100;
                }catch (java.lang.NumberFormatException e){
                    allClear.callOnClick();
                    resultView.setText("SYNTAX ERROR");
                    return;
                }
                InputValues.set(i - 1, String.valueOf(tempResult));
                InputValues.remove(i);
                i--;
                //Toast.makeText(MainActivity.this, "CALLED", Toast.LENGTH_SHORT).show();
            }
        }

        if(checkErrors()){
            allClear.callOnClick();
            resultView.setText("SYNTAX ERROR");
            return;
        }


        //BASIC OPERATIONS >> MULTIPLY >> DIVIDE >> ADDITION >> SUBTRACTION
        ArrayList<Integer> prioOpsIndex = new ArrayList<>();
        int mode = 0; // PEMDAS LOGIC MODE 0 = MULTIPLY, and so on..
        while(mode != 4){
            for(int i = 0; i < InputValues.size(); i++){
                switch (mode){
                    case 0:
                        if(InputValues.get(i).equals(multiply.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                    case 1:
                        if(InputValues.get(i).equals(divide.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                    case 2:
                        if(InputValues.get(i).equals(plus.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                    case 3:
                        if(InputValues.get(i).equals(minus.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                }
            }
            mode++;
        }


        while(!prioOpsIndex.isEmpty()) {
            int index = prioOpsIndex.get(0);

            float firstValue;
            float secondValue;

            try {
                firstValue = Float.parseFloat(InputValues.get(index - 1));
                secondValue = Float.parseFloat(InputValues.get(index + 1));
            }catch (java.lang.NumberFormatException e){
                allClear.callOnClick();
                resultView.setText("SYNTAX ERROR");
                return;
            }catch (java.lang.IndexOutOfBoundsException e){
                firstValue = Float.parseFloat(InputValues.get(index - 1));
                InputValues.add("0");
                secondValue = 0;
            }

            float result;

            switch (InputValues.get(index)) {
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    result = firstValue / secondValue;
                    break;
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + InputValues.get(index));
            }

            InputValues.set(index - 1, String.valueOf(result));
            InputValues.remove(index + 1);
            InputValues.remove(index + 0);
            //prioOpsIndex.remove(0);
            for (int i = 0; i < prioOpsIndex.size(); i++) {
                if (prioOpsIndex.get(i) > prioOpsIndex.get(0)) {
                    prioOpsIndex.set(i, prioOpsIndex.get(i) - 2);
                }
            }
            prioOpsIndex.remove(0);
        }
        //Toast.makeText(MainActivity.this, String.valueOf(InputValues.get(0)), Toast.LENGTH_SHORT).show();
    }

    public void parenthesisParsing(){
        if(openParenthesisIndex.size() != closeParenthesisIndex.size()){
            allClear.callOnClick();
            resultView.setText("SYNTAX ERROR");
            return;
        }

        if(openParenthesisIndex.isEmpty()){
            calculateV2(0, InputValues.size());
            return;
        }

        //iterateInputValues();
        //Toast.makeText(MainActivity.this, String.valueOf(openParenthesisIndex.size()), Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, String.valueOf(InputValues.size()), Toast.LENGTH_SHORT).show();
        while(!openParenthesisIndex.isEmpty()){
            int openIndex  = openParenthesisIndex.get(openParenthesisIndex.size() - 1);
            int closeIndex = 0; // = closeParenthesisIndex.get(0);

            int i = openIndex;
            while (i < InputValues.size()){
                if(InputValues.get(i).equals(")")){
                    closeIndex = i;
                    break;
                }
                i++;
            }

            //Clean Up
            //____________________________________________________________________________________//
            //Removal for the Parenthesis in the Parenthesis set
            openParenthesisIndex.remove(openParenthesisIndex.size() - 1);

            for(int w = 0; w < closeParenthesisIndex.size(); w++) {
                if (closeParenthesisIndex.get(w) == closeIndex) {
                    closeParenthesisIndex.remove(w);
                    //Minus 1 to the close parenthesis' that comes after closeIndex
                    for (int index = 0; index < closeParenthesisIndex.size(); index++) {
                        if (closeParenthesisIndex.get(index) < closeIndex) continue;
                        closeParenthesisIndex.set(index, closeParenthesisIndex.get(index) - 1);
                    }
                    break;
                }
            }

            //____________________________________________________________________________________//


            //Toast.makeText(MainActivity.this, String.valueOf(InputValues.get(openIndex)), Toast.LENGTH_SHORT).show();
            //NEEDS TO HAVE ERROR HANDLING

            int tmp = InputValues.size();
            boolean emptyValue = (closeIndex - openIndex) < 2;
            if(checkErrorsV2(openIndex + 1, closeIndex - 1) || emptyValue){
                allClear.callOnClick();
                resultView.setText("SYNTAX ERROR");
                return;
            }

            int tmpDifference = tmp - InputValues.size();
            closeIndex -= tmpDifference;

            //____________________________________________________________________________________//
            //REMOVAL FOR THE PARENTHESIS IN THE INPUT VALUES SET
            InputValues.remove(closeIndex);
            InputValues.remove(openIndex);
            //____________________________________________________________________________________//
            //iterateInputValues();

            //String txt = String.valueOf(closeIndex);
            //Toast.makeText(MainActivity.this, InputValues.get(closeIndex-2), Toast.LENGTH_SHORT).show();
            calculateV2(openIndex, closeIndex - 2);
        }
        //Toast.makeText(MainActivity.this, String.valueOf(openParenthesisIndex.size()), Toast.LENGTH_SHORT).show();
        //String txt = String.valueOf(closeParenthesisIndex.size()) + " " + String.valueOf(openParenthesisIndex.size());
        //Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();
        /*
        StringBuilder txt = new StringBuilder();
        for(int i = 0; i < InputValues.size(); i++){
            txt.append(".").append(InputValues.get(i));
        }
        resultView.setText(txt);
        */

        if(InputValues.size() >= 3) calculateV2(0, InputValues.size());
    }


    public void iterateInputValues(){
        String delim = ".";
        String txt = "";
        txt = String.join(delim, InputValues);
        Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();
    }

    public boolean checkErrorsV2(int startIndex, int endIndex){
        boolean isValidOperation = InputValues.get(startIndex).equals(plus.getText().toString()) ||
                InputValues.get(startIndex).equals(minus.getText().toString());

        //if(tempValue.equals("")) InputValues.add("0");

        //if(isOperation(InputValues.get(0)) && isValidOperation){

        if(isValidOperation){
            float temp;
            try {
                temp = Float.parseFloat(InputValues.get(startIndex + 1));
            }catch (java.lang.NumberFormatException e){
                return true;
            }
            temp = temp * Float.parseFloat(InputValues.get(startIndex).concat("1"));
            //InputValues.set(1, String.valueOf(temp));
            InputValues.set(startIndex + 1, String.valueOf(temp));
            InputValues.remove(startIndex);
            endIndex--;
        }

        //This check would not work if parenthesis is involved
        /*
        for (int i = 0; i < InputValues.size(); i++){
            if(isOperation(InputValues.get(i)) && (i%2 == 0)){
                return true;
            }
        }
        */

        for(int i = startIndex; i < endIndex; i++){
            if(isOperation(InputValues.get(i)) && (i - startIndex) % 2 == 0){
                return true;
            }
        }

        return false;
    }
    //WORK IN PROGRESS CALCULATION LOGIC:
    public void calculateV2(int startIndex , int endIndex){
        //Toast.makeText(MainActivity.this, "CALLED", Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, String.valueOf(endIndex), Toast.LENGTH_SHORT).show();
        for(int i = startIndex; i < endIndex; i++){
            if(InputValues.get(i).equals(percentage.getText().toString())) {
                float tempResult;
                try {
                    tempResult = Float.parseFloat(InputValues.get(i-1)) / 100;
                }catch (java.lang.Exception e){
                    allClear.callOnClick();
                    resultView.setText("SYNTAX ERROR");
                    return;
                }
                InputValues.set(i - 1, String.valueOf(tempResult));
                InputValues.remove(i);
                endIndex--;
                i--;
                //Toast.makeText(MainActivity.this, "CALLED", Toast.LENGTH_SHORT).show();
            }
        }

        int tmp = InputValues.size();
        if(checkErrorsV2(startIndex, endIndex)){
            allClear.callOnClick();
            resultView.setText("SYNTAX ERROR");
            return;

        }

        int tmpDifference = tmp - InputValues.size();
        endIndex -= tmpDifference;

        //BASIC OPERATIONS >> MULTIPLY >> DIVIDE >> ADDITION >> SUBTRACTION
        ArrayList<Integer> prioOpsIndex = new ArrayList<>();
        int mode = 0; // PEMDAS LOGIC MODE 0 = MULTIPLY, and so on..
        while(mode != 4){
            for(int i = startIndex; i < endIndex; i++){
                switch (mode){
                    case 0:
                        if(InputValues.get(i).equals(multiply.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                    case 1:
                        if(InputValues.get(i).equals(divide.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                    case 2:
                        if(InputValues.get(i).equals(plus.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                    case 3:
                        if(InputValues.get(i).equals(minus.getText().toString())){
                            prioOpsIndex.add(i);
                        }
                        break;
                }
            }
            mode++;
        }

        //iterateInputValues();

        /*
        StringBuilder txt = new StringBuilder();
        for(int i = startIndex; i <= endIndex; i++){
            txt.append(".").append(InputValues.get(i));
        }
        */

        //Toast.makeText(MainActivity.this, String.valueOf(prioOpsIndex.size()), Toast.LENGTH_SHORT).show();


        //DANGER ZONE!!!
        while(!prioOpsIndex.isEmpty()) {
            int index = prioOpsIndex.get(0);

            float firstValue;
            float secondValue;

            try {
                firstValue = Float.parseFloat(InputValues.get(index - 1));
                secondValue = Float.parseFloat(InputValues.get(index + 1));
            }catch (java.lang.NumberFormatException e){
                allClear.callOnClick();
                resultView.setText("SYNTAX ERROR");
                return;
            }catch (java.lang.IndexOutOfBoundsException e){
                firstValue = Float.parseFloat(InputValues.get(index - 1));
                InputValues.add("0");
                secondValue = 0;
            }


            float result;

            switch (InputValues.get(index)) {
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    result = firstValue / secondValue;
                    break;
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + InputValues.get(index));
            }

            InputValues.set(index - 1, String.valueOf(result));
            InputValues.remove(index + 1);
            InputValues.remove(index + 0);
            endIndex -= 2;
            //prioOpsIndex.remove(0);
            for (int i = 0; i < prioOpsIndex.size(); i++) {
                if (prioOpsIndex.get(i) > prioOpsIndex.get(0)) {
                    prioOpsIndex.set(i, prioOpsIndex.get(i) - 2);
                }
            }
            prioOpsIndex.remove(0);
            //Toast.makeText(MainActivity.this, "CALLED", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isOperation(String input){
        return input.equals(multiply.getText().toString()) || input.equals(divide.getText().toString())
                ||input.equals(plus.getText().toString()) || input.equals(minus.getText().toString())||
                input.equals(percentage.getText().toString());
    }
    public void setTextValue(Button button){
        String text = button.getText().toString();
        resultView.setText(resultView.getText().toString().concat(text));
    }
}