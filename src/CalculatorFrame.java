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

    private CalculatorModel calcModel;
    private ActionListener mouseListener;
    private KeyListener keyListener;


    public CalculatorFrame(){
        calcModel = new CalculatorModel(this);
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
                calcModel.handleAction(e.getActionCommand());
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
                    calcModel.handleAction("Back");
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    calcModel.handleAction("=");
                else calcModel.handleAction("" + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        panel.addKeyListener(keyListener);
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

    public String getTextField()
    {
        return textField.getText();
    }

    public String getWorkingLabel()
    {
        return workingLabel.getText();
    }

    public void setTextField(String text){
        textField.setText(text);
    }

    public void setWorkingLabel(String text){
        workingLabel.setText(text);
    }
}
