/*
 * Name: Ivan Suarez
 * Date: 2/12/2018
 * Class name: CalculatorModel.java
 * Purpose: Handling input and and updating the labels
 */
import javax.swing.JOptionPane;

public class CalculatorModel {

    private static final int MODE_NORM = 0;
    private static final int MODE_PLUS = 1;
    private static final int MODE_MINUS = 2;
    private static final int MODE_DIVIDE = 3;
    private static final int MODE_MULTIPLY = 4;
    private static final int MODE_MOD = 5;
    private static final int MODE_EQUALS = 6;

    private Calculator calc;
    private CalculatorFrame cFrame;
    private int currentMode;
    private int input;

    /**
     * Handler of input and updater of the gui
     */
    public CalculatorModel(CalculatorFrame cFrame){
        calc = new Calculator();
        this.cFrame = cFrame;
    }

    /**
     * Checks the input from the keyboard and button and send it to the appropriate action
     */
    public void handleAction(String action)
    {
        switch (action){
            case "Exit":
                exitActionPerformed();
                break;
            case "About":
                aboutActionPerformed();
                break;
            case "-":
                updateLabel(action);
                currentMode = MODE_MINUS;
                break;
            case "+":
                updateLabel(action);
                currentMode = MODE_PLUS;
                break;
            case "*":
                updateLabel(action);
                currentMode = MODE_MULTIPLY;
                break;
            case "/":
                updateLabel(action);
                currentMode = MODE_DIVIDE;
                break;
            case "%":
                updateLabel(action);
                currentMode = MODE_MOD;
                break;
            case "C":
                reset();
                break;
            case "c":
                reset();
                break;
            case "CE":
                cFrame.setTextField("0");
                break;
            case "Back":
                deleteTrailingChar();
                break;
            case "=":
                calculate();
                currentMode = MODE_EQUALS;
                cFrame.setWorkingLabel("");
                cFrame.setTextField("" + calc.getValue());
                break;
            default:
                inputToTextBox(action);
        }
    }

    /**
     * Checks alpha numeric characters that weren't a function, and not an int it will be ignored
     */
    private void inputToTextBox(String numberToParse){
        //try to parse an int, if fail just ignore it, wasn't valid input
        try {
            input = Integer.parseInt(numberToParse);

            //if the box is empty or 0, replace the 0 with the number, else append the number to the end of the current text
            if (cFrame.getTextField().equals("") || cFrame.getTextField().equals("0") || currentMode == MODE_EQUALS) {
                if(currentMode == MODE_EQUALS)
                    currentMode = MODE_NORM;
                cFrame.setTextField("" + input);
            }
            else
                cFrame.setTextField(cFrame.getTextField() + input);
        } catch (NumberFormatException nfe) {
        }
    }

    /**
     * Removes the characters to the right of the string, unless it is already a single character, then it's set to 0
     */
    private void deleteTrailingChar(){
        //if not already 0 or empty
        if (!cFrame.getTextField().equals("") || !cFrame.getTextField().equals("0")) {
            //if it is already a single digit number like 5 then set it to 0. Else delete the trailing number
            if (cFrame.getTextField().length() <= 1) {
                cFrame.setTextField("0");
            }
            else{
                //set text to current substring of current text, starting at position 0 (head) and ending
                //at 1 position before the final character
                cFrame.setTextField(cFrame.getTextField().substring(0, cFrame.getTextField().length() - 1));
            }
        }
    }

    /*
     *takes a current stored value and does appropriate math on it depending on mode using the value in the textField
     */
    private void calculate(){
        try {
            input = Integer.parseInt(cFrame.getTextField());
        } catch (NumberFormatException nfe) {
        }
        switch (currentMode){
            case MODE_PLUS:
                calc.plus(input);
                break;
            case MODE_MINUS:
                calc.minus(input);
                break;
            case MODE_DIVIDE:
                if(input == 0)
                    JOptionPane.showMessageDialog(cFrame, "Cannot divide by 0");
                else
                    calc.divide(input);
                break;
            case MODE_MULTIPLY:
                calc.multiply(input);
                break;
            case MODE_MOD:
                if(input == 0)
                    JOptionPane.showMessageDialog(cFrame, "Cannot mod by 0");
                else
                    calc.mod(input);
                break;
            default: break;
        }
    }

    /**
     * Updates label based on given mode, sent as a string
     */
    private void updateLabel(String mode){
        //if label is empty then replace it, else work with value already stored
        if(cFrame.getWorkingLabel().equals("")) {
            cFrame.setWorkingLabel(cFrame.getTextField() + " " + mode + " ");

            try {
                calc.setValue(Integer.parseInt(cFrame.getTextField()));
            }
            catch (NumberFormatException nfe){
            }

            cFrame.setTextField("0");
        }
        else{
            calculate();
            cFrame.setWorkingLabel(calc.getValue() + " " + mode + " ");
            cFrame.setTextField("0");
        }
    }

    /**
     * reinitializes working values
     */
    private void reset(){
        currentMode = MODE_NORM;
        calc.resetValue();
        cFrame.setWorkingLabel("");
        cFrame.setTextField("" + calc.getValue());
    }

    /**
     * Closes app
     */
    private void exitActionPerformed()
    {
        cFrame.dispose();
    }

    /*
     * Shows an about message
     */
    private void aboutActionPerformed()
    {
        JOptionPane.showMessageDialog(cFrame, "Easy Calc by Ivan Suarez");
    }
}
