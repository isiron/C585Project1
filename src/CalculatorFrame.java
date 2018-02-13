//Name, Date, Class Name, Purpose
/*
 * Name: Ivan Suarez
 * Date: 2/11/2018
 * Class name: CalculatorName.java
 * Purpose: Building the gui, including the key listeners and action listeners.
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by My on 2/11/2018.
 */
public class CalculatorFrame extends JFrame {
    private JPanel panel;
    private JLabel workingLabel;
    private JTextField textField;
    private JButton plusButton;
    private JButton minusButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JButton modButton;
    private JButton clearButton;
    private JButton ceButton;
    private JButton equalButton;

    //Menu stuff
    private JMenuBar menuBar;
    private JMenu app;
    private JMenu help;
    private JMenuItem exit;
    private JMenuItem about;

    //test change
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private static final int FIELD_LENGTH = 10;
    private static final int MODE_NORM = 0;
    private static final int MODE_PLUS = 1;
    private static final int MODE_MINUS = 2;
    private static final int MODE_DIVIDE = 3;
    private static final int MODE_MULTIPLY = 4;
    private static final int MODE_MOD = 5;
    private static final int MODE_EQUALS = 8;

    private Calculator calc;
    private ActionListener mouseListener;
    private KeyListener keyListener;
    private int currentMode;
    private int input;


    public CalculatorFrame(){
        calc = new Calculator();
        currentMode = MODE_NORM;
        buildMenu();
        buildButtons();
        buildPanel();
        buildFrame();
    }

    /**
     * Creates a mouse listener for buttons in this class
     */
    private void addClickListeners(){
        mouseListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(e.getActionCommand());
                panel.requestFocus();
            }
        };

        plusButton.addActionListener(mouseListener);
        minusButton.addActionListener(mouseListener);
        multiplyButton.addActionListener(mouseListener);
        divideButton.addActionListener(mouseListener);
        modButton.addActionListener(mouseListener);
        clearButton.addActionListener(mouseListener);
        ceButton.addActionListener(mouseListener);
        equalButton.addActionListener(mouseListener);
    }

    private void addKeyListeners(){
        keyListener = new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //if backspace is hit (key code 8) then interpret that as a C for clearing
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    handleAction("Back");
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    handleAction("=");
                else handleAction("" + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };


        panel.addKeyListener(keyListener);
    }

    private void handleAction(String action)
    {
        switch (action){
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
                textField.setText("0");
                break;
            case "Back":
                deleteTrailingChar();
                break;
            case "=":
                calculate();
                currentMode = MODE_EQUALS;
                workingLabel.setText("");
                textField.setText("" + calc.getValue());
                break;
            default:
                inputToTextBox(action);
        }
    }

    private void inputToTextBox(String numberToParse){
        //try to parse an int, if fail just ignore it, wasn't valid input
        try {
            input = Integer.parseInt(numberToParse);

            //if the box is empty or 0, replace the 0 with the number, else append the number to the end of the current text
            if (textField.getText().equals("") || textField.getText().equals("0") || currentMode == MODE_EQUALS) {
                if(currentMode == MODE_EQUALS)
                    currentMode = MODE_NORM;
                textField.setText("" + input);
            }
            else
                textField.setText(textField.getText() + input);
        } catch (NumberFormatException nfe) {
        }
    }

    private void deleteTrailingChar(){
        //if not already 0 or empty
        if (!textField.getText().equals("") || !textField.getText().equals("0")) {
            //if it is already a single digit number like 5 then set it to 0. Else delete the trailing number
            if (textField.getText().length() <= 1) {
                textField.setText("0");
            }
            else{
                //set text to current substring of current text, starting at position 0 (head) and ending
                //at 1 position before the final character
                textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            }
        }
    }

    //takes a current stored value and does appropriate math on it depending on mode using the value in the textField
    private void calculate(){
        try {
            input = Integer.parseInt(textField.getText());
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
                    JOptionPane.showMessageDialog(this, "Cannot divide by 0");
                else
                calc.divide(input);
                break;
            case MODE_MULTIPLY:
                calc.multiply(input);
                break;
            case MODE_MOD:
                calc.mod(input);
                break;
            default: break;
        }
    }

    private void updateLabel(String mode){
        //if label is empty then replace it, else work with value already stored
        if(workingLabel.getText().equals("")) {
            workingLabel.setText(textField.getText() + " " + mode + " ");

            try {
                calc.setValue(Integer.parseInt(textField.getText()));
            }
            catch (NumberFormatException nfe){
            }

            textField.setText("0");
        }
        else{
            //if say it is 6 + [6] when you hit - the mode with change,
            calculate();
            workingLabel.setText(calc.getValue() + " " + mode + " ");
            textField.setText("0");
        }
    }

    private void reset(){
        currentMode = MODE_NORM;
        calc.resetValue();
        workingLabel.setText("");
        textField.setText("" + calc.getValue());
    }

    private void buildMenu(){
        menuBar = new JMenuBar();
        app = new JMenu("App");
        help = new JMenu("Help");
        exit = new JMenuItem("Exit");
        about = new JMenuItem("About");
        app.add(exit);
        help.add(about);
        menuBar.add(app);
        menuBar.add(help);
    }

    private  void buildButtons(){
        plusButton = new JButton("+");
        minusButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");
        modButton = new JButton("%");
        clearButton = new JButton("C");
        ceButton = new JButton("CE");
        equalButton = new JButton("=");

        //the focus should not stick to the buttons
        plusButton.setFocusPainted(false);
        minusButton.setFocusPainted(false);
        multiplyButton.setFocusPainted(false);
        divideButton.setFocusPainted(false);
        modButton.setFocusPainted(false);
        clearButton.setFocusPainted(false);
        ceButton.setFocusPainted(false);
        equalButton.setFocusPainted(false);
    }

    private void buildPanel(){
        textField = new JTextField(FIELD_LENGTH);
        panel = new JPanel();
        workingLabel = new JLabel("");

        //the keylistener will handle input, don't edit textfield directly
        textField.setEditable(false);
        textField.setText("0");
        addClickListeners();
        addKeyListeners();

        panel.add(workingLabel);
        panel.add(textField);
        panel.add(plusButton);
        panel.add(minusButton);
        panel.add(multiplyButton);
        panel.add(divideButton);
        panel.add(modButton);
        panel.add(ceButton);
        panel.add(clearButton);
        panel.add(equalButton);
    }

    private void buildFrame(){
        setLayout(new BorderLayout());
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        add(panel);
        panel.requestFocus();
    }
}
